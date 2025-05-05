package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGalleryDataPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionGalleryEditImagePacket implements IPacket, IClientPacket {

   public String target;
   public Integer imageId;
   public Integer imageIndex;
   public Integer position;
   public String title;
   public String description;


   public FactionGalleryEditImagePacket(String targetName, Integer imageId, Integer imageIndex, Integer position, String title, String description) {
      this.target = targetName;
      this.imageId = imageId;
      this.imageIndex = imageIndex;
      this.position = position;
      this.title = title;
      this.description = description;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.target);
      data.writeInt(this.imageId.intValue());
      data.writeInt(this.imageIndex.intValue());
      data.writeInt(this.position.intValue());
      data.writeUTF(this.title);
      data.writeUTF(this.description);
   }

   public void handleClientPacket(EntityPlayer player) {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionGalleryDataPacket((String)FactionGUI.factionInfos.get("name"))));
   }
}
