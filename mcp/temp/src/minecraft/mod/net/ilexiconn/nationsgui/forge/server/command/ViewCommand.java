package net.ilexiconn.nationsgui.forge.server.command;

import net.ilexiconn.nationsgui.forge.server.command.ViewCommand$Manager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class ViewCommand extends CommandBase {

   private static ViewCommand$Manager manager;


   public ViewCommand() {
      manager = new ViewCommand$Manager();
   }

   public static ViewCommand$Manager getManager() {
      return manager;
   }

   public String func_71517_b() {
      return "view";
   }

   public String func_71518_a(ICommandSender sender) {
      return "\u00a7c/view <username>";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if(sender instanceof EntityPlayerMP) {
         EntityPlayerMP viewer = (EntityPlayerMP)sender;
         if(args.length > 0) {
            String sTarget = args[0];
            EntityPlayerMP target;
            if((target = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(sTarget)) != null) {
               if(target.func_70089_S()) {
                  if(viewer.func_130014_f_().field_73011_w.field_76574_g == target.func_130014_f_().field_73011_w.field_76574_g) {
                     getManager().startView(viewer, target);
                  } else {
                     viewer.func_70006_a(ChatMessageComponent.func_111066_d("\u00a7cLe joueur n\'est pas dans la m\u00eame dimension"));
                  }
               } else {
                  viewer.func_70006_a(ChatMessageComponent.func_111066_d("\u00a7cLe joueur est mort"));
               }
            } else {
               viewer.func_70006_a(ChatMessageComponent.func_111066_d("\u00a7cLe joueur n\'existe pas"));
            }
         } else if(getManager().isViewer(viewer)) {
            getManager().stopView(viewer);
         } else {
            viewer.func_70006_a(ChatMessageComponent.func_111066_d(this.func_71518_a(sender)));
         }
      }

   }

   public int compareTo(Object o) {
      return 0;
   }
}
