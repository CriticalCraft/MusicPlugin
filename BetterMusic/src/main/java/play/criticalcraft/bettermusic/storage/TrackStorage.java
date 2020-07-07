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
        String tracks = "CREATE TABLE IF NOT EXISTS tracks (tracks_id INTEGER PRIMARY KEY , name      TEXT    , url       TEXT    NOT NULL, duration  INT     NOT NULL, playlist TEXT   NOT NULL, timeofday TEXT    )";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + BetterMusic.i.getDataFolder() + "/Music.db");

            Statement stmt = conn.createStatement();

            stmt.execute(tracks);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void insertTrack(String name, String url, int duration, String playlist, String day) {
        String sql = "INSERT INTO tracks(name, url, duration, playlist, timeofday) VALUES(?,?,?,?,?)";
        try {
            Connection conn = this.connect();

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2, url);
            pstmt.setInt(3, duration);
            pstmt.setString(4, playlist);
            pstmt.setString(5, day);
            pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void insertTrack(String name, String url, int duration, String playlist) {
        String sql = "INSERT INTO tracks(name, url, duration, playlist, timeofday) VALUES(?,?,?,?,?)";
        try {
            Connection conn = this.connect();

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2, url);
            pstmt.setInt(3, duration);
            pstmt.setString(4, playlist);
            pstmt.setString(5, null);

            pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Track> getPlaylist(String region, String Time) {
        ArrayList<Track> tracks = new ArrayList<Track>();
        String sql = "SELECT name, url, duration, playlist FROM tracks WHERE (playlist = '" + region.toLowerCase() + "' AND timeofday = '" + Time + "') OR (playlist = '" + region.toLowerCase() + "' AND timeofday IS NULL);";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + BetterMusic.i.getDataFolder() + "/Music.db");

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tracks.add(new Track(rs.getString("name"), rs.getString("url"), rs.getInt("duration"), rs.getString("playlist")));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tracks;
    }

    public ArrayList<Track> getPlaylist(String region) {
        ArrayList<Track> tracks = new ArrayList<Track>();
        String sql = "SELECT name, url, duration, playlist FROM tracks WHERE playlist = '" + region.toLowerCase() + "'";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + BetterMusic.i.getDataFolder() + "/Music.db");

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tracks.add(new Track(rs.getString("name"), rs.getString("url"), rs.getInt("duration"), rs.getString("playlist")));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tracks;
    }

    public void deleteTrack(String name) {

        String sql = "DELETE FROM tracks WHERE name = ?";


        try (Connection conn = this.connect(); PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, name);
            psmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}