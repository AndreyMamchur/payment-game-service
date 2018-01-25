package com.paymentgameservice.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DoRequest {
    final static String requestUrl = "https://test.lgaming.net/external/extended";

    public static String putXml(String xml){
        String response = "";
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection) new URL(requestUrl).openConnection();
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/xml");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(xml.getBytes());
            outputStream.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED){
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            System.out.println("Output from Server .....\n");
            while ((response = bufferedReader.readLine()) != null){
                System.out.println(response);
            }

            connection.disconnect();
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
