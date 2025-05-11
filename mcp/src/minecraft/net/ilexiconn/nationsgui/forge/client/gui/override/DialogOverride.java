/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.DialogExecPacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class DialogOverride
extends Gui
implements ElementOverride {
    public static CFontRenderer dg28 = ModernGui.getCustomFont("minecraftDungeons", 28);
    public static String currentDialogIdentifier = null;
    public static int currentDialogSpeakerIndex = 0;
    public static long currentDialogLastSpeaker = 0L;
    public static int currentDialogLetterIndex = 0;
    public static long currentDialogLastLetter = 0L;
    public static boolean hasPlayedVoice = false;
    public long lastVoiceOverCheck = 0L;

    public static boolean displaysDialog() {
        return !ClientData.dialogs.isEmpty();
    }

    public static void resetDisplay() {
        currentDialogIdentifier = null;
        currentDialogSpeakerIndex = 0;
        currentDialogLetterIndex = 0;
        currentDialogLastLetter = 0L;
        currentDialogLastSpeaker = 0L;
        if (ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying()) {
            ClientProxy.commandPlayer.close();
        }
        hasPlayedVoice = false;
    }

    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return new RenderGameOverlayEvent.ElementType[0];
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB > 50L) {
            if (ClientData.dialogs.isEmpty()) {
                return;
            }
            HashMap<String, Object> dialogData = ClientData.dialogs.get(0);
            if (dialogData.get("command") != null && !dialogData.get("command").equals("") && (dialogData.get("content") == null || dialogData.get("content").equals(""))) {
                ClientData.dialogs.remove(0);
                DialogOverride.resetDisplay();
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new DialogExecPacket((String)dialogData.get("identifier"))));
                return;
            }
            int offsetXMiddle = resolution.func_78326_a() / 2;
            float offsetXNotif = (float)offsetXMiddle - 876.0f / (float)GenericOverride.GUI_SCALE / 2.0f - 25.0f;
            float offsetYNotif = resolution.func_78328_b() - 130;
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect(offsetXNotif, offsetYNotif, 221 * GenericOverride.GUI_SCALE, 148 * GenericOverride.GUI_SCALE, 876 * GenericOverride.GUI_SCALE, 239 * GenericOverride.GUI_SCALE, 876 / GenericOverride.GUI_SCALE, 239 / GenericOverride.GUI_SCALE, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            ModernGui.glColorHexFromNgColor((String)dialogData.get("color"), 1.0f);
            ModernGui.drawRectangle(offsetXNotif, offsetYNotif + 0.5f, 1.0f, 5.0f, 1.0f);
            ModernGui.drawRectangle(offsetXNotif, offsetYNotif + 0.5f, 1.0f, 1.0f, 5.0f);
            ModernGui.drawRectangle(offsetXNotif, offsetYNotif + (float)(239 / GenericOverride.GUI_SCALE) - 5.5f, 1.0f, 1.0f, 5.0f);
            ModernGui.drawRectangle(offsetXNotif, offsetYNotif + (float)(239 / GenericOverride.GUI_SCALE) - 1.5f, 1.0f, 5.0f, 1.0f);
            String title = (String)dialogData.get("title");
            if (title != null && !title.isEmpty()) {
                ModernGui.glColorHexFromNgColor((String)dialogData.get("color"), 1.0f);
                ModernGui.drawRoundedRectangle(offsetXNotif + 10.0f, offsetYNotif - 9.0f, 0.0f, 286 / GenericOverride.GUI_SCALE, 53 / GenericOverride.GUI_SCALE);
                ModernGui.drawScaledStringCustomFont(title, offsetXNotif + 10.0f + (float)(286 / GenericOverride.GUI_SCALE / 2), offsetYNotif - 7.0f, 0xDEDEED, 0.5f, "center", false, "minecraftDungeons", 30);
            }
            ClientEventHandler.STYLE.bindTexture("dialog_speaker_" + dialogData.get("spriteSpeaker"));
            ModernGui.drawScaledCustomSizeModalRect(offsetXNotif + 876.0f / (float)GenericOverride.GUI_SCALE - 72.0f, offsetYNotif - 65.0f, 432 * currentDialogSpeakerIndex, 0.0f, 432, 432, 432 / GenericOverride.GUI_SCALE, 432 / GenericOverride.GUI_SCALE, 4320.0f, 432.0f, true);
            float delay = 15.0f;
            Double speed = (Double)dialogData.get("dialogSpeedFactor");
            if (speed != 100.0) {
                delay = (float)((double)delay * (100.0 / speed));
            }
            if ((float)(System.currentTimeMillis() - currentDialogLastLetter) > delay) {
                if (currentDialogLetterIndex == ((String)dialogData.get("content")).length()) {
                    if ((dialogData.get("voiceOverUrl") == null || System.currentTimeMillis() - currentDialogLastLetter > 2000L) && ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying()) {
                        ClientProxy.commandPlayer.close();
                    }
                    if (System.currentTimeMillis() - currentDialogLastLetter > (long)(((String)dialogData.get("content")).length() * 30)) {
                        ClientData.dialogs.remove(0);
                        DialogOverride.resetDisplay();
                        if (dialogData.get("command") != null) {
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new DialogExecPacket((String)dialogData.get("identifier"))));
                        }
                    }
                } else {
                    currentDialogLastLetter = System.currentTimeMillis();
                    currentDialogLetterIndex = Math.min(currentDialogLetterIndex + 1, ((String)dialogData.get("content")).length());
                    if (!(hasPlayedVoice || ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying())) {
                        hasPlayedVoice = true;
                        ClientProxy.commandPlayer = new SoundStreamer(dialogData.get("voiceOverUrl") != null && !((String)dialogData.get("voiceOverUrl")).isEmpty() ? (String)dialogData.get("voiceOverUrl") : "https://static.nationsglory.fr/N363y26Ny4.mp3");
                        ClientProxy.commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.15f);
                        new Thread(ClientProxy.commandPlayer).start();
                    }
                }
            }
            if (System.currentTimeMillis() - currentDialogLastSpeaker > 350L) {
                if (currentDialogLetterIndex < ((String)dialogData.get("content")).length()) {
                    currentDialogLastSpeaker = System.currentTimeMillis();
                    currentDialogSpeakerIndex = currentDialogSpeakerIndex == 9 ? 0 : currentDialogSpeakerIndex + 1;
                } else {
                    currentDialogSpeakerIndex = 0;
                }
            }
            String textToDiplay = ((String)dialogData.get("content")).substring(0, currentDialogLetterIndex);
            ModernGui.drawSectionStringCustomFont(textToDiplay, offsetXNotif + 10.0f, offsetYNotif + 16.0f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 26, 8, 400);
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect(offsetXNotif + 180.0f, offsetYNotif + 62.0f, 1108 * GenericOverride.GUI_SCALE, 178 * GenericOverride.GUI_SCALE, 112 * GenericOverride.GUI_SCALE, 26 * GenericOverride.GUI_SCALE, 112 / GenericOverride.GUI_SCALE, 26 / GenericOverride.GUI_SCALE, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            String label = I18n.func_135053_a((String)"overlay.dialog.button.label.skip");
            if (currentDialogLetterIndex == ((String)dialogData.get("content")).length()) {
                label = ClientData.dialogs.size() > 1 ? I18n.func_135053_a((String)"overlay.dialog.button.label.next") : I18n.func_135053_a((String)"overlay.dialog.button.label.close");
            }
            ModernGui.drawScaledStringCustomFont(label.toUpperCase(), offsetXNotif + 180.0f + (float)(112 / GenericOverride.GUI_SCALE / 2) - 3.0f, offsetYNotif + 63.0f, 0xDEDEED, 0.25f, "center", false, "minecraftDungeons", 30);
        }
    }
}

