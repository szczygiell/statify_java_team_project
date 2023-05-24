package com.statify;

import java.util.ArrayList;
import java.util.List;

public enum FeatureName {
    LOUDNESS("loudness") {
        String description = "The overall loudness of a track in decibels (dB). Loudness values are averaged across the entire track and are useful for comparing relative loudness of tracks. Loudness is the quality of a sound that is the primary psychological correlate of physical strength (amplitude).";
    },
    ACOUSTICNESS("acousticness") {
        String descrption = "A confidence measure from 0.0 to 1.0 of whether the track is acoustic. 1.0 represents high confidence the track is acoustic.";
    },
    ENERGY("energy") {
        String description = "Energy is a measure from 0.0 to 1.0 and represents a perceptual measure of intensity and activity. Typically, energetic tracks feel fast, loud, and noisy. For example, death metal has high energy, while a Bach prelude scores low on the scale. Perceptual features contributing to this attribute include dynamic range, perceived loudness, timbre, onset rate, and general entropy.";
    },
    DANCEABILITY("danceability") {
        String descrition = "Danceability describes how suitable a track is for dancing based on a combination of musical elements including tempo, rhythm stability, beat strength, and overall regularity. A value of 0.0 is least danceable and 1.0 is most danceable.";
    },
    LIVENESS("liveness") {
        String description = "Detects the presence of an audience in the recording. Higher liveness values represent an increased probability that the track was performed live. A value above 0.8 provides strong likelihood that the track is live.";
    },
    INSTRUMENTALNESS("instrumentalness") {
    String description = "Predicts whether a track contains no vocals. \"Ooh\" and \"aah\" sounds are treated as instrumental in this context. Rap or spoken word tracks are clearly "vocal". The closer the instrumentalness value is to 1.0, the greater likelihood the track contains no vocal content.";
    },
    VALENCE("valence") {
        String description = "A measure from 0.0 to 1.0 describing the musical positiveness conveyed by a track. Tracks with high valence sound more positive (e.g. happy, cheerful, euphoric), while tracks with low valence sound more negative (e.g. sad, depressed, angry).";
    };

    public final String keyName;
    public final String description;

    private FeatureName(String keyName) {
        this.keyName = keyName;
    }

    public static String[] toArray(FeatureName[] features) {
        List<String> output = new ArrayList();
        for (FeatureName feature : features) {
            output.add(feature.keyName);
        }
        return output.toArray(String[]::new);
    }
}
