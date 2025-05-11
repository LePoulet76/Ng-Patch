/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.server.item.tools.electric.IElectricTool
 *  icbm.explosion.ICBMExplosion
 *  icbm.explosion.items.ItemMissile
 *  icbm.explosion.items.ItemRocketLauncher
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ChatMessageComponent
 */
package net.ilexiconn.nationsgui.forge.server.command;

import fr.nationsglory.server.item.tools.electric.IElectricTool;
import icbm.explosion.ICBMExplosion;
import icbm.explosion.items.ItemMissile;
import icbm.explosion.items.ItemRocketLauncher;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;

public class CommandRefill
extends CommandBase {
    public String func_71517_b() {
        return "refill";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return null;
    }

    public void func_71515_b(ICommandSender icommandsender, String[] astring) {
        if (icommandsender instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer)icommandsender;
            ItemStack itemStack = entityPlayer.func_70694_bm();
            if (itemStack == null) {
                entityPlayer.func_70006_a(ChatMessageComponent.func_111077_e((String)"nationsgui.command.refill.refused"));
                return;
            }
            boolean done = false;
            if (itemStack.field_77993_c == ICBMExplosion.itemMissile.field_77779_bT) {
                if (itemStack.func_77942_o()) {
                    itemStack.func_77978_p().func_74776_a("missileFuel", ItemMissile.getMaxFuelQuantity((ItemStack)itemStack));
                    done = true;
                }
            } else if (itemStack.field_77993_c == ICBMExplosion.itemRocketLauncher.field_77779_bT) {
                if (itemStack.func_77942_o()) {
                    ItemRocketLauncher itemRocketLauncher = (ItemRocketLauncher)itemStack.func_77973_b();
                    itemRocketLauncher.setEnergy(itemStack, itemRocketLauncher.getEnergyCapacity(itemStack));
                    done = true;
                }
            } else if (itemStack.func_77973_b() instanceof IElectricTool) {
                ((IElectricTool)itemStack.func_77973_b()).setCharge(itemStack, ((IElectricTool)itemStack.func_77973_b()).getMaxCharge());
                done = true;
            }
            if (done) {
                entityPlayer.func_70006_a(ChatMessageComponent.func_111082_b((String)"nationsgui.command.refill.done", (Object[])new Object[]{itemStack.func_82833_r()}));
            } else {
                entityPlayer.func_70006_a(ChatMessageComponent.func_111077_e((String)"nationsgui.command.refill.refused"));
            }
        }
    }

    public int compareTo(Object o) {
        return 0;
    }
}

