// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.msc.data.services;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.RecognizerResult;
import com.microsoft.bot.dialogs.WaterfallStepContext;
import com.microsoft.bot.msc.data.model.CorinthPOIDepartmentObject;
import com.microsoft.bot.msc.data.model.CorinthPOITouristObject;
import com.microsoft.bot.msc.data.model.NERExtractionObject;
import com.microsoft.bot.msc.sentiment.model.SentimentResult;
import com.microsoft.bot.schema.InputHints;
import org.springframework.stereotype.Service;

/**
 * A helper class wrapper for creating Responses .
 */

@Service
public class NLGResponseService {


    private DatabaseService databaseService;


    public DatabaseService getDatabaseService() {
        return databaseService;
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public NLGResponseService(DatabaseService databaseService) {
        this.setDatabaseService(databaseService);
    }



    public String generateFindPhoneNumberResponse(NERExtractionObject nerObject,WaterfallStepContext stepContext) {

        CorinthPOIDepartmentObject object = null;

        try {
            object = getDatabaseService().getDepartmentItemByNameTypeLocation(nerObject.getName(), nerObject.getType(), nerObject.getLocation());
            if(object!=null)
                stepContext.getValues().put("POI_INFO", object);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String responseMessageText = "Sorry, I didn't get that. Please try asking in a different way";

        //IF TELEPHONE NUMBER OF THE DEPARTMENT IS FOUND THEN
        if (object!=null) {
            responseMessageText = "The telephone number of " + object.getName() + " is " + object.getContact().getPhone1() + "," + object.getContact().getPhone2();
        }

        return responseMessageText;

    }


    public String generateFindAddressResponse(NERExtractionObject nerObject, WaterfallStepContext stepContext) {

        CorinthPOIDepartmentObject object = null;

        try {
            object = getDatabaseService().getDepartmentItemByNameTypeLocation(nerObject.getName(), nerObject.getType(), nerObject.getLocation());
            if(object!=null)
                stepContext.getValues().put("POI_INFO", object);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String responseMessageText = "Sorry, I didn't get that. Please try asking in a different way";

        //IF TELEPHONE NUMBER OF THE DEPARTMENT IS FOUND THEN
        if (object!=null) {
            responseMessageText = "The address  of " + object.getName() + " is " + object.getAddress().getStreetAddress() + "," + object.getAddress().getCity();
        }

        return responseMessageText;

    }

    public String generateCancelResponse(RecognizerResult luisResult) {

        String cancelMessageText = String.format(
                "I hope you found what you where looking for. Going back to menu (intent was %s)",
                luisResult.getTopScoringIntent().intent
        );

        return cancelMessageText;

    }

    public String generateFindTouristPOIResponse(NERExtractionObject nerObject, WaterfallStepContext stepContext) {
        CorinthPOITouristObject object = null;

        try {

            //remove last 's'
            String type = nerObject.getType();
            if(type!=null && type.endsWith("s"))
                type = type.substring(0,type.length()-1);


            object = databaseService.getTouristItemByNameTypeLocation(nerObject.getName(), type, nerObject.getCuisine(), nerObject.getAmentities(), nerObject.getLocation());
        } catch (Exception e) {
            e.printStackTrace();
        }


        //CREATE MESSAGE
        String responseMessageText = "Sorry, I didn't get that. Please try asking in a different way";



        if ((object!=null) && (nerObject.getRating()!=null) && (nerObject.getCuisine()!=null))
            responseMessageText = "A '"+nerObject.getRating() + "' " +nerObject.getCuisine() +  (nerObject.getType()!=null ? nerObject.getType() : "place")   + " I found is '" +object.getName()+"'!";
        else  if ((object!=null) && (nerObject.getRating()!=null))
            responseMessageText = "A '"+nerObject.getRating()  + "' "+ (nerObject.getType()!=null ? nerObject.getType() : "place")  + " I found is '" +object.getName()+"'!";
        else if ((object!=null) && (nerObject.getCuisine()!=null))
            responseMessageText = "There are some '"+nerObject.getCuisine() + "' " + (nerObject.getType()!=null ? nerObject.getType() : "place")  + "s around! One of them is '" +object.getName()+"'";
        else if (object!=null)
            responseMessageText = "I found '" +object.getName()+"'. It is a "+ (nerObject.getType()!=null ? nerObject.getType() : "place")  +" that maybe you are interested";


        if (object!=null)
            responseMessageText += "\nThe address is '" +object.getAddress().getStreetAddress()+","+ object.getAddress().getCity()+"'. tel:'"+object.getContact().getPhone1()+"'";


        return responseMessageText;
    }

    public String generateRatingServicesResponse(SentimentResult sentimentAnalysisResult) {

        String responseMessageText = "";

        switch (sentimentAnalysisResult.getLabel()) {


            case "POSITIVE":
                 responseMessageText = "This is some great news. Thanks for your kind words!";

            case "NEGATIVE":
                responseMessageText = "Thanks for the feedback. We take your thoughts under consideration for better improvement";


            default:
                // Catch all for errors
                responseMessageText = "Sorry, I didn't get that. Please try tell it in a different way ";
        }

        return  responseMessageText;

    }

    public String generateDefaultResponse(RecognizerResult luisResult) {

        String defaultMessageText = String.format(
                "Sorry, I didn't understand that. Please try tell it in a different way. The top scoring intent was %s",
                luisResult.getTopScoringIntent().intent
        );

        return defaultMessageText;

    }
}




