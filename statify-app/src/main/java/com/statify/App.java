package com.statify;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import javax.sound.midi.SysexMessage;

import java.util.Hashtable;
import com.statify.Statify;

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
        String user_token = "BQA5p1HJ1C8FC209biUYH0Uh7J9XaMFwnNCN3jhJNypFUnni6HwAoQ5X0F4D7jJHEHH2gyW-ru_EJlFWceVv5WYax7Hcz8NGJNSIghuRO5Zhi6N2-z-imKzNNXHwt4Bcy1uOXDeDxz6Sdo3LTdzOuSEjxW8N6q94eGKoO9IPAL1azrqbJGXwM0LVu1hPk_xJgTOZPbxt-DTjUcSBGW8lYl6TrbgJmzVUUxJC_mNRoaHtAnZDw2bq1V-NlCJPpt4NA5wPox9xNcJREX4B1KE81_LpvsUyQK9SxxYPNDED-s-gG-RSc-aMpVpH3je54SdXwIk";
        User user = new User(user_token);
        String playlistId = user.getFirstPlaylistId();
        List<String> trackIds = user.getPlaylistTracksIds(playlistId);
        // String tracks_albumId = user.getTracksAlbumId(trackId);
        List<Float> danceability_table = new ArrayList<>();

        for (String trackId : trackIds) {
            Dictionary<String, Float> audioFeatures = user.getTracksAudioFeatures(trackId);
            System.out.println(audioFeatures.get("danceability"));
            danceability_table.add(audioFeatures.get("danceability"));
        }
        // System.out.println(danceability_table);
        Statify statify = new Statify();
        statify.getDanceabilityHistogram(danceability_table);

    }
}
