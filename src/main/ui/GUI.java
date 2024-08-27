package ui;

import model.FantasyPool;
import model.FantasyTeam;
import model.Player;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

// Creates a graphical user interface for the Hockey Fantasy Pool
public class GUI extends JFrame {
    private FantasyPool fantasyPool;
    private FantasyTeam fantasyTeam;
    private JsonWriter writer;
    private JsonReader reader;
    private Vector<String> players;
    private JList jlistOfPlayers;
    private JScrollPane playerScrollPane;
    private ButtonSoundEffect soundEffect = new ButtonSoundEffect();

    private JButton saveButton;
    private JButton addPlayerButton;
    private JButton removePlayerButton;
    private JButton loadButton;

    private JTextField playerNameField;
    private JTextField teamNameField;
    private JTextField goalField;
    private JTextField assistField;
    private JTextField penaltyMinutesField;

    // EFFECTS: constructs a GUI for the hockey fantasy pool
    public GUI() {
        super("Fantasy Team");
        createGUI();
    }

    // EFFECTS: launches the hockey fantasy pool GUI
    public static void main(String[] args) {
        new GUI();
    }

    // EFFECTS: initializes the frame, and fantasy pool, adds the panel to the frame
    public void createGUI() {
        //setup
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = new Dimension(1165, 765);
        setPreferredSize(size);
        setMinimumSize(size);
        setResizable(false);

        //add comps
        addComponentsToPanel();
        createTeam();

        //finish
        setVisible(true);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: initializes all the fantasy pool and fantasy team that the players will be added to
    public void createTeam() {
        fantasyPool = new FantasyPool("Fantasy Pool");
        fantasyTeam = new FantasyTeam("Fantasy Team");
        fantasyPool.addFantasyTeam(fantasyTeam);
    }

    // EFFECTS: adds all the components onto the panel
    public void addComponentsToPanel() {
        JPanel panel = new JPanel();
        add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addList(panel);

        addTextWindow("Player name", panel);
        addTextWindow("Team Name", panel);
        addTextWindow("Goals", panel);
        addTextWindow("Assists", panel);
        addTextWindow("PIM", panel);

        addAddPlayerButton("Add Player", panel);
        addRemovePlayerButton("Remove Player", panel);

        addSaveButton(panel);
        addLoadButton("Load", panel);
    }

    // MODIFIES: this
    // EFFECTS: create button, adds add player functionality, and adds it to the panel
    public void addAddPlayerButton(String text, JPanel panel) {
        addPlayerButton = new JButton(text);
        addPlayerButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        addPlayerButton.addActionListener(this::addPlayerButtonClicked);
        panel.add(addPlayerButton);
    }

    // MODIFIES: this
    // EFFECTS: create button, adds remove player functionality, and adds it to the panel
    public void addRemovePlayerButton(String text, JPanel panel) {
        removePlayerButton = new JButton(text);
        removePlayerButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        removePlayerButton.addActionListener(this::removePlayerButtonClicked);
        panel.add(removePlayerButton);
    }

    // MODIFIES: this
    // EFFECTS: create button, adds load list functionality, and adds it to the panel
    public void addLoadButton(String text, JPanel panel) {
        loadButton = new JButton(text);
        loadButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        loadButton.addActionListener(this::loadButtonClicked);
        panel.add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: create button, adds save functionality, and adds it to the panel
    public void addSaveButton(JPanel panel) {
        saveButton = new JButton("Save");
        saveButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        saveButton.addActionListener(this::saveButtonClicked);
        panel.add(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: when box is clicked, creates a new player taking the info from the text boxes to construct it,
    //          then adds player to fantasy team and refreshes list
    public void addPlayerButtonClicked(ActionEvent e) {
        jlistOfPlayers.setBackground(Color.black);
        jlistOfPlayers.setForeground(Color.white);

        String playerName = playerNameField.getText();
        String teamName = teamNameField.getText();
        int goals = Integer.parseInt(goalField.getText());
        int assists = Integer.parseInt(assistField.getText());
        int pim = Integer.parseInt(penaltyMinutesField.getText());

        Player newPlayer = new Player(playerName, teamName, goals, assists, pim);
        fantasyPool.getFantasyPool().get(0).addPlayer(newPlayer);

        soundEffect.setFile("./Res/added.wav");
        soundEffect.play();

        refreshList();
    }

    // MODIFIES: this
    // EFFECTS: when clicked, removes the selected player from the list and fantasy team
    public void removePlayerButtonClicked(ActionEvent e) {
        fantasyPool.getFantasyPool().get(0).getRoster().remove(jlistOfPlayers.getSelectedIndex());
        soundEffect.setFile("./Res/delete.wav");
        soundEffect.play();
        refreshList();
    }

    // MODIFIES: this
    // EFFECTS: when clicked, saves the fantasy pool
    public void saveButtonClicked(ActionEvent e) {
        try {
            writer = new JsonWriter("./data/fantasypool.json");
            writer.open();
            writer.write(fantasyPool);
            writer.close();
            soundEffect.setFile(".//Res/ding.wav.wav");
            soundEffect.play();
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: when clicked, loads the previous fantasy pool, then refreshes list
    public void loadButtonClicked(ActionEvent e) {
        reader = new JsonReader("./data/fantasypool.json");
        try {
            fantasyPool = reader.read();
            soundEffect.setFile("./Res/loading.wav");
            soundEffect.play();
        } catch (IOException i) {
            i.printStackTrace();
        }
        refreshList();
    }

    // MODIFIES: this
    // EFFECTS: cycles through the fantasy team and refreshes list
    public void refreshList() {
        players.clear();
        for (Player player : fantasyPool.getFantasyPool().get(0).getRoster()) {
            players.add(playerToString(player));
        }
        playerNameField.setText("");
        teamNameField.setText("");
        goalField.setText("");
        assistField.setText("");
        penaltyMinutesField.setText("");
        jlistOfPlayers.updateUI();
    }

    // MODIFIES: this
    // EFFECTS: creates a list, adds the vector of players to list, then adds list to panel
    public void addList(JPanel panel) {
        players = new Vector<>();
        jlistOfPlayers = new JList(players);

        jlistOfPlayers.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        jlistOfPlayers.setVisibleRowCount(10);
        jlistOfPlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        playerScrollPane = new JScrollPane(jlistOfPlayers);
        jlistOfPlayers.updateUI();
        panel.add(playerScrollPane);

    }

    // MODIFIES: this
    // EFFECTS: creates a text window with a label of the given field above it, then adds it to the panel
    public void addTextWindow(String category, JPanel panel) {
        JTextField playerInfo = new JTextField(30);
        if (category.equals("Player name")) {
            playerNameField = playerInfo;
        } else if (category.equals("Assists")) {
            assistField = playerInfo;
        } else if (category.equals("Team Name")) {
            teamNameField = playerInfo;
        } else if (category.equals("Goals")) {
            goalField = playerInfo;
        } else {
            penaltyMinutesField = playerInfo;
        }
        JLabel playerField = new JLabel(category);
        playerInfo.setSize(10, 20);
        playerField.setLabelFor(playerInfo);

        playerField.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        panel.add(playerField);
        panel.add(playerInfo);
    }

    // EFFECTS: gets all the information from the player and displays it in a string
    public String playerToString(Player player) {
        String name = player.getPlayerName();
        String breaks = "  |  ";
        String team = player.getTeam();
        String goals = Integer.toString(player.getGoals());
        String assists = Integer.toString(player.getAssists());
        String pim = Integer.toString(player.getPenaltyMinutes());

        return name + breaks + team + breaks + goals + breaks + assists + breaks + pim;
    }

    // creates a sound effect for a button
    public class ButtonSoundEffect {
        Clip clip;

        // EFFECTS: takes the audio clip and turns it into a file that can be played
        public void setFile(String fileName) {
            try {
                File file = new File(fileName);
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(sound);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // EFFECTS: plays the given sound file
        public void play() {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}