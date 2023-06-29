// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.msc.core;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.dialogs.*;
import com.microsoft.bot.dialogs.prompts.PromptOptions;
import com.microsoft.bot.dialogs.prompts.TextPrompt;
import com.microsoft.bot.msc.data.services.*;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.InputHints;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * The class containing the main dialog for the msc.
 */
public class RatingServicesDialog extends ComponentDialog {

    private final LuisNLPRecognizerService luisRecognizer;
    private DatabaseService databaseService;
    private TranslatorService translatorService;

    private HuggingFaceService huggingFaceService;

    private NLGResponseService nlgResponseService;


    public RatingServicesDialog(LuisNLPRecognizerService withLuisRecognizer, DatabaseService databaseService, TranslatorService translatorService, HuggingFaceService huggingFaceService,NLGResponseService nlgResponseService) {
        super("RatingServicesDialog");

        this.luisRecognizer = withLuisRecognizer;
        this.databaseService = databaseService;
        this.translatorService = translatorService;
        this.huggingFaceService = huggingFaceService;
        this.nlgResponseService = nlgResponseService;


        addDialog(new TextPrompt("TextPrompt"));
        WaterfallStep[] waterfallSteps = {
            this::introStep,
            this::sentimentAnalysisProcessingStep,
            this::finalStep
        };
        addDialog(new WaterfallDialog("RatingServicesWaterfallDialog", Arrays.asList(waterfallSteps)));

        // The initial child Dialog to run.
        setInitialDialogId("RatingServicesWaterfallDialog");
    }

    /**
     * First step in the waterfall dialog. Prompts the user for a command. Currently, this expects a
     * request, like "find the telephone number of revenue department" 
     *
     * @param stepContext A {@link WaterfallStepContext}
     * @return A {@link DialogTurnResult}
     */
    private CompletableFuture<DialogTurnResult> introStep(WaterfallStepContext stepContext) {

        String messageText = stepContext.getOptions() != null
            ? stepContext.getOptions().toString()
            : String.format("What do you think about the services offered?");
        Activity promptMessage = MessageFactory
            .text(messageText, messageText, InputHints.EXPECTING_INPUT);
        PromptOptions promptOptions = new PromptOptions();
        promptOptions.setPrompt(promptMessage);
        return stepContext.prompt("TextPrompt", promptOptions);
    }


    /**
     * Second step in the waterfall.  This will use HuggingFaceService to attempt to do sentiment analysis,

     * @param stepContext A {@link WaterfallStepContext}
     * @return A {@link DialogTurnResult}
     */
    private CompletableFuture<DialogTurnResult> sentimentAnalysisProcessingStep(WaterfallStepContext stepContext) {


        String input = stepContext.getResult().toString();




        return huggingFaceService.sentimentAnalysis(input).thenCompose(sentimentAnalysisResult -> {

            Activity activityMsg  =null;
            String responseMessageText = nlgResponseService.generateRatingServicesResponse(sentimentAnalysisResult);

            activityMsg = MessageFactory
                    .text(
                            responseMessageText, responseMessageText,
                            InputHints.IGNORING_INPUT
                    );

            return stepContext.getContext().sendActivity(activityMsg).thenCompose(resourceResponse -> stepContext.next(null));


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
        return stepResult.thenCompose(result -> stepContext.replaceDialog("TopLevelDialog"));
    }
}
