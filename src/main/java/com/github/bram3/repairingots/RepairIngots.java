package com.github.bram3.repairingots;

import com.github.bram3.repairingots.commands.GiveIngotCommand;
import com.github.bram3.repairingots.config.Config;
import com.github.bram3.repairingots.events.InventoryClickEvent;
import com.github.bram3.repairingots.managers.IngotManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RepairIngots extends JavaPlugin {

    @Override
    public void onEnable() {
        Config config = new Config(this);
        IngotManager ingotManager = new IngotManager(config);
        new GiveIngotCommand(this, config, ingotManager);
        getServer().getPluginManager().registerEvents(new InventoryClickEvent(ingotManager), this);
    }
}
