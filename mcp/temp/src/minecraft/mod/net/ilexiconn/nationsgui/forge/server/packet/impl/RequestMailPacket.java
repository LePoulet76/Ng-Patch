package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.gui.MailGUI;
import net.ilexiconn.nationsgui.forge.server.Mail;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

public class RequestMailPacket implements IPacket, IClientPacket {

   public List mails = new ArrayList();


   public void fromBytes(ByteArrayDataInput data) {
      this.mails = (List)(new Gson()).fromJson(data.readUTF(), List.class);
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.mails));
   }

   public void handleClientPacket(EntityPlayer player) {
      ClientData.getMail().clear();
      Iterator var2 = this.mails.iterator();

      while(var2.hasNext()) {
         Object object = var2.next();
         String str = (String)object;
         String[] sp = str.split(":", 3);
         String subject = sp.length > 2?sp[1]:I18n.func_135053_a("gui.mail.nosubject");
         Mail mail = new Mail(sp[0], subject, sp[sp.length - 1].trim());
         ClientData.addMail(mail);
      }

      MailGUI.loaded = true;
   }
}
