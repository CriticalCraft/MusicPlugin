package play.criticalcraft.bettermusic.storage;

import org.bukkit.ChatColor;
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

    //                      1           2               3           4               5
    //insertTrack (String name, String playlist, int duration,  String url, String day)
    public void insertTrack(Player player, String[] args) {
        int min, sec;
        int playlistID = 0, trackID = 0;
        try {
            min = Integer.parseInt(args[3]);
            sec = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            player.sendMessage("Minutes and seconds need to be a number!");
            return;
        }

        if (args.length == 6) {


            if (!args[2].equals(trackStorage.getPlaylistName(args[2]))) {

                insertPlaylist(args[2]);
                player.sendMessage(ChatColor.GREEN + "New playlist made");
            }


            if (!args[1].equals(trackStorage.getTrack(args[1], args[5]).getName()) && !args[5].equals(trackStorage.getTrack(args[1], args[5]).getUrl())) {

                trackStorage.insertTrack(args[1], min * 60 + sec, args[5]);
                player.sendMessage(ChatColor.GREEN + "Added Song to database");
            } else {
                player.sendMessage(ChatColor.YELLOW + "Song was already in database");
            }


            playlistID = trackStorage.getPlaylistID(args[2]);
            trackID = trackStorage.getTrackID(args[1], args[5]);


            trackStorage.insertPlaylistTrack(trackID, playlistID);

            player.sendMessage("Added " + args[1] + " to the playlist: " + args[2]);


        } else if (args.length == 7) {
            if (trackStorage.insertTrack(args[1], min * 60 + sec, args[5], args[7])) {
                player.sendMessage("Added " + args[1] + " to the playlist: " + args[2]);
            } else {
                player.sendMessage("Song already in database");
            }

        } else {
            player.sendMessage("Missing arguments");
        }

    }

    private void insertPlaylist(String name) {
        trackStorage.insertPlaylist(name);
    }

    public void deleteTrack(Player player, String name, String region) {

        trackStorage.deleteTrackFromPlaylist(name, region);
        player.sendMessage("Deleted song: " + name);
    }

    public void deletePlaylist(Player player, String name) {


        trackStorage.deletePlaylist(name);
        player.sendMessage("Deleted playlist: " + name);
    }

    public ArrayList<Track> getTracks(String region, String time) {


        return trackStorage.getTrackPlaylist(region.toLowerCase(), time);
    }

    public ArrayList<Track> listBiomeTracks(String region) {
        ArrayList<Track> tracks = new ArrayList<>();
        try {
            tracks = trackStorage.getTrackPlaylist(region.toLowerCase());
        } catch (Exception e) {

        }

        return tracks;
    }


}
