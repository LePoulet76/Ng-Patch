package net.ilexiconn.nationsgui.forge.client.gui.radio;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RadioGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.radio.component.MailButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.component.PlayButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.component.VolumeButtonGUI;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetPlayingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetVolumePacket;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RadioGUI extends GuiScreen implements Runnable
{
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/gui/radio.png");
    private RadioBlockEntity blockEntity;
    private PlayButtonGUI playButton;
    private VolumeButtonGUI softerButton;
    private VolumeButtonGUI louderButton;
    private String displayText = "";
    private int updateTimer;

    public RadioGUI(RadioBlockEntity blockEntity)
    {
        this.blockEntity = blockEntity;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int x = this.width / 2 - 116;
        int y = this.height / 2 - 82;
        this.buttonList.add(new CloseButtonGUI(0, x + 212, y + 13));
        this.buttonList.add(this.playButton = new PlayButtonGUI(1, x + 186, y + 110));
        this.buttonList.add(this.softerButton = new VolumeButtonGUI(2, x + 143, y + 135));
        this.buttonList.add(this.louderButton = new VolumeButtonGUI(3, x + 164, y + 135));
        this.louderButton.louder = true;
        this.buttonList.add(new MailButtonGUI(4, x + 143, y + 110));
        PermissionCache.INSTANCE.checkPermission(PermissionType.RADIO_MODERATION, new RadioGUI$1(this), new String[0]);
        (new Thread(this)).start();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ++this.updateTimer;

        if (this.updateTimer > 100)
        {
            this.updateTimer = 0;
            (new Thread(this)).start();
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        int x = this.width / 2 - 116;
        int y = this.height / 2 - 82;
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(x, y, 0, 0, 233, 165);
        this.fontRenderer.drawSplitString(this.displayText, x + 32, y + 49, 182, 5604619);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        int sliderX = MathHelper.abs_int(this.blockEntity.source.hashCode()) % 188;
        this.drawTexturedModalRect(x + sliderX, y + 54, 233, 0, 17, 59);
        GL11.glDisable(GL11.GL_BLEND);
        this.softerButton.enabled = !this.blockEntity.needsRedstone && this.blockEntity.volume > 0;
        this.louderButton.enabled = !this.blockEntity.needsRedstone && this.blockEntity.volume < 10;
        this.playButton.play = this.blockEntity.streamer == null || !this.blockEntity.streamer.isPlaying();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (button.id == 1)
        {
            this.blockEntity.playing = this.playButton.play;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SetPlayingPacket(this.blockEntity)));
        }
        else if (button.id == 2)
        {
            --this.blockEntity.volume;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SetVolumePacket(this.blockEntity)));
        }
        else if (button.id == 3)
        {
            ++this.blockEntity.volume;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SetVolumePacket(this.blockEntity)));
        }
        else if (button.id == 4)
        {
            this.mc.displayGuiScreen(new RequestGUI(this));
        }
        else if (button.id == 5)
        {
            this.mc.displayGuiScreen(new RadioModeratorGUI(this));
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public RadioBlockEntity getBlockEntity()
    {
        return this.blockEntity;
    }

    public void run()
    {
        if (this.blockEntity.source.equals("https://radio.nationsglory.fr/listen/ngradio/ngradio"))
        {
            try
            {
                InputStream e = (new URL("https://apiv2.nationsglory.fr/mods/radio/titlesong")).openStream();

                try
                {
                    String s = IOUtils.toString(e);

                    if (!s.isEmpty())
                    {
                        this.displayText = s;
                    }
                }
                finally
                {
                    IOUtils.closeQuietly(e);
                }
            }
            catch (IOException var7)
            {
                var7.printStackTrace();
            }
        }
        else
        {
            this.displayText = "";
        }
    }

    static List access$000(RadioGUI x0)
    {
        return x0.buttonList;
    }
}
