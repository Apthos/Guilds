package net.apthos.guilds.player.tasks;

import net.apthos.guilds.Guilds;
import net.apthos.guilds.Math.DimensionalVector;
import net.apthos.guilds.land.Chunk;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_11_R1.SystemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ChunkGrid extends Task {

    public ChunkGrid(Player player, int interval) {
        super(player, interval);
        Bukkit.broadcastMessage(ChatColor.YELLOW + "CONSTRUCTOR STARTED!");
        if (!hasPlayer()) {
            Bukkit.broadcastMessage(ChatColor.RED + "PLAYER IS NULL!");
            return;
        }
        work();
        Bukkit.broadcastMessage(ChatColor.YELLOW + "CONSTRUCTOR ENDED!");
    }

    @Override
    public void work() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Chunk.Corners corners = new Chunk(player.getLocation().getChunk())
                        .getCorners(player.getEyeLocation().getY()-.65);
                if (player.isSneaking()) {
                    this.cancel();
                }
                drawLine(corners.SW, corners.NW, .25, EnumParticle.CLOUD);
                drawLine(corners.NW, corners.NE, .25, EnumParticle.CLOUD);
                drawLine(corners.NE, corners.SE, .25, EnumParticle.CLOUD);
                drawLine(corners.SE, corners.SW, .25, EnumParticle.CLOUD);
            }
        }.runTaskTimerAsynchronously(Guilds.getInstance(), 0, interval);
    }

    private void drawLine(Location start, Location end, double interval, EnumParticle eP) {
        if (interval <= 0) return;
        if (start == null || end == null){
            Bukkit.broadcastMessage(ChatColor.GOLD + "a location is null!");
            return;
        }
        DimensionalVector DV = new DimensionalVector(start, end);
        for (double x = 0; x <= DV.distance(start, end); x += interval) {
            player.getLocation().getWorld().playEffect(player.getLocation(),
                    Effect.HEART, null);
            Location point = DV.follow(start, x);
            PacketPlayOutWorldParticles packet =
                    new PacketPlayOutWorldParticles(eP, true,
                            (float) point.getX(), (float) point.getY(), (float) point.getZ()
                            , 0, 0, 0, 0, 1);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }


}
