package com.easynull.luxium.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.TickEvent;

public class ClientUtil {
    public static int ticksInGame = 0;
    public static float partialTicks = 0;

    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!Minecraft.getInstance().isPaused()) {
                ticksInGame++;
                partialTicks = 0;
            }
        }
    }
    public static void updateTilePacket(BlockEntity tile) {
        if (tile.getLevel() instanceof ServerLevel) {
            Packet<?> packet = tile.getUpdatePacket();
            if (packet != null) {
                BlockPos pos = tile.getBlockPos();
                ((ServerChunkCache) tile.getLevel().getChunkSource()).chunkMap.getPlayers(new ChunkPos(pos), false).forEach(e -> e.connection.send(packet));
            }
        }
    }
}
