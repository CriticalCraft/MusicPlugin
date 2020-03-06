package play.criticalcraft.bettermusic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import play.criticalcraft.bettermusic.Listeners.JoinListener;
import play.criticalcraft.bettermusic.Listeners.MusicPlayer;
import play.criticalcraft.bettermusic.Listeners.PlayerBiomeTracker;
import play.criticalcraft.bettermusic.storage.TrackStorage;
import play.criticalcraft.bettermusic.tasks.TimeTask;

import java.io.File;

public final class BetterMusic extends JavaPlugin {

    public static BetterMusic i;
    public PlayerBiomeTracker playerBiomeTracker;

    private File file = new File(this.getDataFolder() + "/Music.json");
    private File testFile = new File(this.getDataFolder() + "/test.json");

    @Override
    public void onEnable() {
        // Plugin startup logic
        i = this;
        //getServer().getPluginManager().registerEvents(new MusicPlayer(p), this);
        BetterMusic.i = this;
        final TrackStorage t = new TrackStorage();
        if (!new File(this.getDataFolder() + "/test.db").exists()) {
            TrackStorage.createDB();
            TrackStorage.createTable();
            t.insertTrack("Sun", "https://audio.jukehost.co.uk/544503aea332f1ec97b6cdaaba7358f2a78d9209/a5a743cb789", 180, "plains", "day");
            t.insertTrack("Circus", "https://audio.jukehost.co.uk/544503aea332f1ec97b6cdaaba7358f2a78d9209/b140f73fbf4", 154, "plains");
            t.insertTrack("assasin", "https://audio.jukehost.co.uk/544503aea332f1ec97b6cdaaba7358f2a78d9209/365b7367921", 486, "plains");
            t.insertTrack("epic", "https://audio.jukehost.co.uk/544503aea332f1ec97b6cdaaba7358f2a78d9209/e141ad3c516", 186, "plains", "night");
            t.insertTrack("epic", "https://audio.jukehost.co.uk/544503aea332f1ec97b6cdaaba7358f2a78d9209/e141ad3c516", 186, "desert", "day");
            t.insertTrack("Sun", "https://audio.jukehost.co.uk/544503aea332f1ec97b6cdaaba7358f2a78d9209/a5a743cb789", 180, "desert", "night");
        }
        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);


        this.playerBiomeTracker = new PlayerBiomeTracker();
        final BukkitTask timeTask = new TimeTask(this).runTaskTimer(this, 5L, 20L);
        for (final Player p : Bukkit.getOnlinePlayers()) {
            new MusicPlayer(p);
        }




    }
}
