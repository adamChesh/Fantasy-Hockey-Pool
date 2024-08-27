package model;

import org.junit.jupiter.api.Test;

import static model.FantasyTeam.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FantasyPoolTest {

    @Test
    public void testTeamPoints() {
        Player ep = new Player("Elias Pettersson", "Vancouver Canucks",
                35, 40, 10);
        Player bb = new Player("Brock Boeser", "Vancouver Canucks",
                30, 30, 15);
        Player jt = new Player("JT Miller", "Vancouver Canucks",
                35, 38, 45);
        FantasyTeam fantasyTeam = new FantasyTeam("Team 1");

        fantasyTeam.addPlayer(ep);
        fantasyTeam.addPlayer(bb);
        fantasyTeam.addPlayer(jt);

        fantasyTeam.totalFantasyTeamPoints();

        assertEquals(100 * POINTS_FOR_GOALS, fantasyTeam.teamPointsForGoals());
        assertEquals(108 * POINTS_FOR_ASSISTS, fantasyTeam.teamPointsForAssists());
        assertEquals(70 * POINTS_FOR_PIM, fantasyTeam.teamPointsForPenaltyMinutes());
        assertEquals((100 * POINTS_FOR_GOALS) + (108 * POINTS_FOR_ASSISTS) + (70 * POINTS_FOR_PIM),
                fantasyTeam.getPoints());

    }

    @Test
    public void testViewPool() {
        Player ep = new Player("Elias Pettersson", "Vancouver Canucks",
                35, 40, 10);
        Player bb = new Player("Brock Boeser", "Vancouver Canucks",
                30, 30, 15);
        Player jt = new Player("JT Miller", "Vancouver Canucks",
                35, 38, 45);
        Player ss = new Player("Steven Stamkos", "Tampa Bay Lightning",
                40, 45, 30);
        Player bm = new Player("Brad Marchand", "Boston Bruins",
                45, 40, 80);

        FantasyTeam fantasyTeam1 = new FantasyTeam("Team 1");
        FantasyTeam fantasyTeam2 = new FantasyTeam("Team 2");
        FantasyTeam fantasyTeam3 = new FantasyTeam("Team 3");

        fantasyTeam1.addPlayer(ep);
        fantasyTeam1.addPlayer(bb);
        fantasyTeam2.addPlayer(jt);
        fantasyTeam2.addPlayer(ss);
        fantasyTeam3.addPlayer(bm);

        FantasyPool fantasyPool = new FantasyPool("Pool 1");

        fantasyPool.addFantasyTeam(fantasyTeam1);
        fantasyPool.addFantasyTeam(fantasyTeam2);
        fantasyPool.addFantasyTeam(fantasyTeam3);

        assertEquals("Pool 1 with teams: \n" +
                "\n" +
                "Team 1 with players:  Elias Pettersson (Vancouver Canucks), Brock Boeser (Vancouver Canucks),\n" +
                "Team 2 with players:  JT Miller (Vancouver Canucks), Steven Stamkos (Tampa Bay Lightning),\n" +
                "Team 3 with players:  Brad Marchand (Boston Bruins),\n", fantasyPool.producePool());
    }

    @Test
    public void testViewLeaderboard() {
        Player ep = new Player("Elias Pettersson", "Vancouver Canucks",
                40, 40, 10);
        Player bm = new Player("Brad Marchand", "Boston Bruins",
                30, 30, 10);
        Player ss = new Player("Steven Stamkos", "Tampa Bay Lightning",
                45, 45, 10);
        Player bb = new Player("Brock Boeser", "Vancouver Canucks",
                30, 20, 10);

        FantasyTeam t1 = new FantasyTeam("Team 1");
        FantasyTeam t2 = new FantasyTeam("Team 2");
        FantasyTeam t3 = new FantasyTeam("Team 3");

        t1.addPlayer(ep);
        t2.addPlayer(bm);
        t3.addPlayer(ss);
        t3.addPlayer(bb);

        FantasyPool fantasyPool = new FantasyPool("Pool 1");

        fantasyPool.addFantasyTeam(t1);
        fantasyPool.addFantasyTeam(t2);
        fantasyPool.addFantasyTeam(t3);

        assertEquals("The Pool 1 Leaderboard: \n" +
                "\n" +
                "1: Team 3 - 375 points\n" +
                "2: Team 1 - 210 points\n" +
                "3: Team 2 - 160 points\n", fantasyPool.produceLeaderboard());
    }

    @Test
    public void testRemoveFantasyTeamAndPlayer() {
        Player ep = new Player("Elias Pettersson", "Vancouver Canucks",
                40, 40, 10);
        Player bm = new Player("Brad Marchand", "Boston Bruins",
                30, 30, 10);
        Player ss = new Player("Steven Stamkos", "Tampa Bay Lightning",
                45, 45, 10);

        FantasyTeam t1 = new FantasyTeam("Team 1");
        FantasyTeam t2 = new FantasyTeam("Team 2");

        t1.addPlayer(ep);
        t1.addPlayer(bm);
        t2.addPlayer(ss);

        FantasyPool fantasyPool = new FantasyPool("Pool 1");

        fantasyPool.addFantasyTeam(t1);
        fantasyPool.addFantasyTeam(t2);

        assertEquals("Pool 1 with teams: \n" +
                "\n" +
                "Team 1 with players:  Elias Pettersson (Vancouver Canucks), Brad Marchand (Boston Bruins),\n" +
                "Team 2 with players:  Steven Stamkos (Tampa Bay Lightning),\n", fantasyPool.producePool());

        fantasyPool.getFantasyPool().get(0).removePlayer(ep);

        assertEquals("Pool 1 with teams: \n" +
                "\n" +
                "Team 1 with players:  Brad Marchand (Boston Bruins),\n" +
                "Team 2 with players:  Steven Stamkos (Tampa Bay Lightning),\n", fantasyPool.producePool());

        fantasyPool.removeFantasyTeam(t1);

        assertEquals("Pool 1 with teams: \n" +
                "\n" +
                "Team 2 with players:  Steven Stamkos (Tampa Bay Lightning),\n", fantasyPool.producePool());
    }

    @Test
    public void testForSetters() {
        Player js = new Player("John Smith", "Buffalo Sabres", 20, 25, 20);

        assertEquals(20, js.getGoals());
        assertEquals(25, js.getAssists());
        assertEquals(20, js.getPenaltyMinutes());

        js.setPenaltyMinutes(50);
        js.setAssists(100);
        js.setGoals(46);

        assertEquals(46, js.getGoals());
        assertEquals(100, js.getAssists());
        assertEquals(50, js.getPenaltyMinutes());
    }

    @Test
    public void testGetters() {
        Player ms = new Player("Mark Stone", "Vegas Golden Knights", 40, 30,
                30);

        assertEquals("Vegas Golden Knights", ms.getTeam());
        assertEquals("Mark Stone", ms.getPlayerName());
    }
}