package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

@SideOnly(Side.CLIENT)
public interface ReloadableResourceManager extends ResourceManager
{
    void reloadResources(List var1);

    void registerReloadListener(ResourceManagerReloadListener var1);
}
