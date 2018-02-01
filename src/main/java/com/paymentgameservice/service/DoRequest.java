package com.paymentgameservice.service;

import lombok.NonNull;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class DoRequest {
    final static String requestUrl = "https://test.lgaming.net/external/extended";

    //отправляет запрос и читает ответ
    public String putXml(@NonNull String signXml, @NonNull String xml){
        String response = "";
        HttpURLConnection connection = null;
        OutputStreamWriter outputStreamWriter = null;
        try{
            connection = (HttpURLConnection) new URL(requestUrl).openConnection();
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("PayLogic-Signature", signXml);
            connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

            outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(xml);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                System.out.println(response);
                response += line;
            }
            System.out.println("----------RESPONSE INFORMATION----------");
            System.out.println( "Permission: " + connection.getPermission());
            System.out.println(connection.getHeaderFields());
            System.out.println();
            bufferedReader.close();
            connection.disconnect();
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection !=null){
                connection.disconnect();
            }
        }
        return response;
    }

    public String postXml(@NonNull String signXml, @NonNull String xml){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(requestUrl);
        httppost.addHeader("PayLogic-Signature", signXml);
        httppost.addHeader("Content-Type", "text/xml;charset=UTF-8");
        HttpEntity entity = null;
        try {
            entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httppost.setEntity(entity);

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(20000)
                .setConnectTimeout(20000)
                .setConnectionRequestTimeout(20000)
                .build();
        httppost.setConfig(requestConfig);

        System.out.println("executing request " + httppost.getURI());
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = "";
        try {
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Header header:response.getAllHeaders()) {
            System.out.println(header);
        }

        return result;
    }
}
