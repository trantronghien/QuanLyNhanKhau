package com.it.hientran.tracuunhankhau.internet;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 5/25/2017.
 */

public final class RequestServer {

    public static final String getJsonFromServer(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response responses = client.newCall(request).execute();
        return responses.body().string();
    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static final String getInfoFromServer(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add(Config.POST_KEY , Config.POST_VALUE)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().string();
    }

    /**
     * include add multi parameter post request to server
     * @param url  --> url server
     * @param params   is one map include key and value
     * @return  json from server response
     * @throws IOException
     */
    public final static String getJsonUsingPostServer (String url, Map<String , String> params) throws IOException {
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        for ( Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.add(key , value);
        }

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().string();
    }
}
