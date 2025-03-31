package eu.jxstcolin.main;

import eu.jxstcolin.database.MongoDB;
import eu.jxstcolin.managers.ConfigManager;
import eu.jxstcolin.user.User;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Rolepaper extends JavaPlugin {

    @Getter private static Rolepaper instance;

    @Getter private ConfigManager configManager;
    @Getter private String prefix;

    @Getter private HashMap<String, User> onlineUsers;

    @Override
    public void onEnable() {
        instance = this;
        onlineUsers = new HashMap<>();

        MongoDB mongoDB = new MongoDB();

        configManager = new ConfigManager();
        prefix = configManager.getConfig().getString("prefix");
        register();
    }

    public void register(){

    }

    @Override
    public void onDisable() {

    }
}
