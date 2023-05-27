package com.statify;

import java.util.HashMap;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.RadarChart;

public class PlaylistsRadarChartTest {

    public static void showPlaylistRadarChart(String token) {
        String user_token = token;
        User user = new User(user_token);
        int limit = 1;

        Statify.setUser(user);
        Statify.SetPlaylistsNum(limit);
        HashMap<String, String> playlists = user.getPlaylistsHashMap(4);
        FeatureName[] features = { FeatureName.ACOUSTICNESS, FeatureName.DANCEABILITY, FeatureName.ENERGY,
                FeatureName.LOUDNESS, FeatureName.LIVENESS };
        XChartPanel<RadarChart> chartPanel = Statify.getTracksRadarChartFromPlaylists(playlists, features);
        TopLevelWindow.createChartFrame(chartPanel);
    }

    public static void featureNameTest() {
        for (FeatureName v : FeatureName.values()) {
            System.out.println(v.keyName);
        }
    }

    public static void main(String[] args) {
        String token = "BQAu1DziRe3zmVAk0CLj1DEZj5j00izJ8mwbab7wL14WrDJjrtLOiQG557zYg-0Qea__VJw3YcoT3qlQCET91QZ-640l8VH5kxuZyQX9GeP40hqpoVqZWLrpaJ8qW1BXjOYmFtVsnYf2GoV4OYt0lbt0LfncGMKTScrirvrDIhEqYEAkuStFiP136jTFmn42sCOEQs2eXu2__IA5ThjUUS3icdMQfg";
        showPlaylistRadarChart(token);
        // featureNameTest();
        

    }
}
