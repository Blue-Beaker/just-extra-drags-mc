package io.bluebeaker.justextradrags.compat;

import io.bluebeaker.justextradrags.ServerChecker;
import io.bluebeaker.justextradrags.network.NetworkHandler;
import io.bluebeaker.justextradrags.network.PacketSetContainerSlot;
import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.*;
import java.util.List;

//Taken from JustEnoughDrags with Apache2.0 license
public class UniversalGhostHandler<T extends GuiContainer> implements IGhostIngredientHandler<T> {

    protected final Class applySlot;
    protected Container lastContainer;
    protected boolean checkFit = true;
    protected Set<Integer> slotIDs = new HashSet<>();

    public <I extends Slot> UniversalGhostHandler(Class<I> applySlot, boolean checkFit) {
        this.applySlot = applySlot;
        this.checkFit=checkFit;
    }

    @Override
    public <I> List<Target<I>> getTargets(T gui, I ingredient, boolean doStart) {
        if(!ServerChecker.isAvailableOnServer()) {
            return Collections.emptyList();
        }
        List<Target<I>> targets = new ArrayList<>();
        if (ingredient instanceof ItemStack) {
            ItemStack stack = ((ItemStack) ingredient).copy();
            // Modified: Add slotID check
            if(!slotIDs.isEmpty()){
                for (Integer slotID : slotIDs) {
                    if(slotID>=gui.inventorySlots.inventorySlots.size()) continue;
                    Slot slot = gui.inventorySlots.inventorySlots.get(slotID);
                    if (isSlotValid(slot, stack)) {
                        targets.add(createTarget(slot, gui));
                    }
                }
            //Or original if slotID is not defined
            }else{
                for (Slot slot : gui.inventorySlots.inventorySlots) {
                    if (isSlotValid(slot, stack)) {
                        targets.add(createTarget(slot, gui));
                    }
                }
            }
        }
        lastContainer = gui.inventorySlots;
        return targets;
    }
    //Following are original
    public <I> Target<I> createTarget(Slot slot, T gui) {
        return new GhostTarget<I>(slot, gui.getGuiLeft(), gui.getGuiTop());
    }

    public void setSlotIDs(Collection<Integer> slotIDs){
        this.slotIDs.clear();
        this.slotIDs.addAll(slotIDs);
    }

    @Override
    public void onComplete() {
        if (lastContainer != null) {
            lastContainer.detectAndSendChanges();
        }
    }

    @SuppressWarnings(value = { "unchecked" })
    public boolean isSlotValid(Slot slot, ItemStack stack) {
        if(checkFit && !slot.isItemValid(stack)) return false;
        return this.applySlot.isAssignableFrom(slot.getClass());
    }

    protected static class GhostTarget<I> implements Target<I> {
        protected final Rectangle rectangle;
        protected final Slot slot;

        public GhostTarget(Slot slot, int xoff, int yoff) {
            this.rectangle = new Rectangle(slot.xPos + xoff, slot.yPos + yoff, 16, 16);
            this.slot = slot;
        }

        @Override
        public Rectangle getArea() {
            return rectangle;
        }

        @Override
        public void accept(I ingredient) {
            if (ingredient instanceof ItemStack) {
                ItemStack stack = ((ItemStack) ingredient).copy();
                slot.putStack(stack);
                slot.onSlotChanged();

                NetworkHandler.INSTANCE.sendToServer(new PacketSetContainerSlot(slot.slotNumber, stack));
            }
        }
    }
}
