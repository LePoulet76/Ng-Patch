/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  org.apache.commons.io.IOUtils
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.radio;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RadioModeratorGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RequestGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.component.GearButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.component.MailButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.component.PlayButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.component.VolumeButtonGUI;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetPlayingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetVolumePacket;
import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class RadioGUI
extends GuiScreen
implements Runnable {
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/gui/radio.png");
    private RadioBlockEntity blockEntity;
    private PlayButtonGUI playButton;
    private VolumeButtonGUI softerButton;
    private VolumeButtonGUI louderButton;
    private String displayText = "";
    private int updateTimer;

    public RadioGUI(RadioBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    public void func_73866_w_() {
        int x = this.field_73880_f / 2 - 116;
        int y = this.field_73881_g / 2 - 82;
        this.field_73887_h.add(new CloseButtonGUI(0, x + 212, y + 13));
        this.playButton = new PlayButtonGUI(1, x + 186, y + 110);
        this.field_73887_h.add(this.playButton);
        this.softerButton = new VolumeButtonGUI(2, x + 143, y + 135);
        this.field_73887_h.add(this.softerButton);
        this.louderButton = new VolumeButtonGUI(3, x + 164, y + 135);
        this.field_73887_h.add(this.louderButton);
        this.louderButton.louder = true;
        this.field_73887_h.add(new MailButtonGUI(4, x + 143, y + 110));
        PermissionCache.INSTANCE.checkPermission(PermissionType.RADIO_MODERATION, new IPermissionCallback(){

            @Override
            public void call(String permission, boolean has) {
                if (has) {
                    int x = RadioGUI.this.field_73880_f / 2 - 116;
                    int y = RadioGUI.this.field_73881_g / 2 - 82;
                    RadioGUI.this.field_73887_h.add(new GearButtonGUI(5, x + 192, y + 13));
                }
            }
        }, new String[0]);
        new Thread(this).start();
    }

    public void func_73876_c() {
        ++this.updateTimer;
        if (this.updateTimer > 100) {
            this.updateTimer = 0;
            new Thread(this).start();
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        int x = this.field_73880_f / 2 - 116;
        int y = this.field_73881_g / 2 - 82;
        this.func_73873_v_();
        this.field_73882_e.func_110434_K().func_110577_a(TEXTURE);
        this.func_73729_b(x, y, 0, 0, 233, 165);
        this.field_73886_k.func_78279_b(this.displayText, x + 32, y + 49, 182, 5604619);
        this.field_73882_e.func_110434_K().func_110577_a(TEXTURE);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        int sliderX = MathHelper.func_76130_a((int)this.blockEntity.source.hashCode()) % 188;
        this.func_73729_b(x + sliderX, y + 54, 233, 0, 17, 59);
        GL11.glDisable((int)3042);
        this.softerButton.field_73742_g = !this.blockEntity.needsRedstone && this.blockEntity.volume > 0;
        this.louderButton.field_73742_g = !this.blockEntity.needsRedstone && this.blockEntity.volume < 10;
        this.playButton.play = this.blockEntity.streamer == null || !this.blockEntity.streamer.isPlaying();
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_73875_a(GuiButton button) {
        if (button.field_73741_f == 0) {
            this.field_73882_e.func_71373_a(null);
        } else if (button.field_73741_f == 1) {
            this.blockEntity.playing = this.playButton.play;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SetPlayingPacket(this.blockEntity)));
        } else if (button.field_73741_f == 2) {
            --this.blockEntity.volume;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SetVolumePacket(this.blockEntity)));
        } else if (button.field_73741_f == 3) {
            ++this.blockEntity.volume;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SetVolumePacket(this.blockEntity)));
        } else if (button.field_73741_f == 4) {
            this.field_73882_e.func_71373_a((GuiScreen)new RequestGUI(this));
        } else if (button.field_73741_f == 5) {
            this.field_73882_e.func_71373_a((GuiScreen)new RadioModeratorGUI(this));
        }
    }

    public boolean func_73868_f() {
        return false;
    }

    public RadioBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    @Override
    public void run() {
        if (this.blockEntity.source.equals("https://radio.nationsglory.fr/listen/ngradio/ngradio")) {
            try {
                InputStream inputStream = new URL("https://apiv2.nationsglory.fr/mods/radio/titlesong").openStream();
                try {
                    String s = IOUtils.toString((InputStream)inputStream);
                    if (!s.isEmpty()) {
                        this.displayText = s;
                    }
                }
                finally {
                    IOUtils.closeQuietly((InputStream)inputStream);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.displayText = "";
        }
    }
}

