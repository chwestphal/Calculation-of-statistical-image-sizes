// BV Ue04 WS2016/17 Vorgabe Hilfsklasse HistoView
//
// Copyright (C) 2014 by Klaus Jung

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class HistoView extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int graySteps = 256;
	private static final int height = 200;
	private static final int width = graySteps;
	
	private int[] histogram = null;
	
	public HistoView() {
		super();
		TitledBorder titBorder = BorderFactory.createTitledBorder("Histogram");
		titBorder.setTitleColor(Color.GRAY);
		setBorder(titBorder);
		add(new HistoScreen());
	}
	
	public boolean setHistogram(int[] histogram) {
		if(histogram == null || histogram.length != graySteps) {
			return false;
		}
		this.histogram = histogram;
		update();
		return true;
	}
	
	public boolean update() {
		if(histogram == null) {
			return false;
		}
		invalidate();
		repaint();
		return true;
	}
	
	class HistoScreen extends JComponent {

		private static final long serialVersionUID = 1L;
		
		public void paintComponent(Graphics g) {
			int highestValue = Integer.MIN_VALUE;
			
			for(int x = 0; x<histogram.length; x++) {
				if(histogram[x] > highestValue) {
					highestValue = histogram[x];
				}
			}
			
			double ratio = height/(highestValue*1.0);
			
			g.clearRect(0, 0, width, height);

			// TODO: draw histogram instead of diagonal lines
			g.setColor(Color.black);
			
			for(int i = 0; i<histogram.length; i++) {
				
				int relHeight = (int) Math.round(ratio*histogram[i]);
				g.drawLine(i, height, i, (height-relHeight));
			}
		}
		
		public Dimension getPreferredSize() {
			return new Dimension(width, height);
		}
	}
	


}
