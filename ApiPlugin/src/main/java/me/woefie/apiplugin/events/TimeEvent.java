package me.woefie.apiplugin.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Date;

public class TimeEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();



    private Date time;


    public TimeEvent(Date time) {

        this.time = time;

    }

    public Date getTime() {
        return time;
    }
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


}
