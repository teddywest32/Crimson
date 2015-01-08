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
package subterranean.crimson.universal;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

import subterranean.crimson.server.graphics.frames.About;

public enum FileResolver {

	;

	public static String getExtension(String filename) {

		int i = filename.lastIndexOf('.');
		if (i > 0) {
			return filename.substring(i + 1);
		} else {
			return "";
		}
	}

	public static ImageIcon getIcon(String extension, int size) {
		if (expandExtension(extension).equals("File")) {
			return new ImageIcon(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/subterranean/crimson/server/graphics/icons/fm/" + size + "/unknown.png")));
		}

		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/subterranean/crimson/server/graphics/icons/fm/" + size + "/" + extension + ".png")));

	}
	
	public static ImageIcon getDirIcon(int size){
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/subterranean/crimson/server/graphics/icons/fm/" + size + "/directory.png")));
	}

	public static String expandExtension(String extension) {
		switch (extension) {
		case "doc": {
			return "Microsoft Word Document";
		}
		case "docx": {
			return "Microsoft Word Open XML Document";
		}
		case "odt": {
			return "OpenDocument Text Document";
		}
		case "pages": {
			return "Pages Document";
		}
		case "rtf": {
			return "Rich Text Format File";
		}
		case "txt": {
			return "Plain Text File";
		}
		case "dat": {
			return "Data File";
		}
		case "pps": {
			return "PowerPoint Slide Show";
		}
		case "ppt": {
			return "PowerPoint Presentation";
		}
		case "pptx": {
			return "PowerPoint Open XML Presentation";
		}
		case "tar": {
			return "Consolidated Unix File Archive";
		}
		case "xml": {
			return "XML File";
		}
		case "m4a": {
			return "MPEG-4 Audio File";
		}
		case "mp3": {
			return "MP3 Audio File";
		}
		case "ogg": {
			return "Ogg Vorbis";
		}
		case "wav": {
			return "WAVE Audio File";
		}
		case "wma": {
			return "Windows Media Audio File";
		}
		case "3gp": {
			return "3GPP Multimedia File";
		}
		case "avi": {
			return "Audio Video Interleave File";
		}
		case "mp4": {
			return "MPEG-4 Video File";
		}
		case "flv": {
			return "Flash Video File";
		}
		case "wmv": {
			return "Windows Media Video File";
		}
		case "bmp": {
			return "Bitmap Image File";
		}
		case "png": {
			return "Portable Network Graphic";
		}
		case "jpg": {
			return "JPEG Image";
		}
		case "jpeg": {
			return "JPEG Image";
		}
		case "apk": {
			return "Android Package File";
		}
		case "exe": {
			return "Windows Executable File";
		}
		case "jar": {
			return "Java Archive";
		}
		case "app": {
			return "Mac OS X Application";
		}
		case "html": {
			return "Hypertext Markup Language File";
		}
		case "htm": {
			return "Hypertext Markup Language File";
		}
		case "ico": {
			return "Icon File";
		}
		case "sys": {
			return "Windows System File";
		}
		case "7z": {
			return "7-Zip Compressed File";
		}
		case "rar": {
			return "WinRAR Compressed Archive";
		}
		case "zip": {
			return "Zipped File";
		}


		}
		return "File";
	}

}
