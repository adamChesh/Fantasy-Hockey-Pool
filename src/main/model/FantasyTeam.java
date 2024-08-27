package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a fantasy hockey team with a name and a roster of players
public class FantasyTeam implements Comparable<FantasyTeam>, Writable {
    public static int POINTS_FOR_GOALS = 3;
    public static int POINTS_FOR_ASSISTS = 2;
    public static int POINTS_FOR_PIM = 1;

    String teamName;
    List<Player> roster;
    int points;

    // REQUIRES: points >= 0
    // EFFECTS: creates a fantasy team that consists of a team name, team roster,
    //          and amount of points they have
    public FantasyTeam(String teamName) {
        this.teamName = teamName;
        this.roster = new ArrayList<>();
        points = 0;
    }

    // EFFECTS: returns the name of the team
    public String getTeamName() {
        return teamName;
    }

//    public void setTeamName(String teamName) {
//        this.teamName = teamName;
//    }

    // EFFECTS: returns the points the team has
    public int getPoints() {
        return points;
    }

    // EFFECTS: returns the roster of players of the team
    public List<Player> getRoster() {
        return roster;
    }

    // MODIFIES: this
    // EFFECTS: adds a player to the roster
    public void addPlayer(Player player) {
        roster.add(player);
    }

    // MODIFIES: this
    // EFFECTS: removes a player from the roster
    public void removePlayer(Player player) {
        roster.remove(player);
    }

    // EFFECTS: returns the fantasy points the team has from goals
    public int teamPointsForGoals() {
        int teamGoals = 0;
        for (Player p : roster) {
            teamGoals += p.goals;
        }
        return teamGoals * POINTS_FOR_GOALS;
    }

    // EFFECTS: returns the fantasy points the team has from assists
    public int teamPointsForAssists() {
        int teamAssists = 0;
        for (Player p : roster) {
            teamAssists += p.assists;
        }
        return teamAssists * POINTS_FOR_ASSISTS;
    }

    // EFFECTS: returns the fantasy points the team has from penalty minutes
    public int teamPointsForPenaltyMinutes() {
        int teamPim = 0;
        for (Player p : roster) {
            teamPim += p.penaltyMinutes;
        }
        return teamPim * POINTS_FOR_PIM;
    }

    // MODIFIES: this
    // EFFECTS: updates the total fantasy points for the team
    public void totalFantasyTeamPoints() {
        points = teamPointsForAssists() + teamPointsForGoals() + teamPointsForPenaltyMinutes();
    }

    @Override
    public int compareTo(FantasyTeam fantasyTeam) {
        totalFantasyTeamPoints();
        fantasyTeam.totalFantasyTeamPoints();
        return fantasyTeam.getPoints() - points;
    }


    // EFFECTS: returns the fantasy team as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("TeamName", teamName);

        JSONArray playerArray = new JSONArray();
        for (Player p : roster) {
            playerArray.put(p.toJson());
        }
        json.put("Players", playerArray);

        return json;
    }
}
