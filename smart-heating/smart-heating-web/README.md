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
	
	
SEVERE: An error occured instantiating job to be executed. job= 'my-jobs.job1'
org.apache.openejb.quartz.SchedulerException: Problem instantiating class 'ru.skysoftlab.smarthome.heating.quartz.AlarmReadJob' -  [See nested exception: java.lang.NullPointerException]
        at org.apache.openejb.quartz.core.JobRunShell.initialize(JobRunShell.java:134)
        at org.apache.openejb.quartz.core.QuartzSchedulerThread.run(QuartzSchedulerThread.java:375)
Caused by: java.lang.NullPointerException
        at ru.skysoftlab.smarthome.heating.cdi.OwfsProducer.getOwfsConnectionFactory(OwfsProducer.java:48)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:483)
        at org.apache.webbeans.inject.InjectableMethod.doInjection(InjectableMethod.java:155)
        at org.apache.webbeans.portable.ProducerMethodProducer.produce(ProducerMethodProducer.java:110)
        at org.apache.webbeans.portable.AbstractProducer.produce(AbstractProducer.java:182)
        at org.apache.webbeans.component.AbstractOwbBean.create(AbstractOwbBean.java:121)
        at org.apache.webbeans.component.ProducerMethodBean.create(ProducerMethodBean.java:117)
        at org.apache.webbeans.context.DependentContext.getInstance(DependentContext.java:68)
        at org.apache.webbeans.context.AbstractContext.get(AbstractContext.java:125)
        at org.apache.webbeans.container.BeanManagerImpl.getReference(BeanManagerImpl.java:754)
        at org.apache.webbeans.container.BeanManagerImpl.getInjectableReference(BeanManagerImpl.java:628)
        at org.apache.webbeans.inject.AbstractInjectable.inject(AbstractInjectable.java:95)
        at org.apache.webbeans.inject.InjectableField.doInjection(InjectableField.java:65)
        at org.apache.webbeans.portable.InjectionTargetImpl.injectFields(InjectionTargetImpl.java:208)
        at org.apache.webbeans.portable.InjectionTargetImpl.inject(InjectionTargetImpl.java:194)
        at org.apache.webbeans.portable.InjectionTargetImpl.inject(InjectionTargetImpl.java:184)
        at org.apache.webbeans.component.AbstractOwbBean.create(AbstractOwbBean.java:125)
        at org.apache.webbeans.component.ManagedBean.create(ManagedBean.java:55)
        at org.apache.webbeans.context.creational.BeanInstanceBag.create(BeanInstanceBag.java:76)
        at org.apache.webbeans.context.AbstractContext.getInstance(AbstractContext.java:160)
        at org.apache.webbeans.context.AbstractContext.get(AbstractContext.java:125)
        at org.apache.webbeans.container.BeanManagerImpl.getReference(BeanManagerImpl.java:754)
        at org.apache.webbeans.inject.instance.InstanceImpl.iterator(InstanceImpl.java:270)
        at ru.skysoftlab.smarthome.heating.quartz.CdiJobFactory.newJob(CdiJobFactory.java:33)
        at org.apache.openejb.quartz.core.JobRunShell.initialize(JobRunShell.java:127)
        ... 1 more
	