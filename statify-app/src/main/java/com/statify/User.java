package com.statify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Dictionary;
import java.util.Hashtable;
import java.lang.Math;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;

import org.apache.hc.core5.http.ParseException;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;

import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForSeveralTracksRequest;

import java.io.IOException;

public class User {
    String accessToken = "BQDSqmePvpeHlxENAQzi1804OcGzKIYqC20ErKTNdBGE13xV5m8BFtAWJAQYEaJ5eZxP7vM06iThXMA5ekTz8RIIELZRMgNpUn9RXMWytclL52kT0-nyu042YF1joliW5w7FAQuUDmdiSkPDS52fNaJEzdisHmh3lHZJ2hD3gRFhZkClQqrIGbO4WImojHwmvxp1mgJPKHGm-GEN8Up389Az3kc_dfhZHpF5fwi1N7q5JJZEADif4z6VP-59NG_hOA43wPPX9VuLvTPhhzXIqr0md64KL2jbr6Y6g7S2wWVl3cXKEd6dHFVqWtNXcwUb1HK44vZcn7f2VpRIfR2WKwoMCg";
    private final SpotifyApi spotifyApi;
    public static final String[] audioFeaturesNames = { "danceability", "loudness", "acousticness" };

    User(String user_token) {
        this.accessToken = user_token;
        this.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(this.accessToken)
                .build();
    }

    public List<String> getPlaylistsIds(int limit) {
        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
                .getListOfCurrentUsersPlaylists()
                .limit(limit)
                .offset(0)
                .build();
        List<String> playlists_ids = new ArrayList<>();
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();
            PlaylistSimplified[] playlists = playlistSimplifiedPaging.getItems();
            for (PlaylistSimplified playlist : playlists) {
                playlists_ids.add(playlist.getId());
            }
            System.out.println(playlists[0].getName());
            return playlists_ids;

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return playlists_ids;
        }
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
            // System.out.println(playlistTrackPaging.getItems()[0].getTrack().getName());
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

    public Dictionary<String, List<Float>> audioFeaturesRequest(String[] tracksIds) {
        final GetAudioFeaturesForSeveralTracksRequest getAudioFeaturesForSeveralTracksRequest = spotifyApi
                .getAudioFeaturesForSeveralTracks(tracksIds)
                .build();
        Dictionary<String, List<Float>> selectedAudioFeatures = new Hashtable<>();
        List<List<Float>> featureBuffers = new ArrayList<>();
        for (int feature_id = 0; feature_id < audioFeaturesNames.length; feature_id++) {
            featureBuffers.add(new ArrayList<>());
        }

        try {
            final AudioFeatures[] audioFeaturesList = getAudioFeaturesForSeveralTracksRequest.execute();
            for (AudioFeatures aFeatures : audioFeaturesList) {
                featureBuffers.get(0).add(aFeatures.getDanceability());
                featureBuffers.get(1).add(aFeatures.getLoudness());
                featureBuffers.get(2).add(aFeatures.getAcousticness());

            }
            for (int feature_id = 0; feature_id < audioFeaturesNames.length; feature_id++) {
                selectedAudioFeatures.put(audioFeaturesNames[feature_id], featureBuffers.get(feature_id));
            }

            return selectedAudioFeatures;
        } catch (IOException | SpotifyWebApiException | ParseException | NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
            return selectedAudioFeatures;
        }
    }

    public Dictionary<String, List<Float>> getAllTracksAudioFeatures(String[] tracksIds) {
        Dictionary<String, List<Float>> audioFeatures = new Hashtable<>();

        int sublistLimit = 50;
        int sublistNum = (int) Math.ceil((float) tracksIds.length / sublistLimit);
        System.out.println("sublist num = " + sublistNum);
        // create feature bufers
        List<List<Float>> featureBuffers = new ArrayList<>();
        for (int feature_id = 0; feature_id < audioFeaturesNames.length; feature_id++) {
            featureBuffers.add(new ArrayList<>());
        }

        for (int sublist_id = 0; sublist_id < sublistNum; sublist_id++) {
            String[] sublist = Arrays.copyOfRange(tracksIds, sublist_id * sublistLimit,
                    Math.min((sublist_id + 1) * sublistLimit, tracksIds.length));
            Dictionary<String, List<Float>> sublistAudioFeatures = this.audioFeaturesRequest(sublist);

            if (sublistAudioFeatures.isEmpty()) {
                continue;
            }
            // add sublist's audio features to feature Buffers
            for (int feature_id = 0; feature_id < audioFeaturesNames.length; feature_id++) {
                featureBuffers.get(feature_id).addAll(sublistAudioFeatures.get(audioFeaturesNames[feature_id]));
            }
        }

        for (int feature_id = 0; feature_id < audioFeaturesNames.length; feature_id++) {
            audioFeatures.put(audioFeaturesNames[feature_id], featureBuffers.get(feature_id));
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
            // System.out.println("Name: " + track.getName());
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
            for (int i = 0; i < limit; i++) {
                trackIds.add(artists[i].getId());
            }
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
            // System.out.println("Name: " + artistInfo.getName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return artistInfo;
        }
    }

    public List<Dictionary<String, String>> getTopArtistsInfoList(int limit, String time_range) {
        List<Dictionary<String, String>> dictionaryList = new ArrayList<>();

        List<String> topArtistsIds = getTopArtistsIds(limit, time_range);

        for (int i = 0; i < limit; i++) {
            Dictionary<String, String> dictionary = getArtistInfo(topArtistsIds.get(i));
            dictionaryList.add(dictionary);

        }

        return dictionaryList;
    }

}


