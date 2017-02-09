package net.apthos.guilds.utils;

import net.apthos.guilds.Guilds;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerUtils {

    public static File getPlayerFile(Player player){
        return ( new File(Guilds.getInstance().getDataFolder() + "/Profiles/" +
        player.getUniqueId().toString() + ".yml") );
    }

}
