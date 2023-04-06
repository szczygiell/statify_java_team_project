package com.statify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class TokenTest {
  private static String accessToken = "";
  private static GetCurrentUsersProfileRequest getCurrentUsersProfileRequest;

  public TokenTest(String newToken) {
    accessToken = newToken;
    final SpotifyApi spotifyApi = new SpotifyApi.Builder()
    .setAccessToken(accessToken)
    .build();
    getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
    .build();
  }

    public static boolean TokenTestFun() {
      try {
        final User user = getCurrentUsersProfileRequest.execute();
        return user.getDisplayName() instanceof String;
      } catch (IOException | SpotifyWebApiException | ParseException e) {
        System.out.println("Error: " + e.getMessage());
      }
      return false;
    }
    
  public static void main(String[] args) {
    String accessToken = "BQD4KpgQTYbOUdRpG-3ceCUr26eUHul5TfMeLXtbDEK3CYqXo38ISiY-yir3eerTM86FHFU9WC0FfMQX1st94yo1UlHU_iC-2ZIPPdMyyFn4lSp89aCpUEWhASrdygw6xqtj4BxzsTb84TatJ5mBlTB0GkpAFJ09yrsC7nAW36iwcOFv9o3Yp-kdCqoBn8xHphxu05CSHfw7pnR5DnJpF4-NtmwuOmE4WvK_ZIzWOZU8Sf8bem0gFwjfp0PhPGBunES-7WbxBhwB1TKCp0_adQsCL49a4IJdE6klERqQI3Jr0DSneLXufuXZw5usgjy3dHmdjcF7";
    TokenTest token_test = new TokenTest(accessToken);
    boolean ans = token_test.TokenTestFun();
    System.out.println(ans);
  }
}