package subterranean.crimson.universal.remote;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JLabel;

import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.streams.remotestream.RemoteStream;

public class Player extends JLabel {

	private static final long serialVersionUID = 1L;
	// selection rectangle.
	private BasicStroke bs;
	// rectangle selection outline
	private GradientPaint gp;
	public boolean isSelecting = false;
	private Rectangle oldScreenRect = RemoteVariables.diffRect;
	private float oldscreenScale = 1.0f;
	private Rectangle oldselectionRect = RemoteVariables.diffRect;
	boolean PartialScreenMode = false;
	private BufferedImage screenImage = null;

	private Rectangle screenRect = RemoteVariables.emptyRect;

	private float screenScale = 1.0f;

	private Rectangle selectionRect = RemoteVariables.emptyRect;

	// mouse coordinates for selection
	public int srcx, srcy, destx, desty;

	public RemoteStream ro;
	public Connection c;
	public EventsListener evl;

	public boolean running = false;
	public boolean viewOnly = false;
	public boolean pause = false;
	public boolean hold = false;

	public byte frames = 0;

	public Player() {
		setFocusable(true);
		InitialSelectionRect();
	};

	public Player(Connection c) {
		this.c = c;
		setFocusable(true);
		InitialSelectionRect();

		evl = new EventsListener(this);
		evl.addAdapters();
	};

	public void start() {
		if (c == null) {
			ro = new RemoteStream(1000, true);
		} else {
			ro = new RemoteStream(1000, true, this);
		}
		running = true;
	}

	public void stop() {
		ro.stop();
		running = false;
	}

	public void InitialSelectionRect() {
		// Define the stroke for drawing selection rectangle outline.
		bs = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[] { 12, 12 }, 0);

		// Define the gradient paint for coloring selection rectangle outline.
		gp = new GradientPaint(0.0f, 0.0f, Color.red, 1.0f, 1.0f, Color.white, true);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(screenImage, 0, 0, (int) (screenRect.width * screenScale), (int) (screenRect.height * screenScale), this);
		DrawSelectingRect(g);
	}

	public void DrawSelectingRect(Graphics g) {
		if (isSelecting)
			if (srcx != destx || srcy != desty) {
				// Compute upper-left and lower-right coordinates for selection
				// rectangle corners.

				int x1 = (srcx < destx) ? srcx : destx;
				int y1 = (srcy < desty) ? srcy : desty;

				int x2 = (srcx > destx) ? srcx : destx;
				int y2 = (srcy > desty) ? srcy : desty;

				// Establish selection rectangle origin.
				selectionRect.x = x1;
				selectionRect.y = y1;

				// Establish selection rectangle extents.
				selectionRect.width = (x2 - x1) + 1;
				selectionRect.height = (y2 - y1) + 1;

				// Draw selection rectangle.
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(bs);
				g2d.setPaint(gp);
				g2d.draw(selectionRect);

				PartialScreenMode = true;
			}
	}

	public void updateScreenRect() {
		screenScale = RemoteVariables.screenScale;

		if (!PartialScreenMode) {
			screenRect = RemoteVariables.screenRect;
			if (!screenRect.equals(oldScreenRect)) {
				oldScreenRect = screenRect;
				setSize(screenRect.getSize());
				setPreferredSize(screenRect.getSize());
				// if (!recorder.viewerOptions.capture.getScreenRect().equals(screenRect))
				// {
				// recorder.viewerOptions.capture.updateScreenSize(screenRect);
				// recorder.viewerOptions.setNewScreenImage(screenRect,
				// recorder.viewerOptions.getColorQuality());
				// }
			}

			if (oldscreenScale != screenScale) {
				Dimension dimension = new Dimension((int) (screenScale * screenRect.getWidth()), (int) (screenScale * screenRect.getHeight()));
				setSize(dimension);
				setPreferredSize(dimension);
				oldscreenScale = screenScale;
			}
		} else {
			if (!isSelecting)
				if (!selectionRect.equals(oldselectionRect)) {
					// screenRect = selectionRect;
					oldselectionRect = selectionRect;
					setSize(selectionRect.getSize());
					setPreferredSize(selectionRect.getSize());
					// if
					// (!recorder.viewerOptions.capture.getScreenRect().equals(selectionRect))
					// {
					// recorder.viewerOptions.capture.updateScreenSize(selectionRect);
					// recorder.viewerOptions.setNewScreenImage(selectionRect,
					// recorder.viewerOptions.getColorQuality());
					// }
				}

			if (screenScale != screenScale) {
				Dimension dimension = new Dimension((int) (screenScale * selectionRect.getWidth()), (int) (screenScale * selectionRect.getHeight()));
				setSize(dimension);
				setPreferredSize(dimension);
				oldscreenScale = screenScale;
			}
		}
	}

	public void stopSelectingMode() {
		PartialScreenMode = false;
		selectionRect = new Rectangle(0, 0, 0, 0);
		oldselectionRect = new Rectangle(-1, -1, -1, -1);
		// screenRect = Commons.emptyRect;
		RemoteVariables.screenRect = new Rectangle(0, 0, 0, 0);
		// if (recorder.config.reverseConnection)
		// recorder.viewerOptions.setChanged(true);
		// else
		// recorder.viewer.setOption(Commons.RECT_OPTION);
	}

	public void doneSelecting() {
		if (isSelecting) {
			isSelecting = false;
			oldselectionRect = new Rectangle(0, 0, 0, 0);

			if (PartialScreenMode) {
				float screenScale = 1.0f / RemoteVariables.screenScale;
				Rectangle rect = new Rectangle(selectionRect);
				rect.x = (int) (rect.x * screenScale);
				rect.y = (int) (rect.y * screenScale);
				rect.height = (int) (rect.height * screenScale);
				rect.width = (int) (rect.width * screenScale);

				RemoteVariables.screenRect = rect;
				// if (recorder.config.reverseConnection)
				// recorder.viewerOptions.setChanged(true);
				// else
				// recorder.viewer.setOption(Commons.RECT_OPTION);
			}

			srcx = destx;
			srcy = desty;

			Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(cursor);
		}
	}

	public boolean isPartialScreenMode() {
		return PartialScreenMode;
	}

	public Rectangle getSelectionRect() {
		return selectionRect;
	}

	public void startSelectingMode() {
		isSelecting = true;
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		setCursor(cursor);
	}

	public void UpdateScreen(HashMap<String, byte[]> changedBlocks) {
		updateScreenRect();

		screenImage = RemoteVariables.screenImage;

		screenImage = RemoteVariables.capture.setChangedBlocks(screenImage, changedBlocks);

		RemoteVariables.screenImage = screenImage;

		repaint();
		frames++;
	}

	public BufferedImage screenshot() {
		return screenImage;
	}

}
