/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.voices.debug;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Queue;

public class MovingAverage {
    private final Queue window = new LinkedList();
    private final int period;
    private BigDecimal sum = BigDecimal.ZERO;

    public MovingAverage(int period) {
        if (period < 0) {
            System.err.println("Period must be a positive integer");
        }
        this.period = period;
    }

    public void add(int val) {
        BigDecimal num = new BigDecimal(val);
        this.sum = this.sum.add(num);
        this.window.add(num);
        if (this.window.size() > this.period) {
            this.sum = this.sum.subtract((BigDecimal)this.window.remove());
        }
    }

    public BigDecimal getAverage() {
        if (this.window.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal divisor = BigDecimal.valueOf(this.window.size());
        return this.sum.divide(divisor, 2, RoundingMode.HALF_UP);
    }
}

