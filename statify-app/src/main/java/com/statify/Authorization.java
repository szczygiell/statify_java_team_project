package com.statify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import java.net.PortUnreachableException;
import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.io.IOException;

import java.awt.Desktop;
import java.net.URISyntaxException;
import org.apache.commons.lang3.SystemUtils;

public class Authorization {
    private static final String clientId = "9f99ac817d684668944750770c078eb7";
    private static final String clientSecret = "5f1b959e68fd4aaf9410edc06c25f283";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/");
    private static final String code = "";
    static Process process;

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            // .state("x4xkmn9pu3j6ukrs8n")
            // .scope("user-read-birthdate,user-read-email")
            .show_dialog(true)
            .build();

    public static void authorizationCodeUri_Sync() {
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI: " + uri.toString());
    }

    private static final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
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
        try {
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRequest
                    .executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void browseURL(String urlString) {
        Runtime rt = Runtime.getRuntime();
        String url = urlString;
        String[] browsers = { "google-chrome", "firefox", "mozilla", "epiphany", "konqueror",
                "netscape", "opera", "links", "lynx" };

        StringBuffer cmd = new StringBuffer();
        for (int i = 0; i < browsers.length; i++)
            if (i == 0)
                cmd.append(String.format("%s \"%s\"", browsers[i], url));
            else
                cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
        // If the first didn't work, try the next browser and so on
        try {

            process = rt.exec(new String[] { "sh", "-c", cmd.toString() });
            process.destroy();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        final URI uri = authorizationCodeUri_Async();
        browseURL(uri.toString());
        if (process.isAlive()) {
            System.out.println("Process is now alive");
        } else {
            System.out.println("Process now not alive");
        }
        LocalhostServer localhostServer = new LocalhostServer();
        localhostServer.startServer();
        if (process.isAlive()) {
            System.out.println("Process still alive");
        }
        String code = localhostServer.getCodePrameterValue();
        // localhostServer.stopServer();
        System.out.println("Code=" + code);
        // authorizationCodeUri_Async();
        // authorizationCode_Async();
        if (process.isAlive()) {
            System.out.println("Process still alive after destroy");
        } else {
            System.out.println("Process now not alive after destroy");
        }

    }
}