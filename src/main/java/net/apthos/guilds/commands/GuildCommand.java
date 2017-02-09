package net.apthos.guilds.commands;

import net.apthos.guilds.guild.Guild;
import net.apthos.guilds.player.Profile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommand implements CommandExecutor{


    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "ERROR: Player only Command!");
            return true;
        }

        Player player = (Player) sender;
        Profile profile = new Profile(player);

        if (!profile.hasPermission()){
            player.sendMessage(ChatColor.RED + "ERROR: YOU DON'T HAVE PERMISSION!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage( ChatColor.translateAlternateColorCodes('&', "" +
                    "&2<= &aGuilds Menu &2 ================>\n" +
                    " &e/guilds &6. . .\n" +
                    "     &6create &3[name]\n" +
                    "&2<= &aGuilds Menu &2 ================>\n"
            ));
            return true;
        }

        if (args[0].equalsIgnoreCase("create")){
            if (args.length == 2){
                new Guild(player, args[1]);
                player.sendMessage(ChatColor.GREEN + "You've created the " + ChatColor.AQUA
                        + args[1] + ChatColor.GREEN + " guild!" );
                return true;
            }
            player.sendMessage(ChatColor.GOLD + "USAGE: " + ChatColor.YELLOW +
                    "/G create [name]");
            return true;
        }

        return false;
    }
}
