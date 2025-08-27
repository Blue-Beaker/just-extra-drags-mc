## Just Extra Drags

#### From 1.1.0 the config format is updated. The old config format can still be used, but any line of config in old format will disable server-side check of the mod.
#### Also, JustEnoughDrags is no longer a hard-dependency of this mod.

Adds JEI dragging support for more mods, that haven't been supported by [JustEnoughDrags](https://github.com/warmthdawn/justenoughdrags).  
It have buildin support for BuildCraft Pipes and Gates for now,
But allows modpack maker to add custom slots on custom GUIs without the need to write codes.  

Also patches JustEnoughDrags so it won't crash on the dedicated server.  

### Custom entries
The format is  
```
a.b.c.GuiThing:a.b.c.ContainerThing:a.b.c.SlotThing:true:0,2,10-13
```
From 1.1.0 server-side check is implemented, the old format is still usable but any line of old config will disable the serverside checking.  
(Old Config Format)  
```
a.b.c.GuiThing:a.b.c.SlotThing:true:0,2,10-13
```
Try to set the 4th augment `true` if the drag isn't working. It makes the slot ignore which items can be put into it.  
5th augment accepts a comma-separated slot IDs allowed to drag to, such as '5,10-13'.  
Turn on 'Debug' in mod config to get the required classpath in logs. a generated config line is also printed.  

### Examples
See [This](ConfigExamples.md).
### License
Most of this project is licensed under MIT, but a small portion of code is taken from [JustEnoughDrags by warmthdawn](https://github.com/warmthdawn/justenoughdrags) with [Apache 2.0 License](LICENSE_JustEnoughDrags) and modified for this mod:
- [UniversalGhostHandler.java](src/main/java/io/bluebeaker/justextradrags/compat/UniversalGhostHandler.java)
- [NetworkHandler.java](src/main/java/io/bluebeaker/justextradrags/network/NetworkHandler.java)
- [PacketSetContainerSlot.java](src/main/java/io/bluebeaker/justextradrags/network/PacketSetContainerSlot.java)