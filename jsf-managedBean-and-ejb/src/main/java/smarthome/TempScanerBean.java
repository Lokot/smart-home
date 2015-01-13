  package smarthome;
  
  import java.io.IOException;
import java.util.Date;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;

import org.owfs.jowfsclient.Enums.OwBusReturn;
import org.owfs.jowfsclient.Enums.OwPersistence;
import org.owfs.jowfsclient.Enums.OwTemperatureScale;
import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsConnectionConfig;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.OwfsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthome.entitys.Temp;
import smarthome.owfs.OwfsServerStarter;
  
  @Singleton
  @Startup
  public class TempScanerBean
  {
    private Logger logger = LoggerFactory.getLogger("scanTemps");
    private static OwfsConnection client = null;
    
    public TempScanerBean()
    {
      try
      {
        OwfsServerStarter.startOwfsServer();
        try
        {
          OwfsConnectionConfig connectionConfig = new OwfsConnectionConfig("127.0.0.1", OwfsServerStarter.owsfPort.intValue());
          connectionConfig.setPersistence(OwPersistence.ON);
          connectionConfig.setTemperatureScale(OwTemperatureScale.CELSIUS);
          connectionConfig.setBusReturn(OwBusReturn.ON);
          client = OwfsConnectionFactory.newOwfsClient(connectionConfig);
        }
        catch (Exception e)
        {
          this.logger.error("Ошибка соединения с сервером OWFS", e);
        }
      }
      catch (IOException ex)
      {
        this.logger.error("Ошибка запуска сервера OWFS на порту " + OwfsServerStarter.owsfPort, ex);
      }
    }
    
    @Schedule(minute = "*/30", hour = "*", persistent = false)
    private void scanTemps()
    {
      this.logger.info("Сканирование температур.");
      Date dateTime = new Date();
      EntityManager em = HibernateUtil.getEm();
      for (Sensors sensor : Sensors.values())
      {
        Temp temp = new Temp();
        temp.setSensor(sensor);
        temp.setDate(dateTime);
        try
        {
          temp.setTemp(getTemp(sensor).floatValue());
          em.getTransaction().begin();
          em.persist(temp);
          em.getTransaction().commit();
        }
        catch (OwfsException ex)
        {
          this.logger.error("Ошибка чтения температуры с датчика № " + sensor.getId());
        }
        catch (Exception e)
        {
          e.printStackTrace();
          this.logger.error("Ошибка сохранения температуры с датчика № " + sensor.getId());
          em.getTransaction().rollback();
        }
      }
    }
    
    public static Float getTemp(Sensors sensor)
      throws OwfsException
    {
      if (client != null)
      {
        if (sensor.getId().length() < 1) {
          throw new OwfsException("Отсутствует идентификатор датчика.", -1);
        }
        try
        {
          return Float.valueOf(client.read("/" + sensor.getId() + "/temperature").replace(" ", ""));
        }
        catch (IOException ex)
        {
          throw new OwfsException(ex.getMessage(), -1);
        }
        catch (OwfsException ex)
        {
          throw ex;
        }
      }
      throw new OwfsException("Отсутствует подключение к OWFS серверу.", -1);
    }
  }