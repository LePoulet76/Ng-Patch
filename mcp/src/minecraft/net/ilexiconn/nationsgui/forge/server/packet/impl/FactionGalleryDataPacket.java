package net.ilexiconn.nationsgui.forge.server.packet.impl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.GalleryGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGalleryDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionGalleryDataPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, Object>> galleryImages = new ArrayList();
    public String target;

    public FactionGalleryDataPacket(String targetName)
    {
        this.target = targetName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.galleryImages = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionGalleryDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.target);
    }
    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        GalleryGUI.loaded = true;
        GalleryGUI.selectedImageIndex = 0;
        GalleryGUI.firstImageCarouselIndex = 0;
        GalleryGUI.galleryImages = this.galleryImages;
    }
}
