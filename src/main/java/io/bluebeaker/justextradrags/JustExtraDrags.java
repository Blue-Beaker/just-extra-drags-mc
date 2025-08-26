package io.bluebeaker.justextradrags;

import io.bluebeaker.justextradrags.compat.BCCompat;
import io.bluebeaker.justextradrags.config.JXDConfigManager;
import io.bluebeaker.justextradrags.network.NetworkHandler;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.common.versioning.ComparableVersion;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

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

import java.util.Map;

@Mod(modid = JustExtraDrags.MODID, name = JustExtraDrags.NAME, version = JustExtraDrags.VERSION,acceptableRemoteVersions = "*")
public class JustExtraDrags
{
    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
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
        BCCompat.addBuildcraftCompat();
        JXDConfigManager.updateEntriesFromConfig();

        NetworkHandler.registerMessages(MODID);
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
        }
    }

    @NetworkCheckHandler
    public boolean checkModLists(Map<String, String> modList, Side side) {
        if (side == Side.SERVER) {
            ComparableVersion remoteVersion = new ComparableVersion(modList.getOrDefault(MODID,"0.0.0"));
            ServerChecker.onConnected(remoteVersion.compareTo(new ComparableVersion("1.1.0"))>=0);
        }
        return true;
    }

    public static Logger getLogger(){
        return logger;
    }
}
