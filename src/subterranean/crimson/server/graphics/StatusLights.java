package subterranean.crimson.server.graphics;

/*
 * 	Crimson Extended Administration Tool
 *  Copyright (C) 2015 Subterranean Security
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import subterranean.crimson.universal.Utilities;

public class StatusLights extends JComponent {

	private static final long serialVersionUID = 1L;
	private static final Color unlit = Color.lightGray;

	private Color TOP = unlit;
	private Color MID = unlit;
	private Color BOT = unlit;

	private String topTip;
	private String midTip;
	private String botTip;

	private Thread animator = new Thread();

	public boolean isAnimating() {
		return animator.isAlive();
	}

	public StatusLights() {

	}

	public StatusLights(String tT, String mT, String bT) {
		topTip = tT;
		midTip = mT;
		botTip = bT;

	}

	@Override
	public void paintComponent(Graphics graphics) {
		graphics.setColor(Color.black);

		int circleDiameter = this.getWidth() - 1;

		// draw outlines
		graphics.drawOval(0, 0, circleDiameter, circleDiameter);
		graphics.drawOval(0, circleDiameter, circleDiameter, circleDiameter);
		graphics.drawOval(0, circleDiameter * 2, circleDiameter, circleDiameter);

		// top
		graphics.setColor(TOP);
		graphics.fillOval(0, 0, circleDiameter, circleDiameter);

		// middle
		graphics.setColor(MID);
		graphics.fillOval(0, circleDiameter, circleDiameter, circleDiameter);

		// bottom
		graphics.setColor(BOT);
		graphics.fillOval(0, circleDiameter * 2, circleDiameter, circleDiameter);
	}

	// 1 = top 2 = mid 3 = bottom
	public void light(Color color, int pos) {
		switch (pos) {
		case 1: {
			TOP = color;
			break;
		}
		case 2: {
			MID = color;
			break;
		}
		case 3: {
			BOT = color;
			break;
		}
		}

		repaint();

	}

	public void replaceLight(Color color, int pos) {
		clear();
		light(color, pos);
	}

	public void clear() {
		TOP = unlit;
		MID = unlit;
		BOT = unlit;

		repaint();
	}

	public void animate(String type) {
		animate(type, Color.BLACK, Color.BLACK);
	}

	public void animate(String type, final Color primary, final Color secondary) {
		if (animator != null) {
			animator.interrupt();
		}

		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Runnable runnable = null;
		switch (type.toLowerCase()) {
		case "random": {
			runnable = new Runnable() {
				public void run() {
					int time = 250;
					clear();
					while (!Thread.currentThread().isInterrupted()) {

						try {
							switch (Utilities.rand(0, 2)) {
							case 0: {
								TOP = randomColor();
								repaint();
								Thread.sleep(time);
								TOP = unlit;
								repaint();

							}
							case 1: {
								MID = randomColor();
								repaint();
								Thread.sleep(time);
								MID = unlit;
								repaint();
							}
							case 2: {
								BOT = randomColor();
								repaint();
								Thread.sleep(time);
								BOT = unlit;
								repaint();
							}

							}
						} catch (InterruptedException e) {
							break;
						}

					}
					clear();
				}
			};

			break;
		}
		case "indeterminate": {
			runnable = new Runnable() {
				public void run() {
					int time = 200;
					clear();
					while (!Thread.currentThread().isInterrupted()) {

						try {
							TOP = primary;
							repaint();
							Thread.sleep(time);
							MID = primary;
							TOP = unlit;
							repaint();
							Thread.sleep(time);
							BOT = primary;
							MID = unlit;
							repaint();
							Thread.sleep(time);
							MID = primary;
							BOT = unlit;
							repaint();
							Thread.sleep(time);
							MID = unlit;
						} catch (InterruptedException e) {
							break;
						}

					}
					clear();
				}
			};
			break;
		}
		default: {
			return;
		}

		}

		animator = new Thread(runnable);
		animator.start();

	}

	public void stopAnimation() {
		animator.interrupt();
	}

	public void blink(final Color color, final int i) {
		new Thread(new Runnable() {

			public void run() {
				switch (i) {
				case 1: {
					TOP = color;
					repaint();
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					TOP = unlit;
					break;
				}
				case 2: {
					MID = color;
					repaint();
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					MID = unlit;
					break;
				}
				case 3: {
					BOT = color;
					repaint();
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					BOT = unlit;
					break;
				}
				}
				repaint();
			}
		}).start();

	}

	private static Color randomColor() {
		switch (Utilities.rand(0, 5)) {
		case 0: {
			return Color.CYAN;
		}
		case 1: {
			return Color.GREEN;
		}
		case 2: {
			return Color.YELLOW;
		}
		case 3: {
			return Color.RED;
		}
		case 4: {
			return Color.PINK;
		}
		case 5: {
			return Color.WHITE;
		}
		}
		return Color.BLACK;
	}

}
