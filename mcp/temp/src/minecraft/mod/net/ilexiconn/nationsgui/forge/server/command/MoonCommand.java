package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MoonOpenGuiPacket;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import org.bukkit.Bukkit;

public class MoonCommand extends CommandBase {

   private static Class cl = Bukkit.getPluginManager().getPlugin("NationsGUI") != null?Bukkit.getPluginManager().getPlugin("NationsGUI").getClass():null;


   public String func_71517_b() {
      return "moon";
   }

   public String func_71518_a(ICommandSender sender) {
      return null;
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if(cl == null && Bukkit.getPluginManager().getPlugin("NationsGUI") != null) {
         cl = Bukkit.getPluginManager().getPlugin("NationsGUI").getClass();
      }

      if(args.length == 2 && MinecraftServer.func_71276_C().func_71203_ab().func_72353_e(sender.func_70005_c_())) {
         if(args[0].equalsIgnoreCase("setGoal") && this.isDouble(args[1])) {
            double e = Double.parseDouble(args[1]);

            try {
               cl.getDeclaredMethod("setMoonGoal", new Class[]{Double.TYPE}).invoke((Object)null, new Object[]{Double.valueOf(e)});
               sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7aLe nouveau goal est de \u00a72" + e)));
            } catch (NoSuchMethodException var7) {
               var7.printStackTrace();
            }
         } else if(args[0].equalsIgnoreCase("setActive") && (args[1].equals("1") || args[1].equals("0"))) {
            try {
               cl.getDeclaredMethod("setMoonActive", new Class[]{Boolean.TYPE}).invoke((Object)null, new Object[]{Boolean.valueOf(args[1].equals("1"))});
               sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7aLe projet lunaire est maintenant " + (args[1].equals("1")?"\u00a72activ\u00e9":"\u00a7cd\u00e9sactiv\u00e9"))));
            } catch (NoSuchMethodException var6) {
               var6.printStackTrace();
            }
         }

      } else {
         if(this.isMoonActived()) {
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new MoonOpenGuiPacket(this.isGoalAchieved(), this.getGoal(), this.getActualMoney(), this.getDonators())), (Player)sender);
         } else {
            sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7cIl n\'y a pas de projet lunaire en cours !")));
         }

      }
   }

   private List<String> getDonators() {
      try {
         return (List)cl.getDeclaredMethod("getDonators", new Class[0]).invoke((Object)null, new Object[0]);
      } catch (SecurityException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public boolean isGoalAchieved() {
      try {
         return ((Boolean)cl.getDeclaredMethod("isGoalAchieved", new Class[0]).invoke((Object)null, new Object[0])).booleanValue();
      } catch (SecurityException var2) {
         var2.printStackTrace();
         return false;
      }
   }

   public double getGoal() {
      try {
         return ((Double)cl.getDeclaredMethod("getGoal", new Class[0]).invoke((Object)null, new Object[0])).doubleValue();
      } catch (SecurityException var2) {
         var2.printStackTrace();
         return 0.0D;
      }
   }

   public double getActualMoney() {
      try {
         return ((Double)cl.getDeclaredMethod("getActualMoney", new Class[0]).invoke((Object)null, new Object[0])).doubleValue();
      } catch (SecurityException var2) {
         var2.printStackTrace();
         return 0.0D;
      }
   }

   public boolean isMoonActived() {
      try {
         return ((Boolean)cl.getDeclaredMethod("isMoonActived", new Class[0]).invoke((Object)null, new Object[0])).booleanValue();
      } catch (SecurityException var2) {
         var2.printStackTrace();
         return false;
      }
   }

   private boolean isDouble(String string) {
      try {
         Double.parseDouble(string);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   public int compareTo(Object arg0) {
      return 0;
   }

}
