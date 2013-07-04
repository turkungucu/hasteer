/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import com.dao.Task;
import com.struts.form.TasksForm;
import com.task.AbstractTask;
import com.util.TaskUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 *
 * @author ecolak
 */
public class TasksAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();

        TasksForm frm = (TasksForm)form;
        if(StringUtils.isBlank(frm.getCmd())) {
            try {
                frm.setTasks(Task.getAll());
            } catch(Exception e) {
                errors.add(Globals.ERROR_KEY, new ActionMessage("dbException", e.getMessage()));
                saveErrors(request, errors);
                return (mapping.findForward("failure"));
            }
        }

        else if("view".equals(frm.getCmd()) || "enable".equals(frm.getCmd()) || 
                "disable".equals(frm.getCmd()) || "remove".equals(frm.getCmd())) {
            long taskId = 0;
            try {
                taskId = Long.parseLong(frm.getTaskId());
            } catch(Exception e) {
                // ignore
            }

            if(taskId > 0) {
                Task task = null;
                try {
                    task = Task.getById(taskId);
                } catch(Exception e) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("dbException", e.getMessage()));
                    saveErrors(request, errors);
                    return (mapping.findForward("failure"));
                }

                if(task == null) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("errors.does.not.exist", "Task"));
                    saveErrors(request, errors);
                    return (mapping.findForward("failure"));
                }

                if("view".equals(frm.getCmd())) {
                    initForm(frm, task);
                    if(task.isEnabled()) {
                        messages.add(Globals.MESSAGE_KEY, new ActionMessage("TasksForm.task.running"));
                        saveMessages(request, messages);
                    }
                } else if("enable".equals(frm.getCmd())) {
                    task.setEnabled(true);
                    task.save();
                    initAllTasks(frm);
                    try {
                        TaskUtil.enableTask(task.getTaskId(), task.getClassName(), task.getDelay(), task.getPeriod());
                    } catch(Exception e) {
                        errors.add(Globals.ERROR_KEY, new ActionMessage("shared.errors.generic.error", e.getMessage()));
                        saveErrors(request, errors);
                        return (mapping.findForward("failure"));
                    }                   
                } else if("disable".equals(frm.getCmd())) {
                    task.setEnabled(false);
                    task.save();
                    initAllTasks(frm);
                    try {
                        TaskUtil.disableTask(task.getTaskId());
                    } catch(Exception e) {
                        errors.add(Globals.ERROR_KEY, new ActionMessage("shared.errors.generic.error", e.getMessage()));
                        saveErrors(request, errors);
                        return (mapping.findForward("failure"));
                    }
                } else if("remove".equals(frm.getCmd())) {
                    task.delete();
                    initAllTasks(frm);
                }
            }
            
        }

        else if("submit".equals(frm.getCmd())) {
            long taskId = 0;
            try {
                taskId = Long.parseLong(frm.getTaskId());
            } catch(Exception e) {
                // ignore
            }

            try {
                Task task = null;
                if(taskId > 0)
                    task = Task.getById(taskId);
                else
                    task = new Task();

                if(task != null) {
                    task.setTaskName(frm.getTaskName());
                    task.setClassName(frm.getClassName());
                    task.setDelay(Integer.parseInt(frm.getDelay()));
                    task.setPeriod(Integer.parseInt(frm.getPeriod()));
                    task.save();
                }
            } catch(Exception e) {
                errors.add(Globals.ERROR_KEY, new ActionMessage("dbException", e.getMessage()));
                saveErrors(request, errors);
                return (mapping.findForward("failure"));
            }
         
            messages.add(Globals.MESSAGE_KEY, new ActionMessage("shared.success.changesSaved"));
            saveMessages(request, messages);
        }

        return mapping.findForward("success");
    }

    private void initForm(TasksForm frm, Task task) {
        frm.setTaskId(String.valueOf(task.getTaskId()));
        frm.setTaskName(task.getTaskName());
        frm.setClassName(task.getClassName());
        frm.setDelay(String.valueOf(task.getDelay()));
        frm.setPeriod(String.valueOf(task.getPeriod()));
    }

    private void initAllTasks(TasksForm frm) {
        frm.setCmd(null);
        frm.setTasks(Task.getAll());
    }
}
