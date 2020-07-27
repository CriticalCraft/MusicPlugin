//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package play.criticalcraft.bettermusic.listener;

import java.util.*;


import com.sk89q.worldedit.math.BlockVector3;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;


import net.mcjukebox.shared.api.Region;
import org.bukkit.Location;

import org.bukkit.event.Listener;

import org.bukkit.util.Vector;

public class RegionListener implements Listener {


    public RegionListener() {

    }


    public List<Region> getApplicableRegions(Location location) {
        ArrayList regionList = new ArrayList();
        WorldGuardPlatform platform = WorldGuard.getInstance().getPlatform();
        World world = platform.getMatcher().getWorldByName(location.getWorld().getName());
        RegionManager regionManager = platform.getRegionContainer().get(world);
        Vector bukkitVector = location.toVector();
        BlockVector3 vector = BlockVector3.at(bukkitVector.getX(), bukkitVector.getY(), bukkitVector.getZ());
        Set<ProtectedRegion> regions = regionManager.getApplicableRegions(vector).getRegions();
        Iterator var9 = regions.iterator();

        while (var9.hasNext()) {
            ProtectedRegion region = (ProtectedRegion) var9.next();

            regionList.add(new Region(region.getId(), region.getPriority()));
        }

        return regionList;
    }


}
