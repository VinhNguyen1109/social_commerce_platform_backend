package com.V17Tech.social_commerce_platform_v2.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateDeserializer extends JsonDeserializer<Date> {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    static {
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateString = p.getText().trim();
        if (dateString.length() > 23) {
            dateString = dateString.substring(0, 23) + "Z";
        }
        try {
            return format.parse(dateString);
        } catch (Exception e) {
            throw new IOException("Invalid date format: " + dateString, e);
        }
    }
}
