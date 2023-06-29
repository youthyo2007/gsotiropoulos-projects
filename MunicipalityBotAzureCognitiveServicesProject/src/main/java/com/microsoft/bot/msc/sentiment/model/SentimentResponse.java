package com.microsoft.bot.msc.sentiment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SentimentResponse {

    private List<SentimentResult> sentiments;


    public List<SentimentResult> getSentiments() {
        return sentiments;
    }

    public void setSentiments(List<SentimentResult> sentiments) {
        this.sentiments = sentiments;
    }
}
