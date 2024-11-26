package io.github.jy2694.proxyBridge.api;

import io.github.jy2694.proxyBridge.entity.Message;
import io.github.jy2694.proxyBridge.entity.ServerData;
import io.github.jy2694.proxyBridge.entity.UserData;
import io.github.jy2694.proxyBridge.network.Channel;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Request {

    private Channel channel;

    public Request(Channel channel) {
        this.channel = channel;
    }

    public CompletableFuture<UserData> getUserData(String username) {
        return CompletableFuture.supplyAsync(()->{
            Message<String> request = Message.ofUserDataRequest(username);
            channel.send(request);
            channel.waitReply(request);
            while(!channel.isReplyExpired(request) && channel.getReply(request) == null) Thread.onSpinWait();
            return (UserData) channel.getReply(request);
        });
    }

    public CompletableFuture<UserData> getUserData(UUID uniqueId) {
        return CompletableFuture.supplyAsync(()->{
            Message<UUID> request = Message.ofUserDataRequest(uniqueId);
            channel.send(request);
            channel.waitReply(request);
            while(!channel.isReplyExpired(request) && channel.getReply(request) == null) Thread.onSpinWait();
            return (UserData) channel.getReply(request);
        });
    }

    public CompletableFuture<ServerData> getServerData(String serverName) {
        return CompletableFuture.supplyAsync(()->{
            Message<Void> request = Message.ofServerDataRequest(serverName);
            channel.send(request);
            channel.waitReply(request);
            while(!channel.isReplyExpired(request) && channel.getReply(request) == null) Thread.onSpinWait();
            return (ServerData) channel.getReply(request);
        });
    }
}
