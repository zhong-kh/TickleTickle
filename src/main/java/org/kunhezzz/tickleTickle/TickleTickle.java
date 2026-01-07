package org.kunhezzz.tickleTickle;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.kunhezzz.tickleTickle.Commands.TickleCommand;
import org.kunhezzz.tickleTickle.Listeners.UtilListeners;
import org.kunhezzz.tickleTickle.LiteralManager.PlayerTextManager;


public final class TickleTickle extends JavaPlugin {

    private PlayerTextManager textManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        textManager = new PlayerTextManager(this);
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(
                new UtilListeners(textManager),
                this
        );
    }

    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, cmds -> {
            cmds.registrar().register(new TickleCommand(this).buildCmd());
        });
    }

    public PlayerTextManager getTextManager() {
        return textManager;
    }
}
