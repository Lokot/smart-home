  package smarthome.gpio;
  
  import javax.ejb.Singleton;
  import javax.ejb.Startup;
  
  @Singleton
  @Startup
  public class PinCreater
  {
    public PinCreater()
    {
      Thread starter = new Thread(new Starter());
      
      starter.start();
      try
      {
        starter.join();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }


/* Location:           E:\classes\
 * Qualified Name:     smarthome.gpio.PinCreater
 * JD-Core Version:    0.7.0.1
 */