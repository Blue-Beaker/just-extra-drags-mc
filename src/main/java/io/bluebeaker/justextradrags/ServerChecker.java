package io.bluebeaker.justextradrags;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ServerChecker {
    private static boolean isOnServer = false;
    public static void onConnected(boolean isOnServer){
        ServerChecker.isOnServer=isOnServer;
    }
    public static boolean isAvailableOnServer(){
        return isOnServer;
    }
}
