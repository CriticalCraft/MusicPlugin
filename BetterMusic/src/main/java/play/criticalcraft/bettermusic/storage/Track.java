package play.criticalcraft.bettermusic.storage;


public class Track {

    private String name;
    private String url;
    private int duration;


    public Track(String name, String url, int duration) {
        this.name = name;
        this.url = url;
        this.duration = duration;
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


}
