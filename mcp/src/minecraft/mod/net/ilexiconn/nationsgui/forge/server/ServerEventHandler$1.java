package net.ilexiconn.nationsgui.forge.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.capes.Cape;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.entity.data.NGPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

class ServerEventHandler$1 extends TimerTask
{
    final EntityPlayer val$entityPlayer;

    final EntityJoinWorldEvent val$event;

    final ServerEventHandler this$0;

    ServerEventHandler$1(ServerEventHandler this$0, EntityPlayer var2, EntityJoinWorldEvent var3)
    {
        this.this$0 = this$0;
        this.val$entityPlayer = var2;
        this.val$event = var3;
    }

    public void run()
    {
        if (this.val$entityPlayer != null && MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(this.val$entityPlayer.username) != null)
        {
            EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(this.val$entityPlayer.username);
            NGPlayerData data = NGPlayerData.get(player);

            if (data != null)
            {
                Iterator badges = (new ArrayList(data.getCapes())).iterator();

                while (badges.hasNext())
                {
                    Cape buddies = (Cape)badges.next();
                    NationsGUI.addSkinToPlayer(((EntityPlayer)this.val$event.entity).username, "capes_" + buddies.getIdentifier().toLowerCase());
                    data.removeCape(buddies);
                    System.out.println("[DEBUG] Convert old cape " + buddies.getIdentifier() + " for player " + player.username);
                }

                badges = (new ArrayList(data.getEmotes())).iterator();

                while (badges.hasNext())
                {
                    String var14 = (String)badges.next();
                    NationsGUI.addSkinToPlayer(((EntityPlayer)this.val$event.entity).username, "emotes_" + var14);
                    data.removeEmote(var14);
                    System.out.println("[DEBUG] Convert old emote " + var14 + " for player " + player.username);
                }
            }

            NBTTagCompound var13 = ((NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("Badges")).getCompoundTag(player.username);
            int playerFile;
            String e1;
            NBTTagCompound var16;

            if (var13.hasKey("AvailableBadges"))
            {
                String[] var15 = var13.getString("AvailableBadges").split(",");
                int aliases = var15.length;

                for (playerFile = 0; playerFile < aliases; ++playerFile)
                {
                    e1 = var15[playerFile];
                    NationsGUI.addSkinToPlayer(((EntityPlayer)this.val$event.entity).username, "badges_" + e1);
                    System.out.println("[DEBUG] Convert old badge " + e1 + " for player " + player.username);
                }

                var16 = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("Badges");
                var13.removeTag("AvailableBadges");
                var16.setCompoundTag(player.username, var13);
            }

            var16 = ((NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("Buddies")).getCompoundTag(player.username);
            NBTTagCompound var17 = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("Aliases");

            if (var16.hasKey("List"))
            {
                for (playerFile = 0; playerFile < var16.getTagList("List").tagCount(); ++playerFile)
                {
                    e1 = ((NBTTagString)var16.getTagList("List").tagAt(playerFile)).data;
                    String hats = null;
                    Iterator list = var17.getTags().iterator();

                    while (list.hasNext())
                    {
                        NBTTagString i = (NBTTagString)list.next();

                        if (i.data.equals(e1))
                        {
                            hats = i.getName();
                            break;
                        }
                    }

                    if (hats != null)
                    {
                        System.out.println("[DEBUG] Convert old buddy " + e1 + " (alias " + hats + ") for player " + player.username);
                        NationsGUI.addSkinToPlayer(((EntityPlayer)this.val$event.entity).username, "buddies_" + hats);
                    }
                }

                NBTTagCompound var18 = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("Buddies");
                var13.removeTag("List");
                var18.setCompoundTag(player.username, var13);
            }

            File var19 = new File(".", "/world/players/" + player.username + ".dat");

            if (var19.exists())
            {
                try
                {
                    NBTTagCompound var20 = CompressedStreamTools.readCompressed(new FileInputStream(var19));

                    if (var20.hasKey("NGHats"))
                    {
                        NBTTagCompound var21 = var20.getCompoundTag("NGHats");

                        if (var21.hasKey("HatsList") && var21.getTagList("HatsList").tagCount() > 0)
                        {
                            NBTTagList var22 = var21.getTagList("HatsList");

                            for (int var23 = 0; var23 < var22.tagCount(); ++var23)
                            {
                                NBTTagString tag = (NBTTagString)var22.tagAt(var23);
                                System.out.println("[DEBUG] Convert old hat " + tag.data + " for player " + player.username);
                                NationsGUI.addSkinToPlayer(((EntityPlayer)this.val$event.entity).username, "hats_" + tag.data);
                            }
                        }

                        var20.removeTag("NGHats");
                    }

                    CompressedStreamTools.writeCompressed(var20, new FileOutputStream(var19));
                    return;
                }
                catch (IOException var12)
                {
                    var12.printStackTrace();
                }
            }
        }
    }
}
