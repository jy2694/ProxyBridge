package io.github.jy2694.proxyBridge.entity.valina;


import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
            //todo : request change health scale
            return true;
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
            //todo : request change health
            return true;
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
            //todo : request change fly speed
            return true;
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
            //todo : request change walk speed
            return true;
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
            //todo : request change game mode
            return true;
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
            //todo : request change allow flight
            return true;
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
            //todo : request changeexp
            return true;
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

    public CompletableFuture<Boolean> getTotalExperience(int exp){
        return CompletableFuture.supplyAsync(() -> {
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).setTotalExperience(exp);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).setTotalExperience(exp);
                return true;
            }
            //todo : request change total exp
            return true;
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
            //todo : request change level
            return true;
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
            //todo : request change food level
            return true;
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

    public CompletableFuture<OnlineWorld> getWorld(){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) return OnlineWorld.adapt(Bukkit.getPlayer(uniqueId).getWorld());
            else if(name != null && Bukkit.getPlayer(name) != null) return OnlineWorld.adapt(Bukkit.getPlayer(name).getWorld());
            //todo : request world
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

    public CompletableFuture<Boolean> sendMessage(String message){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).sendMessage(message);
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).sendMessage(message);
                return true;
            }
            //todo : request send message
            return true;
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
            //todo : request send title
            return true;
        });
    }

    public CompletableFuture<Boolean> sendActionBar(String message){
        return CompletableFuture.supplyAsync(()->{
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null){
                Bukkit.getPlayer(uniqueId).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                return true;
            }
            else if(name != null && Bukkit.getPlayer(name) != null){
                Bukkit.getPlayer(name).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                return true;
            }
            //todo : request send message
            return true;
        });
    }

    public CompletableFuture<Boolean> teleport(OnlineLocation location){
        return CompletableFuture.supplyAsync(()->{
            Player player = null;
            if(uniqueId != null && Bukkit.getPlayer(uniqueId) != null) player = Bukkit.getPlayer(uniqueId);
            else if(name != null && Bukkit.getPlayer(name) != null) player = Bukkit.getPlayer(name);
            if(player == null){
                //request teleport to other server
            } else if(player != null){
                //try to teleport
            }
            return true;
        });
    }
}
