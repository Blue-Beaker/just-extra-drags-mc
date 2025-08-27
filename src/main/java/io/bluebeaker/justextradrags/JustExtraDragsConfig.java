package io.bluebeaker.justextradrags;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = JustExtraDrags.MODID,type = Type.INSTANCE,category = "general")
public class JustExtraDragsConfig {
    @Comment("Enable debug, dumps GUI, Container, and Slot classes into the log, when opening GUI or clicking on slots.")
    @LangKey("config.justextradrags.debug.name")
    public static boolean debug = false;
    @Comment("Enable support for Buildcraft Pipes")
    @LangKey("config.justextradrags.compat.bctransport.name")
    public static boolean BCTransport = true;
    @Comment("Enable support for Buildcraft Gates")
    @LangKey("config.justextradrags.compat.bcsilicon.name")
    public static boolean BCSilicon = true;
    @Comment({"Add support for Custom slots.",
            "Format: containerGUI:container:slot[:true/false][:slotIDs]",
            "Use debug to find the container, the slot, and slot IDs.",
            "Try to set the 4th augment `true` if the drag isn't working. It makes the slot ignore which items can be put into it.",
            "5th augment accepts a comma-separated slot IDs allowed to drag to, such as '5,10-13'"
    })
    @LangKey("config.justextradrags.custom.name")
    public static String[] customEntries = new String[]{};

    @Comment("Patch JustEnoughDrags so it won't crash on the dedicated server.")
    public static boolean serverSidePatches = true;
}