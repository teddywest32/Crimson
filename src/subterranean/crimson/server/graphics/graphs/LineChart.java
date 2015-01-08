/*******************************************************************************
 *              Crimson Extended Administration Tool (CrimsonXAT)              *
 *                   Copyright (C) 2015 Subterranean Security                  *
 *                                                                             *
 *     This program is free software: you can redistribute it and/or modify    *
 *     it under the terms of the GNU General Public License as published by    *
 *      the Free Software Foundation, either version 3 of the License, or      *
 *                      (at your option) any later version.                    *
 *                                                                             *
 *       This program is distributed in the hope that it will be useful,       *
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of       *
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
 *                 GNU General Public License for more details.                *
 *                                                                             *
 *      You should have received a copy of the GNU General Public License      *
 *      along with this program.  If not, see http://www.gnu.org/licenses      *
 *******************************************************************************/
package subterranean.crimson.server.graphics.graphs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public abstract class LineChart extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	protected TimeSeries s1 = new TimeSeries("Connections");
	protected TimeSeries s2 = new TimeSeries("Connections");

	public LineChart() {

		super(new BorderLayout());

		final TimeSeriesCollection dataset = new TimeSeriesCollection(this.s1);
		final JFreeChart chart = createChart(dataset);

		Timer timer = new Timer(900, this);
		timer.setInitialDelay(0);

		// Sets background color of chart
		chart.setBackgroundPaint(Color.LIGHT_GRAY);

		// Created Chartpanel for chart area
		final ChartPanel chartPanel = new ChartPanel(chart);

		// Added chartpanel to main panel
		add(chartPanel);

		timer.start();

	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart("", "", "", dataset, false, false, false);

		XYPlot plot = result.getXYPlot();
		plot.setDataset(1, new TimeSeriesCollection(s2));

		plot.setBackgroundPaint(new Color(0x000000));
		plot.setDomainGridlinesVisible(false);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinesVisible(false);
		plot.setRangeGridlinePaint(Color.lightGray);

		ValueAxis xaxis = plot.getDomainAxis();
		xaxis.setAutoRange(true);

		xaxis.setFixedAutoRange(160000.0); // 160 seconds
		xaxis.setVerticalTickLabels(false);

		ValueAxis yaxis = plot.getRangeAxis();

		yaxis.setAutoRange(true);
		yaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		return result;
	}

	public abstract void actionPerformed(final ActionEvent e);

}
