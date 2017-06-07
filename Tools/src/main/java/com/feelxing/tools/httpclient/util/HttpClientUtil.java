package com.feelxing.tools.httpclient.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MEV on 2017/5/4.
 */
public class HttpClientUtil {
    public static void main(String[] args) throws Exception{
       // getRequesetString("http://210.12.2.66:8287/cityRank/getComSomeAreaRank?groupId=1&rankType=1");
        JsonObject obj=getRequesetJSONObject("http://210.12.2.66:8287/cityRank/getComSomeAreaRank?groupId=1&rankType=1");
        System.out.println(obj.get("result").getAsString());
    }
    public static String  getRequesetString(String url) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //"http://210.12.2.66:8287/cityRank/getComSomeAreaRank?groupId=1&rankType=1"
            HttpGet httpget = new HttpGet(url);
            System.out.println("Executing request " + httpget.getRequestLine());
            ResponseHandler<String> responseHandler=setStringResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("url:"+url);
            System.out.println("response:"+responseBody);
            return responseBody;
        } finally {
            httpclient.close();
        }
    }
    public static JsonObject  getRequesetJSONObject(String url) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //"http://210.12.2.66:8287/cityRank/getComSomeAreaRank?groupId=1&rankType=1"
            HttpGet httpget = new HttpGet(url);
            System.out.println("Executing request " + httpget.getRequestLine());
            ResponseHandler<JsonObject> responseHandler=setJsonResponseHanlder();
            JsonObject responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("url:"+url);
            System.out.println("response:"+responseBody);
            return responseBody;
        }finally {
            httpclient.close();
        }
    }
    public static JsonObject postRequesetJSONObject(String url,Map<String,String> map) throws Exception{
        CloseableHttpClient httpClient=HttpClients.createDefault();
        try{
            HttpPost httpPost=new HttpPost(url);
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            if(map!=null&&map.size()>0){
                nvps=assemblyParm(map);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            ResponseHandler<JsonObject> responseHandler=setJsonResponseHanlder();
            JsonObject responseBody = httpClient.execute(httpPost, responseHandler);
            return  responseBody;
        }finally {
            httpClient.close();
        }
    }
    private static List<NameValuePair> assemblyParm(Map<String,String> map){
        List<NameValuePair> formparams=new ArrayList<NameValuePair>();
        if(map!=null){
            map.forEach((k,v)->{
                formparams.add(new BasicNameValuePair(k,v));
            });
        }
        return  formparams;
    }

    /**
     * 设置返回结果为JSONObject
     * @return
     */
    private static ResponseHandler<JsonObject> setJsonResponseHanlder(){
        ResponseHandler<JsonObject> rh=new ResponseHandler<JsonObject>() {
            @Override
            public JsonObject handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                StatusLine statusLine = httpResponse.getStatusLine();
                HttpEntity entity = httpResponse.getEntity();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(
                            statusLine.getStatusCode(),
                            statusLine.getReasonPhrase());
                }
                if (entity == null) {
                    throw new ClientProtocolException("Response contains no content");
                }
                Gson gson = new GsonBuilder().create();
                ContentType contentType = ContentType.getOrDefault(entity);
                Charset charset = contentType.getCharset();
                Reader reader = new InputStreamReader(entity.getContent(), charset);
                return gson.fromJson(reader, JsonObject.class);
            }
        };
        return  rh;
    }
    private static ResponseHandler setStringResponseHandler(){
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(
                    final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        return  responseHandler;
    }
}
