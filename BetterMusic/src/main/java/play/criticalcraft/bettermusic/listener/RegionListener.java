//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package play.criticalcraft.bettermusic.Listeners;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import net.mcjukebox.plugin.bukkit.MCJukebox;
import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import net.mcjukebox.plugin.bukkit.api.ResourceType;
import net.mcjukebox.plugin.bukkit.api.models.Media;
import net.mcjukebox.plugin.bukkit.managers.RegionManager;
import net.mcjukebox.plugin.bukkit.managers.shows.Show;
import net.mcjukebox.plugin.bukkit.managers.shows.ShowManager;
import net.mcjukebox.shared.api.Region;
import net.mcjukebox.shared.utils.RegionUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class RegionListener implements Listener {
    private RegionManager utils;
    private HashMap<UUID, String> playerInRegion = new HashMap();

    public RegionListener(RegionManager utils) {
        this.utils = utils;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.onMove(new PlayerMoveEvent(event.getPlayer(), event.getPlayer().getLocation().add(1.0D, 0.0D, 0.0D), event.getPlayer().getLocation()));
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        this.onMove(new PlayerMoveEvent(event.getPlayer(), event.getFrom(), event.getTo()));
    }

    @EventHandler
    public void onMinecartMove(VehicleMoveEvent event) {
        if (event.getVehicle().getPassenger() != null && event.getVehicle().getPassenger() instanceof Player) {
            this.onMove(new PlayerMoveEvent((Player)event.getVehicle().getPassenger(), event.getFrom(), event.getTo()));
        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
            int highestPriority = -1;
            String highestRegion = null;
            Iterator var4 = RegionUtils.getInstance().getProvider().getApplicableRegions(e.getTo()).iterator();

            while(var4.hasNext()) {
                Region region = (Region)var4.next();
                if (region.getPriority() > highestPriority && this.utils.hasRegion(region.getId())) {
                    highestPriority = region.getPriority();
                    highestRegion = region.getId();
                }
            }

            ShowManager showManager = MCJukebox.getInstance().getShowManager();
            if (highestRegion == null && this.utils.hasRegion("__global__")) {
                highestRegion = "__global__";
            }

            String lastShow;
            if (highestRegion != null) {
                if (!this.playerInRegion.containsKey(e.getPlayer().getUniqueId()) || !((String)this.playerInRegion.get(e.getPlayer().getUniqueId())).equals(highestRegion)) {
                    if (this.playerInRegion.containsKey(e.getPlayer().getUniqueId()) && this.utils.getURL((String)this.playerInRegion.get(e.getPlayer().getUniqueId())).equals(this.utils.getURL(highestRegion))) {
                        this.playerInRegion.put(e.getPlayer().getUniqueId(), highestRegion);
                    } else {
                        if (this.playerInRegion.containsKey(e.getPlayer().getUniqueId())) {
                            lastShow = this.utils.getURL((String)this.playerInRegion.get(e.getPlayer().getUniqueId()));
                            if (lastShow.toCharArray()[0] == '@') {
                                showManager.getShow(lastShow).removeMember(e.getPlayer());
                            }
                        }

                        if (this.utils.getURL(highestRegion).toCharArray()[0] == '@') {
                            if (this.playerInRegion.containsKey(e.getPlayer().getUniqueId())) {
                                JukeboxAPI.stopMusic(e.getPlayer());
                            }

                            showManager.getShow(this.utils.getURL(highestRegion)).addMember(e.getPlayer(), true);
                            this.playerInRegion.put(e.getPlayer().getUniqueId(), highestRegion);
                        } else {
                            Media media = new Media(ResourceType.MUSIC, this.utils.getURL(highestRegion));
                            JukeboxAPI.play(e.getPlayer(), media);
                            this.playerInRegion.put(e.getPlayer().getUniqueId(), highestRegion);
                        }
                    }
                }
            } else {
                if (this.playerInRegion.containsKey(e.getPlayer().getUniqueId())) {
                    lastShow = this.utils.getURL((String)this.playerInRegion.get(e.getPlayer().getUniqueId()));
                    this.playerInRegion.remove(e.getPlayer().getUniqueId());
                    if (lastShow == null || lastShow.toCharArray()[0] != '@') {
                        JukeboxAPI.stopMusic(e.getPlayer());
                        return;
                    }

                    if (lastShow.toCharArray()[0] == '@') {
                        showManager.getShow(lastShow).removeMember(e.getPlayer());
                        return;
                    }
                }

            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (this.playerInRegion.containsKey(event.getPlayer().getUniqueId())) {
            String lastAudio = this.utils.getURL((String)this.playerInRegion.get(event.getPlayer().getUniqueId()));
            if (lastAudio == null || lastAudio.toCharArray()[0] != '@') {
                JukeboxAPI.stopMusic(event.getPlayer());
            }

            this.playerInRegion.remove(event.getPlayer().getUniqueId());
        }

        ShowManager showManager = MCJukebox.getInstance().getShowManager();
        if (showManager.inInShow(event.getPlayer().getUniqueId())) {
            Iterator var3 = showManager.getShowsByPlayer(event.getPlayer().getUniqueId()).iterator();

            while(var3.hasNext()) {
                Show show = (Show)var3.next();
                if (!(Boolean)show.getMembers().get(event.getPlayer().getUniqueId())) {
                    return;
                }

                show.removeMember(event.getPlayer());
            }
        }

    }

    public HashMap<UUID, String> getPlayerInRegion() {
        return this.playerInRegion;
    }
}
