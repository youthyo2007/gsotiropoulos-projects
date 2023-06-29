// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.msc.data.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import com.microsoft.bot.integration.Configuration;
import com.microsoft.bot.msc.sentiment.model.SentimentResult;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 */

@Service
public class HuggingFaceService {

        private final String HOST = "https://distilbert-base-uncase-nick7k96.westeurope.inference.ml.azure.com/score";
    private final String SCORE_PATH = "/score";
    private final String URI_PARAMS = "";

    private String key = "pyMw7rhc7cPsYzbwncEWYx1G7ge94TX5";


    /**
     * @param configuration The configuration class with the hugging face key stored.
     */
    public HuggingFaceService(Configuration configuration) {
        String endPointKey = configuration.getProperty("HuggingFaceKey");

        if (endPointKey == null) {
            throw new IllegalArgumentException("key");
        }

        key = endPointKey;
    }


    public CompletableFuture<SentimentResult> sentimentAnalysis(String text) {
        return CompletableFuture.supplyAsync(() -> {
            String body = String.format("{ \"inputs\": \"%s\" }", text);


            String uri = new StringBuilder(HOST).toString();

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(uri)
                    .header("Authorization","Bearer " + key)
                    .post(requestBody)
                    .build();

          try {

                Response response = client.newCall(request).execute();

                if (!response.isSuccessful()) {
                    String message = new StringBuilder("The call to the sentiment service returned HTTP status code ")
                            .append(response.code())
                            .append(".").toString();
                    throw new Exception(message);
                }

              ObjectMapper objectMapper = new ObjectMapper();
              Reader reader = new StringReader(response.body().string());
              SentimentResult[] result = objectMapper.readValue(reader, SentimentResult[].class);
              return result[0];

            } catch (Exception e) {
                LoggerFactory.getLogger(HuggingFaceService.class).error("findPackages", e);
                e.printStackTrace();
                throw new CompletionException(e);
            }
        });
    }

}




