package net.ilexiconn.nationsgui.forge.client.gui.override;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.DialogExecPacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class DialogOverride extends Gui implements ElementOverride
{
    public static CFontRenderer dg28 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(28));
    public static String currentDialogIdentifier = null;
    public static int currentDialogSpeakerIndex = 0;
    public static long currentDialogLastSpeaker = 0L;
    public static int currentDialogLetterIndex = 0;
    public static long currentDialogLastLetter = 0L;
    public static boolean hasPlayedVoice = false;
    public long lastVoiceOverCheck = 0L;

    public static boolean displaysDialog()
    {
        return !ClientData.dialogs.isEmpty();
    }

    public static void resetDisplay()
    {
        currentDialogIdentifier = null;
        currentDialogSpeakerIndex = 0;
        currentDialogLetterIndex = 0;
        currentDialogLastLetter = 0L;
        currentDialogLastSpeaker = 0L;

        if (ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying())
        {
            ClientProxy.commandPlayer.close();
        }

        hasPlayedVoice = false;
    }

    public ElementType getType()
    {
        return ElementType.HOTBAR;
    }

    public ElementType[] getSubTypes()
    {
        return new ElementType[0];
    }

    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks)
    {
        if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB.longValue() > 50L)
        {
            if (ClientData.dialogs.isEmpty())
            {
                return;
            }

            HashMap dialogData = (HashMap)ClientData.dialogs.get(0);

            if (dialogData.get("command") != null && !dialogData.get("command").equals("") && (dialogData.get("content") == null || dialogData.get("content").equals("")))
            {
                ClientData.dialogs.remove(0);
                resetDisplay();
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new DialogExecPacket((String)dialogData.get("identifier"))));
                return;
            }

            int offsetXMiddle = resolution.getScaledWidth() / 2;
            float offsetXNotif = (float)offsetXMiddle - 876.0F / (float)GenericOverride.GUI_SCALE / 2.0F - 25.0F;
            float offsetYNotif = (float)(resolution.getScaledHeight() - 130);
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect(offsetXNotif, offsetYNotif, (float)(221 * GenericOverride.GUI_SCALE), (float)(148 * GenericOverride.GUI_SCALE), 876 * GenericOverride.GUI_SCALE, 239 * GenericOverride.GUI_SCALE, 876 / GenericOverride.GUI_SCALE, 239 / GenericOverride.GUI_SCALE, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            ModernGui.glColorHexFromNgColor((String)dialogData.get("color"), 1.0F);
            ModernGui.drawRectangle(offsetXNotif, offsetYNotif + 0.5F, 1.0F, 5.0F, 1.0F);
            ModernGui.drawRectangle(offsetXNotif, offsetYNotif + 0.5F, 1.0F, 1.0F, 5.0F);
            ModernGui.drawRectangle(offsetXNotif, offsetYNotif + (float)(239 / GenericOverride.GUI_SCALE) - 5.5F, 1.0F, 1.0F, 5.0F);
            ModernGui.drawRectangle(offsetXNotif, offsetYNotif + (float)(239 / GenericOverride.GUI_SCALE) - 1.5F, 1.0F, 5.0F, 1.0F);
            String title = (String)dialogData.get("title");

            if (title != null && !title.isEmpty())
            {
                ModernGui.glColorHexFromNgColor((String)dialogData.get("color"), 1.0F);
                ModernGui.drawRoundedRectangle(offsetXNotif + 10.0F, offsetYNotif - 9.0F, 0.0F, (float)(286 / GenericOverride.GUI_SCALE), (float)(53 / GenericOverride.GUI_SCALE));
                ModernGui.drawScaledStringCustomFont(title, offsetXNotif + 10.0F + (float)(286 / GenericOverride.GUI_SCALE / 2), offsetYNotif - 7.0F, 14606061, 0.5F, "center", false, "minecraftDungeons", 30);
            }

            ClientEventHandler.STYLE.bindTexture("dialog_speaker_" + dialogData.get("spriteSpeaker"));
            ModernGui.drawScaledCustomSizeModalRect(offsetXNotif + 876.0F / (float)GenericOverride.GUI_SCALE - 72.0F, offsetYNotif - 65.0F, (float)(432 * currentDialogSpeakerIndex), 0.0F, 432, 432, 432 / GenericOverride.GUI_SCALE, 432 / GenericOverride.GUI_SCALE, 4320.0F, 432.0F, true);
            float delay = 15.0F;
            Double speed = (Double)dialogData.get("dialogSpeedFactor");

            if (speed.doubleValue() != 100.0D)
            {
                delay = (float)((double)delay * (100.0D / speed.doubleValue()));
            }

            if ((float)(System.currentTimeMillis() - currentDialogLastLetter) > delay)
            {
                if (currentDialogLetterIndex == ((String)dialogData.get("content")).length())
                {
                    if ((dialogData.get("voiceOverUrl") == null || System.currentTimeMillis() - currentDialogLastLetter > 2000L) && ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying())
                    {
                        ClientProxy.commandPlayer.close();
                    }

                    if (System.currentTimeMillis() - currentDialogLastLetter > (long)(((String)dialogData.get("content")).length() * 30))
                    {
                        ClientData.dialogs.remove(0);
                        resetDisplay();

                        if (dialogData.get("command") != null)
                        {
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new DialogExecPacket((String)dialogData.get("identifier"))));
                        }
                    }
                }
                else
                {
                    currentDialogLastLetter = System.currentTimeMillis();
                    currentDialogLetterIndex = Math.min(currentDialogLetterIndex + 1, ((String)dialogData.get("content")).length());

                    if (!hasPlayedVoice && (ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying()))
                    {
                        hasPlayedVoice = true;
                        ClientProxy.commandPlayer = new SoundStreamer(dialogData.get("voiceOverUrl") != null && !((String)dialogData.get("voiceOverUrl")).isEmpty() ? (String)dialogData.get("voiceOverUrl") : "https://static.nationsglory.fr/N363y26Ny4.mp3");
                        ClientProxy.commandPlayer.setVolume(Minecraft.getMinecraft().gameSettings.soundVolume * 0.15F);
                        (new Thread(ClientProxy.commandPlayer)).start();
                    }
                }
            }

            if (System.currentTimeMillis() - currentDialogLastSpeaker > 350L)
            {
                if (currentDialogLetterIndex < ((String)dialogData.get("content")).length())
                {
                    currentDialogLastSpeaker = System.currentTimeMillis();
                    currentDialogSpeakerIndex = currentDialogSpeakerIndex == 9 ? 0 : currentDialogSpeakerIndex + 1;
                }
                else
                {
                    currentDialogSpeakerIndex = 0;
                }
            }

            String textToDiplay = ((String)dialogData.get("content")).substring(0, currentDialogLetterIndex);
            ModernGui.drawSectionStringCustomFont(textToDiplay, offsetXNotif + 10.0F, offsetYNotif + 16.0F, 14606061, 0.5F, "left", false, "georamaSemiBold", 26, 8, 400);
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect(offsetXNotif + 180.0F, offsetYNotif + 62.0F, (float)(1108 * GenericOverride.GUI_SCALE), (float)(178 * GenericOverride.GUI_SCALE), 112 * GenericOverride.GUI_SCALE, 26 * GenericOverride.GUI_SCALE, 112 / GenericOverride.GUI_SCALE, 26 / GenericOverride.GUI_SCALE, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            String label = I18n.getString("overlay.dialog.button.label.skip");

            if (currentDialogLetterIndex == ((String)dialogData.get("content")).length())
            {
                if (ClientData.dialogs.size() > 1)
                {
                    label = I18n.getString("overlay.dialog.button.label.next");
                }
                else
                {
                    label = I18n.getString("overlay.dialog.button.label.close");
                }
            }

            ModernGui.drawScaledStringCustomFont(label.toUpperCase(), offsetXNotif + 180.0F + (float)(112 / GenericOverride.GUI_SCALE / 2) - 3.0F, offsetYNotif + 63.0F, 14606061, 0.25F, "center", false, "minecraftDungeons", 30);
        }
    }
}
