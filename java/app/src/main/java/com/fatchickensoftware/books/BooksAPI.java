package com.fatchickensoftware.books;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BooksAPI {

    public interface SearchCallback {
        void onResult(Volumes volumes);
    }

    private OkHttpClient client;

    public BooksAPI(OkHttpClient client) {
        this.client = client;
    }

    public void search(String apiKey, String query, int startIndex, int maxResults, SearchCallback callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://www.googleapis.com/books/v1/volumes").newBuilder();
        urlBuilder.addQueryParameter("q", query);
        urlBuilder.addQueryParameter("startIndex", Integer.toString(startIndex));
        urlBuilder.addQueryParameter("maxResults", Integer.toString(maxResults));

        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .addHeader("key", apiKey)
                .url(urlBuilder.build())
                .build();

        asyncRequest(request, callback);
    }

    private void asyncRequest(Request request, SearchCallback callback) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onResult(null);
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    try {
                        callback.onResult(new Gson().fromJson(response.body().string(), Volumes.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onResult(null);
                    }
                } else {
                    callback.onResult(null);
                }
            }
        });
    }
}
