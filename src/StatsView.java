
// BV Ue04 WS2016/17 Vorgabe Hilfsklasse StatsView
//
// Copyright (C) 2014 by Klaus Jung

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class StatsView extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String[] names = { "Minimum:", "Maximum:", "Mean:", "Median:", "Variance:", "Entropy:" }; // TODO:
																													// enter
																													// proper
																													// names
	private static final int rows = names.length;
	private static final int border = 2;
	private static final int columns = 2;
	private static final int graySteps = 256;

	private JLabel[] infoLabel = new JLabel[rows];
	private JLabel[] valueLabel = new JLabel[rows];

	private int[] histogram = null;
	private int[] origPix = null;

	public StatsView() {
		super(new GridLayout(rows, columns, border, border));
		TitledBorder titBorder = BorderFactory.createTitledBorder("Statistics");
		titBorder.setTitleColor(Color.GRAY);
		setBorder(titBorder);
		for (int i = 0; i < rows; i++) {
			infoLabel[i] = new JLabel(names[i]);
			valueLabel[i] = new JLabel("-----");
			add(infoLabel[i]);
			add(valueLabel[i]);
		}
	}

	private void setValue(int column, int value) {
		valueLabel[column].setText("" + value);
	}

	private void setValue(int column, double value) {
		valueLabel[column].setText(String.format(Locale.US, "%.2f", value));
	}

	public boolean setHistogram(int[] histogram) {
		if (histogram == null || histogram.length != graySteps) {
			return false;
		}
		this.histogram = histogram;
		update();
		return true;
	}

	public boolean setOrig(int[] origPix) {
		if (origPix == null) {
			return false;
		}
		this.origPix = origPix;
		update();
		return true;
	}

	public boolean update() {
		if (histogram == null) {
			return false;
		}

		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		double mean = 0;
		int allValues = 0;
		double allPix = origPix.length;
		double middle = allPix / 2;
		double median = 0;
		double variance = 0;
		double entropy = 0;

		for (int i = 0; i < histogram.length; i++) {

			if (histogram[i] != 0 && i < min) {
				min = i;
			}

			if (histogram[i] != 0 && i > max) {
				max = i;
			}

			allValues += i * histogram[i];
						
		}
		

		mean = allValues / allPix;
		
		for (int i = 0; i < histogram.length; i++) {
			variance +=( Math.pow((i-mean), 2) * histogram[i])/allPix;
		}
		
		//variance = variance / allPix;

		int iterationValue = 0;
		for (int i = 0; i < histogram.length; i++) {
			iterationValue += histogram[i];
		
			if (iterationValue >= middle) {
				median = i;
				break;
			}
		}
		
		double p = 0;
		for (int i = 0; i < histogram.length; i++) {
			p = histogram[i] / allPix;
			if(p > 0) {
				entropy += - ( p * Math.log(p)/Math.log(2));

			}
		}

		// TODO: calculate and display statistic values
		setValue(0, min);
		setValue(1, max);
		setValue(2, mean);
		setValue(3, median);
		setValue(4, variance);
		setValue(5, entropy);

		return true;
	}

}
