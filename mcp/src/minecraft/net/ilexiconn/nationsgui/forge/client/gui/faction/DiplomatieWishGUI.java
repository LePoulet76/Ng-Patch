package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieWishActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieWishDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sun.misc.BASE64Decoder;

@SideOnly(Side.CLIENT)
public class DiplomatieWishGUI extends GuiScreen
{
    protected int xSize = 333;
    protected int ySize = 168;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    private RenderItem itemRenderer = new RenderItem();
    public static HashMap<String, ArrayList<HashMap<String, String>>> diplomatieWishInfos = new HashMap();
    private GuiScrollBarFaction scrollBarReceived;
    private GuiScrollBarFaction scrollBarSent;
    private EntityPlayer entityPlayer;
    private HashMap<String, DynamicTexture> cachedFlags = new HashMap();
    private String hoveredCountryFlag = "";
    private String hoveredAction = "";
    private String hoveredCountry = "";
    private String hoveredRelationType = "";
    private String clickedCountry = "";

    public DiplomatieWishGUI(EntityPlayer entityPlayer)
    {
        this.entityPlayer = entityPlayer;
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
        this.scrollBarReceived = new GuiScrollBarFaction((float)(this.guiLeft + 175), (float)(this.guiTop + 57), 80);
        this.scrollBarSent = new GuiScrollBarFaction((float)(this.guiLeft + 306), (float)(this.guiTop + 57), 80);
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieWishDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (mouseX >= this.guiLeft + 320 && mouseX <= this.guiLeft + 320 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 320), (float)(this.guiTop - 6), 0, 179, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 320), (float)(this.guiTop - 6), 0, 169, 9, 10, 512.0F, 512.0F, false);
        }

        this.drawScaledString(I18n.getString("faction.diplomatie_wish.title"), this.guiLeft + 51, this.guiTop + 17, 1644825, 1.2F, false, false);
        this.hoveredCountryFlag = "";
        this.hoveredCountry = "";
        this.hoveredAction = "";
        this.hoveredRelationType = "";
        this.drawScaledString(I18n.getString("faction.diplomatie_wish.received"), this.guiLeft + 57, this.guiTop + 44, 1644825, 0.9F, false, false);
        ArrayList received = (ArrayList)diplomatieWishInfos.get("received");

        if (received != null && received.size() > 0)
        {
            GUIUtils.startGLScissor(this.guiLeft + 57, this.guiTop + 53, 118, 88);

            for (int sent = 0; sent < received.size(); ++sent)
            {
                HashMap l = (HashMap)received.get(sent);
                int paysInfos = this.guiLeft + 57;
                Float offsetX = Float.valueOf((float)(this.guiTop + 53 + sent * 22) + this.getSlideReceived());
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                ModernGui.drawModalRectWithCustomSizedTexture((float)paysInfos, (float)offsetX.intValue(), 57, 53, 118, 22, 512.0F, 512.0F, false);

                if (!this.cachedFlags.containsKey(l.get("factionName")) && l.get("flag") != null && !((String)l.get("flag")).isEmpty() || this.cachedFlags.containsKey(l.get("factionName")) && !((DynamicTexture)this.cachedFlags.get(l.get("factionName"))).equals(l.get("flag")))
                {
                    BufferedImage offsetY = decodeToImage((String)l.get("flag"));
                    this.cachedFlags.put(l.get("factionName"), new DynamicTexture(offsetY));
                }

                if (this.cachedFlags.containsKey(l.get("factionName")) && this.cachedFlags.get(l.get("factionName")) != null)
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)this.cachedFlags.get(l.get("factionName"))).getGlTextureId());
                    ModernGui.drawScaledCustomSizeModalRect((float)(paysInfos + 5), (float)(offsetX.intValue() + 5), 0.0F, 0.0F, 156, 78, 18, 10, 156.0F, 78.0F, false);
                }
                else
                {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(paysInfos + 5), (float)(offsetX.intValue() + 5), 38, 169, 18, 10, 512.0F, 512.0F, false);
                }

                if (mouseX >= paysInfos + 5 && mouseX <= paysInfos + 5 + 18 && mouseY >= offsetX.intValue() + 5 && mouseY <= offsetX.intValue() + 5 + 10)
                {
                    this.hoveredCountryFlag = (String)l.get("factionName");
                }

                this.drawScaledString(I18n.getString("faction.common." + ((String)l.get("relationType")).toLowerCase()), paysInfos + 28, offsetX.intValue() + 6, 16777215, 1.0F, false, false);

                if (((Boolean)FactionGui_OLD.factionInfos.get("isInCountry")).booleanValue() && ((Boolean)FactionGui_OLD.factionInfos.get("isAtLeastOfficer")).booleanValue())
                {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(paysInfos + 93), (float)(offsetX.intValue() + 5), 15, 169, 10, 10, 512.0F, 512.0F, false);

                    if (mouseX >= paysInfos + 93 && mouseX <= paysInfos + 93 + 10 && mouseY >= offsetX.intValue() + 5 && mouseY <= offsetX.intValue() + 5 + 10)
                    {
                        this.hoveredCountry = (String)l.get("factionName");
                        this.hoveredAction = "yes";
                        this.hoveredRelationType = (String)l.get("relationType");
                    }

                    ModernGui.drawModalRectWithCustomSizedTexture((float)(paysInfos + 105), (float)(offsetX.intValue() + 5), 25, 169, 10, 10, 512.0F, 512.0F, false);

                    if (mouseX >= paysInfos + 105 && mouseX <= paysInfos + 105 + 10 && mouseY >= offsetX.intValue() + 5 && mouseY <= offsetX.intValue() + 5 + 10)
                    {
                        this.hoveredCountry = (String)l.get("factionName");
                        this.hoveredAction = "no";
                        this.hoveredRelationType = (String)l.get("relationType");
                    }
                }
            }

            GUIUtils.endGLScissor();

            if (mouseX >= this.guiLeft + 57 && mouseX <= this.guiLeft + 57 + 118 && mouseY >= this.guiTop + 53 && mouseY <= this.guiTop + 53 + 88)
            {
                this.scrollBarReceived.draw(mouseX, mouseY);
            }
        }

        this.drawScaledString(I18n.getString("faction.diplomatie_wish.sent"), this.guiLeft + 188, this.guiTop + 44, 1644825, 0.9F, false, false);
        ArrayList var11 = (ArrayList)diplomatieWishInfos.get("sent");

        if (var11 != null && var11.size() > 0)
        {
            GUIUtils.startGLScissor(this.guiLeft + 188, this.guiTop + 53, 118, 88);

            for (int var12 = 0; var12 < var11.size(); ++var12)
            {
                HashMap var13 = (HashMap)var11.get(var12);
                int var14 = this.guiLeft + 188;
                Float var15 = Float.valueOf((float)(this.guiTop + 53 + var12 * 22) + this.getSlideSent());
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                ModernGui.drawModalRectWithCustomSizedTexture((float)var14, (float)var15.intValue(), 188, 53, 118, 22, 512.0F, 512.0F, false);

                if (!this.cachedFlags.containsKey(var13.get("factionName")) && var13.get("flag") != null && !((String)var13.get("flag")).isEmpty() || this.cachedFlags.containsKey(var13.get("factionName")) && !((DynamicTexture)this.cachedFlags.get(var13.get("factionName"))).equals(var13.get("flag")))
                {
                    BufferedImage image = decodeToImage((String)var13.get("flag"));
                    this.cachedFlags.put(var13.get("factionName"), new DynamicTexture(image));
                }

                if (this.cachedFlags.containsKey(var13.get("factionName")) && this.cachedFlags.get(var13.get("factionName")) != null)
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)this.cachedFlags.get(var13.get("factionName"))).getGlTextureId());
                    ModernGui.drawScaledCustomSizeModalRect((float)(var14 + 5), (float)(var15.intValue() + 5), 0.0F, 0.0F, 156, 78, 18, 10, 156.0F, 78.0F, false);
                }
                else
                {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(var14 + 5), (float)(var15.intValue() + 5), 38, 169, 18, 10, 512.0F, 512.0F, false);
                }

                if (mouseX >= var14 + 5 && mouseX <= var14 + 5 + 18 && mouseY >= var15.intValue() + 5 && mouseY <= var15.intValue() + 5 + 10)
                {
                    this.hoveredCountryFlag = (String)var13.get("factionName");
                }

                this.drawScaledString(I18n.getString("faction.common." + ((String)var13.get("relationType")).toLowerCase()), var14 + 28, var15.intValue() + 6, 16777215, 1.0F, false, false);

                if (((Boolean)FactionGui_OLD.factionInfos.get("isInCountry")).booleanValue() && ((Boolean)FactionGui_OLD.factionInfos.get("isAtLeastOfficer")).booleanValue())
                {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(var14 + 105), (float)(var15.intValue() + 5), 25, 169, 10, 10, 512.0F, 512.0F, false);

                    if (mouseX >= var14 + 105 && mouseX <= var14 + 105 + 10 && mouseY >= var15.intValue() + 5 && mouseY <= var15.intValue() + 5 + 10)
                    {
                        this.hoveredCountry = (String)var13.get("factionName");
                        this.hoveredAction = "cancel";
                    }
                }
            }

            GUIUtils.endGLScissor();

            if (mouseX >= this.guiLeft + 188 && mouseX <= this.guiLeft + 188 + 118 && mouseY >= this.guiTop + 53 && mouseY <= this.guiTop + 53 + 88)
            {
                this.scrollBarSent.draw(mouseX, mouseY);
            }
        }

        if (!this.hoveredCountryFlag.isEmpty())
        {
            this.drawHoveringText(Arrays.asList(new String[] {this.hoveredCountryFlag}), mouseX, mouseY, this.fontRenderer);
        }
    }

    private float getSlideReceived()
    {
        return ((ArrayList)diplomatieWishInfos.get("received")).size() > 4 ? (float)(-(((ArrayList)diplomatieWishInfos.get("received")).size() - 4) * 22) * this.scrollBarReceived.getSliderValue() : 0.0F;
    }

    private float getSlideSent()
    {
        return ((ArrayList)diplomatieWishInfos.get("sent")).size() > 4 ? (float)(-(((ArrayList)diplomatieWishInfos.get("sent")).size() - 4) * 22) * this.scrollBarSent.getSliderValue() : 0.0F;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (mouseX > this.guiLeft + 320 && mouseX < this.guiLeft + 320 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new DiplomatieGUI_OLD(this.entityPlayer));
            }

            if (!this.hoveredAction.isEmpty() && !this.hoveredCountry.isEmpty() && !this.clickedCountry.equals(this.hoveredCountry))
            {
                this.clickedCountry = this.hoveredCountry;

                if (this.hoveredAction.equals("no") && this.hoveredRelationType.equalsIgnoreCase("colony"))
                {
                    Minecraft.getMinecraft().displayGuiScreen(new RefuseColonyConfirmGui(this, this.hoveredCountry, this.hoveredAction, this.hoveredRelationType));
                }
                else
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieWishActionPacket((String)FactionGui_OLD.factionInfos.get("name"), this.hoveredCountry, this.hoveredAction, this.hoveredRelationType)));
                }
            }
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
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

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();
            int j1;

            while (iterator.hasNext())
            {
                String i1 = (String)iterator.next();
                j1 = font.getStringWidth(i1);

                if (j1 > k)
                {
                    k = j1;
                }
            }

            int var15 = par2 + 12;
            j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (var15 + k > this.width)
            {
                var15 -= 28 + k;
            }

            if (j1 + k1 + 6 > this.height)
            {
                j1 = this.height - k1 - 6;
            }

            this.zLevel = 300.0F;
            this.itemRenderer.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, var15, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            this.itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
