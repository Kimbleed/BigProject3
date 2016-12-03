package com.example.awesoman.bigproject3.network;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Awesome on 2016/11/17.
 */

public abstract class UICallback implements Callback {

    Handler handler =new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(final Call call,final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailureInUi(call,e);
            }
        });
    }

    @Override
    public void onResponse(final Call call,final Response response) throws IOException {
        try{
            Log.i("response",response.body().toString());
            if(!response.isSuccessful()){
                throw new IOException("error code:"+response.code());
            }
            else {
                final String content = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onResponseInUi(call,content);
                    }
                });
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public abstract void onFailureInUi(Call call, IOException e);
    public abstract void onResponseInUi(Call call , String  body);

}
