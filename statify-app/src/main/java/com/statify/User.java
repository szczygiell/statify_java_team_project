package com.statify;

import java.util.ArrayList;
import java.util.List;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

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

            return playlistSimplifiedPaging.getItems()[0].getId();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return e.getMessage();
        }
    }

    public List<String> getPlaylistTracksIds(String playlistId) {
        final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                .getPlaylistsItems(playlistId)
                // .fields("description")
                .limit(10)
                // .offset(0)
                // .market(CountryCode.SE)
                // .additionalTypes("track,episode")
                .build();

        try {
            final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
            List<String> tracks_indeces = new ArrayList<String>();
            for (PlaylistTrack pl_track : playlistTrackPaging.getItems()) {
                tracks_indeces.add(pl_track.getTrack().getId());
            }
            return tracks_indeces;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<String>();
        }
    }

}
