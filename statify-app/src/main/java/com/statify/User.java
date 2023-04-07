package com.statify;

import java.util.ArrayList;
import java.util.List;
import java.util.Dictionary;
import java.util.Hashtable;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.AbstractDataPagingRequest;
import se.michaelthelin.spotify.requests.data.AbstractDataRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetSeveralTracksRequest;


import java.io.IOException;

public class User {
    String accessToken = "";
    private final SpotifyApi spotifyApi;

    User(String user_token) {
        this.accessToken = user_token;
        this.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(this.accessToken)
                .build();
    }

    public String getFirstPlaylistId() {

        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
                .getListOfCurrentUsersPlaylists()
                .limit(10)
                .offset(0)
                .build();
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();
            int playlist_number = 1;
            PlaylistSimplified playlist = playlistSimplifiedPaging.getItems()[playlist_number];
            System.out.println(playlist.getName());
            return playlist.getId();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return e.getMessage();
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

    public Dictionary<String, Float> getTracksAudioFeatures(String trackId) {
        final GetAudioFeaturesForTrackRequest getAudioFeaturesForTrackRequest = spotifyApi
                .getAudioFeaturesForTrack(trackId)
                .build();

        Dictionary<String, Float> selectedAudioFeatures = new Hashtable<>();

        try {
            final AudioFeatures audioFeatures = getAudioFeaturesForTrackRequest.execute();
            selectedAudioFeatures.put("danceability", audioFeatures.getDanceability());
            selectedAudioFeatures.put("loudness", audioFeatures.getLoudness());
            selectedAudioFeatures.put("acousticness", audioFeatures.getAcousticness());
            return selectedAudioFeatures;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return selectedAudioFeatures;
        }

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
            trackInfo.put("duration", track.getDurationMs().toString(0)); //track length in milliseconds
            trackInfo.put("duration", track.getAlbum().getName());
            trackInfo.put("duration", track.getAlbum().getImages()[0].toString()); // "Image(height=" + height + ", url=" + url + ", width=" + width + ")"
            return trackInfo;
            // System.out.println("Name: " + track.getName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return trackInfo;
        }
      }


}
