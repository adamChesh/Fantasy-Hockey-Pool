package ui;

import model.FantasyPool;
import model.FantasyTeam;
import model.Player;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// launches a fantasy hockey application that can be interacted with
public class FantasyPoolInteraction {
    FantasyPool fantasyPool;
    Scanner input = new Scanner(System.in);
    JsonWriter writer = new JsonWriter("./data/fantasypool.json");
    JsonReader reader = new JsonReader("./data/fantasypool.json");

    // EFFECTS: launches the fantasy pool manager
    public FantasyPoolInteraction() {
        getFantasyPool();
        System.out.println("\n");
        create();
    }

    // EFFECTS: creates the initial menu screen with numbered interactions
    public void create() {
        System.out.println("Welcome to The 2020/21 Hockey Fantasy Pool");
        String selection;
        do {
            createMainMenu("please select an option:", "1. Leaderboard", "2. View Teams", "3. Edit Teams",
                    "4. Add Teams", "5. Remove Teams", "6. Save Fantasy Pool", "7. Exit");
            selection = input.nextLine();
            if (selection.equals("1")) {
                viewLeaderboard();
            } else if (selection.equals("2")) {
                viewTeams();
            } else if (selection.equals("3")) {
                editTeamsInPool();
            } else if (selection.equals("4")) {
                addTeamToPool();
            } else if (selection.equals("5")) {
                removeTeamFromPool();
            } else if (selection.equals("6")) {
                saveFantasyPoolWasSelected();
            }
        } while (!selection.equals("7"));
    }

    // EFFECTS: saves the fantasy pool and tells you that is saved
    private void saveFantasyPoolWasSelected() {
        try {
            saveFantasyPool();
            System.out.println("\n Successfully Saved! \n");
        } catch (FileNotFoundException f) {
            System.out.println("File was not found!");
        }
    }

    // EFFECTS: retrieves the previous saved fantasy pool
    public void getFantasyPool() {
        try {
            loadFantasyPool();
            System.out.println("Previous fantasy pool was loaded!");
        } catch (IOException i) {
            System.out.println("Error loading previous fantasy pool. Creating new fantasy pool");
            fantasyPool = new FantasyPool("2020/21 Hockey Fantasy Pool");
        }
    }

    // METHODS: this
    // EFFECTS: reads the JSON file and retrieves the last fantasy pool saved, if an error occurs reading the file
    //          then IOException was thrown
    public void loadFantasyPool() throws IOException {
        fantasyPool = reader.read();
    }

    // EFFECTS: saves the fantasy pool to a JSON file, if file does not exist then throws FileNotFoundException
    public void saveFantasyPool() throws FileNotFoundException {
        writer.open();
        writer.write(fantasyPool);
        writer.close();
    }

    // EFFECTS: creates the main menu screen
    public void createMainMenu(String s0, String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
        System.out.println(s0);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s5);
        System.out.println(s6);
        System.out.println(s7);
    }

    // EFFECTS: will print out a string of the leaderboard in ascending order by points
    public void viewLeaderboard() {
        System.out.println(this.fantasyPool.produceLeaderboard());
    }

    // EFFECTS: will print out a string of all the teams along with their players
    public void viewTeams() {
        System.out.println(this.fantasyPool.producePool());
    }

    // EFFECTS: produces the list of teams to choose from, then the list of players within that team to choose
    //          from, then allows you to update the selected players; goals, assists, or penalty minutes
    public void editTeamsInPool() {
        if (this.fantasyPool.getFantasyPool().size() == 0) {
            return;
        } else {
            int i = produceTeams();
            System.out.println("You selected: " + fantasyPool.getFantasyPool().get(i).getTeamName());
            int x = produceEditTeamsMenu();
            if (x == 0) {
                addPlayerToTeam(i);
            } else if (x == 1) {
                removePlayerFromTeam(i);
            } else if (x == 2) {
                editPlayerFromTeam(i);
            }
        }
    }

    // EFFECTS: produces list of players to choose from, once chosen it then allows you to edit the
    //          players goals, assists, or penalty minute tallies
    private void editPlayerFromTeam(int i) {
        System.out.println("Select which player you would like to update");
        for (int k = 0; k < fantasyPool.getFantasyPool().get(i).getRoster().size(); k++) {
            System.out.println(k + ": "
                    + fantasyPool.getFantasyPool().get(i).getRoster().get(k).getPlayerName());
        }
        int y = input.nextInt();
        System.out.println("You have selected "
                + fantasyPool.getFantasyPool().get(i).getRoster().get(y).getPlayerName());
        int h = produceEditPlayersMenu();
        if (h == 0) {
            resetPlayersGoals(i, y);
        } else if (h == 1) {
            resetPlayersAssists(i, y);
        } else if (h == 2) {
            resetPlayersPenaltyMinutes(i, y);
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the goal tally for the chosen player
    private void resetPlayersGoals(int i, int y) {
        System.out.println("How many goals has he scored?");
        int g = input.nextInt();
        fantasyPool.getFantasyPool().get(i).getRoster().get(y).setGoals(g);
    }

    // MODIFIES: this
    // EFFECTS: resets the assist tally for the chosen player
    private void resetPlayersAssists(int i, int y) {
        System.out.println("How many assists did he have?");
        int a = input.nextInt();
        fantasyPool.getFantasyPool().get(i).getRoster().get(y).setAssists(a);
    }

    // MODIFIES: this
    // EFFECTS: resets the penalty minutes tally for the chosen player
    private void resetPlayersPenaltyMinutes(int i, int y) {
        System.out.println("How many penalty minutes did he have?");
        int pim = input.nextInt();
        fantasyPool.getFantasyPool().get(i).getRoster().get(y).setPenaltyMinutes(pim);
    }

    // EFFECTS: produces the menu giving all options of fields that can be updated for the player, as well as
    //          tracks the next selection
    public int produceEditPlayersMenu() {
        int i;
        System.out.println("0: Goals");
        System.out.println("1: Assists");
        System.out.println("2: Penalty Minutes");
        i = input.nextInt();
        input.nextLine();

        return i;
    }

    // MODIFIES: this
    // EFFECTS: adds a player to the chosen team by filling the fields with the the answers to the
    //          prompted questions
    private void addPlayerToTeam(int i) {
        System.out.println("What is the name of the player");
        String pn = input.nextLine();
        System.out.println("What team does he play for?");
        String tn = input.nextLine();
        System.out.println("How many goals has he scored?");
        int g = input.nextInt();
        System.out.println("How many goals has he assisted?");
        int a = input.nextInt();
        System.out.println("How many penalty minutes has he gotten?");
        int pim = input.nextInt();
        input.nextLine();
        fantasyPool.getFantasyPool().get(i).addPlayer(new Player(pn, tn, g, a, pim));
    }

    // MODIFIES: this
    // EFFECTS: removes a player from the chosen team
    private void removePlayerFromTeam(int i) {
        System.out.println("Please select which player you would like to remove");
        for (int p = 0; p < fantasyPool.getFantasyPool().get(i).getRoster().size(); p++) {
            System.out.println(p + ":" + " "
                    + fantasyPool.getFantasyPool().get(i).getRoster().get(p).getPlayerName());
        }
        int t = input.nextInt();
        fantasyPool.getFantasyPool().get(i).getRoster().remove(t);
    }


    // EFFECTS: produces the menu with all the edit team options
    public int produceEditTeamsMenu() {
        int x;
        System.out.println("0: Add");
        System.out.println("1: Remove");
        System.out.println("2: Update Player Stats");
        x = input.nextInt();
        input.nextLine();

        return x;
    }


    // MODIFIES: this
    // EFFECTS: creates a add team menu, prompts you to create a new team and add as many players
    //          you'd like
    public void addTeamToPool() {
        insertTeamIntoPool();
        int i = createAddTeamsMenu();
        while (i == 1) {
            System.out.println("What is the name of the player");
            String pn = input.nextLine();
            System.out.println("What team does he play for?");
            String tn = input.nextLine();
            System.out.println("How many goals has he scored?");
            int g = input.nextInt();
            System.out.println("How many goals has he assisted?");
            int a = input.nextInt();
            System.out.println("How many penalty minutes has he gotten?");
            int pim = input.nextInt();
            input.nextLine();
            fantasyPool.getFantasyPool().get(fantasyPool.getFantasyPool().size() - 1).addPlayer(
                    new Player(pn, tn, g, a, pim));
            i = createAddTeamsMenu();
        }
    }


    // EFFECTS: Creates the menu for whether you would like to add another player or not,
    //          as well as tracks the next selection
    private int createAddTeamsMenu() {
        int s;
        System.out.println("1: Add another player");
        System.out.println("2: Finish");
        s = input.nextInt();
        input.nextLine();

        return s;
    }


    // MODIFIES: this
    // EFFECTS: insert a single team with a player into the fantasy pool
    public void insertTeamIntoPool() {
        System.out.println("What will be the name of your team?");
        String fantasyTeamName = input.nextLine();
        System.out.println("What is the name of the player");
        String pn = input.nextLine();
        System.out.println("What team does he play for?");
        String tn = input.nextLine();
        System.out.println("How many goals has he scored?");
        int g = input.nextInt();
        System.out.println("How many goals has he assisted?");
        int a = input.nextInt();
        System.out.println("How many penalty minutes has he gotten?");
        int pim = input.nextInt();
        input.nextLine();
        fantasyPool.addFantasyTeam(new FantasyTeam(fantasyTeamName));
        for (int i = 0; i < fantasyPool.getFantasyPool().size(); i++) {
            if (fantasyTeamName.equals(fantasyPool.getFantasyPool().get(i).getTeamName())) {
                fantasyPool.getFantasyPool().get(i).addPlayer(
                        new Player(pn, tn, g, a, pim));
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: will produce a menu of all teams and remove selected team from pool
    public void removeTeamFromPool() {
        if (this.fantasyPool.getFantasyPool().size() == 0) {
            return;
        } else {
            int i = produceTeams();
            if (i < 0 || i >= this.fantasyPool.getFantasyPool().size()) {
                return;
            } else {
                this.fantasyPool.removeFantasyTeam(fantasyPool.getFantasyPool().get(i));
            }
        }
    }

    // EFFECTS: produce the menu of all the teams as well as track the next input
    public int produceTeams() {
        int i;
        System.out.println("Please select a team: ");

        for (int x = 0; x < this.fantasyPool.getFantasyPool().size(); x++) {
            System.out.println(x + ":" + " " + this.fantasyPool.getFantasyPool().get(x).getTeamName());
        }
        i = input.nextInt();

        return i;
    }

}
