package com.coffe.shentao.mvvmdome2.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.coffe.shentao.mvvmdome2.R;
import com.coffe.shentao.mvvmdome2.databinding.ActivityMainBinding;
import com.coffe.shentao.mvvmdome2.vo.Resource;
import com.coffe.shentao.mvvmdome2.vo.User;

public class MainActivity extends AppCompatActivity {
    //自动生成的ViewDataBinding ，类名是根据xml名称自动生成
    private ActivityMainBinding mainBinding;
    //ViewModel
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 替换setContentView()
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // 注意：这里不可以直接new MainViewModel()
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        //设置事件处理器
        mainBinding.setEventHandler(new MainEventHandler(this));
        //获取userLiveData
        LiveData<Resource<User>> userLiveData = mainViewModel.getUser();
        //观察userLivedata中的数据(User)变化
        userLiveData.observe(this, userResource -> {
            //绑定到DataBinding,set**()方法根据xml中的<var.. >标签自动生成.
            mainBinding.setLoadStatus(userResource == null ? null : userResource.status);
            mainBinding.setUser(userResource == null ? null : userResource.data);
            mainBinding.setResource(userResource);
        });

    }
    //eventHander调用这个
    void onSearchUser(String text) {
        //通知ViewModel
        mainViewModel.setUserName(text);
    }
}
