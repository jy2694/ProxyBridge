package io.github.jy2694.proxyBridge.entity;

import org.bukkit.Server;

public class ServerData {

    public static ServerData adapt(Server server){
        return new ServerData(server.getOnlinePlayers().size(), server.getMaxPlayers(), server.getOnlinePlayers().stream().map(p -> p.getName()).toArray(String[]::new));
    }

    private int onlinePlayers;
    private int maxPlayers;
    private String[] playerList;
    private long tps;

    public ServerData(int onlinePlayers, int maxPlayers, String[] playerList) {
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
        this.playerList = playerList;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String[] getPlayerList() {
        return playerList;
    }
}
