// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.msc.translation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.microsoft.bot.builder.Middleware;
import com.microsoft.bot.builder.NextDelegate;
import com.microsoft.bot.builder.StatePropertyAccessor;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.builder.UserState;
import com.microsoft.bot.msc.data.services.TranslatorService;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ActivityTypes;

/**
 * Middleware for translating text between the user and bot.
 * Uses the Microsoft Translator Text API.
 */
public class TranslationMiddleware implements Middleware  {
    private final TranslatorService translator;
    private final UserState userState;
    
    private final StatePropertyAccessor<String> languageStateProperty;

    /**
     * Initializes a new instance of the {@link TranslationMiddleware} class.
     * @param withTranslator Translator implementation to be used for text translation.
     * @param withUserState State property for current language.
     */
    public TranslationMiddleware(TranslatorService withTranslator, UserState withUserState) {
        if (withTranslator == null) {
            throw new IllegalArgumentException("withTranslator");
        }
        this.translator = withTranslator;
        if (withUserState == null) {
            throw new IllegalArgumentException("userState");
        }

        this.userState = withUserState;
        this.languageStateProperty = userState.createProperty("LanguagePreference");
    }

    /**
     * Processes an incoming activity.
     * @param turnContext Context object containing information for a single turn of conversation with a user.
     * @param next The delegate to call to continue the bot middleware pipeline.
     * @return A Task representing the asynchronous operation.
     */
    public CompletableFuture<Void> onTurn(TurnContext turnContext, NextDelegate next) {
        if (turnContext == null) {
            throw new IllegalArgumentException("turnContext");
        }
   
        
   
        return this.shouldTranslate(turnContext).thenCompose(translate -> {
            if (translate) {
                if (turnContext.getActivity().isType(ActivityTypes.MESSAGE)) {
                    return this.translator.translate(
                        turnContext.getActivity().getText(),null,
                        TranslationSettings.DEFAULT_LANGUAGE)
                    .thenApply(text -> {
                        turnContext.getActivity().setText(text);
                        return CompletableFuture.completedFuture(null);
                    });
                }
            }
            return CompletableFuture.completedFuture(null);
        }).thenCompose(task -> {
            turnContext.onSendActivities((newContext, activities, nextSend) -> {
                return this.languageStateProperty.get(turnContext, () -> TranslationSettings.DEFAULT_LANGUAGE).thenCompose(userLanguage -> {
                    Boolean shouldTranslate = !userLanguage.equals(TranslationSettings.DEFAULT_LANGUAGE);
                    //Boolean shouldTranslate = true;

                    // Translate messages sent to the user to user language
                    if (shouldTranslate) {
                        ArrayList<CompletableFuture<Void>> tasks = new ArrayList<CompletableFuture<Void>>();
                        for (Activity activity : activities.stream().filter(a -> a.getType().equals(ActivityTypes.MESSAGE)).collect(Collectors.toList())) {
                            tasks.add(this.translateMessageActivity(activity, TranslationSettings.DEFAULT_LANGUAGE,userLanguage));
                        }

                        if (!Arrays.asList(tasks).isEmpty()) {
                            CompletableFuture.allOf(tasks.toArray(new CompletableFuture[tasks.size()])).join();
                        }
                    }

                    return nextSend.get();
                });
            });

            turnContext.onUpdateActivity((newContext, activity, nextUpdate) -> {
                return this.languageStateProperty.get(turnContext, () -> TranslationSettings.DEFAULT_LANGUAGE).thenCompose(userLanguage -> {
                    Boolean shouldTranslate = !userLanguage.equals(TranslationSettings.DEFAULT_LANGUAGE);
                    //Boolean shouldTranslate = true;
                    // Translate messages sent to the user to user language
                    if (activity.getType().equals(ActivityTypes.MESSAGE)) {
                        if (shouldTranslate) {
                            this.translateMessageActivity(activity, TranslationSettings.DEFAULT_LANGUAGE,userLanguage);
                        }
                    }

                    return nextUpdate.get();
                });
            });

            return next.next();
        });
    }

    private CompletableFuture<Void> translateMessageActivity(Activity activity, String fromLocale, String targetLocale) {
        if (activity.getType().equals(ActivityTypes.MESSAGE)) {
            return this.translator.translate(activity.getText(), fromLocale, targetLocale).thenAccept(text -> {
                activity.setText(text);
            });
        }
        return CompletableFuture.completedFuture(null);
    }


    private CompletableFuture<Boolean> shouldTranslate(TurnContext turnContext) {

          
            //System.out.println("detecting language of:"+turnContext.getActivity().getText());
            return this.translator.detect(turnContext.getActivity().getText())
                .thenApply(userLanguage -> {
                if (Strings.isNullOrEmpty(userLanguage)) {
                        userLanguage = TranslationSettings.DEFAULT_LANGUAGE;
                }
                         
                languageStateProperty.set(turnContext, userLanguage);
                return !userLanguage.equals(TranslationSettings.DEFAULT_LANGUAGE);
            });


        /*return this.languageStateProperty.get(turnContext, () -> TranslationSettings.DEFAULT_LANGUAGE).thenApply(userLanguage -> {
            System.out.println("hello-userLanguage"+userLanguage);

            if (Strings.isNullOrEmpty(userLanguage)) {
                userLanguage = TranslationSettings.DEFAULT_LANGUAGE;
            }
           

            return !userLanguage.equals(TranslationSettings.DEFAULT_LANGUAGE);
        });*/

/* 
    private CompletableFuture<Boolean> shouldTranslateOld(TurnContext turnContext) {
        return this.languageStateProperty.get(turnContext, () -> TranslationSettings.DEFAULT_LANGUAGE).thenApply(userLanguage -> {
            System.out.println("helloß"+userLanguage);

            if (Strings.isNullOrEmpty(userLanguage)) {
                userLanguage = TranslationSettings.DEFAULT_LANGUAGE;
            }
           

            return !userLanguage.equals(TranslationSettings.DEFAULT_LANGUAGE);
        });
    }
    */


    }



}
