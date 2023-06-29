// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.msc.data.services;

import com.microsoft.bot.ai.luis.LuisApplication;
import com.microsoft.bot.ai.luis.LuisRecognizer;
import com.microsoft.bot.ai.luis.LuisRecognizerOptionsV3;
import com.microsoft.bot.builder.Recognizer;
import com.microsoft.bot.builder.RecognizerResult;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.integration.Configuration;
import com.microsoft.bot.msc.data.model.NERExtractionObject;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.CompletableFuture;


public class LuisNLPRecognizerService implements Recognizer {

    private LuisRecognizer recognizer;

    /**
     *
     *
     * @param configuration The Configuration object to use.
     */
    public LuisNLPRecognizerService(Configuration configuration) {
        Boolean luisIsConfigured = StringUtils.isNotBlank(configuration.getProperty("LuisAppId"))
            && StringUtils.isNotBlank(configuration.getProperty("LuisAPIKey"))
            && StringUtils.isNotBlank(configuration.getProperty("LuisAPIHostName"));
        if (luisIsConfigured) {
            LuisApplication luisApplication = new LuisApplication(
                configuration.getProperty("LuisAppId"),
                configuration.getProperty("LuisAPIKey"),
                String.format("https://%s", configuration.getProperty("LuisAPIHostName"))
            );
            // Set the recognizer options depending on which endpoint version you want to use.
            // More details can be found in
            // https://docs.microsoft.com/en-gb/azure/cognitive-services/luis/luis-migration-api-v3
            LuisRecognizerOptionsV3 recognizerOptions = new LuisRecognizerOptionsV3(luisApplication);
            recognizerOptions.setIncludeInstanceData(true);

            this.recognizer = new LuisRecognizer(recognizerOptions);
        }
    }

    /**
     * Verify if the recognizer is configured.
     *
     * @return True if it's configured, False if it's not.
     */
    public Boolean isConfigured() {
        return this.recognizer != null;
    }

    /**
     * Return an object with preformatted LUIS results for the bot's dialogs to consume.
     *
     * @param context A {link TurnContext}
     * @return A {link RecognizerResult}
     */
    public CompletableFuture<RecognizerResult> executeLuisQuery(TurnContext context) {
        return this.recognizer.recognize(context);
    }


    public NERExtractionObject extractEntities(RecognizerResult result) {

        NERExtractionObject nerObject = new NERExtractionObject();


        String name = "", type = "", location = "", cuisine ="";

        if (result.getEntities().get("$instance").get("CorinthPOI_Name") != null) {
            nerObject.setName(result.getEntities().get("$instance").get("CorinthPOI_Name").get(0).get("text").asText());
        }

        if (result.getEntities().get("$instance").get("CorinthPOI_Type") != null) {
            nerObject.setType(result.getEntities().get("$instance").get("CorinthPOI_Type").get(0).get("text").asText());
        }

        if (result.getEntities().get("$instance").get("CorinthPOI_Location") != null) {
            nerObject.setLocation(result.getEntities().get("$instance").get("CorinthPOI_Location").get(0).get("text").asText());
        }

        if (result.getEntities().get("$instance").get("CorinthPOI_Cuisine") != null) {
            nerObject.setCuisine(result.getEntities().get("$instance").get("CorinthPOI_Cuisine").get(0).get("text").asText());
        }

        if (result.getEntities().get("$instance").get("CorinthPOI_Amenities") != null) {
            nerObject.setAmentities(result.getEntities().get("$instance").get("CorinthPOI_Amenities").get(0).get("text").asText());
        }

        if (result.getEntities().get("$instance").get("CorinthPOI_PriceRange") != null) {
            nerObject.setPriceRange(result.getEntities().get("$instance").get("CorinthPOI_PriceRange").get(0).get("text").asText());
        }

        if (result.getEntities().get("$instance").get("CorinthPOI_Rating") != null) {
            nerObject.setRating(result.getEntities().get("$instance").get("CorinthPOI_Rating").get(0).get("text").asText());
        }

        if (result.getEntities().get("$instance").get("CorinthPOI_Product") != null) {
            nerObject.setRating(result.getEntities().get("$instance").get("CorinthPOI_Product").get(0).get("text").asText());
        }

        return nerObject;
    }




    /**
     * Runs an utterance through a recognizer and returns a generic recognizer result.
     *
     * @param turnContext Turn context.
     * @return Analysis of utterance.
     */
    @Override
    public CompletableFuture<RecognizerResult> recognize(TurnContext turnContext) {
        return this.recognizer.recognize(turnContext);
    }
}
