package view;


import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * A Selector that opens a new {@link SelectorFrame} that provides the user
 * several options to select from.
 * @author Richard Pohl
 * */
public class Selector extends Button implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7164574855131358998L;

	private MenuPanel container;
	private SelectorFrame selectorFrame;
	private Image image;
	private List<SelectorOption> options;
	
	/**Constructs a new {@link Selector}.
	 * @param options - The options from which the user can choose one.
	 * The first option in the list will be considered as default option.*/
	public Selector(MenuPanel container, 
			List<SelectorOption> options) {
		this.container = container;
		this.options = options;
		addActionListener(this);
		createSelectorFrame();
		container.add(this);
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public List<SelectorOption> getOptions() {
		return options;
	}
	
	public void setOptions(List<SelectorOption> options) {
		this.options = options;
		createSelectorFrame();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		selectorFrame.pack();
		selectorFrame.setLocation(container.getChessFrame().getX() + 64, 
				container.getChessFrame().getY() + getY() + 24);
		selectorFrame.setVisible(true);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(48, 48);
	}
	
	@Override
	public void paint(Graphics g) {
		if (image != null)
			g.drawImage(image, 2, 2, image.getWidth(null), 
					image.getHeight(null), null);
	}
	
	/** Chooses the default option and the default image. */
	public void reset() {
		image = options.get(0).getImage();
		options.get(0).choose();
	}
	
	/** Creates the selector frame for this selector. */
	private void createSelectorFrame()
	{
		selectorFrame = new SelectorFrame();
		for (SelectorOption option: options)
		{
			// set image to first options image (start state) and choose that
			// option initially
			if (image == null) 
			{
				image = option.getImage();
				option.choose();
			}
			// add selector button for each option
			new SelectorButton(this, selectorFrame, option);
		}
	}
}
