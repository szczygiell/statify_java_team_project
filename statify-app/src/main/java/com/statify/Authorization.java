package com.statify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class Authorization {
    private static final String clientId = "9f99ac817d684668944750770c078eb7";
    private static final String clientSecret = "5f1b959e68fd4aaf9410edc06c25f283";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/");
    private static String code = "";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            // .state("x4xkmn9pu3j6ukrs8n")
            .scope("user-read-private user-read-email user-library-read playlist-read-private user-top-read")
            .show_dialog(true)
            .build();

    public static URI authorizationCodeUri_Async() {
        URI uri = null;
        try {
            final CompletableFuture<URI> uriFuture = authorizationCodeUriRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            uri = uriFuture.join();
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
        return uri;
    }

    public static void authorizationCode_Async() {
        final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();
        try {
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRequest
                    .executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static String getAccessToken() {
        final URI uri = authorizationCodeUri_Async();
        Browser browser = new Browser();
        browser.setUp(uri.toString());
        LocalhostServer localhostServer = new LocalhostServer();
        localhostServer.startServer();
        browser.driver.close();
        code = localhostServer.getCodePrameterValue();
        authorizationCode_Async();
        String token = spotifyApi.getAccessToken();
        System.out.println("TOKEN=" + token);
        return token;

    }
}