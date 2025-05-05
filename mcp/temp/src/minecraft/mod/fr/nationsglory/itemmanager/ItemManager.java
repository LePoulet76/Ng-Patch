package fr.nationsglory.itemmanager;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fr.nationsglory.itemmanager.CommonProxy;

@Mod(
   modid = "fr/nationsglory/itemmanager",
   version = "1.0",
   dependencies = "required-after:nationsgui"
)
public class ItemManager {

   public static final String MODID = "fr/nationsglory/itemmanager";
   public static final String VERSION = "1.0";
   @SidedProxy(
      clientSide = "fr.nationsglory.itemmanager.client.ClientProxy",
      serverSide = "fr.nationsglory.itemmanager.CommonProxy"
   )
   public static CommonProxy proxy;


   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      proxy.preInit(event);
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      proxy.init();
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      proxy.postInit();
   }
}
