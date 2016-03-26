package ru.skysoftlab.smarthome.heating.quartz;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.openejb.quartz.JobBuilder;
import org.apache.openejb.quartz.JobDetail;
import org.apache.openejb.quartz.JobKey;
import org.apache.openejb.quartz.Scheduler;
import org.apache.openejb.quartz.SchedulerException;
import org.apache.openejb.quartz.SimpleScheduleBuilder;
import org.apache.openejb.quartz.Trigger;
import org.apache.openejb.quartz.TriggerBuilder;
import org.apache.openejb.quartz.TriggerKey;
import org.apache.openejb.resource.quartz.QuartzResourceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@Singleton
public class JobController {

	private Logger LOG = LoggerFactory.getLogger(JobController.class);

	private Scheduler scheduler;

	@Inject
	private CdiJobFactory jobFactory;

	@PostConstruct
	public void scheduleJobs() {
		// TODO может заинжектить?
		QuartzResourceAdapter ra;
		try {
			ra = (QuartzResourceAdapter) new InitialContext().lookup("java:openejb/Resource/QuartzResourceAdapter");
			scheduler = ra.getScheduler();

			try {
				scheduler.setJobFactory(jobFactory);

				JobKey job1Key = JobKey.jobKey("job1", "my-jobs");
				JobDetail job1 = JobBuilder.newJob(AlarmReadJob.class).withIdentity(job1Key).build();

				TriggerKey tk1 = TriggerKey.triggerKey("trigger1", "my-jobs");
				Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity(tk1).startNow()
						.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5)).build();

				scheduler.start();
				scheduler.scheduleJob(job1, newHashSet(trigger1), true);
			} catch (SchedulerException e) {
				LOG.error("Error while creating scheduler", e);
			}
		} catch (NamingException e1) {
			e1.printStackTrace();
			LOG.error("Error inject QuartzResourceAdapter", e1);
		}

	}

	private Set<? extends Trigger> newHashSet(Trigger... trigger) {
		Set<Trigger> set = new HashSet<>();
		for (Trigger t : trigger) {
			set.add(t);
		}
		return set;
	}

	@PreDestroy
	public void stopJobs() {
		if (scheduler != null) {
			try {
				scheduler.shutdown(false);
			} catch (SchedulerException e) {
				LOG.error("Error while closing scheduler", e);
			}
		}
	}

}
