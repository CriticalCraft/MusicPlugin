package me.woefie.apiplugin;


import me.woefie.apiplugin.Listeners.JoinListener;
import me.woefie.apiplugin.Listeners.MusicPlayer;
import me.woefie.apiplugin.Listeners.PlayerBiomeTracker;
import me.woefie.apiplugin.storage.Playlist;
import me.woefie.apiplugin.storage.TrackStorage;
import me.woefie.apiplugin.tasks.TimeTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;


public final class APIPlugin extends JavaPlugin {

    public static APIPlugin i;
    public PlayerBiomeTracker playerBiomeTracker;

    private File file = new File(this.getDataFolder() + "/Music.json");
    private File testFile = new File(this.getDataFolder() + "/test.json");

    @Override
    public void onEnable() {
        // Plugin startup logic
        i = this;
        //getServer().getPluginManager().registerEvents(new MusicPlayer(p), this);


        TrackStorage music = new TrackStorage(file);

        TrackStorage test = new TrackStorage(testFile);
        test.save();

        Playlist playlist = new Playlist(music);



        getServer().getPluginManager().registerEvents(new JoinListener(), this);


        playerBiomeTracker = new PlayerBiomeTracker();

        BukkitTask timeTask = new TimeTask(this).runTaskTimer(this, 5, 20L);

        for (Player p : Bukkit.getOnlinePlayers()) {

            new MusicPlayer(p);

        }


    }


}
