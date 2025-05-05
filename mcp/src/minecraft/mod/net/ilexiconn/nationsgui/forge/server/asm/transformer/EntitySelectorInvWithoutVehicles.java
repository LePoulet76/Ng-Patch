package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import fr.nationsglory.ngvehicles.common.entity.vehicles.EntityVehicle;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;

public class EntitySelectorInvWithoutVehicles implements IEntitySelector
{
    /**
     * Return whether the specified entity is applicable to this filter.
     */
    public boolean isEntityApplicable(Entity entity)
    {
        return entity instanceof IInventory && entity.isEntityAlive() && !(entity instanceof EntityVehicle);
    }
}
