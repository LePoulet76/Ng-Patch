package net.ilexiconn.nationsgui.forge.client.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class FovCommand extends CommandBase {

   public String func_71517_b() {
      return "fov";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return null;
   }

   public void func_71515_b(ICommandSender icommandsender, String[] astring) {
      if(astring.length >= 1) {
         try {
            int e = Integer.parseInt(astring[0]);
            if(e >= 0 && e <= 150) {
               float normalizedFov = (float)(e - 0) / 150.0F;
               Minecraft.func_71410_x().field_71474_y.field_74334_X = normalizedFov;
               icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("Field of view set to " + e));
            } else {
               icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("Field of view must be between 0 and 150"));
            }
         } catch (NumberFormatException var5) {
            icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("Invalid number format. Please enter a valid FOV value."));
         }
      } else {
         icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("Usage: /fov <value>"));
      }

   }

   public int compareTo(Object o) {
      return 0;
   }

   public boolean func_71519_b(ICommandSender par1ICommandSender) {
      return true;
   }
}
