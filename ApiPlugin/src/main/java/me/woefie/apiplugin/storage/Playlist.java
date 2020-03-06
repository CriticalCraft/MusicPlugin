package me.woefie.apiplugin.storage;

import org.bukkit.block.Biome;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Playlist {

    private static JSONArray jsonArray;
    private static JSONObject json;

    public Playlist(TrackStorage json) {

        this.jsonArray = json.getJsonArray();

    }


    public static ArrayList<Track> getTracks(Biome biome, String Time) {

        ArrayList<Track> tracks = new ArrayList<>();
        JSONObject biomeSel;
        for (int i = 0; i <= jsonArray.size() - 1; i++) {
            json = (JSONObject) jsonArray.get(i);
            if (json.get(biome.name().toLowerCase()) != null) {
                biomeSel = (JSONObject) json.get(biome.name().toLowerCase());
                JSONArray time = (JSONArray) biomeSel.get(Time);

                for (int j = 0; j <= time.size() - 1; j++) {
                    tracks.add(getTrack((JSONObject) time.get(j),json.get(biome.name().toLowerCase()).toString()));

                }
                return tracks;
            }
        }
        return null;
    }

    private static Track getTrack(JSONObject object,String biome) {

        return  new Track(object.get("name").toString(), object.get("url").toString(), Integer.parseInt(object.get("duration").toString()),biome);

    }
}
