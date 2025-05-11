/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.network.packet.Packet
 *  noppes.npcs.NoppesUtilServer
 *  noppes.npcs.constants.EnumPacketType
 *  noppes.npcs.controllers.RecipeCarpentry
 *  noppes.npcs.controllers.RecipeController
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;

public class OpenRecipeGUIPacket
implements IPacket,
IServerPacket,
IClientPacket {
    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        Minecraft.func_71410_x().func_71373_a((GuiScreen)new RecipeListGUI(Minecraft.func_71410_x().field_71462_r));
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        try {
            RecipeController controller = RecipeController.instance;
            NBTTagList list = new NBTTagList();
            for (RecipeCarpentry recipe : controller.globalRecipes.values()) {
                list.func_74742_a((NBTBase)recipe.writeNBT());
            }
            NBTTagCompound compound = new NBTTagCompound();
            compound.func_74782_a("recipes", (NBTBase)list);
            NoppesUtilServer.sendData((EntityPlayer)player, (EnumPacketType)EnumPacketType.SyncRecipes, (Object[])new Object[]{compound});
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new OpenRecipeGUIPacket()), (Player)((Player)player));
    }
}

