package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.nationsglory.ngupgrades.common.entity.GenericGeckoEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class GeckoEntityDialogSavePacket implements IPacket, IServerPacket
{
    private int entityUUID;
    private String dialogInteraction;
    private String dialogWalkBy;
    private int radiusWalkBy;

    public GeckoEntityDialogSavePacket(GenericGeckoEntity geckoEntity)
    {
        this.entityUUID = geckoEntity.entityId;
        this.dialogInteraction = geckoEntity.dialogInteraction;
        this.dialogWalkBy = geckoEntity.dialogWalkBy;
        this.radiusWalkBy = geckoEntity.radiusWalkBy;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.entityUUID = data.readInt();
        this.dialogInteraction = data.readUTF();
        this.dialogWalkBy = data.readUTF();
        this.radiusWalkBy = data.readInt();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.entityUUID);
        data.writeUTF(this.dialogInteraction);
        data.writeUTF(this.dialogWalkBy);
        data.writeInt(this.radiusWalkBy);
    }

    public void handleServerPacket(EntityPlayer player)
    {
        if (MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(player.username))
        {
            Entity entity = player.worldObj.getEntityByID(this.entityUUID);

            if (entity instanceof GenericGeckoEntity)
            {
                ((GenericGeckoEntity)entity).setDialogInteraction(this.dialogInteraction);
                ((GenericGeckoEntity)entity).setDialogWalkBy(this.dialogWalkBy);
                ((GenericGeckoEntity)entity).setRadiusWalkBy(this.radiusWalkBy);
            }
        }
    }
}
