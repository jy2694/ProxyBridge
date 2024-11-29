package io.github.jy2694.proxyBridge.network;

import io.github.jy2694.proxyBridge.entity.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Channel {

    public Map<String, List<SerializableRunnable>> queuedRunnable = new ConcurrentHashMap<>();
    private Map<UUID, Integer> responseCounter = new ConcurrentHashMap<>();
    private Map<UUID, Object> responseResult = new ConcurrentHashMap<>();

    public abstract void open();
    public abstract void close();
    public abstract boolean isOpen();
    public void receiveHandle(Message message){
        if(!message.getTo().equals("") && !message.getTo().equals("ALL")) return;
        switch (message.getType()) {
            case NO_REPLY -> {
                if(responseCounter.containsKey(message.getMessageId())) responseCounter.put(message.getMessageId(), responseCounter.getOrDefault(message.getMessageId(), 0)-1);
            }
            case USERDATA_REQUEST_BY_NAME -> {
                String name = (String) message.getBody();
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
                if(offlinePlayer.hasPlayedBefore()){
                    send(Message.ofUserDataResponse(message.getMessageId(), message.getFrom(), UserData.adapt(offlinePlayer.getPlayer())));
                } else {
                    send(Message.ofNoReply(message.getMessageId(), message.getFrom()));
                }
            }
            case USERDATA_REQUEST_BY_UUID -> {
                UUID uniqueId = (UUID) message.getBody();
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);
                if(offlinePlayer.hasPlayedBefore()){
                    send(Message.ofUserDataResponse(message.getMessageId(), message.getFrom(), UserData.adapt(offlinePlayer.getPlayer())));
                } else {
                    send(Message.ofNoReply(message.getMessageId(), message.getFrom()));
                }
            }
            case USERDATA_RESPONSE, SERVER_STATUS_RESPONSE -> {
                responseResult.put(message.getMessageId(), message.getBody());
                responseCounter.remove(message.getMessageId());
            }
            case SERVER_STATUS_REQUEST -> send(Message.ofServerDataResponse(message.getMessageId(), message.getFrom(), ServerData.adapt(Bukkit.getServer())));
            case SEND_MESSAGE -> {
                String[] msg = ((String) message.getBody()).split("===");
                if(!Bukkit.getOfflinePlayer(msg[0]).isOnline()) return;
                Bukkit.getPlayer(msg[0]).sendMessage(msg[1]);
            }
            case SEND_ACTIONBAR -> {
                String[] msg = ((String) message.getBody()).split("===");
                if(!Bukkit.getOfflinePlayer(msg[0]).isOnline()) return;
                Bukkit.getPlayer(msg[0]).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg[1]));
            }
            case SEND_TITLE -> {
                String[] msg = ((String) message.getBody()).split("===");
                if(!Bukkit.getOfflinePlayer(msg[0]).isOnline()) return;
                Bukkit.getPlayer(msg[0]).sendTitle(msg[1], msg[2], Integer.parseInt(msg[3]), Integer.parseInt(msg[4]), Integer.parseInt(msg[5]));
            }
            case QUEUE_RUNNABLE -> {
                RunnableData data = (RunnableData) message.getBody();
                if(!queuedRunnable.containsKey(data.getKey())) queuedRunnable.put(data.getKey(), new CopyOnWriteArrayList<>());
                queuedRunnable.get(data.getKey()).add(data.getRunnable());
            }
        }
    }
    public abstract void send(Message message);

    public void waitReply(Message message) {
        responseCounter.put(message.getMessageId(), 5);
    }

    public Object getReply(Message message) {
        return responseResult.get(message.getMessageId());
    }

    public boolean isReplyExpired(Message message) {
        return responseCounter.get(message.getMessageId()) == 0;
    }
}
