package io.bluebeaker.justextradrags;

import io.bluebeaker.justextradrags.client.GhostHandlerMulti;
import io.bluebeaker.justextradrags.compat.UniversalGhostHandler;
import io.bluebeaker.justextradrags.config.ConfigEntry;
import io.bluebeaker.justextradrags.config.JXDConfigManager;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        // Do not access client-only GUIs on server
        if(FMLCommonHandler.instance().getSide() == Side.SERVER) return;
        for (ConfigEntry entry : JXDConfigManager.getAllEntries()) {
            registerEntry(entry, registry);
        }
    }

    @SideOnly(Side.CLIENT)
    public Map<Class<?>, GhostHandlerMulti> containerToHandlers = new HashMap<>();

    @SideOnly(Side.CLIENT)
    private void registerEntry(ConfigEntry entry,IModRegistry registry){

        if(entry.clazzContainerGui==null || !GuiContainer.class.isAssignableFrom(entry.clazzContainerGui)){
            return;
        }

        Class guiContainer = entry.clazzContainerGui;

        UniversalGhostHandler<GuiContainer> handler = new UniversalGhostHandler<>(entry.clazzSlot, entry.checkFit);
        handler.setSlotIDs(entry.slotIDs);

        if(!containerToHandlers.containsKey(guiContainer)){
            GhostHandlerMulti<GuiContainer> handlerMulti = new GhostHandlerMulti<>();
            containerToHandlers.put(guiContainer, handlerMulti);
            registry.addGhostIngredientHandler(guiContainer, handlerMulti);
        }
        containerToHandlers.get(guiContainer).handlers.add(handler);

        JustExtraDrags.getLogger().info("Adding handler {} -> {}:",guiContainer.getName(),handler.toString());
    }
}