/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.modelapi.ModelRenderOBJ
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.util;

import fr.nationsglory.modelapi.ModelRenderOBJ;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class Transform {
    private double scale;
    private double offsetX;
    private double offsetY;
    private double offsetZ;
    private double rotateX;
    private double rotateY;
    private double rotateZ;

    public Transform() {
        this(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public Transform(double scale, double offsetX, double offsetY, double offsetZ, double rotateX, double rotateY, double rotateZ) {
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.rotateZ = rotateZ;
    }

    public Transform(JSONObject object) {
        this.scale = (Double)object.get("scale");
        this.offsetX = (Double)object.get("offsetX");
        this.offsetY = (Double)object.get("offsetY");
        this.offsetZ = (Double)object.get("offsetZ");
        this.rotateX = (Double)object.get("rotateX");
        this.rotateY = (Double)object.get("rotateY");
        this.rotateZ = (Double)object.get("rotateZ");
    }

    public double getScale() {
        return this.scale;
    }

    public double getOffsetX() {
        return this.offsetX;
    }

    public double getOffsetY() {
        return this.offsetY;
    }

    public double getOffsetZ() {
        return this.offsetZ;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public void setOffsetZ(double offsetZ) {
        this.offsetZ = offsetZ;
    }

    public double getRotateX() {
        return this.rotateX;
    }

    public void setRotateX(double rotateX) {
        this.rotateX = rotateX;
    }

    public double getRotateY() {
        return this.rotateY;
    }

    public void setRotateY(double rotateY) {
        this.rotateY = rotateY;
    }

    public double getRotateZ() {
        return this.rotateZ;
    }

    public void setRotateZ(double rotateZ) {
        this.rotateZ = rotateZ;
    }

    public void applyToModel(ModelRenderOBJ modelOBJ) {
        modelOBJ.field_82906_o = (float)this.getOffsetX();
        modelOBJ.field_82908_p = (float)this.getOffsetY();
        modelOBJ.field_82907_q = (float)this.getOffsetZ();
        modelOBJ.field_78795_f = (float)this.getRotateX();
        modelOBJ.field_78796_g = (float)this.getRotateY();
        modelOBJ.field_78808_h = (float)this.getRotateZ();
        modelOBJ.setScale((float)this.getScale());
    }

    public void applyGL() {
        double scale = this.getScale();
        GL11.glScaled((double)scale, (double)scale, (double)scale);
        GL11.glTranslated((double)this.getOffsetX(), (double)this.getOffsetY(), (double)this.getOffsetZ());
        GL11.glRotated((double)this.getRotateX(), (double)1.0, (double)0.0, (double)0.0);
        GL11.glRotated((double)this.getRotateY(), (double)0.0, (double)1.0, (double)0.0);
        GL11.glRotated((double)this.getRotateZ(), (double)0.0, (double)0.0, (double)1.0);
    }

    public boolean isNull() {
        return this.scale == 1.0 && this.offsetX == 0.0 && this.offsetY == 0.0 && this.offsetZ == 0.0 && this.rotateX == 0.0 && this.rotateY == 0.0 && this.rotateZ == 0.0;
    }
}

