package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import micdoodle8.mods.galacticraft.core.network.GCCorePacketHandlerServer.EnumPacketServer;
import micdoodle8.mods.galacticraft.core.util.PacketUtil;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$4;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$5;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$6;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$7;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$8;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$9;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$EdoraBossButton;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$GalacticraftButton;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI$BookButton;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionCreateGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.SellItemGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionNamePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.OpenRecipeGUIPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class InventoryGUI extends GuiContainer
{
    public static final List<GuiScreenTab> TABS = new ArrayList();
    private final Map < Class <? extends GuiScreen > , Integer > tabAlerts = new HashMap();
    private final EntityPlayer player;
    public static boolean isInAssault;
    public static boolean achievementDone = false;

    public InventoryGUI(EntityPlayer player)
    {
        super(player.inventoryContainer);
        this.player = player;
        this.xSize = 182;
        this.ySize = 223;

        if (ClientProxy.serverType.equals("ng"))
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionNamePacket()));
        }

        PacketCallbacks.MONEY.send(new String[0]);

        if (System.currentTimeMillis() - ClientEventHandler.joinTime > 300000L && !achievementDone)
        {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_5_minutes", 1)));
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == ClientKeyHandler.KEY_SELL.keyCode)
        {
            try
            {
                Method e = GuiContainer.class.getDeclaredMethod("getSlotAtPosition", new Class[] {Integer.TYPE, Integer.TYPE});
                e.setAccessible(true);
                Slot slot = (Slot)e.invoke(this, new Object[] {Integer.valueOf(Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth), Integer.valueOf(this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1)});

                if (slot != null)
                {
                    ItemStack itemStack = slot.getStack();

                    if (itemStack != null)
                    {
                        this.mc.displayGuiScreen(new SellItemGUI(itemStack, slot.slotNumber));
                    }
                }
            }
            catch (Exception var6)
            {
                var6.printStackTrace();
            }
        }

        super.keyTyped(par1, par2);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.buttonList.add(new RecipeListGUI$BookButton(0, this.width / 2 - 6, this.height / 2 - 29));
        this.buttonList.add(new InventoryGUI$GalacticraftButton(1, this.width / 2 - 6, this.height / 2 - 29 - 21));
        this.buttonList.add(new InventoryGUI$EdoraBossButton(2, this.width / 2 - 6, this.height / 2 - 29 + 21));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new OpenRecipeGUIPacket()));
        }
        else if (par1GuiButton.id == 1)
        {
            PacketDispatcher.sendPacketToServer(PacketUtil.createPacket("GalacticraftCore", EnumPacketServer.OPEN_EXTENDED_INVENTORY, new Object[0]));
        }
        else if (par1GuiButton.id == 2)
        {
            Minecraft.getMinecraft().displayGuiScreen(new BossEdoraGui(false));
        }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        int i;

        for (i = 0; i <= 4; ++i)
        {
            if (mouseX >= this.guiLeft - (this.getClass() == ((GuiScreenTab)TABS.get(i)).getClassReferent() ? 23 : 20) && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 55 + i * 31 && mouseY <= this.guiTop + 85 + i * 31)
            {
                this.drawCreativeTabHoveringText(I18n.getString("gui.inventory.tab." + i), mouseX - this.guiLeft, mouseY - this.guiTop);
            }
        }

        for (i = 5; i < TABS.size(); ++i)
        {
            if ((i != 5 || !isInAssault) && mouseX >= this.guiLeft + 178 && mouseX <= this.guiLeft + 201 && mouseY >= this.guiTop + 55 + (i - 4) * 31 && mouseY <= this.guiTop + 55 + 31 + (i - 4) * 31)
            {
                this.drawCreativeTabHoveringText(I18n.getString("gui.inventory.tab." + i), mouseX - this.guiLeft, mouseY - this.guiTop);
            }
        }

        if (mouseX >= this.guiLeft + 109 && mouseX <= this.guiLeft + 109 + 13 && mouseY >= this.guiTop + 51 && mouseY <= this.guiTop + 51 + 14)
        {
            this.drawCreativeTabHoveringText("Money", mouseX - this.guiLeft, mouseY - this.guiTop);
        }
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("inventory");
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        int text;
        GuiScreenTab pX;
        int w;
        int y;

        for (text = 0; text <= 4; ++text)
        {
            pX = (GuiScreenTab)TABS.get(text);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            w = text % 3;
            y = text / 3;

            if (this.getClass() == pX.getClassReferent())
            {
                this.drawTexturedModalRect(this.guiLeft - 23, this.guiTop + 55 + text * 31, 23, 223, 29, 30);
                this.drawTexturedModalRect(this.guiLeft - 23 + 3, this.guiTop + 55 + text * 31 + 5, 182 + w * 20, y * 20, 20, 20);
            }
            else
            {
                this.drawTexturedModalRect(this.guiLeft - 20, this.guiTop + 55 + text * 31, 0, 223, 23, 30);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                this.drawTexturedModalRect(this.guiLeft - 20 + 3, this.guiTop + 55 + text * 31 + 5, 182 + w * 20, y * 20, 20, 20);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        for (text = 5; text < TABS.size(); ++text)
        {
            pX = (GuiScreenTab)TABS.get(text);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            w = text % 3;
            y = text / 3;

            if (this.getClass() == pX.getClassReferent())
            {
                this.drawTexturedModalRect(this.guiLeft + 178, this.guiTop + 55 + (text - 4) * 31, 85, 223, 29, 30);
                this.drawTexturedModalRect(this.guiLeft + 175, this.guiTop + 55 + (text - 4) * 31 + 5, 182 + w * 20, y * 20, 20, 20);
            }
            else
            {
                this.drawTexturedModalRect(this.guiLeft + 178, this.guiTop + 55 + (text - 4) * 31, 114, 223, 23, 30);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                this.drawTexturedModalRect(this.guiLeft + 179, this.guiTop + 55 + (text - 4) * 31 + 5, 182 + w * 20, y * 20, 20, 20);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        ClientData.currentFaction = ClientData.currentFaction.replaceAll("^\\\u00a7[0-9a-z]", "");

        if (ClientProxy.serverType.equals("ng") && ClientData.currentFaction != null)
        {
            ClientEventHandler.STYLE.bindTexture("faction_btn");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 7), (float)(this.guiTop + 223), 0, 233, 169, 23, 256.0F, 256.0F, false);
            ClientEventHandler.STYLE.bindTexture("inventory");
            Double var8;

            if (!ClientData.currentFaction.contains("Wilderness"))
            {
                var8 = Double.valueOf((double)this.fontRenderer.getStringWidth(I18n.getString("gui.inventory.tab.country")) * 1.2D / 2.0D);
                this.drawTexturedModalRect(this.guiLeft + 91 - var8.intValue() - 17, this.guiTop + 225, 201, 107, 12, 17);
                this.drawScaledString(I18n.getString("gui.inventory.tab.country"), this.guiLeft + 93 - var8.intValue(), this.guiTop + 230, 16777215, 1.2F, false, true);
            }
            else
            {
                var8 = Double.valueOf((double)this.fontRenderer.getStringWidth(I18n.getString("gui.inventory.tab.create")) * 1.2D / 2.0D);
                this.drawTexturedModalRect(this.guiLeft + 91 - var8.intValue() - 17, this.guiTop + 225, 185, 107, 12, 17);
                this.drawScaledString(I18n.getString("gui.inventory.tab.create"), this.guiLeft + 93 - var8.intValue(), this.guiTop + 230, 16777215, 1.2F, false, true);
            }
        }

        this.drawScaledString(this.player.getDisplayName(), this.guiLeft + 90, this.guiTop + 10, 16777215, 1.7F, true, true);
        this.fontRenderer.drawString(ClientData.currentFaction, this.guiLeft + 90 - this.fontRenderer.getStringWidth(ClientData.currentFaction) / 2, this.guiTop + 28, 13027014);
        this.mc.fontRenderer.drawString((int)ShopGUI.CURRENT_MONEY + " $", this.guiLeft + 128, this.guiTop + 54, 16777215, true);
        GuiInventory.func_110423_a(this.guiLeft + 55, this.guiTop + 114, 30, (float)(this.guiLeft + 55 - mouseX), (float)(this.guiTop + 64 - mouseY), this.mc.thePlayer);
        String var10 = I18n.getString("key.inventory");
        int var9 = this.guiLeft + 10 + 81;
        w = this.fontRenderer.getStringWidth(var10);
        this.mc.fontRenderer.drawString(var10, var9 - w / 2, this.guiTop + 128, 16777215, true);
        drawRect(this.guiLeft + 10, this.guiTop + 134, var9 - w / 2 - 3, this.guiTop + 135, -1);
        drawRect(var9 + w / 2 + 3, this.guiTop + 134, this.guiLeft + 171, this.guiTop + 135, -1);
        drawRect(this.guiLeft + 10, this.guiTop + 135, var9 - w / 2 - 3, this.guiTop + 136, -10197916);
        drawRect(var9 + w / 2 + 3, this.guiTop + 135, this.guiLeft + 171, this.guiTop + 136, -10197916);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            int i;
            GuiScreenTab type;

            for (i = 0; i <= 4; ++i)
            {
                type = (GuiScreenTab)TABS.get(i);

                if (mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 55 + i * 31 && mouseY <= this.guiTop + 85 + i * 31)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                    if (this.getClass() != type.getClassReferent())
                    {
                        try
                        {
                            this.mc.thePlayer.closeScreen();
                            type.call();
                        }
                        catch (Exception var8)
                        {
                            var8.printStackTrace();
                        }
                    }
                }
            }

            for (i = 5; i < TABS.size(); ++i)
            {
                if (i != 5 || !isInAssault)
                {
                    type = (GuiScreenTab)TABS.get(i);

                    if (mouseX >= this.guiLeft + 178 && mouseX <= this.guiLeft + 201 && mouseY >= this.guiTop + 55 + (i - 4) * 31 && mouseY <= this.guiTop + 55 + 31 + (i - 4) * 31)
                    {
                        this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                        if (this.getClass() != type.getClassReferent())
                        {
                            try
                            {
                                this.mc.thePlayer.closeScreen();
                                type.call();
                            }
                            catch (Exception var7)
                            {
                                var7.printStackTrace();
                            }
                        }
                    }
                }
            }

            if (ClientProxy.serverType.equals("ng") && mouseX >= this.guiLeft + 7 && mouseX <= this.guiLeft + 7 + 169 && mouseY >= this.guiTop + 223 && mouseY <= this.guiTop + 223 + 23 && ClientData.currentFaction != null)
            {
                if (!ClientData.currentFaction.contains("Wilderness"))
                {
                    Minecraft.getMinecraft().displayGuiScreen(new FactionGUI(ClientData.currentFaction));
                }
                else
                {
                    Minecraft.getMinecraft().displayGuiScreen(new FactionCreateGUI());
                }
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
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

    static
    {
        TABS.add(new InventoryGUI$1());
        TABS.add(new InventoryGUI$2());
        TABS.add(new InventoryGUI$3());
        TABS.add(new InventoryGUI$4());
        TABS.add(new InventoryGUI$5());
        TABS.add(new InventoryGUI$6());
        TABS.add(new InventoryGUI$7());
        TABS.add(new InventoryGUI$8());
        TABS.add(new InventoryGUI$9());
    }
}
