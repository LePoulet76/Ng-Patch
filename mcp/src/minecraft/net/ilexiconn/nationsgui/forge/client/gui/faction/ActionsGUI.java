package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionActionsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class ActionsGUI extends TabbedFactionGUI_OLD
{
    public static boolean loaded = false;
    public boolean packetSent = false;
    public static HashMap<String, Object> factionActionsInfos;
    private List<String> flagCoords = Arrays.asList(new String[] {"261,41", "290,69", "290,94", "261,121", "234,121", "205,94", "205,69", "234,41"});
    private List<String> lockCoords = Arrays.asList(new String[] {"264,52", "277,66", "277,91", "264,104", "239,104", "227,91", "227,66", "239,52"});
    private HashMap<String, DynamicTexture> flagTextures = new HashMap();
    private ArrayList<HashMap<String, String>> cachedLogs = new ArrayList();
    private ArrayList<String> cachedTotals = new ArrayList();
    private GuiScrollBarFaction scrollBarTotal;
    private GuiScrollBarFaction scrollBarLogs;
    private int hoveredIndex;
    private String hoveredOwnerFactionId;
    private String hoveredStatus;
    private boolean hoveringStatus;
    private boolean hoveringFlag;
    private GuiButton allActionsButton;

    public ActionsGUI(EntityPlayer player)
    {
        super(player);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        this.packetSent = false;
        this.scrollBarTotal = new GuiScrollBarFaction((float)(this.guiLeft + 248), (float)(this.guiTop + 173), 50);
        this.scrollBarLogs = new GuiScrollBarFaction((float)(this.guiLeft + 378), (float)(this.guiTop + 173), 50);
        this.allActionsButton = new TexturedCenteredButtonGUI(0, this.guiLeft + 10, this.guiTop + 165, 100, 30, "faction_btn", 0, 68, "");
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        if (!this.packetSent && FactionGui_OLD.loaded)
        {
            this.packetSent = true;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionActionsDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
        }

        ClientEventHandler.STYLE.bindTexture("faction_actions");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (FactionGui_OLD.factionInfos != null && loaded)
        {
            this.hoveredIndex = -1;
            this.hoveredOwnerFactionId = null;
            this.hoveredStatus = null;
            this.hoveringStatus = false;
            this.hoveringFlag = false;
            this.drawScaledString(I18n.getString("faction.actions.title"), this.guiLeft + 131, this.guiTop + 16, 1644825, 1.4F, false, false);
            ArrayList owners = (ArrayList)factionActionsInfos.get("owners");
            ArrayList status = (ArrayList)factionActionsInfos.get("status");
            ArrayList tooltipToDraw = new ArrayList();
            int offsetX;
            long diff;
            long var34;

            for (int actionsTotals = 0; actionsTotals < owners.size(); ++actionsTotals)
            {
                String l = (String)owners.get(actionsTotals);
                offsetX = Integer.parseInt(((String)this.flagCoords.get(actionsTotals)).split(",")[0]);
                int offsetY = Integer.parseInt(((String)this.flagCoords.get(actionsTotals)).split(",")[1]);
                int line = Integer.parseInt(((String)this.lockCoords.get(actionsTotals)).split(",")[0]);
                int amount = Integer.parseInt(((String)this.lockCoords.get(actionsTotals)).split(",")[1]);
                String time;
                BufferedImage now;

                if (!this.isNumeric(l))
                {
                    if (!this.flagTextures.containsKey(l))
                    {
                        time = (String)((ArrayList)factionActionsInfos.get("flags")).get(actionsTotals);

                        if (!time.equals("null"))
                        {
                            now = decodeToImage(time);
                            this.flagTextures.put(l, new DynamicTexture(now));
                        }
                    }

                    if (this.flagTextures.containsKey(l))
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)this.flagTextures.get(l)).getGlTextureId());
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + offsetX), (float)(this.guiTop + offsetY), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
                    }
                }
                else
                {
                    if (!this.flagTextures.containsKey((String)FactionGui_OLD.factionInfos.get("id")))
                    {
                        time = (String)FactionGui_OLD.factionInfos.get("flagImage");
                        now = decodeToImage(time);
                        this.flagTextures.put((String)FactionGui_OLD.factionInfos.get("id"), new DynamicTexture(now));
                    }

                    if (this.flagTextures.containsKey((String)FactionGui_OLD.factionInfos.get("id")))
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)this.flagTextures.get((String)FactionGui_OLD.factionInfos.get("id"))).getGlTextureId());
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + offsetX), (float)(this.guiTop + offsetY), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
                    }
                }

                ClientEventHandler.STYLE.bindTexture("faction_actions");

                if (((String)status.get(actionsTotals)).equals("locked"))
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + line), (float)(this.guiTop + amount), 33, 254, 8, 14, 512.0F, 512.0F, false);
                }
                else
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + line), (float)(this.guiTop + amount), 44, 254, 8, 14, 512.0F, 512.0F, false);
                }

                if (mouseX >= this.guiLeft + offsetX && mouseX <= this.guiLeft + offsetX + 17 && mouseY >= this.guiTop + offsetY && mouseY <= this.guiTop + offsetY + 10)
                {
                    time = !this.isNumeric(l) ? (String)((ArrayList)factionActionsInfos.get("factionsName")).get(actionsTotals) : (String)FactionGui_OLD.factionInfos.get("nameColored");
                    tooltipToDraw.add(time);
                    tooltipToDraw.add(I18n.getString("faction.actions.label.status") + ": " + I18n.getString("faction.actions.status." + (String)status.get(actionsTotals)));
                    tooltipToDraw.add(I18n.getString("faction.actions.label.price") + ": \u00a78" + factionActionsInfos.get("price") + "$");

                    if (!((String)status.get(actionsTotals)).equals("locked") && !((String)FactionGui_OLD.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) && (!this.isNumeric(l) || !((Boolean)FactionGui_OLD.factionInfos.get("isInCountry")).booleanValue()))
                    {
                        this.hoveredIndex = actionsTotals;
                        this.hoveredOwnerFactionId = l;
                        this.hoveringFlag = true;
                        ClientEventHandler.STYLE.bindTexture("faction_actions");
                        ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)(this.guiLeft + offsetX - 1), (float)(this.guiTop + offsetY - 1), 54, 256, 19, 12, 512.0F, 512.0F, false);

                        if (FactionGui_OLD.hasPermissions("actions"))
                        {
                            var34 = System.currentTimeMillis() - Long.parseLong((String)factionActionsInfos.get("lastActionFromBuyer"));
                            diff = 18000000L - var34;

                            if (diff > 0L)
                            {
                                long date = diff / 60000L;
                                tooltipToDraw.add("");
                                tooltipToDraw.add(I18n.getString("faction.actions.label.cooldown") + " \u00a74" + date + " minutes");
                            }
                            else
                            {
                                tooltipToDraw.add("");
                                tooltipToDraw.add(I18n.getString("faction.actions.cta.buy"));
                            }
                        }
                    }
                }
                else if (mouseX >= this.guiLeft + line && mouseX <= this.guiLeft + line + 8 && mouseY >= this.guiTop + amount && mouseY <= this.guiTop + amount + 14 && FactionGui_OLD.hasPermissions("actions") && (((String)FactionGui_OLD.factionInfos.get("playerWhoSeeFactionId")).equals(l) || this.isNumeric(l) && ((Boolean)FactionGui_OLD.factionInfos.get("isInCountry")).booleanValue()))
                {
                    tooltipToDraw.add(I18n.getString("faction.actions.label.change." + (String)status.get(actionsTotals)));
                    this.hoveredIndex = actionsTotals;
                    this.hoveredOwnerFactionId = l;
                    this.hoveredStatus = (String)status.get(actionsTotals);
                    this.hoveringStatus = true;
                }
            }

            this.drawScaledString(I18n.getString("faction.actions.title.top"), this.guiLeft + 131, this.guiTop + 160, 3818599, 0.9F, false, false);
            ArrayList var26 = (ArrayList)factionActionsInfos.get("totals");
            GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 168, 117, 60);
            int var27;
            Float var29;

            for (var27 = 0; var27 < var26.size(); ++var27)
            {
                offsetX = this.guiLeft + 131;
                var29 = Float.valueOf((float)(this.guiTop + 168 + var27 * 21) + this.getSlideTotals());
                ClientEventHandler.STYLE.bindTexture("faction_actions");
                ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)var29.intValue(), 131, 168, 117, 21, 512.0F, 512.0F, false);
                this.drawScaledString(((String)var26.get(var27)).replace("##", "  ") + "$", offsetX + 4, var29.intValue() + 7, 11842740, 0.8F, false, true);
            }

            GUIUtils.endGLScissor();

            if (mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 125 && mouseY > this.guiTop + 167 && mouseY < this.guiTop + 167 + 62)
            {
                this.scrollBarTotal.draw(mouseX, mouseY);
            }

            if (this.cachedLogs.size() == 0)
            {
                ArrayList var28 = (ArrayList)factionActionsInfos.get("logs");

                for (offsetX = 0; offsetX < var28.size(); ++offsetX)
                {
                    HashMap var30 = new HashMap();
                    String var31 = (String)var28.get(offsetX);
                    String var32 = var31.split("##")[1];
                    var32 = "\u00a7a+\u00a77" + var32;
                    var32 = var32 + "$";
                    Long var33 = Long.valueOf(Long.parseLong(var31.split("##")[2]));
                    var34 = System.currentTimeMillis();
                    diff = var34 - var33.longValue();
                    String var35 = "\u00a78" + I18n.getString("faction.bank.date_1");
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
                                var35 = var35 + " " + seconds + " " + I18n.getString("faction.common.seconds") + " " + I18n.getString("faction.bank.date_2");
                            }
                            else
                            {
                                var35 = var35 + " " + minutes + " " + I18n.getString("faction.common.minutes") + " " + I18n.getString("faction.bank.date_2");
                            }
                        }
                        else
                        {
                            var35 = var35 + " " + hours + " " + I18n.getString("faction.common.hours") + " " + I18n.getString("faction.bank.date_2");
                        }
                    }
                    else
                    {
                        var35 = var35 + " " + days + " " + I18n.getString("faction.common.days") + " " + I18n.getString("faction.bank.date_2");
                    }

                    var30.put("amount", var32);
                    var30.put("date", var35);
                    var30.put("factionName", var31.split("##")[0]);
                    this.cachedLogs.add(var30);
                }
            }

            this.drawScaledString(I18n.getString("faction.actions.title.logs"), this.guiLeft + 260, this.guiTop + 160, 3818599, 0.9F, false, false);
            GUIUtils.startGLScissor(this.guiLeft + 260, this.guiTop + 168, 118, 60);

            for (var27 = 0; var27 < this.cachedLogs.size(); ++var27)
            {
                offsetX = this.guiLeft + 260;
                var29 = Float.valueOf((float)(this.guiTop + 168 + var27 * 21) + this.getSlideLogs());
                ClientEventHandler.STYLE.bindTexture("faction_actions");
                ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)var29.intValue(), 131, 168, 117, 21, 512.0F, 512.0F, false);
                this.drawScaledString((String)((HashMap)this.cachedLogs.get(var27)).get("amount"), offsetX + 3, var29.intValue() + 3, 11842740, 0.8F, false, false);
                this.drawScaledString((String)((HashMap)this.cachedLogs.get(var27)).get("date"), offsetX + 3, var29.intValue() + 12, 6710886, 0.65F, false, false);
                ClientEventHandler.STYLE.bindTexture("faction_actions");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 104), (float)(var29.intValue() + 5), 19, 256, 10, 11, 512.0F, 512.0F, false);

                if (mouseX > offsetX + 104 && mouseX < offsetX + 104 + 10 && (float)mouseY > var29.floatValue() + 5.0F && (float)mouseY < var29.floatValue() + 5.0F + 11.0F)
                {
                    tooltipToDraw.add(((HashMap)this.cachedLogs.get(var27)).get("factionName"));
                }
            }

            GUIUtils.endGLScissor();

            if (mouseX > this.guiLeft + 260 && mouseX < this.guiLeft + 260 + 125 && mouseY > this.guiTop + 167 && mouseY < this.guiTop + 167 + 62)
            {
                this.scrollBarLogs.draw(mouseX, mouseY);
            }

            if (mouseX > this.guiLeft + 345 && mouseX < this.guiLeft + 345 + 13 && mouseY > this.guiTop + 79 && mouseY < this.guiTop + 79 + 14)
            {
                tooltipToDraw.add(I18n.getString("faction.actions.help0"));
                tooltipToDraw.add("");
                tooltipToDraw.add(I18n.getString("faction.actions.help1"));
                tooltipToDraw.add(I18n.getString("faction.actions.help2"));
                tooltipToDraw.add(I18n.getString("faction.actions.help3"));
                tooltipToDraw.add("");
                tooltipToDraw.add(I18n.getString("faction.actions.help4"));
                tooltipToDraw.add(I18n.getString("faction.actions.help5"));
                tooltipToDraw.add(I18n.getString("faction.actions.help6"));
                tooltipToDraw.add("");
                tooltipToDraw.add(I18n.getString("faction.actions.help7"));
                tooltipToDraw.add(I18n.getString("faction.actions.help8"));
                tooltipToDraw.add(I18n.getString("faction.actions.help9"));
                tooltipToDraw.add("");
                tooltipToDraw.add(I18n.getString("faction.actions.help10"));
                tooltipToDraw.add(I18n.getString("faction.actions.help11"));
                tooltipToDraw.add(I18n.getString("faction.actions.help12"));
            }

            if (tooltipToDraw != null)
            {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }

            this.allActionsButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
            this.drawScaledString(I18n.getString("faction.actions.button.all_1"), this.guiLeft + 10 + 50, this.guiTop + 165 + 5, 16777215, 1.0F, true, true);
            this.drawScaledString(I18n.getString("faction.actions.button.all_2"), this.guiLeft + 10 + 50, this.guiTop + 165 + 15, 16777215, 1.0F, true, true);
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

    private float getSlideTotals()
    {
        return ((ArrayList)factionActionsInfos.get("totals")).size() > 3 ? (float)(-(((ArrayList)factionActionsInfos.get("totals")).size() - 3) * 21) * this.scrollBarTotal.getSliderValue() : 0.0F;
    }

    private float getSlideLogs()
    {
        return ((ArrayList)factionActionsInfos.get("logs")).size() > 3 ? (float)(-(((ArrayList)factionActionsInfos.get("logs")).size() - 3) * 21) * this.scrollBarLogs.getSliderValue() : 0.0F;
    }

    public void drawTooltip(List<String> texts, int mouseX, int mouseY)
    {
        int var10000 = mouseX - this.guiLeft;
        var10000 = mouseY - this.guiTop;
        this.drawHoveringText(texts, mouseX, mouseY, this.fontRenderer);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (loaded && mouseButton == 0)
        {
            if (this.hoveredIndex != -1 && this.hoveredOwnerFactionId != null && this.hoveringFlag)
            {
                if (FactionGui_OLD.hasPermissions("actions") && !((String)FactionGui_OLD.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) && (System.currentTimeMillis() - Long.parseLong((String)factionActionsInfos.get("lastActionFromBuyer")) >= 18000000L || ((Boolean)factionActionsInfos.get("isOp")).booleanValue()))
                {
                    Minecraft.getMinecraft().displayGuiScreen(new BuyActionConfirmGui(this, this.hoveredIndex, this.hoveredOwnerFactionId, (String)factionActionsInfos.get("price")));
                }
            }
            else if (this.hoveredIndex != -1 && this.hoveredOwnerFactionId != null && this.hoveringStatus && this.hoveredStatus != null && FactionGui_OLD.hasPermissions("actions") && (((String)FactionGui_OLD.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) || this.isNumeric(this.hoveredOwnerFactionId) && ((Boolean)FactionGui_OLD.factionInfos.get("isInCountry")).booleanValue()))
            {
                Minecraft.getMinecraft().displayGuiScreen(new LockActionConfirmGui(this, (String)FactionGui_OLD.factionInfos.get("id"), this.hoveredIndex, this.hoveredStatus));
            }

            if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 30 && FactionGui_OLD.factionInfos != null)
            {
                Minecraft.getMinecraft().displayGuiScreen(new ActionsListGui());
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

    public boolean isNumeric(String str)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException var3)
        {
            return false;
        }
    }
}
