package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class DiplomatieGUI_OLD extends TabbedFactionGUI_OLD
{
    public static boolean loaded = false;
    public static ArrayList<HashMap<String, Object>> factionDiplomatieInfos;
    private HashMap<String, Object> selectedDiplomatieInfos;
    public HashMap<String, String> availableActions = new HashMap();
    public HashMap<String, Object> hoveredRelation = new HashMap();
    public String hoveredPlayer = "";
    private String loadedFlag2 = "";
    boolean expanded = false;
    private GuiScrollBarFaction scrollBar;
    private GuiScrollBarFaction scrollBarPlayers;
    private DynamicTexture flagTexture1;
    private DynamicTexture flagTexture2;
    private GuiButton wishButton;
    private EntityPlayer entityPlayer;

    public DiplomatieGUI_OLD(EntityPlayer player)
    {
        super(player);
        this.entityPlayer = player;

        if (FactionGui_OLD.factionInfos != null && FactionGui_OLD.factionInfos.get("flagImage") != null && !((String)FactionGui_OLD.factionInfos.get("flagImage")).isEmpty())
        {
            BufferedImage image = decodeToImage((String)FactionGui_OLD.factionInfos.get("flagImage"));
            this.flagTexture1 = new DynamicTexture(image);
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
        this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 376), (float)(this.guiTop + 54), 150);
        this.scrollBarPlayers = new GuiScrollBarFaction((float)(this.guiLeft + 248), (float)(this.guiTop + 145), 80);
        this.wishButton = new TexturedCenteredButtonGUI(0, this.guiLeft + 10, this.guiTop + 165, 100, 30, "faction_btn", 0, 68, "");
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (loaded)
        {
            this.drawScaledString(I18n.getString("faction.diplomatie.title"), this.guiLeft + 131, this.guiTop + 16, 1644825, 1.4F, false, false);

            if (factionDiplomatieInfos != null && factionDiplomatieInfos.size() > 0)
            {
                if (this.selectedDiplomatieInfos == null)
                {
                    this.selectedDiplomatieInfos = (HashMap)factionDiplomatieInfos.get(0);
                }

                if (this.loadedFlag2.isEmpty() || !this.loadedFlag2.equalsIgnoreCase((String)this.selectedDiplomatieInfos.get("factionName")))
                {
                    if (this.selectedDiplomatieInfos.get("flag") != null && !((String)this.selectedDiplomatieInfos.get("flag")).isEmpty())
                    {
                        BufferedImage onlinePlayers = decodeToImage((String)this.selectedDiplomatieInfos.get("flag"));
                        this.flagTexture2 = new DynamicTexture(onlinePlayers);
                    }
                    else
                    {
                        this.flagTexture2 = null;
                    }
                }

                this.drawScaledString("\u00a7f[" + I18n.getString("faction.common." + this.selectedDiplomatieInfos.get("relationType")) + "\u00a7f] " + this.selectedDiplomatieInfos.get("factionName"), this.guiLeft + 137, this.guiTop + 37, 16777215, 1.0F, false, false);
                this.drawScaledString(I18n.getString("faction.common." + this.selectedDiplomatieInfos.get("relationType")), this.guiLeft + 255, this.guiTop + 80, 16777215, 1.7F, true, false);

                if (!((String)this.selectedDiplomatieInfos.get("relationTime")).isEmpty() && !((String)this.selectedDiplomatieInfos.get("relationTime")).equals("null"))
                {
                    this.drawScaledString(this.convertDate((String)this.selectedDiplomatieInfos.get("relationTime")), this.guiLeft + 253, this.guiTop + 95, 16777215, 0.8F, true, false);
                }

                if (this.flagTexture1 != null)
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.flagTexture1.getGlTextureId());
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 163), (float)(this.guiTop + 77), 0.0F, 0.0F, 156, 78, 35, 20, 156.0F, 78.0F, false);
                }

                this.drawScaledString((String)FactionGui_OLD.factionInfos.get("name"), this.guiLeft + 180, this.guiTop + 103, 16777215, 1.0F, true, false);

                if (this.flagTexture2 != null)
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.flagTexture2.getGlTextureId());
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 313), (float)(this.guiTop + 77), 0.0F, 0.0F, 156, 78, 35, 20, 156.0F, 78.0F, false);
                }

                this.drawScaledString((String)this.selectedDiplomatieInfos.get("factionName"), this.guiLeft + 330, this.guiTop + 103, 16777215, 1.0F, true, false);
                this.hoveredPlayer = "";
                ArrayList var7 = (ArrayList)this.selectedDiplomatieInfos.get("onlinePlayers");
                this.drawScaledString(I18n.getString("faction.diplomatie.online_players") + " (" + var7.size() + ")", this.guiLeft + 131, this.guiTop + 133, 1644825, 0.8F, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 142, 117, 88);
                int i;
                int offsetX;
                Float offsetY;

                for (i = 0; i < var7.size(); ++i)
                {
                    offsetX = this.guiLeft + 131;
                    offsetY = Float.valueOf((float)(this.guiTop + 142 + i * 20) + this.getSlidePlayers());
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 131, 142, 117, 20, 512.0F, 512.0F, false);
                    this.drawScaledString((String)var7.get(i), offsetX + 6, offsetY.intValue() + 6, 16777215, 1.0F, false, false);

                    if (mouseX >= offsetX && mouseX <= offsetX + 117 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 20.0F)
                    {
                        this.hoveredPlayer = (String)var7.get(i);
                    }
                }

                GUIUtils.endGLScissor();

                if (!this.expanded && mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 130 + 125 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 90)
                {
                    this.scrollBarPlayers.draw(mouseX, mouseY);
                }

                this.drawScaledString(I18n.getString("faction.diplomatie.buttons.page_pays"), this.guiLeft + 321, this.guiTop + 146, 16777215, 1.0F, true, false);

                if (this.expanded)
                {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 130), (float)(this.guiTop + 49), 258, 352, 254, 160, 512.0F, 512.0F, false);
                    this.hoveredRelation = new HashMap();
                    GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 50, 245, 158);

                    for (i = 0; i < factionDiplomatieInfos.size(); ++i)
                    {
                        offsetX = this.guiLeft + 131;
                        offsetY = Float.valueOf((float)(this.guiTop + 50 + i * 20) + this.getSlide());
                        ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 259, 353, 245, 20, 512.0F, 512.0F, false);
                        this.drawScaledString("\u00a7f[" + I18n.getString("faction.common." + ((HashMap)factionDiplomatieInfos.get(i)).get("relationType")) + "\u00a7f] " + ((HashMap)factionDiplomatieInfos.get(i)).get("factionName"), offsetX + 6, offsetY.intValue() + 6, 16777215, 1.0F, false, false);

                        if (mouseX >= offsetX && mouseX <= offsetX + 245 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 20.0F)
                        {
                            this.hoveredRelation = (HashMap)factionDiplomatieInfos.get(i);
                        }
                    }

                    GUIUtils.endGLScissor();
                    this.scrollBar.draw(mouseX, mouseY);
                }
            }

            this.wishButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
            this.drawScaledString(I18n.getString("faction.diplomatie.buttons.wish_1"), this.guiLeft + 10 + 50, this.guiTop + 165 + 5, 16777215, 1.0F, true, true);
            this.drawScaledString(I18n.getString("faction.diplomatie.buttons.wish_2"), this.guiLeft + 10 + 50, this.guiTop + 165 + 15, 16777215, 1.0F, true, true);
        }
    }

    private String convertDate(String time)
    {
        String date = "";
        long diff = System.currentTimeMillis() - Long.parseLong(time);
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

        return date;
    }

    private float getSlide()
    {
        return factionDiplomatieInfos.size() > 8 ? (float)(-(factionDiplomatieInfos.size() - 8) * 20) * this.scrollBar.getSliderValue() : 0.0F;
    }

    private float getSlidePlayers()
    {
        return ((ArrayList)this.selectedDiplomatieInfos.get("onlinePlayers")).size() > 4 ? (float)(-(((ArrayList)this.selectedDiplomatieInfos.get("onlinePlayers")).size() - 4) * 20) * this.scrollBarPlayers.getSliderValue() : 0.0F;
    }

    public void drawTooltip(String text, int mouseX, int mouseY)
    {
        int var10000 = mouseX - this.guiLeft;
        var10000 = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(new String[] {text.substring(0, 1).toUpperCase() + text.substring(1)}), mouseX, mouseY, this.fontRenderer);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0)
        {
            if (mouseX >= this.guiLeft + 364 && mouseX <= this.guiLeft + 364 + 20 && mouseY >= this.guiTop + 30 && mouseY <= this.guiTop + 30 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.expanded = !this.expanded;
            }

            if (mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 130 + 254 && mouseY >= this.guiTop + 49 && mouseY <= this.guiTop + 49 + 160 && this.hoveredRelation != null && this.hoveredRelation.size() > 0)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.expanded = false;
                this.selectedDiplomatieInfos = this.hoveredRelation;
                this.hoveredRelation = new HashMap();
            }

            if (!this.expanded && mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 130 + 125 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 90 && !this.hoveredPlayer.isEmpty())
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new ProfilGui(this.hoveredPlayer.split(" ")[0], ""));
            }

            if (!this.expanded && mouseX >= this.guiLeft + 258 && mouseX <= this.guiLeft + 258 + 126 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 20 && this.selectedDiplomatieInfos != null)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new FactionGui_OLD((String)this.selectedDiplomatieInfos.get("factionName")));
            }

            if (!this.expanded && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 30 && this.selectedDiplomatieInfos != null)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new DiplomatieWishGUI(this.player));
            }
        }
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
}
