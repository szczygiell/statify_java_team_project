package com.statify;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.style.Styler.ChartTheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Dictionary;
import java.lang.Math;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JFrame;

import java.util.Dictionary;
import java.util.Hashtable;

import com.statify.User;

public class Statify {

    private static User currentUser;
    private static int playlists_num;

    public static void setUser(User user) {
        currentUser = user;
    }

    public static void SetPlaylistsNum(int new_playlists_num) {
        playlists_num = new_playlists_num;
    }

    private static CategoryChart getBarChart(List<Double> x_values,
            List<Double> y_values,
            String title,
            String seriesName,
            String xAxisTitle,
            String yAxisTitle) {
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .theme(ChartTheme.GGPlot2)
                .title(title)
                .xAxisTitle(xAxisTitle)
                .yAxisTitle(yAxisTitle)
                .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setLabelsVisible(true);
        chart.getStyler().setPlotGridLinesVisible(true);

        // Series
        try {
            chart.addSeries(seriesName, x_values, y_values);
            chart.getStyler().setAvailableSpaceFill(.96);

            return chart;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e);
            chart.addSeries("Error", Arrays.asList(0.0f), Arrays.asList(0));
            return chart;
        }
    }

    private static List<Float> getDancebilityPlaylistsData(int limit) {
        List<String> playlistsIds = Statify.currentUser.getPlaylistsIds(limit);
        List<String> tracksIds = new ArrayList<>();
        for (String pId : playlistsIds) {
            tracksIds.addAll(Statify.currentUser.getPlaylistTracksIds(pId));
        }
        List<Float> danceabilityTable = new ArrayList<>();
        Dictionary<String, List<Float>> audioFeatures = Statify.currentUser
                .getAllTracksAudioFeatures(tracksIds.toArray(new String[0]));
        System.out.println(audioFeatures);
        try {
            danceabilityTable.addAll(audioFeatures.get("danceability"));
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return danceabilityTable;
    }

    public static JPanel getDanceabilityHistogram() {
        List<Float> data = Statify.getDancebilityPlaylistsData(playlists_num);
        int binsNum = (int) Math.cbrt(data.size());
        String title = String.format("Danceability Histogram of your %d playlists", playlists_num);
        String xTitle = "Mean";
        String yTitle = "Tracks count";
        String seriesName = "Tracks in your playlists";
        List<Double> ydata = new ArrayList<>();
        List<Double> xdata = new ArrayList<>();
        try {
            Histogram histogram = new Histogram(data, binsNum);
            ydata = histogram.getyAxisData();
            xdata = histogram.getxAxisData();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        for (int i = 0; i < xdata.size(); i++) {
            double newValue = (double) Math.round(xdata.get(i) * 100d) / 100d;
            xdata.set(i, newValue);
        }
        CategoryChart chart = getBarChart(xdata, ydata, title, seriesName, xTitle, yTitle);

        return new XChartPanel<CategoryChart>(chart);
    }

    public static JScrollPane createTopTracksPanel(int tracksNumber, String timeRange) {
        List<Dictionary<String, String>> data = Statify.currentUser.getTopTracksInfoList(tracksNumber, timeRange);
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(data.size() + 1, 3, 10, 10)); // n+1 rows, 2 columns, 10px between each row and
                                                                     // ech column
        // lepiej to zrobić GridBagLayout bo można dodawać kolumny różnych szerokości
        JLabel mainPositionLabel = new JLabel("Position");
        panel.add(mainPositionLabel);
        JLabel mainTitleLabel = new JLabel("Title");
        panel.add(mainTitleLabel);
        JLabel mainArtistLabel = new JLabel("Artist");
        panel.add(mainArtistLabel);
        JLabel mainAlbumLabel = new JLabel("Album");
        panel.add(mainAlbumLabel);
        // Adding 10 string fields to panel
        for (int i = 0; i < data.size(); i++) {
            JLabel positionLabel = new JLabel(Integer.toString(i + 1));
            panel.add(positionLabel);

            JLabel titleLabel = new JLabel(data.get(i).get("name"));
            panel.add(titleLabel);

            JLabel artistLabel = new JLabel(data.get(i).get("artist"));
            panel.add(artistLabel);

            JLabel albumLabel = new JLabel(data.get(i).get("album"));
            panel.add(albumLabel);
        }
        JScrollPane scrollPane = new JScrollPane(panel); // Create a JScrollPane and pass in the JPanel
        scrollPane.setPreferredSize(new java.awt.Dimension(800, 600));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // scroll_panel.add(panel);
        // JPanel main_panel = new JPanel();

        // main_panel.add(scroll_panel);

        return scrollPane;
    }

    private static List<Float> getLoudnessPlaylistsData(int limit) {
        List<String> playlistsIds = Statify.currentUser.getPlaylistsIds(limit);
        List<String> tracksIds = new ArrayList<>();
        for (String pId : playlistsIds) {
            tracksIds.addAll(Statify.currentUser.getPlaylistTracksIds(pId));
        }
        System.out.println(tracksIds);
        List<Float> danceabilityTable = new ArrayList<>();
        Dictionary<String, List<Float>> audioFeatures = Statify.currentUser
                .getAllTracksAudioFeatures(tracksIds.toArray(new String[0]));
        // System.out.println(audioFeatures);
        try {
            danceabilityTable.addAll(audioFeatures.get("loudness"));
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return danceabilityTable;
    }

    public static JPanel getLoudnessHistogram() {
        List<Float> data = Statify.getLoudnessPlaylistsData(playlists_num);
        int binsNum = (int) Math.cbrt(data.size());
        String title = String.format("Loudness Histogram of your %d playlists", playlists_num);
        String xTitle = "Mean";
        String yTitle = "Tracks count";
        String seriesName = "Tracks in your playlists";
        List<Double> ydata = new ArrayList<>();
        List<Double> xdata = new ArrayList<>();
        try {
            Histogram histogram = new Histogram(data, binsNum);
            ydata = histogram.getyAxisData();
            xdata = histogram.getxAxisData();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        for (int i = 0; i < xdata.size(); i++) {
            double newValue = (double) Math.round(xdata.get(i) * 100d) / 100d;
            xdata.set(i, newValue);
        }
        CategoryChart chart = getBarChart(xdata, ydata, title, seriesName, xTitle, yTitle);

        return new XChartPanel<CategoryChart>(chart);
    }

    private static List<Float> getAcousticnessPlaylistsData(int limit) {
        List<String> playlistsIds = Statify.currentUser.getPlaylistsIds(limit);
        List<String> tracksIds = new ArrayList<>();
        for (String pId : playlistsIds) {
            tracksIds.addAll(Statify.currentUser.getPlaylistTracksIds(pId));
        }
        System.out.println(tracksIds);
        List<Float> danceabilityTable = new ArrayList<>();
        Dictionary<String, List<Float>> audioFeatures = Statify.currentUser
                .getAllTracksAudioFeatures(tracksIds.toArray(new String[0]));
        // System.out.println(audioFeatures);
        try {
            danceabilityTable.addAll(audioFeatures.get("acousticness"));
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return danceabilityTable;
    }

    public static JPanel getAcousticnessHistogram() {
        List<Float> data = Statify.getAcousticnessPlaylistsData(playlists_num);
        int binsNum = (int) Math.cbrt(data.size());
        String title = String.format("Acousticness Histogram of your %d playlists", playlists_num);
        String xTitle = "Mean";
        String yTitle = "Tracks count";
        String seriesName = "Tracks in your playlists";
        List<Double> ydata = new ArrayList<>();
        List<Double> xdata = new ArrayList<>();
        try {
            Histogram histogram = new Histogram(data, binsNum);
            ydata = histogram.getyAxisData();
            xdata = histogram.getxAxisData();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        for (int i = 0; i < xdata.size(); i++) {
            double newValue = (double) Math.round(xdata.get(i) * 100d) / 100d;
            xdata.set(i, newValue);
        }
        CategoryChart chart = getBarChart(xdata, ydata, title, seriesName, xTitle, yTitle);

        return new XChartPanel<CategoryChart>(chart);
    }

    public static void main(String[] args) {
        Statify statify = new Statify();
        // statify.getDanceabilityHistogram(Arrays.asList(1.4f, 5.0f, 6.4f), 2);
        JFrame frame = new JFrame("Song Form");

        // SORRY ZA SYF ALE PROSZĘ TEGO NIE USUWAĆ NA RAZIE!

        // List<Dictionary<String, String>> dictionaryList = new ArrayList<>();

        // Dictionary<String, String> dictionary1 = new Hashtable<>();
        // dictionary1.put("name", "hello");
        // dictionary1.put("artist", "adele");
        // dictionary1.put("album", "waheteverr");
        // dictionaryList.add(dictionary1);

        // Dictionary<String, String> dictionary2 = new Hashtable<>();
        // dictionary2.put("name", "bebe");
        // dictionary2.put("artist", "adadadasele");
        // dictionary2.put("album", "verr");
        // dictionaryList.add(dictionary2);

        // Dictionary<String, String> dictionary3 = new Hashtable<>();
        // dictionary3.put("name", "sbglo");
        // dictionary3.put("artist", "tet");
        // dictionary3.put("album", "wahefgsdteverr");
        // dictionaryList.add(dictionary3);

        // String accessToken =
        // "BQAVQB6_0uvh43j3ABrWlHglI0R7kolM1Ph8cJjKz3RuwHyTeaujE85q8-BVfwILJIf10dC82ZX_D3lClNZcIGp9n_jHknNtmjVhWazVwug1vJZ4toPr4rFROwfA9AXf03TvAD5dk8mHovCA3o1onUH6QprhzkxKCaxK_bV4uHmBjv5l3Zg3F_SYG3Fsd2qQ9HcieS1UO7P-55eOQyNoMrMv1srluX5t9px1tJP-R70DLo1tIhshDjdmt3fpS7DySCdAReQ9oEt18eybwsfV1jjsiuaLii-dnpGe0729uNwQiAV9qiDT8ol8N6Ablk1UKJaQP1vc";
        // User user = new User(accessToken);
        // LoginWindow frame1 = new LoginWindow();
        // frame1.setTitle("Login Form");
        // frame1.setVisible(true);
        // frame1.setBounds(10, 10, 370, 600);
        // frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame1.setResizable(false);
        User user = new User(
                "BQARqoieJea1CkOzBNWAVMwW8qUdrT-PlzEVODfhSme66vkAwoKP7C975qi5l07uA9EYYfyP8VUFWDdzkJoeLKWRH9JYkuKucO_lRtCMFsELhcGZd1Ur-63o5UT5RLatXraOeChOWVRIhb7NWL2nvgBzSIfrMmiq79_ZMnyQWqXONaFez4Me7CRiQwzVzBj5kqReyZtKRc5aVeyh-qWIxHNrzgZo2llDwbiwIDMh0wQ1KyJ7x-FWXIGHpKp8sG8hLk1y0OHtw4QEWg4IrY3pxaUkdPSwEhoyt8yXp91wyrkZuotfRsu-sCgGkxzoubBD1_kRQIioGCN666noFOVZEQ");
        statify.setUser(user);
        String timeRange = "long_term";
        int tracksNumber = 50;

        // List<Dictionary<String, String>> dictionaryListFromMethod =
        // user.getTopTracksInfoList(tracksNumber, timeRange);

        JScrollPane scrollPane = createTopTracksPanel(tracksNumber, timeRange);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}
