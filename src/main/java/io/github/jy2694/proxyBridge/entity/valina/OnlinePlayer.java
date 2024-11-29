package io.github.jy2694.proxyBridge.entity.valina;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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

    public CompletableFuture<Double> getHealthScale(){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getHealthScale();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getHealthScale();
            //todo : request health scale
            return 20.0;
        });
    }

    public CompletableFuture<Double> getHealth(){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getHealth();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getHealth();
            //todo : request health
            return 20.0;
        });
    }

    public CompletableFuture<Float> getFlySpeed(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getFlySpeed();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getFlySpeed();
            //todo : request fly speed
            return 0.0f;
        });
    }

    public CompletableFuture<Float> getWalkSpeed(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getWalkSpeed();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getWalkSpeed();
            //todo : request walk speed
            return 0.0f;
        });
    }

    public CompletableFuture<UUID> getUniqueId(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getUniqueId();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getUniqueId();
            //todo : request uuid
            return null;
        });
    }

    public CompletableFuture<GameMode> getGameMode(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getGameMode();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getGameMode();
            //todo : request gamemode
            return GameMode.SURVIVAL;
        });
    }

    public CompletableFuture<Boolean> getAllowFlight(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getAllowFlight();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getAllowFlight();
            //todo : request allow flight
            return false;
        });
    }

    public CompletableFuture<OnlineLocation> getBedSpawnLocation(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return OnlineLocation.adapt(Bukkit.getPlayer(uniqueId).getLocation(), "");
            else if(name != null && Bukkit.getPlayer(name) != null) return OnlineLocation.adapt(Bukkit.getPlayer(name).getLocation(), "");
            //todo : request location
            return null;
        });
    }

    public CompletableFuture<OnlineLocation> getCompassTarget(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return OnlineLocation.adapt(Bukkit.getPlayer(uniqueId).getCompassTarget(), "");
            else if(name != null && Bukkit.getPlayer(name) != null) return OnlineLocation.adapt(Bukkit.getPlayer(name).getCompassTarget(), "");
            //todo : request location
            return null;
        });
    }

    public CompletableFuture<Float> getExp(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getExp();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getExp();
            //todo : request exp
            return 0.0f;
        });
    }

    public CompletableFuture<Integer> getTotalExperience(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getTotalExperience();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getTotalExperience();
            //todo : request total exp
            return 0;
        });
    }

    public CompletableFuture<Integer> getLevel(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getLevel();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getLevel();
            //todo : request level
            return 0;
        });
    }

    public CompletableFuture<Integer> getFoodLevel(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getFoodLevel();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getFoodLevel();
            //todo : request level
            return 0;
        });
    }

    public CompletableFuture<OnlineLocation> getLocation(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return OnlineLocation.adapt(Bukkit.getPlayer(uniqueId).getLocation(), "");
            else if(name != null && Bukkit.getPlayer(name) != null) return OnlineLocation.adapt(Bukkit.getPlayer(name).getLocation(), "");
            //todo : request location
            return null;
        });
    }

    public CompletableFuture<String> getName(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return Bukkit.getPlayer(uniqueId).getName();
            else if(name != null && Bukkit.getPlayer(name) != null) return Bukkit.getPlayer(name).getName();
            //todo : request name
            return "";
        });
    }

    public CompletableFuture<String> getServer(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return "";
            else if(name != null && Bukkit.getPlayer(name) != null) return "";
            //todo : request server
            return "";
        });
    }


}
