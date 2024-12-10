package io.github.jy2694.proxyBridge.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.jy2694.proxyBridge.ProxyBridge;

public abstract class AbstractConfiguration {
    protected final File file;
    protected final FileConfiguration config;

    public AbstractConfiguration(String fileName) {
        this.file = new File(ProxyBridge.getInstance().getDataFolder(), fileName);
        if(!this.file.exists()) ProxyBridge.getInstance().saveResource(fileName, false);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public abstract void load();

    public void save(){
        try {
            this.config.save(this.file);
        } catch (IOException ignored) {}
    }
}
