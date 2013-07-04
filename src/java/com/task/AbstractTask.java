/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.task;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author ecolak
 */
public abstract class AbstractTask extends TimerTask {
    private int delay;
    private int period;
    private Timer timer;

    public AbstractTask() {}

    public AbstractTask(int delay, int period) {
        this.delay = delay;
        this.period = period;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public void startTask() {
        timer = new Timer();
        timer.scheduleAtFixedRate(this, delay, period);
    }

    public abstract void run();
}
