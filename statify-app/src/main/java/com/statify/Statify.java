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

    public JPanel createTopTracksPanel(int tracksNum, List<Dictionary<String, String>> data) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(tracksNum+1, 3, 10, 10)); // n+1 rows, 2 columns, 10px between each row and ech column
        JLabel mainPositionLabel = new JLabel("Position");
            panel.add(mainPositionLabel);
        JLabel mainTitleLabel = new JLabel("Title");
            panel.add(mainTitleLabel);
        JLabel mainArtistLabel = new JLabel("Artist");
            panel.add(mainArtistLabel);
        JLabel mainAlbumLabel = new JLabel("Album");
            panel.add(mainAlbumLabel);
        // Adding 10 string fields to panel
        for (int i = 0; i < tracksNum; i++) {
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

        String accessToken = "BQDhj22beeKO4Yl02SXA_cIppBdJ9-LKrUgoRIko0QTp5Zn2ltDFFjvpXMcvKU9ipzLPK0IuMjvGaFmx6bFmGVOc47NbaYdhJarWcNK_KXEe0sMnLMH1b_XPrVKXh52Bcfp3hcbtDHGfk2xaCLE8ihXA3b1lidI0lLR_Bi-LMFKeZv9Dhn5WKyk1WFJ_vy1ehxC8BMb7TR4G87Mf0jyjoIm233Xgc4Q7Z6T2_dX46_jjcu4_lkN_IpHCpW6VUv2pNH_1rL7LF2_NSuSD3zBjjIvFGM9xR6M1L_fMeAzmrrQpati7dkovbe2_dAIp3jjVInYTz12l0IzFnHZ9ixDjNJGnoA";
        User user = new User(accessToken);
        String timeRange = "medium_term";
        int tracksNumber = 50;

        List<Dictionary<String, String>> dictionaryListFromMethod = user.getTopTracksInfoList(tracksNumber, timeRange);

        frame.add(statify.createTopTracksPanel(tracksNumber, dictionaryListFromMethod));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
