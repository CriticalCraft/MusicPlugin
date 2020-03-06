package me.woefie.apiplugin.storage;

import javax.naming.Name;

public class Track {

    private String name;
    private String url;
    private int duration;
    private String biome;


    public Track(String name, String url, int duration, String biome) {
        this.name = name;
        this.url = url;
        this.duration = duration;
        this.biome = biome;
    }

    public String getName() {
        return name;
    }


    public String getUrl() {
        return url;
    }

    public int getDuration() {
        return duration;
    }

    public String getBiome() {
        return biome;
    }
}
