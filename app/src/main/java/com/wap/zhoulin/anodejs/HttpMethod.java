package com.wap.zhoulin.anodejs;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhoulin on 2014/12/24.
 */
public class HttpMethod {
    public static String downloadJSON(String url) {
        HttpGet httpGet;
        HttpResponse response;
        Log.d("ANODEJS http get", url);
        try {
            httpGet = new HttpGet(url);
            response = new DefaultHttpClient().execute(httpGet);
            Log.d("ANODEJS response",response.toString());
            if(response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, HTTP.UTF_8);
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            Log.d("ANODEJS http get",e.toString());
            return "{'error':'true'}";
        }finally {
            httpGet = null;
            response = null;
        }

    }
}
