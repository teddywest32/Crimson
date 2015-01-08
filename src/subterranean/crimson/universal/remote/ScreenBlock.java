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

public class ScreenBlock {

	public static final int COMPARE_LENGTH = 1;
	public static final int COMPARE_STRING = 2;
	public static final int COMPARE_BYTES = 3;

	private byte[] data = "".getBytes();

	private String name = "";

	private int differences = 0;

	ScreenBlock(String name) {
		this.name = name;
	}

	public ScreenBlock(String name, byte[] data) {
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public int getDifferences() {
		return differences;
	}

	public byte[] getData() {
		return data;
	}

	public boolean isModified(byte[] newdata, int method) {
		/* Method 1 */
		if (method == COMPARE_LENGTH) {
			if (newdata.length != this.data.length) {
				this.data = newdata;
				return true;
			}
		}
		/* Method 2 */
		if (method == COMPARE_STRING) {
			if (new String(newdata).equals(new String(this.data)) == false) {
				this.data = newdata;
				return true;
			}
		}
		/* Method 3 */
		if (method == COMPARE_BYTES) {
			differences = 0;
			for (int i = 0; i < newdata.length; i++) {
				if (newdata[i] != this.data[i]) {
					differences++;
				}
			}
			return (differences > 0);
		}
		return false;
	}
}
