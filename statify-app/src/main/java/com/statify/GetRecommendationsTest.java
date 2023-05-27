package com.statify;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.util.HashMap;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

public class GetRecommendationsTest {

    public static void getRecommendations(String token) {
        String user_token = token;
        User user = new User(user_token);
        int limit = 1;
        List<String> playlistIds = user.getPlaylistsIds(limit);
        List<String> topTracksIds = user.getTopTrackIds(2, "short_term");
        List<String> topArtistsIds = user.getTopArtistsIds(2, "long_term");

        Statify.setUser(user);
        Statify.SetPlaylistsNum(limit);
        // List<TrackSimplified> recommendedTracks =
        // user.getRecomendations(topArtistsIds, new ArrayList<String>());
        // List<TrackSimplified> recommendedTracks = user.getRecomendations(new
        // ArrayList<String>(), topTracksIds);
        List<TrackSimplified> recommendedTracks = user.getRecomendations(topArtistsIds, topTracksIds);

        for (int i = 0; i < recommendedTracks.size(); i++) {
            System.out.println(recommendedTracks.get(i).getName());
        }
    }

    public static void getRecomendationsPanelByTrack(String token) {
        String user_token = token;
        User user = new User(user_token);
        Statify.setUser(user);
        JScrollPane scrollPane = Statify.getRecommendationsPanelByTopArtists("short_term");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        String token = "BQCSYgGCnPi4sSARwrkWNoWCOos7qdhw7C85o3bbFhJw9FJe4Yl9fLOheg7HxG2uhE8SPOo1QAiVhL6pRmv9UqgE4W_B__tvx-CAklnZ4bPS9QEVrDGtVRx5NZucOvopBhpWYSaZ8_temTk197h__JJJ8miM5n9YNr3FOpA0g7DY1tEH3Cu0cR0iu6IwM5i8RKY4g6Xo5uT11LCl1Wo6_Mbd_TikTg";
        // getRecommendations(token);
        getRecomendationsPanelByTrack(token);
    }
}
