package main;

import io.SetupFileParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Launcher for MICE.
 * @author Richard Pohl
 * */
public class MiceLauncher {	
	public static void main(String[] args)
	{
		File setupFile = new File("setup.dat");
		if (!setupFile.exists())
		{
			System.out.println("Launching setup...");
			// execute setup
			try {
				Process setup 
					= Runtime.getRuntime().exec("java -jar setup.jar");
				setup.waitFor();
				Thread.sleep(2000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Setup finished.");
		}
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(setupFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		SetupFileParser setupFileParser = new SetupFileParser(fileInputStream);
		if (fileInputStream != null)
		{
			// parse settings
			setupFileParser.parse();
			// launch MICE
			try {
				Runtime.getRuntime().exec(
					"java -Xmx"+setupFileParser.getMemory()+"M -jar micec.jar "
					+setupFileParser.getNbCores());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Launching micec...");
	}
}
