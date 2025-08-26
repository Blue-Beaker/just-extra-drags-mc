package io.bluebeaker.justextradrags;

import io.bluebeaker.justextradrags.compat.AltGhostHandler;
import io.bluebeaker.justextradrags.config.ConfigEntry;
import io.bluebeaker.justextradrags.config.JXDConfigManager;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    private static void registerEntry(ConfigEntry entry,IModRegistry registry){

        if(entry.clazzContainerGui==null || !GuiContainer.class.isAssignableFrom(entry.clazzContainerGui)){
            return;
        }

        Class guiContainer = entry.clazzContainerGui;

        AltGhostHandler<GuiContainer> handler = new AltGhostHandler<>(entry.clazzSlot, entry.checkFit);

        registry.addGhostIngredientHandler(guiContainer, handler);
        handler.setSlotIDs(entry.slotIDs);

        JustExtraDrags.getLogger().info("Adding handler {} -> {}:",guiContainer.getName(),handler.toString());
    }
}