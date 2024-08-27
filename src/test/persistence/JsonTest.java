package persistence;

import model.FantasyTeam;
import model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPlayer(String playerName, String team, int goals, int assists, int penaltyMinutes,
                               Player player) {
        assertEquals(playerName, player.getPlayerName());
        assertEquals(team, player.getTeam());
        assertEquals(goals, player.getGoals());
        assertEquals(assists, player.getAssists());
        assertEquals(penaltyMinutes, player.getPenaltyMinutes());
    }

    protected void checkFantasyTeam(String teamName, FantasyTeam fantasyTeam) {
        assertEquals(teamName, fantasyTeam.getTeamName());
    }
}
