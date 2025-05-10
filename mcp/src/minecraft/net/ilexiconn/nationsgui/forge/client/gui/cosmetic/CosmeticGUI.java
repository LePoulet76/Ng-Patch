package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class CosmeticGUI extends CommonCosmeticGUI
{
    public static HashMap<String, Object> data = new HashMap();
    public static boolean loaded = false;
    private EntityOtherPlayerMP playerEntity = null;
    public static HashMap<String, Object> cachedDataMainMenu = new HashMap();
    private static Gson gson = new Gson();
    public static Long timeOpenGUI = Long.valueOf(System.currentTimeMillis());

    public CosmeticGUI(String playerTarget)
    {
        this.playerTarget = playerTarget;
        data.clear();
        loaded = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticDataPacket(playerTarget)));
        this.playerEntity = null;

        try
        {
            HashMap e = (HashMap)gson.fromJson(new InputStreamReader((new URL("https://apiv2.nationsglory.fr/json/cosmetics_home.json")).openStream(), StandardCharsets.UTF_8), (new CosmeticGUI$1(this)).getType());
            cachedDataMainMenu = e;
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (this.displayModal)
        {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (mouseButton == 0 && this.hoveredAction.contains("tab#"))
        {
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            Minecraft.getMinecraft().displayGuiScreen(new CosmeticCategoryGUI(this.hoveredAction.replaceAll("tab#", ""), this.playerTarget));
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        this.hoveredAction = "";
        this.tooltipToDraw.clear();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
        ModernGui.drawScaledStringCustomFont(Minecraft.getMinecraft().thePlayer.username.equalsIgnoreCase(this.playerTarget) ? I18n.getString("cosmetic.title") : I18n.getString("cosmetic.title_of").replace("#player#", this.playerTarget.toUpperCase()), (float)(this.guiLeft + 12), (float)(this.guiTop + 11), 12895428, 1.0F, "left", false, "georamaExtraBold", 28);
        LinkedTreeMap categoryCounters = (LinkedTreeMap)data.get("categoryCounters");
        LinkedTreeMap rarityCounters = (LinkedTreeMap)data.get("rarityCounters");
        LinkedTreeMap maxCounters = (LinkedTreeMap)data.get("maxCounters");

        if (!cachedDataMainMenu.isEmpty())
        {
            ModernGui.glColorHex(((Integer)rarityColors.get((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("rarity"))).intValue(), 1.0F);
            ModernGui.drawRoundedRectangle((float)this.guiLeft + 14.0F, (float)this.guiTop + 39.0F, this.zLevel, 268.0F, 105.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("cosmetic");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 14), (float)(this.guiTop + 39), (float)(483 * GUI_SCALE), (float)(0 * GUI_SCALE), 310 * GUI_SCALE, 105 * GUI_SCALE, 310, 105, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
            GUIUtils.startGLScissor(this.guiLeft + 14, this.guiTop + 0, 185, 144);
            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(this.guiLeft + 14, this.guiTop + 15, 0, 0, 500, 500, 500 / GUI_SCALE, 500 / GUI_SCALE, 500.0F, 500.0F, true, (String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("image"));
            GUIUtils.endGLScissor();

            if (!((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("category")).isEmpty())
            {
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                ModernGui.glColorHex(((Integer)rarityColors.get((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("rarity"))).intValue(), 1.0F);
                ModernGui.drawRoundedRectangle((float)(this.guiLeft + 292), (float)(this.guiTop + 35), 0.0F, 35.0F, 9.0F);
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 310 - 6 - 2 - (int)(semiBold25.getStringWidth(I18n.getString("cosmetic.rarity." + ((LinkedTreeMap)cachedDataMainMenu.get("news")).get("rarity"))) / 2.0F / 2.0F)), (float)(this.guiTop + 34), (float)(851 * GUI_SCALE), (float)(((Integer)categoryIcons49Y.get((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("category"))).intValue() * GUI_SCALE), 49 * GUI_SCALE, 49 * GUI_SCALE, 12, 12, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
                ModernGui.drawScaledStringCustomFont(I18n.getString("cosmetic.rarity." + ((LinkedTreeMap)cachedDataMainMenu.get("news")).get("rarity")), (float)(this.guiLeft + 310 + 5), (float)this.guiTop + 36.5F, COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 25);
            }

            ModernGui.drawScaledStringCustomFont((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("title_" + (System.getProperty("java.lang").equals("fr") ? "fr" : "en")), (float)(this.guiLeft + 205), (float)(this.guiTop + 57), COLOR_WHITE, 0.5F, "left", false, "georamaSemiBold", 30);
            ModernGui.drawSectionStringCustomFont((String)((LinkedTreeMap)cachedDataMainMenu.get("news")).get("description_" + (System.getProperty("java.lang").equals("fr") ? "fr" : "en")), (float)(this.guiLeft + 205), (float)(this.guiTop + 73), COLOR_LIGHT_GRAY, 0.5F, "left", false, "georamaSemiBold", 25, 10, 220);

            if (!((LinkedTreeMap)cachedDataMainMenu.get("news")).get("link_button").equals(""))
            {
                ClientEventHandler.STYLE.bindTexture("cosmetic");

                if (mouseX >= this.guiLeft + 205 && mouseX <= this.guiLeft + 205 + 46 && mouseY >= this.guiTop + 123 && mouseY <= this.guiTop + 123 + 14)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 205), (float)(this.guiTop + 123), (float)(283 * GUI_SCALE), (float)(611 * GUI_SCALE), 46 * GUI_SCALE, 14 * GUI_SCALE, 46, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("cosmetic.label.get").toUpperCase(), (float)(this.guiLeft + 205 + 23), (float)this.guiTop + 126.5F, COLOR_LIGHT_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
                    this.hoveredAction = "open_url#" + ((LinkedTreeMap)cachedDataMainMenu.get("news")).get("link_button");
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 205), (float)(this.guiTop + 123), (float)(283 * GUI_SCALE), (float)(595 * GUI_SCALE), 46 * GUI_SCALE, 14 * GUI_SCALE, 46, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("cosmetic.label.get").toUpperCase(), (float)(this.guiLeft + 205 + 23), (float)this.guiTop + 126.5F, COLOR_WHITE, 0.5F, "center", false, "georamaSemiBold", 27);
                }
            }
        }

        int offsetX;

        if (loaded)
        {
            offsetX = 0;

            for (Iterator textY = ((ArrayList)data.get("market")).iterator(); textY.hasNext(); ++offsetX)
            {
                LinkedTreeMap text = (LinkedTreeMap)textY.next();
                ClientEventHandler.STYLE.bindTexture("cosmetic");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 334), (float)(this.guiTop + 39 + offsetX * 56), (float)(345 * GUI_SCALE), (float)(((Integer)marketBackgroundByRarityY.get((String)text.get("rarity"))).intValue() * GUI_SCALE), 118 * GUI_SCALE, 49 * GUI_SCALE, 118, 49, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
                boolean index = mouseX >= this.guiLeft + 334 && mouseX <= this.guiLeft + 334 + 118 && mouseY >= this.guiTop + 39 + offsetX * 56 && mouseY <= this.guiTop + 39 + offsetX * 56 + 49;

                if (ClientProxy.SKIN_MANAGER.getSkinFromID((String)text.get("skin_name")) != null)
                {
                    ClientProxy.SKIN_MANAGER.getSkinFromID((String)text.get("skin_name")).renderInGUI(this.guiLeft + 335 - (int)(index ? (float)(120 / GUI_SCALE) * 0.05F / 2.0F : 0.0F), this.guiTop + 40 + offsetX * 56 - (int)(index ? (float)(120 / GUI_SCALE) * 0.05F / 2.0F : 0.0F), index ? 2.1F : 2.0F, par3);
                }

                String name = text.containsKey("name_" + System.getProperty("java.lang")) ? (String)text.get("name_" + System.getProperty("java.lang")) : (String)text.get("skin_name");
                ModernGui.drawSectionStringCustomFont(name.toUpperCase(), (float)(this.guiLeft + 389), (float)(this.guiTop + 55 + offsetX * 56), COLOR_WHITE, 0.5F, "left", false, "georamaSemiBold", 30, 8, 90);

                if (!((String)text.get("market_end")).isEmpty())
                {
                    ClientEventHandler.STYLE.bindTexture("cosmetic");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 418), (float)(this.guiTop + 33 + offsetX * 56), (float)(483 * GUI_SCALE), (float)(238 * GUI_SCALE), 39 * GUI_SCALE, 13 * GUI_SCALE, 39, 13, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(formatTime(Long.valueOf(Long.parseLong((String)text.get("market_end")))), (float)(this.guiLeft + 438), (float)(this.guiTop + 37 + offsetX * 56), COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 22);
                }

                if (index && (((String)text.get("market_end")).isEmpty() || System.currentTimeMillis() < Long.parseLong((String)text.get("market_end"))))
                {
                    Iterator category = text.entrySet().iterator();

                    while (category.hasNext())
                    {
                        Entry entry = (Entry)category.next();
                        this.itemToBuyHover.put(entry.getKey(), (String)entry.getValue());
                    }

                    this.hoveredAction = "open_modal";
                }
            }
        }

        offsetX = this.guiLeft + 27;
        int var14 = this.guiTop + 211;
        String var15 = "";

        if (loaded)
        {
            var15 = String.format("%.0f", new Object[] {data.get("ownedCount")}) + "/" + String.format("%.0f", new Object[] {maxCounters.get("total")}) + " " + I18n.getString("cosmetic.unlocked");
            ModernGui.drawScaledStringCustomFont(var15, (float)offsetX, (float)var14, COLOR_LIGHT_GRAY, 0.5F, "left", false, "georamaSemiBold", 25);
            offsetX = (int)((float)offsetX + semiBold25.getStringWidth(var15) / 2.0F + 12.0F);
        }

        ClientEventHandler.STYLE.bindTexture("cosmetic");
        ModernGui.glColorHex(((Integer)rarityColors.get("limited")).intValue(), 1.0F);
        ModernGui.drawRoundedRectangle((float)offsetX, (float)(var14 - 2), 0.0F, 30.0F, 9.0F);
        ModernGui.drawScaledStringCustomFont(I18n.getString("cosmetic.rarity.limited"), (float)(offsetX + 15), (float)(var14 - 1), COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
        offsetX += 34;

        if (loaded)
        {
            var15 = (rarityCounters.containsKey("limited") ? String.format("%.0f", new Object[] {rarityCounters.get("limited")}): "0") + "/" + (maxCounters.containsKey("limited") ? String.format("%.0f", new Object[] {maxCounters.get("limited")}): "?") + " " + I18n.getString("cosmetic.unlocked");
            ModernGui.drawScaledStringCustomFont(var15, (float)offsetX, (float)var14, COLOR_LIGHT_GRAY, 0.5F, "left", false, "georamaSemiBold", 25);
            offsetX = (int)((float)offsetX + semiBold25.getStringWidth(var15) / 2.0F + 12.0F);
        }

        ClientEventHandler.STYLE.bindTexture("cosmetic");
        ModernGui.glColorHex(((Integer)rarityColors.get("epic")).intValue(), 1.0F);
        ModernGui.drawRoundedRectangle((float)offsetX, (float)(var14 - 2), 0.0F, 30.0F, 9.0F);
        ModernGui.drawScaledStringCustomFont(I18n.getString("cosmetic.rarity.epic"), (float)(offsetX + 15), (float)(var14 - 1), COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
        offsetX += 34;

        if (loaded)
        {
            var15 = (rarityCounters.containsKey("epic") ? String.format("%.0f", new Object[] {rarityCounters.get("epic")}): "0") + "/" + (maxCounters.containsKey("epic") ? String.format("%.0f", new Object[] {maxCounters.get("epic")}): "?") + " " + I18n.getString("cosmetic.unlocked");
            ModernGui.drawScaledStringCustomFont(var15, (float)offsetX, (float)var14, COLOR_LIGHT_GRAY, 0.5F, "left", false, "georamaSemiBold", 25);
            offsetX = (int)((float)offsetX + semiBold25.getStringWidth(var15) / 2.0F + 12.0F);
        }

        ClientEventHandler.STYLE.bindTexture("cosmetic");
        ModernGui.glColorHex(((Integer)rarityColors.get("rare")).intValue(), 1.0F);
        ModernGui.drawRoundedRectangle((float)offsetX, (float)(var14 - 2), 0.0F, 30.0F, 9.0F);
        ModernGui.drawScaledStringCustomFont(I18n.getString("cosmetic.rarity.rare"), (float)(offsetX + 15), (float)(var14 - 1), COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
        offsetX += 34;

        if (loaded)
        {
            var15 = (rarityCounters.containsKey("rare") ? String.format("%.0f", new Object[] {rarityCounters.get("rare")}): "0") + "/" + (maxCounters.containsKey("rare") ? String.format("%.0f", new Object[] {maxCounters.get("rare")}): "?") + " " + I18n.getString("cosmetic.unlocked");
            ModernGui.drawScaledStringCustomFont(var15, (float)offsetX, (float)var14, COLOR_LIGHT_GRAY, 0.5F, "left", false, "georamaSemiBold", 25);
            offsetX = (int)((float)offsetX + semiBold25.getStringWidth(var15) / 2.0F + 12.0F);
        }

        int var16 = 0;

        for (Iterator var17 = CATEGORIES_ORDER.iterator(); var17.hasNext(); ++var16)
        {
            String var18 = (String)var17.next();
            ClientEventHandler.STYLE.bindTexture("cosmetic");

            if (mouseX >= this.guiLeft + 13 + 45 * var16 && mouseX <= this.guiLeft + 13 + 45 * var16 + 36 && mouseY >= this.guiTop + 161 && mouseY <= this.guiTop + 161 + 36)
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 15 + 45 * var16), (float)(this.guiTop + 161), (float)(951 * GUI_SCALE), (float)(var16 * 30 * GUI_SCALE), 30 * GUI_SCALE, 30 * GUI_SCALE, 30, 30, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                this.hoveredAction = "tab#" + var18;
            }
            else
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 15 + 45 * var16), (float)(this.guiTop + 161), (float)(920 * GUI_SCALE), (float)(var16 * 30 * GUI_SCALE), 30 * GUI_SCALE, 30 * GUI_SCALE, 30, 30, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            }

            if (loaded)
            {
                ModernGui.drawScaledStringCustomFont((categoryCounters.containsKey(var18) ? String.format("%.0f", new Object[] {categoryCounters.get(var18)}): "0") + "/" + (maxCounters.containsKey(var18) ? String.format("%.0f", new Object[] {maxCounters.get(var18)}): "?"), (float)(this.guiLeft + 37 + 45 * var16), (float)(this.guiTop + 190), COLOR_LIGHT_GRAY, 0.5F, "center", false, "georamaSemiBold", 20);
            }
        }

        super.drawScreen(mouseX, mouseY, par3);

        if (!this.tooltipToDraw.isEmpty())
        {
            this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }
}
