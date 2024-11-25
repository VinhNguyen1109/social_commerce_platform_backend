package com.V17Tech.social_commerce_platform_v2.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Component
public class CommonUtil {
    public static  String getUserNameFromToken(String jwtToken) {
        try {
            if (jwtToken == null) {
                return null;
            } else {
                String tokenBody = jwtDecode(jwtToken);
                if (tokenBody == null) {
                    return null;
                } else {
                    JSONParser jp = new JSONParser(tokenBody);
                    return (String)jp.parseObject().get("preferred_username");
                }
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }


    public static   String jwtDecode(String jwtToken) {
        if (jwtToken == null) {
            return null;
        } else {
            String[] split_string = jwtToken.split("\\.");
            if (split_string.length < 2) {
                return null;
            } else {
                String base64EncodedBody = split_string[1];
                Base64 base64Url = new Base64(true);
                return new String(base64Url.decode(base64EncodedBody));
            }
        }
    }

    public static String serializeToString(Object object) throws IOException {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(object);
        return compressString(jsonString);
    }

    public static Object deserializeFromString(String string, Class<?> clazz) throws IOException {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("String cannot be null or empty");
        }
        string = decompressString(string);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(string, clazz);
    }


    public static String compressString(String str) throws IOException {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("String cannot be null or empty");
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(str.getBytes());
            gzipOutputStream.close();
            return byteArrayOutputStream.toString("ISO-8859-1");
        }
    }

    // Giải nén chuỗi JSON khi cần đọc từ cơ sở dữ liệu
    public static String decompressString(String compressedStr) throws IOException {
        if (compressedStr == null || compressedStr.isEmpty()) {
            throw new IllegalArgumentException("String cannot be null or empty");
        }

        byte[] compressedBytes = compressedStr.getBytes("ISO-8859-1");
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedBytes);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
             InputStreamReader reader = new InputStreamReader(gzipInputStream, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            StringBuilder decompressed = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                decompressed.append(line);
            }
            return decompressed.toString();
        }
    }
}
