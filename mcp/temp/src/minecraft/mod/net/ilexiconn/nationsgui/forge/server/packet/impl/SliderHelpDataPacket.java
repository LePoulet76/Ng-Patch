package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SliderHelpGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SliderHelpDataPacket$1;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class SliderHelpDataPacket implements IPacket, IClientPacket {

   private String identifier;
   private ArrayList<String> images = new ArrayList();
   private String wikiURL;


   public SliderHelpDataPacket(String identifier) {
      this.identifier = identifier;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.identifier = data.readUTF();
      this.images = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new SliderHelpDataPacket$1(this)).getType());
      this.wikiURL = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.identifier);
   }

   public void handleClientPacket(EntityPlayer player) {
      if(this.images.isEmpty()) {
         Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
      } else {
         SliderHelpGui.images = this.images;
         SliderHelpGui.wikiURL = this.wikiURL;
         ClientProxy.playClientMusic("https://static.nationsglory.fr/N4y22G434N.mp3", 1.0F);
         Iterator var2 = this.images.iterator();

         while(var2.hasNext()) {
            String image = (String)var2.next();
            ClientProxy.getRemoteResource(image);
         }

      }
   }
}
