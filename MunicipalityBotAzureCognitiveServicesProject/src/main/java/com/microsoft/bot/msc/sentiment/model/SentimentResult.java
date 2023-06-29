package com.microsoft.bot.msc.sentiment.model;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Translation result from Translator API v3.
 */
public class SentimentResult {
    @JsonProperty("label")
    private String label;

    @JsonProperty("score")
    private String score;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
