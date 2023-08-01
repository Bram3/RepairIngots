package com.github.bram3.repairingots.config;

import com.github.bram3.repairingots.RepairIngots;
import com.github.justadeni.HexColorLib;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public class Config {
    public YamlDocument configDocument;
    public YamlDocument messagesDocument;


    public Config(RepairIngots repairIngots) {
        try {
            this.configDocument = YamlDocument.create(new File(repairIngots.getDataFolder(), "config.yml"), Objects.requireNonNull(repairIngots.getResource("spigot-config.yml")), GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
            this.messagesDocument = YamlDocument.create(new File(repairIngots.getDataFolder(), "messages.yml"), Objects.requireNonNull(repairIngots.getResource("spigot-messages.yml")), GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
        } catch (IOException e) {
            repairIngots.getLogger().log(Level.SEVERE, "Failed to initialize a config file! Shutting down...", e);
            Bukkit.shutdown();
        }
    }

    public String getColoredMessage(String path) {
        return HexColorLib.INSTANCE.color(messagesDocument.getString(path));
    }
}
