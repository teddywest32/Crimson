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
package subterranean.crimson.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import subterranean.crimson.server.containers.ConnectionProfile;
import subterranean.crimson.universal.IngressBuffer;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.ObjectTransfer;
import subterranean.crimson.universal.Reporter;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.exceptions.InvalidObjectException;

public class AppletConnection extends Thread {

	private PrintWriter out;
	private BufferedReader in;
	public Socket sock;
	public IngressBuffer i = new IngressBuffer();

	public boolean encrypted;
	public SecretKeySpec key;

	public int clientId;
	public ConnectionProfile profile;

	public AppletConnection(Socket s, SecretKeySpec k) {

		key = k;
		sock = s;
		try {
			out = new PrintWriter(sock.getOutputStream(), true);

			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			Logger.add("Failed to open stream on socket");
			Reporter.report(e);
		}
		if (in == null || out == null) {
			// bad socket
			Logger.add("Bad Socket");
		}
	}

	public void run() {

		while (!Thread.interrupted()) {
			String ingress = null;
			try {
				ingress = in.readLine();
			} catch (IOException e) {
				// Lost connection
				break;
			}
			if (ingress != null) {
				// check for encryption
				Object res = null;
				try {
					res = ObjectTransfer.fromString(ingress);
				} catch (InvalidObjectException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (res instanceof byte[]) {

					// decrypt
					try {
						Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
						aes.init(Cipher.DECRYPT_MODE, key);
						String decrypted = new String(aes.doFinal((byte[]) res));
						try {
							res = ObjectTransfer.fromString(decrypted);
						} catch (InvalidObjectException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Reporter.report(e);
					}

				}

				Message r = (Message) res;

				// filter out commands that were sent without prompt (keylogger,
				// information, packet data)
				// if (r.getName() == MN.CLIENT_disconnectionNotice) {
				// break;
				// } else if (r.getName() == MN.APPLET_login) {
				// Object[] o = { true };
				// Message mreq = new Message(r.getStreamID(), MN.APPLET_login, o);
				//
				// send(mreq);
				//
				// } else {
				//
				// i.add(r);
				// }
			}
		}
		// lost applet connection

	}

	public void send(Message c) {

		byte[] ciphertext = null;
		String m = ObjectTransfer.toString(c, false);
		try {
			Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
			aes.init(Cipher.ENCRYPT_MODE, key);
			ciphertext = aes.doFinal(m.getBytes());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			Logger.add("Error with encrypting");
			e.printStackTrace();
			Reporter.report(e);
		}

		out.println(ObjectTransfer.toString(ciphertext, false));

	}

}
