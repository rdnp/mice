package view;

import java.awt.Image;

/**
 * An option presented in a {@link SelectorFrame}, that is visually represented
 * by a {@link SelectorButton}.
 * @author Richard Pohl
 * */
public abstract class SelectorOption {
	private Image image;
	
	/**
	 * Constructs a {@link SelectorOption}. 
	 * @param image - The image to display on the {@link SelectorButton} that
	 * represents this option.
	 * */
	public SelectorOption(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return image;
	}
	
	/** Describes what to do when the option is chosen. */
	public abstract void choose();
}
