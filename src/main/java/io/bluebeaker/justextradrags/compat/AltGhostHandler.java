package io.bluebeaker.justextradrags.compat;

import com.warmthdawn.justenoughdrags.jei.GenericGhostHandler;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AltGhostHandler<T extends GuiContainer> extends GenericGhostHandler<T> {
    protected boolean checkItemValidForStack = true;
    protected Set<Integer> slotIDs = new HashSet<>();

    public <I extends Slot> AltGhostHandler(Class<I> applySlot) {
        super(applySlot);
    }

    public <I extends Slot> AltGhostHandler(Class<I> applySlot,boolean checkItemValidForStack) {
        super(applySlot);
        this.checkItemValidForStack=checkItemValidForStack;
    }

    public <I extends Slot> AltGhostHandler(Class<I> applySlot, boolean checkItemValidForStack, Collection<Integer> slotIDs) {
        super(applySlot);
        this.checkItemValidForStack=checkItemValidForStack;
        this.slotIDs.addAll(slotIDs);
    }
    public void setSlotIDs(Collection<Integer> slotIDs){
        this.slotIDs.clear();
        this.slotIDs.addAll(slotIDs);
    }

    @SuppressWarnings(value = { "unchecked" })
    @Override
    public boolean isSlotValid(Slot slot, ItemStack stack, boolean doStart) {
        if(!slotIDs.isEmpty() && !slotIDs.contains(slot.getSlotIndex())) return false;
        if(checkItemValidForStack && !slot.isItemValid(stack)) return false;
        return this.applySlot.isAssignableFrom(slot.getClass());
    }

    @Override
    public String toString(){
        return this.getClass().getName()+":"+this.applySlot.getName()+"@ slotID"+slotIDs.stream().map(String::valueOf).collect(Collectors.joining(","))+(this.checkItemValidForStack?"":" (Unchecked)");
    }
}
