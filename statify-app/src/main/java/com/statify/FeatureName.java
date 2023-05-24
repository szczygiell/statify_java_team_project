package com.statify;

import java.util.ArrayList;
import java.util.List;

public enum FeatureName {
    LOUDNESS("loudness"),
    ACOUSTICNESS("acousticness"),
    ENERGY("energy"),
    DANCEABILITY("danceability"),
    LIVENESS("liveness"),
    INSTRUMENTALNESS("instrumentalness");

    public final String keyName;

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
