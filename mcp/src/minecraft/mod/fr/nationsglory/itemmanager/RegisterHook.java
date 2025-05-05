package fr.nationsglory.itemmanager;

import cpw.mods.fml.common.Loader;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class RegisterHook
{
    public static boolean blockIsBlacklisted(Object object, String modID)
    {
        if (modID == null)
        {
            modID = Loader.instance().activeModContainer().getModId();
        }

        List list = (List)NationsGUITransformer.config.getItemBlacklist().get(modID);

        if (list != null && (object instanceof Block && list.contains(Integer.valueOf(((Block)object).blockID)) || object instanceof Item && list.contains(Integer.valueOf(((Item)object).itemID))))
        {
            int id = object instanceof Block ? ((Block)object).blockID : ((Item)object).itemID;
            Block.blocksList[id] = null;
            Item.itemsList[id] = null;
            return true;
        }
        else
        {
            return false;
        }
    }
}
