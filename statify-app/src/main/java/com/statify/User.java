package com.statify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.PriorityQueue;
import java.util.Properties;
import java.lang.Math;
import java.io.IOException;
import org.apache.hc.core5.http.ParseException;
import org.eclipse.jetty.util.thread.strategy.ProduceConsume;

import se.michaelthelin.spotify.model_objects.specification.Playlist;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForSeveralTracksRequest;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;

public class User {
    String accessToken = "BQDSqmePvpeHlxENAQzi1804OcGzKIYqC20ErKTNdBGE13xV5m8BFtAWJAQYEaJ5eZxP7vM06iThXMA5ekTz8RIIELZRMgNpUn9RXMWytclL52kT0-nyu042YF1joliW5w7FAQuUDmdiSkPDS52fNaJEzdisHmh3lHZJ2hD3gRFhZkClQqrIGbO4WImojHwmvxp1mgJPKHGm-GEN8Up389Az3kc_dfhZHpF5fwi1N7q5JJZEADif4z6VP-59NG_hOA43wPPX9VuLvTPhhzXIqr0md64KL2jbr6Y6g7S2wWVl3cXKEd6dHFVqWtNXcwUb1HK44vZcn7f2VpRIfR2WKwoMCg";
    private final SpotifyApi spotifyApi;

    User(String user_token) {
        this.accessToken = user_token;
        this.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(this.accessToken)
                .build();
    }

    // gets list of firsy {limit} playlists from library
    public PlaylistSimplified[] getPlaylists(int limit) {
        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
                .getListOfCurrentUsersPlaylists()
                .limit(limit)
                .offset(0)
                .build();
        PlaylistSimplified[] playlists = new PlaylistSimplified[0];
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();
            return playlistSimplifiedPaging.getItems();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return playlists;
        }
    }

    public HashMap<String, String> getPlaylistsHashMap() {
        int limit = 20;
        return this.getPlaylistsHashMap(limit);
    }

    // Returns hashmap of pairs playlist name :playlist id
    public HashMap<String, String> getPlaylistsHashMap(int limit) {
        PlaylistSimplified[] playlists = getPlaylists(limit);
        HashMap<String, String> playlistsDictionary = new HashMap<>();
        // ArrayList<String> checkedNames;
        for (PlaylistSimplified playlist : playlists) {
            playlistsDictionary.put(playlist.getName(), playlist.getId());
        }
        return playlistsDictionary;
    }

    public HashMap<String, String> getPlaylistsHashMap(ArrayList<String> checkedNames) {
        PlaylistSimplified[] playlists = getPlaylists(20);
        HashMap<String, String> playlistsDictionary = new HashMap<>();
        // ArrayList<String> checkedNames;
        for (PlaylistSimplified playlist : playlists) {
            if (checkedNames.contains(playlist.getName())) {
                playlistsDictionary.put(playlist.getName(), playlist.getId());
            }
        }
        return playlistsDictionary;
    }

    public List<String> getPlaylistsIds(int limit) {
        List<String> playlists_ids = new ArrayList<>();
        PlaylistSimplified[] playlists = getPlaylists(limit);
        for (PlaylistSimplified playlist : playlists) {
            playlists_ids.add(playlist.getId());
        }
        return playlists_ids;
    }

    public List<String> getPlaylistTracksIds(String playlistId) {
        final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                .getPlaylistsItems(playlistId)
                // .fields("description")
                // .limit(100)
                // .offset(0)
                // .market(CountryCode.SE)
                // .additionalTypes("track,episode")
                .build();

        try {
            final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
            List<String> tracks_indeces = new ArrayList<String>();
            for (PlaylistTrack pl_track : playlistTrackPaging.getItems()) {
                IPlaylistItem track = pl_track.getTrack();
                if (track == null) {
                    continue;
                }
                tracks_indeces.add(track.getId());
            }
            return tracks_indeces;
        } catch (IOException | SpotifyWebApiException | ParseException | NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<String>();
        }
    }

    public String getTracksAlbumId(String trackId) {
        final GetTrackRequest getTrackRequest = spotifyApi.getTrack(trackId)
                .build();
        try {
            final Track track = getTrackRequest.execute();
            return track.getAlbum().getId();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return "";
        }
    }

    // gets dictionary with feature name: list of values in all tracks
    public Dictionary<String, List<Float>> audioFeaturesRequest(String[] tracksIds) {
        final GetAudioFeaturesForSeveralTracksRequest getAudioFeaturesForSeveralTracksRequest = spotifyApi
                .getAudioFeaturesForSeveralTracks(tracksIds)
                .build();
        Dictionary<String, List<Float>> selectedAudioFeatures = new Hashtable<>();
        for (FeatureName feature : FeatureName.values()) {
            selectedAudioFeatures.put(feature.keyName, new ArrayList<Float>());
        }
        try {
            final AudioFeatures[] audioFeaturesList = getAudioFeaturesForSeveralTracksRequest.execute();
            for (AudioFeatures aFeatures : audioFeaturesList) {
                selectedAudioFeatures.get(FeatureName.DANCEABILITY.keyName).add(aFeatures.getDanceability());
                selectedAudioFeatures.get(FeatureName.LOUDNESS.keyName).add(aFeatures.getLoudness());
                selectedAudioFeatures.get(FeatureName.ACOUSTICNESS.keyName).add(aFeatures.getAcousticness());
                selectedAudioFeatures.get(FeatureName.INSTRUMENTALNESS.keyName).add(aFeatures.getInstrumentalness());
                selectedAudioFeatures.get(FeatureName.ENERGY.keyName).add(aFeatures.getEnergy());
            }
            return selectedAudioFeatures;
        } catch (IOException | SpotifyWebApiException | ParseException | NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
            return selectedAudioFeatures;
        }
    }

    // returns dictionary of feature_name: feature value
    public Dictionary<String, List<Float>> getAllTracksAudioFeatures(String[] tracksIds) {
        Dictionary<String, List<Float>> audioFeatures = new Hashtable<>();
        int sublistLimit = 50;
        int sublistNum = (int) Math.ceil((float) tracksIds.length / sublistLimit);
        for (FeatureName feature : FeatureName.values()) {
            audioFeatures.put(feature.keyName, new ArrayList<>());
        }
        for (int sublist_id = 0; sublist_id < sublistNum; sublist_id++) {
            String[] sublist = Arrays.copyOfRange(tracksIds, sublist_id * sublistLimit,
                    Math.min((sublist_id + 1) * sublistLimit, tracksIds.length));
            Dictionary<String, List<Float>> sublistAudioFeatures = this.audioFeaturesRequest(sublist);

            if (sublistAudioFeatures.isEmpty()) {
                continue;
            }
            // add sublist's audio features to featureBuffer
            for (FeatureName feature : FeatureName.values()) {
                String fName = feature.keyName;
                audioFeatures.get(fName).addAll(sublistAudioFeatures.get(fName));
            }
        }
        return audioFeatures;
    }

    public List<String> getTopTrackIds(int limit, String time_range) {
        List<String> trackIds = new ArrayList<>();
        final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
                .time_range(time_range) // "short_term": 4 weeks; "medium_term": 6 months; "long_term": years
                .limit(limit) // number of tracks
                .build();
        try {
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
            Track[] tracks = trackPaging.getItems();
            for (int i = 0; i < limit; i++) {
                trackIds.add(tracks[i].getId());
            }
            return trackIds;

        } catch (IOException | SpotifyWebApiException | ParseException | NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<String>();
        }

    }

    public Dictionary<String, String> getTrackInfo(String trackId) {
        final GetTrackRequest getTrackRequest = spotifyApi.getTrack(trackId)
                .build();

        Dictionary<String, String> trackInfo = new Hashtable<>();

        try {
            final Track track = getTrackRequest.execute();
            trackInfo.put("name", track.getName());
            trackInfo.put("artist", track.getArtists()[0].getName());
            trackInfo.put("duration", track.getDurationMs().toString(0)); // track length in milliseconds
            trackInfo.put("album", track.getAlbum().getName());
            trackInfo.put("image", track.getAlbum().getImages()[0].toString()); // "Image(height=" + height + ", url=" +
                                                                                // url + ", width=" + width + ")"
            return trackInfo;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return trackInfo;
        }
    }

    public List<Dictionary<String, String>> getTopTracksInfoList(int limit, String time_range) {
        List<Dictionary<String, String>> dictionaryList = new ArrayList<>();

        List<String> topTracksIds = getTopTrackIds(limit, time_range);

        for (int i = 0; i < limit; i++) {
            Dictionary<String, String> dictionary = getTrackInfo(topTracksIds.get(i));
            dictionaryList.add(dictionary);

        }

        return dictionaryList;
    }

    public List<String> getTopArtistsIds(int limit, String time_range) {
        List<String> trackIds = new ArrayList<>();
        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
                .time_range(time_range) // "short_term": 4 weeks; "medium_term": 6 months; "long_term": years
                .limit(limit) // number of tracks
                .build();

        try {
            final Paging<Artist> artistsPaging = getUsersTopArtistsRequest.execute();
            Artist[] artists = artistsPaging.getItems();

            for (int i = 0; i < artists.length; i++) {
                trackIds.add(artists[i].getId());
            }
            // if(limit > artists.length){
            // trackIds.add(Integer.toString(artists.length));
            // }
            return trackIds;

        } catch (IOException | SpotifyWebApiException | ParseException | NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<String>();
        }

    }

    public Dictionary<String, String> getArtistInfo(String artistId) {
        final GetArtistRequest getArtistRequest = spotifyApi.getArtist(artistId)
                .build();

        Dictionary<String, String> artistInfo = new Hashtable<>();

        try {
            final Artist artist = getArtistRequest.execute();
            artistInfo.put("name", artist.getName());
            artistInfo.put("genres", String.join(", ", artist.getGenres()));

            return artistInfo;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return artistInfo;
        }
    }

    public List<Dictionary<String, String>> getTopArtistsInfoList(int limit, String time_range) {
        List<Dictionary<String, String>> dictionaryList = new ArrayList<>();

        List<String> topArtistsIds = getTopArtistsIds(limit, time_range);

        for (int i = 0; i < topArtistsIds.size(); i++) {
            Dictionary<String, String> dictionary = getArtistInfo(topArtistsIds.get(i));
            dictionaryList.add(dictionary);
        }
        return dictionaryList;
    }

    public Dictionary<String, Integer> getTopGenresDict(int limit, String time_range) {
        Dictionary<String, Integer> dictionary = new Hashtable<>();

        List<Dictionary<String, String>> artistInfoList = getTopArtistsInfoList(limit, time_range);
        for (int i = 0; i < limit; i++) {
            List<String> genresList = Arrays.asList(artistInfoList.get(i).get("genres").split(", "));
            for (int j = 0; j < genresList.size(); j++) {
                String klucz = genresList.get(j);
                if (dictionary.get(klucz) != null) {
                    dictionary.put(klucz, dictionary.get(klucz) + 1);
                } else {
                    dictionary.put(klucz, 1);
                }
            }

        }
        return dictionary;
    }

    public Dictionary<String, Integer> analyzeDictionary(Dictionary<String, Integer> dictionary) {
        PriorityQueue<String> pq = new PriorityQueue<>((key1, key2) -> dictionary.get(key2) - dictionary.get(key1));

        // Przejście po słowniku i dodanie kluczy do kolejki priorytetowej
        Enumeration<String> keys = dictionary.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            pq.offer(key);
        }

        // Pobranie 10 kluczy z najwyższymi wartościami
        int topCount = 10;
        Dictionary<String, Integer> result = new Hashtable<>();
        int remainingSum = 0;
        for (int i = 0; i < topCount; i++) {
            String key = pq.poll();
            if (key != null) {
                int value = dictionary.get(key);
                result.put(key, value);
            }
        }

        // Counting sum of rest values from keys - not used now but do not delete this
        // while (!pq.isEmpty()) {
        // String key = pq.poll();
        // remainingSum += dictionary.get(key);
        // }

        // Adding ket "others" with its value from above - not used now but do not
        // delete this
        // result.put("others", remainingSum);

        return result;
    }

    // artistIds' and tracksIds' length must be max 5
    public List<TrackSimplified> getRecomendations(List<String> artistsIds, List<String> tracksIds) {
        String tracksIdsString = String.join(",", tracksIds);
        String artistsIdsString = String.join(",", artistsIds);
        final GetRecommendationsRequest getRecommendationsRequest = spotifyApi.getRecommendations()
                .limit(30)
                // .market(CountryCode.SE)
                // .max_popularity(50)
                // .min_popularity(10)
                .seed_artists(artistsIdsString)
                // .seed_genres("electro")
                .seed_tracks(tracksIdsString)
                // .target_popularity(20)
                .build();
        List<TrackSimplified> recommendedTracks = new ArrayList<TrackSimplified>();
        try {
            final CompletableFuture<Recommendations> recommendationsFuture = getRecommendationsRequest.executeAsync();

            final Recommendations recommendations = recommendationsFuture.join();

            recommendedTracks.addAll(new ArrayList<>(Arrays.asList(recommendations.getTracks())));

        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
        return recommendedTracks;
    }

    // hashmap of id : [name, artist, duration]
    public HashMap<String, List<String>> getRecomendationsByTopTracks(String timeDuration) {
        List<String> topTracksIds = getTopTrackIds(5, timeDuration);
        List<TrackSimplified> recommendedTracks = getRecomendations(new ArrayList<String>(), topTracksIds);
        HashMap<String, List<String>> tracks = new HashMap<>();
        for (TrackSimplified track : recommendedTracks) {

            List<String> trackProperties = new ArrayList<>();
            trackProperties.add(track.getName());
            trackProperties.add(track.getArtists()[0].getName());
            trackProperties.add(String.valueOf((int) track.getDurationMs() / 60000) + " min" + String
                    .valueOf((int) track.getDurationMs() / 1000 % 60) + "s");

            tracks.put(track.getId(), trackProperties);
            // tracks.put(track.getName(), track.getArtists()[0].getName());
        }
        return tracks;
    }

    public HashMap<String, List<String>> getRecomendationsByTopArtists(String timeDuration) {
        List<String> topArtistsIds = getTopArtistsIds(5, timeDuration);
        List<TrackSimplified> recommendedTracks = getRecomendations(topArtistsIds, new ArrayList<String>());
        HashMap<String, List<String>> tracks = new HashMap<>();
        for (TrackSimplified track : recommendedTracks) {
            List<String> trackProperties = new ArrayList<>();
            trackProperties.add(track.getName());
            trackProperties.add(track.getArtists()[0].getName());
            trackProperties.add(String.valueOf(String.valueOf((int) track.getDurationMs() / 60000) + " min" + String
                    .valueOf((int) track.getDurationMs() / 1000 % 60) + "s"));

            tracks.put(track.getId(), trackProperties);
        }
        return tracks;
    }

    public int getMaxAmmount(int limit, String time_range, String type) {

        if (type.equals("artists")) {
            final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
                    .time_range(time_range) // "short_term": 4 weeks; "medium_term": 6 months; "long_term": years
                    .limit(limit) // number of tracks
                    .build();
            try {
                final Paging<Artist> artistsPaging = getUsersTopArtistsRequest.execute();
                Artist[] artists = artistsPaging.getItems();
                return artists.length;
            } catch (IOException | SpotifyWebApiException | ParseException | NullPointerException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        if (type.equals("tracks")) {
            final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
                    .time_range(time_range) // "short_term": 4 weeks; "medium_term": 6 months; "long_term": years
                    .limit(limit) // number of tracks
                    .build();
            try {
                final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
                Track[] tracks = trackPaging.getItems();
                return tracks.length;
            } catch (IOException | SpotifyWebApiException | ParseException | NullPointerException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return 1;

    }
}
