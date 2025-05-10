package net.ilexiconn.nationsgui.forge.client.gui.auction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.data.Auction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionRemovePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class AuctionRemoveGui extends GuiScreen
{
    private int posX;
    private int posY;
    private GuiScreen previous;
    private Auction auction;
    private List<String> textList = new ArrayList();

    public AuctionRemoveGui(GuiScreen previous, Auction auction)
    {
        this.previous = previous;
        this.auction = auction;
        this.textList.clear();
        StringBuilder sub = new StringBuilder();
        String[] words = I18n.getString("hdv.remove.confirm.text").split(" ");
        String[] var5 = words;
        int var6 = words.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            String word = var5[var7];
            String temp = (!Objects.equals(words[0], word) ? " " : "") + word;

            if (Minecraft.getMinecraft().fontRenderer.getStringWidth(sub.toString()) + Minecraft.getMinecraft().fontRenderer.getStringWidth(temp) <= 210)
            {
                sub.append(temp);
            }
            else
            {
                this.textList.add(sub.toString());
                sub = new StringBuilder(word);
            }
        }

        if (!sub.toString().equals(""))
        {
            this.textList.add(sub.toString());
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.posX = this.width / 2 - 137;
        this.posY = this.height / 2 - 58;
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.posX + 50, this.posY + 75, 75, 20, I18n.getString("hdv.remove.confirm.yes")));
        this.buttonList.add(new GuiButton(1, this.posX + 260 - 75 - 50, this.posY + 75, 75, 20, I18n.getString("hdv.remove.confirm.no")));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        ClientEventHandler.STYLE.bindTexture("auction_house");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.posX, (float)this.posY, 0, 0, 275, 171, 275.0F, 256.0F, false);
        this.fontRenderer.drawString(I18n.getString("hdv.remove.confirm.title"), this.posX + 14 + 123 - this.fontRenderer.getStringWidth(I18n.getString("hdv.remove.confirm.title")) / 2, this.posY + 14, 0);
        int i = 0;

        for (Iterator var5 = this.textList.iterator(); var5.hasNext(); ++i)
        {
            String line = (String)var5.next();
            this.fontRenderer.drawString(line, this.posX + 14 + 123 - this.fontRenderer.getStringWidth(line) / 2, this.posY + 30 + 10 * i, 0);
        }

        super.drawScreen(par1, par2, par3);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        switch (par1GuiButton.id)
        {
            case 0:
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AuctionRemovePacket(this.auction.getUuid())));

                if (this.previous instanceof AuctionGui)
                {
                    AuctionGui auctionGui = (AuctionGui)this.previous;
                    auctionGui.removeAuction(this.auction);
                }

                this.mc.displayGuiScreen(this.previous);
                break;

            case 1:
                this.mc.displayGuiScreen(this.previous);
        }
    }
}
