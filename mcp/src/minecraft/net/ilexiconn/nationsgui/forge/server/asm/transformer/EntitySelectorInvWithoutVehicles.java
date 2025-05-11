/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.ngvehicles.common.entity.vehicles.EntityVehicle
 *  net.minecraft.command.IEntitySelector
 *  net.minecraft.entity.Entity
 *  net.minecraft.inventory.IInventory
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import fr.nationsglory.ngvehicles.common.entity.vehicles.EntityVehicle;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;

public class EntitySelectorInvWithoutVehicles
implements IEntitySelector {
    public boolean func_82704_a(Entity entity) {
        return entity instanceof IInventory && entity.func_70089_S() && !(entity instanceof EntityVehicle);
    }
}

