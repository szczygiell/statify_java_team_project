/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.statify;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MouseInfo;
import java.util.HashMap;
import javax.swing.Timer;

import org.apache.hc.core5.http.nio.support.AsyncServerFilterChainExchangeHandlerFactory;
import org.apache.http.params.CoreConnectionPNames;

/**
 *
 * @author mwewior
 * @author jmacuga
 * @author fszczygi
 * @author mkowale2
 *
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */

    public MainWindow() {
        int heightInit = 1080;
        int widthInit = 1920;

        initComponents(widthInit, heightInit);
        panelsSetDefault();
        this.setBounds(100, 100, 1060, 740);
        setTransparent();
        creditsPanel.setVisible(true);
        setMinimumSize(new java.awt.Dimension(1060, 740));
    }

    public final void panelsSetDefault() {
        timeButtonsPanel.setVisible(false);
        optionsPanel.setVisible(false);
        planeUpperPanel.setVisible(true);
        creditsPanel.setVisible(false);
        creditsLabel.setVisible(false);
        planeBasePanel.setVisible(true);
        actionPanel.removeAll();
        actionPanel.revalidate();
        actionPanel.repaint();
        genPanel.setVisible(false);
        genPanel.removeAll();
        genPanel.revalidate();
        genPanel.repaint();
        transPanel.setVisible(false);
    }

    public void setTransparent() {
        centrePanel.revalidate();
        centrePanel.repaint();
        actionButtonsPanel.setOpaque(false);
        optionsPanel.setOpaque(false);
        timeButtonsPanel.setOpaque(false);
        planeUpperPanel.setOpaque(false);
        actionPanel.setOpaque(false);
        creditsPanel.setOpaque(false);
        planeBasePanel.setOpaque(false);
        centrePanel.setVisible(true);
    }

    public void addNewPanel(JPanel newPanel) {
        actionPanel.removeAll();
        actionPanel.add(newPanel);
        actionPanel.revalidate();
        actionPanel.repaint();
        newPanel.setVisible(true);
    }

    public void addNewScrollPane(JScrollPane newScrollPane) {
        actionPanel.removeAll();
        actionPanel.add(newScrollPane);
        actionPanel.revalidate();
        actionPanel.repaint();
        newScrollPane.setVisible(true);
    }

    public final void upperPanelDefault() {
        timeButtonsPanel.setVisible(false);
        optionsPanel.setVisible(false);
        planeUpperPanel.setVisible(true);
    }

    public void infoWindowsHide() {
        infoWindow.setVisible(false);
        infoWindow1.setVisible(false);
        infoWindow2.setVisible(false);
        infoWindow3.setVisible(false);
        infoWindow4.setVisible(false);
        infoWindow5.setVisible(false);
        infoWindow6.setVisible(false);
    }

    public void timeButtonsPanelSetDefault() {
        java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        timeButtonsPanel.add(weeks4Button, c);
        weeks4Button.setVisible(true);
        c.gridx = 2;
        c.gridy = 0;
        timeButtonsPanel.add(months6Button, c);
        months6Button.setVisible(true);
        c.gridx = 4;
        c.gridy = 0;
        timeButtonsPanel.add(allTimeHistoryButton, c);
        allTimeHistoryButton.setVisible(true);
        numTracksTextField.setVisible(true);
        infoTextField.setVisible(true);
    }

    public void trackListGrapher(String timeRange) {
        String track_num = numTracksTextField.getText();
        try {
            int trNum = Integer.parseInt(track_num);
            int maxValue = Statify.currentUser.getMaxAmmount(trNum, timeRange, "tracks");
            if (trNum <= 0 || trNum > 50) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Invalid amount of tracks.\nValue must range between 1 and 50.");
            } else {
                if (trNum > maxValue) {
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "We can't show you " + trNum + " tracks.\n"
                                    + "In choosen period of time you have listened to "
                                    + maxValue + " tracks");
                }
                actionPanel.removeAll();
                upperPanelDefault();
                javax.swing.JScrollPane graph = Statify.createTopTracksPanel(trNum, timeRange);
                actionPanel.add(graph);
                graph.setVisible(true);
                timeButtonsPanel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of tracks has to be a whole number");
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of tracks has to be a whole number");

        }
    }

    public void artistListGrapher(String timeRange) {
        String artist_num = numTracksTextField.getText();
        try {
            int trNum = Integer.parseInt(artist_num);
            int maxValue = Statify.currentUser.getMaxAmmount(trNum, timeRange, "artists");
            if (trNum <= 0 || trNum > 50) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Invalid amount of artists\nValue must range between 1 and " + maxValue);
            } else {
                if (trNum > maxValue) {
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "We can't show you " + trNum + " artists.\n"
                                    + "In choosen period of time you have listened to "
                                    + maxValue + " artists");
                }
                actionPanel.removeAll();
                upperPanelDefault();
                javax.swing.JScrollPane graph = Statify.createTopArtistsPanel(trNum, timeRange);
                actionPanel.add(graph);
                graph.setVisible(true);
                timeButtonsPanel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of artists has to be a whole number");
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of artists has to be a whole number");
        }
    }

    public void genreListGrapher(String timeRange) {

        try {
            int maxValue = Statify.currentUser.getMaxAmmount(50, timeRange, "artists");
            actionPanel.removeAll();
            upperPanelDefault();
            User user = Statify.currentUser;
            java.util.Dictionary<String, Integer> dict = user.getTopGenresDict(maxValue, timeRange);
            java.util.Dictionary<String, Integer> outDict = user.analyzeDictionary(dict);
            javax.swing.JPanel graph = Statify.createPieChart(outDict);
            actionPanel.add(graph);
            graph.setVisible(true);
            timeButtonsPanel.setVisible(true);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of tracks has to be a whole number");
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of tracks has to be a whole number");

        }
    }

    public void chooseTypeTopList(String timeRange) {
        if (buttonFlag.equals("tracks")) {
            trackListGrapher(timeRange);
        } else if (buttonFlag.equals("artist")) {
            artistListGrapher(timeRange);
        } else if (buttonFlag.equals("genre")) {
            genreListGrapher(timeRange);
        } else {
            panelsSetDefault();
            addNewPanel(planeBasePanel);
        }
    }

    public void showInfoHint(java.awt.event.MouseEvent evt, String msg) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            JLabel hintinfoText = new JLabel(msg);
            hintinfoText.setBounds(0, 0, 400, 50);
            hintinfoText.setHorizontalAlignment(JLabel.CENTER);
            hintinfoText.setVerticalAlignment(JLabel.CENTER);
            hintinfoText.setFont(new java.awt.Font("Liberation Sans", 1, 15));
            hintinfoText.setForeground(Color.BLACK);
            infoWindow1.add(hintinfoText);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow1.setLocation(mouse_x - 170, mouse_y - 100);
            infoWindow1.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow1.setVisible(false);
                infoWindow1.revalidate();
                infoWindow1.removeAll();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public void panelInit(JPanel panel) {

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 750, Short.MAX_VALUE));
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 422, Short.MAX_VALUE));
        actionPanel.add(panel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents(int widthF, int heightF) {
        java.awt.GridBagConstraints gridBagConstraints;

        planeRoot = new javax.swing.JPanel();
        sidePanel = new javax.swing.JPanel();
        logoPanel = new javax.swing.JPanel();
        typeActionPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chooseActionPanel = new javax.swing.JPanel();
        artistButton = new javax.swing.JButton();
        genreButton = new javax.swing.JButton();
        topTracksButton = new javax.swing.JButton();
        tracksanalyseButton = new javax.swing.JButton();
        playlistanalyseButton = new javax.swing.JButton();
        generatePlaylistButton = new javax.swing.JButton();
        bottomSidePanel = new javax.swing.JPanel();
        homeButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        centrePanel = new GradientPanel(widthF, heightF);
        actionButtonsPanel = new javax.swing.JPanel();
        optionsPanel = new javax.swing.JPanel();
        loudnessButton = new javax.swing.JButton();
        danceabilityButton = new javax.swing.JButton();
        acousticnessButton = new javax.swing.JButton();
        numPlaylistTextField = new javax.swing.JTextField();
        timeButtonsPanel = new javax.swing.JPanel();
        weeks4Button = new javax.swing.JButton();
        months6Button = new javax.swing.JButton();
        allTimeHistoryButton = new javax.swing.JButton();
        numTracksTextField = new javax.swing.JTextField();
        planeUpperPanel = new javax.swing.JPanel();
        actionPanel = new javax.swing.JPanel();
        creditsPanel = new javax.swing.JPanel();
        creditsLabel = new javax.swing.JLabel();
        planeBasePanel = new javax.swing.JPanel();
        buttonFlag = "";
        infoWindow = new javax.swing.JFrame();
        infoWindow1 = new javax.swing.JFrame();
        infoWindow2 = new javax.swing.JFrame();
        infoWindow3 = new javax.swing.JFrame();
        infoWindow4 = new javax.swing.JFrame();
        infoWindow5 = new javax.swing.JFrame();
        infoWindow6 = new javax.swing.JFrame();
        infoWindow7 = new javax.swing.JFrame();
        infoWindow8 = new javax.swing.JFrame();
        infoWindow9 = new javax.swing.JFrame();
        infoWindow10 = new javax.swing.JFrame();
        infoWindow11 = new javax.swing.JFrame();
        infoWindow12 = new javax.swing.JFrame();
        infoText = new javax.swing.JLabel();
        infoText1 = new javax.swing.JLabel();
        infoText2 = new javax.swing.JLabel();
        infoText3 = new javax.swing.JLabel();
        infoText4 = new javax.swing.JLabel();
        infoText5 = new javax.swing.JLabel();
        infoText6 = new javax.swing.JLabel();
        infoText7 = new javax.swing.JLabel();
        infoText8 = new javax.swing.JLabel();
        infoText9 = new javax.swing.JLabel();
        infoText10 = new javax.swing.JLabel();
        infoText11 = new javax.swing.JLabel();
        infoText12 = new javax.swing.JLabel();
        infoTextField = new javax.swing.JLabel();
        infoTextField2 = new javax.swing.JLabel();
        genArtistButton = new javax.swing.JButton();
        genTracksButton = new javax.swing.JButton();
        genPanel = new javax.swing.JPanel();
        genFlag = false;
        transPanel = new javax.swing.JPanel();
        instrumentalButton = new javax.swing.JButton();
        energyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Statify PAP23L edition");
        setMinimumSize(new java.awt.Dimension(830, 440));

        infoWindow.setUndecorated(true);
        infoWindow.setBackground(new Color(255, 255, 255, 50));
        infoWindow.setSize(400, 100);
        infoWindow1.setUndecorated(true);
        infoWindow1.setBackground(new Color(255, 255, 255, 50));
        infoWindow1.setSize(400, 100);
        infoWindow2.setUndecorated(true);
        infoWindow2.setBackground(new Color(255, 255, 255, 50));
        infoWindow2.setSize(400, 100);
        infoWindow3.setUndecorated(true);
        infoWindow3.setBackground(new Color(255, 255, 255, 50));
        infoWindow3.setSize(400, 100);
        infoWindow4.setUndecorated(true);
        infoWindow4.setBackground(new Color(255, 255, 255, 50));
        infoWindow4.setSize(400, 100);
        infoWindow5.setUndecorated(true);
        infoWindow5.setBackground(new Color(255, 255, 255, 50));
        infoWindow5.setSize(400, 50);
        infoWindow6.setUndecorated(true);
        infoWindow6.setBackground(new Color(255, 255, 255, 50));
        infoWindow6.setSize(400, 50);
        infoWindow7.setUndecorated(true);
        infoWindow7.setBackground(new Color(255, 255, 255, 50));
        infoWindow7.setSize(400, 50);
        infoWindow8.setUndecorated(true);
        infoWindow8.setBackground(new Color(255, 255, 255, 50));
        infoWindow8.setSize(400, 50);
        infoWindow9.setUndecorated(true);
        infoWindow9.setBackground(new Color(255, 255, 255, 50));
        infoWindow9.setSize(400, 50);
        infoWindow10.setUndecorated(true);
        infoWindow10.setBackground(new Color(255, 255, 255, 50));
        infoWindow10.setSize(400, 50);
        infoWindow11.setUndecorated(true);
        infoWindow11.setBackground(new Color(255, 255, 255, 50));
        infoWindow11.setSize(400, 50);
        infoWindow12.setUndecorated(true);
        infoWindow12.setBackground(new Color(255, 255, 255, 50));
        infoWindow12.setSize(400, 50);

        // int width = infoWindow.getWidth();
        // int height = infoWindow.getHeight();
        // infoText.setBounds(0, 0, width, height);
        // infoText.setHorizontalAlignment(JLabel.CENTER);
        // infoText.setVerticalAlignment(JLabel.CENTER);
        // infoText.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        // infoText.setForeground(Color.BLACK);
        // infoWindow.add(infoText);

        String text = "<html>Here you will discover your most listened to genres. <br/>Enter the number of genres in the text box,<br/> then choose the period of the last 4 weeks, 6 months or<br/> since you started using Spotify<html>"; // przycisk
                                                                                                                                                                                                                                             // genre
        // javax.swing.JLabel infoText = new JLabel();
        int width = infoWindow.getWidth();
        int height = infoWindow.getHeight();
        int width1 = infoWindow1.getWidth();
        int height1 = infoWindow1.getHeight();
        infoText.setBounds(0, 0, width1, height1);
        infoText.setHorizontalAlignment(JLabel.CENTER);
        infoText.setVerticalAlignment(JLabel.CENTER);
        infoText.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText.setForeground(Color.BLACK);
        infoText.setText(text);

        String text1 = "<html> Here you will discover your most listened to artists<br/>Enter the number of artists in the text field, then<br/>select the period of the last 4 weeks, 6 months or<br/> since you started using Spotify<html>"; // przycisk
                                                                                                                                                                                                                                                // artists
        infoText1.setBounds(0, 0, width1, height1);
        infoText1.setHorizontalAlignment(JLabel.CENTER);
        infoText1.setVerticalAlignment(JLabel.CENTER);
        infoText1.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText1.setForeground(Color.BLACK);
        infoText1.setText(text1);

        String text2 = "<html> Here you will find the most listened to, favorite songs<br/>Enter the number of songs in the text field, then<br/>select the period of the last 4 weeks, 6 months or<br/> since you started using Spotify<html>"; // przycisk
                                                                                                                                                                                                                                                 // top
                                                                                                                                                                                                                                                 // tracks
        infoText2.setBounds(0, 0, width1, height1);
        infoText2.setHorizontalAlignment(JLabel.CENTER);
        infoText2.setVerticalAlignment(JLabel.CENTER);
        infoText2.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText2.setForeground(Color.BLACK);
        infoText2.setText(text2);
        String text3 = "<html>Get to know the various statistics of your songs from the<br/> library. Enter the number of songs in the text field,<br/> and then select the statistics you are<br/> interested in: loudness, danceability or acousticness<html>"; // przycisk
                                                                                                                                                                                                                                                                  // analyse
                                                                                                                                                                                                                                                                  // tracks
        infoText3.setBounds(0, 0, width1, height1);
        infoText3.setHorizontalAlignment(JLabel.CENTER);
        infoText3.setVerticalAlignment(JLabel.CENTER);
        infoText3.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText3.setForeground(Color.BLACK);
        infoText3.setText(text3);
        String text4 = "<html>Learn the various statistics of your playlists from the<br/> library. Enter the number of playlists in the text field,<br/> and then select the statistics you are<br/> interested in: loudness, danceability or acousticness<html>"; // przycisk
                                                                                                                                                                                                                                                                    // analyse
                                                                                                                                                                                                                                                                    // playlists
        infoText4.setBounds(0, 0, width1, height1);
        infoText4.setHorizontalAlignment(JLabel.CENTER);
        infoText4.setVerticalAlignment(JLabel.CENTER);
        infoText4.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText4.setForeground(Color.BLACK);
        infoText4.setText(text4);
        String text5 = "Generate your playlist based on what you listen to"; // przycisk generate playlist
        infoText5.setBounds(0, 0, width, height);
        infoText5.setHorizontalAlignment(JLabel.CENTER);
        infoText5.setVerticalAlignment(JLabel.CENTER);
        infoText5.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText5.setForeground(Color.BLACK);
        infoText5.setText(text5);
        String text6 = "Back to homepage"; // przycisk home
        infoText6.setBounds(0, 0, width, height);
        infoText6.setHorizontalAlignment(JLabel.CENTER);
        infoText6.setVerticalAlignment(JLabel.CENTER);
        infoText6.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText6.setForeground(Color.BLACK);
        infoText6.setText(text6);
        String text7 = "The overall loudness of a track in decibels (dB)."; // przycisk loudness
        infoText7.setBounds(0, 0, width, height);
        infoText7.setHorizontalAlignment(JLabel.CENTER);
        infoText7.setVerticalAlignment(JLabel.CENTER);
        infoText7.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText7.setForeground(Color.BLACK);
        infoText7.setText(text7);
        String text8 = "Danceability -> how suitable a track is for dancing."; // przycisk danceability
        infoText8.setBounds(0, 0, width, height);
        infoText8.setHorizontalAlignment(JLabel.CENTER);
        infoText8.setVerticalAlignment(JLabel.CENTER);
        infoText8.setFont(new java.awt.Font("Liberation Sans", 1, 15));

        infoText8.setForeground(Color.BLACK);
        infoText8.setText(text8);
        String text9 = "How much acoustic instruments impact the track"; // przycisk acusticness
        infoText9.setBounds(0, 0, width, height);
        infoText9.setHorizontalAlignment(JLabel.CENTER);
        infoText9.setVerticalAlignment(JLabel.CENTER);
        infoText9.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText9.setForeground(Color.BLACK);
        infoText9.setText(text9);
        String text10 = "Uzyskaj statystyki z ostatnich 4 tygodni słuchania muzyki"; // przycisk 4 weeks
        infoText10.setBounds(0, 0, width, height);
        infoText10.setHorizontalAlignment(JLabel.CENTER);
        infoText10.setVerticalAlignment(JLabel.CENTER);
        infoText10.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText10.setForeground(Color.BLACK);
        infoText10.setText(text10);

        String text11 = "^^Enter number of tracks or artists^^";
        infoTextField.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        // infoTextField.setBackground(new Color(0, 0, 0, 0));
        infoTextField.setForeground(Color.BLACK);
        infoTextField.setText(text11);

        String text12 = "How instrumental are tracks"; // przycisk instrumental
        infoText11.setBounds(0, 0, width, height);
        infoText11.setHorizontalAlignment(JLabel.CENTER);
        infoText11.setVerticalAlignment(JLabel.CENTER);
        infoText11.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText11.setForeground(Color.BLACK);
        infoText11.setText(text12);

        String text13 = "How much energetic are tracks"; // przycisk energy
        infoText12.setBounds(0, 0, width, height);
        infoText12.setHorizontalAlignment(JLabel.CENTER);
        infoText12.setVerticalAlignment(JLabel.CENTER);
        infoText12.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        infoText12.setForeground(Color.BLACK);
        infoText12.setText(text13);

        String text14 = "^^Enter number of playlist from which you will get statistics^^";
        infoTextField2.setFont(new java.awt.Font("Liberation Sans", 1, 15));
        // infoTextField.setBackground(new Color(0, 0, 0, 0));
        infoTextField2.setForeground(Color.BLACK);
        infoTextField2.setText(text14);

        genPanel.setLayout(new GridBagLayout());

        planeRoot.setMaximumSize(new java.awt.Dimension(1980, 1080));
        planeRoot.setPreferredSize(new java.awt.Dimension(960, 540));
        planeRoot.setLayout(new java.awt.BorderLayout());

        sidePanel.setBackground(new java.awt.Color(25, 20, 20));
        sidePanel.setMinimumSize(new java.awt.Dimension(210, 440));
        sidePanel.setPreferredSize(new java.awt.Dimension(210, 540));
        sidePanel.setLayout(new java.awt.BorderLayout());

        logoPanel.setBackground(new java.awt.Color(25, 20, 20));
        logoPanel.setMinimumSize(new java.awt.Dimension(210, 120));
        logoPanel.setPreferredSize(new java.awt.Dimension(210, 120));
        logoPanel.setLayout(new java.awt.BorderLayout());
        sidePanel.add(logoPanel, java.awt.BorderLayout.NORTH);

        typeActionPanel.setBackground(new java.awt.Color(25, 20, 20));
        typeActionPanel.setMinimumSize(new java.awt.Dimension(210, 320));
        typeActionPanel.setPreferredSize(new java.awt.Dimension(210, 220));
        typeActionPanel.setLayout(new javax.swing.BoxLayout(typeActionPanel, javax.swing.BoxLayout.PAGE_AXIS));

        jScrollPane1.setBackground(new java.awt.Color(52, 235, 107));
        jScrollPane1.setBorder(null);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(188, 220));

        chooseActionPanel.setBackground(new java.awt.Color(25, 20, 20));
        chooseActionPanel.setMinimumSize(new java.awt.Dimension(188, 200));
        chooseActionPanel.setPreferredSize(new java.awt.Dimension(188, 440));

        artistButton.setBackground(new java.awt.Color(44, 51, 51));
        artistButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        artistButton.setForeground(new java.awt.Color(255, 255, 255));
        artistButton
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons/icons8-musician-male-32.png"))); // NOI18N
        artistButton.setText("artists");
        artistButton.setBorder(null);
        artistButton.setMaximumSize(new java.awt.Dimension(80, 24));
        artistButton.setMinimumSize(new java.awt.Dimension(80, 24));
        artistButton.setPreferredSize(new java.awt.Dimension(80, 24));
        artistButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                artistButtonMouseClicked(evt);
            }
        });
        artistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                artistButtonActionPerformed(evt);
            }
        });

        genreButton.setBackground(new java.awt.Color(44, 51, 51));
        genreButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        genreButton.setForeground(new java.awt.Color(255, 255, 255));
        genreButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons/icons8-music-record-32.png"))); // NOI18N
        genreButton.setText("genre");
        genreButton.setBorder(null);
        genreButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                genreButtonMouseClicked(evt);
            }
        });
        genreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genreButtonActionPerformed(evt);
            }
        });

        topTracksButton.setBackground(new java.awt.Color(44, 51, 51));
        topTracksButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        topTracksButton.setForeground(new java.awt.Color(255, 255, 255));
        topTracksButton
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons/icons8-volume-level-32.png"))); // NOI18N
        topTracksButton.setText("top tracks");
        topTracksButton.setBorder(null);
        topTracksButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                topTracksButtonMouseClicked(evt);
            }
        });
        topTracksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topTracksButtonActionPerformed(evt);
            }
        });

        tracksanalyseButton.setBackground(new java.awt.Color(44, 51, 51));
        tracksanalyseButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        tracksanalyseButton.setForeground(new java.awt.Color(255, 255, 255));
        tracksanalyseButton
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons/icons8-sample-rate-32.png"))); // NOI18N
        tracksanalyseButton.setText("analyse tracks");
        tracksanalyseButton.setBorder(null);
        tracksanalyseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tracksanalyseButtonMouseClicked(evt);
            }
        });
        tracksanalyseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tracksanalyseButtonActionPerformed(evt);
            }
        });

        playlistanalyseButton.setBackground(new java.awt.Color(44, 51, 51));
        playlistanalyseButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        playlistanalyseButton.setForeground(new java.awt.Color(255, 255, 255));
        playlistanalyseButton
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons/icons8-audio-wave2-32.png"))); // NOI18N
        playlistanalyseButton.setText("analyse playlist");
        playlistanalyseButton.setBorder(null);
        playlistanalyseButton.setMaximumSize(new java.awt.Dimension(80, 24));
        playlistanalyseButton.setMinimumSize(new java.awt.Dimension(80, 24));
        playlistanalyseButton.setPreferredSize(new java.awt.Dimension(80, 24));
        playlistanalyseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playlistanalyseButtonMouseClicked(evt);
            }
        });
        playlistanalyseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playlistanalyseButtonActionPerformed(evt);
            }
        });

        generatePlaylistButton.setBackground(new java.awt.Color(44, 51, 51));
        generatePlaylistButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        generatePlaylistButton.setForeground(new java.awt.Color(255, 255, 255));
        generatePlaylistButton
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons/icons8-music-library-32.png"))); // NOI18N
        generatePlaylistButton.setText("generate playlist");
        generatePlaylistButton.setBorder(null);
        generatePlaylistButton.setMaximumSize(new java.awt.Dimension(80, 24));
        generatePlaylistButton.setMinimumSize(new java.awt.Dimension(80, 24));
        generatePlaylistButton.setPreferredSize(new java.awt.Dimension(80, 24));
        generatePlaylistButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generatePlaylistButtonMouseClicked(evt);
            }
        });
        generatePlaylistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatePlaylistButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout chooseActionPanelLayout = new javax.swing.GroupLayout(chooseActionPanel);
        chooseActionPanel.setLayout(chooseActionPanelLayout);
        chooseActionPanelLayout.setHorizontalGroup(
                chooseActionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(chooseActionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(chooseActionPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(playlistanalyseButton, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tracksanalyseButton, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                        .addComponent(topTracksButton, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(genreButton, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(artistButton, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(generatePlaylistButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(14, 14, 14)));
        chooseActionPanelLayout.setVerticalGroup(
                chooseActionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(chooseActionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(artistButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(genreButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(topTracksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tracksanalyseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(playlistanalyseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(generatePlaylistButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jScrollPane1.setViewportView(chooseActionPanel);

        typeActionPanel.add(jScrollPane1);

        sidePanel.add(typeActionPanel, java.awt.BorderLayout.CENTER);

        bottomSidePanel.setBackground(new java.awt.Color(25, 20, 20));
        bottomSidePanel.setMinimumSize(new java.awt.Dimension(210, 100));
        bottomSidePanel.setPreferredSize(new java.awt.Dimension(210, 100));
        bottomSidePanel.setLayout(null);

        homeButton.setBackground(new java.awt.Color(44, 51, 51));
        homeButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        homeButton.setForeground(new java.awt.Color(255, 255, 255));
        homeButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/com/icons/icons8-equal-housing-opportunity-32.png"))); // NOI18N
        homeButton.setText("INFO");
        homeButton.setBorder(null);
        homeButton.setHideActionText(true);
        homeButton.setMaximumSize(new java.awt.Dimension(180, 60));
        homeButton.setMinimumSize(new java.awt.Dimension(180, 60));
        homeButton.setPreferredSize(new java.awt.Dimension(180, 60));
        homeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeButtonMouseClicked(evt);
            }
        });
        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeButtonActionPerformed(evt);
            }
        });
        bottomSidePanel.add(homeButton);
        homeButton.setBounds(12, 20, 180, 60);

        sidePanel.add(bottomSidePanel, java.awt.BorderLayout.PAGE_END);

        planeRoot.add(sidePanel, java.awt.BorderLayout.WEST);

        mainPanel.setBackground(new java.awt.Color(29, 185, 84));
        mainPanel.setPreferredSize(new java.awt.Dimension(750, 540));
        mainPanel.setLayout(new java.awt.BorderLayout());

        centrePanel.setBackground(new java.awt.Color(29, 185, 84));
        centrePanel.setLayout(new java.awt.BorderLayout());
        centrePanel.setVisible(true);
        actionButtonsPanel.setPreferredSize(new java.awt.Dimension(20, 120));
        actionButtonsPanel.setLayout(new java.awt.CardLayout());

        optionsPanel.setBackground(new java.awt.Color(29, 185, 84));
        optionsPanel.setMinimumSize(new java.awt.Dimension(620, 120));
        optionsPanel.setPreferredSize(new java.awt.Dimension(20, 120));
        java.awt.GridBagLayout optionsPanelLayout = new java.awt.GridBagLayout();
        optionsPanelLayout.columnWidths = new int[] { 0, 40, 0, 40, 0, 40, 0, 40, 0 };
        optionsPanelLayout.rowHeights = new int[] { 0, 10, 0 };
        optionsPanel.setLayout(optionsPanelLayout);

        loudnessButton.setBackground(new java.awt.Color(44, 51, 51));
        loudnessButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        loudnessButton.setForeground(new java.awt.Color(255, 255, 255));
        loudnessButton.setText("loudness");
        loudnessButton.setBorder(null);
        loudnessButton.setMaximumSize(new java.awt.Dimension(130, 54));
        loudnessButton.setMinimumSize(new java.awt.Dimension(130, 54));
        loudnessButton.setPreferredSize(new java.awt.Dimension(130, 54));
        loudnessButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loudnessButtonMouseClicked(evt);
            }
        });
        loudnessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loudnessButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        optionsPanel.add(loudnessButton, gridBagConstraints);

        danceabilityButton.setBackground(new java.awt.Color(44, 51, 51));
        danceabilityButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        danceabilityButton.setForeground(new java.awt.Color(255, 255, 255));
        danceabilityButton.setText("danceability");
        danceabilityButton.setMaximumSize(new java.awt.Dimension(200, 54));
        danceabilityButton.setMinimumSize(new java.awt.Dimension(130, 54));
        danceabilityButton.setPreferredSize(new java.awt.Dimension(130, 54));
        danceabilityButton.setBorder(null);
        danceabilityButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                danceabilityButtonMouseClicked(evt);
            }
        });
        danceabilityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                danceabilityButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        optionsPanel.add(danceabilityButton, gridBagConstraints);

        acousticnessButton.setBackground(new java.awt.Color(44, 51, 51));
        acousticnessButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        acousticnessButton.setForeground(new java.awt.Color(255, 255, 255));
        acousticnessButton.setText("acousticness");
        acousticnessButton.setMaximumSize(new java.awt.Dimension(130, 54));
        acousticnessButton.setMinimumSize(new java.awt.Dimension(130, 54));
        acousticnessButton.setPreferredSize(new java.awt.Dimension(130, 54));
        acousticnessButton.setBorder(null);
        acousticnessButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acousticnessButtonMouseClicked(evt);
            }
        });
        acousticnessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acousticnessButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        optionsPanel.add(acousticnessButton, gridBagConstraints);

        instrumentalButton.setBackground(new java.awt.Color(44, 51, 51));
        instrumentalButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        instrumentalButton.setForeground(new java.awt.Color(255, 255, 255));
        instrumentalButton.setText("instrumental");
        instrumentalButton.setMaximumSize(new java.awt.Dimension(130, 54));
        instrumentalButton.setMinimumSize(new java.awt.Dimension(130, 54));
        instrumentalButton.setPreferredSize(new java.awt.Dimension(130, 54));
        instrumentalButton.setBorder(null);
        instrumentalButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                instrumentalButtonMouseClicked(evt);
            }
        });
        instrumentalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instrumentalButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        optionsPanel.add(instrumentalButton, gridBagConstraints);

        energyButton.setBackground(new java.awt.Color(44, 51, 51));
        energyButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        energyButton.setForeground(new java.awt.Color(255, 255, 255));
        energyButton.setText("energy");
        energyButton.setMaximumSize(new java.awt.Dimension(130, 54));
        energyButton.setMinimumSize(new java.awt.Dimension(130, 54));
        energyButton.setPreferredSize(new java.awt.Dimension(130, 54));
        energyButton.setBorder(null);
        energyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                energyButtonMouseClicked(evt);
            }
        });
        energyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                energyButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        optionsPanel.add(energyButton, gridBagConstraints);

        numPlaylistTextField.setBackground(new java.awt.Color(44, 51, 51));
        numPlaylistTextField.setFont(new java.awt.Font("Liberation Sans", 2, 15)); // NOI18N
        numPlaylistTextField.setForeground(new java.awt.Color(255, 255, 255));
        numPlaylistTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numPlaylistTextField.setText("Amount of playlists(max ??)");
        numPlaylistTextField.setToolTipText("");
        numPlaylistTextField.setMaximumSize(new java.awt.Dimension(210, 32));
        numPlaylistTextField.setMinimumSize(new java.awt.Dimension(210, 32));
        numPlaylistTextField.setPreferredSize(new java.awt.Dimension(210, 32));
        numPlaylistTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                numPlaylistTextFieldFocusGained(evt);
            }
        });
        numPlaylistTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numPlaylistTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 10;
        optionsPanel.add(numPlaylistTextField, gridBagConstraints);

        infoTextField2.setPreferredSize(new java.awt.Dimension(350, 32));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 10;
        optionsPanel.add(infoTextField2, gridBagConstraints);

        actionButtonsPanel.add(optionsPanel, "card2");

        timeButtonsPanel.setBackground(new java.awt.Color(29, 185, 84));
        timeButtonsPanel.setMinimumSize(new java.awt.Dimension(620, 120));
        timeButtonsPanel.setPreferredSize(new java.awt.Dimension(620, 120));
        java.awt.GridBagLayout timeButtonsPanelLayout = new java.awt.GridBagLayout();
        timeButtonsPanelLayout.columnWidths = new int[] { 0, 40, 0, 40, 0 };
        timeButtonsPanelLayout.rowHeights = new int[] { 0, 10, 0 };
        timeButtonsPanel.setLayout(timeButtonsPanelLayout);

        weeks4Button.setBackground(new java.awt.Color(44, 51, 51));
        weeks4Button.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        weeks4Button.setForeground(new java.awt.Color(255, 255, 255));
        weeks4Button.setText("4 weeks");
        weeks4Button.setBorder(null);
        weeks4Button.setMaximumSize(new java.awt.Dimension(130, 54));
        weeks4Button.setMinimumSize(new java.awt.Dimension(130, 54));
        weeks4Button.setPreferredSize(new java.awt.Dimension(130, 54));
        weeks4Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weeks4ButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        timeButtonsPanel.add(weeks4Button, gridBagConstraints);

        months6Button.setBackground(new java.awt.Color(44, 51, 51));
        months6Button.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        months6Button.setForeground(new java.awt.Color(255, 255, 255));
        months6Button.setText("6 months");
        months6Button.setBorder(null);
        months6Button.setMaximumSize(new java.awt.Dimension(130, 54));
        months6Button.setMinimumSize(new java.awt.Dimension(130, 54));
        months6Button.setPreferredSize(new java.awt.Dimension(130, 54));
        months6Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                months6ButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        timeButtonsPanel.add(months6Button, gridBagConstraints);

        allTimeHistoryButton.setBackground(new java.awt.Color(44, 51, 51));
        allTimeHistoryButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        allTimeHistoryButton.setForeground(new java.awt.Color(255, 255, 255));
        allTimeHistoryButton.setText("all-time");
        allTimeHistoryButton.setBorder(null);
        allTimeHistoryButton.setMaximumSize(new java.awt.Dimension(130, 54));
        allTimeHistoryButton.setMinimumSize(new java.awt.Dimension(130, 54));
        allTimeHistoryButton.setPreferredSize(new java.awt.Dimension(130, 54));
        allTimeHistoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allTimeHistoryButtonActionPerformed(evt);
            }
        });

        genArtistButton.setBackground(new java.awt.Color(44, 51, 51));
        genArtistButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        genArtistButton.setForeground(new java.awt.Color(255, 255, 255));
        genArtistButton.setText("By favourite artists");
        genArtistButton.setBorder(null);
        genArtistButton.setMaximumSize(new java.awt.Dimension(200, 54));
        genArtistButton.setMinimumSize(new java.awt.Dimension(200, 54));
        genArtistButton.setPreferredSize(new java.awt.Dimension(200, 54));
        genArtistButton.setSize(new java.awt.Dimension(200, 54));
        genArtistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genArtistButtonActionPerformed(evt);
            }
        });

        genTracksButton.setBackground(new java.awt.Color(44, 51, 51));
        genTracksButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        genTracksButton.setForeground(new java.awt.Color(255, 255, 255));
        genTracksButton.setText("By favourite tracks");
        genTracksButton.setBorder(null);
        genTracksButton.setMaximumSize(new java.awt.Dimension(200, 54));
        genTracksButton.setMinimumSize(new java.awt.Dimension(200, 54));
        genTracksButton.setPreferredSize(new java.awt.Dimension(200, 54));
        genTracksButton.setSize(new java.awt.Dimension(200, 54));
        genTracksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genTracksButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        timeButtonsPanel.add(allTimeHistoryButton, gridBagConstraints);

        numTracksTextField.setBackground(new java.awt.Color(44, 51, 51));
        numTracksTextField.setFont(new java.awt.Font("Liberation Sans", 2, 15)); // NOI18N
        numTracksTextField.setForeground(new java.awt.Color(255, 255, 255));
        numTracksTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numTracksTextField.setText("Amount of tracks (max 50)");
        numTracksTextField.setMaximumSize(new java.awt.Dimension(210, 32));
        numTracksTextField.setMinimumSize(new java.awt.Dimension(210, 32));
        numTracksTextField.setPreferredSize(new java.awt.Dimension(210, 32));
        numTracksTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                numTracksTextFieldFocusGained(evt);
            }
        });
        numTracksTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numTracksTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        timeButtonsPanel.add(numTracksTextField, gridBagConstraints);

        infoTextField.setPreferredSize(new java.awt.Dimension(250, 32));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        timeButtonsPanel.add(infoTextField, gridBagConstraints);

        actionButtonsPanel.add(timeButtonsPanel, "card2");

        planeUpperPanel.setBackground(new java.awt.Color(29, 185, 84));
        planeUpperPanel.setMinimumSize(new java.awt.Dimension(620, 120));

        javax.swing.GroupLayout planeUpperPanelLayout = new javax.swing.GroupLayout(planeUpperPanel);
        planeUpperPanel.setLayout(planeUpperPanelLayout);
        planeUpperPanelLayout.setHorizontalGroup(
                planeUpperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 620, Short.MAX_VALUE));
        planeUpperPanelLayout.setVerticalGroup(
                planeUpperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 120, Short.MAX_VALUE));

        actionButtonsPanel.add(planeUpperPanel, "card3");
        actionButtonsPanel.setVisible(true);

        centrePanel.add(actionButtonsPanel, java.awt.BorderLayout.NORTH);

        actionPanel.setBackground(new java.awt.Color(29, 185, 84, 0));
        actionPanel.setPreferredSize(new java.awt.Dimension(750, 420));
        actionPanel.setLayout(new java.awt.CardLayout());

        transPanel.setBackground(new Color(0, 0, 0, 0));
        transPanel.setPreferredSize(new java.awt.Dimension(700, 400));
        transPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transPanelMouseClicked(evt);
            }
        });

        creditsPanel.setBackground(new java.awt.Color(29, 185, 84));
        creditsPanel.setMinimumSize(new java.awt.Dimension(620, 320));
        creditsPanel.setLayout(new java.awt.GridLayout(1, 0));

        creditsLabel.setText("<html><font color='white'>" +
                "<p><br></p>" +
                "<p><strong>Loudness</strong> - The overall loudness of a track in decibels (dB). Loudness values are averaged across the entire track and are useful for comparing relative loudness of tracks. Loudness is the quality of a sound that is the primary psychological correlate of physical strength (amplitude). Values typically range between -60 and 0 db.</p>"
                +
                "<p><br></p>" +
                "<p><strong>Danceability</strong> - &nbsp;describes how suitable a track is for dancing based on a combination of musical elements including tempo, rhythm stability, beat strength, and overall regularity. A value of 0.0 is least danceable and 1.0 is most danceable.</p>"
                +
                "<p><br></p>" +
                "<p><strong>Acousticness</strong> - A confidence measure from 0.0 to 1.0 of whether the track is acoustic. 1.0 represents high confidence the track is acoustic.</p>"
                +
                "<p><br></p>" +
                "<p><strong>Instrumentalness</strong> - Predicts whether a track contains no vocals. &quot;Ooh&quot; and &quot;aah&quot; sounds are treated as instrumental in this context. Rap or spoken word tracks are clearly &quot;vocal&quot;. The closer the instrumentalness value is to 1.0, the greater likelihood the track contains no vocal content. Values above 0.5 are intended to represent instrumental tracks, but confidence is higher as the value approaches 1.0.</p>"
                +
                "<p><br></p>" +
                "<p><strong>Energy</strong> - a measure from 0.0 to 1.0 and represents a perceptual measure of intensity and activity. Typically, energetic tracks feel fast, loud, and noisy. For example, death metal has high energy, while a Bach prelude scores low on the scale. Perceptual features contributing to this attribute include dynamic range, perceived loudness, timbre, onset rate, and general entropy.</p>"
                +
                "<p><br></p>");

        creditsLabel.setFont(new Font("Noto Sans CJK JP", Font.PLAIN, 18));
        creditsLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        creditsLabel.setOpaque(false);
        creditsPanel.add(creditsLabel, java.awt.BorderLayout.CENTER);

        actionPanel.add(creditsPanel, "card0");

        planeBasePanel.setBackground(new java.awt.Color(29, 185, 84));
        planeBasePanel.setMinimumSize(new java.awt.Dimension(620, 320));

        javax.swing.GroupLayout planeBasePanelLayout = new javax.swing.GroupLayout(planeBasePanel);
        planeBasePanel.setLayout(planeBasePanelLayout);
        planeBasePanelLayout.setHorizontalGroup(
                planeBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 620, Short.MAX_VALUE));
        planeBasePanelLayout.setVerticalGroup(
                planeBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 440, Short.MAX_VALUE));

        actionPanel.add(planeBasePanel, "card0");

        centrePanel.add(actionPanel, java.awt.BorderLayout.CENTER);

        mainPanel.add(centrePanel, java.awt.BorderLayout.CENTER);

        planeRoot.add(mainPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(planeRoot, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_homeButtonActionPerformed
        panelsSetDefault();
        addNewPanel(creditsPanel);
        creditsLabel.setVisible(true);
    }// GEN-LAST:event_homeButtonActionPerformed

    private void artistButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow1.add(infoText1);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow1.setLocation(mouse_x - 170, mouse_y - 100);
            infoWindow1.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow1.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void genreButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow.add(infoText);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow.setLocation(mouse_x - 170, mouse_y - 100);
            infoWindow.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void topTracksButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow2.add(infoText2);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow2.setLocation(mouse_x - 170, mouse_y - 100);
            infoWindow2.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow2.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void tracksanalyseButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow3.add(infoText3);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow3.setLocation(mouse_x - 170, mouse_y - 100);
            infoWindow3.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow3.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void playlistanalyseButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow4.add(infoText4);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow4.setLocation(mouse_x - 170, mouse_y - 100);
            infoWindow4.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow4.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void generatePlaylistButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow5.add(infoText5);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow5.setLocation(mouse_x - 170, mouse_y - 50);
            infoWindow5.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow5.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void homeButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow6.add(infoText6);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow6.setLocation(mouse_x - 170, mouse_y - 50);
            infoWindow6.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow6.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void loudnessButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow7.add(infoText7);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow7.setLocation(mouse_x - 170, mouse_y - 50);
            infoWindow7.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow7.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void danceabilityButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow8.add(infoText8);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow8.setLocation(mouse_x - 170, mouse_y - 50);
            infoWindow8.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow8.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void acousticnessButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow9.add(infoText9);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow9.setLocation(mouse_x - 170, mouse_y - 50);
            infoWindow9.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow9.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void instrumentalButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow11.add(infoText11);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow11.setLocation(mouse_x - 170, mouse_y - 50);
            infoWindow11.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow11.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void energyButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow12.add(infoText12);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow12.setLocation(mouse_x - 170, mouse_y - 50);
            infoWindow12.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow12.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void transPanelMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            infoWindowsHide();
            infoWindow10.add(infoText10);
            java.awt.PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int mouse_x = (int) b.getX();
            int mouse_y = (int) b.getY();
            infoWindow10.setLocation(mouse_x - 170, mouse_y - 100);
            infoWindow10.setVisible(true);
            Timer timer = new Timer(4000, e -> {
                infoWindow10.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void numPlaylistTextFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_numPlaylistTextFieldActionPerformed

    }// GEN-LAST:event_numPlaylistTextFieldActionPerformed

    private void numPlaylistTextFieldFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_numPlaylistTextFieldFocusGained
        numPlaylistTextField.setText("");
    }// GEN-LAST:event_numPlaylistTextFieldFocusGained

    private void numTracksTextFieldFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_numTracksTextFieldFocusGained
        numTracksTextField.setText("");
    }// GEN-LAST:event_numTracksTextFieldFocusGained

    private void numTracksTextFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_numTracksTextFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_numTracksTextFieldActionPerformed

    private void loudnessButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_optionButton1ActionPerformed
        String playlists_num = numPlaylistTextField.getText();
        try {
            int plNum = Integer.parseInt(playlists_num);
            if (plNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Invalid amount of playlists");
            } else {
                actionPanel.removeAll();
                Statify.SetPlaylistsNum(plNum);
                upperPanelDefault();
                if (analFlag) {
                    JPanel histo = Statify.getLoudnessHistogram();
                    // histo.add(transPanel);
                    panelInit(histo);
                    addNewPanel(histo);
                    // transPanel.setVisible(true);

                } else {
                    if (plNum > 5) {
                        javax.swing.JOptionPane.showMessageDialog(this, "Maximum amount of playlist is 5");
                    } else {
                        User user = Statify.currentUser;
                        HashMap<String, String> playlists = user.getPlaylistsHashMap(plNum);// , selectedPlaylists);
                        FeatureName[] features = { FeatureName.ACOUSTICNESS, FeatureName.DANCEABILITY,
                                FeatureName.ENERGY,
                                FeatureName.LOUDNESS, FeatureName.LIVENESS };
                        JPanel radar = Statify.getTracksRadarChartFromPlaylists(playlists, features);
                        panelInit(radar);
                        radar.setVisible(true);
                    }
                }
                optionsPanel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");

        }
    }// GEN-LAST:event_optionButton1ActionPerformed

    private void danceabilityButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_optionButton2ActionPerformed
        String playlists_num = numPlaylistTextField.getText();
        if (genFlag) {
            System.out.println("piękniusia playlista hej");
            // tu obsługujemy dodanie playlisty do biblioteki
        } else {
            try {
                actionPanel.removeAll();
                upperPanelDefault();
                if (analFlag) {
                    int plNum = Integer.parseInt(playlists_num);
                    Statify.SetPlaylistsNum(plNum);
                    if (plNum <= 0) {
                        javax.swing.JOptionPane.showMessageDialog(this, "Invalid amount of playlists");
                    } else {
                        JPanel histo = Statify.getDanceabilityHistogram();
                        panelInit(histo);
                        histo.setVisible(true);
                    }
                } else {
                    java.util.ArrayList<String> selectedNames = new java.util.ArrayList<>();
                    User user = Statify.currentUser;
                    for (Object elem : selectedPlaylists) {
                        if (elem != null) {
                            selectedNames.add(elem.toString());
                        }
                    }
                    HashMap<String, String> playlists = user.getPlaylistsHashMap(selectedNames); // ,
                                                                                                 // selectedPlaylists);
                    FeatureName[] features = { FeatureName.ACOUSTICNESS, FeatureName.DANCEABILITY, FeatureName.ENERGY,
                            FeatureName.LOUDNESS, FeatureName.INSTRUMENTALNESS };
                    JPanel radar = Statify.getTracksRadarChartFromPlaylists(playlists, features);
                    selectedNames.clear();
                    panelInit(radar);
                    radar.setVisible(true);
                }
                optionsPanel.setVisible(true);
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
            } catch (NullPointerException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");

            }
        }
    }// GEN-LAST:event_optionButton2ActionPerformed

    private void acousticnessButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_optionButton3ActionPerformed
        String playlists_num = numPlaylistTextField.getText();
        try {
            int plNum = Integer.parseInt(playlists_num);
            if (plNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Invalid amount of playlists");
            } else {
                actionPanel.removeAll();
                Statify.SetPlaylistsNum(plNum);
                upperPanelDefault();
                if (analFlag) {
                    JPanel histo = Statify.getAcousticnessHistogram();
                    panelInit(histo);
                    histo.setVisible(true);
                } else {
                    if (plNum > 5) {
                        javax.swing.JOptionPane.showMessageDialog(this, "Maximum amount of playlist is 5");
                    } else {
                        User user = Statify.currentUser;
                        HashMap<String, String> playlists = user.getPlaylistsHashMap(plNum); // , selectedPlaylists);
                        FeatureName[] features = { FeatureName.ACOUSTICNESS, FeatureName.DANCEABILITY,
                                FeatureName.ENERGY,
                                FeatureName.LOUDNESS, FeatureName.LIVENESS };
                        JPanel radar = Statify.getTracksRadarChartFromPlaylists(playlists, features);
                        panelInit(radar);
                        radar.setVisible(true);
                    }
                }
                optionsPanel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        }
    }// GEN-LAST:event_optionButton3ActionPerformed

    private void instrumentalButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_optionButton3ActionPerformed
        String playlists_num = numPlaylistTextField.getText();
        try {
            int plNum = Integer.parseInt(playlists_num);
            if (plNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Invalid amount of playlists");
            } else {
                actionPanel.removeAll();
                Statify.SetPlaylistsNum(plNum);
                upperPanelDefault();
                if (analFlag) {
                    JPanel histo = Statify.getInstrumentalHistogram();
                    panelInit(histo);
                    histo.setVisible(true);
                } else {
                    if (plNum > 5) {
                        javax.swing.JOptionPane.showMessageDialog(this, "Maximum amount of playlist is 5");
                    } else {
                        User user = Statify.currentUser;
                        HashMap<String, String> playlists = user.getPlaylistsHashMap(plNum); // , selectedPlaylists);
                        FeatureName[] features = { FeatureName.ACOUSTICNESS, FeatureName.DANCEABILITY,
                                FeatureName.ENERGY,
                                FeatureName.LOUDNESS, FeatureName.LIVENESS };
                        JPanel radar = Statify.getTracksRadarChartFromPlaylists(playlists, features);
                        panelInit(radar);
                        radar.setVisible(true);
                    }
                }
                optionsPanel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        }
    }

    private void energyButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_optionButton3ActionPerformed
        String playlists_num = numPlaylistTextField.getText();
        try {
            int plNum = Integer.parseInt(playlists_num);
            if (plNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Invalid amount of playlists");
            } else {
                actionPanel.removeAll();
                Statify.SetPlaylistsNum(plNum);
                upperPanelDefault();
                if (analFlag) {
                    JPanel histo = Statify.getEnergyHistogram();
                    panelInit(histo);
                    histo.setVisible(true);
                } else {
                    if (plNum > 5) {
                        javax.swing.JOptionPane.showMessageDialog(this, "Maximum amount of playlist is 5");
                    } else {
                        User user = Statify.currentUser;
                        HashMap<String, String> playlists = user.getPlaylistsHashMap(plNum); // , selectedPlaylists);
                        FeatureName[] features = { FeatureName.ACOUSTICNESS, FeatureName.DANCEABILITY,
                                FeatureName.ENERGY,
                                FeatureName.LOUDNESS, FeatureName.LIVENESS };
                        JPanel radar = Statify.getTracksRadarChartFromPlaylists(playlists, features);
                        panelInit(radar);
                        radar.setVisible(true);
                    }
                }
                optionsPanel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        }
    }

    private void weeks4ButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_weeks4ButtonActionPerformed
        if (genFlag) {
            if (genFlag2) {
                JScrollPane scrollPane = Statify.getRecommendationsPanelByTopArtists("short_term");

                actionPanel.removeAll();
                actionPanel.revalidate();
                actionPanel.repaint();
                actionPanel.add(scrollPane, BorderLayout.CENTER);
                loudnessButton.setVisible(false);
                acousticnessButton.setVisible(false);
                energyButton.setVisible(false);
                instrumentalButton.setVisible(false);
                infoTextField2.setVisible(false);

                numPlaylistTextField.setVisible(false);
                danceabilityButton.setPreferredSize(new java.awt.Dimension(200, 54));
                danceabilityButton.setText("Add playlist to your library");
                danceabilityButton.setVisible(true);
                optionsPanel.setVisible(true);
            } else {
                JScrollPane scrollPane = Statify.getRecommendationsPanelByTopTracks("short_term");
                actionPanel.removeAll();
                actionPanel.revalidate();
                actionPanel.repaint();
                actionPanel.add(scrollPane);
                loudnessButton.setVisible(false);
                acousticnessButton.setVisible(false);
                energyButton.setVisible(false);
                instrumentalButton.setVisible(false);
                infoTextField2.setVisible(false);
                numPlaylistTextField.setVisible(false);
                danceabilityButton.setPreferredSize(new java.awt.Dimension(200, 54));
                danceabilityButton.setText("Add playlist to your library");
                danceabilityButton.setVisible(true);
                optionsPanel.setVisible(true);
            }
        } else {
            chooseTypeTopList("short_term");
        }
    }// GEN-LAST:event_weeks4ButtonActionPerformed

    private void months6ButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_months6ButtonActionPerformed
        if (genFlag) {
            if (genFlag2) {
                JScrollPane scrollPane = Statify.getRecommendationsPanelByTopArtists("medium_term");
                actionPanel.removeAll();
                actionPanel.revalidate();
                actionPanel.repaint();
                actionPanel.add(scrollPane);
                loudnessButton.setVisible(false);
                acousticnessButton.setVisible(false);
                energyButton.setVisible(false);
                instrumentalButton.setVisible(false);
                infoTextField2.setVisible(false);
                energyButton.setVisible(false);
                instrumentalButton.setVisible(false);
                infoTextField2.setVisible(false);
                numPlaylistTextField.setVisible(false);
                danceabilityButton.setPreferredSize(new java.awt.Dimension(200, 54));
                danceabilityButton.setText("Add playlist to your library");
                danceabilityButton.setVisible(true);
                optionsPanel.setVisible(true);
            } else {
                JScrollPane scrollPane = Statify.getRecommendationsPanelByTopTracks("medium_term");
                actionPanel.removeAll();
                actionPanel.revalidate();
                actionPanel.repaint();
                actionPanel.add(scrollPane);
                loudnessButton.setVisible(false);
                acousticnessButton.setVisible(false);
                energyButton.setVisible(false);
                instrumentalButton.setVisible(false);
                infoTextField2.setVisible(false);
                numPlaylistTextField.setVisible(false);
                danceabilityButton.setPreferredSize(new java.awt.Dimension(200, 54));
                danceabilityButton.setText("Add playlist to your library");
                danceabilityButton.setVisible(true);
                optionsPanel.setVisible(true);
            }
        } else {
            chooseTypeTopList("medium_term");
        }
    }// GEN-LAST:event_months6ButtonActionPerformed

    private void allTimeHistoryButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_allTimeHistoryButtonActionPerformed
        if (genFlag) {
            if (genFlag2) {
                JScrollPane scrollPane = Statify.getRecommendationsPanelByTopArtists("long_term");
                actionPanel.removeAll();
                actionPanel.revalidate();
                actionPanel.repaint();
                actionPanel.add(scrollPane);
                loudnessButton.setVisible(false);
                acousticnessButton.setVisible(false);
                energyButton.setVisible(false);
                instrumentalButton.setVisible(false);
                infoTextField2.setVisible(false);
                numPlaylistTextField.setVisible(false);
                danceabilityButton.setPreferredSize(new java.awt.Dimension(200, 54));
                danceabilityButton.setText("Add playlist to your library");
                danceabilityButton.setVisible(true);
                optionsPanel.setVisible(true);
            } else {
                JScrollPane scrollPane = Statify.getRecommendationsPanelByTopTracks("long_term");
                actionPanel.removeAll();
                actionPanel.revalidate();
                actionPanel.repaint();
                actionPanel.add(scrollPane);
                loudnessButton.setVisible(false);
                acousticnessButton.setVisible(false);
                energyButton.setVisible(false);
                instrumentalButton.setVisible(false);
                infoTextField2.setVisible(false);
                numPlaylistTextField.setVisible(false);
                danceabilityButton.setPreferredSize(new java.awt.Dimension(200, 54));
                danceabilityButton.setText("Add playlist to your library");
                danceabilityButton.setVisible(true);
                optionsPanel.setVisible(true);
            }
        } else {
            chooseTypeTopList("long_term");
        }
    }// GEN-LAST:event_allTimeHistoryButtonActionPerformed

    private void artistButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_artistButtonActionPerformed
        buttonFlag = "artist";
        genFlag = false;
        timeButtonsPanelSetDefault();
        panelsSetDefault();
        timeButtonsPanel.setVisible(true);
    }// GEN-LAST:event_artistButtonActionPerformed

    private void genreButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_genreButtonActionPerformed
        buttonFlag = "genre";
        genFlag = false;
        timeButtonsPanelSetDefault();
        panelsSetDefault();
        numTracksTextField.setVisible(false);
        infoTextField.setVisible(false);
        timeButtonsPanel.setVisible(true);
    }// GEN-LAST:event_genreButtonActionPerformed

    private void topTracksButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_topTracksButtonActionPerformed
        buttonFlag = "tracks";
        genFlag = false;
        timeButtonsPanelSetDefault();
        panelsSetDefault();
        timeButtonsPanel.setVisible(true);
    }// GEN-LAST:event_topTracksButtonActionPerformed

    private void tracksanalyseButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tracksanalyseButtonActionPerformed
        panelsSetDefault();
        genFlag = false;
        analFlag = true;
        acousticnessButton.setVisible(true);
        numPlaylistTextField.setVisible(true);
        loudnessButton.setVisible(true);
        instrumentalButton.setVisible(true);
        energyButton.setVisible(true);
        infoTextField2.setVisible(true);
        danceabilityButton.setPreferredSize(new java.awt.Dimension(130, 54));
        danceabilityButton.setText("danceability");
        optionsPanel.setVisible(true);
    }// GEN-LAST:event_tracksanalyseButtonActionPerformed

    private void playlistanalyseButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_playlistanalyseButtonActionPerformed
        panelsSetDefault();
        genFlag = false;
        analFlag = false;
        acousticnessButton.setVisible(false);
        loudnessButton.setVisible(false);
        instrumentalButton.setVisible(false);
        energyButton.setVisible(false);
        infoTextField2.setVisible(false);
        danceabilityButton.setText("Show chart");
        optionsPanel.setVisible(true);

        Color darkGreyColor = new Color(44, 51, 51);

        HashMap<String, String> playlistsDictionary = Statify.currentUser.getPlaylistsHashMap();
        Object[] names = playlistsDictionary.keySet().toArray();
        ObjectSelectionPanel selectPanel = Statify.getPlaylistsSelectableList(names);
        selectPanel.setBackground(darkGreyColor);
        selectedPlaylists = selectPanel.selectedPlaylists();
        addNewPanel(selectPanel);
        numPlaylistTextField.setVisible(false);
        selectPanel.setVisible(true);
    }// GEN-LAST:event_playlistanalyseButtonActionPerformed

    private void generatePlaylistButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatePlaylistButtonActionPerformed
        panelsSetDefault();

        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new java.awt.Insets(10, 10, 10, 10);
        genPanel.add(genArtistButton, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        genPanel.add(genTracksButton, constraints);
        genPanel.setBackground(new Color(255, 255, 255, 0));
        genPanel.setVisible(true);
        actionPanel.add(genPanel);
        actionPanel.setVisible(true);
    }// GEN-LAST:event_generatePlaylistButtonActionPerformed

    private void genArtistButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_months6ButtonActionPerformed
        genFlag = true;
        genFlag2 = true;
        genPanel.removeAll();
        genPanel.revalidate();
        genPanel.repaint();
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new java.awt.Insets(10, 10, 10, 10);
        genPanel.add(weeks4Button, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        genPanel.add(months6Button, constraints);
        constraints.gridx = 2;
        constraints.gridy = 0;
        genPanel.add(allTimeHistoryButton, constraints);
        genPanel.setBackground(new Color(255, 255, 255, 0));
        genPanel.setVisible(true);
        actionPanel.add(genPanel);
        actionPanel.setVisible(true);
    }

    private void genTracksButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_months6ButtonActionPerformed
        genFlag = true;
        genFlag2 = false;
        genPanel.removeAll();
        genPanel.revalidate();
        genPanel.repaint();
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new java.awt.Insets(10, 10, 10, 10);
        genPanel.add(weeks4Button, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        genPanel.add(months6Button, constraints);
        constraints.gridx = 2;
        constraints.gridy = 0;
        genPanel.add(allTimeHistoryButton, constraints);
        genPanel.setBackground(new Color(255, 255, 255, 0));
        genPanel.setVisible(true);
        actionPanel.add(genPanel);
        actionPanel.setVisible(true);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainWindow mainFrame = new MainWindow();
                mainFrame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acousticnessButton;
    private javax.swing.JPanel actionButtonsPanel;
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton allTimeHistoryButton;
    private javax.swing.JButton artistButton;
    private javax.swing.JPanel bottomSidePanel;
    private javax.swing.JPanel centrePanel;
    private javax.swing.JPanel chooseActionPanel;
    private javax.swing.JPanel creditsPanel;
    private javax.swing.JLabel creditsLabel;
    private javax.swing.JButton danceabilityButton;
    private javax.swing.JButton energyButton;
    private javax.swing.JButton instrumentalButton;
    private javax.swing.JButton generatePlaylistButton;
    private javax.swing.JButton genreButton;
    private javax.swing.JButton homeButton;
    private javax.swing.JFrame infoWindow;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loudnessButton;
    private javax.swing.JPanel logoPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton months6Button;
    private javax.swing.JTextField numPlaylistTextField;
    private javax.swing.JTextField numTracksTextField;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JPanel planeBasePanel;
    private javax.swing.JPanel planeRoot;
    private javax.swing.JPanel planeUpperPanel;
    private javax.swing.JButton playlistanalyseButton;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JPanel timeButtonsPanel;
    private javax.swing.JButton topTracksButton;
    private javax.swing.JButton tracksanalyseButton;
    private javax.swing.JPanel typeActionPanel;
    private javax.swing.JButton weeks4Button;
    private String buttonFlag;
    private javax.swing.JLabel infoText;
    private javax.swing.JLabel infoText1;
    private javax.swing.JFrame infoWindow1;
    private javax.swing.JFrame infoWindow2;
    private javax.swing.JFrame infoWindow3;
    private javax.swing.JFrame infoWindow4;
    private javax.swing.JFrame infoWindow5;
    private javax.swing.JFrame infoWindow6;
    private javax.swing.JFrame infoWindow7;
    private javax.swing.JFrame infoWindow8;
    private javax.swing.JFrame infoWindow9;
    private javax.swing.JFrame infoWindow10;
    private javax.swing.JFrame infoWindow11;
    private javax.swing.JFrame infoWindow12;
    private javax.swing.JLabel infoText2;
    private javax.swing.JLabel infoText3;
    private javax.swing.JLabel infoText4;
    private javax.swing.JLabel infoText5;
    private javax.swing.JLabel infoText6;
    private javax.swing.JLabel infoText7;
    private javax.swing.JLabel infoText8;
    private javax.swing.JLabel infoText9;
    private javax.swing.JLabel infoText10;
    private javax.swing.JLabel infoText11;
    private javax.swing.JLabel infoText12;
    private Boolean analFlag;
    private javax.swing.JButton genArtistButton;
    private javax.swing.JButton genTracksButton;
    private Boolean genFlag;
    private Boolean genFlag2;
    private javax.swing.JPanel genPanel;
    private javax.swing.JPanel transPanel;
    private Object[] selectedPlaylists;
    private javax.swing.JLabel infoTextField;
    private javax.swing.JLabel infoTextField2;

    // End of variables declaration//GEN-END:variables
}
