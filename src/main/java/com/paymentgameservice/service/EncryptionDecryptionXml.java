package com.paymentgameservice.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.*;

import java.util.List;

@Component
public class EncryptionDecryptionXml {
    public static final String privateFile = "src\\main\\resources\\private.pem";
    public static final String publicFile = "src\\main\\resources\\public.pem";

    public String sign(String message) throws SignatureException {
        try {
            Signature sign = Signature.getInstance("SHA1withRSA");
            String key = readFileToString(privateFile);
            PrivateKey privateKey = privateKeyFromString(key);
            sign.initSign(privateKey);
            sign.update(message.getBytes("UTF-8"));
            return new String(Base64.encodeBase64(sign.sign()),"UTF-8");
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }
    }

    public boolean verify(String message, String signature) throws SignatureException{
        try {
            Signature sign = Signature.getInstance("SHA1withRSA");
            String key = readFileToString(publicFile);
            PublicKey publicKey = publicKeyFromString(key);
            sign.initVerify(publicKey);
            sign.update(message.getBytes("UTF-8"));
            return sign.verify(Base64.decodeBase64(signature.getBytes("UTF-8")));
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }
    }

    public String readFileToString(String nameOfFile){
        String key="";
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(nameOfFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String line: lines){
            key=key + line + "\n";
        }
        System.out.println(key);
        return key;
    }

    public PrivateKey privateKeyFromString(String key){
        key = key.replaceAll("\\n", "").replace("-----BEGIN RSA PRIVATE KEY-----", "").replace("-----END RSA PRIVATE KEY-----", "");
        System.out.println(key);
        try {
            byte[] privKeyBytes=Base64.decodeBase64(key);
            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            KeySpec privSpec=new PKCS8EncodedKeySpec(privKeyBytes);
            return keyFactory.generatePrivate(privSpec);
        }
        catch (  Exception e) {
            throw new IllegalStateException("Error loading private key: " + e);
        }
    }

    public PublicKey publicKeyFromString(String key){
        key = key.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
        System.out.println(key);
        try {
            byte[] publicKeyBytes=Base64.decodeBase64(key);
            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            KeySpec publicSpec=new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(publicSpec);
        }
        catch (  Exception e) {
            throw new IllegalStateException("Error loading public key: " + e);
        }
    }

}
