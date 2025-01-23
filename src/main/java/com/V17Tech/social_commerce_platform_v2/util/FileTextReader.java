package com.V17Tech.social_commerce_platform_v2.util;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileTextReader {
    private final ResourceLoader resourceLoader;
    public String readFileFromResources(String filename) throws IOException {
        Resource resource = resourceLoader.getResource("file:" + filename);
        InputStream inputStream = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
        reader.close();
        return stringBuilder.toString();
    }
}
