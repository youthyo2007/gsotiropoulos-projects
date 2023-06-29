// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.msc.core;

import com.microsoft.bot.builder.ActivityHandler;
import com.microsoft.bot.builder.BotState;
import com.microsoft.bot.builder.ConversationState;
import com.microsoft.bot.builder.StatePropertyAccessor;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.builder.UserState;
import com.microsoft.bot.dialogs.Dialog;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;

import java.util.concurrent.CompletableFuture;

/**
 * This Bot implementation can run any type of Dialog. The use of type parameterization is to allow
 * multiple different bots to be run at different endpoints within the same project. This can be
 * achieved by defining distinct Controller types each with dependency on distinct Bot types. The
 * ConversationState is used by the Dialog system. The UserState isn't, however, it might have been
 * used in a Dialog implementation, and the requirement is that all BotState objects are saved at
 * the end of a turn.
 *
 * @param <T> parameter of a type inheriting from Dialog
 */
public class DialogBot<T extends Dialog> extends ActivityHandler {

    private Dialog dialog;
    private BotState conversationState;
    private BotState userState;


    private static final String ENGLISH_ENGLISH = "en";
    private static final String ENGLISH_SPANISH = "es";
    private static final String ENGLISH_GREEK = "el";
    private  final StatePropertyAccessor<String> languagePreference;



    /**
     * Gets the dialog in use.
     *
     * @return instance of dialog
     */
    protected Dialog getDialog() {
        return dialog;
    }

    /**
     * Gets the conversation state.
     *
     * @return instance of conversationState
     */
    protected BotState getConversationState() {
        return conversationState;
    }

    /**
     * Gets the user state.
     *
     * @return instance of userState
     */
    protected BotState getUserState() {
        return userState;
    }

    /**
     * Sets the dialog in use.
     *
     * @param withDialog the dialog (of Dialog type) to be set
     */
    protected void setDialog(Dialog withDialog) {
        dialog = withDialog;
    }

    /**
     * Sets the conversation state.
     *
     * @param withConversationState the conversationState (of BotState type) to be set
     */
    protected void setConversationState(BotState withConversationState) {
        conversationState = withConversationState;
    }

    /**
     * Sets the user state.
     *
     * @param withUserState the userState (of BotState type) to be set
     */
    protected void setUserState(BotState withUserState) {
        userState = withUserState;
    }

    /**
     * Creates a DialogBot.
     *
     * @param withConversationState ConversationState to use in the bot
     * @param withUserState         UserState to use
     * @param withDialog            Param inheriting from Dialog class
     */
    public DialogBot(ConversationState withConversationState, UserState withUserState, T withDialog) {
        this.conversationState = withConversationState;
        this.userState = withUserState;
        this.dialog = withDialog;
        this.languagePreference = userState.createProperty("LanguagePreference");
    }

    /**
     * Saves the BotState objects at the end of each turn.
     *
     * @param turnContext
     * @return
     */
    @Override
    public CompletableFuture<Void> onTurn(TurnContext turnContext) {
        return super.onTurn(turnContext)
            // Save any state changes that might have occurred during the turn.
            .thenCompose(turnResult -> conversationState.saveChanges(turnContext, false))
            .thenCompose(saveResult -> userState.saveChanges(turnContext, false));
    }

    /**
     * This method is executed when the turnContext receives a message activity.
     *
     * @param turnContext
     * @return
     */
    /* 
    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {
        LoggerFactory.getLogger(DialogBot.class).info("Running dialog with Message Activity.");

        // Run the Dialog with the new message Activity.
        return Dialog.run(dialog, turnContext, conversationState.createProperty("DialogState"));
    }
    */


    /**
     * This method is executed when the turnContext receives a message activity.
     * @param turnContext The context object for this turn.
     * @return A task that represents the work queued to execute.
     */
    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {
        if (isLanguageChangeRequested(turnContext.getActivity().getText())) {
            String currentLang = turnContext.getActivity().getText().toLowerCase();
            //String lang = currentLang.equals(ENGLISH_ENGLISH) || currentLang.equals(ENGLISH_SPANISH)        ? ENGLISH_ENGLISH : ENGLISH_SPANISH;

            // If the user requested a language change through the suggested actions with values "es" or "en",
            // simply change the user's language preference in the user state.
            // The translation middleware will catch this setting and translate both ways to the user's
            // selected language.
            // If Spanish was selected by the user, the reply below will actually be shown in spanish to the user.
            languagePreference.set(turnContext, currentLang);
            userState.saveChanges(turnContext, false);
        }
   
            LoggerFactory.getLogger(DialogBot.class).info("Running dialog with Message Activity.");
            // Run the Dialog with the new message Activity.
            return Dialog.run(dialog, turnContext, conversationState.createProperty("DialogState"));
    
    }



 /**
     * Checks whether the utterance from the user is requesting a language change.
     * In a production bot, we would use the Microsoft Text Translation API language
     * detection feature, along with detecting language names.
     * For the purpose of the msc, we just assume that the user requests language
     * changes by responding with the language code through the suggested action presented
     * above or by typing it.
     * @param utterance utterance the current turn utterance.
     * @return the utterance.
     */
    private static Boolean isLanguageChangeRequested(String utterance) {
        if (Strings.isNullOrEmpty(utterance)) {
            return false;
        }

        utterance = utterance.toLowerCase().trim();
        return utterance.equals(ENGLISH_SPANISH) || utterance.equals(ENGLISH_ENGLISH)
            || utterance.equals(ENGLISH_GREEK);
    }


}
