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
        String user_token = "BQASdS2zvASHunhTiDfntD0iV188U1LZfWU8sonveRFeUBUxT1tKFdx8We4WfE70RxCcrFHFyy_LqlqBB7UBeMVJAEZJc5ajCdLzUKU3PVQoCPJJOgKfJOIr6Bi5wOklP2VhRV8FEGrci4L_Je0qIB9ISZwRuek4J-r_l4PiZc5Qn5G9BRKOZD7SKtNqukxw76qdzMg-XrGY8JD0rcRDIsCNaLNxzA9HDrrMdPkLKBNQP1yupXh5AcvxGEKieZtZFw2rQZ_wmGTLU303GIDvS2MPWW6Cy4h-JKuetVGLmic9D0Y8z9y71T5yfDiSvET5ub8";
        User user = new User(user_token);
        int limit = 15;
        List<String> playlistsIds = user.getPlaylistsIds(limit);
        List<String> tracksIds = new ArrayList<>();
        for (String pId : playlistsIds) {
            tracksIds.addAll(user.getPlaylistTracksIds(pId));
        }
        // List<String> trackIds = user.getPlaylistTracksIds(playlistId);
        List<Float> danceability_table = new ArrayList<>();
        for (String trackId : tracksIds) {
            if (trackId == null) {
                continue;
            }
            Dictionary<String, Float> audioFeatures = user.getTracksAudioFeatures(trackId);
            danceability_table.add(audioFeatures.get("danceability"));
        }
        // System.out.println(danceability_table);
        Statify statify = new Statify();
        JPanel chart = statify.getDanceabilityHistogram(danceability_table, limit);
        TopLevelWindow.createChartFrame(chart);

    }
}
