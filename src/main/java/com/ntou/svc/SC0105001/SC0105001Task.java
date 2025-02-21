package com.ntou.svc.SC0105001;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import javax.ws.rs.core.Response;

public class SC0105001Task implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        try {
            try (Response response = new SC0105001().doAPI()) {}

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
