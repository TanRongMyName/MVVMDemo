package com.coffe.shentao.mvvmdome2.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.coffe.shentao.mvvmdome2.repo.UserRepo;
import com.coffe.shentao.mvvmdome2.vo.Resource;
import com.coffe.shentao.mvvmdome2.vo.User;

public class MainViewModel extends ViewModel {
    private final UserRepo userRepo = UserRepo.getInstance();
    private final MutableLiveData<String> userNameLiveData = new MutableLiveData<>();
    private final LiveData<Resource<User>> userEntityLiveData;

    public MainViewModel() {
        //switchMap:当userNameLiveData中的数据发生变化时 触发input事件，
        userEntityLiveData = Transformations.switchMap(userNameLiveData, input -> {
            Log.v("Tanrong","输入的数据是："+input);
            if (input == null) {
                return new MutableLiveData<>();
            } else {
                //如果收到新的input（userName），那么就去UserRepo获取这个用户的信息
                //返回值将赋值给userEntityLiveData；
                Log.v("Tanrong","网络请求！！！");
                return userRepo.getUser(input);
            }
        });
    }

    public LiveData<Resource<User>> getUser() {
        return userEntityLiveData;
    }

    public void setUserName(String userName) {
        //将userName设置给userNameLiveData
        userNameLiveData.postValue(userName);
    }
}

