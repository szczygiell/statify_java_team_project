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
        String[] features = { "acousticness", "danceability", "liveness", "energy" };
        XChartPanel<RadarChart> chartPanel = Statify.getTracksRadarChartFromPlaylists(playlists, features);
        TopLevelWindow.createChartFrame(chartPanel);
    }

    public static void main(String[] args) {
        String token = "BQD39nT6LRHG8SHQlkdVURKXdLEcBIXGGWu5yDFD7_aHLZ81dC_dZN2iXrIj6yoF1N_JtrXoREHaTuITZcjboIuBRNDzWD4uJwzlOmoSuOJEPzAALfzmW2h6GBQ3GVp_4sFYNKvGy0jmVwVu4f4z3Ur8eQYgVJK_M3yMy2HWqVrAVARsPJNqxoF3idl7G5YPdCwCLlcFWZv4FKQOdXdBi8pfvo_tAg";
        showPlaylistRadarChart(token);

    }
}
