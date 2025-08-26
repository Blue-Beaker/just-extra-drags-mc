package io.bluebeaker.justextradrags.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

// Taken from JustEnoughDrags with Apache2.0 license
public class NetworkHandler {

    public static SimpleNetworkWrapper INSTANCE;
    private static int ID = 0;
    private static int nextID() {
        return ID++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);

        // Server side
        INSTANCE.registerMessage(PacketSetContainerSlot.Handler.class, PacketSetContainerSlot.class, nextID(), Side.SERVER);
    }
}
