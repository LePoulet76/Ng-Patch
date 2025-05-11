/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockDoor
 *  net.minecraft.block.BlockMobSpawner
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.EnumArmorMaterial
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.nbt.NBTTagString
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.management.ServerConfigurationManager
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.world.World
 *  net.minecraftforge.event.ForgeSubscribe
 *  net.minecraftforge.event.entity.EntityJoinWorldEvent
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.event.entity.living.LivingHurtEvent
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$Action
 *  net.minecraftforge.event.world.BlockEvent
 *  net.minecraftforge.event.world.BlockEvent$BreakEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 */
package net.ilexiconn.nationsgui.forge.server;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.itemmanager.CommonProxy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIHooks;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ItemFoodTransformer;
import net.ilexiconn.nationsgui.forge.server.capes.Cape;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.entity.data.NGPlayerData;
import net.ilexiconn.nationsgui.forge.server.event.PotionEffectEvent;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmor;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONEffect;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BonusDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ClickLimiterPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.DateTimeSyncPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EntityDamagePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ObjectivePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.StopSoundsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.UpdatePotionsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.UpdateSkillsLevelClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WorldNameSyncPacket;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;

public enum ServerEventHandler {
    INSTANCE;

    static String MAIN_BAGDE;
    static HashMap<String, Long> lastPlayerEffect;
    static HashMap<String, Long> lastPlayerAttack;
    public Map<String, Integer> nameToID;
    public Map<Integer, String> idToName;

    @ForgeSubscribe
    public void onItemEntitySpawn(EntityJoinWorldEvent event) {
        EntityItem entityItem;
        ItemStack itemStack;
        if (event.entity instanceof EntityItem && (itemStack = (entityItem = (EntityItem)event.entity).func_92059_d()) != null && itemStack.func_77973_b() instanceof ItemFood) {
            ItemFoodTransformer.updateFoodStack((ItemFood)itemStack.func_77973_b(), itemStack, event.world, null, 0, false);
        }
    }

    @ForgeSubscribe
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        ItemStack[] armor;
        if (event.entity instanceof EntityPlayer && !event.entity.field_70170_p.field_72995_K && (armor = ((EntityPlayer)event.entity).field_71071_by.field_70460_b)[3] != null && armor[3].func_77973_b() instanceof JSONArmor && (!lastPlayerEffect.containsKey(((EntityPlayer)event.entity).getDisplayName()) || System.currentTimeMillis() - lastPlayerEffect.get(((EntityPlayer)event.entity).getDisplayName()) > 10000L)) {
            lastPlayerEffect.put(((EntityPlayer)event.entity).getDisplayName(), System.currentTimeMillis());
            JSONArmor helmet = (JSONArmor)armor[3].func_77973_b();
            if (helmet.effects != null) {
                EnumArmorMaterial material = helmet.func_82812_d();
                for (int i = 0; i < armor.length - 1; ++i) {
                    if (armor[i] != null && armor[i].func_77973_b() instanceof JSONArmor) {
                        JSONArmor current = (JSONArmor)armor[i].func_77973_b();
                        if (current.func_82812_d() == material) continue;
                        return;
                    }
                    return;
                }
                for (JSONEffect effect : helmet.effects) {
                    effect.applyEffect((EntityPlayer)event.entity);
                }
            }
        }
    }

    @ForgeSubscribe
    public void onEntityAttack(AttackEntityEvent event) {
        if (!lastPlayerAttack.containsKey(((EntityPlayer)event.entity).getDisplayName()) || System.currentTimeMillis() - lastPlayerAttack.get(((EntityPlayer)event.entity).getDisplayName()) > 66L) {
            lastPlayerAttack.put(((EntityPlayer)event.entity).getDisplayName(), System.currentTimeMillis());
            return;
        }
        event.setCanceled(true);
    }

    @ForgeSubscribe
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block;
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK && !event.entityPlayer.field_71075_bZ.field_75098_d && (block = Block.field_71973_m[event.entity.field_70170_p.func_72798_a(event.x, event.y, event.z)]) instanceof BlockDoor) {
            event.entity.field_70170_p.func_72908_a((double)event.x, (double)event.y, (double)event.z, "mob.zombie.wood", 0.5f, 1.75f);
        }
    }

    @ForgeSubscribe
    public void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayerMP) {
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new DateTimeSyncPacket(System.currentTimeMillis())), (Player)((Player)event.entity));
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new WorldNameSyncPacket(event.world.func_72912_H().func_76065_j())), (Player)((Player)event.entity));
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP)event.entity;
            String playerIP = entityPlayerMP.field_71135_a.field_72575_b.func_74430_c().toString().split(":")[0].replace("/", "");
            NationsGUIHooks.allowedIPConnection.remove(playerIP);
        }
        if (event.entity instanceof EntityPlayer && event.world.field_72995_K && Minecraft.func_71410_x().field_71439_g.equals((Object)event.entity)) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ObjectivePacket()));
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new BonusDataPacket()));
        }
        if (event.entity instanceof EntityPlayer && !event.world.field_72995_K) {
            final EntityPlayer entityPlayer = (EntityPlayer)event.entity;
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new StopSoundsPacket()), (Player)((Player)event.entity));
            new Timer().schedule(new TimerTask(){

                @Override
                public void run() {
                    if (entityPlayer != null && MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(entityPlayer.field_71092_bJ) != null) {
                        File playerFile;
                        NBTTagCompound badges;
                        EntityPlayerMP player = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(entityPlayer.field_71092_bJ);
                        NGPlayerData data = NGPlayerData.get((EntityPlayer)player);
                        if (data != null) {
                            for (Cape cape : new ArrayList<Cape>(data.getCapes())) {
                                NationsGUI.addSkinToPlayer(((EntityPlayer)event.entity).field_71092_bJ, "capes_" + cape.getIdentifier().toLowerCase());
                                data.removeCape(cape);
                                System.out.println("[DEBUG] Convert old cape " + cape.getIdentifier() + " for player " + player.field_71092_bJ);
                            }
                            for (String string : new ArrayList<String>(data.getEmotes())) {
                                NationsGUI.addSkinToPlayer(((EntityPlayer)event.entity).field_71092_bJ, "emotes_" + string);
                                data.removeEmote(string);
                                System.out.println("[DEBUG] Convert old emote " + string + " for player " + player.field_71092_bJ);
                            }
                        }
                        if ((badges = ((NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("Badges")).func_74775_l(player.field_71092_bJ)).func_74764_b("AvailableBadges")) {
                            for (String badge : badges.func_74779_i("AvailableBadges").split(",")) {
                                NationsGUI.addSkinToPlayer(((EntityPlayer)event.entity).field_71092_bJ, "badges_" + badge);
                                System.out.println("[DEBUG] Convert old badge " + badge + " for player " + player.field_71092_bJ);
                            }
                            NBTTagCompound nBTTagCompound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("Badges");
                            badges.func_82580_o("AvailableBadges");
                            nBTTagCompound.func_74766_a(player.field_71092_bJ, badges);
                        }
                        NBTTagCompound nBTTagCompound = ((NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("Buddies")).func_74775_l(player.field_71092_bJ);
                        NBTTagCompound aliases = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("Aliases");
                        if (nBTTagCompound.func_74764_b("List")) {
                            for (int i = 0; i < nBTTagCompound.func_74761_m("List").func_74745_c(); ++i) {
                                String buddy = ((NBTTagString)nBTTagCompound.func_74761_m((String)"List").func_74743_b((int)i)).field_74751_a;
                                String aliasName = null;
                                for (NBTTagString alias : aliases.func_74758_c()) {
                                    if (!alias.field_74751_a.equals(buddy)) continue;
                                    aliasName = alias.func_74740_e();
                                    break;
                                }
                                if (aliasName == null) continue;
                                System.out.println("[DEBUG] Convert old buddy " + buddy + " (alias " + aliasName + ") for player " + player.field_71092_bJ);
                                NationsGUI.addSkinToPlayer(((EntityPlayer)event.entity).field_71092_bJ, "buddies_" + aliasName);
                            }
                            NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("Buddies");
                            badges.func_82580_o("List");
                            compound.func_74766_a(player.field_71092_bJ, badges);
                        }
                        if ((playerFile = new File(".", "/world/players/" + player.field_71092_bJ + ".dat")).exists()) {
                            try {
                                NBTTagCompound comp = CompressedStreamTools.func_74796_a((InputStream)new FileInputStream(playerFile));
                                if (comp.func_74764_b("NGHats")) {
                                    NBTTagCompound hats = comp.func_74775_l("NGHats");
                                    if (hats.func_74764_b("HatsList") && hats.func_74761_m("HatsList").func_74745_c() > 0) {
                                        NBTTagList list = hats.func_74761_m("HatsList");
                                        for (int i = 0; i < list.func_74745_c(); ++i) {
                                            NBTTagString tag = (NBTTagString)list.func_74743_b(i);
                                            System.out.println("[DEBUG] Convert old hat " + tag.field_74751_a + " for player " + player.field_71092_bJ);
                                            NationsGUI.addSkinToPlayer(((EntityPlayer)event.entity).field_71092_bJ, "hats_" + tag.field_74751_a);
                                        }
                                    }
                                    comp.func_82580_o("NGHats");
                                }
                                CompressedStreamTools.func_74799_a((NBTTagCompound)comp, (OutputStream)new FileOutputStream(playerFile));
                                return;
                            }
                            catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }, 5000L);
            ClickLimiterPacket clickLimiterPacket = new ClickLimiterPacket();
            clickLimiterPacket.limit = CommonProxy.localConfig.getClickLimit();
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(clickLimiterPacket), (Player)((Player)event.entity));
        }
    }

    @ForgeSubscribe
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.block instanceof BlockMobSpawner) {
            TileEntityMobSpawner tileEntity = (TileEntityMobSpawner)event.world.func_72796_p(event.x, event.y, event.z);
            int entityID = this.nameToID.get(tileEntity.func_98049_a().func_98276_e());
            if (NationsGUI.CONFIG.spawnerBlacklist.contains(entityID)) {
                return;
            }
            event.setExpToDrop(0);
            if (!event.getPlayer().field_71075_bZ.field_75098_d && !event.world.field_72995_K && event.world.func_82736_K().func_82766_b("doTileDrops")) {
                float radius = 0.7f;
                double x = (double)(event.world.field_73012_v.nextFloat() * radius) + (double)(1.0f - radius) * 0.5;
                double y = (double)(event.world.field_73012_v.nextFloat() * radius) + (double)(1.0f - radius) * 0.5;
                double z = (double)(event.world.field_73012_v.nextFloat() * radius) + (double)(1.0f - radius) * 0.5;
                EntityItem entityitem = new EntityItem(event.world, (double)event.x + x, (double)event.y + y, (double)event.z + z, new ItemStack(383, 1, entityID));
                entityitem.field_70293_c = 10;
                event.world.func_72838_d((Entity)entityitem);
            }
        }
    }

    @ForgeSubscribe
    public void onPotionStart(PotionEffectEvent.Start event) {
        PacketDispatcher.sendPacketToAllPlayers((Packet)PacketRegistry.INSTANCE.generatePacket(new UpdatePotionsPacket(event.entity.field_70157_k, event.effect.func_76456_a(), true)));
    }

    @ForgeSubscribe
    public void onPotionFinish(PotionEffectEvent.Finish event) {
        PacketDispatcher.sendPacketToAllPlayers((Packet)PacketRegistry.INSTANCE.generatePacket(new UpdatePotionsPacket(event.entity.field_70157_k, event.effect.func_76456_a(), false)));
    }

    @ForgeSubscribe
    public void onLoadWorld(WorldEvent.Load event) {
        this.loadEntityIDs(event.world);
    }

    public void loadEntityIDs(World world) {
        if (this.nameToID != null && this.idToName != null) {
            return;
        }
        this.nameToID = new HashMap<String, Integer>();
        this.idToName = new HashMap<Integer, String>();
        try {
            HashMap classToStringMapping = (HashMap)EntityList.field_75626_c;
            Field classToIDMappingField = EntityList.class.getDeclaredField(NationsGUITransformer.inDevelopment ? "classToIDMapping" : "field_75624_e");
            classToIDMappingField.setAccessible(true);
            Map classToIDMapping = (Map)classToIDMappingField.get(null);
            for (Class cls : classToStringMapping.keySet()) {
                if (!EntityLiving.class.isAssignableFrom(cls)) continue;
                try {
                    EntityLiving entityliving = (EntityLiving)cls.getConstructor(World.class).newInstance(world);
                    entityliving.func_70631_g_();
                    int id = (Integer)classToIDMapping.get(cls);
                    String name = (String)classToStringMapping.get(cls);
                    if (name.equals("EnderDragon")) continue;
                    this.nameToID.put(name, id);
                    this.idToName.put(id, name);
                }
                catch (Exception exception) {}
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ForgeSubscribe
    public void onEntityDamage(LivingHurtEvent event) {
        if (event.source.func_76346_g() != null && event.source.func_76346_g() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.source.func_76346_g();
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new EntityDamagePacket(event.entity.field_70157_k, event.ammount)), (Player)((Player)player));
        }
    }

    @ForgeSubscribe
    public void onLoadWorld(BlockEvent event) {
        this.loadEntityIDs(event.world);
    }

    @SideOnly(value=Side.SERVER)
    @ForgeSubscribe
    public void definePlayerBlockReach(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayerMP) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP)event.entity;
            HashMap<String, Integer> skillsLevel = new HashMap<String, Integer>();
            HashMap<String, String> topPlayersSkills = NationsGUI.getTopPlayersSkills();
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new UpdateSkillsLevelClientPacket(skillsLevel, topPlayersSkills)), (Player)((Player)entityPlayerMP));
        }
    }

    @SideOnly(value=Side.SERVER)
    @ForgeSubscribe
    public void onPlayerUseEdoraBlockOnNGIslands(PlayerInteractEvent event) {
        ItemStack item;
        if (NationsGUI.getServerType().equalsIgnoreCase("build") && (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) && (item = event.entityPlayer.func_71045_bC()) != null && item.func_77973_b() instanceof ItemBlock) {
            ServerConfigurationManager serverConfigManager;
            ItemBlock itemBlock = (ItemBlock)item.func_77973_b();
            int blockID = itemBlock.func_77883_f();
            Block block = Block.field_71973_m[blockID];
            String blockName = Block.field_71973_m[blockID].func_71917_a();
            if (blockName.toLowerCase().contains("edora") && !(serverConfigManager = MinecraftServer.func_71276_C().func_71203_ab()).func_72353_e(event.entityPlayer.field_71092_bJ)) {
                event.entityPlayer.func_70006_a(ChatMessageComponent.func_111077_e((String)Translation.get("\u00a7cCe bloc n'est pas disponible pour le moment.")));
                event.setCanceled(true);
            }
        }
    }

    @SideOnly(value=Side.CLIENT)
    @ForgeSubscribe
    public void onPlayerUseEdoraBlockOnNGIslandsClient(PlayerInteractEvent event) {
        ItemStack item;
        if (ClientProxy.serverType.equalsIgnoreCase("build") && (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) && (item = event.entityPlayer.func_71045_bC()) != null && item.func_77973_b() instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock)item.func_77973_b();
            int blockID = itemBlock.func_77883_f();
            Block block = Block.field_71973_m[blockID];
            String blockName = Block.field_71973_m[blockID].func_71917_a();
            if (blockName.toLowerCase().contains("edora") && !ClientData.isOp) {
                event.setCanceled(true);
            }
        }
    }

    static {
        lastPlayerEffect = new HashMap();
        lastPlayerAttack = new HashMap();
    }
}

