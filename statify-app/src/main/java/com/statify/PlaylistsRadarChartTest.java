package com.statify;

import java.util.HashMap;

import javax.management.ValueExp;

import org.knowm.xchart.XChartPanel;
import org.checkerframework.checker.fenum.qual.FenumTop;
import org.knowm.xchart.RadarChart;

public class PlaylistsRadarChartTest {

    public static void showPlaylistRadarChart(String token) {
        String user_token = token;
        User user = new User(user_token);
        int limit = 1;

        Statify.setUser(user);
        Statify.SetPlaylistsNum(limit);
        XChartPanel<RadarChart> chartPanel = Statify.getTracksRadarChartFromPlaylists();
        TopLevelWindow.createChartFrame(chartPanel);
    }

    public static void featureNameTest() {
        for (FeatureName v : FeatureName.values()) {
            System.out.println(v.keyName);
        }
    }

    public static void main(String[] args) {
        String token = "BQAqH-VlgiPx8COx6hqj1UkUsJ2Px2qrQ_zlRr7w2vV1HXTMlhPssFmJoVF5XHFLUWsXF2mP0Eeo3tNi2LD_AwBbURETbhlRH5evjMijfUL5iZ7667v1XLAG5HpNOwY3ws66SbMJm5tLI8OMuIFc6DxMQ3CHwhPTLlTiHlWAuHHILhHjKd5jd12xF3IknVdT2Rulpzrwo_qAjF_CmIGMGrluGQy6HbLcIc2EMqVo317if6pCKEYrwvCgSiqJZdOnJpU_Vv_jeJPhx5q2OLAkjt7a5eZuSJmJoV0idMl__fldNagws04DNFDmTn1u0QU4ZTShLC8";
        showPlaylistRadarChart(token);
        // featureNameTest();

    }
}
