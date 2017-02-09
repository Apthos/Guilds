package net.apthos.guilds.guild;

import net.apthos.guilds.Guilds;
import net.apthos.guilds.land.Chunk;
import net.apthos.guilds.player.Profile;
import net.apthos.guilds.utils.PlayerUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.apthos.guilds.guild.GuildUtils.generateUUID;

public class Guild {

    private String UUID = "$NULL$";
    private String name = "$NULL$";
    private Set<Profile> members = new HashSet<>();
    private Set<Chunk> chunks = new HashSet<>();

    public Guild(Player player, String name) {
        this.UUID = generateUUID();
        createSettings(true, name, UUID);
    }

    public Guild(String UUID){
        File file = new File
                (Guilds.getInstance().getDataFolder() + "/Guilds/" + UUID + ".yml");
        if (file.exists()) {
            loadSettings(file);
        }
    }

    public void saveSettings(){

    }

    private void loadSettings(File file){
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
        if (!this.UUID.equalsIgnoreCase(conf.getString("uuid"))) return;
        this.name = conf.getString("name");
        //this.members = processOfflineProfiles()
    }

    private void createSettings(boolean fromDefault, String name, String UUID){
        Guilds.updateRegistry(this);
        YamlConfiguration YAML = new YamlConfiguration();
        YAML.createSection("uuid"); YAML.set("uuid", UUID);
        YAML.createSection("name"); YAML.set("name", name);
        YAML.createSection("owned_land");
        YAML.createSection("members");
        try {
           YAML.save(new File
                   (Guilds.getInstance().getDataFolder() + "/Guilds/" + UUID + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveSettings();
    }

    public String getGUID(){
        return UUID;
    }

    public String getName(){
        return name;
    }

}
