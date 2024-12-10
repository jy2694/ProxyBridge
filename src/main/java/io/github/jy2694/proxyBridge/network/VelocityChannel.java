package io.github.jy2694.proxyBridge.network;

import io.github.jy2694.proxyBridge.ProxyBridge;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class VelocityChannel implements PluginMessageListener {

    private final ProxyBridge proxyBridge;
    private final Map<UUID, String[]> responseResult = new ConcurrentHashMap<>();

    public VelocityChannel(ProxyBridge proxyBridge) {
        this.proxyBridge = proxyBridge;
        Bukkit.getMessenger().registerOutgoingPluginChannel(proxyBridge, "proxy_bridge");
        Bukkit.getMessenger().registerIncomingPluginChannel(proxyBridge, "proxy_bridge", this);
    }

    /**
     * 플레이어를 특정 서버에 연결합니다.
     *
     * @param player 연결할 플레이어
     * @param server 연결할 서버의 이름
     * @return 연결 작업을 나타내는 CompletableFuture
     */
    public CompletableFuture<Void> connect(Player player, String server) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);

                out.writeUTF("Connect");
                out.writeUTF(server);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * 다른 플레이어를 특정 서버에 연결합니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param name 연결할 플레이어의 이름
     * @param server 연결할 서버의 이름
     * @return 연결 작업을 나타내는 CompletableFuture
     */
    public CompletableFuture<Void> connectOther(Player player, String name, String server) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);

                out.writeUTF("ConnectOther");
                out.writeUTF(name);
                out.writeUTF(server);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * 특정 플레이어의 IP 주소를 가져옵니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param name IP 주소를 가져올 플레이어의 이름
     * @return IP 주소를 포함하는 CompletableFuture
     */
    public CompletableFuture<String[]> ip(Player player, String name) {
        return CompletableFuture.supplyAsync(() -> {
            UUID messageId = UUID.randomUUID();
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("IP");
                out.writeUTF(name);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
                responseResult.put(messageId, new String[] { "IP", name });
                while (responseResult.getOrDefault(messageId, new String[4]).length != 4)
                    Thread.onSpinWait();
                return Arrays.copyOfRange(responseResult.get(messageId), 2, 4);
            } catch (Exception ex) {
                return new String[2];
            } finally {
                responseResult.remove(messageId);
            }
        });
    }

    /**
     * 특정 서버의 플레이어 수를 가져옵니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param server 플레이어 수를 가져올 서버의 이름
     * @return 플레이어 수를 포함하는 CompletableFuture
     */
    public CompletableFuture<Integer> playerCount(Player player, String server) {
        return CompletableFuture.supplyAsync(() -> {
            UUID messageId = UUID.randomUUID();
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("PlayerCount");
                out.writeUTF(server);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
                responseResult.put(messageId, new String[] { "PlayerCount", server });
                while (responseResult.getOrDefault(messageId, new String[3]).length != 3)
                    Thread.onSpinWait();
                return Integer.parseInt(responseResult.get(messageId)[2]);
            } catch (Exception ex) {
                return -1;
            } finally {
                responseResult.remove(messageId);
            }
        });
    }

    /**
     * 특정 서버의 플레이어 목록을 가져옵니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param server 플레이어 목록을 가져올 서버의 이름
     * @return 플레이어 목록을 포함하는 CompletableFuture
     */
    public CompletableFuture<List<String>> playerList(Player player, String server) {
        return CompletableFuture.supplyAsync(() -> {
            UUID messageId = UUID.randomUUID();
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("PlayerList");
                out.writeUTF(server);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
                responseResult.put(messageId, new String[] { "PlayerList", server });
                while (responseResult.getOrDefault(messageId, new String[3]).length != 3)
                    Thread.onSpinWait();
                return Arrays.asList(responseResult.get(messageId)[2].split(","));
            } catch (Exception ex) {
                return List.of();
            } finally {
                responseResult.remove(messageId);
            }
        });
    }

    /**
     * 서버 목록을 가져옵니다.
     *
     * @param player 요청을 보낸 플레이어
     * @return 서버 목록을 포함하는 CompletableFuture
     */
    public CompletableFuture<List<String>> getServers(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            UUID messageId = UUID.randomUUID();
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("GetServers");
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
                responseResult.put(messageId, new String[] { "GetServers" });
                while (responseResult.getOrDefault(messageId, new String[2]).length != 2)
                    Thread.onSpinWait();
                return Arrays.asList(responseResult.get(messageId)[1].split(","));
            } catch (Exception ex) {
                return List.of();
            } finally {
                responseResult.remove(messageId);
            }
        });
    }

    /**
     * 특정 플레이어에게 메시지를 보냅니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param name 메시지를 받을 플레이어의 이름
     * @param message 보낼 메시지
     * @return 메시지 전송 작업을 나타내는 CompletableFuture
     */
    public CompletableFuture<Void> message(Player player, String name, String message) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("Message");
                out.writeUTF(name);
                out.writeUTF(message);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * 특정 플레이어에게 JSON 형식의 메시지를 보냅니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param name 메시지를 받을 플레이어의 이름
     * @param json 보낼 JSON 형식의 메시지
     * @return 메시지 전송 작업을 나타내는 CompletableFuture
     */
    public CompletableFuture<Void> messageRaw(Player player, String name, String json) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("MessageRaw");
                out.writeUTF(name);
                out.writeUTF(json);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * 플레이어가 현재 접속해 있는 서버의 이름을 가져옵니다.
     *
     * @param player 요청을 보낸 플레이어
     * @return 서버 이름을 포함하는 CompletableFuture
     */
    public CompletableFuture<String> getServer(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            UUID messageId = UUID.randomUUID();
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("GetServer");
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
                responseResult.put(messageId, new String[] { "GetServer", player.getName() });
                while (responseResult.getOrDefault(messageId, new String[3]).length != 3)
                    Thread.onSpinWait();
                return responseResult.get(messageId)[2];
            } catch (Exception ex) {
                return null;
            } finally {
                responseResult.remove(messageId);
            }
        });
    }

    /**
     * 플레이어의 UUID를 가져옵니다.
     *
     * @param player 요청을 보낸 플레이어
     * @return UUID를 포함하는 CompletableFuture
     */
    public CompletableFuture<UUID> uuid(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            UUID messageId = UUID.randomUUID();
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("UUID");
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
                responseResult.put(messageId, new String[] { "UUID", player.getName() });
                while (responseResult.getOrDefault(messageId, new String[3]).length != 3)
                    Thread.onSpinWait();
                return UUID.fromString(responseResult.get(messageId)[2]);
            } catch (Exception ex) {
                return null;
            } finally {
                responseResult.remove(messageId);
            }
        });
    }

    /**
     * 다른 플레이어의 UUID를 가져옵니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param name UUID를 가져올 플레이어의 이름
     * @return UUID를 포함하는 CompletableFuture
     */
    public CompletableFuture<UUID> uuidOther(Player player, String name) {
        return CompletableFuture.supplyAsync(() -> {
            UUID messageId = UUID.randomUUID();
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("UUIDOther");
                out.writeUTF(name);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
                responseResult.put(messageId, new String[] { "UUIDOther", name });
                while (responseResult.getOrDefault(messageId, new String[3]).length != 3)
                    Thread.onSpinWait();
                return UUID.fromString(responseResult.get(messageId)[2]);
            } catch (Exception ex) {
                return null;
            } finally {
                responseResult.remove(messageId);
            }
        });

    }

    /**
     * 특정 플레이어의 서버 IP 주소를 가져옵니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param name 서버 IP 주소를 가져올 플레이어의 이름
     * @return 서버 IP 주소를 포함하는 CompletableFuture
     */
    public CompletableFuture<String[]> serverIp(Player player, String name) {
        return CompletableFuture.supplyAsync(() -> {
            UUID messageId = UUID.randomUUID();
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("ServerIP");
                out.writeUTF(name);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
                responseResult.put(messageId, new String[] { "ServerIP", name });
                while (responseResult.getOrDefault(messageId, new String[4]).length != 4)
                    Thread.onSpinWait();
                return Arrays.copyOfRange(responseResult.get(messageId), 2, 4);
            } catch (Exception ex) {
                return new String[2];
            } finally {
                responseResult.remove(messageId);
            }
        });

    }

    /**
     * 특정 플레이어를 킥합니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param name 킥할 플레이어의 이름
     * @param reason 킥 사유
     * @return 킥 작업을 나타내는 CompletableFuture
     */
    public CompletableFuture<Void> kickPlayer(Player player, String name, String reason) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("KickPlayer");
                out.writeUTF(name);
                out.writeUTF(reason);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * 특정 플레이어를 JSON 형식의 사유로 킥합니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param name 킥할 플레이어의 이름
     * @param reasonJson JSON 형식의 킥 사유
     * @return 킥 작업을 나타내는 CompletableFuture
     */
    public CompletableFuture<Void> kickPlayerRaw(Player player, String name, String reasonJson) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("KickPlayerRaw");
                out.writeUTF(name);
                out.writeUTF(reasonJson);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * 특정 서버의 하위 채널로 메시지를 전달합니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param server 메시지를 전달할 서버의 이름
     * @param subChannel 하위 채널의 이름
     * @param message 전달할 메시지
     * @return 메시지 전달 작업을 나타내는 CompletableFuture
     */
    public CompletableFuture<Void> forward(Player player, String server, String subChannel, String message) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("Forward");
                out.writeUTF(server);
                out.writeUTF(subChannel);
                out.writeUTF(message);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * 특정 플레이어에게 특정 서버의 하위 채널로 메시지를 전달합니다.
     *
     * @param player 요청을 보낸 플레이어
     * @param name 메시지를 받을 플레이어의 이름
     * @param server 메시지를 전달할 서버의 이름
     * @param subChannel 하위 채널의 이름
     * @param message 전달할 메시지
     * @return 메시지 전달 작업을 나타내는 CompletableFuture
     */
    public CompletableFuture<Void> forwardToPlayer(Player player, String name, String server, String subChannel,
            String message) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("ForwardToPlayer");
                out.writeUTF(name);
                out.writeUTF(server);
                out.writeUTF(subChannel);
                out.writeUTF(message);
                player.sendPluginMessage(proxyBridge, "BungeeCord", b.toByteArray());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        try {
            if (!channel.equals("BungeeCord"))
                return;
            ByteArrayInputStream b = new ByteArrayInputStream(message);
            DataInputStream in = new DataInputStream(b);
            String operator = in.readUTF();
            if (operator.equalsIgnoreCase("ip")) {
                String server = in.readUTF();
                List<UUID> responseTarget = responseResult.entrySet()
                        .stream().filter(entry -> {
                            String[] value = entry.getValue();
                            return value.length == 3 && value[0].equalsIgnoreCase("ip") && value[1].equals(server);
                        })
                        .map(Map.Entry::getKey)
                        .toList();
                if (responseTarget.isEmpty())
                    return;
                String[] result = new String[] {
                        "ip",
                        server,
                        in.readUTF(),
                        in.readUTF()
                };
                for (UUID uuid : responseTarget)
                    responseResult.put(uuid, result);
            } else if (operator.equalsIgnoreCase("playerCount")) {
                String server = in.readUTF();
                List<UUID> responseTarget = responseResult.entrySet()
                        .stream().filter(entry -> {
                            String[] value = entry.getValue();
                            return value.length == 2 && value[0].equalsIgnoreCase("playerCount")
                                    && value[1].equals(server);
                        })
                        .map(Map.Entry::getKey)
                        .toList();
                if (responseTarget.isEmpty())
                    return;
                String[] result = new String[] {
                        "playerCount",
                        server,
                        in.readUTF()
                };
                for (UUID uuid : responseTarget)
                    responseResult.put(uuid, result);
            } else if (operator.equalsIgnoreCase("playerList")) {
                String server = in.readUTF();
                List<UUID> responseTarget = responseResult.entrySet()
                        .stream().filter(entry -> {
                            String[] value = entry.getValue();
                            return value.length == 2 && value[0].equalsIgnoreCase("playerList")
                                    && value[1].equals(server);
                        })
                        .map(Map.Entry::getKey)
                        .toList();
                if (responseTarget.isEmpty())
                    return;
                String[] result = new String[] {
                        "playerList",
                        server,
                        in.readUTF()
                };
                for (UUID uuid : responseTarget)
                    responseResult.put(uuid, result);
            } else if (operator.equalsIgnoreCase("getServers")) {
                List<UUID> responseTarget = responseResult.entrySet()
                        .stream().filter(entry -> entry.getValue().length == 1 && entry.getValue()[0].equalsIgnoreCase("getServers"))
                        .map(Map.Entry::getKey)
                        .toList();
                if (responseTarget.isEmpty())
                    return;
                String[] result = new String[] {
                        "getServers",
                        in.readUTF()
                };
                for (UUID uuid : responseTarget)
                    responseResult.put(uuid, result);
            } else if (operator.equalsIgnoreCase("getServer")) {
                List<UUID> responseTarget = responseResult.entrySet()
                        .stream().filter(entry -> entry.getValue().length == 2 && entry.getValue()[0].equalsIgnoreCase("getServer") && entry.getValue()[1].equals(player.getName()))
                        .map(Map.Entry::getKey)
                        .toList();
                if (responseTarget.isEmpty())
                    return;
                String[] result = new String[] {
                        "getServer",
                        player.getName(),
                        in.readUTF()
                };
                for (UUID uuid : responseTarget)
                    responseResult.put(uuid, result);
            } else if (operator.equalsIgnoreCase("uuid")) {
                List<UUID> responseTarget = responseResult.entrySet()
                        .stream().filter(entry -> entry.getValue().length == 2 && entry.getValue()[0].equalsIgnoreCase("uuid") && entry.getValue()[1].equals(player.getName()))
                        .map(Map.Entry::getKey)
                        .toList();
                if (responseTarget.isEmpty())
                    return;
                String[] result = new String[] {
                        "uuid",
                        player.getName(),
                        in.readUTF()
                };
                for (UUID uuid : responseTarget)
                    responseResult.put(uuid, result);
            } else if (operator.equalsIgnoreCase("uuidOther")) {
                String name = in.readUTF();
                List<UUID> responseTarget = responseResult.entrySet()
                        .stream().filter(entry -> entry.getValue().length == 2 && entry.getValue()[0].equalsIgnoreCase("uuidOther") && entry.getValue()[1].equals(name))
                        .map(Map.Entry::getKey)
                        .toList();
                if (responseTarget.isEmpty())
                    return;
                String[] result = new String[] {
                        "uuidOther",
                        name,
                        in.readUTF()
                };
                for (UUID uuid : responseTarget)
                    responseResult.put(uuid, result);
            } else if (operator.equalsIgnoreCase("serverIp")) {
                String name = in.readUTF();
                List<UUID> responseTarget = responseResult.entrySet()
                        .stream().filter(entry -> entry.getValue().length == 2 && entry.getValue()[0].equalsIgnoreCase("serverIp") && entry.getValue()[1].equals(name))
                        .map(Map.Entry::getKey)
                        .toList();
                if (responseTarget.isEmpty())
                    return;
                String[] result = new String[] {
                        "serverIp",
                        name,
                        in.readUTF(),
                        in.readUTF()
                };
                for (UUID uuid : responseTarget)
                    responseResult.put(uuid, result);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
