package com.easynull.luxium.init.blocks;

import com.easynull.luxium.client.utils.TooltipUtil;
import com.easynull.luxium.init.ModCreativeTab;
import com.easynull.luxium.init.ModInit;
import com.easynull.luxium.init.items.ItemScepter;
import com.easynull.luxium.init.tiles.TileFillingPrism;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.List;

public class BlockFillingPrism extends BaseEntityBlock {
    public BlockFillingPrism() {
        super(BlockBehaviour.Properties.of(Material.STONE).noOcclusion());
    }
    @Override
    public void fillItemCategory(CreativeModeTab pTab, NonNullList<ItemStack> items) {
        if(pTab == ModCreativeTab.tab) {
            for (PrismSkins skin : PrismSkins.values()) {
                ItemStack stack = new ItemStack(this);
                String name = "skin.luxium.prism_filling." + skin.name();
                setSkin(stack, skin, name);
                items.add(stack);
            }
        }
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileFillingPrism(pos, state);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> type) {
        return createTickerHelper(type, ModInit.prism.get(), TileFillingPrism::tick);
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player player, InteractionHand hand, BlockHitResult pHit) {
        TileFillingPrism prism = (TileFillingPrism) pLevel.getBlockEntity(pPos);
        ItemStack stack = player.getItemInHand(hand).copy();
        Container conPrism = prism.getInv();

        if(!stack.isEmpty() && conPrism.getItem(0).isEmpty()) {
            if (stack.getCount() > 1) {
                player.getItemInHand(hand).shrink(1);
                stack.setCount(1);
                conPrism.setItem(0, stack);
            } else {
                conPrism.setItem(0, stack);
                player.getInventory().removeItem(player.getItemInHand(hand));
                return InteractionResult.SUCCESS;
            }
        }
        if (!conPrism.getItem(0).isEmpty()) {
            player.getInventory().add(conPrism.getItem(0));
            conPrism.removeItemNoUpdate(0);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }


    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            TileFillingPrism prism = (TileFillingPrism) pLevel.getBlockEntity(pPos);
            ItemStack stack = prism.getInv().getItem(0);
            Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY() + 0.8, pPos.getZ(), stack);
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    public void setSkin(ItemStack stack, PrismSkins skin, String name) {
        if (!stack.hasTag()) stack.setTag(new CompoundTag());
        stack.getOrCreateTag().getString(skin.name());
        if(skin != PrismSkins.base) {
            stack.setHoverName(new TranslatableComponent(name));
        }
    }

    public enum PrismSkins {
        base,
        angel,
        halloween,
        christmas
    }
}