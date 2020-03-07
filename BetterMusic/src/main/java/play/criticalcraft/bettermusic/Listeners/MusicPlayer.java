package play.criticalcraft.bettermusic.Listeners;



import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import net.mcjukebox.plugin.bukkit.api.ResourceType;
import net.mcjukebox.plugin.bukkit.api.models.Media;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import play.criticalcraft.bettermusic.BetterMusic;
import play.criticalcraft.bettermusic.events.TimeEvent;
import play.criticalcraft.bettermusic.storage.Track;
import play.criticalcraft.bettermusic.storage.TrackStorage;
import play.criticalcraft.bettermusic.storage.TrackStorageManager;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.bukkit.Bukkit.getServer;


public class MusicPlayer implements Listener {

    private Player p;
    private Track musicPlaying;
    private long musicStarted;
    private Media media;
    private Biome biome;


    public MusicPlayer(Player p) {
        this.p = p;

        this.musicPlaying = null;
        this.musicStarted = 0;
        if (getTrack()!= null) {
            this.media = new Media(ResourceType.MUSIC, getTrack().getUrl());

        media.setLooping(false);
        media.setFadeDuration(2);
        media.setVolume(30);
        }
        getServer().getPluginManager().registerEvents(this, BetterMusic.i);


    }



    @EventHandler
    public void check(TimeEvent e) {

        if(musicPlaying == null){
            setMusicPlaying(getTrack());
            return;
        }

        long playing = System.currentTimeMillis() - musicStarted;
        if(TimeUnit.MILLISECONDS.toSeconds(playing) >= musicPlaying.getDuration()){
            setMusicPlaying(getTrack());
        }
        Track next = getTrack();

        if(next == null || !musicPlaying.getBiome().equals(next.getBiome())){

            setMusicPlaying(getTrack());
        }




    }


    private void setMusicPlaying(Track track) {

        if(track != null){
            System.out.println("Now playing: " + track.getName()+" for player: "+ p.getDisplayName());
        }else{

            System.out.println("Now playing: Nothing");

        }

        musicPlaying = track;
        musicStarted = System.currentTimeMillis();

        if (musicPlaying != null) {
            media.setURL(musicPlaying.getUrl());
            System.out.println(media.getFadeDuration());
            JukeboxAPI.play(p, media);
        } else {
            JukeboxAPI.stopMusic(p);
        }
    }



    private Track getTrack() {


        biome = BetterMusic.i.playerBiomeTracker.getBiome(p);

        return getRandomTrack(biome);
    }

    private Track getRandomTrack(Biome biome) {

        ArrayList<Track> tracks = TrackStorageManager.getInstance().getTracks(biome, getWorldTime(p));




        if (tracks.size() == 0) {
            return null;
        }

        if (tracks.size() == 1) {
            return tracks.get(0);
        }
        int random = new Random().nextInt(tracks.size() );
        return tracks.get(random);

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        if (!e.getPlayer().equals(p)) {
            return;
        }
        JukeboxAPI.stopMusic(p);

        HandlerList.unregisterAll(this);

    }


    public String getWorldTime(Player player) {
        long time = player.getWorld().getTime();


        if (time > 0 && time < 12300) {
            return "day";
        } else {
            return "night";
        }
    }
}
