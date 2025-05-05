package net.ilexiconn.nationsgui.forge.server.command;

import fr.nationsglory.ngbridge.Bukkit2Forge;
import java.util.Iterator;
import micdoodle8.mods.galacticraft.core.entities.player.GCCorePlayerMP;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.entity.data.NGPlayerData;
import net.ilexiconn.nationsgui.forge.server.world.EventSaveData;
import net.ilexiconn.nationsgui.forge.server.world.EventSaveData$Event;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EventCommand extends CommandBase {

   public String func_71517_b() {
      return "goevent";
   }

   public String func_71518_a(ICommandSender iCommandSender) {
      return "";
   }

   public void func_71515_b(ICommandSender iCommandSender, String[] strings) {
      MinecraftServer server = MinecraftServer.func_71276_C();
      boolean isOp = !(iCommandSender instanceof EntityPlayer) || server.func_71203_ab().func_72353_e(((EntityPlayer)iCommandSender).field_71092_bJ);
      EventSaveData eventSaveData = EventSaveData.get(MinecraftServer.func_71276_C().func_130014_f_());
      GCCorePlayerMP player = null;
      if(strings.length >= 3) {
         player = (GCCorePlayerMP)MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(strings[2]);
      } else if(iCommandSender instanceof GCCorePlayerMP) {
         player = (GCCorePlayerMP)iCommandSender;
      }

      EventSaveData$Event event = strings.length >= 2?eventSaveData.getEvent(strings[1]):null;
      if(strings.length >= 1) {
         String var8 = strings[0];
         byte var9 = -1;
         switch(var8.hashCode()) {
         case -690213213:
            if(var8.equals("register")) {
               var9 = 2;
            }
            break;
         case 3304:
            if(var8.equals("go")) {
               var9 = 0;
            }
            break;
         case 100571:
            if(var8.equals("end")) {
               var9 = 1;
            }
            break;
         case 3322014:
            if(var8.equals("list")) {
               var9 = 4;
            }
            break;
         case 836015164:
            if(var8.equals("unregister")) {
               var9 = 3;
            }
         }

         switch(var9) {
         case 0:
            if(event == null) {
               throw new CommandException("event.notRegistered", new Object[0]);
            }

            if(player == null) {
               throw new CommandException("event.playerNotFound", new Object[0]);
            }

            NGPlayerData ngPlayerData1 = NGPlayerData.get(player);
            if(ngPlayerData1.eventSave != null) {
               throw new CommandException("event.alreadyInEvent", new Object[0]);
            }

            this.savePlayerData(player);
            this.clearPlayerData(player);
            this.applyWarp(player, event);
            break;
         case 1:
            if(player == null) {
               throw new CommandException("event.playerNotFound", new Object[0]);
            }

            this.restorePlayerData(player);
            break;
         case 2:
            if(!isOp || player == null) {
               throw new CommandException("event.permissionDenied", new Object[0]);
            }

            if(event != null) {
               throw new CommandException("event.alreadyRegistered", new Object[0]);
            }

            if(strings.length < 2) {
               throw new CommandException("event.eventNameMissing", new Object[0]);
            }

            this.saveWarp(player, eventSaveData, strings[1]);
            iCommandSender.func_70006_a(ChatMessageComponent.func_111077_e("event.registered"));
            break;
         case 3:
            if(!isOp) {
               throw new CommandException("event.permissionDenied", new Object[0]);
            }

            if(event == null) {
               throw new CommandException("event.notRegistered", new Object[0]);
            }

            eventSaveData.removeEvent(event.getName());
            iCommandSender.func_70006_a(ChatMessageComponent.func_111077_e("event.unregistered"));
            break;
         case 4:
            iCommandSender.func_70006_a(ChatMessageComponent.func_111077_e("event.listHeader"));
            Iterator ngPlayerData = eventSaveData.getEvents().iterator();

            while(ngPlayerData.hasNext()) {
               EventSaveData$Event eventSaved = (EventSaveData$Event)ngPlayerData.next();
               iCommandSender.func_70006_a(ChatMessageComponent.func_111066_d(eventSaved.getName()));
            }
         }
      }

   }

   private boolean canBeTeleported(EntityPlayerMP player, int dimension, ChunkCoordinates coordinates) {
      Player bukkitPlayer = Bukkit.getServer().getPlayer(player.field_71092_bJ);
      Iterator var5 = Bukkit.getServer().getWorlds().iterator();

      World world;
      net.minecraft.world.World world1;
      do {
         if(!var5.hasNext()) {
            return false;
         }

         world = (World)var5.next();
         world1 = Bukkit2Forge.convertWorld(world);
      } while(world1.field_73011_w.field_76574_g != dimension);

      PlayerTeleportEvent playerTeleportEvent = new PlayerTeleportEvent(bukkitPlayer, bukkitPlayer.getLocation(), new Location(world, (double)coordinates.field_71574_a, (double)coordinates.field_71572_b, (double)coordinates.field_71573_c));
      Bukkit.getServer().getPluginManager().callEvent(playerTeleportEvent);
      return !playerTeleportEvent.isCancelled();
   }

   private void applyWarp(GCCorePlayerMP player, EventSaveData$Event event) {
      NationsGUI.teleportPlayerToLocation(player.field_71092_bJ, event.getDimensionId() + "", event.getEventCoordinates().field_71574_a, event.getEventCoordinates().field_71572_b, event.getEventCoordinates().field_71573_c, event.getRotationYaw(), event.getRotationPitch());
   }

   private void saveWarp(EntityPlayer player, EventSaveData worldData, String name) {
      EventSaveData$Event event = new EventSaveData$Event();
      event.setName(name);
      event.setDimensionId(player.field_71093_bK);
      event.setEventCoordinates(new ChunkCoordinates((int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v));
      event.setRotationYaw(player.field_70177_z);
      event.setRotationPitch(player.field_70125_A);
      worldData.putEvent(name, event);
   }

   private void savePlayerData(GCCorePlayerMP player) {
      NBTTagCompound saveStates = new NBTTagCompound();
      saveStates.func_74768_a("dimension", player.field_71093_bK);
      saveStates.func_74780_a("posX", player.field_70165_t);
      saveStates.func_74780_a("posY", player.field_70163_u);
      saveStates.func_74780_a("posZ", player.field_70161_v);
      saveStates.func_74776_a("rotationYaw", player.field_70177_z);
      saveStates.func_74776_a("rotationPitch", player.field_70125_A);
      saveStates.func_74768_a("experienceLevel", player.field_71068_ca);
      saveStates.func_74776_a("experience", player.field_71106_cc);
      NBTTagList inventory = new NBTTagList();
      player.field_71071_by.func_70442_a(inventory);
      saveStates.func_74782_a("inventory", inventory);
      NBTTagList inventoryExtended = new NBTTagList();
      player.getExtendedInventory().writeToNBT(inventoryExtended);
      saveStates.func_74782_a("inventoryExtended", inventoryExtended);
      NGPlayerData ngPlayerData = NGPlayerData.get(player);
      ngPlayerData.eventSave = saveStates;
   }

   private void clearPlayerData(GCCorePlayerMP player) {
      player.func_70674_bp();
      player.field_71068_ca = 0;
      player.field_71106_cc = 0.0F;
      player.field_71071_by.func_82347_b(-1, -1);
      ItemStack[] inventoryStacks = player.getExtendedInventory().inventoryStacks;
      int i = 0;

      for(int inventoryStacksLength = inventoryStacks.length; i < inventoryStacksLength; ++i) {
         inventoryStacks[i] = null;
      }

   }

   private void transferToDimension(EntityPlayerMP entityPlayer, int id) {
      entityPlayer.field_70170_p.func_72900_e(entityPlayer);
      entityPlayer.field_71133_b.func_71203_ab().func_72356_a(entityPlayer, id);
   }

   protected void restorePlayerData(GCCorePlayerMP player) {
      NGPlayerData ngPlayerData = NGPlayerData.get(player);
      NBTTagCompound eventSave = ngPlayerData.eventSave;
      if(eventSave == null) {
         throw new CommandException("event.noInEvent", new Object[0]);
      } else if(this.canBeTeleported(player, eventSave.func_74762_e("dimension"), new ChunkCoordinates((int)eventSave.func_74769_h("posX"), (int)eventSave.func_74769_h("posY"), (int)eventSave.func_74769_h("posZ")))) {
         this.clearPlayerData(player);
         NationsGUI.teleportPlayerToLocation(player.field_71092_bJ, eventSave.func_74762_e("dimension") + "", (int)eventSave.func_74769_h("posX"), (int)eventSave.func_74769_h("posY"), (int)eventSave.func_74769_h("posZ"), eventSave.func_74760_g("rotationYaw"), eventSave.func_74760_g("rotationPitch"));
         player.field_71071_by.func_70443_b(eventSave.func_74761_m("inventory"));
         player.getExtendedInventory().readFromNBT(eventSave.func_74761_m("inventoryExtended"));
         player.field_71068_ca = eventSave.func_74762_e("experienceLevel");
         player.field_71106_cc = eventSave.func_74760_g("experience");
         ngPlayerData.eventSave = null;
      }
   }

   public int compareTo(Object o) {
      return 0;
   }
}
