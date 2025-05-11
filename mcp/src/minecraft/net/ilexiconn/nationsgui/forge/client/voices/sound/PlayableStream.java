/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.entity.Entity
 */
package net.ilexiconn.nationsgui.forge.client.voices.sound;

import java.util.Comparator;
import net.ilexiconn.nationsgui.forge.client.voices.debug.MovingAverage;
import net.ilexiconn.nationsgui.forge.client.voices.sound.JitterBuffer;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundManager;
import net.ilexiconn.nationsgui.forge.server.voices.EntityVector;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;

public class PlayableStream {
    public final int id;
    public int voiceMode;
    public Entity entity;
    public boolean end;
    public long lastUpdated;
    public long lastFlushed;
    private MovingAverage jitterAverage = new MovingAverage(5);
    public JitterBuffer buffer;
    private SoundManager manager;

    public PlayableStream(SoundManager manager, int id, int voiceMode) {
        this.manager = manager;
        this.id = id;
        this.voiceMode = voiceMode;
        this.lastUpdated = System.currentTimeMillis();
        this.lastFlushed = System.currentTimeMillis();
        this.buffer = new JitterBuffer(SoundManager.universalAudioFormat, 0);
    }

    public int getJitterRate() {
        return this.jitterAverage.getAverage().intValue();
    }

    public String getEntityName() {
        return "missingno" + this.id;
    }

    public boolean isEntityPlayer() {
        return this.entity instanceof AbstractClientPlayer;
    }

    public void update(int l) {
        this.jitterAverage.add(l);
    }

    public EntityVector getCustomEntityVector() {
        EntityVector vector = this.getEntityVector();
        return vector.entityName != null ? vector : (this.entity != null ? new EntityVector(this.id, "", this.entity.field_70165_t, this.entity.field_70163_u, this.entity.field_70161_v, this.entity.field_70159_w, this.entity.field_70181_x, this.entity.field_70179_y) : EntityVector.NULL);
    }

    public EntityVector getEntityVector() {
        if (this.manager.entityData.containsKey(this.id)) {
            return this.manager.entityData.get(this.id);
        }
        return null;
    }

    public AbstractClientPlayer getPlayer() {
        return (AbstractClientPlayer)this.entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public static class PlayableStreamComparator
    implements Comparator<PlayableStream> {
        @Override
        public int compare(PlayableStream a, PlayableStream b) {
            int f = a.id < b.id ? -1 : (a.id > b.id ? 1 : 0);
            return f;
        }
    }
}

