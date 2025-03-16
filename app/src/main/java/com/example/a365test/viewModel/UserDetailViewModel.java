package com.example.a365test.viewModel;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a365test.API.ApiAction;
import com.example.a365test.API.ApiManager;
import com.example.a365test.API.ApiService;
import com.example.a365test.API.Data.ResultHttpData;
import com.example.a365test.API.Data.UserDetailData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserDetailViewModel extends ViewModel implements ApiService {

    private MutableLiveData<UserDetailData> usersData = new MutableLiveData<>();
    private MutableLiveData<String> imageLogo = new MutableLiveData<>();
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> location = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler mainHandler = new Handler(Looper.getMainLooper());

    public void getList(ResultHttpData resultHttpData) {
        if (resultHttpData == null || resultHttpData.getRtnData() == null) {
            return;
        }
        if (resultHttpData.getRtnData() instanceof UserDetailData) {
            UserDetailData mUserData = (UserDetailData) resultHttpData.getRtnData();
            mainHandler.post(() -> {
                imageLogo.setValue(mUserData.getAvatar_url());
                name.setValue(mUserData.getName());
                location.setValue(mUserData.getLocation());
                email.setValue(mUserData.getEmail());
            });
        }
    }

    public void getListApi(int pos) {
        ApiManager apiViewModel = ApiManager.getInstance();
        if (apiViewModel == null) {
            return;
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                apiViewModel.getUser(ApiAction.API_GET_USER_DETAIL, pos, UserDetailViewModel.this);  // Replace "since_value" with the actual value.
            }
        });
    }
    @Override
    public void onResult(int tag, ResultHttpData data) {
        if (data != null){
            switch (tag){
                case ApiAction.API_GET_USER_DETAIL:
                    getList(data);
                    break;
            }
        }
    }
    public MutableLiveData<UserDetailData> getUsersData() {
        return usersData;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> getLocation() {
        return location;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getImageLogo() {
        return imageLogo;
    }
}
