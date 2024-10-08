## Just Extra Drags
An addon for [justenoughdrags](https://github.com/warmthdawn/justenoughdrags), which adds support for more mods.  
It only have buildin support for BuildCraft Pipes for now,
But it allows modpack maker to add custom slots on custom GUIs without the need to write codes.  

### Custom entries
Format is `ContainerClass:SlotClass` or `ContainerClass:SlotClass:true/false`.
Try to set the third augment `true` if the drag isn't working. It makes the slot ignore which items can be put into it.
```
    S:customEntries <
        net.minecraft.client.gui.inventory.GuiChest:net.minecraft.inventory.Slot:true
     >
```