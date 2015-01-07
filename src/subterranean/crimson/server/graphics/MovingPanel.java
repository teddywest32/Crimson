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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import sl.SLAnimator;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class MovingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Color BG_COLOR = new Color(0x3B5998);
	private static final Color BORDER_COLOR = new Color(0x000000);

	private static final TweenManager tweenManager = SLAnimator.createTweenManager();
	private Runnable action;
	private boolean actionEnabled = true;
	private boolean hover = false;
	private int borderThickness = 2;

	public MovingPanel(JPanel panel) {
		setBackground(BG_COLOR);
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);

	}

	public void setAction(Runnable action) {
		this.action = action;
	}

	public void enableAction() {
		actionEnabled = true;
		if (hover)
			showBorder();
	}

	public void disableAction() {
		actionEnabled = false;
	}

	private void showBorder() {
		tweenManager.killTarget(borderThickness);
		Tween.to(MovingPanel.this, Accessor.BORDER_THICKNESS, 0.4f).target(10).start(tweenManager);
	}

	private void hideBorder() {
		tweenManager.killTarget(borderThickness);
		Tween.to(MovingPanel.this, Accessor.BORDER_THICKNESS, 0.4f).target(2).start(tweenManager);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gg = (Graphics2D) g;

		int w = getWidth();
		int h = getHeight();

		int t = borderThickness;
		gg.setColor(BORDER_COLOR);
		gg.fillRect(0, 0, t, h - 1);
		gg.fillRect(0, 0, w - 1, t);
		gg.fillRect(0, h - 1 - t, w - 1, t);
		gg.fillRect(w - 1 - t, 0, t, h - 1);
	}

	public void runAction() {
		action.run();
	}

	// -------------------------------------------------------------------------
	// Tween Accessor
	// -------------------------------------------------------------------------

	public static class Accessor extends SLAnimator.ComponentAccessor {
		public static final int BORDER_THICKNESS = 100;

		@Override
		public int getValues(Component target, int tweenType, float[] returnValues) {
			MovingPanel tp = (MovingPanel) target;

			int ret = super.getValues(target, tweenType, returnValues);
			if (ret >= 0)
				return ret;

			switch (tweenType) {
			case BORDER_THICKNESS:
				returnValues[0] = tp.borderThickness;
				return 1;
			default:
				return -1;
			}
		}

		@Override
		public void setValues(Component target, int tweenType, float[] newValues) {
			MovingPanel tp = (MovingPanel) target;

			super.setValues(target, tweenType, newValues);

			switch (tweenType) {
			case BORDER_THICKNESS:
				tp.borderThickness = Math.round(newValues[0]);
				tp.repaint();
				break;
			}
		}
	}
}
