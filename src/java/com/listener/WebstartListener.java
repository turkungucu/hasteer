/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.listener;

import com.dao.Task;
import com.task.AbstractTask;
import com.util.ConfigUtil;
import com.util.TaskUtil;
import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author ecolak
 */
public class WebstartListener implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("app server is about to get destroyed");
    }

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("app server started");
        
        ConfigUtil.runStaticInitializers();
        List<Task> enabledTasks = null;
        try {
            enabledTasks = Task.getEnabledTasks();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        if(enabledTasks != null) {
            for(Task task : enabledTasks) {
                try {
                    TaskUtil.enableTask(task.getTaskId(), task.getClassName(),
                            task.getDelay(), task.getPeriod());
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } 
        //WeeklyDealsEmailTask task = new WeeklyDealsEmailTask();
        //ProcessDealsTask task = new ProcessDealsTask();
        //Thread t = new Thread(new LuceneProductIndexerTask());
        //t.start();
        //LuceneProductIndexerTask task2 = new LuceneProductIndexerTask();
    }
}
