package com.paymentgameservice.service;

import lombok.NonNull;
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
}
