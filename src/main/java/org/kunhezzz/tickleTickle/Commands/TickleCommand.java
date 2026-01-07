package org.kunhezzz.tickleTickle.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.kunhezzz.tickleTickle.Events.TickleEvent;
import org.kunhezzz.tickleTickle.LiteralManager.PlayerTextManager;
import org.kunhezzz.tickleTickle.TickleTickle;

public class TickleCommand {

    private final TickleTickle plugin;
    public TickleCommand(TickleTickle plugin) {
        this.plugin = plugin;
    }

    public LiteralCommandNode<CommandSourceStack> buildCmd() {

        // Root
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("tickle");

        // Set
        root.then(Commands.literal("set")
                .then(Commands.argument("text", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            Entity executor = ctx.getSource().getExecutor();
                            if (!(executor instanceof Player player)) {
                                executor.sendMessage("[TickleTickle] Only player can tickle");
                                return Command.SINGLE_SUCCESS;
                            }

                            String text = StringArgumentType.getString(ctx, "text");
                            plugin.getTextManager().setText(player, text);
                            executor.sendMessage("[TickleTickle] File saved successfully");
                            return Command.SINGLE_SUCCESS;
                        })
                )
        );

        // Tickle player
        root.then(Commands.argument("player", ArgumentTypes.player())
                        .executes(ctx -> {
                            Entity entity = ctx.getSource().getExecutor();
                            if (! (entity instanceof Player)) {
                                entity.sendMessage("[TickleTickle] Only players could tickle");
                                return Command.SINGLE_SUCCESS;
                            }

                            Player tickler = (Player) entity;
                            final PlayerSelectorArgumentResolver resolver = ctx.getArgument("player", PlayerSelectorArgumentResolver.class);
                            final Player tickled = resolver.resolve(ctx.getSource()).getFirst();

                            String str = plugin.getTextManager().getText(tickled);
                            tickled.getWorld().spawnParticle(Particle.CRIT, tickled.getLocation(), 6, 0, 0, 0, 0.6);
                            String name = tickled.getName();
                            plugin.getServer().broadcastMessage("[TickleTickle] " + tickler.getName() + " tickled " + tickled.getName() + " and says " + str);
                            if (! (tickled.getName().equals(tickler.getName()))) {
                                tickled.sendMessage("[TickleTickle] " + tickler.getName() + "tickled you and says " + str);
                            } else {
                                tickled.sendMessage("[TickleTickle] you tickled yourself and say " + str);
                            }


                            TickleEvent e = new TickleEvent(tickler, tickled);
                            Bukkit.getPluginManager().callEvent(e);

                            return Command.SINGLE_SUCCESS;
                        })
        );

        return root.build();
    }
}
