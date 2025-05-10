package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class BuildOverride extends Gui implements ElementOverride
{
    private DynamicTexture imageTexture;
    private String loadImageIslandId;
    public static HashMap<Long, String> cachedChronos = new HashMap();
    public static long lastSwitchNameAndDesc = 0L;
    public static boolean displayName = true;

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
        if (ClientProxy.serverType.equals("build") && !ClientData.currentIsland.isEmpty())
        {
            Double doubleY = Double.valueOf((double)resolution.getScaledHeight() * 0.4D);
            int y = doubleY.intValue();
            int x = resolution.getScaledWidth() - 120;
            drawRect(x, y, x + 120, y + 16, -1157627904);
            drawRect(x, y + 16, x + 120, y + 16 + 59, 1996488704);
            drawRect(x, y + 16 + 59, x + 120, y + 16 + 59 + 20 + 20, -1157627904);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(x + 5, y + -8, 0, 67, 32, 32);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if ((this.imageTexture == null || this.loadImageIslandId != null && !this.loadImageIslandId.equals(ClientData.currentIsland.get("id"))) && ClientData.currentIsland.containsKey("image"))
            {
                BufferedImage words = decodeToImage((String)ClientData.currentIsland.get("image"));
                this.imageTexture = new DynamicTexture(words);
                this.loadImageIslandId = (String)ClientData.currentIsland.get("id");
            }

            if (this.imageTexture != null)
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.imageTexture.getGlTextureId());
                ModernGui.drawScaledCustomSizeModalRect((float)(x + 6), (float)(y - 7), 0.0F, 0.0F, 102, 102, 30, 30, 102.0F, 102.0F, false);
            }

            this.drawSmallString(client.fontRenderer, I18n.getString("island.overlay.island") + " n\u00b0" + (String)ClientData.currentIsland.get("id"), x + 40, y + 4, 16777215);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            String var17;

            if (ClientData.currentJumpStartTime.longValue() != -1L && System.currentTimeMillis() - ClientData.currentJumpStartTime.longValue() < 5400000L)
            {
                this.drawScaledString(Minecraft.getMinecraft().fontRenderer, I18n.getString("island.overlay.jump_inprogress"), x + 5, y + 28, 16777215, 0.9F, false, false);
                String var16 = chronoTimeToStr(Long.valueOf(System.currentTimeMillis() - ClientData.currentJumpStartTime.longValue()), true);
                this.drawScaledString(Minecraft.getMinecraft().fontRenderer, I18n.getString("island.overlay.time") + " \u00a77" + var16, x + 5, y + 43, 16777215, 0.9F, false, false);
                var17 = "-";
                String var18 = "Aucun temps";

                if (!ClientData.currentJumpRecord.isEmpty())
                {
                    var17 = ClientData.currentJumpRecord.split(",")[0].split("#")[0];
                    var18 = ClientData.currentJumpRecord.split(",")[0].split("#")[1];
                }

                this.drawScaledString(Minecraft.getMinecraft().fontRenderer, "\u00a76Record: \u00a7e" + var17, x + 5, y + 58, 16777215, 0.9F, false, false);
                this.drawScaledString(Minecraft.getMinecraft().fontRenderer, "\u00a7d" + (this.isNumeric(var18) ? chronoTimeToStr(Long.valueOf(Long.parseLong(var18)), true) : var18), x + 5, y + 68, 16777215, 0.9F, false, false);
            }
            else
            {
                int lineNumber;

                if (ClientData.currentIsland.containsKey("team"))
                {
                    this.drawScaledString(Minecraft.getMinecraft().fontRenderer, I18n.getString("island.overlay.team") + " " + (String)ClientData.currentIsland.get("team") + " \u00a7e(" + (String)ClientData.currentIsland.get("score") + ")", x + 5, y + 28, 16777215, 0.9F, false, false);
                    List var14 = Arrays.asList(((String)ClientData.currentIsland.get("scores")).split(","));

                    for (int line = 0; line < var14.size() && line < 3; ++line)
                    {
                        lineNumber = line + 1;
                        this.drawScaledString(Minecraft.getMinecraft().fontRenderer, "\u00a7e" + lineNumber + " \u00a77> \u00a7r" + ((String)var14.get(line)).split("##")[0] + " \u00a76(" + ((String)var14.get(line)).split("##")[1] + ")", x + 5, y + 43 + line * 10, 16777215, 0.9F, false, false);
                    }
                }
                else
                {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                    if (System.currentTimeMillis() - lastSwitchNameAndDesc > 10000L)
                    {
                        lastSwitchNameAndDesc = System.currentTimeMillis();
                        displayName = !displayName;
                    }

                    String[] var15 = ((String)ClientData.currentIsland.get("description")).replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");

                    if (displayName)
                    {
                        var15 = ((String)ClientData.currentIsland.get("name")).replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");
                    }

                    var17 = "";
                    lineNumber = 0;
                    String[] var10 = var15;
                    int var11 = var15.length;

                    for (int var12 = 0; var12 < var11; ++var12)
                    {
                        String descWord = var10[var12];

                        if (Minecraft.getMinecraft().fontRenderer.getStringWidth(var17 + descWord) <= 120)
                        {
                            if (!var17.equals(""))
                            {
                                var17 = var17 + " ";
                            }

                            var17 = var17 + descWord;
                        }
                        else
                        {
                            if (lineNumber == 0 && !displayName)
                            {
                                var17 = "\u00a7o\"" + var17;
                            }

                            String var10002 = displayName ? "\u00a76" + var17 : var17;
                            this.drawScaledString(Minecraft.getMinecraft().fontRenderer, var10002, x + 60, y + 28 + lineNumber * 10, 16777215, 0.9F, true, false);
                            ++lineNumber;
                            var17 = descWord;
                        }
                    }

                    if (lineNumber == 0 && !displayName)
                    {
                        var17 = "\u00a7o\"" + var17;
                    }

                    this.drawScaledString(Minecraft.getMinecraft().fontRenderer, displayName ? "\u00a76" + var17 : var17 + "\"", x + 60, y + 28 + lineNumber * 10, 16777215, 0.9F, true, false);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.drawSmallString(client.fontRenderer, I18n.getString("island.overlay.by") + " " + (String)ClientData.currentIsland.get("creator"), x + 5, y + 65, 16777215);
                }
            }

            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(x + 13, y + 78, 33, 67, 94, 15);

            if (ClientData.currentJumpStartTime.longValue() == -1L)
            {
                this.drawScaledString(Minecraft.getMinecraft().fontRenderer, I18n.getString("island.overlay.infos"), x + 60, y + 83, 0, 0.8F, true, false);
            }
            else
            {
                this.drawScaledString(Minecraft.getMinecraft().fontRenderer, I18n.getString("island.overlay.stop_jump").replaceAll("<key>", Keyboard.getKeyName(ClientKeyHandler.KEY_WARZONE_LEAVE.keyCode)), x + 60, y + 83, 0, 0.8F, true, false);
            }

            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(x + 13, y + 98, 33, 67, 94, 15);
            this.drawScaledString(Minecraft.getMinecraft().fontRenderer, I18n.getString("island.overlay.list"), x + 60, y + 103, 0, 0.8F, true, false);
        }
    }

    private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, 0.0F);
        GL11.glScalef(0.95F, 0.95F, 0.95F);
        this.drawString(fontRenderer, string, 0, 0, 16777215);
        GL11.glPopMatrix();
    }

    private void drawVerySmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, 0.0F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.drawString(fontRenderer, string, 0, 0, 16777215);
        GL11.glPopMatrix();
    }

    public void drawScaledString(FontRenderer fontRenderer, String text, int x, int y, int color, float scale, boolean centered, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        float newX = (float)x;

        if (centered)
        {
            newX = (float)x - (float)fontRenderer.getStringWidth(text) * scale / 2.0F;
        }

        if (shadow)
        {
            fontRenderer.drawString(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
        }

        fontRenderer.drawString(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static BufferedImage decodeToImage(String imageString)
    {
        BufferedImage image = null;

        try
        {
            BASE64Decoder e = new BASE64Decoder();
            byte[] imageByte = e.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }

        return image;
    }

    public static String chronoTimeToStr(Long chrono, boolean millis)
    {
        if (cachedChronos.containsKey(chrono))
        {
            return (String)cachedChronos.get(chrono);
        }
        else
        {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(chrono.longValue());
            chrono = Long.valueOf(chrono.longValue() - TimeUnit.MINUTES.toMillis(minutes));
            long seconds = TimeUnit.MILLISECONDS.toSeconds(chrono.longValue());
            chrono = Long.valueOf(chrono.longValue() - TimeUnit.SECONDS.toMillis(seconds));
            long left = chrono.longValue() / 10L;
            String chronoStr = (minutes < 10L ? "0" + minutes : Long.valueOf(minutes)) + ":" + (seconds < 10L ? "0" + seconds : Long.valueOf(seconds)) + (millis ? ":" + (left < 10L ? "0" + left : Long.valueOf(left)) : "");
            cachedChronos.put(chrono, chronoStr);
            return chronoStr;
        }
    }

    public boolean isNumeric(String str)
    {
        if (str != null && str.length() != 0)
        {
            char[] var2 = str.toCharArray();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                char c = var2[var4];

                if (!Character.isDigit(c))
                {
                    return false;
                }
            }

            if (Integer.parseInt(str) < 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}
