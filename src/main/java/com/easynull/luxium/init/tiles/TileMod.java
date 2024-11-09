package com.easynull.luxium.init.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileMod extends BlockEntity {
    public TileMod(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        writeNBT(tag);
    }

    @Nonnull
    @Override
    public final CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        writeNBT(tag);
        return tag;
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);
        readNBT(tag);
    }

    public void writeNBT(CompoundTag cmp) {}

    public void readNBT(CompoundTag cmp) {}

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
