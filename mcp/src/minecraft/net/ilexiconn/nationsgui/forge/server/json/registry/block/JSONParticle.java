/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.World
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.block;

import net.minecraft.world.World;

public class JSONParticle {
    private String id;
    private int amount;
    private float x;
    private float y;
    private float z;

    public void spawnParticle(World world, int x, int y, int z) {
        for (int i = 1; i < this.amount; ++i) {
            world.func_72869_a(this.id, (double)((float)x + this.x), (double)((float)y + this.y), (double)((float)z + this.z), 0.0, 0.0, 0.0);
        }
    }
}

