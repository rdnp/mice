
package view;

import io.SetupFileParser;
import io.SetupFileWriter;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Setup dialog for MICE.
 * @author Richard Pohl
 * */
public class SetupDialog extends Dialog implements WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8065894484097562089L;
	private Choice memory;
	private Choice nbCores;
	
	
	public SetupDialog() {
		super((Dialog)null, "MICE Setup © 2011, Richard Pohl. " +
				"All rights reserved.");
		// load data
		SetupFileParser parser = null;
		File setupFile = new File("setup.dat");
		if (setupFile.exists())
			try {
				parser = new SetupFileParser(
					new FileInputStream(setupFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		if (parser != null)
			parser.parse();
		else
			parser = new SetupFileParser(null);
		add(new Label("Maximum memory to use (MB):"));
		memory = new Choice();
		memory.add("128");
		memory.add("256");
		memory.add("512");
		memory.add("1024");
		memory.add("1536");
		memory.add("2048");
		memory.add("2560");
		memory.add("3072");
		memory.add("3584");
		memory.add("4096");
		memory.add("5120");
		memory.add("6144");
		memory.add("7168");
		memory.add("8192");
		memory.add("10240");
		memory.add("12288");
		memory.add("14336");
		memory.add("16384");
		memory.add("20480");
		add(memory);
		memory.select(Integer.toString(parser.getMemory()));
		add(new Label("Maximum number of cores to use:"));
		nbCores = new Choice();
		nbCores.add("1");
		nbCores.add("2");
		nbCores.add("3");
		nbCores.add("4");
		nbCores.add("5");
		nbCores.add("6");
		nbCores.add("7");
		nbCores.add("8");
		nbCores.add("9");
		nbCores.add("10");
		nbCores.add("11");
		nbCores.add("12");
		nbCores.add("13");
		nbCores.add("14");
		nbCores.add("15");
		nbCores.add("16");
		nbCores.select(Integer.toString(parser.getNbCores()));
		add(nbCores);
		addWindowListener(this);
		setLayout(new FlowLayout());
		pack();
	}
	
	@Override
	public void windowActivated(WindowEvent e) {}
	
	@Override
	public void windowClosed(WindowEvent e) {}
	
	@Override
	public void windowClosing(WindowEvent e) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("setup.dat");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		if (fileOutputStream != null)
		{
			SetupFileWriter writer = new SetupFileWriter(fileOutputStream);
			writer.write(Integer.parseInt(memory.getSelectedItem()),
						 Integer.parseInt(nbCores.getSelectedItem()));
		}
		System.exit(0);
	}
	
	@Override
	public void windowDeactivated(WindowEvent e) {}
	
	@Override
	public void windowDeiconified(WindowEvent e) {}
	
	@Override
	public void windowIconified(WindowEvent e) {}
	
	@Override
	public void windowOpened(WindowEvent e) {}
	
	public static void main(String [] args)
	{
		SetupDialog dialog = new SetupDialog();
		dialog.setVisible(true);
	}
}
