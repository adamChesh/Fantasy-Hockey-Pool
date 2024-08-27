package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a hockey fantasy pool that has a name and a roster of teams
public class FantasyPool implements Writable {
    List<FantasyTeam> fantasyPool;
    String nameOfPool;

    // EFFECTS: creates a fantasy pool with a name for the pool and a list of fantasy teams
    public FantasyPool(String name) {
        this.nameOfPool = name;
        this.fantasyPool = new ArrayList<>();
    }

    public String getNameOfPool() {
        return nameOfPool;
    }

    // MODIFIES: this
    // EFFECTS: adds a fantasy team to the pool
    public void addFantasyTeam(FantasyTeam team) {
        fantasyPool.add(team);
    }

    // MODIFIES: this
    // EFFECTS: removes a fantasy team from the pool
    public void removeFantasyTeam(FantasyTeam team) {
        fantasyPool.remove(team);
    }


    // EFFECTS: return the list of fantasy teams in the pool
    public List<FantasyTeam> getFantasyPool() {
        return fantasyPool;
    }

    // EFFECTS: produce a string that says the name of the pool, name of all the teams in the pool,
    //          and all the players on each team
    public String producePool() {
        String vp = nameOfPool + " with teams: ";
        vp = vp.concat("\n");
        for (FantasyTeam fantasyTeam : fantasyPool) {
            vp = vp.concat("\n" + fantasyTeam.getTeamName() + " with players: ");
            for (int i = 0; i < fantasyTeam.getRoster().size(); i++) {
                vp = vp.concat(" " + fantasyTeam.getRoster().get(i).getPlayerName() + " " + "("
                        + fantasyTeam.getRoster().get(i).getTeam() + ")" + ",");
            }
        }
        vp = vp.concat("\n");
        return vp;
    }

    // EFFECTS: produces a string of the leaderboard of the teams with most points at top, teams with
    //          least amount of points at bottom
    public String produceLeaderboard() {
        String vl = "The" + " " + nameOfPool + " " + "Leaderboard: ";
        vl = vl.concat("\n");
        Collections.sort(fantasyPool);
        for (int i = 0; i < fantasyPool.size(); i++) {
            fantasyPool.get(i).totalFantasyTeamPoints();
            vl = vl.concat("\n" + (i + 1) + ":" + " " + fantasyPool.get(i).getTeamName()
                    + " " + "- " + fantasyPool.get(i).getPoints() + " " + "points");
        }
        vl = vl.concat("\n");
        return vl;
    }

    // EFFECTS: returns the fantasy pool as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("NameOfPool", nameOfPool);

        JSONArray fantasyTeamArray = new JSONArray();
        for (FantasyTeam ft : fantasyPool) {
            fantasyTeamArray.put(ft.toJson());
        }
        json.put("FantasyTeams", fantasyTeamArray);

        return json;
    }
}
