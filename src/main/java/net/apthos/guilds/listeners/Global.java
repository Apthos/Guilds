package net.apthos.guilds.listeners;

import net.apthos.guilds.Guilds;
import net.apthos.guilds.land.Chunk;
import net.apthos.guilds.player.Profile;
import net.apthos.guilds.rules.Rule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler
        ;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;


public class Global implements Listener {

//    @EventHandler
//    public void onBlockPlace(BlockPlaceEvent e){
//        Chunk chunk = new Chunk(e.getBlock().getChunk());
//        if (new Rule(chunk).rules){
//            e.setCancelled(true);
//        }
//    }
//
//    @EventHandler
//    public void onBlockBreak(BlockBreakEvent e){
//        Chunk chunk = new Chunk(e.getBlock().getChunk());
//        if (chunk.getRules().isAllowed(e.getPlayer()
//                ,Chunk.RulesManager.RuleTypes.BLOCK_PROTECTION)){
//            e.setCancelled(true);
//        }
//    }
//
//    @EventHandler
//    public void onBlockInteraction(PlayerInteractEvent e){
//        Chunk chunk = new Chunk(e.getClickedBlock().getChunk());
//        if (chunk.getRules().isAllowed(e.getPlayer()
//                ,Chunk.RulesManager.RuleTypes.BLOCK_INTERACTION)){
//            e.setCancelled(true);
//        }
//    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Guilds.getInstance().addProfile(new Profile(e.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Guilds.getInstance().removeProfile(Guilds.getInstance().getProfile(e.getPlayer()));
    }

}
