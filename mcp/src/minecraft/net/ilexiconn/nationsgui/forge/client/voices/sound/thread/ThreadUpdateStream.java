/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package net.ilexiconn.nationsgui.forge.client.voices.sound.thread;

import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.voices.sound.PlayableStream;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundManager;
import net.ilexiconn.nationsgui.forge.server.voices.EntityVector;
import net.minecraft.client.Minecraft;

public class ThreadUpdateStream
implements Runnable {
    private SoundManager manager;
    Minecraft mc;

    public ThreadUpdateStream(SoundManager manager) {
        this.manager = manager;
        this.mc = Minecraft.func_71410_x();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        while (true) {
            Object e1;
            if (!this.manager.streaming.isEmpty()) {
                Object stream;
                e1 = this.manager.streaming.entrySet().iterator();
                int i = 0;
                while (e1.hasNext()) {
                    stream = (PlayableStream)((Map.Entry)e1.next()).getValue();
                    if (i < 3) {
                        if (((PlayableStream)stream).end) {
                            if (!this.mc.field_71416_A.field_77381_a.playing("" + ((PlayableStream)stream).id)) {
                                this.manager.killStream(((PlayableStream)stream).id);
                            }
                        } else if (System.currentTimeMillis() - ((PlayableStream)stream).lastUpdated > 340L && !this.mc.field_71416_A.field_77381_a.playing("" + ((PlayableStream)stream).id)) {
                            this.manager.killStream(((PlayableStream)stream).id);
                        }
                        switch (((PlayableStream)stream).voiceMode) {
                            case 0: {
                                this.mc.field_71416_A.field_77381_a.setPosition("" + ((PlayableStream)stream).id, (float)this.mc.field_71439_g.field_70165_t, (float)this.mc.field_71439_g.field_70163_u, (float)this.mc.field_71439_g.field_70161_v);
                                this.mc.field_71416_A.field_77381_a.setVelocity("" + ((PlayableStream)stream).id, (float)this.mc.field_71439_g.field_70165_t, (float)this.mc.field_71439_g.field_70181_x, (float)this.mc.field_71439_g.field_70179_y);
                                break;
                            }
                            case 1: {
                                EntityVector vector = ((PlayableStream)stream).getCustomEntityVector();
                                this.mc.field_71416_A.field_77381_a.setPosition("" + ((PlayableStream)stream).id, (float)vector.x, (float)vector.y, (float)vector.z);
                                this.mc.field_71416_A.field_77381_a.setVelocity("" + ((PlayableStream)stream).id, (float)vector.motX, (float)vector.motY, (float)vector.motZ);
                            }
                        }
                    } else {
                        this.manager.killStream(((PlayableStream)stream).id);
                    }
                    ++i;
                }
                try {
                    stream = this;
                    synchronized (stream) {
                        this.wait(30L);
                    }
                }
                catch (InterruptedException var8) {
                    var8.printStackTrace();
                }
                continue;
            }
            try {
                e1 = this;
                synchronized (e1) {
                    this.wait(500L);
                    continue;
                }
            }
            catch (InterruptedException var10) {
                var10.printStackTrace();
                continue;
            }
            break;
        }
    }
}

