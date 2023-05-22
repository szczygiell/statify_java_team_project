package com.statify;

public enum FeatureName {
    LOUDNESS {
        public String toString() {
            return "Loudness";
        }

        public String keyName() {
            return "loudness";
        }
    },

    ACOUSTICNESS {
        public String toString() {
            return "Acousticness";
        }

        public String keyName() {
            return "acousticness";
        }
    },

    DANCEABILITY {
        public String toString() {
            return "Danceability";
        }

        public String keyName() {
            return "danceability";
        }
    };

    abstract String keyName();

}
