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

public enum ServerEventHandler {

   INSTANCE("INSTANCE", 0);
   static String MAIN_BAGDE;
   static HashMap<String, Long> lastPlayerEffect = new HashMap();
   static HashMap<String, Long> lastPlayerAttack = new HashMap();
   public Map<String, Integer> nameToID;
   public Map<Integer, String> idToName;
   // $FF: synthetic field
   private static final ServerEventHandler[] $VALUES = new ServerEventHandler[]{INSTANCE};


   private ServerEventHandler(String var1, int var2) {}

   @ForgeSubscribe
   public void onItemEntitySpawn(EntityJoinWorldEvent event) {
      if(event.entity instanceof EntityItem) {
         EntityItem entityItem = (EntityItem)event.entity;
         ItemStack itemStack = entityItem.func_92059_d();
         if(itemStack != null && itemStack.func_77973_b() instanceof ItemFood) {
            ItemFoodTransformer.updateFoodStack((ItemFood)itemStack.func_77973_b(), itemStack, event.world, (Entity)null, 0, false);
         }
      }

   }

   @ForgeSubscribe
   public void onLivingUpdate(LivingUpdateEvent event) {
      if(event.entity instanceof EntityPlayer && !event.entity.field_70170_p.field_72995_K) {
         ItemStack[] armor = ((EntityPlayer)event.entity).field_71071_by.field_70460_b;
         if(armor[3] != null && armor[3].func_77973_b() instanceof JSONArmor && (!lastPlayerEffect.containsKey(((EntityPlayer)event.entity).getDisplayName()) || System.currentTimeMillis() - ((Long)lastPlayerEffect.get(((EntityPlayer)event.entity).getDisplayName())).longValue() > 10000L)) {
            lastPlayerEffect.put(((EntityPlayer)event.entity).getDisplayName(), Long.valueOf(System.currentTimeMillis()));
            JSONArmor helmet = (JSONArmor)armor[3].func_77973_b();
            if(helmet.effects != null) {
               EnumArmorMaterial material = helmet.func_82812_d();
               int i = 0;

               while(true) {
                  if(i < armor.length - 1) {
                     if(armor[i] != null && armor[i].func_77973_b() instanceof JSONArmor) {
                        JSONArmor var8 = (JSONArmor)armor[i].func_77973_b();
                        if(var8.func_82812_d() != material) {
                           return;
                        }

                        ++i;
                        continue;
                     }

                     return;
                  }

                  Iterator var7 = helmet.effects.iterator();

                  while(var7.hasNext()) {
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
   public void onEntityAttack(AttackEntityEvent event) {
      if(lastPlayerAttack.containsKey(((EntityPlayer)event.entity).getDisplayName()) && System.currentTimeMillis() - ((Long)lastPlayerAttack.get(((EntityPlayer)event.entity).getDisplayName())).longValue() <= 66L) {
         event.setCanceled(true);
      } else {
         lastPlayerAttack.put(((EntityPlayer)event.entity).getDisplayName(), Long.valueOf(System.currentTimeMillis()));
      }
   }

   @ForgeSubscribe
   public void onPlayerInteract(PlayerInteractEvent event) {
      if(event.action == Action.LEFT_CLICK_BLOCK && !event.entityPlayer.field_71075_bZ.field_75098_d) {
         Block block = Block.field_71973_m[event.entity.field_70170_p.func_72798_a(event.x, event.y, event.z)];
         if(block instanceof BlockDoor) {
            event.entity.field_70170_p.func_72908_a((double)event.x, (double)event.y, (double)event.z, "mob.zombie.wood", 0.5F, 1.75F);
         }
      }

   }

   @ForgeSubscribe
   public void onEntityJoinWorld(EntityJoinWorldEvent event) {
      if(event.entity instanceof EntityPlayerMP) {
         PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new DateTimeSyncPacket(Long.valueOf(System.currentTimeMillis()))), (Player)event.entity);
         PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new WorldNameSyncPacket(event.world.func_72912_H().func_76065_j())), (Player)event.entity);
         EntityPlayerMP entityPlayer = (EntityPlayerMP)event.entity;
         String clickLimiterPacket = entityPlayer.field_71135_a.field_72575_b.func_74430_c().toString().split(":")[0].replace("/", "");
         NationsGUIHooks.allowedIPConnection.remove(clickLimiterPacket);
      }

      if(event.entity instanceof EntityPlayer && event.world.field_72995_K && Minecraft.func_71410_x().field_71439_g.equals(event.entity)) {
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ObjectivePacket()));
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new BonusDataPacket()));
      }

      if(event.entity instanceof EntityPlayer && !event.world.field_72995_K) {
         EntityPlayer entityPlayer1 = (EntityPlayer)event.entity;
         PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new StopSoundsPacket()), (Player)event.entity);
         (new Timer()).schedule(new ServerEventHandler$1(this, entityPlayer1, event), 5000L);
         ClickLimiterPacket clickLimiterPacket1 = new ClickLimiterPacket();
         clickLimiterPacket1.limit = CommonProxy.localConfig.getClickLimit();
         PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(clickLimiterPacket1), (Player)event.entity);
      }

   }

   @ForgeSubscribe
   public void onBlockBreak(BreakEvent event) {
      if(event.block instanceof BlockMobSpawner) {
         TileEntityMobSpawner tileEntity = (TileEntityMobSpawner)event.world.func_72796_p(event.x, event.y, event.z);
         int entityID = ((Integer)this.nameToID.get(tileEntity.func_98049_a().func_98276_e())).intValue();
         if(NationsGUI.CONFIG.spawnerBlacklist.contains(Integer.valueOf(entityID))) {
            return;
         }

         event.setExpToDrop(0);
         if(!event.getPlayer().field_71075_bZ.field_75098_d && !event.world.field_72995_K && event.world.func_82736_K().func_82766_b("doTileDrops")) {
            float radius = 0.7F;
            double x = (double)(event.world.field_73012_v.nextFloat() * radius) + (double)(1.0F - radius) * 0.5D;
            double y = (double)(event.world.field_73012_v.nextFloat() * radius) + (double)(1.0F - radius) * 0.5D;
            double z = (double)(event.world.field_73012_v.nextFloat() * radius) + (double)(1.0F - radius) * 0.5D;
            EntityItem entityitem = new EntityItem(event.world, (double)event.x + x, (double)event.y + y, (double)event.z + z, new ItemStack(383, 1, entityID));
            entityitem.field_70293_c = 10;
            event.world.func_72838_d(entityitem);
         }
      }

   }

   @ForgeSubscribe
   public void onPotionStart(PotionEffectEvent$Start event) {
      PacketDispatcher.sendPacketToAllPlayers(PacketRegistry.INSTANCE.generatePacket(new UpdatePotionsPacket(event.entity.field_70157_k, event.effect.func_76456_a(), true)));
   }

   @ForgeSubscribe
   public void onPotionFinish(PotionEffectEvent$Finish event) {
      PacketDispatcher.sendPacketToAllPlayers(PacketRegistry.INSTANCE.generatePacket(new UpdatePotionsPacket(event.entity.field_70157_k, event.effect.func_76456_a(), false)));
   }

   @ForgeSubscribe
   public void onLoadWorld(Load event) {
      this.loadEntityIDs(event.world);
   }

   public void loadEntityIDs(World world) {
      if(this.nameToID == null || this.idToName == null) {
         this.nameToID = new HashMap();
         this.idToName = new HashMap();

         try {
            HashMap e = (HashMap)EntityList.field_75626_c;
            Field classToIDMappingField = EntityList.class.getDeclaredField(NationsGUITransformer.inDevelopment?"classToIDMapping":"field_75624_e");
            classToIDMappingField.setAccessible(true);
            Map classToIDMapping = (Map)classToIDMappingField.get((Object)null);
            Iterator var5 = e.keySet().iterator();

            while(var5.hasNext()) {
               Class cls = (Class)var5.next();
               if(EntityLiving.class.isAssignableFrom(cls)) {
                  try {
                     EntityLiving entityliving = (EntityLiving)cls.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
                     entityliving.func_70631_g_();
                     int id = ((Integer)classToIDMapping.get(cls)).intValue();
                     String name = (String)e.get(cls);
                     if(!name.equals("EnderDragon")) {
                        this.nameToID.put(name, Integer.valueOf(id));
                        this.idToName.put(Integer.valueOf(id), name);
                     }
                  } catch (Exception var10) {
                     ;
                  }
               }
            }
         } catch (Exception var11) {
            var11.printStackTrace();
         }

      }
   }

   @ForgeSubscribe
   public void onEntityDamage(LivingHurtEvent event) {
      if(event.source.func_76346_g() != null && event.source.func_76346_g() instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)event.source.func_76346_g();
         PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new EntityDamagePacket(event.entity.field_70157_k, event.ammount)), (Player)player);
      }

   }

   @ForgeSubscribe
   public void onLoadWorld(BlockEvent event) {
      this.loadEntityIDs(event.world);
   }

   @SideOnly(Side.SERVER)
   @ForgeSubscribe
   public void definePlayerBlockReach(EntityJoinWorldEvent event) {
      if(event.entity instanceof EntityPlayerMP) {
         EntityPlayerMP entityPlayerMP = (EntityPlayerMP)event.entity;
         HashMap skillsLevel = new HashMap();
         HashMap topPlayersSkills = NationsGUI.getTopPlayersSkills();
         PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new UpdateSkillsLevelClientPacket(skillsLevel, topPlayersSkills)), (Player)entityPlayerMP);
      }

   }

   @SideOnly(Side.SERVER)
   @ForgeSubscribe
   public void onPlayerUseEdoraBlockOnNGIslands(PlayerInteractEvent event) {
      if(NationsGUI.getServerType().equalsIgnoreCase("build") && (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)) {
         ItemStack item = event.entityPlayer.func_71045_bC();
         if(item != null && item.func_77973_b() instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock)item.func_77973_b();
            int blockID = itemBlock.func_77883_f();
            Block var10000 = Block.field_71973_m[blockID];
            String blockName = Block.field_71973_m[blockID].func_71917_a();
            if(blockName.toLowerCase().contains("edora")) {
               ServerConfigurationManager serverConfigManager = MinecraftServer.func_71276_C().func_71203_ab();
               if(!serverConfigManager.func_72353_e(event.entityPlayer.field_71092_bJ)) {
                  event.entityPlayer.func_70006_a(ChatMessageComponent.func_111077_e(Translation.get("\u00a7cCe bloc n\'est pas disponible pour le moment.")));
                  event.setCanceled(true);
               }
            }
         }
      }

   }

   @SideOnly(Side.CLIENT)
   @ForgeSubscribe
   public void onPlayerUseEdoraBlockOnNGIslandsClient(PlayerInteractEvent event) {
      if(ClientProxy.serverType.equalsIgnoreCase("build") && (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)) {
         ItemStack item = event.entityPlayer.func_71045_bC();
         if(item != null && item.func_77973_b() instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock)item.func_77973_b();
            int blockID = itemBlock.func_77883_f();
            Block var10000 = Block.field_71973_m[blockID];
            String blockName = Block.field_71973_m[blockID].func_71917_a();
            if(blockName.toLowerCase().contains("edora") && !ClientData.isOp) {
               event.setCanceled(true);
            }
         }
      }

   }

}
