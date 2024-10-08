package io.bluebeaker.justextradrags;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = JustExtraDrags.MODID,type = Type.INSTANCE,category = "general")
public class JustExtraDragsConfig {
    public static boolean debug = false;
    @Comment("Enable support for Buildcraft Pipes")
    @LangKey("config.justextradrags.compat.bctransport.name")
    public static boolean BCTransport = true;
    @Comment("Enable support for Buildcraft Gates")
    @LangKey("config.justextradrags.compat.bcsilicon.name")
    public static boolean BCSilicon = true;
}