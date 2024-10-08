package io.bluebeaker.justextradrags.compat;

import com.warmthdawn.justenoughdrags.jei.GenericGhostHandler;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class AltGhostHandler<T extends GuiContainer> extends GenericGhostHandler<T> {

    public <I extends Slot> AltGhostHandler(Class<I> applySlot) {
        super(applySlot);
    }

    @SuppressWarnings(value = { "unchecked" })
    @Override
    public boolean isSlotValid(Slot slot, ItemStack stack, boolean doStart) {
        return this.applySlot.isAssignableFrom(slot.getClass());
    }
}
