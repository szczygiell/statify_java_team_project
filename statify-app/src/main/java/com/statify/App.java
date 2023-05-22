package com.statify;

import javax.swing.JPanel;
import javax.swing.JFrame;

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

    public static void danceabilityHistogramSeveralTracks(String token) {
        String user_token = token;
        User user = new User(user_token);
        int limit = 1;

        Statify.setUser(user);
        Statify.SetPlaylistsNum(limit);
        JPanel chart = Statify.getDanceabilityHistogram();
        TopLevelWindow.createChartFrame(chart);

    }

    public static void loudnessHistogramSeveralTracks(String token) {
        String user_token = token;
        User user = new User(user_token);
        int limit = 1;

        Statify.setUser(user);
        Statify.SetPlaylistsNum(limit);
        JPanel chart = Statify.getLoudnessHistogram();
        TopLevelWindow.createChartFrame(chart);

    }

    public static void acousticnessHistogramSeveralTracks(String token) {
        String user_token = token;
        User user = new User(user_token);
        int limit = 1;

        Statify.setUser(user);
        Statify.SetPlaylistsNum(limit);
        JPanel chart = Statify.getLoudnessHistogram();
        TopLevelWindow.createChartFrame(chart);
    }

    public static void login_window() {
        LoginWindow frame = new LoginWindow();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(10, 10, 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

    }

    public static void runApp() {
        String accessToken = Authorization.getAccessToken();

        TokenTest token_test = new TokenTest(accessToken);
        if (token_test.TokenTestFun()) {
            Statify.setUser(new User(accessToken));
            MainWindow main_window = new MainWindow();
            main_window.setVisible(true);
        } else {
            System.out.println("Invalid access token :(((");
        }
    }

    public static void main(String[] args) {
        login_window();
        // runApp();

    }
}
