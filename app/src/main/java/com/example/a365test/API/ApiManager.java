package com.example.a365test.API;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import com.example.a365test.API.Data.ResultHttpData;
import com.example.a365test.API.Data.UserDetailData;
import com.example.a365test.API.Data.UsersListData;
import com.google.gson.Gson;

public class ApiManager extends ViewModel {
    private int mTag = 0;

    private static String TAG = "ApiManager";
    private static ApiManager connectManager = null;
    private static Gson gson = null;
    private static String API_URL = ""; // 后端网址
    private ApiService apiService;

    public static ApiManager getInstance() {
        if (connectManager == null) {
            connectManager = new ApiManager();
        }
        if (gson == null) {
            gson = new Gson();
        }
        return connectManager;
    }

    // 取得园区列表
    public void getList(int tag, int since, ApiService apiService) {
        this.apiService = apiService;
        mTag = tag;
        API_URL = "https://api.github.com/users?" + "since=" + since + "&per_page=20";
        String mData = "";
        Run(mData);
    }

    public void getUser(int tag, int id, ApiService apiService) {
        this.apiService = apiService;
        mTag = tag;
        API_URL = "https://api.github.com/users/" + id;
        String mData = "";
        Run(mData);
    }

    private void Run(String dataPost) {
        try {
            Map<String, String> params = getMap(dataPost);
            String apiT = "";
            apiService.onResult(mTag, toJson(new HttpConnect(API_URL, apiT, params, mTag).getResultFromUrl2()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Map<String, String> getMap(String dataPost) {
        Map<String, String> params = new HashMap<>();
        String[] dataList = dataPost.split("&");
        for (String data : dataList) {
            String[] mapView = data.split("=");
            if (mapView.length >= 2) {
                params.put(mapView[0], mapView[1]);
            } else if (mapView.length == 1) {
                params.put(mapView[0], ""); // 传送 key 值
            }
        }
        return params;
    }

    public ResultHttpData toJson(ResultHttpData data) {
        String encryptStringRtn = data.getRtnDataString();
        String decryptString = "";
        JSONObject jsonResult = null;
        try {
            if (!encryptStringRtn.isEmpty()) {
                jsonResult = new JSONObject(encryptStringRtn);
                Log.w(TAG, "run - 从服务器读取的解码 JSON 数据: " + jsonResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (jsonResult == null) {
                try {
                    // 將 JSON 字串轉為 JSONArray
                    JSONArray jsonArray = new JSONArray(encryptStringRtn);
                    jsonResult = new JSONObject();
                    jsonResult.put("data", jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (jsonResult != null) {
            decryptString = jsonResult.toString();
            DataInfo dataStruct = JsonStringToObject(decryptString, data.getTag());
            data.setRtnData(dataStruct.rtnData);
            if (!dataStruct.isParsered) {
                data.setRtnCode(RtnCode.RtnCode_CustomParserFail);
            }
        } else {
            decryptString = encryptStringRtn;
        }
        data.setRtnDataString(decryptString);
        return data;
    }

    private DataInfo JsonStringToObject(String jsonString, int action) {
        DataInfo dataInfo = new DataInfo();
        try {
            switch (action) {
                case ApiAction.API_GET_USER_LIST:
                    dataInfo.rtnData = gson.fromJson(jsonString, UsersListData.class);
                    break;
                case ApiAction.API_GET_USER_DETAIL:
                    dataInfo.rtnData = gson.fromJson(jsonString, UserDetailData.class);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataInfo.isParsered = false;
        }
        return dataInfo;
    }

    private static class DataInfo {
        public Object rtnData = null;
        public boolean isParsered = true;
    }
}
