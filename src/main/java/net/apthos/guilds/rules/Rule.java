package net.apthos.guilds.rules;

import net.apthos.guilds.Guilds;
import net.apthos.guilds.guild.Guild;
import net.apthos.guilds.land.Chunk;
import net.apthos.guilds.player.Profile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class Rule {

    public enum RuleTypes {
        BLOCK_PROTECTION(), MOB_PROTECTION(), BLOCK_INTERACTION(), MOB_INTERACTION(),
        PVP(), MOB_SPAWNING(), HOSTILE_MOB_SPAWNING();

        public List<RuleTypes> getRuleList(){
            List<RuleTypes> rules = new ArrayList<>();
            rules.add(RuleTypes.BLOCK_PROTECTION);
            rules.add(RuleTypes.MOB_PROTECTION);
            rules.add(RuleTypes.BLOCK_INTERACTION);
            rules.add(RuleTypes.MOB_INTERACTION);
            rules.add(RuleTypes.PVP);
            rules.add(RuleTypes.MOB_SPAWNING);
            rules.add(RuleTypes.HOSTILE_MOB_SPAWNING);
            return rules;
        }
    }

    private HashMap<RuleTypes, Boolean> rules = new HashMap<>();
    private Chunk.Type claimType;
    private Guild guild;
    private Profile playerProfile;

    public Rule(Chunk chunk){
        String dataFile = chunk.getSourceChunk().getWorld().getName() + "{" +
                toString().valueOf(chunk.getSourceChunk().getX()) +
                toString().valueOf(chunk.getSourceChunk().getZ()) + "}";

        File file =
                new File(Guilds.getInstance().getDataFolder() + "/" + dataFile + ".yml");

        if(file.exists()){
            loadSettings(YamlConfiguration.loadConfiguration(file), "");
            return;
        }
        loadSettings(YamlConfiguration.loadConfiguration(file), "default.wild.");
    }

    public void loadSettings(YamlConfiguration YAML, String root) {
        if (root.equalsIgnoreCase("default.wild.")){
            YAML = YamlConfiguration.loadConfiguration(new File
                    (Guilds.getInstance().getDataFolder() + "/claims.yml"));
        }

        for (RuleTypes rule : RuleTypes.values()) {
            Bukkit.broadcastMessage(rule.name() + " - " + rule.toString());
            this.rules.put(rule, YAML.getBoolean(root + rule.name().toUpperCase()));
        }

        if (root.equalsIgnoreCase("")){
            if (YAML.getString("Type").equalsIgnoreCase("PERSONAL")) {
                this.claimType = Chunk.Type.PERSONAL;
                //Profile profile = new Profile();
            }
            if (YAML.getString("Type").equalsIgnoreCase("GUILD")) {
                this.claimType = Chunk.Type.GUILD;
                Guild guild = new Guild(YAML.getString("UUID"));
                this.guild = guild;
            }
            return;
        }
        this.claimType = Chunk.Type.WILD;
    }

}
