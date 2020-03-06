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

public class TrackStorage
{
    private Connection connect() {
        final String url = "jdbc:sqlite:" + BetterMusic.i.getDataFolder() + "/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createDB() {
        try {
            final Connection conn = DriverManager.getConnection("jdbc:sqlite:" + BetterMusic.i.getDataFolder() + "/test.db");
            try {
                if (conn != null) {
                    final DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                    conn.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Throwable t) {
                if (conn != null) {
                    try {
                        conn.close();
                    }
                    catch (Throwable exception) {
                        t.addSuppressed(exception);
                    }
                }
                throw t;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("DB created successfully");
    }

    public static void createTable() {
        final String tracks = "CREATE TABLE IF NOT EXISTS tracks (tracks_id INTEGER PRIMARY KEY , name      TEXT    , url       TEXT    NOT NULL, duration  INT     NOT NULL, playlist TEXT   NOT NULL, timeofday TEXT    )";
        try {
            final Connection conn = DriverManager.getConnection("jdbc:sqlite:" + BetterMusic.i.getDataFolder() + "/test.db");
            try {
                final Statement stmt = conn.createStatement();
                try {
                    stmt.execute(tracks);
                    if (stmt != null) {
                        stmt.close();
                    }
                }
                catch (Throwable t) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (Throwable exception) {
                            t.addSuppressed(exception);
                        }
                    }
                    throw t;
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Throwable t2) {
                if (conn != null) {
                    try {
                        conn.close();
                    }
                    catch (Throwable exception2) {
                        t2.addSuppressed(exception2);
                    }
                }
                throw t2;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertTrack(final String name, final String url, final int duration, final String playlist, final String day) {
        final String sql = "INSERT INTO tracks(name, url, duration, playlist, timeofday) VALUES(?,?,?,?,?)";
        try {
            final Connection conn = this.connect();
            try {
                final PreparedStatement pstmt = conn.prepareStatement(sql);
                try {
                    pstmt.setString(1, name);
                    pstmt.setString(2, url);
                    pstmt.setInt(3, duration);
                    pstmt.setString(4, playlist);
                    pstmt.setString(5, day);
                    pstmt.executeUpdate();
                    if (pstmt != null) {
                        pstmt.close();
                    }
                }
                catch (Throwable t) {
                    if (pstmt != null) {
                        try {
                            pstmt.close();
                        }
                        catch (Throwable exception) {
                            t.addSuppressed(exception);
                        }
                    }
                    throw t;
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Throwable t2) {
                if (conn != null) {
                    try {
                        conn.close();
                    }
                    catch (Throwable exception2) {
                        t2.addSuppressed(exception2);
                    }
                }
                throw t2;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertTrack(final String name, final String url, final int duration, final String playlist) {
        final String sql = "INSERT INTO tracks(name, url, duration, playlist) VALUES(?,?,?,?)";
        try {
            final Connection conn = this.connect();
            try {
                final PreparedStatement pstmt = conn.prepareStatement(sql);
                try {
                    pstmt.setString(1, name);
                    pstmt.setString(2, url);
                    pstmt.setInt(3, duration);
                    pstmt.setString(4, playlist);
                    pstmt.executeUpdate();
                    if (pstmt != null) {
                        pstmt.close();
                    }
                }
                catch (Throwable t) {
                    if (pstmt != null) {
                        try {
                            pstmt.close();
                        }
                        catch (Throwable exception) {
                            t.addSuppressed(exception);
                        }
                    }
                    throw t;
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Throwable t2) {
                if (conn != null) {
                    try {
                        conn.close();
                    }
                    catch (Throwable exception2) {
                        t2.addSuppressed(exception2);
                    }
                }
                throw t2;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Track> getTracks(final Biome biome, final String Time) {
        final ArrayList<Track> tracks = new ArrayList<Track>();
        final String sql = "SELECT name, url, duration, playlist FROM tracks WHERE (playlist = '" + biome.name().toLowerCase() + "' AND timeofday = '" + Time + "') OR (playlist = '" + biome.name().toLowerCase() + "' AND timeofday IS NULL);";
        try {
            final Connection conn = DriverManager.getConnection("jdbc:sqlite:" + BetterMusic.i.getDataFolder() + "/test.db");
            try {
                final Statement stmt = conn.createStatement();
                try {
                    final ResultSet rs = stmt.executeQuery(sql);
                    try {
                        while (rs.next()) {
                            tracks.add(new Track(rs.getString("name"), rs.getString("url"), rs.getInt("duration"), rs.getString("playlist")));
                        }
                        if (rs != null) {
                            rs.close();
                        }
                    }
                    catch (Throwable t) {
                        if (rs != null) {
                            try {
                                rs.close();
                            }
                            catch (Throwable exception) {
                                t.addSuppressed(exception);
                            }
                        }
                        throw t;
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                }
                catch (Throwable t2) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (Throwable exception2) {
                            t2.addSuppressed(exception2);
                        }
                    }
                    throw t2;
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Throwable t3) {
                if (conn != null) {
                    try {
                        conn.close();
                    }
                    catch (Throwable exception3) {
                        t3.addSuppressed(exception3);
                    }
                }
                throw t3;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tracks;
    }
}