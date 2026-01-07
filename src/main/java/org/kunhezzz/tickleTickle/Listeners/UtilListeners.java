package org.kunhezzz.tickleTickle.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kunhezzz.tickleTickle.LiteralManager.PlayerTextManager;

public class UtilListeners implements Listener{
    private final PlayerTextManager manager;

    public UtilListeners(PlayerTextManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        manager.load(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        manager.save(e.getPlayer());
    }
}
