package io.bluebeaker.justextradrags.network;

import io.bluebeaker.justextradrags.JustExtraDrags;
import io.bluebeaker.justextradrags.config.JXDConfigManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

// Taken from JustEnoughDrags with Apache2.0 license
public class PacketSetContainerSlot implements IMessage {
    public PacketSetContainerSlot() {
    }

    public PacketSetContainerSlot(int containerSlot, ItemStack stack) {
        this.containerSlot = containerSlot;
        this.stack = stack;
    }

    private int containerSlot;
    private ItemStack stack;

    @Override
    public void fromBytes(ByteBuf buf) {
        containerSlot = buf.readInt();
        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(containerSlot);
        ByteBufUtils.writeItemStack(buf, stack);
    }

    public static class Handler implements IMessageHandler<PacketSetContainerSlot, IMessage> {

        @Override
        public IMessage onMessage(PacketSetContainerSlot message, MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServerWorld().addScheduledTask(() -> {
                if (message.stack.isEmpty() || message.stack.getCount() > message.stack.getMaxStackSize()) {
                    return;
                }
                if (player.openContainer != null) {
                    if (message.containerSlot >= 0 && message.containerSlot < player.openContainer.inventorySlots.size()) {
                        Slot slot = player.openContainer.getSlot(message.containerSlot);

                        //Modified: Check whether the slot is allowed to put
                        if(JXDConfigManager.isAllowedToPut(player.openContainer,slot)){
                            slot.putStack(message.stack);
                            slot.onSlotChanged();
                        }else {
                            JustExtraDrags.getLogger().info("Rejected a player's attempt to drag item {} to {} {}.",message.stack,player.openContainer,slot);
                        }
                    }
                }
            });

            return null;
        }
    }
}
