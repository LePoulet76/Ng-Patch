package net.ilexiconn.nationsgui.forge.server.command;

import java.util.List;
import net.ilexiconn.nationsgui.forge.server.ServerUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class SpawnGeckoEntityCommand extends CommandBase {

   public String func_71517_b() {
      return "spawngeckoentity";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return "/spawngeckoentity <name> <stalk:true> <health:true>";
   }

   public void func_71515_b(ICommandSender icommandsender, String[] astring) {
      boolean isStalking = false;
      boolean hideHealthBar = false;
      if(astring.length > 0) {
         if(astring.length > 1) {
            isStalking = astring[1].equalsIgnoreCase("true");
            if(astring.length > 2) {
               hideHealthBar = astring[2].equalsIgnoreCase("true");
            }
         }

         if(astring[0].contains("package")) {
            ServerUtils.spawnGeckoCarePackageAt(icommandsender.func_82114_b().field_71574_a, icommandsender.func_82114_b().field_71572_b, icommandsender.func_82114_b().field_71573_c, icommandsender.func_130014_f_().func_72912_H().func_76065_j(), astring[0], 20.0D);
         } else {
            ServerUtils.spawnGeckoEntityAt(icommandsender.func_82114_b().field_71574_a, icommandsender.func_82114_b().field_71572_b, icommandsender.func_82114_b().field_71573_c, icommandsender.func_130014_f_().func_72912_H().func_76065_j(), astring[0], 20.0D, isStalking, hideHealthBar);
         }
      } else {
         icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("\u00a7cUsage: /spawngeckoentity <nom> <stalk? true|false> <hidehealthbar? true|false>"));
      }

   }

   public int compareTo(Object o) {
      return 0;
   }

   public List func_71516_a(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
      return null;
   }

   public boolean func_82358_a(String[] par1ArrayOfStr, int par2) {
      return par2 == 0;
   }
}
