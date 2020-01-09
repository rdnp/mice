package view;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A Button in a {@link SelectorFrame}, i.e. a Frame that is shown to present
 * the user several options to select and that will close automatically when
 * one option is selected.
 * @author Richard Pohl
 * */
public class SelectorButton extends Button implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1860904494832091525L;
	
	private Selector selector;
	private SelectorFrame container;
	private SelectorOption option;
	
	/** Constructs a {@link SelectorButton} and adds it to container.
	 * @param selector - The {@link Selector} connected with this Button.
	 * @param container - The container to add the button to.
	 * @param option - The command to execute when the option represented
	 * by the {@link SelectorButton} is selected. */
	public SelectorButton(Selector selector, SelectorFrame container,
			SelectorOption option) {
		this.selector = selector;
		this.container = container;
		this.option = option;
		container.add(this);
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// execute command
		option.choose();
		// close container frame
		container.setVisible(false);
		// set selector image
		selector.setImage(option.getImage());
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(48,48);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(option.getImage(), 2, 2, getWidth() - 4, getHeight() - 4, 
				null);
	}
}
