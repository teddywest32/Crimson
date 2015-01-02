package subterranean.crimson.universal.remote;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public enum RemoteVariables {

	;

	public static final Rectangle emptyRect = new Rectangle(0, 0, 0, 0);
	public static final Rectangle diffRect = new Rectangle(-1, -1, -1, -1);

	public static float imageQuality = -1.0f;
	public static float screenScale = 1.0f;
	public static int blocks = 20;
	public static int colorQuality = BufferedImage.TYPE_INT_RGB;
	public static BufferedImage screenImage = null;
	public static ScreenCapture capture = new ScreenCapture(imageQuality, blocks, blocks);

	public static Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

	public static Hashtable properties = new Hashtable();

	public static boolean screenCompression = true;

	public static void setNewScreenImage(Rectangle rectangle, int colorQuality) {
		screenImage = (new BufferedImage(rectangle.width, rectangle.height, colorQuality));
	}

	public static void setNewScreenRect() {
		screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	}

	static {
		setNewScreenImage(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()), colorQuality);
	}
}
