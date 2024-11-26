package io.github.jy2694.proxyBridge.network;

import io.github.jy2694.proxyBridge.ProxyBridge;
import io.github.jy2694.proxyBridge.entity.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class PluginMessageChannel extends Channel implements PluginMessageListener {

    private ProxyBridge proxyBridge;

    public PluginMessageChannel(ProxyBridge proxyBridge) {
        this.proxyBridge = proxyBridge;
    }

    @Override
    public void open() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(proxyBridge, "proxy_bridge");
        Bukkit.getMessenger().registerIncomingPluginChannel(proxyBridge, "proxy_bridge", this);
    }

    @Override
    public void close() {
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(proxyBridge);
        Bukkit.getMessenger().unregisterIncomingPluginChannel(proxyBridge, "proxy_bridge");
    }

    @Override
    public boolean isOpen() {
        return Bukkit.getMessenger().isIncomingChannelRegistered(proxyBridge, "proxy_bridge")
                && Bukkit.getMessenger().isOutgoingChannelRegistered(proxyBridge, "proxy_bridge");
    }

    @Override
    public void send(Message message) {
        try{
            String line = message.toString();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            out.writeUTF("Forward");
            out.writeUTF("ALL");
            out.writeUTF("proxy_bridge");
            byte[] data = line.getBytes(StandardCharsets.UTF_8);
            out.writeShort(data.length);
            out.write(data);

            Player p = Bukkit.getOnlinePlayers().stream().findAny().get();
            if(p == null) return;
            p.sendPluginMessage(proxyBridge, "proxy_bridge", b.toByteArray());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if(!channel.equals("proxy_bridge")) return;
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
            String subchannel = in.readUTF();
            if(!subchannel.equals("proxy_bridge")) return;
            short len = in.readShort();
            byte[] msg = new byte[len];
            in.readFully(msg);
            Message message = Message.parse(new String(msg, StandardCharsets.UTF_8));
            receiveHandle(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
