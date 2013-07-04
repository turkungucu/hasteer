/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dao;

import com.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ecolak
 */
@Entity
@Table(name = "tasks")
    
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String HQL_GET_ALL = "SELECT t FROM Task t";
    private static final String HQL_GET_ENABLED = "SELECT t FROM Task t WHERE enabled = 1";
    private static final String HQL_GET_BY_TASK_ID = "SELECT t FROM Task t WHERE t.taskId = :taskId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "task_id")
    private Long taskId;

    @Basic(optional = false)
    @Column(name = "task_name")
    private String taskName;

    @Basic(optional = false)
    @Column(name = "class_name")
    private String className;

    @Column(name = "period")
    private Integer period;

    @Column(name = "delay")
    private Integer delay;

    @Basic(optional = false)
    @Column(name = "enabled")
    private boolean enabled;

    public Task() {
    }

    public Task(Long taskId) {
        this.taskId = taskId;
    }

    public Task(Long taskId, String taskName, String className, boolean enabled) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.className = className;
        this.enabled = enabled;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static List<Task> getAll() throws HibernateException {
        List<Task> deals = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_ALL);
        deals = query.list();
        tx.commit();
        return deals;
    }

    public static List<Task> getEnabledTasks() throws HibernateException {
        List<Task> deals = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_ENABLED);
        deals = query.list();
        tx.commit();
        return deals;
    }

    public static Task getById(long taskId) throws HibernateException {
        Task task = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_TASK_ID);
        query.setLong("taskId", taskId);
        task = (Task)query.uniqueResult();
        tx.commit();

        return task;
    }

    public void save() throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(this);
        tx.commit();
    }

    public void delete() throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.delete(this);
        tx.commit();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskId != null ? taskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.taskId == null && other.taskId != null) || (this.taskId != null && !this.taskId.equals(other.taskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dao.Tasks[taskId=" + taskId + "]";
    }

}
