package com.statify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetArtistExample {
    private static final String accessToken = "BQCNpsZf10n5lfPSgo9sABXL4pVYwZJN0qofDUB3c8Wj0f829XbpqJzKsBFeUw1TRCGcrArsLjTbPm4bBPqZZQLzxxxOW5i4_zdox3DC3rIrBOwzygsh9DaqoTrGXXdbTXayYiRUZTZ0d7SM8JaAvIGVenMxG58d0BuMwGlS_74-P8q12XPp7PaIJr_R0YWra2FxFBY";
    private static final String id = "bdea0ff2378440aeb89acfde01674470";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetArtistRequest getArtistRequest = spotifyApi.getArtist(id)
            .build();

    public static void getArtist_Sync() {
        try {
            final Artist artist = getArtistRequest.execute();

            System.out.println("Name: " + artist.getName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getArtist_Async() {
        try {
            final CompletableFuture<Artist> albumFuture = getArtistRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final Artist artist = albumFuture.join();

            System.out.println("Name: " + artist.getName());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        getArtist_Sync();
        getArtist_Async();
    }
}
