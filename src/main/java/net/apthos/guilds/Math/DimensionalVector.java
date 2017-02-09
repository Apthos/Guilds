package net.apthos.guilds.Math;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class DimensionalVector {

    private double x, y, z, length;

    public DimensionalVector(Location P1, Location P2){
        length = distance(P1, P2);
        if (P1.getX() == P2.getX() && P1.getY() == P2.getY() && P1.getZ() == P2.getZ()){
            Bukkit.broadcastMessage(ChatColor.RED +
                    "Dimensional Vector Locations are the same!");
            return;
        }
        x = P2.getX() - P1.getX();
        y = P2.getY() - P1.getY();
        z = P2.getZ() - P1.getZ();
        setLength(1);
    }

    public void setLength(double length){
        if (x != 0.0) {
            x = (x/this.length)*length;
        }
        if (y != 0.0) {
            y = (y/this.length)*length;
        }
        if (z != 0.0) {
            z = (z/this.length)*length;
        }
        this.length = length;
    }

    public Location follow(Location location, double distance){
        if (distance == 0){
            return location;
        }
        setLength(distance);
        Location loc = new Location(location.getWorld(), location.getX() + x,
                location.getY() + y, location.getZ() + z);
        setLength(1);
        return loc;
    }

    public double distance(Location P1, Location P2){
        return (Math.sqrt(Math.pow(P2.getX() - P1.getX(),2)
                        + Math.pow(P2.getY() - P1.getY(),2)
                        + Math.pow(P2.getZ() - P1.getZ(),2)
                ));
    }

}
