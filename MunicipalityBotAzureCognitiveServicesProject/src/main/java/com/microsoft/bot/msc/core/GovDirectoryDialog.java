// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.msc.core;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.dialogs.*;
import com.microsoft.bot.dialogs.prompts.PromptOptions;
import com.microsoft.bot.dialogs.prompts.TextPrompt;
import com.microsoft.bot.msc.data.model.CorinthPOIDepartmentObject;
import com.microsoft.bot.msc.data.model.NERExtractionObject;
import com.microsoft.bot.msc.data.services.*;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.InputHints;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * The class containing the main dialog for the msc.
 */
public class GovDirectoryDialog extends ComponentDialog {

    private final LuisNLPRecognizerService luisRecognizer;
    private DatabaseService databaseService;
    private TranslatorService translatorService;

    private HuggingFaceService huggingFaceService;

    private NLGResponseService nlgResponseService;

    public  String  POI_INFO = "POI_INFO";




    public GovDirectoryDialog(LuisNLPRecognizerService withLuisRecognizer, DatabaseService databaseService, TranslatorService translatorService, HuggingFaceService huggingFaceService, NLGResponseService nlgResponseService) {
        super("GovDirectoryDialog");

        this.luisRecognizer = withLuisRecognizer;
        this.databaseService = databaseService;
        this.translatorService = translatorService;
        this.huggingFaceService = huggingFaceService;
        this.nlgResponseService = nlgResponseService;


        addDialog(new TextPrompt("TextPrompt"));
        WaterfallStep[] waterfallSteps = {
            this::introStep,
            this::nLPMainProcessingStep,
            this::finalStep
        };
        addDialog(new WaterfallDialog("GovDirectoryWaterfallDialog", Arrays.asList(waterfallSteps)));

        // The initial child Dialog to run.
        setInitialDialogId("GovDirectoryWaterfallDialog");
    }

    /**
     * First step in the waterfall dialog. Prompts the user for a command. Currently, this expects a
     * request, like "find the telephone number of revenue department" 
     *
     * @param stepContext A {@link WaterfallStepContext}
     * @return A {@link DialogTurnResult}
     */
    private CompletableFuture<DialogTurnResult> introStep(WaterfallStepContext stepContext) {
        if (!luisRecognizer.isConfigured()) {
            Activity text = MessageFactory.text("NOTE: LUIS is not configured. "
                + "To enable all capabilities, add 'LuisAppId', 'LuisAPIKey' and 'LuisAPIHostName' "
                + "to the appsettings.json file.", null, InputHints.IGNORING_INPUT);
            return stepContext.getContext().sendActivity(text)
                .thenCompose(sendResult -> stepContext.next(null));
        }




        String messageText = stepContext.getOptions() != null
            ? stepContext.getOptions().toString()
            : String.format("Say something like 'find the telephone number of revenue department'");
        Activity promptMessage = MessageFactory
            .text(messageText, messageText, InputHints.EXPECTING_INPUT);
        PromptOptions promptOptions = new PromptOptions();
        promptOptions.setPrompt(promptMessage);
        return stepContext.prompt("TextPrompt", promptOptions);
    }



        /**
         * Second step in the waterfall.  This will use LUIS to attempt to extract place name and place type,

         * @param stepContext A {@link WaterfallStepContext}
         * @return A {@link DialogTurnResult}
         */
    private CompletableFuture<DialogTurnResult> nLPMainProcessingStep(WaterfallStepContext stepContext) {


        // Call LUIS and gather any potential booking details. (Note the TurnContext has the response to the prompt.)
        return luisRecognizer.recognize(stepContext.getContext()).thenCompose(luisResult -> {

            NERExtractionObject nerObject = null;
            CorinthPOIDepartmentObject object = null;
            Activity activityMsg  =null;


            switch (luisResult.getTopScoringIntent().intent) {

                case "CorinthMunicipality_FindContactNumber":

                    // Extract the values for the composite entities from the LUIS result.
                     nerObject = luisRecognizer.extractEntities(luisResult);
                     String responseMessageText = nlgResponseService.generateFindPhoneNumberResponse(nerObject,stepContext);


                            activityMsg = MessageFactory
                            .text(
                                    responseMessageText, responseMessageText,
                                InputHints.IGNORING_INPUT
                            );
                        return stepContext.getContext().sendActivity(activityMsg).thenCompose(resourceResponse -> stepContext.next(null));

                case "CorinthMunicipality_FindAddress":
                         // Extract the values for the composite entities from the LUIS result.
                         nerObject = luisRecognizer.extractEntities(luisResult);
                         responseMessageText = nlgResponseService.generateFindAddressResponse(nerObject,stepContext);


                         activityMsg = MessageFactory
                            .text(
                                    responseMessageText, responseMessageText,
                                    InputHints.IGNORING_INPUT
                            );
                    return stepContext.getContext().sendActivity(activityMsg).thenCompose(resourceResponse -> stepContext.next(null));


                case "Cancel":
                    String cancelMessageText = nlgResponseService.generateCancelResponse(luisResult);

                    Activity cancelMessage = MessageFactory
                            .text(
                                    cancelMessageText, cancelMessageText,
                                    InputHints.IGNORING_INPUT
                            );
                    return stepContext.getContext().sendActivity(cancelMessage)
                            .thenCompose(resourceResponse -> stepContext.replaceDialog("TopLevelDialog"));


                default:
                    String defaultMessageText = nlgResponseService.generateDefaultResponse(luisResult);
                    // Catch all for unhandled intents

                    Activity defaultMessage = MessageFactory
                        .text(
                                defaultMessageText, defaultMessageText,
                            InputHints.IGNORING_INPUT
                        );
                    return stepContext.getContext().sendActivity(defaultMessage)
                        .thenCompose(resourceResponse -> stepContext.next(null));
            }
        });
    }



    /**
     * This is the final step in the main waterfall dialog. It wraps up the msc "book a flight"
     * interaction with a simple confirmation.
     *
     * @param stepContext A {@link WaterfallStepContext}
     * @return A {@link DialogTurnResult}
     */
    private CompletableFuture<DialogTurnResult> finalStep(WaterfallStepContext stepContext) {
        CompletableFuture<Void> stepResult = CompletableFuture.completedFuture(null);

        // Restart the main dialog with a different message the second time around
        String promptMessage = "What else can I do for you?";
        return stepResult.thenCompose(result -> stepContext.replaceDialog(getInitialDialogId(),promptMessage));
    }
}
