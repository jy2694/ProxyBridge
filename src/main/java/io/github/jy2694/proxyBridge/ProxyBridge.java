package io.github.jy2694.proxyBridge;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.jy2694.proxyBridge.api.ProxyBridgeAPI;
import io.github.jy2694.proxyBridge.config.GlobalConfig;
import io.github.jy2694.proxyBridge.config.PluginConfiguration;
import io.github.jy2694.proxyBridge.network.Channel;
import io.github.jy2694.proxyBridge.network.PluginMessageChannel;
import io.github.jy2694.proxyBridge.network.RedisChannel;
import io.github.jy2694.proxyBridge.network.VelocityChannel;

public final class ProxyBridge extends JavaPlugin {

    private static ProxyBridge instance;
    private static ProxyBridgeAPI api;
    private VelocityChannel velocityChannel;
    private Channel messageChannel;

    @Override
    public void onEnable() {
        instance = this;
        //Message Channel open
        new PluginConfiguration().load();
        if(GlobalConfig.isPluginMessageChannel()){
            messageChannel = new PluginMessageChannel(this);
        } else if(GlobalConfig.isRedisChannel()){
            messageChannel = new RedisChannel(GlobalConfig.getRedisHost(), GlobalConfig.getRedisPort(), GlobalConfig.getRedisPassword());
        } else {
            getLogger().severe("No message channel is enabled. Please check the config.yml file.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        try{
            messageChannel.open();
        } catch(Exception e){
            getLogger().severe("Message channel open failed. Please check the config.yml file.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        //Velocity command channel open
        velocityChannel = new VelocityChannel(this);
        api = new ProxyBridgeAPI(this);
    }

    @Override
    public void onDisable() {
        
    }

    public Channel getMessageChannel() {
        return messageChannel;
    }

    public VelocityChannel getVelocityChannel() {
        return velocityChannel;
    }

    public static ProxyBridge getInstance() {
        return instance;
    }

    public static ProxyBridgeAPI getAPI() {
        return api;
    }
}
