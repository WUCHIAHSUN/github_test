package com.example.a365test.API;

import android.util.Log;

import com.example.a365test.API.Data.ResultHttpData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class HttpConnect {
    private static final String TAG = "HttpConnect";
    private static final int TIMEOUT = 30000; // 超时 30 秒

    private static final String CRLF = "\r\n";
    private static final String TWO_HYPHENS = "--";
    private static final String BOUNDARY = "***************";

    private String mUrl;
    private String mApiToken;
    private Map<String, String> mParams;
    private int mTag;
    private int mType;

    private ResultHttpData result;

    public HttpConnect(String url, String apiToken, Map<String, String> params, int tag) {
        this.mUrl = url;
        this.mApiToken = apiToken;
        this.mParams = params;
        this.mTag = tag;
        this.mType = 0;
        if (result != null) {
            result.setTag(mTag);
        }
    }

    public ResultHttpData getResultFromUrl2() {
        ResultHttpData resultData = new ResultHttpData();
        resultData.setTag(mTag);
        String responseString = "";
        OutputStreamWriter outputStream = null;
        try {
            Gson gson = new Gson();
            Log.w(TAG, "run - 取自 HashMap 的資料: " + gson.toJson(mParams));
            String data = gson.toJson(mParams);
            Log.w(TAG, "run - 編碼 Json 資料: " + data);

            // ##########################################################################################
            URL url = new URL(mUrl);
            // ##########################################################################################
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(TIMEOUT); // 設置連接超時時間.
            conn.setReadTimeout(180 * 1000); // 設置讀取超時時間.
            conn.setDoInput(true); // 打開輸入流，從伺服器獲取資料.
            conn.setRequestMethod("GET"); // 設置請求方法為 GET.
            conn.setUseCaches(false); // 使用 GET 方式不使用緩存.
            conn.setRequestProperty("Content-Type", "application/json");

            int respCode = conn.getResponseCode();
            resultData.setRtnCode(respCode);

            BufferedReader bufferedReader = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                throw new SocketTimeoutException();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            responseString = stringBuilder.toString();
            String strResult = responseString;
            Log.w(TAG, "run - 從伺服器讀取的編碼字串資料: " + strResult);
            resultData.setRtnDataString(strResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultData;
    }
}
