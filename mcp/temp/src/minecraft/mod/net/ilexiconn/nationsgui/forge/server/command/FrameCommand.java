package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FrameOpenGuiPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class FrameCommand extends CommandBase {

   public String func_71517_b() {
      return "frame";
   }

   public String func_71518_a(ICommandSender sender) {
      return "/frame <player> <url> <title>";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if(args.length >= 1) {
         String target = "";
         String url = "";
         String title = "";
         String musicUrl = "";
         int beginIndexTitle = 1;
         if(args[0].contains(".png")) {
            url = args[0];
         } else {
            target = args[0];
         }

         if(url.isEmpty() && args.length >= 2) {
            url = args[1];
            beginIndexTitle = 2;
         }

         if(args.length > beginIndexTitle && args[beginIndexTitle].contains(".mp3")) {
            musicUrl = args[beginIndexTitle];
            ++beginIndexTitle;
         }

         if(args.length > beginIndexTitle) {
            for(int targetPlayer = beginIndexTitle; targetPlayer < args.length; ++targetPlayer) {
               title = title + args[targetPlayer] + " ";
            }
         }

         if(target.isEmpty() && sender instanceof EntityPlayer) {
            target = ((EntityPlayerMP)sender).field_71092_bJ;
         }

         if(!target.isEmpty() && !url.isEmpty()) {
            EntityPlayerMP var13 = null;
            MinecraftServer minecraftServer = MinecraftServer.func_71276_C();
            List playerList = minecraftServer.func_71203_ab().field_72404_b;
            Iterator var11 = playerList.iterator();

            while(var11.hasNext()) {
               EntityPlayerMP player = (EntityPlayerMP)var11.next();
               if(player.func_70005_c_().equalsIgnoreCase(target)) {
                  var13 = player;
                  break;
               }
            }

            if(var13 == null) {
               return;
            }

            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new FrameOpenGuiPacket(url, title, musicUrl)), (Player)var13);
         }
      }

   }

   public int compareTo(Object arg0) {
      return 0;
   }
}
