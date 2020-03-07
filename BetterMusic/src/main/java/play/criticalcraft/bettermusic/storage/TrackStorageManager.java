package play.criticalcraft.bettermusic.storage;

import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TrackStorageManager {

    private static TrackStorageManager instance;


    private TrackStorage trackStorage;

    public TrackStorageManager() {
        trackStorage = new TrackStorage();
    }

    public static TrackStorageManager getInstance() {

        if (instance == null) {
            instance = new TrackStorageManager();
        }

        return instance;
    }

    //insertTrack (String name,  String url,  int duration,  String playlist,  String day)
    public void insertTrack(Player player, String[] args) {
        int min, sec;
        try {
            min = Integer.parseInt(args[3]);
            sec = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            player.sendMessage("Minutes and seconds need to be a number!");
            return;
        }

        if (args.length == 6) {

            System.out.println(args[5]);
            if (args[5].equals("day") || args[5].equals("night")) {

                player.sendMessage("No url given");
                return;

            } else {
                trackStorage.insertTrack(args[1], args[5], min * 60 + sec, args[2]);
                player.sendMessage("Added " + args[1] + " to the playlist: " + args[2]);
            }
        } else if (args.length == 7) {
            trackStorage.insertTrack(args[1], args[6], min * 60 + sec, args[2], args[5]);
            player.sendMessage("Added " + args[1] + " to the playlist: " + args[2]);
        } else {
            player.sendMessage("Missing arguments");
        }

    }

    public void deleteTrack(Player player, String name) {


        trackStorage.deleteTrack(name);
        player.sendMessage("Deleted track: " + name);
    }

    public ArrayList<Track> getTracks(Biome biome, String time) {


        return trackStorage.getPlaylist(biome, time);
    }

    public ArrayList<Track> listBiomeTracks(String biome) {


        return trackStorage.getPlaylist(Biome.valueOf(biome.toUpperCase()));
    }


}
