package com.statify;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

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
        String user_token = "BQCGFkTRUc1N7aml3r2DUJCPC_YYvelBuvlCzK4t6azSFzIqvo7hFhaEGCTYT9llljJzJY9lnF8OaKRd4O1Sy5jZnHNpYHYLgdCdJC_CLh9aWyB2r8JEI--FXhYw8G0Ui_z23WonGGRtNE8RfwQP9iX7ZukmkNTwWWJL-1q7s_NFLYX1Gm8Azbht2h5EqJHnjpBSnXvk0fqi4hLc3CHWgS234p_DFO8TUcoWleOTEJoqf9DxFX4NTx_x-tbbOGs6REClpz71W-kg2zugzemV7GORSi85l7Kc1XyqWZuALevg92Ak0VLELPsdEq8yJscCb1o";
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
        statify.getDanceabilityHistogram(danceability_table);

    }
}
