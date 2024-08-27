package persistence;

import model.FantasyPool;
import model.FantasyTeam;
import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads FantasyPool from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader that reads FantasyPool in JSON file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads FantasyPool from file and returns it, if an error occurs reading the file then
    //          throws the IOException
    public FantasyPool read() throws IOException {
        String jsonData = readFile(source);
        JSONObject json = new JSONObject(jsonData);
        return parseFantasyPool(json);
    }

    // EFFECTS: read the source file as a string and the returns it, if an error occurs reading the file then
    //          throws the IOException
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses FantasyPool from JSON object and returns it
    public FantasyPool parseFantasyPool(JSONObject jsonObject) {
        String name = jsonObject.getString("NameOfPool");
        FantasyPool fantasyPool = new FantasyPool(name);
        parseFantasyTeams(fantasyPool, jsonObject);
        return fantasyPool;
    }

    // MODIFIES: fantasyPool
    // EFFECTS: parses a list of fantasy teams and adds them to a fantasy pool
    private void parseFantasyTeams(FantasyPool fantasyPool, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("FantasyTeams");
        for (Object json : jsonArray) {
            JSONObject nextFantasyTeam = (JSONObject) json;
            parseFantasyTeam(fantasyPool, nextFantasyTeam);
        }
    }

    // MODIFIES: fantasyPool
    // EFFECTS: parses FantasyTeam from JSON object and adds it to a fantasy pool
    private void parseFantasyTeam(FantasyPool fantasyPool, JSONObject jsonObject) {
        String fantasyName = jsonObject.getString("TeamName");
        FantasyTeam fantasyTeam = new FantasyTeam(fantasyName);
        parsePlayers(fantasyTeam, jsonObject);
        fantasyTeam.totalFantasyTeamPoints();
        fantasyPool.addFantasyTeam(fantasyTeam);
    }


    // MODIFIES: fantasyTeam
    // EFFECTS: parses a list of players from JSON object and adds them to a fantasy team
    private void parsePlayers(FantasyTeam fantasyTeam, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            parsePlayer(fantasyTeam, nextPlayer);
        }
    }

    // MODIFIES: fantasyTeam
    // EFFECTS: Parses Player from JSON object and adds it to a fantasy team
    private void parsePlayer(FantasyTeam fantasyTeam, JSONObject jsonObject) {
        String playerName = jsonObject.getString("PlayerName");
        String team = jsonObject.getString("PlayerTeam");
        int goals = jsonObject.getInt("Goals");
        int assists = jsonObject.getInt("Assists");
        int penaltyMinutes = jsonObject.getInt("PenaltyMinutes");
        Player player = new Player(playerName, team, goals, assists, penaltyMinutes);
        fantasyTeam.addPlayer(player);
    }

}

