package net.ilexiconn.nationsgui.forge.client.util;

import fr.nationsglory.modelapi.ModelRenderOBJ;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class Transform
{
    private double scale;
    private double offsetX;
    private double offsetY;
    private double offsetZ;
    private double rotateX;
    private double rotateY;
    private double rotateZ;

    public Transform()
    {
        this(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public Transform(double scale, double offsetX, double offsetY, double offsetZ, double rotateX, double rotateY, double rotateZ)
    {
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.rotateZ = rotateZ;
    }

    public Transform(JSONObject object)
    {
        this.scale = ((Double)object.get("scale")).doubleValue();
        this.offsetX = ((Double)object.get("offsetX")).doubleValue();
        this.offsetY = ((Double)object.get("offsetY")).doubleValue();
        this.offsetZ = ((Double)object.get("offsetZ")).doubleValue();
        this.rotateX = ((Double)object.get("rotateX")).doubleValue();
        this.rotateY = ((Double)object.get("rotateY")).doubleValue();
        this.rotateZ = ((Double)object.get("rotateZ")).doubleValue();
    }

    public double getScale()
    {
        return this.scale;
    }

    public double getOffsetX()
    {
        return this.offsetX;
    }

    public double getOffsetY()
    {
        return this.offsetY;
    }

    public double getOffsetZ()
    {
        return this.offsetZ;
    }

    public void setScale(double scale)
    {
        this.scale = scale;
    }

    public void setOffsetX(double offsetX)
    {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY)
    {
        this.offsetY = offsetY;
    }

    public void setOffsetZ(double offsetZ)
    {
        this.offsetZ = offsetZ;
    }

    public double getRotateX()
    {
        return this.rotateX;
    }

    public void setRotateX(double rotateX)
    {
        this.rotateX = rotateX;
    }

    public double getRotateY()
    {
        return this.rotateY;
    }

    public void setRotateY(double rotateY)
    {
        this.rotateY = rotateY;
    }

    public double getRotateZ()
    {
        return this.rotateZ;
    }

    public void setRotateZ(double rotateZ)
    {
        this.rotateZ = rotateZ;
    }

    public void applyToModel(ModelRenderOBJ modelOBJ)
    {
        modelOBJ.offsetX = (float)this.getOffsetX();
        modelOBJ.offsetY = (float)this.getOffsetY();
        modelOBJ.offsetZ = (float)this.getOffsetZ();
        modelOBJ.rotateAngleX = (float)this.getRotateX();
        modelOBJ.rotateAngleY = (float)this.getRotateY();
        modelOBJ.rotateAngleZ = (float)this.getRotateZ();
        modelOBJ.setScale((float)this.getScale());
    }

    public void applyGL()
    {
        double scale = this.getScale();
        GL11.glScaled(scale, scale, scale);
        GL11.glTranslated(this.getOffsetX(), this.getOffsetY(), this.getOffsetZ());
        GL11.glRotated(this.getRotateX(), 1.0D, 0.0D, 0.0D);
        GL11.glRotated(this.getRotateY(), 0.0D, 1.0D, 0.0D);
        GL11.glRotated(this.getRotateZ(), 0.0D, 0.0D, 1.0D);
    }

    public boolean isNull()
    {
        return this.scale == 1.0D && this.offsetX == 0.0D && this.offsetY == 0.0D && this.offsetZ == 0.0D && this.rotateX == 0.0D && this.rotateY == 0.0D && this.rotateZ == 0.0D;
    }
}
