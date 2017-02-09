package net.apthos.guilds.player;

import net.apthos.guilds.Guilds;
import net.apthos.guilds.guild.Guild;
import net.apthos.guilds.player.tasks.Task;
import net.apthos.guilds.utils.PlayerUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Profile {

    protected List<Task> taskList = new ArrayList<>();

    private Player player = null;
    private Guild guild = null;

    public Profile(Player player) {
        this.player = player;
        loadFile();
    }

    public Profile(String UUID) {

    }

    public Player getPlayer() {
        return player;
    }

    public boolean hasPermission() {
        if (player.getName().equalsIgnoreCase("Apthos") ||
                player.getName().equalsIgnoreCase("The_Gurl")) {
            return true;
        }
        return false;
    }

    public Guild getGuild() {
        return this.guild;
    }

    public boolean hasGuild() {
        return ! (this.guild == null);
    }

    private boolean hasFile() {
        return PlayerUtils.getPlayerFile(this.player).exists();
    }

    private void createFile() {
        YamlConfiguration conf = new YamlConfiguration();
        conf.createSection("name"); conf.set("name", this.player.getName());
        conf.createSection("guild"); conf.set("guild", "$null$");
        conf.createSection("owned_land");
        conf.createSection("modified");
        conf.set("modified", LocalDateTime.now().toString());
        try {
            conf.save(PlayerUtils.getPlayerFile(player));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFile() {
        if (Guilds.MySQL) {
            // Database player creation
            return;
        }
        if (!hasFile()) {
            createFile();
            return;
        }
        YamlConfiguration yml =
                YamlConfiguration.loadConfiguration(PlayerUtils.getPlayerFile(player));
        if (!yml.getString("name").equalsIgnoreCase(player.getName())){
            yml.set("name", player.getName());
        }
        if (!yml.getString("guild").equalsIgnoreCase("$null$")){
            this.guild = Guilds.getInstance().getGuild(yml.getString("guild"));

        }

    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

}
