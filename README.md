## Just Extra Drags
An addon for [justenoughdrags](https://github.com/warmthdawn/justenoughdrags), and adds support for more mods.  
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

### Examples
```
# BuildCraft AutoCrafter
buildcraft.factory.gui.GuiAutoCraftItems:buildcraft.lib.gui.slot.SlotPhantom:true
buildcraft.silicon.gui.GuiAdvancedCraftingTable:buildcraft.lib.gui.slot.SlotPhantom:true

# Railcraft cart, item and fluid Filters
mods.railcraft.client.gui.GuiCartTank:mods.railcraft.common.gui.slots.SlotFluidFilter
mods.railcraft.client.gui.GuiCartCargo:mods.railcraft.common.gui.slots.SlotStackFilter
mods.railcraft.client.gui.GuiManipulatorCartItem:mods.railcraft.common.gui.slots.SlotMinecartPhantom
mods.railcraft.client.gui.GuiManipulatorCartItem:mods.railcraft.common.gui.slots.SlotRailcraft
mods.railcraft.client.gui.GuiManipulatorCartFluid:mods.railcraft.common.gui.slots.SlotRailcraft
mods.railcraft.client.gui.GuiManipulatorCartFluid:mods.railcraft.common.gui.slots.SlotFluidFilter
mods.railcraft.client.gui.GuiManipulatorCartFluid:mods.railcraft.common.gui.slots.SlotMinecartPhantom
mods.railcraft.client.gui.GuiDispenserTrain:mods.railcraft.common.gui.slots.SlotDispensableCart

# Forestry carpenter and worktable recipes
forestry.factory.gui.GuiCarpenter:forestry.core.gui.slots.SlotCraftMatrix
forestry.worktable.gui.GuiWorktable:forestry.core.gui.slots.SlotCraftMatrix
```