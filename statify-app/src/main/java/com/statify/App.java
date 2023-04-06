package com.statify;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import javax.swing.JPanel;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        String user_token = "BQA9FRATlnaXOGZCWbG7rmOUWetlDI9fEhwYyjXoDf-tkZ1coMDi549Ms7IjWirU737LWtDLSXh54S0wG3SQBP28BR1f0OcmlReIwGZi7JFx4JIORr0-5RZxWCIaEfEdWDhnWjH6flu1anp6tDXUDRsrR2raObdaw9miWpxpAdDay6rMO5zT21mvHkDZQVtUszZNs42WscGCokhc9paOWtp5OxC5qg6nN9nNzLQ5jm5SNdQw4AgNSoHhWJwDzLy_Obm2o8Ei0Xpfo7NlTb0KqLkmVD_KcQlerBwkmrrq1CSCsJSZgx3OHaLlcTnhlZsFcUI";
        User user = new User(user_token);
        String playlistId = user.getFirstPlaylistId();
        List<String> trackIds = user.getPlaylistTracksIds(playlistId);
        List<Float> danceability_table = new ArrayList<>();

        for (String trackId : trackIds) {
            Dictionary<String, Float> audioFeatures = user.getTracksAudioFeatures(trackId);
            danceability_table.add(audioFeatures.get("danceability"));
        }
        // System.out.println(danceability_table);
        Statify statify = new Statify();
        JPanel chart = statify.getDanceabilityHistogram(danceability_table);
        TopLevelWindow.createChartFrame(chart);

    }
}
