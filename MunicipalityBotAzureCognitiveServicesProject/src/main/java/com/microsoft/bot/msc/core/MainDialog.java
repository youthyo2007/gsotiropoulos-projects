// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.msc.core;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.dialogs.ComponentDialog;
import com.microsoft.bot.dialogs.DialogTurnResult;
import com.microsoft.bot.dialogs.WaterfallDialog;
import com.microsoft.bot.dialogs.WaterfallStep;
import com.microsoft.bot.dialogs.WaterfallStepContext;
import com.microsoft.bot.dialogs.prompts.PromptOptions;
import com.microsoft.bot.dialogs.prompts.TextPrompt;
import com.microsoft.bot.msc.data.model.UserProfileObject;
import com.microsoft.bot.msc.data.services.*;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * The class containing the main dialog for the msc.
 */
public class MainDialog extends ComponentDialog {

    private final LuisNLPRecognizerService luisRecognizer;
    private DatabaseService databaseService;
    private TranslatorService translatorService;

    private HuggingFaceService huggingFaceService;

    private NLGResponseService nlgResponseService;

    private final Integer plusDayValue = 7;


    public  String  USER_INFO = "USER_INFO";

    public MainDialog(LuisNLPRecognizerService withLuisRecognizer, DatabaseService databaseService, TranslatorService translatorService, HuggingFaceService huggingFaceService,NLGResponseService nlgResponseService) {
        super("MainDialog");

        this.luisRecognizer = withLuisRecognizer;
        this.databaseService = databaseService;
        this.translatorService = translatorService;
        this.huggingFaceService = huggingFaceService;
        this.nlgResponseService = nlgResponseService;
        nlgResponseService.setDatabaseService(databaseService);


        TopLevelDialog topLevelDialog = new TopLevelDialog(withLuisRecognizer, databaseService, translatorService, huggingFaceService,nlgResponseService);

        WaterfallStep[] waterfallSteps = {
                this::welcomeStep,
                this::topLevelDialogStep
        };

        addDialog(new TextPrompt("TextPrompt"));
        addDialog(topLevelDialog);
        addDialog(new WaterfallDialog("WaterfallDialog", Arrays.asList(waterfallSteps)));



        // The initial child Dialog to run.
        setInitialDialogId("WaterfallDialog");


    }

    /**
     * First step in the waterfall dialog. Prompts the user for a name.
     *
     * @param stepContext A {@link WaterfallStepContext}
     * @return A {@link DialogTurnResult}
     */

    private CompletableFuture<DialogTurnResult> welcomeStep(WaterfallStepContext stepContext) {
        // Create an object in which to collect the user's information within the dialog.
        stepContext.getValues().put(USER_INFO, new UserProfileObject());
        // Ask the user to enter their name.
        PromptOptions promptOptions = new PromptOptions();
        promptOptions.setPrompt(MessageFactory.text("Welcome! to our bot! What is your name?"));
        return stepContext.prompt("TextPrompt", promptOptions);

    }

    /**
     * Second step in the waterfall dialog. Starts the TopLevelDialog.
     *
     * @param stepContext A {@link WaterfallStepContext}
     * @return A {@link DialogTurnResult}
     */
    private CompletableFuture<DialogTurnResult> topLevelDialogStep(WaterfallStepContext stepContext) {

        // Set the user's name to what they entered in response to the name prompt.
        UserProfileObject userProfile = (UserProfileObject) stepContext.getValues().get(USER_INFO);
        userProfile.setName((String) stepContext.getResult());
        return stepContext.beginDialog("TopLevelDialog");

    }

}
