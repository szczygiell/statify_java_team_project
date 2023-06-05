package com.statify;

import org.apache.commons.compress.archivers.zip.UnsupportedZipFeatureException.Feature;
import org.checkerframework.checker.units.qual.C;
import org.checkerframework.checker.units.qual.radians;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;

import com.madgag.gif.fmsware.GifDecoder;

import java.awt.Component;
import java.util.Comparator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.util.Rotation;
import java.util.Map;
import java.util.AbstractMap;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

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

import javax.swing.JCheckBox;
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
import java.util.HashMap;
import javax.swing.BorderFactory;

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
        RadarChart radarChart = new RadarChartBuilder().build();
        radarChart.getStyler().setToolTipsEnabled(true);
        radarChart.getStyler().setLegendPosition(LegendPosition.InsideSW);
        radarChart.setRadiiLabels(radiiLabels);
        for (int i = 0; i < dataSeriesNames.length; i++) {
            radarChart.addSeries(dataSeriesNames[i], dataSeries.get(i));
        }
        RadarStyler radarStyler = radarChart.getStyler();
        Color[] colorSeries = {
                new Color(170, 246, 131, 150),
                new Color(238, 96, 85, 150),
                new Color(255, 155, 133, 150),
                new Color(255, 217, 125, 150),
                new Color(96, 211, 148, 150),
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
        panel.setBackground(Color.BLACK);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel mainPositionLabel = new JLabel("   Position");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(mainPositionLabel, c);

        JLabel mainTitleLabel = new JLabel("  Title");
        c.gridx = 1;
        c.gridy = 0;
        panel.add(mainTitleLabel, c);

        JLabel mainArtistLabel = new JLabel("  Artist");
        c.gridx = 2;
        c.gridy = 0;
        panel.add(mainArtistLabel, c);

        JLabel mainAlbumLabel = new JLabel("  Album");
        c.gridx = 3;
        c.gridy = 0;
        panel.add(mainAlbumLabel, c);

        // Dodawanie 10 pól tekstowych do panelu
        for (int i = 0; i < data.size(); i++) {
            JLabel positionLabel = new JLabel("    " + Integer.toString(i + 1));
            c.gridx = 0;
            c.gridy = i + 1;
            panel.add(positionLabel, c);

            JLabel titleLabel = new JLabel("    " + data.get(i).get("name"));
            c.gridx = 1;
            c.gridy = i + 1;
            panel.add(titleLabel, c);

            JLabel artistLabel = new JLabel("    " + data.get(i).get("artist"));
            c.gridx = 2;
            c.gridy = i + 1;
            panel.add(artistLabel, c);

            JLabel albumLabel = new JLabel("    " + data.get(i).get("album"));
            c.gridx = 3;
            c.gridy = i + 1;
            panel.add(albumLabel, c);

            // Co drugi wiersz ma szary kolor tła
            if (i % 2 == 1) {
                positionLabel.setBackground(Color.BLACK);
                titleLabel.setBackground(Color.BLACK);
                artistLabel.setBackground(Color.BLACK);
                albumLabel.setBackground(Color.BLACK);
            } else {
                positionLabel.setBackground(Color.DARK_GRAY);
                titleLabel.setBackground(Color.DARK_GRAY);
                artistLabel.setBackground(Color.DARK_GRAY);
                albumLabel.setBackground(Color.DARK_GRAY);
            }

            positionLabel.setOpaque(true);
            titleLabel.setOpaque(true);
            artistLabel.setOpaque(true);
            albumLabel.setOpaque(true);
        }

        // Ustawienie koloru tekstu na biały dla wszystkich napisów
        mainPositionLabel.setForeground(Color.WHITE);
        mainTitleLabel.setForeground(Color.WHITE);
        mainArtistLabel.setForeground(Color.WHITE);
        mainAlbumLabel.setForeground(Color.WHITE);
        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setForeground(Color.WHITE);
            }
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new java.awt.Dimension(800, 600));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }

    public static JScrollPane createTopArtistsPanel(int artistsNumber, String timeRange) {
        List<Dictionary<String, String>> data = Statify.currentUser.getTopArtistsInfoList(artistsNumber, timeRange);
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel mainPositionLabel = new JLabel("   Position");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(mainPositionLabel, c);

        JLabel mainArtistLabel = new JLabel("  Artist");
        c.gridx = 1;
        c.gridy = 0;
        panel.add(mainArtistLabel, c);

        JLabel mainGenresLabel = new JLabel("  Genres");
        c.gridx = 2;
        c.gridy = 0;
        panel.add(mainGenresLabel, c);

        // Dodawanie 10 pól tekstowych do panelu
        for (int i = 0; i < data.size(); i++) {
            JLabel positionLabel = new JLabel("    " + Integer.toString(i + 1));
            c.gridx = 0;
            c.gridy = i + 1;
            panel.add(positionLabel, c);

            JLabel artistLabel = new JLabel("    " + data.get(i).get("name"));
            c.gridx = 1;
            c.gridy = i + 1;
            panel.add(artistLabel, c);

            JLabel genresLabel = new JLabel("    " + data.get(i).get("genres"));
            c.gridx = 2;
            c.gridy = i + 1;
            panel.add(genresLabel, c);

            // Co drugi wiersz ma szary kolor tła
            if (i % 2 == 1) {
                positionLabel.setBackground(Color.BLACK);
                artistLabel.setBackground(Color.BLACK);
                genresLabel.setBackground(Color.BLACK);

            } else {
                positionLabel.setBackground(Color.DARK_GRAY);
                artistLabel.setBackground(Color.DARK_GRAY);
                genresLabel.setBackground(Color.DARK_GRAY);
            }

            positionLabel.setOpaque(true);
            artistLabel.setOpaque(true);
            genresLabel.setOpaque(true);
        }

        // Ustawienie koloru tekstu na biały dla wszystkich napisów
        mainPositionLabel.setForeground(Color.WHITE);
        mainArtistLabel.setForeground(Color.WHITE);
        mainGenresLabel.setForeground(Color.WHITE);
        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setForeground(Color.WHITE);
            }
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new java.awt.Dimension(800, 600));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }

    public static JScrollPane createTopArtistsPanel2(int artistsNumber, String timeRange) {
        List<Dictionary<String, String>> data = Statify.currentUser.getTopArtistsInfoList(artistsNumber, timeRange);
        JPanel panel = new JPanel();
        // System.out.println(data.size());
        panel.setLayout(new GridBagLayout()); // n+1 rows, 2 columns, 10px between each row and
        GridBagConstraints c = new GridBagConstraints(); // ech column
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
        c.fill = GridBagConstraints.BOTH + 10;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(mainGenresLabel, c);

        // Adding 10 string fields to panel
        for (int i = 0; i < data.size(); i++) {
            JLabel positionLabel = new JLabel(Integer.toString(i + 1));
            c.fill = GridBagConstraints.BOTH + 10;
            c.gridx = 0;
            c.gridy = i + 1;
            panel.add(positionLabel, c);

            JLabel nameLabel = new JLabel(data.get(i).get("name"));
            c.fill = GridBagConstraints.BOTH + 10;
            c.gridx = 1;
            c.gridy = i + 1;
            panel.add(nameLabel, c);

            JLabel genresLabel = new JLabel(data.get(i).get("genres"));
            c.fill = GridBagConstraints.BOTH + 10;
            c.gridx = 2;
            c.gridy = i + 1;
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
        Dictionary<String, List<Double>> MeansFeaturesDictionary;
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
            System.out.println("acousticness= " + meanPlaylistFeatures.get("acousticness"));
            List<Double> valuesList = new ArrayList<>();

            for (FeatureName featureName : features) {
                Float value = meanPlaylistFeatures.get(featureName.keyName);
                valuesList.add(value.doubleValue());
            }
            double[] dataSeries = valuesList.stream().mapToDouble(Double::doubleValue).toArray();
            dataSeriesList.add(dataSeries);
        }
        String[] featuresNamesArray = new String[features.length];
        for (int i = 0; i < features.length; i++) {
            featuresNamesArray[i] = features[i].keyName;
        }

        RadarChart radarChart = getRadarChart(
                featuresNamesArray, playlistNames, dataSeriesList,
                "Your playlists Features RadarChart");

        return new XChartPanel<RadarChart>(radarChart);
    }

    // TODO wykres z wybranych playlist
    public static JPanel getAudioFeatureHistogram(FeatureName feature) {
        List<Float> data = getFeaturePlaylistData(playlists_num, feature);
        int binsNum = (int) Math.cbrt(data.size());
        String title = feature.keyName + String.format(" Histogram of your %d playlists", playlists_num);
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

    public static JPanel getInstrumentalHistogram() {
        return getAudioFeatureHistogram(FeatureName.INSTRUMENTALNESS);
    }

    public static JPanel getEnergyHistogram() {
        return getAudioFeatureHistogram(FeatureName.ENERGY);
    }

    public static ObjectSelectionPanel getPlaylistsSelectableList(Object[] objects) {
        ObjectSelectionPanel selectionPanel = new ObjectSelectionPanel(objects);
        return selectionPanel;
    }

    private static JScrollPane createRecommendationsPanel(HashMap<String, String> recomendedTracks) {
        JPanel panel = new JPanel();
        Color darkGreyColor = new Color(44, 51, 51);

        panel.setLayout(new GridLayout(recomendedTracks.size() + 1, 2, 10, 10)); // n+1 rows, 2 columns, 10px between
                                                                                 // each row and
        JLabel mainTitleLabel = new JLabel("TITLE");
        mainTitleLabel.setForeground(new Color(255, 255, 255));
        mainTitleLabel.setFont(new Font("Noto Sans CJK JP", Font.BOLD, 25));
        mainTitleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        panel.add(mainTitleLabel);
        panel.add(mainTitleLabel);
        JLabel mainArtistLabel = new JLabel("ARTIST");
        mainArtistLabel.setFont(new Font("Noto Sans CJK JP", Font.BOLD, 25));
        mainArtistLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        mainArtistLabel.setForeground(new Color(255, 255, 255));
        panel.add(mainArtistLabel);
        panel.add(mainArtistLabel);
        // Adding 10 string fields to panel
        for (String trackName : recomendedTracks.keySet()) {
            JLabel titleLabel = new JLabel(trackName);
            titleLabel.setForeground(new Color(255, 255, 255));
            titleLabel.setFont(new Font("Noto Sans CJK JP", Font.PLAIN, 15));
            titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            panel.add(titleLabel);
            JLabel artistLabel = new JLabel(recomendedTracks.get(trackName));
            artistLabel.setForeground(new Color(255, 255, 255));
            artistLabel.setFont(new Font("Noto Sans CJK JP", Font.PLAIN, 15));
            artistLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            panel.add(artistLabel);

        }
        panel.setBackground(darkGreyColor);
        panel.setAlignmentX(10);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JScrollPane scrollPane = new JScrollPane(panel); // Create a JScrollPane and pass in the JPanel
        scrollPane.setPreferredSize(new java.awt.Dimension(800, 600));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    public static JScrollPane getRecommendationsPanelByTopTracks(String timeDuration) {
        HashMap<String, String> recommendations = currentUser.getRecomendationsByTopTracks(timeDuration);
        return Statify.createRecommendationsPanel(recommendations);
    }

    public static JScrollPane getRecommendationsPanelByTopArtists(String timeDuration) {
        HashMap<String, String> recommendations = currentUser.getRecomendationsByTopArtists(timeDuration);
        return Statify.createRecommendationsPanel(recommendations);
    }

    public static JPanel createPieChart2(Dictionary<String, Integer> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        Enumeration<String> keys = data.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            int value = data.get(key);
            dataset.setValue(key, value);
        }

        JFreeChart chart = ChartFactory.createPieChart("Your Top 10 Genres", dataset, true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionOutlinesVisible(false);
        plot.setLabelGenerator(null);
        plot.setShadowPaint(null);
        plot.setLabelBackgroundPaint(new Color(220, 220, 220));
        plot.setBackgroundPaint(Color.WHITE);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));

        return chartPanel;
    }

    public static JPanel createPieChart(Dictionary<String, Integer> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        Enumeration<String> keys = data.keys();
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            int value = data.get(key);
            sortedEntries.add(new AbstractMap.SimpleEntry<>(key, value));
        }

        sortedEntries.sort(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart3D("Your Top 10 Genres", dataset, true, true, false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setSectionOutlinesVisible(false);
        plot.setShadowPaint(null);
        plot.setLabelBackgroundPaint(new Color(220, 220, 220));

        DecimalFormat df = new DecimalFormat("0.00%");
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} - {2}", NumberFormat.getNumberInstance(), df));

        chart.setBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setStartAngle(90);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.8f);
        plot.setCircular(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));

        return chartPanel;
    }

    public static void main(String[] args) {
        Statify statify = new Statify();
        // statify.getDanceabilityHistogram(Arrays.asList(1.4f, 5.0f, 6.4f), 2);
        // JFrame frame = new JFrame("Song Form");

        User user = new User(
                "BQBjOs2YqOU7i2q1hnD4OBzpoqPrwLpZJQuL6c6jEOBdNqlTgPjA0yl36txSQLgEjLyXzGuJDDwFvYCfSQm-0GTZCw5tQNujpaqWTVfX8LbiYqahSVBAUR8Nma_GkDa2bOOZizyYookq1bb3D2FKjT-FwWl78EObC0PZgYPjh6T-cMHuA0NrbGzdtRmAaOqwioXSxyWCeAavewe-cwicA7OPXtwGpbj338zeZQ3lJ8OKN5DTTLvjuH9I-cV-uqGulMPJUi5Ab272EvrGoYxSDu9kz4VaomXTTPQ1AhTYqVrtt_NwmwP0Hs6Etv6H2n7r6Co3q_Me-QKpz7E8LZyavdpf");
        statify.setUser(user);
        String timeRange = "long_term";
        int artistsNumber = 40;

        Dictionary<String, Integer> dict = user.getTopGenresDict(artistsNumber, timeRange);
        Dictionary<String, Integer> outDict = user.analyzeDictionary(dict);
        Enumeration<String> keys = outDict.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            Integer value = outDict.get(key);
            System.out.println("Klucz: " + key + ", Wartość: " + value);
        }
        // JScrollPane scrollPane = createTopArtistsPanel(artistsNumber, timeRange);
        // frame.add(scrollPane);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.pack();
        // frame.setVisible(true);
        JFrame frame = new JFrame("Wykres kołowy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel chartPanel = createPieChart(outDict);
        frame.getContentPane().add(chartPanel);

        frame.pack();
        frame.setVisible(true);

    }

}
