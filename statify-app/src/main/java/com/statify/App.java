package com.statify;

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
        String user_token = "";
        User user = new User(user_token);
        String playlistId = user.getFirstPlaylistId();
        user.getPlaylistTracksIds(playlistId);

    }
}
