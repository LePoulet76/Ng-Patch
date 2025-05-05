package net.minecraft.logging;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.logging.Logger;

public interface ILogAgent
{
    void logInfo(String var1);

    @SideOnly(Side.SERVER)
    Logger func_120013_a();

    void logWarning(String var1);

    void logWarningFormatted(String var1, Object ... var2);

    void logWarningException(String var1, Throwable var2);

    void logSevere(String var1);

    void logSevereException(String var1, Throwable var2);

    @SideOnly(Side.CLIENT)
    void logFine(String var1);
}
