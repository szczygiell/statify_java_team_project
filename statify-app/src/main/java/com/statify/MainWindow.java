/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.statify;

import javax.swing.JPanel;
import java.awt.Font;

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
        int heightInit = 1080; //this.getHeight();
        int widthInit = 1920; //this.getWidth();
        
        // to trzeba jakoś naprawić, ogarnąć

        initComponents(widthInit, heightInit);
        panelsSetDefault();
        this.setBounds(100, 100, 960, 540);
        setTransparent();
        creditsPanel.setVisible(true);
        setMinimumSize(new java.awt.Dimension(960, 540));
    }

    public final void panelsSetDefault() {
        // setting upper panels off
        timeButtonsPanel.setVisible(false);
        optionsPanel.setVisible(false);
        // turning base upper panel on
        planeUpperPanel.setVisible(true);
        // setting action panels off
        creditsPanel.setVisible(false);
        creditsLabel.setVisible(false);
        // turning base (credits) action panel on
        planeBasePanel.setVisible(true);
    }

    public void setTransparent(){
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

    public void addNewPanel(JPanel newPanel){
        actionPanel.removeAll();
        actionPanel.add(newPanel);
        actionPanel.revalidate();
        actionPanel.repaint();
        newPanel.setVisible(true);
    }

    public final void upperPanelDefault() {
        timeButtonsPanel.setVisible(false);
        optionsPanel.setVisible(false);
        planeUpperPanel.setVisible(true);
    }

    public void trackListGrapher(String timeRange) {
        String track_num = numTracksTextField.getText();
        try {
            int trNum = Integer.parseInt(track_num);
            if (trNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Invalid amount of tracks");
            } else {
                actionPanel.removeAll();
                upperPanelDefault();
                javax.swing.JScrollPane graph = Statify.createTopTracksPanel(trNum, timeRange);
                actionPanel.add(graph);
                graph.setVisible(true);
                timeButtonsPanel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");

        }
    }

    public void artistListGrapher(String timeRange) {
        String artist_num = numTracksTextField.getText();
        try {
            int trNum = Integer.parseInt(artist_num);
            if (trNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Invalid amount of tracks");
            } else {
                actionPanel.removeAll();
                upperPanelDefault();
                javax.swing.JScrollPane graph = Statify.createTopArtistsPanel(trNum, timeRange);
                actionPanel.add(graph);
                graph.setVisible(true);
                timeButtonsPanel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        }
    }

    public void genreListGrapher(String timeRange){
        // TODO later
    }

    public void chooseTypeTopList(String timeRange){
        if(buttonFlag.equals("tracks")){
            trackListGrapher(timeRange);
        }
        else if(buttonFlag.equals("artist")){
            artistListGrapher(timeRange);
        }
        // else if(buttonFlag.equals("genre")){
        //     genreListGrapher(timeRange);
        // }
        else{
            // actionPanel.removeAll();
            panelsSetDefault();
            addNewPanel(planeBasePanel);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        tracksAnaliseButton = new javax.swing.JButton();
        playlistAnaliseButton = new javax.swing.JButton();
        generatePlaylistButton = new javax.swing.JButton();
        bottomSidePanel = new javax.swing.JPanel();
        homeButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        centrePanel = new GradientPanel(widthF, heightF);
        actionButtonsPanel = new javax.swing.JPanel();
        optionsPanel = new javax.swing.JPanel();
        laundnessButton = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Statify PAP23L edition");
        setMinimumSize(new java.awt.Dimension(830, 440));

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
        artistButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons8-musician-male-32.png"))); // NOI18N
        artistButton.setText("artists");
        artistButton.setBorder(null);
        artistButton.setMaximumSize(new java.awt.Dimension(80, 24));
        artistButton.setMinimumSize(new java.awt.Dimension(80, 24));
        artistButton.setPreferredSize(new java.awt.Dimension(80, 24));
        artistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                artistButtonActionPerformed(evt);
            }
        });

        genreButton.setBackground(new java.awt.Color(44, 51, 51));
        genreButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        genreButton.setForeground(new java.awt.Color(255, 255, 255));
        genreButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons8-music-record-32.png"))); // NOI18N
        genreButton.setText("genre");
        genreButton.setBorder(null);
        genreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genreButtonActionPerformed(evt);
            }
        });

        topTracksButton.setBackground(new java.awt.Color(44, 51, 51));
        topTracksButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        topTracksButton.setForeground(new java.awt.Color(255, 255, 255));
        topTracksButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons8-volume-level-32.png"))); // NOI18N
        topTracksButton.setText("top tracks");
        topTracksButton.setBorder(null);
        topTracksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topTracksButtonActionPerformed(evt);
            }
        });

        tracksAnaliseButton.setBackground(new java.awt.Color(44, 51, 51));
        tracksAnaliseButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        tracksAnaliseButton.setForeground(new java.awt.Color(255, 255, 255));
        tracksAnaliseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons8-sample-rate-32.png"))); // NOI18N
        tracksAnaliseButton.setText("analise tracks");
        tracksAnaliseButton.setBorder(null);
        tracksAnaliseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tracksAnaliseButtonActionPerformed(evt);
            }
        });

        playlistAnaliseButton.setBackground(new java.awt.Color(44, 51, 51));
        playlistAnaliseButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        playlistAnaliseButton.setForeground(new java.awt.Color(255, 255, 255));
        playlistAnaliseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons8-audio-wave2-32.png"))); // NOI18N
        playlistAnaliseButton.setText("analise playlist");
        playlistAnaliseButton.setBorder(null);
        playlistAnaliseButton.setMaximumSize(new java.awt.Dimension(80, 24));
        playlistAnaliseButton.setMinimumSize(new java.awt.Dimension(80, 24));
        playlistAnaliseButton.setPreferredSize(new java.awt.Dimension(80, 24));
        playlistAnaliseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playlistAnaliseButtonActionPerformed(evt);
            }
        });

        generatePlaylistButton.setBackground(new java.awt.Color(44, 51, 51));
        generatePlaylistButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        generatePlaylistButton.setForeground(new java.awt.Color(255, 255, 255));
        generatePlaylistButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons8-music-library-32.png"))); // NOI18N
        generatePlaylistButton.setText("generate playlist");
        generatePlaylistButton.setBorder(null);
        generatePlaylistButton.setMaximumSize(new java.awt.Dimension(80, 24));
        generatePlaylistButton.setMinimumSize(new java.awt.Dimension(80, 24));
        generatePlaylistButton.setPreferredSize(new java.awt.Dimension(80, 24));
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
                .addGroup(chooseActionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(playlistAnaliseButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tracksAnaliseButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(topTracksButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(genreButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(artistButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(generatePlaylistButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        chooseActionPanelLayout.setVerticalGroup(
            chooseActionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chooseActionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(artistButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(genreButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(topTracksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tracksAnaliseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(playlistAnaliseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(generatePlaylistButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
        homeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icons8-equal-housing-opportunity-32.png"))); // NOI18N
        homeButton.setText("HOME");
        homeButton.setBorder(null);
        homeButton.setHideActionText(true);
        homeButton.setMaximumSize(new java.awt.Dimension(180, 60));
        homeButton.setMinimumSize(new java.awt.Dimension(180, 60));
        homeButton.setPreferredSize(new java.awt.Dimension(180, 60));
        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeButtonActionPerformed(evt);
            }
        });
        bottomSidePanel.add(homeButton);
        homeButton.setBounds(12, 20, 180, 60);

        sidePanel.add(bottomSidePanel, java.awt.BorderLayout.PAGE_END);

        planeRoot.add(sidePanel, java.awt.BorderLayout.WEST);

        mainPanel.setPreferredSize(new java.awt.Dimension(750, 540));
        mainPanel.setLayout(new java.awt.BorderLayout());

        centrePanel.setLayout(new java.awt.BorderLayout());
        centrePanel.setVisible(true);
        actionButtonsPanel.setPreferredSize(new java.awt.Dimension(20, 120));
        actionButtonsPanel.setLayout(new java.awt.CardLayout());

        optionsPanel.setMinimumSize(new java.awt.Dimension(620, 120));
        optionsPanel.setPreferredSize(new java.awt.Dimension(20, 120));
        java.awt.GridBagLayout optionsPanelLayout = new java.awt.GridBagLayout();
        optionsPanelLayout.columnWidths = new int[] {0, 40, 0, 40, 0};
        optionsPanelLayout.rowHeights = new int[] {0, 10, 0};
        optionsPanel.setLayout(optionsPanelLayout);

        laundnessButton.setBackground(new java.awt.Color(44, 51, 51));
        laundnessButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        laundnessButton.setForeground(new java.awt.Color(255, 255, 255));
        laundnessButton.setText("loudness");
        laundnessButton.setToolTipText("");
        laundnessButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        laundnessButton.setMaximumSize(new java.awt.Dimension(130, 54));
        laundnessButton.setMinimumSize(new java.awt.Dimension(130, 54));
        laundnessButton.setPreferredSize(new java.awt.Dimension(130, 54));
        laundnessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laundnessButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        optionsPanel.add(laundnessButton, gridBagConstraints);

        danceabilityButton.setBackground(new java.awt.Color(44, 51, 51));
        danceabilityButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        danceabilityButton.setForeground(new java.awt.Color(255, 255, 255));
        danceabilityButton.setText("danceability");
        danceabilityButton.setMaximumSize(new java.awt.Dimension(130, 54));
        danceabilityButton.setMinimumSize(new java.awt.Dimension(130, 54));
        danceabilityButton.setPreferredSize(new java.awt.Dimension(130, 54));
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
        acousticnessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acousticnessButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        optionsPanel.add(acousticnessButton, gridBagConstraints);

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
        gridBagConstraints.gridwidth = 5;
        optionsPanel.add(numPlaylistTextField, gridBagConstraints);

        actionButtonsPanel.add(optionsPanel, "card2");

        timeButtonsPanel.setMinimumSize(new java.awt.Dimension(620, 120));
        timeButtonsPanel.setPreferredSize(new java.awt.Dimension(620, 120));
        java.awt.GridBagLayout timeButtonsPanelLayout = new java.awt.GridBagLayout();
        timeButtonsPanelLayout.columnWidths = new int[] {0, 40, 0, 40, 0};
        timeButtonsPanelLayout.rowHeights = new int[] {0, 10, 0};
        timeButtonsPanel.setLayout(timeButtonsPanelLayout);

        weeks4Button.setBackground(new java.awt.Color(44, 51, 51));
        weeks4Button.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        weeks4Button.setForeground(new java.awt.Color(255, 255, 255));
        weeks4Button.setText("4 weeks");
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
        allTimeHistoryButton.setMaximumSize(new java.awt.Dimension(130, 54));
        allTimeHistoryButton.setMinimumSize(new java.awt.Dimension(130, 54));
        allTimeHistoryButton.setPreferredSize(new java.awt.Dimension(130, 54));
        allTimeHistoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allTimeHistoryButtonActionPerformed(evt);
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

        actionButtonsPanel.add(timeButtonsPanel, "card2");

        planeUpperPanel.setMinimumSize(new java.awt.Dimension(620, 120));

        javax.swing.GroupLayout planeUpperPanelLayout = new javax.swing.GroupLayout(planeUpperPanel);
        planeUpperPanel.setLayout(planeUpperPanelLayout);
        planeUpperPanelLayout.setHorizontalGroup(
            planeUpperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );
        planeUpperPanelLayout.setVerticalGroup(
            planeUpperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        actionButtonsPanel.add(planeUpperPanel, "card3");
        actionButtonsPanel.setVisible(true);

        centrePanel.add(actionButtonsPanel, java.awt.BorderLayout.NORTH);

        actionPanel.setPreferredSize(new java.awt.Dimension(750, 420));
        actionPanel.setLayout(new java.awt.CardLayout());

        creditsPanel.setMinimumSize(new java.awt.Dimension(620, 320));
        creditsPanel.setLayout(new java.awt.GridLayout(1, 0));

        creditsLabel.setText(
            "<html><font color='white'><p>&emsp; &emsp; Statify PAP 23L</p>\n" +
            "<p><br></p>\n" +
            "<p>&emsp; &emsp; developers:</p>\n" +
            "<p>&emsp; &emsp; &ensp; Milosz Kowalewski</p>\n" +
            "<p>&emsp; &emsp; &ensp; Julia Macuga</p>\n" +
            "<p>&emsp; &emsp; &ensp; Filip Szczygielski</p>\n" +
            "<p>&emsp; &emsp; &ensp; Mikolaj Wewior</p>\n" +
            "<p><br></p>\n" +
            "<p>&emsp; &emsp; version 1.2 (pretty stable)</p></font></html>"
        );

        creditsLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        creditsLabel.setOpaque(false);
        creditsPanel.add(creditsLabel, java.awt.BorderLayout.CENTER);

        creditsPanel.add(creditsLabel);

        actionPanel.add(creditsPanel, "card0");

        planeBasePanel.setMinimumSize(new java.awt.Dimension(620, 320));

        javax.swing.GroupLayout planeBasePanelLayout = new javax.swing.GroupLayout(planeBasePanel);
        planeBasePanel.setLayout(planeBasePanelLayout);
        planeBasePanelLayout.setHorizontalGroup(
            planeBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );
        planeBasePanelLayout.setVerticalGroup(
            planeBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );

        actionPanel.add(planeBasePanel, "card0");

        centrePanel.add(actionPanel, java.awt.BorderLayout.CENTER);

        mainPanel.add(centrePanel, java.awt.BorderLayout.CENTER);

        planeRoot.add(mainPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(planeRoot, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        panelsSetDefault();
        addNewPanel(creditsPanel);
        creditsLabel.setVisible(true);
    }//GEN-LAST:event_homeButtonActionPerformed

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

    private void laundnessButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_optionButton1ActionPerformed
        String playlists_num = numPlaylistTextField.getText();
        try {
            int plNum = Integer.parseInt(playlists_num);
            if (plNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Invalid amount of playlists");
            } else {
                actionPanel.removeAll();
                Statify.SetPlaylistsNum(plNum);
                upperPanelDefault();
                JPanel histo = Statify.getLoudnessHistogram();
                panelInit(histo);
                addNewPanel(histo);
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
        try {
            int plNum = Integer.parseInt(playlists_num);
            if (plNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Invalid amount of playlists");
            } else {
                actionPanel.removeAll();
                Statify.SetPlaylistsNum(plNum);
                upperPanelDefault();
                JPanel histo = Statify.getDanceabilityHistogram();
                panelInit(histo);
                histo.setVisible(true);
                optionsPanel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");

        }
        // trzeba to jesszcze jakoś wyłączyć potem

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
                JPanel histo = Statify.getAcousticnessHistogram();
                panelInit(histo);
                histo.setVisible(true);
                optionsPanel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Amount of playlists has to be a whole number");
        }
    }// GEN-LAST:event_optionButton3ActionPerformed

    private void weeks4ButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_weeks4ButtonActionPerformed
        chooseTypeTopList("short_term");
        // if(buttonFlag.equals("tracks")){
        //     trackListGrapher("short_term");
        // }
        // else if(buttonFlag.equals("artist")){
        //     artistListGrapher("short_term");
        // }
        // // else if(buttonFlag.equals("artist")){
        // //     genreListGrapher("short_term");
        // // }
        // else{
        //     panelsSetDefault();
        // }
        }// GEN-LAST:event_weeks4ButtonActionPerformed

    private void months6ButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_months6ButtonActionPerformed
        chooseTypeTopList("medium_term");
    }// GEN-LAST:event_months6ButtonActionPerformed

    private void allTimeHistoryButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_allTimeHistoryButtonActionPerformed
        chooseTypeTopList("long_term");
    }// GEN-LAST:event_allTimeHistoryButtonActionPerformed

    private void artistButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_artistButtonActionPerformed
        buttonFlag = "artist";
        panelsSetDefault();
        timeButtonsPanel.setVisible(true);
        // actionPanel.removeAll();
    }// GEN-LAST:event_artistButtonActionPerformed

    private void genreButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_genreButtonActionPerformed
        buttonFlag = "genre";
        panelsSetDefault();
        timeButtonsPanel.setVisible(true);
    }// GEN-LAST:event_genreButtonActionPerformed

    private void topTracksButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_topTracksButtonActionPerformed
        buttonFlag = "tracks";
        panelsSetDefault();
        timeButtonsPanel.setVisible(true);
    }// GEN-LAST:event_topTracksButtonActionPerformed

    private void tracksAnaliseButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tracksAnaliseButtonActionPerformed
        panelsSetDefault();
        optionsPanel.setVisible(true);
    }// GEN-LAST:event_tracksAnaliseButtonActionPerformed

    private void playlistAnaliseButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_playlistAnaliseButtonActionPerformed
        panelsSetDefault();
        optionsPanel.setVisible(true);
    }// GEN-LAST:event_playlistAnaliseButtonActionPerformed

    private void generatePlaylistButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatePlaylistButtonActionPerformed
        panelsSetDefault();
        optionsPanel.setVisible(true);
    }// GEN-LAST:event_generatePlaylistButtonActionPerformed

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
                System.out.println(mainFrame.getHeight() + "   " + mainFrame.getWidth());
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
    private javax.swing.JButton generatePlaylistButton;
    private javax.swing.JButton genreButton;
    private javax.swing.JButton homeButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton laundnessButton;
    private javax.swing.JPanel logoPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton months6Button;
    private javax.swing.JTextField numPlaylistTextField;
    private javax.swing.JTextField numTracksTextField;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JPanel planeBasePanel;
    private javax.swing.JPanel planeRoot;
    private javax.swing.JPanel planeUpperPanel;
    private javax.swing.JButton playlistAnaliseButton;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JPanel timeButtonsPanel;
    private javax.swing.JButton topTracksButton;
    private javax.swing.JButton tracksAnaliseButton;
    private javax.swing.JPanel typeActionPanel;
    private javax.swing.JButton weeks4Button;
    private String buttonFlag;
    // End of variables declaration//GEN-END:variables
}
