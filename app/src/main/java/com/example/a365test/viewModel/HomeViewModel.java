package com.example.a365test.viewModel;

import android.content.Context;
import android.graphics.Rect;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a365test.API.ApiAction;
import com.example.a365test.API.ApiManager;
import com.example.a365test.API.ApiService;
import com.example.a365test.API.Data.ResultHttpData;
import com.example.a365test.API.Data.UsersListData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.view.View;

public class HomeViewModel extends ViewModel implements ApiService {
    private MutableLiveData<UsersListData> usersListData = new MutableLiveData<>();
    public MutableLiveData<Integer> since = new MutableLiveData<>();

    public MutableLiveData<UsersListData> getUsersListData() {
        return usersListData;
    }
    private MutableLiveData<Boolean> isBottom = new MutableLiveData<>(false);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private UsersListData mUserData = new UsersListData();
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public void getList(ResultHttpData resultHttpData) {
        if (resultHttpData == null || resultHttpData.getRtnData() == null) {
            return;
        }
        if (resultHttpData.getRtnData() instanceof UsersListData) {
            UsersListData userData = (UsersListData) resultHttpData.getRtnData();
            mUserData.getData().addAll(userData.getData());
            mainHandler.post(() -> {
                usersListData.setValue(mUserData);
            });
        }
    }

    public void getListApi() {
        ApiManager apiViewModel = ApiManager.getInstance();
        if (apiViewModel == null) {
            return;
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                apiViewModel.getList(ApiAction.API_GET_USER_LIST, since.getValue(), HomeViewModel.this);  // Replace "since_value" with the actual value.
            }
        });
    }

    @Override
    public void onResult(int tag, ResultHttpData data) {
        if (data != null){
            switch (tag){
                case ApiAction.API_GET_USER_LIST:
                    getList(data);
                    break;
            }
        }
    }
    public void isBottomListener(RecyclerView recyclerView){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Boolean.TRUE.equals(isBottom.getValue())){
                    return;
                }
                // 確認 RecyclerView 是否已經滑到底部
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount(); // 總項目數量
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition(); // 最後一個可見項目的位置

                    // 檢查是否滑動到最底部
                    if (lastVisibleItemPosition == totalItemCount - 1) {
                        isBottom.setValue(true);
                    }
                }
            }
        });
    }

    public MutableLiveData<Boolean> getIsBottom() {
        return isBottom;
    }
}
