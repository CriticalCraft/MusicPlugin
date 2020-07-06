package play.criticalcraft.bettermusic.Listeners;

import net.mcjukebox.plugin.bukkit.managers.RegionManager;
import net.mcjukebox.shared.api.Region;
import net.mcjukebox.shared.utils.RegionUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class RegionListener {
    private RegionManager utils;
    private HashMap<UUID, String> playerInRegion = new HashMap();





    public String getRegion (Location l){
        int highestPriority = -1;
        String highestRegion = null;
        Iterator var4 = RegionUtils.getInstance().getProvider().getApplicableRegions(l).iterator();

        while(var4.hasNext()) {
            Region region = (Region)var4.next();
            if (region.getPriority() > highestPriority && this.utils.hasRegion(region.getId())) {
                highestPriority = region.getPriority();
                highestRegion = region.getId();
            }
        }

        return  highestRegion;
    }


}
