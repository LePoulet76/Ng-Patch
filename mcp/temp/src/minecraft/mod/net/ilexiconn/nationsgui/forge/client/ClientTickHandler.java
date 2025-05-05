package net.ilexiconn.nationsgui.forge.client;

import acs.tabbychat.GuiChatTC;
import acs.tabbychat.GuiNewChatTC;
import acs.tabbychat.TaggableChatLine;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.lang.Thread.State;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Timer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.ClientTickHandler$1;
import net.ilexiconn.nationsgui.forge.client.PingThread;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.WaitingSocketGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

public enum ClientTickHandler implements ITickHandler {

   INSTANCE("INSTANCE", 0);
   private PingThread pingThread;
   public static int tick;
   // $FF: synthetic field
   private static final ClientTickHandler[] $VALUES = new ClientTickHandler[]{INSTANCE};


   private ClientTickHandler(String var1, int var2) {}

   public void tickStart(EnumSet<TickType> type, Object ... tickData) {
      if(type.equals(EnumSet.of(TickType.CLIENT))) {
         ClientEventHandler.getInstance().getTitleOverlay().updateTimer();
      }

      if(type.equals(EnumSet.of(TickType.PLAYER))) {
         EntityPlayer scaledResolution = (EntityPlayer)tickData[0];
         boolean flag3 = (float)scaledResolution.func_71024_bL().func_75116_a() > 6.0F || scaledResolution.field_71075_bZ.field_75101_c;
         if(scaledResolution == Minecraft.func_71410_x().field_71439_g && (!scaledResolution.func_70051_ag() && Minecraft.func_71410_x().field_71439_g.field_71158_b.field_78900_b >= 0.8F && flag3 && ClientKeyHandler.KEY_SPRINT.field_74513_e || ClientKeyHandler.toggleSprintEnabled)) {
            scaledResolution.func_70031_b(true);
         }

         if(this.pingThread == null || this.pingThread.getState() == State.TERMINATED) {
            this.pingThread = new PingThread();
            this.pingThread.start();
         }
      } else if(type.equals(EnumSet.of(TickType.CLIENT))) {
         if(ClientEventHandler.getInstance().snackbarGUI == null && !ClientProxy.SNACKBAR_LIST.isEmpty()) {
            ClientEventHandler.getInstance().snackbarGUI = (SnackbarGUI)ClientProxy.SNACKBAR_LIST.get(0);
            ClientProxy.SNACKBAR_LIST.remove(ClientEventHandler.getInstance().snackbarGUI);
         }

         if(ClientEventHandler.getInstance().snackbarGUI != null) {
            ScaledResolution scaledResolution1 = new ScaledResolution(Minecraft.func_71410_x().field_71474_y, Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d);
            ClientEventHandler.getInstance().snackbarGUI.updateSnackbar(scaledResolution1, this.getMouseX(scaledResolution1), this.getMouseY(scaledResolution1));
         }
      }

   }

   public int getMouseX(ScaledResolution resolution) {
      return Mouse.getX() / resolution.func_78325_e();
   }

   public int getMouseY(ScaledResolution resolution) {
      return resolution.func_78328_b() - Mouse.getY() * resolution.func_78328_b() / Minecraft.func_71410_x().field_71440_d - 1;
   }

   public void tickEnd(EnumSet<TickType> type, Object ... tickData) {
      if(Minecraft.func_71410_x().field_71462_r != null && Minecraft.func_71410_x().field_71462_r instanceof GuiDisconnected && Minecraft.func_71410_x().field_71439_g != null && !Minecraft.func_71410_x().func_110432_I().func_111285_a().equalsIgnoreCase("mistersand") && !Minecraft.func_71410_x().func_110432_I().func_111285_a().equalsIgnoreCase("ibalix") && System.currentTimeMillis() - ClientData.lastPlayerWantDisconnect.longValue() > 10000L) {
         String curScreen = ClientProxy.currentServerName;
         if(ClientSocket.out != null) {
            ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST hub");
            ClientData.waitingJoinTime = Long.valueOf(System.currentTimeMillis());
            Minecraft.func_71410_x().func_71373_a(new WaitingSocketGui());
            if(ClientProxy.getServerIpAndPort(curScreen) != null) {
               (new Timer()).schedule(new ClientTickHandler$1(this, curScreen), 3000L);
            }
         }
      }

      if(type.equals(EnumSet.of(TickType.CLIENT))) {
         GuiScreen curScreen1 = Minecraft.func_71410_x().field_71462_r;
         if(curScreen1 != null) {
            this.onTickInGui(curScreen1);
            if(!ClientProxy.isPlayerOnServer()) {
               ClientProxy.currentServerName = "";
            }
         } else {
            this.onTickInGame();
         }
      }

   }

   public EnumSet<TickType> ticks() {
      return EnumSet.of(TickType.PLAYER, TickType.CLIENT);
   }

   public String getLabel() {
      return "nationsgui";
   }

   private void onTickInGame() {
      if(Minecraft.func_71410_x().field_71456_v.func_73827_b().getClass() == GuiNewChat.class) {
         try {
            Class e = GuiNewChat.class;
            Field chatLineField = e.getDeclaredFields()[3];
            chatLineField.setAccessible(true);
            ArrayList missedChats = TaggableChatLine.convertList((ArrayList)chatLineField.get(Minecraft.func_71410_x().field_71456_v.func_73827_b()));
            Class IngameGui = GuiIngame.class;
            Field persistantGuiField = IngameGui.getDeclaredFields()[6];
            persistantGuiField.setAccessible(true);
            persistantGuiField.set(Minecraft.func_71410_x().field_71456_v, GuiNewChatTC.me);
            GuiNewChatTC.me.addChatLines(missedChats);
         } catch (Throwable var6) {
            var6.printStackTrace();
            System.out.println("The current GUI mods are incompatible with TabbyChat");
         }
      } else if(Minecraft.func_71410_x().field_71456_v.func_73827_b().getClass() != GuiNewChatTC.class) {
         System.out.println("The current GUI mods are incompatible with TabbyChat - 2");
      }

   }

   private void onTickInGui(GuiScreen var3) {
      try {
         if(var3 != null && var3.getClass() == GuiChat.class) {
            Class e = GuiChat.class;
            Field inputField = e.getDeclaredFields()[7];
            inputField.setAccessible(true);
            String defText = ((GuiTextField)((GuiTextField)inputField.get((GuiChat)var3))).func_73781_b();
            Minecraft.func_71410_x().func_71373_a(new GuiChatTC(defText));
         }
      } catch (Exception var5) {
         var5.printStackTrace();
         System.out.println("The current GUI mods are incompatible with TabbyChat - 3");
      }

   }

}
