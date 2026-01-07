package org.kunhezzz.tickleTickle.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TickleEvent extends Event implements Cancellable {

    private boolean cancelled;
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private Player tickler;

    public @NotNull Player getTickled() {
        return tickled;
    }

    public @NotNull Player getTickler() {
        return tickler;
    }

    private Player tickled;


    public TickleEvent(@NotNull Player tickler, @NotNull Player tickled) {
        this.tickled = tickled;
        this.tickler = tickler;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }


}
