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
        String token = "BQCAVu0ouWNP1gZEu0Z6tmArKeXdW1zn3GrMHi7m4n3zcP8FXLQXO8jDCSX5_dxgqfusf1a-Rd-xFsOrsP81zk_LeBtKByysNEcnpk6C8MReqlos4EV3Rg7eFWBQaH1DEuLaiu671I0tOV1RnINPSVNwz2S4ZJItPvqaNO2Y-mbIFcsWkkO-1BDYxEA4E0Via3RD92Mb9bdT4_cWmsVmmbQdBYjl0Q";
        showPlaylistRadarChart(token);
        // featureNameTest();

    }
}
