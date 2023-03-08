package com.github.naterepos.specialmixture;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.CustomServerBossInfoManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.List;
import java.util.UUID;

public class ServerUtilities {

    public static List<ServerPlayerEntity> getPlayers() {
        return ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();
    }

    public static ServerPlayerEntity getPlayer(UUID player) {
        return ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUUID(player);
    }

    public static void runCommandToTarget(String command, String playerName) {
        ServerLifecycleHooks.getCurrentServer().getCommandManager().handleCommand(ServerLifecycleHooks.getCurrentServer().getCommandSource(), command.replace("%player%", playerName));
    }

    public static CustomServerBossInfoManager getBossBars() {
        return ServerLifecycleHooks.getCurrentServer().getCustomBossEvents();
    }

    public static ServerPlayerEntity getPlayer(String username) {
        return ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUsername(username);
    }

    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public static ServerWorld getDefaultWorld() {
        return getServer().getWorlds().iterator().next();
    }

    public static String getWorldName(ServerWorld world) {
        return ((IServerWorldInfo) world.getWorldInfo()).getWorldName();
    }

    public static ServerWorld getWorld(String world) {
        for(ServerWorld otherWorld : getServer().getWorlds()) {
            if(((IServerWorldInfo) otherWorld.getWorldInfo()).getWorldName().equals(world)) {
                return otherWorld;
            }
        }
        return null;
    }
}
