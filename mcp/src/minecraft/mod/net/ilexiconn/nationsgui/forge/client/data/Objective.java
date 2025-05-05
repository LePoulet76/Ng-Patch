package net.ilexiconn.nationsgui.forge.client.data;

import java.util.Objects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Objective
{
    private String id;
    private String title;
    private String text;
    private String rewardName;
    private int currentStep;
    private int stepsNumber;
    private ItemStack itemStack = null;

    public Objective(NBTTagCompound nbtTagCompound)
    {
        this.id = nbtTagCompound.getString("id");
        this.title = nbtTagCompound.getString("title");
        this.text = nbtTagCompound.getString("text");
        this.currentStep = nbtTagCompound.getInteger("currentStep");
        this.stepsNumber = nbtTagCompound.getInteger("stepsNumber");
        this.rewardName = nbtTagCompound.getString("rewardName");

        if (nbtTagCompound.hasKey("itemId") && Item.itemsList[nbtTagCompound.getInteger("itemId")] != null)
        {
            this.itemStack = new ItemStack(nbtTagCompound.getInteger("itemId"), nbtTagCompound.getInteger("itemQuantity"), nbtTagCompound.getInteger("itemMeta"));
        }
    }

    public Objective(String id, String title, String text, int currentStep, int stepsNumber, ItemStack itemStack)
    {
        this.id = id;
        this.title = title;
        this.text = text;
        this.currentStep = currentStep;
        this.stepsNumber = stepsNumber;
        this.itemStack = itemStack;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getText()
    {
        return this.text;
    }

    public String getRewardName()
    {
        return this.rewardName;
    }

    public int getCurrentStep()
    {
        return this.currentStep;
    }

    public int getStepsNumber()
    {
        return this.stepsNumber;
    }

    public String getId()
    {
        return this.id;
    }

    public ItemStack getItemStack()
    {
        return this.itemStack;
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        else if (o != null && this.getClass() == o.getClass())
        {
            Objective objective = (Objective)o;
            return this.id.equals(objective.id);
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return Objects.hash(new Object[] {this.id});
    }
}
