package net.ilexiconn.nationsgui.forge.server.notifications;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import net.ilexiconn.nationsgui.bukkit.util.Translation;
import net.ilexiconn.nationsgui.forge.server.notifications.INotificationActionHandler;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager$NColor;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager$NIcon;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NotificationPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class NotificationManager {

   public static int T_SHORT = 3000;
   public static int T_MEDIUM = 5000;
   public static int T_LONG = 10000;
   private static HashMap<String, INotificationActionHandler> registry = new HashMap();


   public static void registerAction(String id, INotificationActionHandler notificationActionHandler) {
      registry.put(id, notificationActionHandler);
   }

   public static void executeAction(EntityPlayer player, String id, NBTTagCompound data) {
      if(registry.containsKey(id)) {
         ((INotificationActionHandler)registry.get(id)).handleAction(player, data);
      }

   }

   public static void sendNotificationToPlayer(Player player, String title, String content, NotificationManager$NColor color, NotificationManager$NIcon icon, long lifeTime) {
      NBTTagCompound nbtTagCompound = new NBTTagCompound();
      nbtTagCompound.func_74778_a("title", title);
      nbtTagCompound.func_74778_a("color", color.name());
      nbtTagCompound.func_74778_a("icon", icon.name());
      nbtTagCompound.func_74778_a("content", content);
      nbtTagCompound.func_74772_a("lifetime", lifeTime);
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new NotificationPacket(nbtTagCompound)), player);
   }

   public static void sendNotificationToPlayer(Player player, String title, String content, NotificationManager$NColor color, NotificationManager$NIcon icon, long lifeTime, String actionAllowID, String actionAllowTitle, String actionDenyID, String actionDenyTitle) {
      NBTTagCompound nbtTagCompound = new NBTTagCompound();
      nbtTagCompound.func_74778_a("title", title);
      nbtTagCompound.func_74778_a("color", color.name());
      nbtTagCompound.func_74778_a("icon", icon.name());
      nbtTagCompound.func_74778_a("content", content);
      nbtTagCompound.func_74772_a("lifetime", lifeTime);
      NBTTagCompound actions = new NBTTagCompound();
      NBTTagCompound allow = new NBTTagCompound();
      NBTTagCompound deny = new NBTTagCompound();
      allow.func_74778_a("translatedTitle", actionAllowTitle);
      allow.func_74778_a("id", actionAllowID);
      deny.func_74778_a("translatedTitle", actionDenyTitle);
      deny.func_74778_a("id", actionDenyID);
      actions.func_74782_a("allow", allow);
      actions.func_74782_a("deny", deny);
      nbtTagCompound.func_74766_a("actions", actions);
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new NotificationPacket(nbtTagCompound)), player);
   }

   public static void sendNotificationToPlayer(Player player, String title, String content, NotificationManager$NColor color, NotificationManager$NIcon icon, long lifeTime, String actionAllowID, String actionAllowTitle, String actionDenyID, String actionDenyTitle, String actionDenyArgs, String actionAllowArgs) {
      NBTTagCompound nbtTagCompound = new NBTTagCompound();
      nbtTagCompound.func_74778_a("title", title);
      nbtTagCompound.func_74778_a("color", color.name());
      nbtTagCompound.func_74778_a("icon", icon.name());
      System.out.println(content);
      nbtTagCompound.func_74778_a("content", content);
      nbtTagCompound.func_74772_a("lifetime", lifeTime);
      NBTTagCompound actions = new NBTTagCompound();
      NBTTagCompound allow = new NBTTagCompound();
      NBTTagCompound deny = new NBTTagCompound();
      boolean par1 = false;
      boolean par2 = false;
      if(!actionAllowTitle.equals("null")) {
         if(actionAllowTitle.equals("default")) {
            allow.func_74778_a("translatedTitle", Translation.get("Accepter"));
         } else {
            allow.func_74778_a("translatedTitle", actionAllowTitle);
         }

         allow.func_74778_a("id", actionAllowID);
         allow.func_74778_a("args", actionAllowArgs);
         actions.func_74782_a("allow", allow);
         par1 = true;
      }

      if(!actionDenyTitle.equals("null")) {
         if(actionDenyTitle.equals("default")) {
            deny.func_74778_a("translatedTitle", Translation.get("Refuser"));
         } else {
            deny.func_74778_a("translatedTitle", actionDenyTitle);
         }

         deny.func_74778_a("id", actionDenyID);
         deny.func_74778_a("args", actionDenyArgs);
         actions.func_74782_a("deny", deny);
         par2 = true;
      }

      if(par1 || par2) {
         nbtTagCompound.func_74766_a("actions", actions);
      }

      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new NotificationPacket(nbtTagCompound)), player);
   }

}
