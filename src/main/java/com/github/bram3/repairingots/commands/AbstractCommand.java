package com.github.bram3.repairingots.commands;

import com.github.bram3.repairingots.RepairIngots;
import com.github.bram3.repairingots.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;

public abstract class AbstractCommand extends BukkitCommand {
    RepairIngots repairIngots;
    Config config;

    protected AbstractCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases, @NotNull String permission, Config config, RepairIngots repairIngots) {
        super(name, description, usageMessage, aliases);
        this.setPermissionMessage(ChatColor.RED + "You must have " + permission + " to use this command!");
        this.setPermission(permission);

        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap map = (CommandMap) field.get(Bukkit.getServer());
            map.register(name, this);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            repairIngots.getLogger().log(Level.SEVERE, "Failed to initialize a command. Exiting...\n" + e.getMessage());
            Bukkit.shutdown();
        }


        this.config = config;
        this.repairIngots = repairIngots;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        execute(sender, args);
        return false;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return onTabComplete(sender, args);
    }

    public abstract void execute(CommandSender sender, String[] args);

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

}