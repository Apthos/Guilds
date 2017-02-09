package net.apthos.guilds;

import net.apthos.guilds.commands.GuildCommand;
import net.apthos.guilds.commands.PersonalCommand;
import net.apthos.guilds.database.GuildsDatabase;
import net.apthos.guilds.guild.Guild;
import net.apthos.guilds.guild.GuildUtils;
import net.apthos.guilds.land.Chunk;
import net.apthos.guilds.listeners.Global;
import net.apthos.guilds.player.Profile;
import net.apthos.guilds.player.tasks.ChunkGrid;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public final class Guilds extends JavaPlugin implements CommandExecutor {

    private static Guilds INSTANCE;
    private static GuildsDatabase DATABASE;

    public static boolean MySQL;

    private Set<Guild> guilds = new HashSet<>();
    private Set<Profile> profiles = new HashSet<>();
    private Set<Chunk> chunks = new HashSet<>();

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveInitResources();
        setupGlobalListeners();
        setupCommandExecutors();
        MySQL = YamlConfiguration.loadConfiguration(
                new File(this.getDataFolder() + "/sql.yml")).getBoolean("ENABLED");
        if (MySQL){
            setupDatabase();
        }
        profilePlayers();
    }

    private void profilePlayers(){
        for (Player player : Bukkit.getOnlinePlayers()){
            addProfile(new Profile(player));
        }
    }

    private void setupDatabase() {
        YamlConfiguration conf = YamlConfiguration.loadConfiguration
                (new File(INSTANCE.getDataFolder() + "/sql.yml"));
        DATABASE = new GuildsDatabase(conf.getString("HOST"), conf.getString("PORT"),
                conf.getString("USER"), conf.getString("PASS"));
    }

    public static Guilds getInstance(){
        return INSTANCE;
    }

    public static GuildsDatabase getGuildsDatabase(){
        return DATABASE;
    }

    public static void updateRegistry(Guild guild){
        YamlConfiguration yml = new YamlConfiguration().loadConfiguration( new File(
                getInstance().getDataFolder() + "/data.yml"));
        yml.createSection("registry." + guild.getName());
        yml.set("registry." + guild.getName(), guild.getGUID());
    }

    private void saveInitResources(){
        saveResource("claim.yml", false);
        saveResource("sql.yml", false);
        File GuildsFolder = new File(Guilds.getInstance().getDataFolder() + File.separator +
                "Guilds");
        if (!GuildsFolder.exists()){ GuildsFolder.mkdirs(); }
        File ChunksFolder = new File(Guilds.getInstance().getDataFolder() + File.separator +
                "Chunks");
        if (!ChunksFolder.exists()){ ChunksFolder.mkdirs(); }
        File ProfilesFolder = new File(Guilds.getInstance().getDataFolder() + File.separator
                + "Profiles");
        if (!ProfilesFolder.exists()){ ProfilesFolder.mkdirs(); }
    }

    private void setupGlobalListeners(){
        this.getServer().getPluginManager().registerEvents(new Global(), this);
    }

    private void setupCommandExecutors() {
        getCommand("p").setExecutor(new PersonalCommand());
        getCommand("personal").setExecutor(new PersonalCommand());
        getCommand("g").setExecutor(new GuildCommand());
        getCommand("guild").setExecutor(new GuildCommand());
    }

    public void addProfile(Profile profile){
        this.profiles.add(profile);
    }

    public void removeProfile(Profile profile){
        this.profiles.remove(profile);
    }

    public Profile getProfile(Player player){
        for (Profile profile : profiles){
            if (profile.getPlayer().getName().equalsIgnoreCase(player.getName()))
                return profile;
        }
        return null;
    }

    public Guild getGuild(String string){
        if (getLocalGuild(string) == null){
            if (string.contains("-")){
                if (GuildUtils.guildExists(string)){
                    Guild guild = new Guild(string);
                    return guild;
                }
            }
        }
        return getLocalGuild(string);
    }

    private Guild getLocalGuild(String string){
        for (Guild guild : this.guilds){
            if (string.contains("-"))
                if (guild.getGUID().equalsIgnoreCase(string))
                    return guild;
            else
                if (guild.getName().equalsIgnoreCase(string))
                    return guild;
        }
        return null;
    }

    @Override
    public void onDisable() {
        DATABASE.close();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {

        if (command.getName().equalsIgnoreCase("Map")){
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED +
                        "Error: Only players may use this command!");
                return true;
            }
            Profile profile = getProfile((Player) sender);
            profile.addTask(new ChunkGrid(profile.getPlayer(), 5));
            Bukkit.broadcastMessage(ChatColor.RED + "Task has been added!");
        }

        return false;
    }
}
