package net.ilexiconn.nationsgui.forge.server.asm;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks$1$1;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

final class NationsGUIClientHooks$1 implements Runnable
{
    public void run()
    {
        try
        {
            URL e = new URL("https://apiv2.nationsglory.fr/mods/staff_members");
            new Gson();
            Type listType = (new NationsGUIClientHooks$1$1(this)).getType();
            NationsGUIClientHooks.whitelistedStaff = Arrays.asList(new String[] {"MisterSand", "iBalix"});

            try
            {
                NationsGUIClientHooks.whitelistedStaff = (List)(new Gson()).fromJson(new InputStreamReader(e.openStream()), listType);
            }
            catch (Exception var9)
            {
                ;
            }

            String[] blocks = new String[] {"stone", "dirt", "grass_top"};
            ClientData.textureClean = true;

            if (!NationsGUIClientHooks.whitelistedStaff.contains(Minecraft.getMinecraft().getSession().getUsername()))
            {
                String[] var5 = blocks;
                int var6 = blocks.length;

                for (int var7 = 0; var7 < var6; ++var7)
                {
                    String block = var5[var7];

                    if (!NationsGUIClientHooks.access$000(new ResourceLocation("textures/blocks/" + block + ".png")))
                    {
                        ClientData.textureClean = false;
                        break;
                    }
                }
            }
        }
        catch (IOException var10)
        {
            var10.printStackTrace();
        }
    }
}
