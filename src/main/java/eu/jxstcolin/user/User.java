package eu.jxstcolin.user;

import eu.jxstcolin.database.MongoDB;
import eu.jxstcolin.main.Rolepaper;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class User {

    @Getter private Player player;
    @Getter private String username, uniqueId;

    @Getter @Setter private double stamina;

    @Getter private ScheduledExecutorService executorService;

    public User(Player player) {
        this.player = player;
        this.username = player.getName();
        this.uniqueId = player.getUniqueId().toString();

        if (MongoDB.getCollection("players").find(new Document("uuid", uniqueId)).first() == null) {
            MongoDB.getCollection("players").insertOne(new Document("uuid", uniqueId)
                    .append("username", username)
                    .append("bankAmount", 0.0)
                    .append("stamina", 100.0) // stamina wird gespeichert sobald der spieler sich ausloggt, und wird gesetzt wenn der spieler sich einloggt (um bugusing zu vermeiden)
            );
        }

        MongoDB.getCollection("players").updateOne(new Document("uuid", uniqueId), new Document("$set", new Document("username", username)));
        stamina = getPlayerData().getDouble("stamina");

        executorService = Executors.newSingleThreadScheduledExecutor();

        initializeLocalTimers();
    }

    private void initializeLocalTimers() { // stamina timer um jede paar millisekunden der der spieler sprintet stamina zu revoken???

    }

    public void sendMessage(String message, boolean prefix, Object... format) {
        player.sendMessage(MiniMessage.miniMessage().deserialize(prefix ? Rolepaper.getInstance().getPrefix() + String.format(message, format) : String.format(message, format)));
    }

    public static User getUser(Player player) {
        return Rolepaper.getInstance().getOnlineUsers().get(player.getUniqueId().toString());
    }

    public Document getPlayerData() {
        return MongoDB.getCollection("players").find(new Document("uuid", uniqueId)).first();
    }
}
