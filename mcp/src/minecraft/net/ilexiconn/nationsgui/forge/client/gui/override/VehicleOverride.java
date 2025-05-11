/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.ngvehicles.common.entity.vehicles.EntityPoweredVehicle
 *  fr.nationsglory.ngvehicles.common.entity.vehicles.EntityVehicle$VehicleCategory
 *  fr.nationsglory.ngvehicles.common.entity.vehicles.EntityVehicle$VehicleInfo
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import fr.nationsglory.ngvehicles.common.entity.vehicles.EntityPoweredVehicle;
import fr.nationsglory.ngvehicles.common.entity.vehicles.EntityVehicle;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class VehicleOverride
extends Gui
implements ElementOverride {
    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return new RenderGameOverlayEvent.ElementType[0];
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        if (ClientProxy.serverType.equals("ng") && !Minecraft.func_71410_x().field_71474_y.field_74330_P && client.field_71439_g != null && client.field_71439_g.field_70154_o != null && client.field_71439_g.field_70154_o instanceof EntityPoweredVehicle) {
            ClientEventHandler.getInstance().getObjectiveOverlay().setActive(false);
            EntityPoweredVehicle vehicle = (EntityPoweredVehicle)client.field_71439_g.field_70154_o;
            ArrayList cats = new ArrayList();
            vehicle.addInformationOnClient(cats);
            int x = 5;
            int y = 20;
            for (EntityVehicle.VehicleCategory cat : cats) {
                VehicleOverride.func_73734_a((int)x, (int)y, (int)160, (int)(y + 16), (int)-1157627904);
                this.drawSmallString(client.field_71466_p, cat.getName(), x + 5, y + 4, 0xFFFFFF);
                VehicleOverride.func_73734_a((int)x, (int)(y += 16), (int)160, (int)(y + 16 * cat.getInfos().size()), (int)0x77000000);
                for (EntityVehicle.VehicleInfo info : cat.getInfos()) {
                    boolean hasTexture;
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    boolean bl = hasTexture = info.getLocation() != null;
                    if (hasTexture) {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a(info.getLocation());
                        ModernGui.drawModalRectWithCustomSizedTexture(x + 3, y, 0, 0, 16, 16, 16.0f, 16.0f, false);
                    }
                    this.drawSmallString(client.field_71466_p, info.getLine(), x + 3 + (hasTexture ? 19 : 0), y + 4, 0xFFFFFF);
                    y += 16;
                }
                y += 10;
            }
        } else {
            ClientEventHandler.getInstance().getObjectiveOverlay().setActive(true);
        }
    }

    private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)0.0f);
        GL11.glScalef((float)0.95f, (float)0.95f, (float)0.95f);
        this.func_73731_b(fontRenderer, string, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
    }
}

