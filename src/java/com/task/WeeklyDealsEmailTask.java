
package com.task;

import com.constants.HasteerConstants;
import com.dao.Deal;
import com.dao.EmailSubscriber;
import com.dao.User;
import com.util.ConfigUtil;
import com.api.Jsp;
import com.api.notification.WeeklyDeals;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Alinur Goksel
 */
public class WeeklyDealsEmailTask extends TimerTask {
    public static final long period = 
            ConfigUtil.getLong(Jsp.getProperty(WeeklyDealsEmailTask.class, "period"),
            HasteerConstants.DEFAULT_WEEKLY_DEALS_EMAIL_TASK_PERIOD);

    public WeeklyDealsEmailTask(){
        Timer timer = new Timer();
        Calendar c = Calendar.getInstance();
        c.set(2010, Calendar.SEPTEMBER, 20, 10, 0);
        timer.scheduleAtFixedRate(this, c.getTime(), period);
    }
    
    public void run() {
        System.out.println("Processing Weekly Deals Email Task...");

        List<Deal> deals = Deal.getUpcomingDeals();
        String emailTypeKey = WeeklyDeals.class.getSimpleName();
        List<EmailSubscriber> subscribers = EmailSubscriber.getByEmailTypeKey(emailTypeKey);

        for (EmailSubscriber subscriber : subscribers) {
            try {
                WeeklyDeals w = new WeeklyDeals(User.getUserByEmail(subscriber.getEmailAddress()), deals);
                w.generateEmail();
            } catch (Exception e) {}
        }
    }

}
