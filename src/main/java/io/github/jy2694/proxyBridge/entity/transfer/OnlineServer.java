package io.github.jy2694.proxyBridge.entity.transfer;

import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import io.github.jy2694.proxyBridge.ProxyBridge;
import io.github.jy2694.proxyBridge.config.GlobalConfig;
import io.github.jy2694.proxyBridge.entity.Message;
import io.github.jy2694.proxyBridge.entity.MessageType;

public class OnlineServer {
    
    public static OnlineServer adapt(){
        return new OnlineServer(GlobalConfig.getServerName());
    }

    private final String server;

    public OnlineServer(String server){
        this.server = server;
    }

    public String getServer(){
        return server;
    }

    public CompletableFuture<Boolean> broadcastMessage(String msg){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())){
                Bukkit.getServer().broadcastMessage(msg);
                return true;
            }
            Message message = Message.ofRequest(MessageType.SERVER_BROADCAST_MESSAGE, "ALL", this, msg);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<String[]> getOnlinePlayers(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new);
            Message message = Message.ofRequest(MessageType.SERVER_GET_ONLINE_PLAYERS, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (String[]) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Integer> getOnlinePlayersCount(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getServer().getOnlinePlayers().size();
            Message message = Message.ofRequest(MessageType.SERVER_GET_ONLINE_PLAYERS_COUNT, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (int) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> getAllowEnd(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getServer().getAllowEnd();
            Message message = Message.ofRequest(MessageType.SERVER_GET_ALLOW_END, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> getAllowNether(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getServer().getAllowNether();
            Message message = Message.ofRequest(MessageType.SERVER_GET_ALLOW_NETHER, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> getAllowFlight(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getServer().getAllowFlight();
            Message message = Message.ofRequest(MessageType.SERVER_GET_ALLOW_FLIGHT, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<GameMode> getDefaultGameMode(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getServer().getDefaultGameMode();
            Message message = Message.ofRequest(MessageType.SERVER_GET_DEFAULT_GAMEMODE, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (GameMode) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> setDefaultGameMode(GameMode gameMode){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) {
                Bukkit.getServer().setDefaultGameMode(gameMode);
                return true;
            }
            Message message = Message.ofRequest(MessageType.SERVER_SET_DEFAULT_GAMEMODE, "ALL", this, gameMode);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> banIp(String ip){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) {
                try{
                    InetAddress address = InetAddress.getByName(ip);
                    Bukkit.getServer().banIP(address);
                    return true;
                } catch(Exception e){
                    return false;
                }
            }
            Message message = Message.ofRequest(MessageType.SERVER_BAN_IP, "ALL", this, ip);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<String[]> getBannedPlayers(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getServer().getBannedPlayers().stream().map(OfflinePlayer::getName).toArray(String[]::new);
            Message message = Message.ofRequest(MessageType.SERVER_GET_BANNED_PLAYERS, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (String[]) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Integer> getMaxPlayers(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getServer().getMaxPlayers();
            Message message = Message.ofRequest(MessageType.SERVER_GET_MAX_PLAYERS, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (int) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> setMaxPlayers(int maxPlayers){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) {
                Bukkit.getServer().setMaxPlayers(maxPlayers);
                return true;
            }
            Message message = Message.ofRequest(MessageType.SERVER_SET_MAX_PLAYERS, "ALL", this, maxPlayers);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<String[]> getWorlds(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getServer().getWorlds().stream().map(World::getName).toArray(String[]::new);
            Message message = Message.ofRequest(MessageType.SERVER_GET_WORLDS, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (String[]) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<String> getMOTD(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getServer().getMotd();
            Message message = Message.ofRequest(MessageType.SERVER_GET_MOTD, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (String) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> setMOTD(String motd){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) {
                Bukkit.getServer().setMotd(motd);
                return true;
            }
            Message message = Message.ofRequest(MessageType.SERVER_SET_MOTD, "ALL", this, motd);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<String> getBukkitVersion(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getVersion();
            Message message = Message.ofRequest(MessageType.SERVER_GET_BUKKIT_VERSION, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (String) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
}