package persistence;

import model.FantasyPool;
import model.FantasyTeam;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderFileDoesNotExist() {
        JsonReader reader = new JsonReader("./data/fileDoesNotExist.json");
        try {
            FantasyPool fantasyPool = reader.read();
            fail("IOException should have been thrown!");
        } catch (IOException i) {
            // EXPECTED
        }
    }

    @Test
    public void testReaderEmptyFantasyPool() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPool.json");
        try {
            FantasyPool fantasyPool = reader.read();
            assertEquals("2020 Hockey Fantasy Pool", fantasyPool.getNameOfPool());
            assertEquals("2020 Hockey Fantasy Pool with teams: \n" +
                    "\n", fantasyPool.producePool());
        } catch (IOException i) {
            fail("IOException was thrown when it shouldn't have!");
        }
    }

    @Test
    public void testReaderFantasyPoolWithTeams() {
        JsonReader reader = new JsonReader("./data/testReaderPoolWithTeams.json");
        try {
            FantasyPool fantasyPool = reader.read();
            assertEquals("Pool 1", fantasyPool.getNameOfPool());
            List<FantasyTeam> teams = fantasyPool.getFantasyPool();
            assertEquals(2, teams.size());
            checkFantasyTeam("Team 2", teams.get(1));
            checkPlayer("Elias Pettersson",
                    "Vancouver Canucks", 40, 40, 10, teams.get(0).getRoster().get(0));
        } catch (IOException i) {
            fail("IOException was thrown when it shouldn't have!");
        }
    }

}
