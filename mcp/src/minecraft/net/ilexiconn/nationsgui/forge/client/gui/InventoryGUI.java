/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.core.network.GCCorePacketHandlerServer$EnumPacketServer
 *  micdoodle8.mods.galacticraft.core.util.PacketUtil
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import micdoodle8.mods.galacticraft.core.network.GCCorePacketHandlerServer;
import micdoodle8.mods.galacticraft.core.util.PacketUtil;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.BossEdoraGui;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.MailGUI;
import net.ilexiconn.nationsgui.forge.client.gui.QuestGUI;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.SkillsGui;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseListGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionCreateGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.SellItemGUI;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandListGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionNamePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MarketPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.OpenRecipeGUIPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class InventoryGUI
extends GuiContainer {
    public static final List<GuiScreenTab> TABS = new ArrayList<GuiScreenTab>();
    private final Map<Class<? extends GuiScreen>, Integer> tabAlerts = new HashMap<Class<? extends GuiScreen>, Integer>();
    private final EntityPlayer player;
    public static boolean isInAssault;
    public static boolean achievementDone;

    public InventoryGUI(EntityPlayer player) {
        super(player.field_71069_bz);
        this.player = player;
        this.field_74194_b = 182;
        this.field_74195_c = 223;
        if (ClientProxy.serverType.equals("ng")) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionNamePacket()));
        }
        PacketCallbacks.MONEY.send(new String[0]);
        if (System.currentTimeMillis() - ClientEventHandler.joinTime > 300000L && !achievementDone) {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_5_minutes", 1)));
        }
    }

    protected void func_73869_a(char par1, int par2) {
        if (par2 == ClientKeyHandler.KEY_SELL.field_74512_d) {
            try {
                ItemStack itemStack;
                Method method = GuiContainer.class.getDeclaredMethod("getSlotAtPosition", Integer.TYPE, Integer.TYPE);
                method.setAccessible(true);
                Slot slot = (Slot)method.invoke((Object)this, Mouse.getEventX() * this.field_73880_f / Minecraft.func_71410_x().field_71443_c, this.field_73881_g - Mouse.getEventY() * this.field_73881_g / Minecraft.func_71410_x().field_71440_d - 1);
                if (slot != null && (itemStack = slot.func_75211_c()) != null) {
                    this.field_73882_e.func_71373_a((GuiScreen)new SellItemGUI(itemStack, slot.field_75222_d));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.func_73869_a(par1, par2);
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_73887_h.add(new RecipeListGUI.BookButton(0, this.field_73880_f / 2 - 6, this.field_73881_g / 2 - 29));
        this.field_73887_h.add(new GalacticraftButton(1, this.field_73880_f / 2 - 6, this.field_73881_g / 2 - 29 - 21));
        this.field_73887_h.add(new EdoraBossButton(2, this.field_73880_f / 2 - 6, this.field_73881_g / 2 - 29 + 21));
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        if (par1GuiButton.field_73741_f == 0) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new OpenRecipeGUIPacket()));
        } else if (par1GuiButton.field_73741_f == 1) {
            PacketDispatcher.sendPacketToServer((Packet)PacketUtil.createPacket((String)"GalacticraftCore", (GCCorePacketHandlerServer.EnumPacketServer)GCCorePacketHandlerServer.EnumPacketServer.OPEN_EXTENDED_INVENTORY, (Object[])new Object[0]));
        } else if (par1GuiButton.field_73741_f == 2) {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new BossEdoraGui(false));
        }
    }

    protected void func_74189_g(int mouseX, int mouseY) {
        int i;
        for (i = 0; i <= 4; ++i) {
            if (mouseX < this.field_74198_m - (((Object)((Object)this)).getClass() == TABS.get(i).getClassReferent() ? 23 : 20) || mouseX > this.field_74198_m + 3 || mouseY < this.field_74197_n + 55 + i * 31 || mouseY > this.field_74197_n + 85 + i * 31) continue;
            this.func_74190_a(I18n.func_135053_a((String)("gui.inventory.tab." + i)), mouseX - this.field_74198_m, mouseY - this.field_74197_n);
        }
        for (i = 5; i < TABS.size(); ++i) {
            if (i == 5 && isInAssault || mouseX < this.field_74198_m + 178 || mouseX > this.field_74198_m + 201 || mouseY < this.field_74197_n + 55 + (i - 4) * 31 || mouseY > this.field_74197_n + 55 + 31 + (i - 4) * 31) continue;
            this.func_74190_a(I18n.func_135053_a((String)("gui.inventory.tab." + i)), mouseX - this.field_74198_m, mouseY - this.field_74197_n);
        }
        if (mouseX >= this.field_74198_m + 109 && mouseX <= this.field_74198_m + 109 + 13 && mouseY >= this.field_74197_n + 51 && mouseY <= this.field_74197_n + 51 + 14) {
            this.func_74190_a("Money", mouseX - this.field_74198_m, mouseY - this.field_74197_n);
        }
    }

    protected void func_74185_a(float partialTicks, int mouseX, int mouseY) {
        int y;
        int x;
        GuiScreenTab type;
        int i;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("inventory");
        this.func_73729_b(this.field_74198_m, this.field_74197_n, 0, 0, this.field_74194_b, this.field_74195_c);
        for (i = 0; i <= 4; ++i) {
            type = TABS.get(i);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            x = i % 3;
            y = i / 3;
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                this.func_73729_b(this.field_74198_m - 23, this.field_74197_n + 55 + i * 31, 23, 223, 29, 30);
                this.func_73729_b(this.field_74198_m - 23 + 3, this.field_74197_n + 55 + i * 31 + 5, 182 + x * 20, y * 20, 20, 20);
                continue;
            }
            this.func_73729_b(this.field_74198_m - 20, this.field_74197_n + 55 + i * 31, 0, 223, 23, 30);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            this.func_73729_b(this.field_74198_m - 20 + 3, this.field_74197_n + 55 + i * 31 + 5, 182 + x * 20, y * 20, 20, 20);
            GL11.glDisable((int)3042);
        }
        for (i = 5; i < TABS.size(); ++i) {
            type = TABS.get(i);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            x = i % 3;
            y = i / 3;
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                this.func_73729_b(this.field_74198_m + 178, this.field_74197_n + 55 + (i - 4) * 31, 85, 223, 29, 30);
                this.func_73729_b(this.field_74198_m + 175, this.field_74197_n + 55 + (i - 4) * 31 + 5, 182 + x * 20, y * 20, 20, 20);
                continue;
            }
            this.func_73729_b(this.field_74198_m + 178, this.field_74197_n + 55 + (i - 4) * 31, 114, 223, 23, 30);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            this.func_73729_b(this.field_74198_m + 179, this.field_74197_n + 55 + (i - 4) * 31 + 5, 182 + x * 20, y * 20, 20, 20);
            GL11.glDisable((int)3042);
        }
        ClientData.currentFaction = ClientData.currentFaction.replaceAll("^\\\u00a7[0-9a-z]", "");
        if (ClientProxy.serverType.equals("ng") && ClientData.currentFaction != null) {
            ClientEventHandler.STYLE.bindTexture("faction_btn");
            ModernGui.drawModalRectWithCustomSizedTexture(this.field_74198_m + 7, this.field_74197_n + 223, 0, 233, 169, 23, 256.0f, 256.0f, false);
            ClientEventHandler.STYLE.bindTexture("inventory");
            if (!ClientData.currentFaction.contains("Wilderness")) {
                Double stringWidth = (double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"gui.inventory.tab.country")) * 1.2 / 2.0;
                this.func_73729_b(this.field_74198_m + 91 - stringWidth.intValue() - 17, this.field_74197_n + 225, 201, 107, 12, 17);
                this.drawScaledString(I18n.func_135053_a((String)"gui.inventory.tab.country"), this.field_74198_m + 93 - stringWidth.intValue(), this.field_74197_n + 230, 0xFFFFFF, 1.2f, false, true);
            } else {
                Double stringWidth = (double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"gui.inventory.tab.create")) * 1.2 / 2.0;
                this.func_73729_b(this.field_74198_m + 91 - stringWidth.intValue() - 17, this.field_74197_n + 225, 185, 107, 12, 17);
                this.drawScaledString(I18n.func_135053_a((String)"gui.inventory.tab.create"), this.field_74198_m + 93 - stringWidth.intValue(), this.field_74197_n + 230, 0xFFFFFF, 1.2f, false, true);
            }
        }
        this.drawScaledString(this.player.getDisplayName(), this.field_74198_m + 90, this.field_74197_n + 10, 0xFFFFFF, 1.7f, true, true);
        this.field_73886_k.func_78276_b(ClientData.currentFaction, this.field_74198_m + 90 - this.field_73886_k.func_78256_a(ClientData.currentFaction) / 2, this.field_74197_n + 28, 0xC6C6C6);
        this.field_73882_e.field_71466_p.func_85187_a((int)ShopGUI.CURRENT_MONEY + " $", this.field_74198_m + 128, this.field_74197_n + 54, 0xFFFFFF, true);
        GuiInventory.func_110423_a((int)(this.field_74198_m + 55), (int)(this.field_74197_n + 114), (int)30, (float)(this.field_74198_m + 55 - mouseX), (float)(this.field_74197_n + 64 - mouseY), (EntityLivingBase)this.field_73882_e.field_71439_g);
        String text = I18n.func_135053_a((String)"key.inventory");
        int pX = this.field_74198_m + 10 + 81;
        int w = this.field_73886_k.func_78256_a(text);
        this.field_73882_e.field_71466_p.func_85187_a(text, pX - w / 2, this.field_74197_n + 128, 0xFFFFFF, true);
        InventoryGUI.func_73734_a((int)(this.field_74198_m + 10), (int)(this.field_74197_n + 134), (int)(pX - w / 2 - 3), (int)(this.field_74197_n + 135), (int)-1);
        InventoryGUI.func_73734_a((int)(pX + w / 2 + 3), (int)(this.field_74197_n + 134), (int)(this.field_74198_m + 171), (int)(this.field_74197_n + 135), (int)-1);
        InventoryGUI.func_73734_a((int)(this.field_74198_m + 10), (int)(this.field_74197_n + 135), (int)(pX - w / 2 - 3), (int)(this.field_74197_n + 136), (int)-10197916);
        InventoryGUI.func_73734_a((int)(pX + w / 2 + 3), (int)(this.field_74197_n + 135), (int)(this.field_74198_m + 171), (int)(this.field_74197_n + 136), (int)-10197916);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            GuiScreenTab type;
            int i;
            for (i = 0; i <= 4; ++i) {
                type = TABS.get(i);
                if (mouseX < this.field_74198_m - 20 || mouseX > this.field_74198_m + 3 || mouseY < this.field_74197_n + 55 + i * 31 || mouseY > this.field_74197_n + 85 + i * 31) continue;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                try {
                    this.field_73882_e.field_71439_g.func_71053_j();
                    type.call();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (i = 5; i < TABS.size(); ++i) {
                if (i == 5 && isInAssault) continue;
                type = TABS.get(i);
                if (mouseX < this.field_74198_m + 178 || mouseX > this.field_74198_m + 201 || mouseY < this.field_74197_n + 55 + (i - 4) * 31 || mouseY > this.field_74197_n + 55 + 31 + (i - 4) * 31) continue;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                try {
                    this.field_73882_e.field_71439_g.func_71053_j();
                    type.call();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ClientProxy.serverType.equals("ng") && mouseX >= this.field_74198_m + 7 && mouseX <= this.field_74198_m + 7 + 169 && mouseY >= this.field_74197_n + 223 && mouseY <= this.field_74197_n + 223 + 23 && ClientData.currentFaction != null) {
                if (!ClientData.currentFaction.contains("Wilderness")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGUI(ClientData.currentFaction));
                } else {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionCreateGUI());
                }
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    static {
        achievementDone = false;
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return InventoryGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new InventoryGUI((EntityPlayer)Minecraft.func_71410_x().field_71439_g));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return MailGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new MailGUI((EntityPlayer)Minecraft.func_71410_x().field_71439_g));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return null;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new CosmeticGUI(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return null;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new QuestGUI((EntityPlayer)Minecraft.func_71410_x().field_71439_g));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return SkillsGui.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new SkillsGui(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return null;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new MarketGui());
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MarketPacket("", "", 0, false)));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return null;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionListGUI());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return null;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseListGui());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return null;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandListGui((EntityPlayer)Minecraft.func_71410_x().field_71439_g));
            }
        });
    }

    public static class EdoraBossButton
    extends GuiButton {
        public EdoraBossButton(int id, int posX, int posY) {
            super(id, posX, posY, 20, 18, "");
        }

        protected int func_73738_a(boolean par1) {
            return par1 ? 220 : 238;
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            if (this.field_73748_h) {
                ClientEventHandler.STYLE.bindTexture("inventory");
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
                int k = this.func_73738_a(this.field_82253_i);
                this.func_73729_b(this.field_73746_c, this.field_73743_d, 216, k, 20, 18);
                this.func_73739_b(par1Minecraft, par2, par3);
            }
        }
    }

    public static class GalacticraftButton
    extends GuiButton {
        public GalacticraftButton(int id, int posX, int posY) {
            super(id, posX, posY, 20, 18, "");
        }

        protected int func_73738_a(boolean par1) {
            return par1 ? 220 : 238;
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            if (this.field_73748_h) {
                ClientEventHandler.STYLE.bindTexture("inventory");
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
                int k = this.func_73738_a(this.field_82253_i);
                this.func_73729_b(this.field_73746_c, this.field_73743_d, 236, k, 20, 18);
                this.func_73739_b(par1Minecraft, par2, par3);
            }
        }
    }
}

