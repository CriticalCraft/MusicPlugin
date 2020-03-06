package me.woefie.apiplugin.tasks;


import me.woefie.apiplugin.APIPlugin;
import me.woefie.apiplugin.events.TimeEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;


public final class TimeTask extends BukkitRunnable {

    APIPlugin plugin;


    private boolean Day;


    public TimeTask(APIPlugin plugin) {
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