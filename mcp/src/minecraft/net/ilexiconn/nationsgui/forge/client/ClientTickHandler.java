/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.ITickHandler
 *  cpw.mods.fml.common.TickType
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.GuiDisconnected
 *  net.minecraft.client.gui.GuiIngame
 *  net.minecraft.client.gui.GuiNewChat
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.entity.player.EntityPlayer
 *  org.lwjgl.input.Mouse
 */
package net.ilexiconn.nationsgui.forge.client;

import acs.tabbychat.GuiChatTC;
import acs.tabbychat.GuiNewChatTC;
import acs.tabbychat.TaggableChatLine;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.PingThread;
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

public enum ClientTickHandler implements ITickHandler
{
    INSTANCE;

    private PingThread pingThread;
    public static int tick;

    public void tickStart(EnumSet<TickType> type, Object ... tickData) {
        if (type.equals(EnumSet.of(TickType.CLIENT))) {
            ClientEventHandler.getInstance().getTitleOverlay().updateTimer();
        }
        if (type.equals(EnumSet.of(TickType.PLAYER))) {
            boolean flag3;
            EntityPlayer player = (EntityPlayer)tickData[0];
            boolean bl = flag3 = (float)player.func_71024_bL().func_75116_a() > 6.0f || player.field_71075_bZ.field_75101_c;
            if (player == Minecraft.func_71410_x().field_71439_g && (!player.func_70051_ag() && Minecraft.func_71410_x().field_71439_g.field_71158_b.field_78900_b >= 0.8f && flag3 && ClientKeyHandler.KEY_SPRINT.field_74513_e || ClientKeyHandler.toggleSprintEnabled)) {
                player.func_70031_b(true);
            }
            if (this.pingThread == null || this.pingThread.getState() == Thread.State.TERMINATED) {
                this.pingThread = new PingThread();
                this.pingThread.start();
            }
        } else if (type.equals(EnumSet.of(TickType.CLIENT))) {
            if (ClientEventHandler.getInstance().snackbarGUI == null && !ClientProxy.SNACKBAR_LIST.isEmpty()) {
                ClientEventHandler.getInstance().snackbarGUI = ClientProxy.SNACKBAR_LIST.get(0);
                ClientProxy.SNACKBAR_LIST.remove((Object)ClientEventHandler.getInstance().snackbarGUI);
            }
            if (ClientEventHandler.getInstance().snackbarGUI != null) {
                ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x().field_71474_y, Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d);
                ClientEventHandler.getInstance().snackbarGUI.updateSnackbar(scaledResolution, this.getMouseX(scaledResolution), this.getMouseY(scaledResolution));
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
        if (Minecraft.func_71410_x().field_71462_r != null && Minecraft.func_71410_x().field_71462_r instanceof GuiDisconnected && Minecraft.func_71410_x().field_71439_g != null && !Minecraft.func_71410_x().func_110432_I().func_111285_a().equalsIgnoreCase("mistersand") && !Minecraft.func_71410_x().func_110432_I().func_111285_a().equalsIgnoreCase("ibalix") && System.currentTimeMillis() - ClientData.lastPlayerWantDisconnect > 10000L) {
            final String serverConnectOn = ClientProxy.currentServerName;
            if (ClientSocket.out != null) {
                ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST hub");
                ClientData.waitingJoinTime = System.currentTimeMillis();
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new WaitingSocketGui());
                if (ClientProxy.getServerIpAndPort(serverConnectOn) != null) {
                    new Timer().schedule(new TimerTask(){

                        @Override
                        public void run() {
                            ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + serverConnectOn);
                            ClientData.waitingJoinTime = System.currentTimeMillis();
                        }
                    }, 3000L);
                }
            }
        }
        if (type.equals(EnumSet.of(TickType.CLIENT))) {
            GuiScreen curScreen = Minecraft.func_71410_x().field_71462_r;
            if (curScreen != null) {
                this.onTickInGui(curScreen);
                if (!ClientProxy.isPlayerOnServer()) {
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
        if (Minecraft.func_71410_x().field_71456_v.func_73827_b().getClass() == GuiNewChat.class) {
            try {
                Class<GuiNewChat> newChatGui = GuiNewChat.class;
                Field chatLineField = newChatGui.getDeclaredFields()[3];
                chatLineField.setAccessible(true);
                ArrayList<TaggableChatLine> missedChats = TaggableChatLine.convertList((ArrayList)chatLineField.get(Minecraft.func_71410_x().field_71456_v.func_73827_b()));
                Class<GuiIngame> IngameGui = GuiIngame.class;
                Field persistantGuiField = IngameGui.getDeclaredFields()[6];
                persistantGuiField.setAccessible(true);
                persistantGuiField.set(Minecraft.func_71410_x().field_71456_v, (Object)GuiNewChatTC.me);
                GuiNewChatTC.me.addChatLines(missedChats);
            }
            catch (Throwable e) {
                e.printStackTrace();
                System.out.println("The current GUI mods are incompatible with TabbyChat");
            }
        } else if (Minecraft.func_71410_x().field_71456_v.func_73827_b().getClass() != GuiNewChatTC.class) {
            System.out.println("The current GUI mods are incompatible with TabbyChat - 2");
        }
    }

    private void onTickInGui(GuiScreen var3) {
        try {
            if (var3 != null && var3.getClass() == GuiChat.class) {
                Class<GuiChat> guiChat = GuiChat.class;
                Field inputField = guiChat.getDeclaredFields()[7];
                inputField.setAccessible(true);
                String defText = ((GuiTextField)inputField.get((GuiChat)var3)).func_73781_b();
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new GuiChatTC(defText));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("The current GUI mods are incompatible with TabbyChat - 3");
        }
    }
}

