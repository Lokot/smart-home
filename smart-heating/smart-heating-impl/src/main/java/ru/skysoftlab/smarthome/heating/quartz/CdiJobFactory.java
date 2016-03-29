package ru.skysoftlab.smarthome.heating.quartz;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.openejb.quartz.Job;
import org.apache.openejb.quartz.JobDetail;
import org.apache.openejb.quartz.Scheduler;
import org.apache.openejb.quartz.SchedulerException;
import org.apache.openejb.quartz.spi.JobFactory;
import org.apache.openejb.quartz.spi.TriggerFiredBundle;

/**
 * Фабрика зачач.
 * 
 * @author Артём
 *
 */
public class CdiJobFactory implements JobFactory {

	/**
	 * Inject any Quartz job instance into an Iterable.
	 */
	@Inject @Any
	private Instance<Job> jobs;

	@Override
	public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
		final JobDetail jobDetail = bundle.getJobDetail();
		final Class<? extends Job> jobClass = jobDetail.getJobClass();

		for (Job job : jobs) {
			if (job.getClass().isAssignableFrom(jobClass)) {
				return job;
			}
		}

		throw new SchedulerException("Cannot create a Job of type " + jobClass);
	}

}
