package net.apthos.guilds.land;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Chunk {

    private org.bukkit.Chunk sourceChunk;
    //private RulesManager rules;
    private Type type;

    public Chunk(org.bukkit.Chunk chunk) {
        sourceChunk = chunk;
        //this.rules = new RulesManager(this);
    }

    public void claim(Player player){
        //Rules rules;
        //Type type =
    }

    /*
     * _____________________________
     *   N-W   |  North  |  N-E    |
     * z-1 x-1 | z-1 x+0 | z-1 x+1 |
     * --------|---------|-------- |
     *   West  | Center  |  East   | Chunk Map /
     * z+0 x-1 | z+0 x+0 | z+0 x+1 |      Neighbor Diagram
     * --------|---------|-------- |
     *   S-W   |  South  |  S-E    |
     * z+1 x-1 | z+1 x+0 | z+1 x+1 |
     * _____________________________
     */

    public Chunk getNeighbor(Neighbor neighbor) {
        switch (neighbor) {
            case North_West: {

            }
            case North: {

            }
            case North_East: {

            }
            case West: {

            }
            case East: {

            }
            case South_West: {

            }
            case South: {

            }
            case South_East: {
            }
        }
        return null;
    }

//    public HashMap<Neighbor, Chunk> getNeighbors(Neighbor neighbor) {
//        HashMap<Neighbor, Chunk> neighbors = new HashMap<>();
//        for (int z = - 1; z <= 1; z++) {
//            for (int x = - 1; x <= 1; x++) {
//                if (x == 0 && z == 0) continue;
//                neighbors.put(Neighbor.getSide(x, z), new Chunk());
//            }
//        }
//        return neighbors;
//    }

    public Corners getCorners(double y){
        return (new Corners(sourceChunk).setY(y));
    }

    public class Corners {
        public Location SW, NW, NE, SE;
        public Corners(org.bukkit.Chunk chunk){
            NW = new Location(chunk.getWorld(), chunk.getX()*16, 0, chunk.getZ()*16);
            SW = NW.clone().add(0, 0, 16); NE = NW.clone().add(16, 0, 0);
            SE = NW.clone().add(16, 0, 16);
        }
        public Corners setY(double Y){
            NW.setY(Y); SW.setY(Y); NE.setY(Y); SE.setY(Y);
            return this;
        }
    }

    public org.bukkit.Chunk getSourceChunk(){
        return this.sourceChunk;
    }

//    public RulesManager getRules(){
//        return rules;
//    }

    public Type getClaimType(){
        return type;
    }

    public enum Neighbor {
        North(0, - 1), North_West(- 1, - 1), North_East(1, - 1), West(- 1, 0), East(1, 0),
        South(0, 1), South_West(- 1, 1), South_East(1, 1);

        int x, y;

        Neighbor(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static Neighbor getSide(int x, int z) {
            if (z == - 1) {
                if (x == - 1) return Neighbor.North_West;
                if (x == 0) return Neighbor.North;
                if (x == 1) return Neighbor.North_East;
            } else if (z == 0) {
                if (x == - 1) return Neighbor.West;
                if (x == 1) return Neighbor.East;
            } else if (z == 1) {
                if (x == - 1) return Neighbor.South_West;
                if (x == 0) return Neighbor.South;
                if (x == 1) return Neighbor.South_East;
            }
            return null;
        }
    }

    public enum Type {
        WILD, GUILD, PERSONAL
    }

}

