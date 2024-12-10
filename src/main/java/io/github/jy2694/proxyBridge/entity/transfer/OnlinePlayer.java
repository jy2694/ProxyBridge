package io.github.jy2694.proxyBridge.entity.transfer;


import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.jy2694.proxyBridge.ProxyBridge;
import io.github.jy2694.proxyBridge.config.GlobalConfig;
import io.github.jy2694.proxyBridge.entity.Message;
import io.github.jy2694.proxyBridge.entity.MessageType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class OnlinePlayer {

    public static OnlinePlayer adapt(Player player) {
        return new OnlinePlayer(player.getName(), player.getUniqueId());
    }

    public static OnlinePlayer fromName(String name){
        return new OnlinePlayer(name, null);
    }

    public static OnlinePlayer fromUniqueId(UUID uniqueId){
        return new OnlinePlayer(null, uniqueId);
    }

    private String name;
    private UUID uniqueId;

    public OnlinePlayer(String name, UUID uniqueId) {
        this.name = name;
        this.uniqueId = uniqueId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof OnlinePlayer onlinePlayer)) return false;
        return Objects.equals(name, onlinePlayer.name) || Objects.equals(uniqueId, onlinePlayer.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uniqueId);
    }

    public boolean isInServer(){
        return (name != null && Bukkit.getPlayer(name) != null) || (uniqueId != null && Bukkit.getPlayer(uniqueId) != null);
    }
    public CompletableFuture<Double> getHealthScale(){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getHealthScale();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getHealthScale();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_HEALTH_SCALE, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (double) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> setHealthScale(double healthScale){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setHealthScale(healthScale);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setHealthScale(healthScale);
                return true;
            }
            Message message = Message.ofRequest(MessageType.PLAYER_SET_HEALTH_SCALE, "ALL", this, healthScale);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Double> getHealth(){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getHealth();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getHealth();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_HEALTH, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (double) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> setHealth(double health){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setHealth(health);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setHealth(health);
                return true;
            }
            Message message = Message.ofRequest(MessageType.PLAYER_SET_HEALTH, "ALL", this, health);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Float> getFlySpeed(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getFlySpeed();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getFlySpeed();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_FLY_SPEED, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (float) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> setFlySpeed(float speed){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setFlySpeed(speed);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setFlySpeed(speed);
                return true;
            }
            Message message = Message.ofRequest(MessageType.PLAYER_SET_FLY_SPEED, "ALL", this, speed);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Float> getWalkSpeed(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getWalkSpeed();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getWalkSpeed();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_WALK_SPEED, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (float) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> setWalkSpeed(float speed){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setWalkSpeed(speed);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setWalkSpeed(speed);
                return true;
            }
            Message message = Message.ofRequest(MessageType.PLAYER_SET_WALK_SPEED, "ALL", this, speed);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<UUID> getUniqueId(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getUniqueId();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getUniqueId();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_UUID, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (UUID) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<GameMode> getGameMode(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getGameMode();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getGameMode();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_GAMEMODE, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (GameMode) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> setGameMode(GameMode gameMode){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setGameMode(gameMode);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setGameMode(gameMode);
                return true;
            }
            Message message = Message.ofRequest(MessageType.PLAYER_SET_GAMEMODE, "ALL", this, gameMode);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> getAllowFlight(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getAllowFlight();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getAllowFlight();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_ALLOW_FLIGHT, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> setAllowFlight(boolean allow){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setAllowFlight(allow);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setAllowFlight(allow);
                return true;
            }
            Message message = Message.ofRequest(MessageType.PLAYER_SET_ALLOW_FLIGHT, "ALL", this, allow);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<OnlineLocation> getBedSpawnLocation(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return OnlineLocation.adapt(Bukkit.getPlayer(uniqueId).getLocation(), GlobalConfig.getServerName());
            else if(name != null && Bukkit.getPlayer(name) != null) return OnlineLocation.adapt(Bukkit.getPlayer(name).getLocation(), GlobalConfig.getServerName());
            Message message = Message.ofRequest(MessageType.PLAYER_GET_BED_SPAWN_LOCATION, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (OnlineLocation) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<OnlineLocation> getCompassTarget(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return OnlineLocation.adapt(Bukkit.getPlayer(uniqueId).getCompassTarget(), GlobalConfig.getServerName());
            else if(name != null && Bukkit.getPlayer(name) != null) return OnlineLocation.adapt(Bukkit.getPlayer(name).getCompassTarget(), GlobalConfig.getServerName());
            Message message = Message.ofRequest(MessageType.PLAYER_GET_COMPASS_TARGET, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (OnlineLocation) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Float> getExp(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getExp();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getExp();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_EXP, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (float) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> setExp(float exp){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setExp(exp);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setExp(exp);
                return true;
            }
            Message message = Message.ofRequest(MessageType.PLAYER_SET_EXP, "ALL", this, exp);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Integer> getTotalExperience(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getTotalExperience();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getTotalExperience();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_TOTAL_EXP, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (int) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> setTotalExperience(int exp){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setTotalExperience(exp);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setTotalExperience(exp);
                return true;
            }
            Message message = Message.ofRequest(MessageType.PLAYER_SET_TOTAL_EXP, "ALL", this, exp);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Integer> getLevel(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getLevel();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getLevel();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_LEVEL, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (int) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> setLevel(int level){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setLevel(level);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setLevel(level);
                return true;
            }
            Message message = Message.ofRequest(MessageType.PLAYER_SET_LEVEL, "ALL", this, level);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Integer> getFoodLevel(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getFoodLevel();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getFoodLevel();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_FOOD_LEVEL, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (int) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> setFoodLevel(int level){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setFoodLevel(level);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setFoodLevel(level);
                return true;
            }
            Message message = Message.ofRequest(MessageType.PLAYER_SET_FOOD_LEVEL, "ALL", this, level);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<OnlineLocation> getLocation(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return OnlineLocation.adapt(Bukkit.getPlayer(uniqueId).getLocation(), GlobalConfig.getServerName());
            else if(name != null && Bukkit.getPlayer(name) != null) return OnlineLocation.adapt(Bukkit.getPlayer(name).getLocation(), GlobalConfig.getServerName());
            Message message = Message.ofRequest(MessageType.PLAYER_GET_LOCATION, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (OnlineLocation) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<OnlineWorld> getWorld(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return OnlineWorld.adapt(Bukkit.getPlayer(uniqueId).getWorld());
            else if(name != null && Bukkit.getPlayer(name) != null) return OnlineWorld.adapt(Bukkit.getPlayer(name).getWorld());
            Message message = Message.ofRequest(MessageType.PLAYER_GET_WORLD, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (OnlineWorld) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<String> getName(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getName();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getName();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_NAME, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (String) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<String> getServer(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return GlobalConfig.getServerName();
            else if(name != null && Bukkit.getPlayer(name) != null) return GlobalConfig.getServerName();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_SERVER, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (String) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> sendMessage(String line){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).sendMessage(line);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).sendMessage(line);
                return true;
            }
            Message message = Message.ofRequest(MessageType.SEND_MESSAGE, "ALL", this, line);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).sendTitle(title, subtitle, fadeIn, stay, fadeOut);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).sendTitle(title, subtitle, fadeIn, stay, fadeOut);
                return true;
            }
            Message message = Message.ofRequest(MessageType.SEND_TITLE, "ALL", this, title, subtitle, fadeIn, stay, fadeOut);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> sendActionBar(String line){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(line));
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(line));
                return true;
            }
            Message message = Message.ofRequest(MessageType.SEND_ACTIONBAR, "ALL", this, line);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> teleport(OnlineLocation location){
        return CompletableFuture.supplyAsync(()->{
            Player player = null;
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) player = Bukkit.getPlayer(uniqueId);
            else if(name != null && Bukkit.getPlayer(name) != null) player = Bukkit.getPlayer(name);
            if(player == null){
                Message message = Message.ofRequest(MessageType.TELEPORT, "ALL", this, location);
                ProxyBridge.getInstance().getMessageChannel().waitReply(message);
                ProxyBridge.getInstance().getMessageChannel().send(message);
                while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
                if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
                return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
            } else {
                if(location.getServer().equals(GlobalConfig.getServerName())){
                    Location bukkitLocation = new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
                    bukkitLocation.setPitch(location.getPitch());
                    bukkitLocation.setYaw(location.getYaw());
                    player.teleport(bukkitLocation);
                    return true;
                } else {
                    Message message = Message.ofRequest(MessageType.PROCESS_ENQUEUE, location.getServer(), new RunnableData(player.getUniqueId() + ":" + PlayerJoinEvent.class.getName(), (SerializableRunnable) () -> {
                        Player player1 = null;
                        if (uniqueId != null && Bukkit.getPlayer(uniqueId) != null)
                            player1 = Bukkit.getPlayer(uniqueId);
                        else if (name != null && Bukkit.getPlayer(name) != null) player1 = Bukkit.getPlayer(name);
                        Location bukkitLocation = new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
                        bukkitLocation.setPitch(location.getPitch());
                        bukkitLocation.setYaw(location.getYaw());
                        player1.teleport(bukkitLocation);
                    }));
                    ProxyBridge.getInstance().getMessageChannel().waitReply(message);
                    ProxyBridge.getInstance().getMessageChannel().send(message);
                    while (!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
                    if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null){
                        //teleport exception
                        return false;
                    }
                    ProxyBridge.getInstance().getVelocityChannel().connect(player, location.getServer());
                    return true;
                }
            }
        });
    }
    public CompletableFuture<Boolean> isOp(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).isOp();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).isOnline();
            Message message = Message.ofRequest(MessageType.PLAYER_GET_OP, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> setOp(boolean op){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setOp(op);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setOp(op);
                return true;
            }
            Message message = Message.ofRequest(MessageType.PLAYER_SET_OP, "ALL", this, op);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
}
