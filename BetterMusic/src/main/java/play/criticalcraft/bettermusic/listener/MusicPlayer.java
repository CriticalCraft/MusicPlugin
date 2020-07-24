package play.criticalcraft.bettermusic.listener;


import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import net.mcjukebox.plugin.bukkit.api.ResourceType;
import net.mcjukebox.plugin.bukkit.api.models.Media;
import net.mcjukebox.plugin.bukkit.events.ClientConnectEvent;
import net.mcjukebox.shared.api.Region;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import play.criticalcraft.bettermusic.BetterMusic;
import play.criticalcraft.bettermusic.events.TimeEvent;
import play.criticalcraft.bettermusic.storage.Track;
import play.criticalcraft.bettermusic.storage.TrackStorageManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.bukkit.Bukkit.getServer;


public class MusicPlayer implements Listener {

    private Player p;
    private Track musicPlaying;
    private long musicStarted;
    private Media media;
    private String highestRegion;


    public MusicPlayer(Player p) {
        this.p = p;

        this.musicPlaying = null;
        this.musicStarted = 0;
        if (getTrack() != null) {
            this.media = new Media(ResourceType.MUSIC, getTrack().getUrl());

            media.setLooping(false);
            media.setFadeDuration(1);
            media.setVolume(30);
        }
        getServer().getPluginManager().registerEvents(this, BetterMusic.i);


    }

    @EventHandler
    public void clientConnect(ClientConnectEvent e){
        setMusicPlaying(getTrack());
    }




    @EventHandler
    public void timer(TimeEvent e) {

        if (musicPlaying == null) {
            setMusicPlaying(getTrack());
            return;
        }


        long playing = System.currentTimeMillis() - musicStarted;
        if (TimeUnit.MILLISECONDS.toSeconds(playing) >= musicPlaying.getDuration()) {
            setMusicPlaying(getTrack());
        }
        Track next = getTrack();


        if (next == null || !musicPlaying.getRegion().equals(next.getRegion())) {
            if (getTracks().contains(musicPlaying.getName())) {

                return;
            }
            setMusicPlaying(next);
        }


    }


    private void setMusicPlaying(Track track) {

        musicPlaying = track;
        musicStarted = System.currentTimeMillis();

        if (musicPlaying != null) {
            System.out.println("Now playing: " + musicPlaying.getName() + " for player: " + this.p.getDisplayName());
            Media testMedia = new Media(ResourceType.MUSIC,musicPlaying.getUrl());
            testMedia.setFadeDuration(1);
            testMedia.setLooping(false);
            //media.setURL(musicPlaying.getUrl());
            //System.out.println(media.getFadeDuration());
            JukeboxAPI.play(p, testMedia);
        } else {
            JukeboxAPI.stopMusic(p,"default",2);
        }
    }


    private Track getTrack() {
        int highestPriority = -1;
        this.highestRegion = null;
        RegionListener regionListener = BetterMusic.i.getRegionListener();
        Iterator var4 = regionListener.getApplicableRegions(this.p.getLocation()).iterator();
        while (var4.hasNext()) {
            Region region = (Region) var4.next();
            if (region.getPriority() > highestPriority) {
                highestPriority = region.getPriority();
                this.highestRegion = region.getId();
            }


        }

        if (highestRegion == null) {
            highestRegion = BetterMusic.i.playerBiomeTracker.getBiome(this.p).toString();
        }

        return getRandomTrack(this.highestRegion);
    }


    private Track getRandomTrack(String region) {

        ArrayList<Track> tracks = TrackStorageManager.getInstance().getTracks(highestRegion, getWorldTime(p));


        if (tracks.size() == 0) {
            return null;
        }

        if (tracks.size() == 1) {
            return tracks.get(0);
        }
        int random = new Random().nextInt(tracks.size());
        return tracks.get(random);

    }

    private ArrayList<String> getTracks() {
        ArrayList<String> trackNames = new ArrayList<>();
        for (Track track : TrackStorageManager.getInstance().getTracks(highestRegion, getWorldTime(p))) {
            trackNames.add(track.getName());
        }
        return trackNames;
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

    public Track getMusicPlaying() {
        return musicPlaying;
    }
}
