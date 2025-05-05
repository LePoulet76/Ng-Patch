package net.ilexiconn.nationsgui.forge.server.asm;

import com.google.gson.Gson;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.DependsOn;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import fr.nationsglory.itemmanager.asm.GameRegistryTransformer;
import fr.nationsglory.itemmanager.data.Config;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.AbstractClientPlayerTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.BlockChestTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.BlockSpawnerTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.BlockTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ClientPlayerTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ContainerTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.CraftItemStackTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.EntityLivingBaseTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.EntityMinecartTNTTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.EntityPlayerSPTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.EntityPlayerTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.EntityRendererTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.EntitySelectorInvTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.FMLNetworkHandlerTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ForgeHookClientTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.FurnitureTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.GameSettingsTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.GuiContainerTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.GuiInGameForgeTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.GuiScreenTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ItemFoodTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ItemRendererTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ItemStackTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.KeyRegistryTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.LocaleTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.MethodAndFieldsDebugTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.MinecraftForgeClientTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.MinecraftTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.NBTTagCompoundTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.NetherrocksTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Packet201PlayerInfoTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Packet2ClientProtocolTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.PlayerControllerMPTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.PlayerRenderDeobfTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.PotionTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.RenderItemTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.RenderPlayerTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.RenderSkullTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.RenderTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ScreenShotHelperTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ServerConfigurationManagerTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ServerListenThreadTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.SkullTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.ThreadLoginVerifierTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.TileEntityRendererTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.TileEntityTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.WavefrontObjectTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.WorldClientTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.WorldTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.loading.LoadControllerTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.loading.LoaderTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.loading.ModContainerTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.loading.ModDiscovererTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.loading.ResourceManagerTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

@Name("NationsGUI")
@SortingIndex(1001)
@MCVersion("1.6.4")
@DependsOn({"NEICorePlugin"})
@TransformerExclusions({"net.ilexiconn.nationsgui.forge.server.asm."})
public class NationsGUITransformer implements IFMLLoadingPlugin, IClassTransformer {

   private List<Transformer> transformerList = new ArrayList();
   public static boolean inDevelopment;
   public static Config config;
   public static boolean isServer = false;
   public static boolean optifine = false;


   public NationsGUITransformer() {
      this.transformerList.add(new MethodAndFieldsDebugTransformer());
      this.transformerList.add(new ServerListenThreadTransformer());
      this.transformerList.add(new LocaleTransformer());
      this.transformerList.add(new SkullTransformer());
      this.transformerList.add(new KeyRegistryTransformer());
      this.transformerList.add(new RenderPlayerTransformer());
      this.transformerList.add(new ClientPlayerTransformer());
      this.transformerList.add(new MinecraftTransformer());
      this.transformerList.add(new ServerConfigurationManagerTransformer());
      this.transformerList.add(new GuiScreenTransformer());
      this.transformerList.add(new RenderSkullTransformer());
      this.transformerList.add(new BlockSpawnerTransformer());
      this.transformerList.add(new GuiContainerTransformer());
      this.transformerList.add(new Packet2ClientProtocolTransformer());
      this.transformerList.add(new GameRegistryTransformer());
      this.transformerList.add(new EntityLivingBaseTransformer());
      this.transformerList.add(new EntityPlayerTransformer());
      this.transformerList.add(new AbstractClientPlayerTransformer());
      this.transformerList.add(new ThreadLoginVerifierTransformer());
      this.transformerList.add(new EntityPlayerSPTransformer());
      this.transformerList.add(new BlockTransformer());
      this.transformerList.add(new GuiInGameForgeTransformer());
      this.transformerList.add(new EntityMinecartTNTTransformer());
      this.transformerList.add(new EntityRendererTransformer());
      this.transformerList.add(new BlockChestTransformer());
      this.transformerList.add(new FurnitureTransformer());
      this.transformerList.add(new WorldTransformer());
      this.transformerList.add(new WavefrontObjectTransformer());
      this.transformerList.add(new ItemFoodTransformer());
      this.transformerList.add(new EntitySelectorInvTransformer());
      this.transformerList.add(new NetherrocksTransformer());
      this.transformerList.add(new ContainerTransformer());
      this.transformerList.add(new NBTTagCompoundTransformer());
      this.transformerList.add(new CraftItemStackTransformer());
      this.transformerList.add(new TileEntityTransformer());
      this.transformerList.add(new ScreenShotHelperTransformer());
      this.transformerList.add(new PotionTransformer());
      this.transformerList.add(new PlayerRenderDeobfTransformer());
      this.transformerList.add(new TileEntityRendererTransformer());
      this.transformerList.add(new ItemRendererTransformer());
      this.transformerList.add(new RenderItemTransformer());
      this.transformerList.add(new ForgeHookClientTransformer());
      this.transformerList.add(new PlayerControllerMPTransformer());
      this.transformerList.add(new GameSettingsTransformer());
      this.transformerList.add(new Packet201PlayerInfoTransformer());
      this.transformerList.add(new MinecraftForgeClientTransformer());
      this.transformerList.add(new RenderTransformer());
      this.transformerList.add(new WorldClientTransformer());
      this.transformerList.add(new ItemStackTransformer());
      this.transformerList.add(new LoaderTransformer());
      this.transformerList.add(new ModDiscovererTransformer());
      this.transformerList.add(new LoadControllerTransformer());
      this.transformerList.add(new ResourceManagerTransformer());
      this.transformerList.add(new ModContainerTransformer());
      this.transformerList.add(new FMLNetworkHandlerTransformer());
      Gson gson = new Gson();

      try {
         config = (Config)gson.fromJson(new InputStreamReader((new URL("https://apiv2.nationsglory.fr/json/item_manager.json")).openStream()), Config.class);
      } catch (IOException var3) {
         config = new Config();
         var3.printStackTrace();
      }

   }

   public String[] getASMTransformerClass() {
      return new String[]{this.getClass().getName()};
   }

   public String getModContainerClass() {
      return null;
   }

   public String getSetupClass() {
      return null;
   }

   public void injectData(Map<String, Object> data) {
      inDevelopment = !((Boolean)data.get("runtimeDeobfuscationEnabled")).booleanValue();
      if(data.get("coremodLocation") != null) {
         isServer = ((File)data.get("coremodLocation")).getName().contains("nationsgui-server-1.8.0");
      }

      try {
         Class.forName("optifine.OptiFineClassTransformer");
         optifine = true;
      } catch (ClassNotFoundException var3) {
         ;
      }

   }

   public byte[] transform(String name, String transformedName, byte[] bytes) {
      Iterator var4 = this.transformerList.iterator();

      while(var4.hasNext()) {
         Transformer transformer = (Transformer)var4.next();
         if(transformedName.equals(transformer.getTarget())) {
            ClassReader classReader = new ClassReader(bytes);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 0);
            transformer.transform(classNode, inDevelopment);
            ClassWriter classWriter = new ClassWriter(classReader, 1);
            classNode.accept(classWriter);
            this.saveBytecode(transformedName, classWriter);
            bytes = classWriter.toByteArray();
         }
      }

      return bytes;
   }

   private void saveBytecode(String name, ClassWriter cw) {
      try {
         File e = new File("debug/");
         if(e.exists()) {
            e.delete();
         }

         e.mkdirs();
         File output = new File(e, name + ".class");
         FileOutputStream out = new FileOutputStream(output);
         out.write(cw.toByteArray());
         out.close();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

}
