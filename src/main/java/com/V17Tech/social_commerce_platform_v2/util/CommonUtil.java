package com.V17Tech.social_commerce_platform_v2.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;

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
}
