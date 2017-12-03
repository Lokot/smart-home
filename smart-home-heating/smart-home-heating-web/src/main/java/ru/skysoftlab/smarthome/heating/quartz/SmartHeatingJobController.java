package ru.skysoftlab.smarthome.heating.quartz;

import static ru.skysoftlab.smarthome.heating.config.ConfigNames.INTERVAL;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.TEMP_INTERVAL;

import java.util.Date;

import javax.inject.Inject;

import org.apache.openejb.quartz.JobBuilder;
import org.apache.openejb.quartz.JobDetail;
import org.apache.openejb.quartz.JobKey;
import org.apache.openejb.quartz.SchedulerException;
import org.apache.openejb.quartz.Trigger;
import org.apache.openejb.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.skylibs.annatations.AppProperty;
import ru.skysoftlab.skylibs.events.EntityChangeEvent;
import ru.skysoftlab.skylibs.events.SystemConfigEvent;
import ru.skysoftlab.skylibs.quartz.JobController;
import ru.skysoftlab.smarthome.common.SmartHomeScheduler;
import ru.skysoftlab.smarthome.heating.quartz.jobs.AlarmReadJob;
import ru.skysoftlab.smarthome.heating.quartz.jobs.ScanTempJob;

public class SmartHeatingJobController implements SmartHomeScheduler {

	private static final String SYSTEM_GROUP = "system-jobs";

	private final TriggerKey alarmTK = TriggerKey.triggerKey("scan-alarm-startUp", SYSTEM_GROUP);

	@Inject
	@AppProperty(INTERVAL)
	private String interval;

	private JobController jobController;

	private Logger LOG = LoggerFactory.getLogger(SmartHeatingJobController.class);

	@Inject
	@AppProperty(TEMP_INTERVAL)
	private String tempInterval;

	private final TriggerKey tempTK = TriggerKey.triggerKey("scan-temp-startUp", SYSTEM_GROUP);

	@Override
	public void configUpdated(SystemConfigEvent event) {
		String alarmInterval = event.getParam(INTERVAL);
		if (alarmInterval != null && alarmInterval.length() > 0 && !interval.equals(alarmInterval)) {
			interval = alarmInterval;
			try {
				jobController.rescheduleJobNow(alarmTK, interval);
				LOG.info("RescheduleAlarmJob");
			} catch (SchedulerException e) {
				LOG.error("Error reschedule scan alarm job", e);
			}
		}

		String tempInterval = event.getParam(TEMP_INTERVAL);
		if (tempInterval != null && tempInterval.length() > 0 && !tempInterval.equals(event.getParam(TEMP_INTERVAL))) {
			this.tempInterval = event.getParam(TEMP_INTERVAL);
			try {
				jobController.rescheduleJobNow(tempTK, this.tempInterval);
				LOG.info("RescheduleTempJob");
			} catch (SchedulerException e) {
				LOG.error("Error reschedule scan temp job", e);
			}
		}

		// Boolean summerMode = event.getParam(SUMMER_MODE);
		// if (summerMode != null) {
		// if (summerMode) {
		// // stopAlarmReadJob();
		// } else {
		// // startAlarmReadJob();
		// }
		// }
		// params.put(HOLIDAY_MODE, holidayField.getValue());
		// params.put(HOLIDAY_MODE_START, holidayStartField.getValue());
		// params.put(HOLIDAY_MODE_STOP, holidayStopField.getValue());
	}

	@Override
	public void entityChange(EntityChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initJobController(JobController controller) {
		jobController = controller;
		startScanTempJob();
		startAlarmReadJob();
	}

	private void startAlarmReadJob() {
		try {
			JobKey alarmJobKey = JobKey.jobKey("scan-alarm", SYSTEM_GROUP);
			JobDetail alarmJob = JobBuilder.newJob(AlarmReadJob.class).withIdentity(alarmJobKey).build();
			long now = new Date().getTime();
			Trigger trigger = jobController.createCronTrigger(alarmTK, interval, new Date(now + 10000));
			jobController.getScheduler().scheduleJob(alarmJob, trigger);
		} catch (SchedulerException e) {
			LOG.error("Error create alarm job", e);
		}
	}

	private void startScanTempJob() {
		try {
			JobKey alarmJobKey = JobKey.jobKey("scan-temp", SYSTEM_GROUP);
			JobDetail alarmJob = JobBuilder.newJob(ScanTempJob.class).withIdentity(alarmJobKey).build();
			Trigger trigger = jobController.createCronTrigger(tempTK, tempInterval, null);
			jobController.getScheduler().scheduleJob(alarmJob, trigger);
		} catch (SchedulerException e) {
			LOG.error("Error create scan temp job", e);
		}
	}

}
