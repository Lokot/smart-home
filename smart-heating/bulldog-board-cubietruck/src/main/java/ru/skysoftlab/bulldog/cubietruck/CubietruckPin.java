package ru.skysoftlab.bulldog.cubietruck;

import io.silverspoon.bulldog.core.pin.Pin;

public class CubietruckPin extends Pin {

   private String fsName;
   private boolean interrupt;

   public CubietruckPin(String name, int address, String port, int indexOnPort, String fsName, boolean interrupt) {
      super(name, address, port, indexOnPort);
      this.fsName = fsName;
      this.interrupt = interrupt;
   }

   public String getFsName() {
      return fsName;
   }

   public boolean isInterrupt() {
      return interrupt;
   }
}
