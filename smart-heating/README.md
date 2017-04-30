Bulldog GPIO Library for Java 
https://github.com/px3/bulldog/tree/master
https://github.com/px3/bulldog.git

##Build
mvn clean install -P tomee,buildVaadin
### Build for OneWire file system
mvn clean install -P tomee,owfs
### Build for use native OneWireAPI
mvn clean install -P tomee,owapi

Зависимости GitHub:
https://github.com/downdrown/VaadinHighChartsAPI.git
https://github.com/adamheinrich/native-utils.git


// TODO разобраться
02, 2016 1:05:35 AM org.owfs.jowfsclient.alarm.AlarmingDevicesReader handleConnectionException
WARNING: Exception occured
java.lang.NullPointerException
        at org.owfs.jowfsclient.util.OWFSUtils.extractDeviceNameFromDevicePath(OWFSUtils.java:11)
        at org.owfs.jowfsclient.alarm.AlarmingDevicesReader.processAlarmingDevice(AlarmingDevicesReader.java:75)
        at org.owfs.jowfsclient.alarm.AlarmingDevicesReader.processAlarmingDevices(AlarmingDevicesReader.java:69)
        at org.owfs.jowfsclient.alarm.AlarmingDevicesReader.readAlarmingDirectory(AlarmingDevicesReader.java:63)
        at org.owfs.jowfsclient.alarm.AlarmingDevicesReader.tryToReadAlarmingDirectory(AlarmingDevicesReader.java:55)
        at org.owfs.jowfsclient.alarm.AlarmingDevicesReader.run(AlarmingDevicesReader.java:28)
        at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
        at java.util.concurrent.FutureTask.runAndReset(FutureTask.java:308)
        at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)
        at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
        at java.lang.Thread.run(Thread.java:745)