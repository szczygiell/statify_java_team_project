package com.statify;

import org.apache.commons.compress.archivers.zip.UnsupportedZipFeatureException.Feature;
import org.checkerframework.checker.units.qual.radians;
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
import java.util.HashMap;
import java.lang.Math;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import java.util.Hashtable;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;
import org.knowm.xchart.RadarChart;
import org.knowm.xchart.RadarChartBuilder;
import org.knowm.xchart.style.RadarStyler;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.util.Enumeration;

public class Statify {

    public static User currentUser;
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

    private static RadarChart getRadarChart(String[] radiiLabels,
            String[] dataSeriesNames,
            List<double[]> dataSeries,
            String title) {

        RadarChartBuilder radarChartBuilder = new RadarChartBuilder();
        RadarChart radarChart = radarChartBuilder.build();
        radarChart.setRadiiLabels(radiiLabels);
        for (int i = 0; i < dataSeriesNames.length; i++) {
            radarChart.addSeries(dataSeriesNames[i], dataSeries.get(i));
        }
        RadarStyler radarStyler = radarChart.getStyler();
        Color[] colorSeries = {
                new Color(170, 246, 131, 150),
                new Color(238, 96, 85, 230),
                new Color(255, 155, 133, 80),
                new Color(255, 217, 125, 100),
                new Color(96, 211, 148, 200),
        };
        radarStyler.setSeriesColors(colorSeries);
        Font monsterratFont = new Font("Noto Sans CJK JP", Font.PLAIN, 25);
        radarStyler.setBaseFont(monsterratFont);
        radarStyler.setRadiiTitleFont(monsterratFont);
        radarStyler.setLegendFont(monsterratFont);
        return radarChart;
    }

    public static JScrollPane createTopTracksPanel(int tracksNumber, String timeRange) {
        List<Dictionary<String, String>> data = Statify.currentUser.getTopTracksInfoList(tracksNumber, timeRange);
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout()); // n+1 rows, 2 columns, 10px between each row and
        GridBagConstraints c = new GridBagConstraints();                                                             // ech column
        // lepiej to zrobić GridBagLayout bo można dodawać kolumny różnych szerokości

        panel.setLayout(new GridBagLayout()); // n+1 rows, 2 columns, 10px between each row and
                                                                     // ech column
        JLabel mainPositionLabel = new JLabel("Position");
        c.fill = GridBagConstraints.BOTH + 10;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(mainPositionLabel, c);
        panel.add(mainPositionLabel, c);
        JLabel mainTitleLabel = new JLabel("Title");
        c.fill = GridBagConstraints.BOTH + 10;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(mainTitleLabel, c);
        panel.add(mainTitleLabel, c);
        JLabel mainArtistLabel = new JLabel("Artist");
        c.fill = GridBagConstraints.BOTH + 10;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(mainArtistLabel, c);
        panel.add(mainArtistLabel, c);
        JLabel mainAlbumLabel = new JLabel("Album");
        c.fill = GridBagConstraints.BOTH + 10;
        c.gridx = 3;
        c.gridy = 0;
        panel.add(mainAlbumLabel, c);
        panel.add(mainAlbumLabel, c);
        // Adding 10 string fields to panel
        for (int i = 0; i < data.size(); i++) {
            JLabel positionLabel = new JLabel(Integer.toString(i + 1));
            c.fill = GridBagConstraints.BOTH + 10;
            c.gridx = 0;
            c.gridy = i+1;
            panel.add(positionLabel, c);

            JLabel titleLabel = new JLabel(data.get(i).get("name"));
            c.fill = GridBagConstraints.BOTH + 10;
            c.gridx = 1;
            c.gridy = i+1;
            panel.add(titleLabel, c);

            JLabel artistLabel = new JLabel(data.get(i).get("artist"));
            c.fill = GridBagConstraints.BOTH + 10;
            c.gridx = 2;
            c.gridy = i+1;
            panel.add(artistLabel, c);

            JLabel albumLabel = new JLabel(data.get(i).get("album"));
            c.fill = GridBagConstraints.BOTH + 10;
            c.gridx = 3;
            c.gridy = i+1;
            panel.add(albumLabel, c);
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

    public static JScrollPane createTopArtistsPanel(int artistsNumber, String timeRange) {
        List<Dictionary<String, String>> data = Statify.currentUser.getTopArtistsInfoList(artistsNumber, timeRange);
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout()); // n+1 rows, 2 columns, 10px between each row and
        GridBagConstraints c = new GridBagConstraints();                                                             // ech column
        // lepiej to zrobić GridBagLayout bo można dodawać kolumny różnych szerokości
        JLabel mainPositionLabel = new JLabel("Position");
        c.fill = GridBagConstraints.BOTH + 10;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(mainPositionLabel, c);
        JLabel mainArtistLabel = new JLabel("Artist");
        c.fill = GridBagConstraints.BOTH + 10;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(mainArtistLabel, c);
        JLabel mainGenresLabel = new JLabel("Genres");
        c.fill = GridBagConstraints.BOTH +10;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(mainGenresLabel, c);

        // Adding 10 string fields to panel
        for (int i = 0; i < data.size(); i++) {
            JLabel positionLabel = new JLabel(Integer.toString(i + 1));
            c.fill = GridBagConstraints.BOTH+10;
            c.gridx = 0;
            c.gridy = i+1;
            panel.add(positionLabel, c);

            JLabel nameLabel = new JLabel(data.get(i).get("name"));
            c.fill = GridBagConstraints.BOTH+10;
            c.gridx = 1;
            c.gridy = i+1;
            panel.add(nameLabel, c);

            JLabel genresLabel = new JLabel(data.get(i).get("genres"));
            c.fill = GridBagConstraints.BOTH+10;
            c.gridx = 2;
            c.gridy = i+1;
            panel.add(genresLabel, c);

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

    public static List<Float> getFeaturePlaylistData(int limit, FeatureName feature) {
        List<String> playlistsIds = Statify.currentUser.getPlaylistsIds(limit);
        List<String> tracksIds = new ArrayList<>();
        for (String pId : playlistsIds) {
            tracksIds.addAll(Statify.currentUser.getPlaylistTracksIds(pId));
        }
        List<Float> data = new ArrayList<>();
        Dictionary<String, List<Float>> audioFeatures = Statify.currentUser
                .getAllTracksAudioFeatures(tracksIds.toArray(new String[0]));
        try {
            data.addAll(audioFeatures.get(feature.keyName));
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return data;
    }

    public static Float getMaxAbsoluteValue(List<Float> inputList) {
        float maxAbsoluteValue = 0;
        for (float value : inputList) {
            float absoluteValue = Math.abs(value);
            if (absoluteValue > maxAbsoluteValue) {
                maxAbsoluteValue = absoluteValue;
            }
        }
        return maxAbsoluteValue;
    }

    private static Dictionary<String, Float> MeanFeatureValues(
            Dictionary<String, List<Float>> tracksAudioFeaturesInput) {
        Dictionary<String, Float> meanAudioFeaturesDict = new Hashtable<>();

        Enumeration<String> keys = tracksAudioFeaturesInput.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            List<Float> values = tracksAudioFeaturesInput.get(key);
            Float maxValue = getMaxAbsoluteValue(values);
            float sum = 0;
            for (Float value : values) {
                sum += value;
            }
            float mean = sum / values.size();
            if (key.equals(FeatureName.LOUDNESS.keyName)) {
                Float maxLoudness = Math.max(15.0f, maxValue);
                mean = -mean / maxLoudness;
            }
            meanAudioFeaturesDict.put(key, mean);
        }
        return meanAudioFeaturesDict;
    }

    private static Dictionary<String, List<Float>> sliceDictionary(
            Dictionary<String, List<Float>> inputDictionary,
            FeatureName[] keysToKeep) {
        Dictionary<String, List<Float>> slicedDictionary = new Hashtable<>();
        for (FeatureName key : keysToKeep) {
            if (inputDictionary.get(key.keyName) != null) {
                List<Float> values = inputDictionary.get(key.keyName);
                slicedDictionary.put(key.keyName, values);
            }
        }
        return slicedDictionary;
    }

    private static double[] convertValuesToDoubleArray(Dictionary<String, Float> dictionary) {
        int size = dictionary.size();
        double[] valuesArray = new double[size];

        Enumeration<Float> values = dictionary.elements();
        int index = 0;
        while (values.hasMoreElements()) {
            float floatValue = values.nextElement();
            double doubleValue = (double) floatValue;
            valuesArray[index] = doubleValue;
            index++;
        }

        return valuesArray;
    }

    public static XChartPanel<RadarChart> getTracksRadarChartFromPlaylists(HashMap<String, String> playlistsHashMap, 
            FeatureName[] features) {
        List<double[]> dataSeriesList = new ArrayList();
        String[] playlistNames = playlistsHashMap.keySet().toArray(new String[0]);
        for (String playlistId : playlistsHashMap.values()) {
            // get playlist's dict track_id : [features list]
            Dictionary<String, List<Float>> playlistTracksAllAudioFeatures = currentUser
                    .getAllTracksAudioFeatures(currentUser.getPlaylistTracksIds(playlistId).toArray(new String[0]));
            // create same dict with only selected features
            Dictionary<String, List<Float>> playlistTracksAudioFeatures = sliceDictionary(
                    playlistTracksAllAudioFeatures, features);
            // convert dict to
            Dictionary<String, Float> meanPlaylistFeatures = MeanFeatureValues(playlistTracksAudioFeatures);
            double[] dataSeries = convertValuesToDoubleArray(meanPlaylistFeatures);
            dataSeriesList.add(dataSeries);
        }
        RadarChart radarChart = getRadarChart(FeatureName.toArray(features), playlistNames, dataSeriesList,
                "Your playlists Features RadarChart");

        return new XChartPanel<RadarChart>(radarChart);
    }

    // TODO wykres z wybranych playlist
    public static JPanel getAudioFeatureHistogram(FeatureName feature) {
        List<Float> data = getFeaturePlaylistData(playlists_num, feature);
        int binsNum = (int) Math.cbrt(data.size());
        String title = feature.keyName + String.format(" Histogram of your %o playlists", playlists_num);
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

    public static JPanel getAcousticnessHistogram() {
        return getAudioFeatureHistogram(FeatureName.ACOUSTICNESS);
    }

    public static JPanel getLoudnessHistogram() {
        return getAudioFeatureHistogram(FeatureName.LOUDNESS);
    }

    public static JPanel getDanceabilityHistogram() {
        return getAudioFeatureHistogram(FeatureName.DANCEABILITY);
    }

    public static JScrollPane getPlaylistsSelectableList() {
        HashMap<String, String> playlistsDictionary = currentUser.getPlaylistsHashMap();
        Object[] names = playlistsDictionary.keySet().toArray();

        JList<String> playlistsNamesList = new JList(names);

        playlistsNamesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        playlistsNamesList.setLayoutOrientation(JList.VERTICAL);
        playlistsNamesList.setCellRenderer(new CheckboxListCellRenderer());

        JScrollPane listScroller = new JScrollPane(playlistsNamesList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        JScrollPane scrollPane = new JScrollPane(playlistsNamesList);
        return scrollPane;
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
        //
        // "BQAVQB6_0uvh43j3ABrWlHglI0R7kolM1Ph8cJjKz3RuwHyTeaujE85q8-BVfwILJIf10dC82ZX_D3lClNZcIGp9n_jHknNtmjVhWazVwug1vJZ4toPr4rFROwfA9AXf03TvAD5dk8mHovCA3o1onUH6QprhzkxKCaxK_bV4uHmBjv5l3Zg3F_SYG3Fsd2qQ9HcieS1UO7P-55eOQyNoMrMv1srluX5t9px1tJP-R70DLo1tIhshDjdmt3fpS7DySCdAReQ9oEt18eybwsfV1jjsiuaLii-dnpGe0729uNwQiAV9qiDT8ol8N6Ablk1UKJaQP1vc";
        // User user = new User(accessToken);
        // LoginWindow frame1 = new LoginWindow();
        // frame1.setTitle("Login Form");
        // frame1.setVisible(true);
        // frame1.setBounds(10, 10, 370, 600);
        // frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame1.setResizable(false);

        User user = new User(
                "BQDYeRNDwTpM1UlGEBxGaDd6aHeypMSlbbEuIyYT_l0eHnbE3sWD3hYruH_zwmYjRiV6JrXdSCndBZVtHCot6m3TkzLy-KvFTOzOioxPbA2EtA7xY_lrP-Qjw00OSIVn-C-KuFi_cpfCK0UJTeNriMD9FSDpcF3nDRuXwkjKddyPD4d6lIQ2Mezu04knubswlI1bPQv3vWS8wb8Qrmr5NYVjwDKE4jX4jwtbC_UoQVyu1eL-PawlCK9YbRWd5MIosReBw3PiMaNDE5vENHQ3xBQpI3h-habc_Xc9UrCPFYRVYGJTrnKGTVNA4rSXLoaNg9JdBUcp");
        statify.setUser(user);
        String timeRange = "short_term";
        int artistsNumber = 10;

        // List<Dictionary<String, String>> dictionaryListFromMethod =
        // user.getTopTracksInfoList(tracksNumber, timeRange);
        // List<Dictionary<String, String>> dictionaryListFromMethod =
        // user.getTopTracksInfoList(tracksNumber, timeRange);

        JScrollPane scrollPane = createTopArtistsPanel(artistsNumber, timeRange);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}
