package net.apthos.guilds.commands;

import net.apthos.guilds.land.Chunk;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PersonalCommand implements CommandExecutor {
    @Override
    public boolean onCommand
            (CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage( ChatColor.translateAlternateColorCodes('&',
                    "" +
                            "&1<= &9Personal Menu &1================>\n" +
                            " &e/p &6. . .\n" +
                            "    &6claim \n" +
                            "&1<= &9Personal Menu &1================>\n"
            ));
            return true;
        }

        if (args[0].equalsIgnoreCase("claim")){
            Chunk chunk = new Chunk(player.getLocation().getChunk());
            if (chunk.getClaimType().equals(Chunk.Type.WILD)){
                chunk.claim(player);
            } else {

            }
        }

        return false;
    }
}
