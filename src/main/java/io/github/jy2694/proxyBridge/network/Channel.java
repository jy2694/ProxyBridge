package io.github.jy2694.proxyBridge.network;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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

public abstract class Channel {

    public Map<String, List<SerializableRunnable>> queuedRunnable = new ConcurrentHashMap<>();
    private Map<UUID, Integer> responseCounter = new ConcurrentHashMap<>();
    private Map<UUID, Object> responseResult = new ConcurrentHashMap<>();
    private Map<UUID, BukkitRunnable> requestTimeOutTasks = new ConcurrentHashMap<>();

    public boolean isResponse(UUID messageId){
        return responseCounter.containsKey(messageId);
    }

    public abstract void open();
    public abstract void close();
    public abstract boolean isOpen();
    public void receiveHandle(Message message){
        if(!message.getTo().equals("") && !message.getTo().equals("ALL")) return;
        switch (message.getType()) {
            case NO_REPLY -> {
                if(responseCounter.containsKey(message.getMessageId())) responseCounter.put(message.getMessageId(), responseCounter.getOrDefault(message.getMessageId(), 0)-1);
            }
            case PROCESS_ENQUEUE -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    RunnableData runnableData = (RunnableData) message.getBody()[0];
                    queuedRunnable.computeIfAbsent(runnableData.getKey(), (key) -> new CopyOnWriteArrayList<>());
                    queuedRunnable.get(runnableData.getKey()).add(runnableData.getRunnable());
                }
            }
            case PLAYER_GET_HEALTH_SCALE -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getHealthScale().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_HEALTH_SCALE, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_SET_HEALTH_SCALE -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    double healthScale = (double) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.setHealthScale(healthScale).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_SET_HEALTH_SCALE, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_HEALTH -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getHealth().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_HEALTH, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_SET_HEALTH -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    double health = (double) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.setHealth(health).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_SET_HEALTH, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_FLY_SPEED -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getFlySpeed().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_FLY_SPEED, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_SET_FLY_SPEED -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    float flySpeed = (float) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.setFlySpeed(flySpeed).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_SET_FLY_SPEED, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_WALK_SPEED -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getWalkSpeed().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_WALK_SPEED, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_SET_WALK_SPEED -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    float walkSpeed = (float) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.setWalkSpeed(walkSpeed).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_SET_WALK_SPEED, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_NAME -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getName().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_NAME, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_UUID -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getUniqueId().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_UUID, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_GAMEMODE -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getGameMode().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_GAMEMODE, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_SET_GAMEMODE -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    GameMode gameMode = (GameMode) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.setGameMode(gameMode).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_SET_GAMEMODE, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_ALLOW_FLIGHT -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getAllowFlight().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_ALLOW_FLIGHT, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_SET_ALLOW_FLIGHT -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    boolean allowFlight = (boolean) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.setAllowFlight(allowFlight).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_SET_ALLOW_FLIGHT, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_OP -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.isOp().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_OP, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_SET_OP -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    boolean op = (boolean) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.setOp(op).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_SET_OP, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_LOCATION -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getLocation().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_LOCATION, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_BED_SPAWN_LOCATION -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getBedSpawnLocation().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_BED_SPAWN_LOCATION, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_COMPASS_TARGET -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getCompassTarget().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_COMPASS_TARGET, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_EXP -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getExp().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_EXP, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_SET_EXP -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    float exp = (float) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.setExp(exp).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_SET_EXP, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_TOTAL_EXP -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getTotalExperience().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_TOTAL_EXP, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_SET_TOTAL_EXP -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    int totalExp = (int) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.setTotalExperience(totalExp).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_SET_TOTAL_EXP, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_LEVEL -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getLevel().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_LEVEL, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_SET_LEVEL -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    int level = (int) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.setLevel(level).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_SET_LEVEL, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_WORLD -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getWorld().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_WORLD, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_FOOD_LEVEL -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getFoodLevel().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_FOOD_LEVEL, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_SET_FOOD_LEVEL -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    int foodLevel = (int) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.setFoodLevel(foodLevel).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_SET_FOOD_LEVEL, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case PLAYER_GET_SERVER -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.getServer().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.PLAYER_GET_SERVER, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SEND_MESSAGE -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    String line = (String) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.sendMessage(line).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SEND_MESSAGE, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SEND_TITLE -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    String title = (String) message.getBody()[1];
                    String subtitle = (String) message.getBody()[2];
                    int fadeIn = (int) message.getBody()[3];
                    int stay = (int) message.getBody()[4];
                    int fadeOut = (int) message.getBody()[5];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.sendTitle(title, subtitle, fadeIn, stay, fadeOut).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SEND_TITLE, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SEND_ACTIONBAR -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    String line = (String) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.sendActionBar(line).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SEND_ACTIONBAR, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case TELEPORT -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlinePlayer onlinePlayer = (OnlinePlayer) message.getBody()[0];
                    OnlineLocation location = (OnlineLocation) message.getBody()[1];
                    if(onlinePlayer.isInServer()){
                        onlinePlayer.teleport(location).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.TELEPORT, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_GET_BIOME -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    OnlineLocation location = (OnlineLocation) message.getBody()[1];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName()) && location.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.getBiome(location).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_GET_BIOME, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_SET_BIOME -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    OnlineLocation location = (OnlineLocation) message.getBody()[1];
                    Biome biome = (Biome) message.getBody()[2];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName()) && location.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.setBiome(location, biome).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_SET_BIOME, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_GET_TIME -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.getTime().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_GET_TIME, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_SET_TIME -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    long time = (long) message.getBody()[1];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.setTime(time).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_SET_TIME, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_GET_SPAWN_LOCATION -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.getSpawnLocation().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_GET_SPAWN_LOCATION, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_SET_SPAWN_LOCATION -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    OnlineLocation location = (OnlineLocation) message.getBody()[1];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName()) && location.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.setSpawnLocation(location).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_SET_SPAWN_LOCATION, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_GET_ALLOW_MONSTERS -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.getAllowMonsters().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_GET_ALLOW_MONSTERS, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_GET_ALLOW_ANIMALS -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.getAllowAnimals().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_GET_ALLOW_ANIMALS, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_GET_DIFFICULTY -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.getDifficulty().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_GET_DIFFICULTY, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_SET_DIFFICULTY -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    Difficulty difficulty = (Difficulty) message.getBody()[1];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.setDifficulty(difficulty).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_SET_DIFFICULTY, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_GET_ENVIRONMENT -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.getEnvironment().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_GET_ENVIRONMENT, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_DROP_ITEM -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    OnlineLocation location = (OnlineLocation) message.getBody()[1];
                    ItemStack itemStack = (ItemStack) message.getBody()[2];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName()) && location.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.dropItem(location, itemStack).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_DROP_ITEM, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_DROP_ITEM_NATURALLY -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    OnlineLocation location = (OnlineLocation) message.getBody()[1];
                    ItemStack itemStack = (ItemStack) message.getBody()[2];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName()) && location.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.dropItemNaturally(location, itemStack).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_DROP_ITEM_NATURALLY, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case WORLD_SPAWN_ENTITY -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineWorld onlineWorld = (OnlineWorld) message.getBody()[0];
                    OnlineLocation location = (OnlineLocation) message.getBody()[1];
                    EntityType entityType = (EntityType) message.getBody()[2];
                    if(onlineWorld.getServer().equals(GlobalConfig.getServerName()) && location.getServer().equals(GlobalConfig.getServerName())){
                        onlineWorld.spawnEntity(location, entityType).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.WORLD_SPAWN_ENTITY, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_BROADCAST_MESSAGE -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    String msg = (String) message.getBody()[1];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        Bukkit.getServer().broadcastMessage(msg);
                        send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_BROADCAST_MESSAGE, message.getFrom(), true));
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_GET_ONLINE_PLAYERS -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.getOnlinePlayers().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_GET_ONLINE_PLAYERS, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_GET_ONLINE_PLAYERS_COUNT -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.getOnlinePlayersCount().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_GET_ONLINE_PLAYERS_COUNT, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_GET_ALLOW_END -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.getAllowEnd().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_GET_ALLOW_END, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_GET_ALLOW_NETHER -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.getAllowNether().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_GET_ALLOW_NETHER, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_GET_ALLOW_FLIGHT -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.getAllowFlight().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_GET_ALLOW_FLIGHT, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_GET_DEFAULT_GAMEMODE -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.getDefaultGameMode().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_GET_DEFAULT_GAMEMODE, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_SET_DEFAULT_GAMEMODE -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    GameMode gameMode = (GameMode) message.getBody()[1];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.setDefaultGameMode(gameMode).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_SET_DEFAULT_GAMEMODE, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_BAN_IP -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    String ip = (String) message.getBody()[1];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.banIp(ip).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_BAN_IP, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_GET_BANNED_PLAYERS -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.getBannedPlayers().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_GET_BANNED_PLAYERS, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_GET_MAX_PLAYERS -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.getMaxPlayers().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_GET_MAX_PLAYERS, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_SET_MAX_PLAYERS -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    int maxPlayers = (int) message.getBody()[1];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.setMaxPlayers(maxPlayers).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_SET_MAX_PLAYERS, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_GET_WORLDS -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.getWorlds().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_GET_WORLDS, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_SET_MOTD -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    String motd = (String) message.getBody()[1];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.setMOTD(motd).thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_SET_MOTD, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_GET_MOTD -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.getMOTD().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_GET_MOTD, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
            case SERVER_GET_BUKKIT_VERSION -> {
                if(isResponse(message.getMessageId())){
                    //Response
                    responseResult.put(message.getMessageId(), message.getBody()[0]);
                } else {
                    //Request
                    OnlineServer onlineServer = (OnlineServer) message.getBody()[0];
                    if(onlineServer.getServer().equals(GlobalConfig.getServerName())){
                        onlineServer.getBukkitVersion().thenAccept((result) -> {
                            send(Message.ofResponse(message.getMessageId(), MessageType.SERVER_GET_BUKKIT_VERSION, message.getFrom(), result));
                        });
                    } else {
                        send(Message.ofResponse(message.getMessageId(), MessageType.NO_REPLY, message.getFrom(), null));
                    }
                }
            }
        }
    }
    public abstract void send(Message message);

    public void waitReply(Message message) {
        responseCounter.put(message.getMessageId(), 5);
    }

    public boolean canReceiveReply(Message message) {
        return isReplyExpired(message) || getReply(message) != null;
    }

    public Object getReply(Message message) {
        return responseResult.get(message.getMessageId());
    }

    public boolean isReplyExpired(Message message) {
        return responseCounter.getOrDefault(message.getMessageId(), 0) == 0;
    }

    public void startTimeOutTask(Message message){
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if(responseCounter.containsKey(message.getMessageId()))
                    responseCounter.remove(message.getMessageId());
            }
        };
        runnable.runTaskLater(ProxyBridge.getInstance(), 100L);
        requestTimeOutTasks.put(message.getMessageId(), runnable);
    }

    public List<SerializableRunnable> getQueuedRunnable(String key){
        return queuedRunnable.getOrDefault(key, new CopyOnWriteArrayList<>());
    }
}
