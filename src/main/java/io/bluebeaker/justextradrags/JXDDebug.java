package io.bluebeaker.justextradrags;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import java.util.HashMap;

@EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class JXDDebug {
    public static JXDDebug INSTANCE = null;

    private static GuiContainer lastContainer = null;

    private static boolean isMousePressed = false;

    public JXDDebug() {
        INSTANCE = this;
    }

    public static JXDDebug getInstance() {
        if (INSTANCE != null)
            return INSTANCE;
        else
            return new JXDDebug();
    }

    @SubscribeEvent
    public static void onGuiEvent(GuiContainerEvent.DrawForeground event) {
        if (!JustExtraDragsConfig.debug)
            return;
        GuiContainer container = event.getGuiContainer();

        if(Mouse.isButtonDown(0) && !isMousePressed){
            Slot slot = event.getGuiContainer().getSlotUnderMouse();
            if(slot!=null){
                JustExtraDrags.getLogger().info("Clicked slot: {}, ID: {}",slot.getClass().getName(),slot.getSlotIndex());
            }
        }
        isMousePressed=Mouse.isButtonDown(0);

        if (container == lastContainer)
            return;
        lastContainer = container;
        JustExtraDrags.getLogger().info("Opened Container: {} , {}",container.getClass().getName(),container.inventorySlots.getClass().getName());

        HashMap<String, Integer> slots = new HashMap<String, Integer>();
        for (Slot slot : event.getGuiContainer().inventorySlots.inventorySlots) {
            String name = slot.getClass().getName();
            if (!slots.containsKey(name))
                slots.put(name, 0);
            slots.put(name, slots.get(name) + 1);
        }
        for (String name : slots.keySet()) {
            JustExtraDrags.getLogger().info(name + " x " + slots.get(name));
        }

    }
}
