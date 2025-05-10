package net.ilexiconn.nationsgui.forge.client.gui.override;

import fr.nationsglory.ngvehicles.common.entity.vehicles.EntityPoweredVehicle;
import fr.nationsglory.ngvehicles.common.entity.vehicles.EntityVehicle.VehicleCategory;
import fr.nationsglory.ngvehicles.common.entity.vehicles.EntityVehicle.VehicleInfo;
import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

public class VehicleOverride extends Gui implements ElementOverride
{
    public ElementType getType()
    {
        return ElementType.HOTBAR;
    }

    public ElementType[] getSubTypes()
    {
        return new ElementType[0];
    }

    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks)
    {
        if (ClientProxy.serverType.equals("ng") && !Minecraft.getMinecraft().gameSettings.showDebugInfo && client.thePlayer != null && client.thePlayer.ridingEntity != null && client.thePlayer.ridingEntity instanceof EntityPoweredVehicle)
        {
            ClientEventHandler.getInstance().getObjectiveOverlay().setActive(false);
            EntityPoweredVehicle vehicle = (EntityPoweredVehicle)client.thePlayer.ridingEntity;
            ArrayList cats = new ArrayList();
            vehicle.addInformationOnClient(cats);
            byte x = 5;
            int y = 20;

            for (Iterator var8 = cats.iterator(); var8.hasNext(); y += 10)
            {
                VehicleCategory cat = (VehicleCategory)var8.next();
                drawRect(x, y, 160, y + 16, -1157627904);
                this.drawSmallString(client.fontRenderer, cat.getName(), x + 5, y + 4, 16777215);
                y += 16;
                drawRect(x, y, 160, y + 16 * cat.getInfos().size(), 1996488704);

                for (Iterator var10 = cat.getInfos().iterator(); var10.hasNext(); y += 16)
                {
                    VehicleInfo info = (VehicleInfo)var10.next();
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    boolean hasTexture = info.getLocation() != null;

                    if (hasTexture)
                    {
                        Minecraft.getMinecraft().renderEngine.bindTexture(info.getLocation());
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(x + 3), (float)y, 0, 0, 16, 16, 16.0F, 16.0F, false);
                    }

                    this.drawSmallString(client.fontRenderer, info.getLine(), x + 3 + (hasTexture ? 19 : 0), y + 4, 16777215);
                }
            }
        }
        else
        {
            ClientEventHandler.getInstance().getObjectiveOverlay().setActive(true);
        }
    }

    private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, 0.0F);
        GL11.glScalef(0.95F, 0.95F, 0.95F);
        this.drawString(fontRenderer, string, 0, 0, 16777215);
        GL11.glPopMatrix();
    }
}
