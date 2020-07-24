package play.criticalcraft.bettermusic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import play.criticalcraft.bettermusic.Commands.CommanManager;
import play.criticalcraft.bettermusic.listener.JoinListener;
import play.criticalcraft.bettermusic.listener.MusicPlayer;
import play.criticalcraft.bettermusic.listener.PlayerBiomeTracker;
import play.criticalcraft.bettermusic.listener.RegionListener;
import play.criticalcraft.bettermusic.storage.TrackStorage;
import play.criticalcraft.bettermusic.tasks.TimeTask;

import java.io.File;

public final class BetterMusic extends JavaPlugin {

    public static BetterMusic i;
    public PlayerBiomeTracker playerBiomeTracker;



    private RegionListener regionListener;


    @Override
    public void onEnable() {
        // Plugin startup logic
        i = this;
        //getServer().getPluginManager().registerEvents(new MusicPlayer(p), this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();


        getCommand("bm").setExecutor(new CommanManager());


        if (!new File(this.getDataFolder() + "/Music.db").exists()) {
            TrackStorage.createDB();
            TrackStorage.createTable();
        }
        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);

        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            this.regionListener = new RegionListener();
        }
        this.playerBiomeTracker = new PlayerBiomeTracker();
        final BukkitTask timeTask = new TimeTask(this).runTaskTimer(this, 200L, 20L);
        for (final Player p : Bukkit.getOnlinePlayers()) {
            System.out.println(p.getDisplayName());
            new MusicPlayer(p);
        }
        this.getLogger().info(this.getName() + " has been loaded!");

    }

    public RegionListener getRegionListener() {
        return regionListener;
    }
}
