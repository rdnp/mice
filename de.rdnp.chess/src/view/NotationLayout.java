package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class NotationLayout implements LayoutManager {
	
	private static final int LEFT_COLUMN_X 		= 2;
	private static final int RIGHT_COLUMN_X 	= 102;

	@Override
	public void addLayoutComponent(String name, Component comp) {}

	@Override
	public void removeLayoutComponent(Component comp) {}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}

	@Override
	public void layoutContainer(Container parent) {
		for (int i = 0; i < parent.getComponentCount(); i++)
		{
			parent.getComponent(i).setBounds(i % 2 == 0?
					LEFT_COLUMN_X: RIGHT_COLUMN_X, 
					(i % 2 == 0? i + 1: i) 
						* NotationElement.NOTATION_ELEMENT_HEIGHT / 2, 
					parent.getComponent(i).getPreferredSize().width,
					parent.getComponent(i).getPreferredSize().height);
		}

	}

}
