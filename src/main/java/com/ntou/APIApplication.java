package com.ntou;

import com.ntou.svc.SC0105001.SC0105001Task;
import com.ntou.tool.Common;
import com.ntou.tool.DateTool;
import lombok.extern.log4j.Log4j2;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Log4j2
@ApplicationPath("/res")
public class APIApplication extends ResourceConfig {
    final String MONTHLY_JOB = "monthlyJob";
    final String MONTHLY_TRIGGER = "monthlyTrigger";

    private Scheduler scheduler;

    public APIApplication() {
        packages("com.ntou.svc");
        packages("com.ntou.exceptions");
    }

    @PostConstruct
    public void init() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            doSC0105001Task();

            log.info(Common.START_B + "scheduler-" + DateTool.getDateTime());
        } catch (SchedulerException e) {
            log.error(Common.EXCEPTION, e);
        }
    }
    @PreDestroy
    public void cleanup() {
        try {
            if (scheduler != null) {
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            log.error(Common.EXCEPTION, e);
        }
    }
    private void doSC0105001Task() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(SC0105001Task.class)
                .withIdentity(MONTHLY_JOB + SC0105001Task.class.getName(), SC0105001Task.class.toString())
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(MONTHLY_TRIGGER + SC0105001Task.class.getName(), SC0105001Task.class.toString())
                .withSchedule(CronScheduleBuilder.cronSchedule("0 15 14 20 * ?"))//("0 0 6 30 * ?"))// 每月30號早上六點觸發(秒、分、小時、日期、月份、星期)
                .build();
        scheduler.scheduleJob(job, trigger);
    }
}
