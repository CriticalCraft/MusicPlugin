package play.criticalcraft.bettermusic.storage;


public class Track {

    private String name;
    private String url;
    private int duration;
    private String region;


    public Track(String name, String url, int duration, String region) {
        this.name = name;
        this.url = url;
        this.duration = duration;
        this.region = region;
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

    public String getRegion() {
        return region;
    }
}
