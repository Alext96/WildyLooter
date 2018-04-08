import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import static org.osbot.rs07.script.MethodProvider.random;
import static org.osbot.rs07.script.MethodProvider.sleep;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;


@ScriptManifest(author = "Alex", info = "loots wildy", name = "WildyLooterV3", version = 0, logo = "")
public class WildyLooterV3 extends Script {


    @Override
    public void onStart() {
        log("välkommen till wilderness lootern");
        log("startar...");
    }

    private enum State {
        LOOT, BANK, EAT
    };

    private enum Bank {
        DRAYNOR(Banks.DRAYNOR),
        AL_KHARID(Banks.AL_KHARID),
        LUMBRIDGE(Banks.LUMBRIDGE_UPPER),
        FALADOR_EAST(Banks.FALADOR_EAST),
        FALADOR_WEST(Banks.FALADOR_WEST),
        VARROCK_EAST(Banks.FALADOR_EAST),
        VARROCK_WEST(Banks.VARROCK_WEST),
        SEERS(Banks.CAMELOT),
        CATHERBY(Banks.CATHERBY),
        EDGEVILLE(Banks.EDGEVILLE),
        YANILLE(Banks.YANILLE),
        GNOME_STRONGHOLD(Banks.GNOME_STRONGHOLD),
        ARDOUNGE_NORTH(Banks.ARDOUGNE_NORTH),
        ARDOUNE_SOUTH(Banks.ARDOUGNE_SOUTH),
        CASTLE_WARS(Banks.CASTLE_WARS),
        DUEL_ARENA(Banks.DUEL_ARENA),
        PEST_CONTROL(Banks.PEST_CONTROL),
        CANIFIS(Banks.CANIFIS),
        BLAST_FURNACE(new Area(1949, 4956, 1947, 4958)),
        TZHAAR(Banks.TZHAAR);

        private final Area area;

        Bank(Area area) {
            this.area = area;
        }
    }

    public static Area closestTo(Entity e) {
        HashMap<Bank, Integer> distMap = new HashMap<Bank, Integer>();
        for (Bank b : Bank.values()) {
            distMap.put(b, e.getPosition().distance(b.area.getRandomPosition()));
        }
        HashMap<Integer, Bank> distMapSorted = sortByDistance(distMap);
        Area cBank = distMapSorted.values().toArray(new Bank[Bank.values().length])[0].area;
        return cBank;
    }

    private static <K, V extends Comparable<? super V>> HashMap<V, K> sortByDistance(Map<K, V> map) {
        HashMap<V, K> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();
        st.sorted(Map.Entry.comparingByValue()).forEachOrdered(e -> result.put(e.getValue(), e.getKey()));
        return result;
    }

    private State getState()
    {

        if(!inventory.isFull())
        {
            return State.LOOT;
        }
        return State.BANK;
    }

    @Override
    public int onLoop() throws InterruptedException {
        switch (getState()) {
            case LOOT:
                GroundItem AdamantArrows = groundItems.closest("Adamant arrow");
                GroundItem Rune = groundItems.closest("Gilded full helm", "Gilded chainbody", "Gilded platebody", "Gilded platelegs", "Gilded plateskirt", "Gilded kiteshield", "Gilded scimitar", "Zamorak full helm", "Zamorak platebody", "Zamorak platebody", "Zamorak platelegs", "Zamorak platelegs", "Zamorak plateskirt", "Zamorak plateskirt", "Zamorak kiteshield", "Zamorak kiteshield", "Saradomin full helm", "Saradomin platebody", "Saradomin platebody", "Saradomin platelegs", "Saradomin platelegs", "Saradomin plateskirt", "Saradomin plateskirt", "Saradomin kiteshield", "Saradomin kiteshield", "Rune full helm (g)", "Rune platebody (g)", "Rune platebody (g)", "Rune platelegs (g)", "Rune platelegs (g)", "Rune plateskirt (g)", "Rune plateskirt (g)", "Rune kiteshield (g)", "Rune kiteshield (g)", "Rune full helm (t)", "Rune platebody (t)", "Rune platebody (t)", "Rune platelegs (t)", "Rune platelegs (t)", "Rune plateskirt (t)", "Rune plateskirt (t)", "Rune kiteshield (t)", "Rune kiteshield (t)", "Rune full helm", "Rune platebody", "Rune platelegs", "Rune Kiteshield", "Rune plateskirt", "Rune 2h sword", "Rune scimitar", "Rune battleaxe", "Swordfish", "Green d'hide body", "Green d'hide chaps", "Green d'hide vamb", "Lobster");
                if(AdamantArrows != null || Rune != null)
                {
                    Rune.interact("Take");
                    AdamantArrows.interact("Take");

                }
                break;
            case BANK:
                while(inventory.isFull()){
                    Area currentBank = closestTo(myPlayer());
                    break;
                }
            case EAT:
        }
        return random(700, 1000);
    }

    @Override
    public void onExit() {
        //here i can log how many goblins i have killed, how long i ran script for, logging purposes
        log("Tack för att du testade goblin dödaren!");
    }

    @Override
    public void onPaint(Graphics2D g) {
        //for displaying GFX/PAINT DISPLAY INFO ON SCREEN
    }

}