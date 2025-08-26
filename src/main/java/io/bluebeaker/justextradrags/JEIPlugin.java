package io.bluebeaker.justextradrags;

import buildcraft.lib.gui.slot.SlotPhantom;
import buildcraft.silicon.gui.GuiGate;
import buildcraft.transport.gui.GuiDiamondPipe;
import buildcraft.transport.gui.GuiDiamondWoodPipe;
import buildcraft.transport.gui.GuiEmzuliPipe_BC8;
import io.bluebeaker.justextradrags.compat.AltGhostHandler;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        // Do not access client-only GUIs on server
        if(FMLCommonHandler.instance().getSide() == Side.SERVER) return;
        if (JustExtraDragsConfig.BCTransport&& Loader.isModLoaded("buildcrafttransport"))
        {
            registry.addGhostIngredientHandler(GuiDiamondPipe.class, new AltGhostHandler<GuiDiamondPipe>(SlotPhantom.class,false));
            registry.addGhostIngredientHandler(GuiDiamondWoodPipe.class, new AltGhostHandler<GuiDiamondWoodPipe>(SlotPhantom.class,false));
            registry.addGhostIngredientHandler(GuiEmzuliPipe_BC8.class, new AltGhostHandler<GuiEmzuliPipe_BC8>(SlotPhantom.class,false));
        }
        if (JustExtraDragsConfig.BCSilicon&&Loader.isModLoaded("buildcraftsilicon")) {
            registry.addGhostIngredientHandler(GuiGate.class, new AltGhostHandler<GuiGate>(SlotPhantom.class,false));
        }
        if(JustExtraDragsConfig.customEntries.length>0){
            CustomEntries.register(registry);
        }
    }
}