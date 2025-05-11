/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.StatCollector
 *  org.lwjgl.input.Keyboard
 */
package net.ilexiconn.nationsgui.forge.client.gui.radio;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.ToggleGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RadioGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.component.GearButtonGUI;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetLoopingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetOpenPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetRedstonePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetSourcePacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

@SideOnly(value=Side.CLIENT)
public class RadioModeratorGUI
extends GuiScreen {
    private RadioGUI parent;
    private GuiTextField sourceField;

    public RadioModeratorGUI(RadioGUI parent) {
        this.parent = parent;
    }

    public void func_73866_w_() {
        int x = this.field_73880_f / 2 - 116;
        int y = this.field_73881_g / 2 - 74;
        Keyboard.enableRepeatEvents((boolean)true);
        this.field_73887_h.add(new CloseButtonGUI(0, x + 212, y + 13));
        this.field_73887_h.add(new GearButtonGUI(1, x + 192, y + 13, true));
        this.field_73887_h.add(new ToggleGUI(x + 189, y + 73, new ToggleGUI.ISliderCallback(){

            @Override
            public void call(boolean active) {
                ((RadioModeratorGUI)RadioModeratorGUI.this).parent.getBlockEntity().needsRedstone = active;
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SetRedstonePacket(RadioModeratorGUI.this.parent.getBlockEntity())));
            }
        }, this.parent.getBlockEntity().needsRedstone));
        this.field_73887_h.add(new ToggleGUI(x + 189, y + 96, new ToggleGUI.ISliderCallback(){

            @Override
            public void call(boolean active) {
                ((RadioModeratorGUI)RadioModeratorGUI.this).parent.getBlockEntity().looping = active;
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SetLoopingPacket(RadioModeratorGUI.this.parent.getBlockEntity())));
            }
        }, this.parent.getBlockEntity().looping));
        this.sourceField = new GuiTextField(this.field_73886_k, x + 77, y + 48, 139, 18);
        this.sourceField.func_73804_f(200);
        this.sourceField.func_73782_a(this.parent.getBlockEntity().source);
        this.sourceField.func_73803_e();
        this.sourceField.func_73796_b(true);
        this.field_73887_h.add(new ToggleGUI(x + 189, y + 119, new ToggleGUI.ISliderCallback(){

            @Override
            public void call(boolean active) {
                ((RadioModeratorGUI)RadioModeratorGUI.this).parent.getBlockEntity().canOpen = active;
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SetOpenPacket(RadioModeratorGUI.this.parent.getBlockEntity())));
            }
        }, this.parent.getBlockEntity().canOpen));
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        int x = this.field_73880_f / 2 - 116;
        int y = this.field_73881_g / 2 - 74;
        this.func_73873_v_();
        this.field_73882_e.func_110434_K().func_110577_a(RadioGUI.TEXTURE);
        this.func_73729_b(x, y, 0, 0, 233, 43);
        for (int i = 0; i < 8; ++i) {
            this.func_73729_b(x, y + 43 + 13 * i, 0, 243, 228, 13);
        }
        this.func_73729_b(x, y + 144, 0, 160, 235, 5);
        this.field_73886_k.func_78276_b(StatCollector.func_74838_a((String)"nationsgui.radio.source"), x + 16, y + 54, 0x6F6F6F);
        this.field_73886_k.func_78276_b(StatCollector.func_74838_a((String)"nationsgui.radio.source"), x + 16, y + 53, 0xFFFFFF);
        this.sourceField.func_73795_f();
        this.field_73886_k.func_78276_b(StatCollector.func_74838_a((String)"nationsgui.radio.redstone"), x + 16, y + 77, 0x6F6F6F);
        this.field_73886_k.func_78276_b(StatCollector.func_74838_a((String)"nationsgui.radio.redstone"), x + 16, y + 76, 0xFFFFFF);
        this.field_73886_k.func_78276_b(StatCollector.func_74838_a((String)"nationsgui.radio.loop"), x + 16, y + 100, 0x6F6F6F);
        this.field_73886_k.func_78276_b(StatCollector.func_74838_a((String)"nationsgui.radio.loop"), x + 16, y + 99, 0xFFFFFF);
        this.field_73886_k.func_78276_b(StatCollector.func_74838_a((String)"nationsgui.radio.open"), x + 16, y + 123, 0x6F6F6F);
        this.field_73886_k.func_78276_b(StatCollector.func_74838_a((String)"nationsgui.radio.open"), x + 16, y + 122, 0xFFFFFF);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_73875_a(GuiButton button) {
        if (button.field_73741_f == 0) {
            this.field_73882_e.func_71373_a(null);
        } else if (button.field_73741_f == 1) {
            this.field_73882_e.func_71373_a((GuiScreen)this.parent);
        }
    }

    public void func_73876_c() {
        this.sourceField.func_73780_a();
    }

    protected void func_73869_a(char character, int key) {
        if (!this.sourceField.func_73802_a(character, key)) {
            super.func_73869_a(character, key);
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int button) {
        this.sourceField.func_73793_a(mouseX, mouseY, button);
        super.func_73864_a(mouseX, mouseY, button);
    }

    public boolean func_73868_f() {
        return false;
    }

    public void func_73874_b() {
        Keyboard.enableRepeatEvents((boolean)false);
        if (!this.parent.getBlockEntity().source.equals(this.sourceField.func_73781_b())) {
            if (this.sourceField.func_73781_b().isEmpty()) {
                this.sourceField.func_73782_a("https://radio.nationsglory.fr/listen/ngradio/ngradio");
            }
            this.parent.getBlockEntity().source = this.sourceField.func_73781_b();
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SetSourcePacket(this.parent.getBlockEntity())));
        }
    }
}

