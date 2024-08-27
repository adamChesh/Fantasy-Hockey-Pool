package persistence;

import model.FantasyPool;
import model.FantasyTeam;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            FantasyPool fantasyPool = new FantasyPool("2021 Fantasy Pool");
            JsonWriter writer = new JsonWriter("./data/\nillegal:fileName.json");
            writer.open();
            fail("IOException should have been thrown!");
        } catch (IOException i) {
            // EXPECTED
        }
    }

    @Test
    public void testWriterEmptyFantasyPool() {
        try {
            FantasyPool fantasyPool = new FantasyPool("2020 Hockey Fantasy Pool");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPool.json");
            writer.open();
            writer.write(fantasyPool);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPool.json");
            fantasyPool = reader.read();
            assertEquals("2020 Hockey Fantasy Pool", fantasyPool.getNameOfPool());
            assertEquals("2020 Hockey Fantasy Pool with teams: \n\n", fantasyPool.producePool());
        } catch (IOException i) {
            fail("threw the IOException when it shouldn't have");
        }
    }

    @Test
    public void testWriterFantasyPoolWithTeams() {
        try {
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

            JsonWriter writer = new JsonWriter("./data/testWriterPoolWithTeams.json");
            writer.open();
            writer.write(fantasyPool);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterPoolWithTeams.json");
            fantasyPool = reader.read();
            assertEquals("Pool 1", fantasyPool.getNameOfPool());
            List<FantasyTeam> teams = fantasyPool.getFantasyPool();
            checkFantasyTeam("Team 1", teams.get(0));
        } catch (IOException i) {
            fail("IOException was thrown when it shouldn't have");
        }
    }
}
