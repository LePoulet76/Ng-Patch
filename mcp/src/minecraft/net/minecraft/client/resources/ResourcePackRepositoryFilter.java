package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import java.io.FileFilter;

@SideOnly(Side.CLIENT)
final class ResourcePackRepositoryFilter implements FileFilter
{
    public boolean accept(File par1File)
    {
        boolean var2 = par1File.isFile() && par1File.getName().endsWith(".zip");
        boolean var3 = par1File.isDirectory() && (new File(par1File, "pack.mcmeta")).isFile();
        return var2 || var3;
    }
}
