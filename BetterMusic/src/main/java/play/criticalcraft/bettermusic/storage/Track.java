package play.criticalcraft.bettermusic.storage;


public class Track {

    private String name;
    private String url;
    private int duration;
    private String region;
    private String time;


    public Track(String name, String url, int duration, String region, String time) {
        this.name = name;
        this.url = url;
        this.duration = duration;
        this.region = region;
        this.time = time;

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

    public String getTime() {
        return time;
    }
}
