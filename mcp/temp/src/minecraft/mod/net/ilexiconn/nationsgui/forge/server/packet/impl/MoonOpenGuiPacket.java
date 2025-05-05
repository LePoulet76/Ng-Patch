package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class MoonOpenGuiPacket implements IPacket, IClientPacket {

   private boolean goalAchieved;
   private double goal;
   private double actualMoney;
   private List<String> donators;


   public MoonOpenGuiPacket(boolean goalAchieved, double goal, double actualMoney, List<String> list) {
      this.goalAchieved = goalAchieved;
      this.goal = goal;
      this.actualMoney = actualMoney;
      this.donators = list;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.goalAchieved = data.readBoolean();
      this.goal = data.readDouble();
      this.actualMoney = data.readDouble();
      this.donators = new ArrayList();
      int i;
      if((i = data.readInt()) != 0) {
         for(int j = 0; j < i; ++j) {
            this.donators.add(data.readUTF());
         }
      }

   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeBoolean(this.goalAchieved);
      data.writeDouble(this.goal);
      data.writeDouble(this.actualMoney);
      if(this.donators != null && !this.donators.isEmpty()) {
         data.writeInt(this.donators.size());
         Iterator var2 = this.donators.iterator();

         while(var2.hasNext()) {
            String donator = (String)var2.next();
            data.writeUTF(donator);
         }
      } else {
         data.writeInt(0);
      }

   }

   public void handleClientPacket(EntityPlayer player) {
      ClientEventHandler.getInstance().openMoonGui(this.goalAchieved, this.goal, this.actualMoney, this.donators);
   }
}
