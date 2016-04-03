https://vaadin.com/tutorial
https://vaadin.com/directory#!addon/loginform
https://dev.vaadin.com/svn/doc/book-examples/trunk/src/com/vaadin/book/examples/addons/jpacontainer/JPAContainerExample.java


tomee.xml add
<Resource id="smartHomeDataSource" type="DataSource">
		JdbcDriver  org.hsqldb.jdbcDriver
		JdbcUrl jdbc:hsqldb:mem:hsqldb
		UserName    root
		Password    some_pass
		JtaManaged true
	</Resource>

	org.owfs.jowfsclient.OwfsException: Error -1
        at org.owfs.jowfsclient.internal.OwfsConnectionImpl.readPacket(OwfsConnectionImpl.java:264)
        at org.owfs.jowfsclient.internal.OwfsConnectionImpl.write(OwfsConnectionImpl.java:150)
        at ru.skysoftlab.smarthome.heating.util.OwsfUtilDS18B.setTemphigh(OwsfUtilDS18B.java:32)
        at ru.skysoftlab.smarthome.heating.owfs.Ds18bAlarmingDeviceListener.onInitialize(Ds18bAlarmingDeviceListener.java:36)
        at org.owfs.jowfsclient.alarm.AlarmingDevicesReader.addAlarmingDeviceHandler(AlarmingDevicesReader.java:32)
        at ru.skysoftlab.smarthome.heating.quartz.AlarmReadJob.setAlarmingDeviceHandler(AlarmReadJob.java:68)
        at ru.skysoftlab.smarthome.heating.quartz.AlarmReadJob.init(AlarmReadJob.java:50)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:483)
        at org.apache.webbeans.intercept.LifecycleInterceptorInvocationContext.proceed(LifecycleInterceptorInvocationContext.java:103)
        at org.apache.webbeans.portable.InjectionTargetImpl.postConstruct(InjectionTargetImpl.java:301)
        at org.apache.webbeans.component.AbstractOwbBean.create(AbstractOwbBean.java:126)
        at org.apache.webbeans.component.ManagedBean.create(ManagedBean.java:55)
        at org.apache.webbeans.context.creational.BeanInstanceBag.create(BeanInstanceBag.java:76)
        at org.apache.webbeans.context.AbstractContext.getInstance(AbstractContext.java:160)
        at org.apache.webbeans.context.AbstractContext.get(AbstractContext.java:125)
        at org.apache.webbeans.container.BeanManagerImpl.getReference(BeanManagerImpl.java:754)
        at org.apache.webbeans.inject.instance.InstanceImpl.iterator(InstanceImpl.java:270)
        at ru.skysoftlab.smarthome.heating.quartz.CdiJobFactory.newJob(CdiJobFactory.java:33)
        at org.apache.openejb.quartz.core.JobRunShell.initialize(JobRunShell.java:127)
        at org.apache.openejb.quartz.core.QuartzSchedulerThread.run(QuartzSchedulerThread.java:375)