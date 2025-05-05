package net.ilexiconn.nationsgui.forge.server.command;

import java.util.List;
import net.ilexiconn.nationsgui.forge.server.ServerUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

public class SpawnGeckoBikeCommand extends CommandBase {

   public String func_71517_b() {
      return "spawngeckobike";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return "/spawngeckobike <name> <flying?>";
   }

   public void func_71515_b(ICommandSender icommandsender, String[] astring) {
      if(icommandsender instanceof EntityPlayer) {
         if(astring.length > 0) {
            ServerUtils.spawnGeckoBike((EntityPlayer)icommandsender, icommandsender.func_82114_b().field_71574_a, icommandsender.func_82114_b().field_71572_b, icommandsender.func_82114_b().field_71573_c, icommandsender.func_130014_f_().func_72912_H().func_76065_j(), astring[0], astring.length > 1 && astring[1].equalsIgnoreCase("true"));
         } else {
            icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("\u00a7cUsage: /spawngeckobike <nom> <flying?>"));
         }
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
