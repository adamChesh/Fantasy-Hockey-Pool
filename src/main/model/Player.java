package model;


import org.json.JSONObject;
import persistence.Writable;

// Represents a fantasy hockey player with their statistics and info
public class Player implements Writable {
    String playerName;
    int goals;
    int assists;
    int penaltyMinutes;
    String team;

    // REQUIRES: goals >= 0, assists >= 0, penaltyMinutes >= 0
    // EFFECTS: constructs a player with their name, the team they play for
    //          and their goals, assists, penalty minutes from the season
    public Player(String playerName, String team, int goals, int assists, int penaltyMinutes) {
        this.playerName = playerName;
        this.team = team;
        this.goals = goals;
        this.assists = assists;
        this.penaltyMinutes = penaltyMinutes;
    }

    // EFFECTS: returns the name of the player
    public String getPlayerName() {
        return playerName;
    }

    // EFFECTS: returns the number of goals a player scored
    public int getGoals() {
        return goals;
    }

    // EFFECTS: returns the number of assists a player got
    public int getAssists() {
        return assists;
    }

    // EFFECTS: returns the number of penalty minutes a player received
    public int getPenaltyMinutes() {
        return penaltyMinutes;
    }

    // MODIFIES: this
    // EFFECTS: sets the amount of goals the player has to goals
    public void setGoals(int goals) {
        this.goals = goals;
    }

    // MODIFIES: this
    // EFFECTS: sets the amount of assists the player has to assists
    public void setAssists(int assists) {
        this.assists = assists;
    }

    // MODIFIES: this
    // EFFECTS: sets the amount of penalty minutes the player has to penaltyMinutes
    public void setPenaltyMinutes(int penaltyMinutes) {
        this.penaltyMinutes = penaltyMinutes;
    }

    // EFFECTS: returns the team name of that the player plays for
    public String getTeam() {
        return team;
    }


    // EFFECTS: returns the player as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("PlayerName", playerName);
        json.put("Goals", goals);
        json.put("Assists", assists);
        json.put("PenaltyMinutes", penaltyMinutes);
        json.put("PlayerTeam", team);

        return json;
    }
}
