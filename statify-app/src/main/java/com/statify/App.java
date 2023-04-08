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

    public static void danceabilityHistogramSeveralTracks(String token) {
        String user_token = token;
        User user = new User(user_token);
        int limit = 30;
        List<String> playlistsIds = user.getPlaylistsIds(limit);
        List<String> tracksIds = new ArrayList<>();
        for (String pId : playlistsIds) {
            tracksIds.addAll(user.getPlaylistTracksIds(pId));
        }
        List<Float> danceability_table = new ArrayList<>();
        Dictionary<String, List<Float>> audioFeatures = user
                .getAllTracksAudioFeatures(tracksIds.toArray(new String[0]));
        try {
            danceability_table.addAll(audioFeatures.get("danceability"));
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        Statify statify = new Statify();
        JPanel chart = statify.getDanceabilityHistogram(danceability_table, limit);
        TopLevelWindow.createChartFrame(chart);

    }

    public static void main(String[] args) {
        String user_token = "BQB7xCiuEbVOErMc6wTGq0edanpPQcEzrbX3wXkeNynCklJua4-q_vOpa0Q3shWBXMOwkdM8RjTVTXu5uDBY4CfwWImHXXvVdl3ZuAdlBiQnzdOpqxxQsAnhIUcDvAXdUQxQOZ1jS-D_Sh_QyYIxrOd3G5DoEFllPC3bb3tvYqE645T9PBtIO0xMjtHHR5g0VyXxiDzQnof0LGnJzgZ-rSNxm5UbgHGuE1jLJqD-OU1A3Xy9Dct2HqTlz5vK18G9v99URQ2NAllodA2fFh__85okwhLqPMdoxfI0Y3F3evUC1zALeyDq7tGHjEi_VVl2jAo";
        // danceabilityHistogramSeparateTracks(user_token);
        danceabilityHistogramSeveralTracks(user_token);

    }
}
