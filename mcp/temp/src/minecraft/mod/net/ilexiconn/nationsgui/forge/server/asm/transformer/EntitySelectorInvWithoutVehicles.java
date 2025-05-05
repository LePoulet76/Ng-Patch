package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import fr.nationsglory.ngvehicles.common.entity.vehicles.EntityVehicle;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;

public class EntitySelectorInvWithoutVehicles implements IEntitySelector {

   public boolean func_82704_a(Entity entity) {
      return entity instanceof IInventory && entity.func_70089_S() && !(entity instanceof EntityVehicle);
   }
}
