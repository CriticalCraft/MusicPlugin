package me.woefie.apiplugin.Listeners;

import me.woefie.apiplugin.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerBiomeTracker implements Listener {
    private HashMap<Player, Biome> lastChecked;
    public PlayerBiomeTracker() {
        this.lastChecked = new HashMap<>();
        //Bukkit.getPluginManager().registerEvents(this, APIPlugin);
    }


    public Biome getBiome(Player p){
        Biome now = p.getLocation().getBlock().getBiome();

        if(!lastChecked.containsKey(p)){
            lastChecked.put(p, now);
            return now;
        }
        for (Location all: getRadiusOfBlock(p.getLocation(), 2)) {
            if (all.getBlock().getBiome() != now){
                return lastChecked.get(p);
            }
            
        }
        lastChecked.put(p,now);
        return now;
    }

    private ArrayList<Location> getRadiusOfBlock(Location loc, int radius){
        ArrayList<Location> locs = new ArrayList<>();

        int radiusSquared = radius*radius;
        for(int x= -radius; x<= radius; x++){
            for(int z = -radius; z <= radius; z++){
                if( (x*x) + (z*z) <= radiusSquared){
                    locs.add(new Location(loc.getWorld(),x+loc.getX(), loc.getY(), z+loc.getZ()));
                }
            }
        }
        return locs;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        lastChecked.remove(e.getPlayer());
    }
}
