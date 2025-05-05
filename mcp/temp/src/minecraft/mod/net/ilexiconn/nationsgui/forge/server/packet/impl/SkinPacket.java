package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class SkinPacket implements IPacket, IClientPacket {

   private String pseudo;
   private List<String> activesSkins;


   public SkinPacket(String pseudo, List<String> activesSkins) {
      this.pseudo = pseudo;
      this.activesSkins = activesSkins;
   }

   public void fromBytes(ByteArrayDataInput data) {
      try {
         NBTTagCompound e = CompressedStreamTools.func_74794_a(data);
         this.pseudo = e.func_74779_i("pseudo");
         this.activesSkins = new ArrayList();
         NBTTagList tagList = e.func_74761_m("activeSkins");

         for(int i = 0; i < tagList.func_74745_c(); ++i) {
            this.activesSkins.add(((NBTTagCompound)tagList.func_74743_b(i)).func_74779_i("skinID"));
         }

      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }
   }

   public void toBytes(ByteArrayDataOutput data) {
      NBTTagCompound tagCompound = new NBTTagCompound();
      tagCompound.func_74778_a("pseudo", this.pseudo);
      NBTTagList tagList = new NBTTagList();
      Iterator e = this.activesSkins.iterator();

      while(e.hasNext()) {
         String activeSkin = (String)e.next();
         NBTTagCompound obj = new NBTTagCompound();
         obj.func_74778_a("skinID", activeSkin);
         tagList.func_74742_a(obj);
      }

      tagCompound.func_74782_a("activeSkins", tagList);

      try {
         CompressedStreamTools.func_74800_a(tagCompound, data);
      } catch (IOException var7) {
         throw new RuntimeException(var7);
      }
   }

   public void handleClientPacket(EntityPlayer player) {
      ClientProxy.SKIN_MANAGER.setPlayerSkins(this.pseudo, this.activesSkins);
   }
}
