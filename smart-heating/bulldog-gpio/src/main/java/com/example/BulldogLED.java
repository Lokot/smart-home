package com.example;


import ru.skysoftlab.bulldog.cubietruck.CubietruckNames;
import io.silverspoon.bulldog.core.gpio.DigitalOutput;
import io.silverspoon.bulldog.core.pin.Pin;
import io.silverspoon.bulldog.core.platform.Board;
import io.silverspoon.bulldog.core.platform.Platform;
import io.silverspoon.bulldog.core.util.BulldogUtil;

public class BulldogLED {
	public static void main(String[] args) {
		// Detect the board we are running on
		Board board = Platform.createBoard();
		System.out.println(board);
		Pin pin = board.getPin(CubietruckNames.PC20);
		System.out.println(pin);

		for (Pin ppp : board.getPins()) {
			System.out.println(ppp.getAddress() + " - " + ppp.getAlias() + " - " + ppp.getIndexOnPort() + " - "
					+ ppp.getName() + " - " + ppp.getPort());
		}
//		Boiler(5, 7, "PC20", "Котел", true);
		
		//
		//
		 // Set up a digital output
		 DigitalOutput output = pin.as(DigitalOutput.class);
		
		 // Blink the LED
		 output.high();
		 BulldogUtil.sleepMs(10000);
		 output.low();
	}
}
