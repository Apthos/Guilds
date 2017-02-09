package net.apthos.guilds.guild;

import net.apthos.guilds.Guilds;

import java.io.File;

public class GuildUtils {

    public static String generateUUID(){
        String gen = "";
        char chars[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o','p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '1', '2', '3', '4', '5', '6','7', '8', '9', '1', '2', '3', '4', '5',
                '6', '7', '8', '9'};

        for (int x = 0; x < (chars.length / 2) - 2; x++) {
            if (x != 0 & x % 4 == 0) {
                gen = gen + "-";
            } else {
                gen = gen + chars[(int) (Math.random() * chars.length)];
            }
        }
        return gen;
    }

    public static File getGuildFile(String guid){
        return (new File
                (Guilds.getInstance().getDataFolder() + "/Guilds/" + guid + ".yml"));
    }

    public static boolean guildExists(String guid){
        return getGuildFile(guid).exists();
    }

}
