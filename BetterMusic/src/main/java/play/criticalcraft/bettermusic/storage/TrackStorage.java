//
// Decompiled by Procyon v0.5.36
//

package play.criticalcraft.bettermusic.storage;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.bukkit.block.Biome;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.DriverManager;

import play.criticalcraft.bettermusic.BetterMusic;

import java.sql.Connection;

public class TrackStorage {


    public static void createDB() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + BetterMusic.i.getDataFolder() + "/Music.db");

            if (conn != null) {
                final DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                conn.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("DB created successfully");
    }

    public static void createTable() {
        String track = "CREATE TABLE track (" +
                "track_id INTEGER NOT NULL," +
                "name TEXT NOT NULL ," +
                "duration INTEGER NOT NULL," +
                "url TEXT NOT NULL," +
                "timeOfDay TEXT," +
                "PRIMARY KEY(track_id)" +
                ");";

        String playlist = "CREATE TABLE playlist (" +
                "playlist_id INTEGER NOT NULL," +
                "name TEXT NOT NULL ," +
                "PRIMARY KEY(playlist_id)" +
                ");";

        String playlisttrack = "CREATE TABLE playlisttrack (" +
                "track_id INTEGER NOT NULL, " +
                "playlist_id INTEGER NOT NULL, " +
                "FOREIGN KEY(playlist_id) REFERENCES playlist(playlist_id), " +
                "FOREIGN KEY(track_id) REFERENCES track(track_id)" +
                ");";

        String trackIndexUrl = "CREATE UNIQUE INDEX trackurl_idx ON track (url);";
        String trackIndexName = "CREATE UNIQUE INDEX trackname_idx ON track (name);";
        String playlistIndex = "CREATE UNIQUE INDEX playlist_idx ON playlist (name);";


        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + BetterMusic.i.getDataFolder() + "/Music.db");

            Statement stmt = conn.createStatement();

            stmt.execute(track);
            stmt.execute(playlist);
            stmt.execute(playlisttrack);
            stmt.execute(trackIndexUrl);
            stmt.execute(trackIndexName);
            stmt.execute(playlistIndex);

            conn.close();
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }


    public boolean insertTrack(String name, int duration, String day, String url) {
        String sql = "INSERT INTO track(name, duration, url, timeOfday) VALUES(?,?,?,?)";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, duration);
            pstmt.setString(3, url);
            pstmt.setString(4, day);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }


    public boolean insertTrack(String name, int duration, String url) {
        String sql = "INSERT INTO track(name, duration, url, timeOfDay) VALUES(?,?,?,?)";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, duration);
            pstmt.setString(3, url);
            pstmt.setString(4, null);


            pstmt.executeUpdate();


        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public void insertPlaylist(String name) {
        String sql = "INSERT OR REPLACE INTO playlist (name) VALUES(?) ";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    public void insertPlaylistTrack(int track_id, int playlist_id) {
        String sql = "INSERT OR REPLACE INTO playlisttrack(track_id, playlist_id) VALUES(?,?)";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, track_id);
            pstmt.setInt(2, playlist_id);


            pstmt.executeUpdate();


        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }


    public ArrayList<Track> getTrackPlaylist(String region, String time) {
        ArrayList<Track> tracks = new ArrayList<Track>();
        String sql = "SELECT track.name,track.url, track.duration " +
                "FROM track " +
                "INNER JOIN playlisttrack pt USING(track_id) " +
                "INNER JOIN playlist p USING(playlist_id) " +
                "WHERE (p.name = ? AND track.timeofday = ?) OR (p.name = ? AND track.timeofday IS NULL);";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, region);
            pstmt.setString(2, time);
            pstmt.setString(3, region);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                tracks.add(new Track(rs.getString("name"), rs.getString("url"), rs.getInt("duration")));
            }


        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return tracks;
    }

    public ArrayList<Track> getTrackPlaylist(String region) {
        ArrayList<Track> tracks = new ArrayList<Track>();
        String sql = "SELECT track.name, track.url, track.duration " +
                "FROM track " +
                "INNER JOIN playlisttrack pt USING(track_id) " +
                "INNER JOIN playlist p USING(playlist_id) " +
                "WHERE p.name = ?";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, region);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                tracks.add(new Track(rs.getString("name"), rs.getString("url"), rs.getInt("duration")));
            }

            conn.close();
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return tracks;
    }

    public Track getTrack(String name, String url) {
        Track _var = null;
        String sql = "SELECT track_id, name, duration, url " +
                "FROM track " +
                "WHERE track.name = ? OR track.url = ?";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, url);

            ResultSet rs = pstmt.executeQuery();

            _var = new Track( rs.getString("name"),rs.getString("url"),rs.getInt("duration"));



        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return _var;

    }

    public int getTrackID(String name, String url) {
        int _var = 0;
        String sql = "SELECT track_id, name, duration, url " +
                "FROM track " +
                "WHERE track.name = ? OR track.url = ?";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, url);

            ResultSet rs = pstmt.executeQuery();


            _var = rs.getInt("track_id");
            conn.close();

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return _var;

    }

    public String getPlaylistName(String name) {
        String _var = null;

        String sql = "SELECT  name " +
                "FROM playlist " +
                "WHERE name = ?";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            _var = rs.getString("name");


        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return _var;
    }

    public int getPlaylistID(String name) {
        int _var = 0;

        String sql = "SELECT playlist_id " +
                "FROM playlist " +
                "WHERE name = ?;";
        try (Connection conn = this.connect(); PreparedStatement ptsmt = conn.prepareStatement(sql)) {

            ptsmt.setString(1, name);
            ResultSet rs = ptsmt.executeQuery();


            _var = rs.getInt("playlist_id");


        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return _var;
    }


    public void deleteTrack(String name) {

        String sql = "DELETE FROM track WHERE name = ?;";


        try (Connection conn = this.connect(); PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, name);
            psmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void deletePlaylist(String name) {
        String sql = "DELETE FROM playlist WHERE name = ?";


        try (Connection conn = this.connect(); PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, name);
            psmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTrackFromPlaylist(String Track, String Playlist) {
        String sql = "DELETE FROM playlisttrack\n" +
                "WHERE track_id IN ( " +
                " SELECT t.track_id " +
                " FROM playlisttrack pt " +
                " INNER JOIN track t USING(track_id) " +
                " WHERE t.name =  ? ) " +
                " AND playlist_id IN( " +
                " SELECT p.playlist_id " +
                " FROM playlisttrack pt " +
                " INNER JOIN playlist p USING(playlist_id) " +
                " WHERE p.name = ?);";


        try (Connection conn = this.connect(); PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, Track);
            psmt.setString(2, Playlist);
            psmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private Connection connect() {
        final String url = "jdbc:sqlite:" + BetterMusic.i.getDataFolder() + "/Music.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}