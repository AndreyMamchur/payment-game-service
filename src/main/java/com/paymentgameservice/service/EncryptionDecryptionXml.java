package com.paymentgameservice.service;

import lombok.NonNull;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.*;

import java.util.Enumeration;
import java.util.List;

@Component
public class EncryptionDecryptionXml {
    public static final String privateFile = "src\\main\\resources\\private.pem";
    public static final String publicFile = "src\\main\\resources\\public.pem";

    //подписывает сообщение
    public String sign(@NonNull String message) throws SignatureException {
        try {
            Signature sign = Signature.getInstance("SHA1withRSA");
            String key = readFileToString(privateFile);
            PrivateKey privateKey = getPrivateKeyFromString(key);
            sign.initSign(privateKey);
            sign.update(message.getBytes("UTF-8"));
            return new String(Base64.encodeBase64(sign.sign()),"UTF-8");
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }
    }

    //проверяет ответ
    public boolean verify(@NonNull String message, @NonNull String signature) throws SignatureException{
        try {
            Signature sign = Signature.getInstance("SHA1withRSA");
            String key = readFileToString(publicFile);
            PublicKey publicKey = getPublicKeyFromString(key);
            sign.initVerify(publicKey);
            sign.update(message.getBytes("UTF-8"));
            return sign.verify(Base64.decodeBase64(signature.getBytes("UTF-8")));
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }
    }


    //читает ключ в String
    public String readFileToString(@NonNull String nameOfFile){
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

    //получает PublicKey из строки
    public PublicKey getPublicKeyFromString(@NonNull String publicKeyString){
        publicKeyString = publicKeyString.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
        System.out.println(publicKeyString);
        try {
            byte[] publicKeyBytes=Base64.decodeBase64(publicKeyString);
            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            KeySpec publicSpec=new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(publicSpec);
        }
        catch (  Exception e) {
            throw new IllegalStateException("Error loading public key: " + e);
        }
    }

    //получает PrivateKey из строки
    public PrivateKey getPrivateKeyFromString(@NonNull String privateKeyString){
        privateKeyString = privateKeyString
                .replaceAll("\\n", "")
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "");

        byte[] encodedPrivateKey = Base64.decodeBase64(privateKeyString);

        try {
            ASN1Sequence primitive = (ASN1Sequence) ASN1Sequence
                    .fromByteArray(encodedPrivateKey);
            Enumeration<?> e = primitive.getObjects();
            BigInteger v = ((ASN1Integer) e.nextElement()).getValue();

            int version = v.intValue();
            if (version != 0 && version != 1) {
                throw new IllegalArgumentException("wrong version for RSA private key");
            }

            BigInteger modulus = ((ASN1Integer) e.nextElement()).getValue();
            BigInteger privateExponent = ((ASN1Integer) e.nextElement()).getValue();

            RSAPrivateKeySpec spec = new RSAPrivateKeySpec(modulus, privateExponent);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey pk = kf.generatePrivate(spec);
            return pk;
        } catch (IOException e2) {
            throw new IllegalStateException();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }
}
