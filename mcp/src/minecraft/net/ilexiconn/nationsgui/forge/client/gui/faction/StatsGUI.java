package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerStatsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class StatsGUI extends GuiScreen
{
    protected int xSize = 299;
    protected int ySize = 217;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    private RenderItem itemRenderer = new RenderItem();
    public static HashMap<String, String> statsInfos = new HashMap();
    private String targetName;

    public StatsGUI(String targetName)
    {
        this.targetName = targetName;
        loaded = false;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerStatsDataPacket(this.targetName)));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        ClientEventHandler.STYLE.bindTexture("player_stats");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (mouseX >= this.guiLeft + 260 && mouseX <= this.guiLeft + 260 + 9 && mouseY >= this.guiTop - 4 && mouseY <= this.guiTop - 4 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 260), (float)(this.guiTop - 4), 0, 229, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 260), (float)(this.guiTop - 4), 0, 219, 9, 10, 512.0F, 512.0F, false);
        }

        this.drawScaledString(I18n.getString("faction.stats.title") + " " + this.targetName, this.guiLeft + 51, this.guiTop + 17, 1644825, 1.2F, false, false);
        ClientEventHandler.STYLE.bindTexture("player_stats");

        if (loaded && statsInfos != null && statsInfos.size() > 0)
        {
            int i = 0;
            Iterator it = statsInfos.entrySet().iterator();

            while (it.hasNext())
            {
                Entry pair = (Entry)it.next();

                if (pair != null)
                {
                    int offsetY = this.guiTop + 37 + 16 * i;
                    int offsetX = this.guiLeft + 56;
                    this.drawScaledString(I18n.getString("faction.stats.type." + pair.getKey().toString().toLowerCase()), offsetX, offsetY, 11842740, 1.0F, false, false);

                    if (pair.getValue() != null)
                    {
                        String value = pair.getValue().toString();
                        String date;
                        long diff;

                        if (pair.getKey().toString().equals("PLAYTIME"))
                        {
                            date = "";
                            diff = Long.parseLong(pair.getValue().toString()) * 1000L;
                            long days = diff / 86400000L;
                            long hours = 0L;
                            long minutes = 0L;
                            long seconds = 0L;

                            if (days == 0L)
                            {
                                hours = diff / 3600000L;

                                if (hours == 0L)
                                {
                                    minutes = diff / 60000L;

                                    if (minutes == 0L)
                                    {
                                        seconds = diff / 1000L;
                                        date = date + " " + seconds + " " + I18n.getString("faction.common.seconds");
                                    }
                                    else
                                    {
                                        date = date + " " + minutes + " " + I18n.getString("faction.common.minutes");
                                    }
                                }
                                else
                                {
                                    date = date + " " + hours + " " + I18n.getString("faction.common.hours");
                                }
                            }
                            else
                            {
                                date = date + " " + days + " " + I18n.getString("faction.common.days");
                            }

                            value = date;
                        }
                        else if (pair.getKey().toString().equals("PLAYTIME_INTERSERV"))
                        {
                            date = "";
                            diff = Long.parseLong(pair.getValue().toString()) * 1000L;

                            if (diff < 18000000L)
                            {
                                value = "< 5h";
                            }
                            else if (diff < 36000000L)
                            {
                                value = "5-10h";
                            }
                            else if (diff < 54000000L)
                            {
                                value = "10-15h";
                            }
                            else if (diff < 90000000L)
                            {
                                value = "15-25h";
                            }
                            else if (diff < 180000000L)
                            {
                                value = "25-50h";
                            }
                            else if (diff < 360000000L)
                            {
                                value = "50-100h";
                            }
                            else if (diff < 691200000L)
                            {
                                value = "4-8" + I18n.getString("faction.common.days.short");
                            }
                            else if (diff < 1728000000L)
                            {
                                value = "8-20" + I18n.getString("faction.common.days.short");
                            }
                            else if (diff < 3456000000L)
                            {
                                value = "20-40" + I18n.getString("faction.common.days.short");
                            }
                            else if (diff < 5184000000L)
                            {
                                value = "40-60" + I18n.getString("faction.common.days.short");
                            }
                            else if (diff < 10368000000L)
                            {
                                value = "60-120" + I18n.getString("faction.common.days.short");
                            }
                            else if (diff < 17280000000L)
                            {
                                value = "120-200" + I18n.getString("faction.common.days.short");
                            }
                            else
                            {
                                value = "> 200" + I18n.getString("faction.common.days.short");
                            }
                        }

                        this.drawScaledString(value, offsetX + 193 - this.fontRenderer.getStringWidth(value), offsetY, 16777215, 1.0F, false, false);
                    }

                    ++i;
                }
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0 && mouseX > this.guiLeft + 260 && mouseX < this.guiLeft + 260 + 9 && mouseY > this.guiTop - 4 && mouseY < this.guiTop - 4 + 10)
        {
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            Minecraft.getMinecraft().displayGuiScreen(new ProfilGui(this.targetName, ""));
        }
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        float newX = (float)x;

        if (centered)
        {
            newX = (float)x - (float)this.fontRenderer.getStringWidth(text) * scale / 2.0F;
        }

        if (shadow)
        {
            this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
        }

        this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
