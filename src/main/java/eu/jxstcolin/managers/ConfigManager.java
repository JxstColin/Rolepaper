package eu.jxstcolin.managers;

import eu.jxstcolin.main.NarcotixCraft;
import eu.jxstcolin.main.Rolepaper;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigManager {

    @Getter private File file;
    @Getter private YamlConfiguration config;

    public ConfigManager() {
        file = new File(Rolepaper.getInstance().getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(file);

        HashMap<String, Object> defaults = new HashMap<>();
        defaults.put("prefix", "<red>Rolepaper</red> <dark_gray>–</dark_gray> <gray>");

        defaults.forEach((key, value) -> {
            if (!config.contains(key)) {
                config.set(key, value);
            }
        });
        save();
        load();
    }

    public void load() {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
