package net.ilexiconn.nationsgui.forge.server;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.itemmanager.CommonProxy;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.ServerEventHandler$1;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIHooks;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ItemFoodTransformer;
import net.ilexiconn.nationsgui.forge.server.event.PotionEffectEvent$Finish;
import net.ilexiconn.nationsgui.forge.server.event.PotionEffectEvent$Start;
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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.WorldEvent.Load;

public enum ServerEventHandler
{
    INSTANCE;
    static String MAIN_BAGDE;
    static HashMap<String, Long> lastPlayerEffect = new HashMap();
    static HashMap<String, Long> lastPlayerAttack = new HashMap();
    public Map<String, Integer> nameToID;
    public Map<Integer, String> idToName;

    @ForgeSubscribe
    public void onItemEntitySpawn(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityItem)
        {
            EntityItem entityItem = (EntityItem)event.entity;
            ItemStack itemStack = entityItem.getEntityItem();

            if (itemStack != null && itemStack.getItem() instanceof ItemFood)
            {
                ItemFoodTransformer.updateFoodStack((ItemFood)itemStack.getItem(), itemStack, event.world, (Entity)null, 0, false);
            }
        }
    }

    @ForgeSubscribe
    public void onLivingUpdate(LivingUpdateEvent event)
    {
        if (event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote)
        {
            ItemStack[] armor = ((EntityPlayer)event.entity).inventory.armorInventory;

            if (armor[3] != null && armor[3].getItem() instanceof JSONArmor && (!lastPlayerEffect.containsKey(((EntityPlayer)event.entity).getDisplayName()) || System.currentTimeMillis() - ((Long)lastPlayerEffect.get(((EntityPlayer)event.entity).getDisplayName())).longValue() > 10000L))
            {
                lastPlayerEffect.put(((EntityPlayer)event.entity).getDisplayName(), Long.valueOf(System.currentTimeMillis()));
                JSONArmor helmet = (JSONArmor)armor[3].getItem();

                if (helmet.effects != null)
                {
                    EnumArmorMaterial material = helmet.getArmorMaterial();
                    int i = 0;

                    while (true)
                    {
                        if (i < armor.length - 1)
                        {
                            if (armor[i] != null && armor[i].getItem() instanceof JSONArmor)
                            {
                                JSONArmor var8 = (JSONArmor)armor[i].getItem();

                                if (var8.getArmorMaterial() != material)
                                {
                                    return;
                                }

                                ++i;
                                continue;
                            }

                            return;
                        }

                        Iterator var7 = helmet.effects.iterator();

                        while (var7.hasNext())
                        {
                            JSONEffect effect = (JSONEffect)var7.next();
                            effect.applyEffect((EntityPlayer)event.entity);
                        }

                        return;
                    }
                }
            }
        }
    }

    @ForgeSubscribe
    public void onEntityAttack(AttackEntityEvent event)
    {
        if (lastPlayerAttack.containsKey(((EntityPlayer)event.entity).getDisplayName()) && System.currentTimeMillis() - ((Long)lastPlayerAttack.get(((EntityPlayer)event.entity).getDisplayName())).longValue() <= 66L)
        {
            event.setCanceled(true);
        }
        else
        {
            lastPlayerAttack.put(((EntityPlayer)event.entity).getDisplayName(), Long.valueOf(System.currentTimeMillis()));
        }
    }

    @ForgeSubscribe
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.action == Action.LEFT_CLICK_BLOCK && !event.entityPlayer.capabilities.isCreativeMode)
        {
            Block block = Block.blocksList[event.entity.worldObj.getBlockId(event.x, event.y, event.z)];

            if (block instanceof BlockDoor)
            {
                event.entity.worldObj.playSoundEffect((double)event.x, (double)event.y, (double)event.z, "mob.zombie.wood", 0.5F, 1.75F);
            }
        }
    }

    @ForgeSubscribe
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityPlayerMP)
        {
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new DateTimeSyncPacket(Long.valueOf(System.currentTimeMillis()))), (Player)event.entity);
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new WorldNameSyncPacket(event.world.getWorldInfo().getWorldName())), (Player)event.entity);
            EntityPlayerMP entityPlayer = (EntityPlayerMP)event.entity;
            String clickLimiterPacket = entityPlayer.playerNetServerHandler.netManager.getSocketAddress().toString().split(":")[0].replace("/", "");
            NationsGUIHooks.allowedIPConnection.remove(clickLimiterPacket);
        }

        if (event.entity instanceof EntityPlayer && event.world.isRemote && Minecraft.getMinecraft().thePlayer.equals(event.entity))
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ObjectivePacket()));
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new BonusDataPacket()));
        }

        if (event.entity instanceof EntityPlayer && !event.world.isRemote)
        {
            EntityPlayer entityPlayer1 = (EntityPlayer)event.entity;
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new StopSoundsPacket()), (Player)event.entity);
            (new Timer()).schedule(new ServerEventHandler$1(this, entityPlayer1, event), 5000L);
            ClickLimiterPacket clickLimiterPacket1 = new ClickLimiterPacket();
            clickLimiterPacket1.limit = CommonProxy.localConfig.getClickLimit();
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(clickLimiterPacket1), (Player)event.entity);
        }
    }

    @ForgeSubscribe
    public void onBlockBreak(BreakEvent event)
    {
        if (event.block instanceof BlockMobSpawner)
        {
            TileEntityMobSpawner tileEntity = (TileEntityMobSpawner)event.world.getBlockTileEntity(event.x, event.y, event.z);
            int entityID = ((Integer)this.nameToID.get(tileEntity.getSpawnerLogic().getEntityNameToSpawn())).intValue();

            if (NationsGUI.CONFIG.spawnerBlacklist.contains(Integer.valueOf(entityID)))
            {
                return;
            }

            event.setExpToDrop(0);

            if (!event.getPlayer().capabilities.isCreativeMode && !event.world.isRemote && event.world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
            {
                float radius = 0.7F;
                double x = (double)(event.world.rand.nextFloat() * radius) + (double)(1.0F - radius) * 0.5D;
                double y = (double)(event.world.rand.nextFloat() * radius) + (double)(1.0F - radius) * 0.5D;
                double z = (double)(event.world.rand.nextFloat() * radius) + (double)(1.0F - radius) * 0.5D;
                EntityItem entityitem = new EntityItem(event.world, (double)event.x + x, (double)event.y + y, (double)event.z + z, new ItemStack(383, 1, entityID));
                entityitem.delayBeforeCanPickup = 10;
                event.world.spawnEntityInWorld(entityitem);
            }
        }
    }

    @ForgeSubscribe
    public void onPotionStart(PotionEffectEvent$Start event)
    {
        PacketDispatcher.sendPacketToAllPlayers(PacketRegistry.INSTANCE.generatePacket(new UpdatePotionsPacket(event.entity.entityId, event.effect.getPotionID(), true)));
    }

    @ForgeSubscribe
    public void onPotionFinish(PotionEffectEvent$Finish event)
    {
        PacketDispatcher.sendPacketToAllPlayers(PacketRegistry.INSTANCE.generatePacket(new UpdatePotionsPacket(event.entity.entityId, event.effect.getPotionID(), false)));
    }

    @ForgeSubscribe
    public void onLoadWorld(Load event)
    {
        this.loadEntityIDs(event.world);
    }

    public void loadEntityIDs(World world)
    {
        if (this.nameToID == null || this.idToName == null)
        {
            this.nameToID = new HashMap();
            this.idToName = new HashMap();

            try
            {
                HashMap e = (HashMap)EntityList.classToStringMapping;
                Field classToIDMappingField = EntityList.class.getDeclaredField(NationsGUITransformer.inDevelopment ? "classToIDMapping" : "classToIDMapping");
                classToIDMappingField.setAccessible(true);
                Map classToIDMapping = (Map)classToIDMappingField.get((Object)null);
                Iterator var5 = e.keySet().iterator();

                while (var5.hasNext())
                {
                    Class cls = (Class)var5.next();

                    if (EntityLiving.class.isAssignableFrom(cls))
                    {
                        try
                        {
                            EntityLiving entityliving = (EntityLiving)cls.getConstructor(new Class[] {World.class}).newInstance(new Object[] {world});
                            entityliving.isChild();
                            int id = ((Integer)classToIDMapping.get(cls)).intValue();
                            String name = (String)e.get(cls);

                            if (!name.equals("EnderDragon"))
                            {
                                this.nameToID.put(name, Integer.valueOf(id));
                                this.idToName.put(Integer.valueOf(id), name);
                            }
                        }
                        catch (Exception var10)
                        {
                            ;
                        }
                    }
                }
            }
            catch (Exception var11)
            {
                var11.printStackTrace();
            }
        }
    }

    @ForgeSubscribe
    public void onEntityDamage(LivingHurtEvent event)
    {
        if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)event.source.getEntity();
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new EntityDamagePacket(event.entity.entityId, event.ammount)), (Player)player);
        }
    }

    @ForgeSubscribe
    public void onLoadWorld(BlockEvent event)
    {
        this.loadEntityIDs(event.world);
    }

    @SideOnly(Side.SERVER)
    @ForgeSubscribe
    public void definePlayerBlockReach(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP)event.entity;
            HashMap skillsLevel = new HashMap();
            HashMap topPlayersSkills = NationsGUI.getTopPlayersSkills();
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new UpdateSkillsLevelClientPacket(skillsLevel, topPlayersSkills)), (Player)entityPlayerMP);
        }
    }

    @SideOnly(Side.SERVER)
    @ForgeSubscribe
    public void onPlayerUseEdoraBlockOnNGIslands(PlayerInteractEvent event)
    {
        if (NationsGUI.getServerType().equalsIgnoreCase("build") && (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK))
        {
            ItemStack item = event.entityPlayer.getCurrentEquippedItem();

            if (item != null && item.getItem() instanceof ItemBlock)
            {
                ItemBlock itemBlock = (ItemBlock)item.getItem();
                int blockID = itemBlock.getBlockID();
                Block var10000 = Block.blocksList[blockID];
                String blockName = Block.blocksList[blockID].getUnlocalizedName();

                if (blockName.toLowerCase().contains("edora"))
                {
                    ServerConfigurationManager serverConfigManager = MinecraftServer.getServer().getConfigurationManager();

                    if (!serverConfigManager.isPlayerOpped(event.entityPlayer.username))
                    {
                        event.entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(Translation.get("\u00a7cCe bloc n\'est pas disponible pour le moment.")));
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void onPlayerUseEdoraBlockOnNGIslandsClient(PlayerInteractEvent event)
    {
        if (ClientProxy.serverType.equalsIgnoreCase("build") && (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK))
        {
            ItemStack item = event.entityPlayer.getCurrentEquippedItem();

            if (item != null && item.getItem() instanceof ItemBlock)
            {
                ItemBlock itemBlock = (ItemBlock)item.getItem();
                int blockID = itemBlock.getBlockID();
                Block var10000 = Block.blocksList[blockID];
                String blockName = Block.blocksList[blockID].getUnlocalizedName();

                if (blockName.toLowerCase().contains("edora") && !ClientData.isOp)
                {
                    event.setCanceled(true);
                }
            }
        }
    }
}
