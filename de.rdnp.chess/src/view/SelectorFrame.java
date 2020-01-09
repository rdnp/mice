package view;

import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * A Frame that is shown to present the user several options to select and that 
 * will close automatically when one option is selected.
 * @author Richard Pohl
 * */
public class SelectorFrame extends JFrame implements WindowListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8252575284146820650L;
	
	public SelectorFrame() {
		setUndecorated(true);
		setLayout(new FlowLayout());
		addWindowListener(this);
	}
	
	@Override
	public void windowActivated(WindowEvent e) {}
	
	@Override
	public void windowClosed(WindowEvent e) {}
	
	@Override
	public void windowClosing(WindowEvent e) {}
	
	@Override
	public void windowDeactivated(WindowEvent e) {
		// automatically close this frame
		dispose();
	}
	
	@Override
	public void windowDeiconified(WindowEvent e) {}
	
	@Override
	public void windowIconified(WindowEvent e) {}
	
	@Override
	public void windowOpened(WindowEvent e) {}
}
