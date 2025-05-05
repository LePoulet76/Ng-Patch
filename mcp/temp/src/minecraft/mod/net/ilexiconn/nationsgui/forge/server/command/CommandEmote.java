package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import net.ilexiconn.nationsgui.forge.server.entity.data.NGPlayerData;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PacketEmote;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.WorldServer;

public class CommandEmote extends CommandBase {

   public String func_71517_b() {
      return "emote";
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "<emote>";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if(args.length > 0) {
         Iterator player;
         File var15;
         NBTTagCompound var19;
         NBTTagCompound var23;
         NBTTagList var26;
         NBTTagList var27;
         if(args[0].equalsIgnoreCase("give") && args.length > 2) {
            if(CommandBase.func_82359_c(sender, args[1]) != null) {
               if(ServerProxy.emotesName.contains(args[2])) {
                  if(NGPlayerData.get(CommandBase.func_82359_c(sender, args[1])).addEmote(args[2])) {
                     sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Vous avez ajout\u00e9 l\'emote \'<emote>\' \u00e0 <player>.").replaceAll("<emote>", args[2]).replaceAll("<player>", args[1])));
                  } else {
                     sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Ce joueur a d\u00e9ja cette emote.")));
                  }
               } else if(args[2].equals("*")) {
                  player = ServerProxy.emotesName.iterator();

                  while(player.hasNext()) {
                     String var21 = (String)player.next();
                     NGPlayerData.get(CommandBase.func_82359_c(sender, args[1])).addEmote(var21);
                  }
               } else {
                  sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Cette emote n\'existe pas")));
               }
            } else {
               var15 = new File(".", "/world/players/" + args[0] + ".dat");
               if(!var15.exists()) {
                  sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("Le fichier du joueur n\'existe pas")));
                  return;
               }

               try {
                  var19 = CompressedStreamTools.func_74796_a(new FileInputStream(var15));
                  if(var19.func_74764_b("NGPlayerData")) {
                     var23 = var19.func_74775_l("NGPlayerData");
                     if(var23.func_74764_b("Emotes") && var23.func_74761_m("Emotes").func_74745_c() > 0) {
                        var26 = var23.func_74761_m("Emotes");
                        var27 = (NBTTagList)var26.func_74737_b();
                        var27.func_74742_a(new NBTTagString("Emote", args[2]));
                        var23.func_74782_a(var26.func_74740_e(), var27);
                     } else {
                        var26 = new NBTTagList("Emotes");
                        var26.func_74742_a(new NBTTagString("Emote", args[2]));
                        var23.func_74782_a(var26.func_74740_e(), var26);
                     }

                     var19.func_74782_a("NGPlayerData", var23);
                  }

                  CompressedStreamTools.func_74799_a(var19, new FileOutputStream(var15));
                  return;
               } catch (IOException var12) {
                  var12.printStackTrace();
               }
            }
         } else {
            Iterator var18;
            if(args[0].equalsIgnoreCase("get") && args.length > 1) {
               if(CommandBase.func_82359_c(sender, args[1]) != null) {
                  List var16 = NGPlayerData.get(CommandBase.func_82359_c(sender, args[1])).emotes;
                  sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Liste des emotes de ce joueur :")));
                  var18 = var16.iterator();

                  while(var18.hasNext()) {
                     String var25 = (String)var18.next();
                     sender.func_70006_a(ChatMessageComponent.func_111066_d("\u00a74[\u00a7cEmotes\u00a74] \u00a77- " + var25));
                  }
               } else {
                  sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Ce joueur n\'est pas connect\u00e9.")));
               }
            } else if(args[0].equalsIgnoreCase("remove") && args.length > 2) {
               if(CommandBase.func_82359_c(sender, args[1]) != null) {
                  if(ServerProxy.emotesName.contains(args[2])) {
                     if(NGPlayerData.get(CommandBase.func_82359_c(sender, args[1])).removeEmote(args[2])) {
                        sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Vous avez retir\u00e9 l\'emote \'<emote>\' de <player>.").replaceAll("<emote>", args[2]).replaceAll("<player>", args[1])));
                     } else {
                        sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Ce joueur n\'a pas cette emote.")));
                     }
                  } else {
                     sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Cette emote n\'existe pas")));
                  }
               } else {
                  var15 = new File(".", "/world/players/" + args[0] + ".dat");
                  if(!var15.exists()) {
                     sender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("Le fichier du joueur n\'existe pas")));
                     return;
                  }

                  try {
                     var19 = CompressedStreamTools.func_74796_a(new FileInputStream(var15));
                     if(var19.func_74764_b("NGPlayerData")) {
                        var23 = var19.func_74775_l("NGPlayerData");
                        if(var23.func_74764_b("Emotes") && var23.func_74761_m("Emotes").func_74745_c() > 0) {
                           var26 = var23.func_74761_m("Emotes");
                           var27 = new NBTTagList(var26.func_74740_e());

                           for(int var28 = 0; var28 < var26.func_74745_c(); ++var28) {
                              NBTTagString var29 = (NBTTagString)var26.func_74743_b(var28);
                              if(!var29.field_74751_a.equalsIgnoreCase(args[2])) {
                                 var27.func_74742_a(var29);
                              }
                           }

                           var23.func_74782_a(var26.func_74740_e(), var27);
                        }

                        var19.func_74782_a("NGPlayerData", var23);
                     }

                     CompressedStreamTools.func_74799_a(var19, new FileOutputStream(var15));
                     return;
                  } catch (IOException var11) {
                     var11.printStackTrace();
                  }
               }
            } else if(args[0].equalsIgnoreCase("play") && args.length > 1 && sender instanceof EntityPlayer) {
               EntityPlayer var14 = (EntityPlayer)sender;
               var18 = var14.field_70170_p.func_72872_a(var14.getClass(), AxisAlignedBB.func_72332_a().func_72299_a(var14.field_70165_t - 50.0D, var14.field_70163_u - 50.0D, var14.field_70161_v - 50.0D, var14.field_70165_t + 50.0D, var14.field_70163_u + 50.0D, var14.field_70161_v + 50.0D)).iterator();

               while(var18.hasNext()) {
                  Object var22 = var18.next();
                  if(var22 instanceof Player) {
                     PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PacketEmote(args[1], var14.field_71092_bJ)), (Player)var22);
                  }
               }
            } else if(args[0].equalsIgnoreCase("playallworlds") && args.length > 1) {
               WorldServer[] var13 = MinecraftServer.func_71276_C().field_71305_c;
               int var17 = var13.length;

               for(int var20 = 0; var20 < var17; ++var20) {
                  WorldServer var24 = var13[var20];
                  Iterator list1 = var24.field_73010_i.iterator();

                  while(list1.hasNext()) {
                     EntityPlayer player2 = (EntityPlayer)list1.next();
                     Iterator tag = player2.field_70170_p.func_72872_a(player2.getClass(), AxisAlignedBB.func_72332_a().func_72299_a(player2.field_70165_t - 50.0D, player2.field_70163_u - 50.0D, player2.field_70161_v - 50.0D, player2.field_70165_t + 50.0D, player2.field_70163_u + 50.0D, player2.field_70161_v + 50.0D)).iterator();

                     while(tag.hasNext()) {
                        Object e2 = tag.next();
                        if(e2 instanceof Player) {
                           PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PacketEmote(args[1], player2.field_71092_bJ)), (Player)e2);
                        }
                     }
                  }
               }
            } else if(args[0].equalsIgnoreCase("playall") && args.length > 1 && sender instanceof EntityPlayer) {
               player = ((EntityPlayer)sender).field_70170_p.field_73010_i.iterator();

               while(player.hasNext()) {
                  EntityPlayer player1 = (EntityPlayer)player.next();
                  Iterator e = player1.field_70170_p.func_72872_a(player1.getClass(), AxisAlignedBB.func_72332_a().func_72299_a(player1.field_70165_t - 50.0D, player1.field_70163_u - 50.0D, player1.field_70161_v - 50.0D, player1.field_70165_t + 50.0D, player1.field_70163_u + 50.0D, player1.field_70161_v + 50.0D)).iterator();

                  while(e.hasNext()) {
                     Object e1 = e.next();
                     if(e1 instanceof Player) {
                        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PacketEmote(args[1], player1.field_71092_bJ)), (Player)e1);
                     }
                  }
               }
            } else {
               sender.func_70006_a(ChatMessageComponent.func_111066_d("\u00a74[\u00a7cEmotes\u00a74] \u00a77/emote <give/get/remove/list/play/playallworlds/playall> [name] [emote]"));
            }
         }
      } else {
         sender.func_70006_a(ChatMessageComponent.func_111066_d("\u00a74[\u00a7cEmotes\u00a74] \u00a77/emote <give/get/remove/list/play/playallworlds/playall> [name] [emote]"));
      }

   }

   public int compareTo(Object o) {
      return 0;
   }
}
