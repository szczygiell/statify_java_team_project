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
        String user_token = "BQBa7O26g90UkKSzILPcTzyqhb0KOydJdpZQgRYZJU3eAqTi3V__pcZXmGvrlY7pFwqu_-0fA1mcMIFCZVimxMhshVGR2R0qK9iGiHdlr7zm_meMafHyljCJ0W91CfESJGfWQDX9fFC5NjgV7Y5bAHHAj63szDoXyeLhdSQacrzq3u5I7UQ2PFQblZbsCOWqm7W4ZSa3siCi_WYzjRCZ-tfwxPCVCW53H_278cCy9RkMeQubOMtSprpqAZdx9aGRKxve8kkeaCOVFOBy05PlxsdrlDuXMtVF3gLVp9G2HpadozNeutRNm2_4u2aoJffpudU";
        User user = new User(user_token);
        String playlistId = user.getFirstPlaylistId();
        String trackId = user.getPlaylistTracksIds(playlistId).get(2);
        // String tracks_albumId = user.getTracksAlbumId(trackId);
        System.out.println(user.getTracksAudioFeatures(trackId));
    }
}
