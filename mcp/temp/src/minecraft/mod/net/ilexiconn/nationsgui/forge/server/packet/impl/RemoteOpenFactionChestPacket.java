package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;

public class RemoteOpenFactionChestPacket implements IPacket, IServerPacket {

   private boolean canTake;
   private boolean canDeposit;
   private int chestLevel;
   private String factionId;
   public static HashMap<String, String> playersTargetGui = new HashMap();
   public static HashMap<String, Boolean> playersCanTake = new HashMap();
   public static HashMap<String, Boolean> playersCanDeposit = new HashMap();
   public static HashMap<String, Integer> playersChestLevel = new HashMap();
   public static HashMap<String, String> openedChestsByPlayer = new HashMap();


   public RemoteOpenFactionChestPacket(String factionName, boolean canTake, boolean canDeposit, int chestLevel) {
      this.factionId = factionName;
      this.canTake = canTake;
      this.canDeposit = canDeposit;
      this.chestLevel = chestLevel;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.factionId = data.readUTF();
      this.canTake = data.readBoolean();
      this.canDeposit = data.readBoolean();
      this.chestLevel = data.readInt();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.factionId);
      data.writeBoolean(this.canTake);
      data.writeBoolean(this.canDeposit);
      data.writeInt(this.chestLevel);
   }

   public void handleServerPacket(EntityPlayer player) {
      NationsGUI.openCountryChest(player, this.factionId, this.chestLevel);
   }

}
