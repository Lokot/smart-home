package smarthome.owfs;

import java.io.IOException;

public class OwfsServerStarter {
	
	public static final Integer owsfPort = Integer.valueOf(3000);

	public static void startOwfsServer() throws IOException {
		Runtime.getRuntime().exec(
				new String[] { "owserver", "-u", "-p", owsfPort.toString() });
	}
}