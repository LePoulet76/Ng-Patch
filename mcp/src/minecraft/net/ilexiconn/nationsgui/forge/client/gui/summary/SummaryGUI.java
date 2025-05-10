package net.ilexiconn.nationsgui.forge.client.gui.summary;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.URI;
import java.net.URISyntaxException;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiBrowser;
import net.ilexiconn.nationsgui.forge.client.gui.achievements.AchievementsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI$IButtonCallback;
import net.ilexiconn.nationsgui.forge.client.gui.summary.component.SummaryButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.summary.component.SummaryButtonGUI$Type;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceListingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoteOpenCatalogPacket;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SummaryGUI extends GuiScreen
{
    public static ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/gui/ingame.png");
    public SummaryGUI$IButtonCallback callback;
    public int currentTick;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int x = this.width / 2 - 75;
        int y = this.height / 2 - 96;
        this.callback = null;
        this.currentTick = 0;
        PermissionCache.INSTANCE.clearCache();
        PacketCallbacks.MONEY.send(new String[0]);
        this.buttonList.add(new CloseButtonGUI(0, x + 130, y + 14));
        GuiButton buttonShop = new GuiButton(1, x + 16, y + 46, 120, 20, StatCollector.translateToLocal("nationsgui.summary.shop"));
        buttonShop.enabled = false;
        this.buttonList.add(buttonShop);
        PermissionCache.INSTANCE.checkPermission(PermissionType.SHOP, new SummaryGUI$1(this, buttonShop), new String[0]);
        this.buttonList.add(new GuiButton(2, x + 16, y + 74, 120, 20, StatCollector.translateToLocal("nationsgui.summary.achievements")));
        GuiButton buttonHelp = new GuiButton(4, x + 16, y + 103, 120, 20, StatCollector.translateToLocal("nationsgui.summary.help"));
        this.buttonList.add(buttonHelp);
        this.buttonList.add(new GuiButton(5, x + 16, y + 131, 57, 20, StatCollector.translateToLocal("nationsgui.summary.spawn")));
        this.buttonList.add(new GuiButton(10, x + 16 + 57 + 7, y + 131, 57, 20, StatCollector.translateToLocal("nationsgui.summary.wiki")));
        this.buttonList.add(new SummaryButtonGUI(6, x + 16, y + 162, SummaryButtonGUI$Type.SETTINGS));
        this.buttonList.add(new SummaryButtonGUI(7, x + 49, y + 162, SummaryButtonGUI$Type.DISCORD));
        this.buttonList.add(new SummaryButtonGUI(8, x + 82, y + 162, SummaryButtonGUI$Type.TEAMSPEAK));
        this.buttonList.add(new SummaryButtonGUI(9, x + 115, y + 162, SummaryButtonGUI$Type.LEAVE));
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (this.callback != null)
        {
            if (this.currentTick >= this.callback.getSeconds() * 20)
            {
                this.callback.call(this.mc);
                this.callback.getButton().enabled = true;
                this.callback = null;
                this.currentTick = 0;
            }

            ++this.currentTick;
        }
        else
        {
            this.currentTick = 0;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        int x = this.width / 2 - 75;
        int y = this.height / 2 - 96;
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(x + 13, y + 1, 0, 75, 34, 34);
        GuiInventory.func_110423_a(x + 31, y + 72, 32, (float)((-mouseX + x + 32) / 4), (float)((-mouseY + y + 32) / 4), this.mc.thePlayer);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, 400.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(x, y - 7, 0, 0, 151, 53);

        for (int i = 0; i < 8; ++i)
        {
            this.drawTexturedModalRect(x + 6, y + 46 + 18 * i, 6, 53, 141, 18);
        }

        this.drawTexturedModalRect(x + 6, y + 188, 6, 71, 141, 4);
        this.mc.fontRenderer.drawString(Minecraft.getMinecraft().thePlayer.username, x + 56, y + 9, 16777215);
        this.mc.fontRenderer.drawString(EnumChatFormatting.GRAY.toString() + (int)ShopGUI.CURRENT_MONEY + "$", x + 56, y + 20, -1);

        if (this.currentTick != 0 && this.callback != null)
        {
            this.mc.fontRenderer.drawString(EnumChatFormatting.GRAY.toString() + ((this.callback.getSeconds() * 20 - this.currentTick) / 20 + 1) + "s", x + 150, this.callback.getButton().yPosition + 6, 16777215);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glPopMatrix();
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
            this.mc.sndManager.resumeAllSounds();
        }
        else if (button.id == 1)
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RemoteOpenCatalogPacket()));
        }
        else if (button.id == 2)
        {
            try
            {
                this.mc.displayGuiScreen(new AchievementsGUI());
            }
            catch (Exception var5)
            {
                ;
            }
        }
        else if (button.id == 4)
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceListingPacket()));
        }
        else if (button.id == 5)
        {
            this.mc.thePlayer.sendChatMessage("/spawn");
            this.setCallback(new SummaryGUI$2(this, button));
        }
        else if (button.id == 6)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        else if (button.id == 7)
        {
            try
            {
                this.func_73896_a(new URI("https://discordapp.com/invite/GWBZeCf"));
            }
            catch (URISyntaxException var4)
            {
                var4.printStackTrace();
            }
        }
        else if (button.id == 8)
        {
            try
            {
                this.func_73896_a(new URI("ts3server://ts.nationsglory.fr"));
            }
            catch (URISyntaxException var3)
            {
                var3.printStackTrace();
            }
        }
        else if (button.id == 9)
        {
            this.setCallback(new SummaryGUI$3(this, button));
        }
        else if (button.id == 10)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiBrowser("https://wiki.nationsglory.fr/fr/"));
        }
    }

    public void setCallback(SummaryGUI$IButtonCallback callback)
    {
        if (this.callback != null)
        {
            this.callback.getButton().enabled = true;
        }

        this.callback = callback;
        this.callback.getButton().enabled = false;
        this.currentTick = 0;
    }

    public void func_73896_a(URI _uri)
    {
        try
        {
            Class t = Class.forName("java.awt.Desktop");
            Object theDesktop = t.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            t.getMethod("browse", new Class[] {URI.class}).invoke(theDesktop, new Object[] {_uri});
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
        }
    }
}
