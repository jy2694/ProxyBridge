package io.github.jy2694.proxyBridge.event;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.jy2694.proxyBridge.ProxyBridge;
import io.github.jy2694.proxyBridge.entity.transfer.SerializableRunnable;

public class EventManager implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        List<SerializableRunnable> runnables = ProxyBridge.getAPI()
                .getQueuedRunnables(player.getUniqueId() + ":" + PlayerJoinEvent.class.getName());
        for(SerializableRunnable runnable : runnables){
            runnable.run();
        }
    }
}
