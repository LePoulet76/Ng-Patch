package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.emotes.ClientEmotesHandler;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.EmotesGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EmoteGui extends GuiScreen
{
    static ResourceLocation menu = new ResourceLocation("nationsgui", "emotes/gui/emote_wheel_base.png");
    Map<Integer, Integer[]> locs = new HashMap();
    private int backX;
    private int backY;
    private int menuX;
    private int menuY;
    private List<String> currentEmotes;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.backX = 185;
        this.backY = 172;
        this.menuX = (this.width - this.backX) / 2;
        this.menuY = (this.height - this.backY) / 2;
        this.locs.put(Integer.valueOf(0), new Integer[] {Integer.valueOf(31), Integer.valueOf(0), Integer.valueOf(61), Integer.valueOf(32)});
        this.locs.put(Integer.valueOf(1), new Integer[] {Integer.valueOf(94), Integer.valueOf(0), Integer.valueOf(124), Integer.valueOf(32)});
        this.locs.put(Integer.valueOf(2), new Integer[] {Integer.valueOf(0), Integer.valueOf(53), Integer.valueOf(30), Integer.valueOf(85)});
        this.locs.put(Integer.valueOf(3), new Integer[] {Integer.valueOf(125), Integer.valueOf(53), Integer.valueOf(155), Integer.valueOf(85)});
        this.locs.put(Integer.valueOf(4), new Integer[] {Integer.valueOf(31), Integer.valueOf(107), Integer.valueOf(61), Integer.valueOf(139)});
        this.locs.put(Integer.valueOf(5), new Integer[] {Integer.valueOf(94), Integer.valueOf(107), Integer.valueOf(124), Integer.valueOf(139)});
        this.currentEmotes = new ArrayList();
        List emoteSkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(Minecraft.getMinecraft().thePlayer.username, SkinType.EMOTES);
        Iterator var2 = emoteSkins.iterator();

        while (var2.hasNext())
        {
            AbstractSkin skin = (AbstractSkin)var2.next();
            this.currentEmotes.add(skin.getId());
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        GL11.glPushMatrix();
        String emote = "";
        this.bindTexture(menu);
        ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)this.menuX, (float)this.menuY, 0, 0, 185, 172, 256.0F, 256.0F, false);
        Iterator var5 = this.locs.entrySet().iterator();

        while (var5.hasNext())
        {
            Entry e = (Entry)var5.next();

            if (this.currentEmotes.size() > ((Integer)e.getKey()).intValue())
            {
                Integer[] coords = (Integer[])e.getValue();
                int textureX = coords[0].intValue();
                int textureY = coords[1].intValue();
                int pointX = coords[2].intValue();
                int pointY = coords[3].intValue();
                byte diameter = 58;
                int radius = diameter / 2;

                if (this.isInsideCircle(mouseX, mouseY, this.menuX + pointX, this.menuY + pointY, radius))
                {
                    this.bindTexture(menu);
                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)(this.menuX + textureX), (float)(this.menuY + textureY), 0, 190, 61, 66, 256.0F, 256.0F, false);
                    emote = this.currentEmotes.get(((Integer)e.getKey()).intValue()) != null && !((String)this.currentEmotes.get(((Integer)e.getKey()).intValue())).isEmpty() ? ((String)this.currentEmotes.get(((Integer)e.getKey()).intValue())).replaceAll("emotes_", "") : "";
                }

                if (this.currentEmotes.get(((Integer)e.getKey()).intValue()) != null && !((String)this.currentEmotes.get(((Integer)e.getKey()).intValue())).isEmpty())
                {
                    if (ClientProxy.SKIN_MANAGER.getSkinFromID((String)this.currentEmotes.get(((Integer)e.getKey()).intValue())) != null)
                    {
                        ClientProxy.SKIN_MANAGER.getSkinFromID((String)this.currentEmotes.get(((Integer)e.getKey()).intValue())).renderInGUI(this.menuX + pointX - 13, this.menuY + pointY - 12, 1.0F, par3);
                    }
                }
                else
                {
                    this.bindTexture(new ResourceLocation("nationsgui", "emotes/icons/null.png"));
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.menuX + pointX - 8), (float)(this.menuY + pointY - 8), 0, 0, 16, 16, 16.0F, 16.0F, false);
                }
            }
        }

        this.fontRenderer.drawString(emote, (this.width - this.fontRenderer.getStringWidth(emote)) / 2, (this.height - this.fontRenderer.FONT_HEIGHT) / 2, 16777215);
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, par3);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int par3)
    {
        super.mouseClicked(mouseX, mouseY, par3);
        Iterator var4 = this.locs.entrySet().iterator();

        while (var4.hasNext())
        {
            Entry e = (Entry)var4.next();
            int id = ((Integer)e.getKey()).intValue();
            Integer[] coords = (Integer[])e.getValue();
            int pointX = coords[2].intValue();
            int pointY = coords[3].intValue();
            byte diameter = 58;
            int radius = diameter / 2;

            if (this.isInsideCircle(mouseX, mouseY, this.menuX + pointX, this.menuY + pointY, radius))
            {
                String anim = "";
                int index = 0;
                AbstractSkin[] var14 = SkinType.EMOTES.getSkins();
                int var15 = var14.length;
                int var16 = 0;

                while (true)
                {
                    if (var16 < var15)
                    {
                        label36:
                        {
                            AbstractSkin skin = var14[var16];

                            if (ClientProxy.SKIN_MANAGER.playerHasSkin(Minecraft.getMinecraft().thePlayer.username, skin))
                            {
                                if (index == id)
                                {
                                    anim = skin.getId();
                                    break label36;
                                }

                                ++index;
                            }

                            ++var16;
                            continue;
                        }
                    }

                    ClientEmotesHandler.playEmote(anim.replaceAll("emotes_", ""), false);
                    Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                    break;
                }
            }
        }
    }

    private void bindTexture(ResourceLocation res)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(res);
    }

    private boolean isInsideCircle(int mouseX, int mouseY, int pointX, int pointY, int radius)
    {
        return (mouseX - pointX) * (mouseX - pointX) + (mouseY - pointY) * (mouseY - pointY) <= radius * radius;
    }

    private boolean iconExist(ResourceLocation rl)
    {
        return EmotesGUI.class.getResourceAsStream("/assets/" + rl.getResourceDomain() + "/" + rl.getResourcePath()) != null;
    }
}
