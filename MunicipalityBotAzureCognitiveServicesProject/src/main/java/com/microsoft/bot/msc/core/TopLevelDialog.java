// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.msc.core;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.dialogs.*;
import com.microsoft.bot.dialogs.choices.Choice;
import com.microsoft.bot.dialogs.choices.FoundChoice;
import com.microsoft.bot.dialogs.choices.ListStyle;
import com.microsoft.bot.dialogs.prompts.ChoicePrompt;
import com.microsoft.bot.dialogs.prompts.PromptOptions;
import com.microsoft.bot.msc.data.model.UserProfileObject;
import com.microsoft.bot.msc.data.services.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The class containing the top level dialog for the msc.
 */
public class TopLevelDialog extends ComponentDialog {

    private final LuisNLPRecognizerService luisRecognizer;
    private DatabaseService databaseService;
    private TranslatorService translatorService;

    private HuggingFaceService huggingFaceService;

    private NLGResponseService nlgResponseService;

    public  String  USER_INFO = "USER_INFO";

    public TopLevelDialog(LuisNLPRecognizerService withLuisRecognizer, DatabaseService databaseService, TranslatorService translatorService, HuggingFaceService huggingFaceService, NLGResponseService nlgResponseService) {
        super("TopLevelDialog");

        this.luisRecognizer = withLuisRecognizer;
        this.databaseService = databaseService;
        this.translatorService = translatorService;
        this.huggingFaceService = huggingFaceService;
        this.nlgResponseService = nlgResponseService;

        GovDirectoryDialog govDirectoryDialog = new GovDirectoryDialog(withLuisRecognizer, databaseService,  translatorService,huggingFaceService,nlgResponseService);
        TouristGuideDialog touristGuideDialog = new TouristGuideDialog(withLuisRecognizer, databaseService,  translatorService,huggingFaceService,nlgResponseService);
        RatingServicesDialog ratingServicesDialog = new RatingServicesDialog(withLuisRecognizer, databaseService,  translatorService,huggingFaceService,nlgResponseService);



        WaterfallStep[] waterfallSteps = {
            this::startMenuStep,
            this::processSelectionStep
        };

        addDialog(new ChoicePrompt("ChoicePrompt"));
        addDialog(govDirectoryDialog);
        addDialog(touristGuideDialog);
        addDialog(ratingServicesDialog);
        addDialog(new WaterfallDialog("TopLevelWaterfallDialog", Arrays.asList(waterfallSteps)));

        // The initial child Dialog to run.
        setInitialDialogId("TopLevelWaterfallDialog");
    }

    /**
     * First step in the waterfall dialog. Prompts the user for a command. Currently, this expects a
     * request, like "find the telephone number of revenue department" 
     *
     * @param stepContext A {@link WaterfallStepContext}
     * @return A {@link DialogTurnResult}
     */


    private CompletableFuture<DialogTurnResult> startMenuStep(WaterfallStepContext stepContext) {

        // Get user name
        UserProfileObject userProfile = (UserProfileObject) stepContext.getValues().get(USER_INFO);

        if (userProfile ==null)
            userProfile = new UserProfileObject("George");

        // Create the PromptOptions which contain the prompt and re-prompt messages.
        // PromptOptions also contains the list of choices available to the user.
        PromptOptions promptOptions = new PromptOptions();
        promptOptions.setPrompt(MessageFactory.text("What can I help you with today? "));
        promptOptions.setRetryPrompt(MessageFactory.text("That was not a valid choice "+userProfile.getName()));
        promptOptions.setChoices(getMenuChoices());
        promptOptions.setStyle(ListStyle.HEROCARD);

        // Prompt the user with the configured PromptOptions.
        return stepContext.prompt("ChoicePrompt", promptOptions);




    }

    private List<Choice> getMenuChoices() {
        return Arrays.asList(
                new Choice("Municipality Services Directory", "municipality_guide"),
                new Choice("Tourist Guide", "tourist_guide"),
                new Choice("Tell us your opinion", "rating_services")

        );
    }



    private CompletableFuture<DialogTurnResult> processSelectionStep(WaterfallStepContext stepContext) {
        String choice = ((FoundChoice) stepContext.getResult()).getValue();

        switch (((FoundChoice) stepContext.getResult()).getValue()) {
            case "Municipality Services Directory":

                return stepContext.beginDialog("GovDirectoryDialog");
            case "Tourist Guide":

                return stepContext.beginDialog("TouristGuideDialog");
            case "Tell us your opinion":

                return stepContext.beginDialog("RatingServicesDialog");
            default:
                throw new IllegalStateException("Unexpected value: " + ((FoundChoice) stepContext.getResult()).getValue());
        }
    }




}
