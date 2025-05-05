package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.Player;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager$NColor;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager$NIcon;
import net.ilexiconn.nationsgui.forge.server.util.CommandUtils;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;

public class NotifyCommand extends CommandBase {

   public String func_71517_b() {
      return "notify";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return null;
   }

   public void func_71515_b(ICommandSender icommandsender, String[] astring) {
      int color;
      if(astring.length == 1) {
         int var21;
         if(astring[0].equalsIgnoreCase("colors")) {
            icommandsender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7eCouleur disponibles:")));
            NotificationManager$NColor[] var18 = NotificationManager$NColor.values();
            color = var18.length;

            for(var21 = 0; var21 < color; ++var21) {
               NotificationManager$NColor var22 = var18[var21];
               icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("\u00a77 - " + var22.name()));
            }
         } else if(astring[0].equalsIgnoreCase("icons")) {
            icommandsender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7eIcones disponibles:")));
            NotificationManager$NIcon[] var19 = NotificationManager$NIcon.values();
            color = var19.length;

            for(var21 = 0; var21 < color; ++var21) {
               NotificationManager$NIcon var23 = var19[var21];
               icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("\u00a77 - " + var23.name()));
            }
         }

      } else {
         if(astring.length >= 4) {
            StringBuilder content = new StringBuilder();

            for(color = 4; color < astring.length; ++color) {
               content.append(astring[color]);
               content.append(" ");
            }

            NotificationManager$NColor var20 = this.getColorFromString(astring[1]);
            NotificationManager$NIcon icon = this.getIconFromString(astring[2]);
            String title = astring[3].replaceAll("_", " ");
            if(var20 != null && icon != null) {
               if(astring.length >= 10) {
                  String radius = astring[4].replaceAll("_", " ");
                  String player = astring[5];
                  String i = astring[6].replaceAll("_", " ");
                  String o = astring[7].replaceAll("_", " ");
                  String o1 = astring[8];
                  String denyargsAction = astring[9].replaceAll("_", " ");
                  content = new StringBuilder();

                  int radius1;
                  for(radius1 = 10; radius1 < astring.length; ++radius1) {
                     content.append(astring[radius1]);
                     content.append(" ");
                  }

                  if(astring[0].startsWith("@")) {
                     TileEntityCommandBlock player1;
                     EntityPlayer var36;
                     if(astring[0].startsWith("@a")) {
                        if(NationsGUI.isRadiusSet(astring[0])) {
                           radius1 = NationsGUI.getRadius(astring[0]);
                           Iterator i1;
                           Object o2;
                           if(icommandsender instanceof TileEntityCommandBlock) {
                              player1 = (TileEntityCommandBlock)icommandsender;
                              i1 = player1.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(player1.field_70329_l - radius1), (double)(player1.field_70330_m - radius1), (double)(player1.field_70327_n - radius1), (double)(player1.field_70329_l + radius1), (double)(player1.field_70330_m + radius1), (double)(player1.field_70327_n + radius1))).iterator();

                              while(i1.hasNext()) {
                                 o2 = i1.next();
                                 this.applyNotificationAction(icommandsender, ((EntityPlayer)o2).field_71092_bJ, title, content, var20, icon, player, radius, o1, o, denyargsAction, i);
                              }
                           } else if(icommandsender instanceof EntityPlayer) {
                              var36 = (EntityPlayer)icommandsender;
                              i1 = ((EntityPlayer)icommandsender).field_70170_p.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a(var36.field_70165_t - (double)radius1, var36.field_70163_u - (double)radius1, var36.field_70161_v - (double)radius1, var36.field_70165_t + (double)radius1, var36.field_70163_u + (double)radius1, var36.field_70161_v + (double)radius1)).iterator();

                              while(i1.hasNext()) {
                                 o2 = i1.next();
                                 this.applyNotificationAction(icommandsender, ((EntityPlayer)o2).field_71092_bJ, title, content, var20, icon, player, radius, o1, o, denyargsAction, i);
                              }
                           }
                        } else {
                           Iterator var34 = CommandUtils.getAllPlayers().iterator();

                           while(var34.hasNext()) {
                              var36 = (EntityPlayer)var34.next();
                              this.applyNotificationAction(icommandsender, var36.field_71092_bJ, title, content, var20, icon, player, radius, o1, o, denyargsAction, i);
                           }
                        }
                     } else if(astring[0].startsWith("@p")) {
                        byte var35 = 5;
                        Object o3;
                        int var37;
                        Iterator var38;
                        if(icommandsender instanceof TileEntityCommandBlock) {
                           player1 = (TileEntityCommandBlock)icommandsender;
                           var37 = 0;
                           var38 = player1.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(player1.field_70329_l - var35), (double)(player1.field_70330_m - var35), (double)(player1.field_70327_n - var35), (double)(player1.field_70329_l + var35), (double)(player1.field_70330_m + var35), (double)(player1.field_70327_n + var35))).iterator();

                           while(var38.hasNext()) {
                              o3 = var38.next();
                              if(var37 <= 0) {
                                 this.applyNotificationAction(icommandsender, ((EntityPlayer)o3).field_71092_bJ, title, content, var20, icon, player, radius, o1, o, denyargsAction, i);
                                 ++var37;
                              }
                           }
                        } else if(icommandsender instanceof EntityPlayer) {
                           var36 = (EntityPlayer)icommandsender;
                           var37 = 0;
                           var38 = ((EntityPlayer)icommandsender).field_70170_p.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a(var36.field_70165_t - (double)var35, var36.field_70163_u - (double)var35, var36.field_70161_v - (double)var35, var36.field_70165_t + (double)var35, var36.field_70163_u + (double)var35, var36.field_70161_v + (double)var35)).iterator();

                           while(var38.hasNext()) {
                              o3 = var38.next();
                              if(var37 <= 0) {
                                 this.applyNotificationAction(icommandsender, ((EntityPlayer)o3).field_71092_bJ, title, content, var20, icon, player, radius, o1, o, denyargsAction, i);
                                 ++var37;
                              }
                           }
                        }
                     }
                  } else if(CommandUtils.isPlayerOnline(astring[0])) {
                     this.applyNotificationAction(icommandsender, astring[0], title, content, var20, icon, player, radius, o1, o, denyargsAction, i);
                  } else {
                     icommandsender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7cLe joueur n\'est pas connect\u00e9")));
                  }
               } else if(astring[0].startsWith("@")) {
                  TileEntityCommandBlock var27;
                  EntityPlayer var28;
                  if(astring[0].startsWith("@a")) {
                     if(NationsGUI.isRadiusSet(astring[0])) {
                        int var24 = NationsGUI.getRadius(astring[0]);
                        Iterator var29;
                        Object var31;
                        if(icommandsender instanceof TileEntityCommandBlock) {
                           var27 = (TileEntityCommandBlock)icommandsender;
                           var29 = var27.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(var27.field_70329_l - var24), (double)(var27.field_70330_m - var24), (double)(var27.field_70327_n - var24), (double)(var27.field_70329_l + var24), (double)(var27.field_70330_m + var24), (double)(var27.field_70327_n + var24))).iterator();

                           while(var29.hasNext()) {
                              var31 = var29.next();
                              this.applyNotificationSimple(icommandsender, ((EntityPlayer)var31).field_71092_bJ, title, content, var20, icon);
                           }
                        } else if(icommandsender instanceof EntityPlayer) {
                           var28 = (EntityPlayer)icommandsender;
                           var29 = ((EntityPlayer)icommandsender).field_70170_p.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a(var28.field_70165_t - (double)var24, var28.field_70163_u - (double)var24, var28.field_70161_v - (double)var24, var28.field_70165_t + (double)var24, var28.field_70163_u + (double)var24, var28.field_70161_v + (double)var24)).iterator();

                           while(var29.hasNext()) {
                              var31 = var29.next();
                              this.applyNotificationSimple(icommandsender, ((EntityPlayer)var31).field_71092_bJ, title, content, var20, icon);
                           }
                        }
                     } else {
                        Iterator var25 = CommandUtils.getAllPlayers().iterator();

                        while(var25.hasNext()) {
                           var28 = (EntityPlayer)var25.next();
                           this.applyNotificationSimple(icommandsender, var28.field_71092_bJ, title, content, var20, icon);
                        }
                     }
                  } else if(astring[0].startsWith("@p")) {
                     byte var26 = 5;
                     int var30;
                     Iterator var32;
                     Object var33;
                     if(icommandsender instanceof TileEntityCommandBlock) {
                        var27 = (TileEntityCommandBlock)icommandsender;
                        var30 = 0;
                        var32 = var27.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(var27.field_70329_l - var26), (double)(var27.field_70330_m - var26), (double)(var27.field_70327_n - var26), (double)(var27.field_70329_l + var26), (double)(var27.field_70330_m + var26), (double)(var27.field_70327_n + var26))).iterator();

                        while(var32.hasNext()) {
                           var33 = var32.next();
                           if(var30 <= 0) {
                              this.applyNotificationSimple(icommandsender, ((EntityPlayer)var33).field_71092_bJ, title, content, var20, icon);
                              ++var30;
                           }
                        }
                     } else if(icommandsender instanceof EntityPlayer) {
                        var28 = (EntityPlayer)icommandsender;
                        var30 = 0;
                        var32 = ((EntityPlayer)icommandsender).field_70170_p.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a(var28.field_70165_t - (double)var26, var28.field_70163_u - (double)var26, var28.field_70161_v - (double)var26, var28.field_70165_t + (double)var26, var28.field_70163_u + (double)var26, var28.field_70161_v + (double)var26)).iterator();

                        while(var32.hasNext()) {
                           var33 = var32.next();
                           if(var30 <= 0) {
                              this.applyNotificationSimple(icommandsender, ((EntityPlayer)var33).field_71092_bJ, title, content, var20, icon);
                              ++var30;
                           }
                        }
                     }
                  }
               } else if(CommandUtils.isPlayerOnline(astring[0])) {
                  this.applyNotificationSimple(icommandsender, astring[0], title, content, var20, icon);
               } else {
                  icommandsender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7cLe joueur n\'est pas connect\u00e9")));
               }

               icommandsender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7aNotification envoy\u00e9e")));
               return;
            }
         }

         icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("\u00a7cUsage: /notify <player> <color> <icon> <title> <titleActionYes?> <idActionYes?> <argsActionYes?> <titleActionNo?> <idActionNo?> <argsActionNo?> <content>"));
      }
   }

   private NotificationManager$NColor getColorFromString(String arg) {
      try {
         return NotificationManager$NColor.valueOf(arg.toUpperCase());
      } catch (IllegalArgumentException var3) {
         return null;
      }
   }

   private NotificationManager$NIcon getIconFromString(String arg) {
      try {
         return NotificationManager$NIcon.valueOf(arg.toUpperCase());
      } catch (IllegalArgumentException var3) {
         return null;
      }
   }

   private void applyNotificationSimple(ICommandSender icommandsender, String target, String title, StringBuilder content, NotificationManager$NColor color, NotificationManager$NIcon icon) {
      NotificationManager.sendNotificationToPlayer((Player)func_82359_c(icommandsender, target), title, content.toString(), color, icon, 10000L);
   }

   private void applyNotificationAction(ICommandSender icommandsender, String target, String title, StringBuilder content, NotificationManager$NColor color, NotificationManager$NIcon icon, String okIdAction, String okMessage, String denyIdAction, String denyMessage, String denyargsAction, String okargsAction) {
      if(okIdAction.equals("link") && !okargsAction.contains("https://static.nationsglory.fr") && !okargsAction.contains("https://glor.cc")) {
         icommandsender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7cMerci d\'utiliser uniquement des liens provenant du panel admin")));
      } else if(denyIdAction.equals("link") && !denyargsAction.contains("https://static.nationsglory.fr") && !denyargsAction.contains("https://glor.cc")) {
         icommandsender.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7cMerci d\'utiliser uniquement des liens provenant du panel admin")));
      } else {
         NotificationManager.sendNotificationToPlayer((Player)func_82359_c(icommandsender, target), title, content.toString(), color, icon, 10000L, okIdAction, okMessage, denyIdAction, denyMessage, denyargsAction, okargsAction);
      }
   }

   public int compareTo(Object o) {
      return 0;
   }
}
