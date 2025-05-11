/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.client.data;

import java.util.Objects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Objective {
    private String id;
    private String title;
    private String text;
    private String rewardName;
    private int currentStep;
    private int stepsNumber;
    private ItemStack itemStack = null;

    public Objective(NBTTagCompound nbtTagCompound) {
        this.id = nbtTagCompound.func_74779_i("id");
        this.title = nbtTagCompound.func_74779_i("title");
        this.text = nbtTagCompound.func_74779_i("text");
        this.currentStep = nbtTagCompound.func_74762_e("currentStep");
        this.stepsNumber = nbtTagCompound.func_74762_e("stepsNumber");
        this.rewardName = nbtTagCompound.func_74779_i("rewardName");
        if (nbtTagCompound.func_74764_b("itemId") && Item.field_77698_e[nbtTagCompound.func_74762_e("itemId")] != null) {
            this.itemStack = new ItemStack(nbtTagCompound.func_74762_e("itemId"), nbtTagCompound.func_74762_e("itemQuantity"), nbtTagCompound.func_74762_e("itemMeta"));
        }
    }

    public Objective(String id, String title, String text, int currentStep, int stepsNumber, ItemStack itemStack) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.currentStep = currentStep;
        this.stepsNumber = stepsNumber;
        this.itemStack = itemStack;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public String getRewardName() {
        return this.rewardName;
    }

    public int getCurrentStep() {
        return this.currentStep;
    }

    public int getStepsNumber() {
        return this.stepsNumber;
    }

    public String getId() {
        return this.id;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Objective objective = (Objective)o;
        return this.id.equals(objective.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}

