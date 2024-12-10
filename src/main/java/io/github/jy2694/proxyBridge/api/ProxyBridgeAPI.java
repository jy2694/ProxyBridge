package io.github.jy2694.proxyBridge.api;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import io.github.jy2694.proxyBridge.ProxyBridge;
import io.github.jy2694.proxyBridge.config.GlobalConfig;
import io.github.jy2694.proxyBridge.entity.Message;
import io.github.jy2694.proxyBridge.entity.MessageType;
import io.github.jy2694.proxyBridge.entity.transfer.OnlineLocation;
import io.github.jy2694.proxyBridge.entity.transfer.OnlinePlayer;
import io.github.jy2694.proxyBridge.entity.transfer.OnlineServer;
import io.github.jy2694.proxyBridge.entity.transfer.OnlineWorld;
import io.github.jy2694.proxyBridge.entity.transfer.RunnableData;
import io.github.jy2694.proxyBridge.entity.transfer.SerializableRunnable;
import io.github.jy2694.proxyBridge.network.VelocityChannel;

public class ProxyBridgeAPI {

    private ProxyBridge plugin;

    public ProxyBridgeAPI(ProxyBridge plugin){
        this.plugin = plugin;
    }

    public OnlinePlayer getPlayer(UUID uuid){
        return OnlinePlayer.fromUniqueId(uuid);
    }

    public OnlinePlayer getPlayer(String name){
        return OnlinePlayer.fromName(name);
    }

    public OnlinePlayer getPlayer(Player player){
        return OnlinePlayer.adapt(player);
    }

    public OnlineLocation getLocation(Location location, String serverName){
        return OnlineLocation.adapt(location, serverName);
    }

    public OnlineLocation getLocation(String world, double x, double y, double z, float yaw, float pitch, String serverName){
        return new OnlineLocation(x, y, z, serverName, world, pitch, yaw);
    }

    public OnlineLocation getLocation(String world, double x, double y, double z, String serverName){
        return new OnlineLocation(x, y, z, serverName, world, 0, 0);
    }

    public OnlineLocation getLocation(Location location){
        return OnlineLocation.adapt(location, GlobalConfig.getServerName());
    }

    public OnlineLocation getLocation(String world, double x, double y, double z){
        return new OnlineLocation(x, y, z, GlobalConfig.getServerName(), world, 0, 0);
    }

    public OnlineWorld getWorld(String worldName){
        if(Bukkit.getWorld(worldName) == null) return null;
        return OnlineWorld.adapt(Bukkit.getWorld(worldName));
    }

    public OnlineWorld getWorld(World world){
        return OnlineWorld.adapt(world);
    }

    public OnlineWorld getWorld(String world, String serverName){
        return new OnlineWorld(world, serverName);
    }

    public OnlineServer getServer(String serverName){
        return new OnlineServer(serverName);
    }

    public OnlineServer getServer(){
        return OnlineServer.adapt();
    }

    public List<SerializableRunnable> getQueuedRunnables(String key){
        return plugin.getMessageChannel().getQueuedRunnable(key);
    }

    public CompletableFuture<Boolean> publishRunnable(String key, SerializableRunnable runnable, String serverName){
        return CompletableFuture.supplyAsync(()->{
            RunnableData data = new RunnableData(key, runnable);
            Message message = Message.ofRequest(MessageType.PROCESS_ENQUEUE, serverName, data);
            plugin.getMessageChannel().waitReply(message);
            plugin.getMessageChannel().send(message);
            while(!plugin.getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (boolean) plugin.getMessageChannel().getReply(message);
        });
    }

    public VelocityChannel getVelocity(){
        return plugin.getVelocityChannel();
    }


}
