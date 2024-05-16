package com.meer.model.service;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Override
    public String getResponse(String prompt, String apiKey) {
        String apiUrl = "https://api.openai.com/v1/completions";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);

            // JSON 형식의 요청 생성
            String requestBody = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 150}";
            StringEntity entity = new StringEntity(requestBody);
            httpPost.setEntity(entity);

            // API 호출 및 응답 처리
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 처리
        }

        return null;
    }
}