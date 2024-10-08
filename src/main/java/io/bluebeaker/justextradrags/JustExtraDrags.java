package io.bluebeaker.justextradrags;

import org.apache.logging.log4j.Logger;

import com.warmthdawn.justenoughdrags.network.NetworkHandler;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = JustExtraDrags.MODID, name = JustExtraDrags.NAME, version = JustExtraDrags.VERSION)
public class JustExtraDrags
{
    public static final String MODID = "justextradrags";
    public static final String NAME = "JustExtraDrags";
    public static final String VERSION = "1.0";
    private static JustExtraDrags INSTANCE = null;
    
    public MinecraftServer server;

    private static Logger logger;
    
    public static JustExtraDrags getInstance(){
        return INSTANCE;
    }

    public JustExtraDrags() {
        MinecraftForge.EVENT_BUS.register(this);
        INSTANCE=this;
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }
    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        this.server=event.getServer();
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkHandler.registerMessages(MODID);
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
        }
    }

    
    public void logInfo(String log){
        logger.info(log);
    }
}
