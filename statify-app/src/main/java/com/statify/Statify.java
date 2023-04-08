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
        String title = String.format("Danceability Histogram of your %o playlists", playlists_num);
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

    public JPanel createTopTracksPanel(List<Dictionary<String, String>> data) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(data.size()+1, 3, 10, 10)); // n+1 rows, 2 columns, 10px between each row and ech column
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
            JLabel positionLabel = new JLabel(Integer.toString(i+1));
            panel.add(positionLabel);

            JLabel titleLabel = new JLabel(data.get(i).get("name"));
            panel.add(titleLabel);

            JLabel artistLabel = new JLabel(data.get(i).get("artist"));
            panel.add(artistLabel);

            JLabel albumLabel = new JLabel(data.get(i).get("album"));
            panel.add(albumLabel);
        }

        return panel;
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
        //System.out.println(audioFeatures);
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
        String title = String.format("Loudness Histogram of your %o playlists", playlists_num);
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
        //System.out.println(audioFeatures);
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
        String title = String.format("Acousticness Histogram of your %o playlists", playlists_num);
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

        String accessToken = "BQAF2bHTIeOHgKhI_sj1D9aE2cLyAG_ODfhXUEtIwytjQA7bIlMD5c1sIiYityH7KzALN05l4ufO-mj-sfa-NjS3AVRR-ujjmxy1QuQ3SLf2vkQM4W20N5JQliQ1PjC7cwcZiCzEMZ_WmW0wTtjdplkw_AtuTX4AHYz3e4yGOWUZz86RtBcz1vnBxOqoNzu6y5KFbaLgDOHeGOV_w7x0RLTjvLPqsQObJB_CxMCewa1tCiiDN0AdJPVVnosl6IK_8Cow4Rdrmu9aL-GJOPsT-6-bj4r6vsUhsGPF1nEycEbMIIishUOn0F12hWWNpmpcG783cPlKQBpWERPAeiiRw3vCMw";
        User user = new User(accessToken);
        String timeRange = "long_term";
        int tracksNumber = 20;

        List<Dictionary<String, String>> dictionaryListFromMethod = user.getTopTracksInfoList(tracksNumber, timeRange);

        frame.add(statify.createTopTracksPanel(dictionaryListFromMethod));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
