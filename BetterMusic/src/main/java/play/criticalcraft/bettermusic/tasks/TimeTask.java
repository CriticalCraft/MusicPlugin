package play.criticalcraft.bettermusic.tasks;



import play.criticalcraft.bettermusic.events.TimeEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import play.criticalcraft.bettermusic.BetterMusic;


import java.util.Date;


public final class TimeTask extends BukkitRunnable {

    BetterMusic plugin;




    public TimeTask(BetterMusic plugin) {
        this.plugin = plugin;
    }


    @Override
    public void run() {

        Bukkit.getServer().getPluginManager().callEvent(new TimeEvent(getServerTime()));


    }

    public Date getServerTime() {


        return new Date(System.currentTimeMillis());
    }


}