/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

import com.task.AbstractTask;

/**
 *
 * @author ecolak
 */
public class TaskUtil {
    public static void enableTask(long taskId, String className, int delay, int period) throws Exception {
        Object o = Class.forName(className).newInstance();
        if(o != null && o instanceof AbstractTask) {
            AbstractTask at = (AbstractTask)o;
            TaskMap.getInstance().put(taskId, at);
            at.setDelay(delay);
            at.setPeriod(period);
            at.startTask();
        }
    }

    public static void disableTask(long taskId) throws Exception {
        AbstractTask at = TaskMap.getInstance().get(taskId);
        if(at != null)
            at.cancel();
    }
}
