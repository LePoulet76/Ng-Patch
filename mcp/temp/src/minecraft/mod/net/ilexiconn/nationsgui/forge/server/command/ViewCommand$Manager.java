package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetViewPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumGameType;

public class ViewCommand$Manager implements ITickHandler {

   public Map<String, String> currentViews = new HashMap();


   public ViewCommand$Manager() {
      System.out.println("Register");
      TickRegistry.registerTickHandler(this, Side.SERVER);
   }

   public boolean isViewer(EntityPlayerMP viewer) {
      return this.currentViews.containsKey(viewer.func_110124_au().toString());
   }

   public void startView(EntityPlayerMP viewer, EntityPlayerMP target) {
      this.currentViews.put(viewer.func_110124_au().toString(), target.func_110124_au().toString());
      viewer.func_70080_a(target.field_70165_t, target.field_70163_u, target.field_70161_v, target.field_70177_z, target.field_70125_A);
      viewer.field_71075_bZ.field_75100_b = true;
      viewer.func_71033_a(EnumGameType.CREATIVE);
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new SetViewPacket(viewer, target)), (Player)viewer);
   }

   public void stopView(EntityPlayerMP viewer) {
      this.currentViews.remove(viewer.func_110124_au().toString());
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new SetViewPacket(viewer, viewer)), (Player)viewer);
   }

   public void tickStart(EnumSet<TickType> type, Object ... tickData) {}

   public void tickEnd(EnumSet<TickType> type, Object ... tickData) {
      Iterator var3 = this.currentViews.keySet().iterator();

      while(var3.hasNext()) {
         String player = (String)var3.next();
         Iterator var5 = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b.iterator();

         while(var5.hasNext()) {
            EntityPlayerMP viewer = (EntityPlayerMP)var5.next();
            if(viewer.func_110124_au().toString().equalsIgnoreCase(player)) {
               Iterator var7 = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b.iterator();

               while(var7.hasNext()) {
                  EntityPlayerMP target = (EntityPlayerMP)var7.next();
                  if(target.func_110124_au().toString().equals(this.currentViews.get(player))) {
                     if(!viewer.func_70093_af() && target.func_70089_S() && viewer.func_130014_f_().field_73011_w.field_76574_g == target.func_130014_f_().field_73011_w.field_76574_g) {
                        viewer.func_70080_a(target.field_70165_t, target.field_70163_u + 15.0D, target.field_70161_v, target.field_70177_z, target.field_70125_A);
                     } else {
                        this.stopView(viewer);
                     }
                  }
               }
            }
         }
      }

   }

   public EnumSet<TickType> ticks() {
      return EnumSet.of(TickType.SERVER);
   }

   public String getLabel() {
      return "SpectateViewTickHandler";
   }
}
