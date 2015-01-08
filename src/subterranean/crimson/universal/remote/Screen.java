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
package subterranean.crimson.universal.remote;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Vector;

public class Screen {

	private HashMap<String, ScreenBlock> screenBlocks = new HashMap<String, ScreenBlock>();

	private double screenSizeWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double screenSizeHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	private int rowScreenBlocks = 0;
	private int columnScreenBlocks = 0;

	private double screenBlockHeight = 0;
	private double screenBlockWidth = 0;

	private final String separator = "-";

	private Vector<String> changedScreenBlocks = new Vector<String>();

	public Screen(int rowScreenBlocks, int columnScreenBlocks) {
		this.rowScreenBlocks = rowScreenBlocks;
		this.columnScreenBlocks = columnScreenBlocks;

		this.screenBlockHeight = getScreenSizeHeight() / rowScreenBlocks;
		this.screenBlockWidth = getScreenSizeWidth() / columnScreenBlocks;
	}

	public void updateScreenSize(Rectangle screenRect) {
		screenSizeWidth = screenRect.width;
		screenSizeHeight = screenRect.height;
		screenBlockHeight = screenSizeHeight / rowScreenBlocks;
		screenBlockWidth = screenSizeWidth / columnScreenBlocks;
	}

	public String getScreenBlockName(int y, int x) {
		return ("" + y + separator + x);
	}

	public ScreenBlock getScreenBlockImage(String name) {
		return (ScreenBlock) screenBlocks.get(name);
	}

	public void addScreenBlock(ScreenBlock block) {
		screenBlocks.put(block.getName(), block);
	}

	public int getBlockRow(String name) {
		return Integer.parseInt(name.split(separator)[0]);
	}

	public int getBlockColumn(String name) {
		return Integer.parseInt(name.split(separator)[1]);
	}

	public Vector<String> getChangedScreenBlocks() {
		return changedScreenBlocks;
	}

	public HashMap getScreenBlocks() {
		return screenBlocks;
	}

	public int getRowScreenBlocks() {
		return rowScreenBlocks;
	}

	public int getColumnScreenBlocks() {
		return columnScreenBlocks;
	}

	public double getScreenBlockHeight() {
		return screenBlockHeight;
	}

	public double getScreenBlockWidth() {
		return screenBlockWidth;
	}

	public double getScreenSizeHeight() {
		return screenSizeHeight;
	}

	public double getScreenSizeWidth() {
		return screenSizeWidth;
	}
}
