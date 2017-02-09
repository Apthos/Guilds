package net.apthos.guilds.player.tasks;

import org.bukkit.entity.Player;

public abstract class Task {

    Player player = null;
    int interval = -1, current;

    public Task(Player player, int interval){
        this.player = player;
        this.interval = interval;
        this.current = interval;
    }

    protected boolean hasPlayer(){
        return !(this.player == null);
    }

    public boolean tick(){
        if (interval == 0){
            tick();
            return true;
        }
        interval--;
        return false;
    }

    public abstract void work();

}
