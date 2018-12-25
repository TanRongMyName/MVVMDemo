package com.coffe.shentao.mvvmdome2.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.coffe.shentao.mvvmdome2.api.ApiResponse;
import com.coffe.shentao.mvvmdome2.api.ApiService;
import com.coffe.shentao.mvvmdome2.vo.Resource;
import com.coffe.shentao.mvvmdome2.vo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepo {
    private static UserRepo userRepo = new UserRepo();

    public static UserRepo getInstance() {
        return userRepo;
    }
    public LiveData<Resource<User>> getUser(String userId) {
        MutableLiveData<Resource<User>> userEntityLiveData = new MutableLiveData<>();
        userEntityLiveData.postValue(Resource.loading(null));
        //请求网络
        ApiService.INSTANCE.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                ApiResponse<User> apiResponse = new ApiResponse<>(response);
                Log.v("","");
                if (apiResponse.isSuccessful()) {
                    userEntityLiveData.postValue(Resource.success(response.body()));
                } else {
                    userEntityLiveData.postValue(Resource.error(apiResponse.errorMessage, null));
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                userEntityLiveData.postValue(Resource.error(t.getMessage(), null));
            }
        });
        return userEntityLiveData;
    }

}
