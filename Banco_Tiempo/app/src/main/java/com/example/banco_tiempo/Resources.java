package com.example.banco_tiempo;

import android.database.Cursor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Resources {
    // Section Hash Sha 256
    public static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public String hash256(String password){
        MessageDigest digest=null;
        String hash = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            hash = bytesToHexString(digest.digest());
        } catch(NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return hash;
    }


}
