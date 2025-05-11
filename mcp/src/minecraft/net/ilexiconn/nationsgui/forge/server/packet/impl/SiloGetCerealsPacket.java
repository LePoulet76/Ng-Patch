/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  fr.nationsglory.ngcontent.NGContent
 *  fr.nationsglory.server.block.entity.GCSiloBlockEntity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.nationsglory.ngcontent.NGContent;
import fr.nationsglory.server.block.entity.GCSiloBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class SiloGetCerealsPacket
implements IPacket,
IServerPacket {
    private int posX;
    private int posY;
    private int posZ;

    public SiloGetCerealsPacket(int posX, int posY, int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        TileEntity tileEntity = player.func_130014_f_().func_72796_p(this.posX, this.posY, this.posZ);
        if (tileEntity instanceof GCSiloBlockEntity && !((GCSiloBlockEntity)tileEntity).cerealType.isEmpty() && ((GCSiloBlockEntity)tileEntity).quantity > 0) {
            if (player.field_71071_by.func_70447_i() != -1) {
                int qteToDrop = Math.min(64, ((GCSiloBlockEntity)tileEntity).quantity);
                ((GCSiloBlockEntity)tileEntity).quantity -= qteToDrop;
                ItemStack cereal = new ItemStack(NGContent.getCerealIdFromName((String)((GCSiloBlockEntity)tileEntity).cerealType).intValue(), qteToDrop, 0);
                if (((GCSiloBlockEntity)tileEntity).quantity == 0) {
                    ((GCSiloBlockEntity)tileEntity).cerealType = "";
                }
                player.field_71071_by.func_70441_a(cereal);
            } else {
                player.func_71035_c(Translation.get("\u00a7cImpossible de vider le silo, votre inventaire est plein."));
            }
        }
    }
}

