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

import io.netty.handler.ssl.SslContext;

import javax.crypto.spec.SecretKeySpec;

import subterranean.crimson.universal.enumerations.EncType;
import subterranean.crimson.universal.upnp.PortMapper;

public abstract class Listener extends Thread {

	protected int PORT;
	protected String name;
	protected boolean SSL;
	protected EncType encryption;
	protected SslContext sslCtx = null;
	protected SecretKeySpec key;
	protected boolean UPNP;

	public int getPORT() {
		return PORT;
	}

	public String getListenerName() {
		return name;
	}

	public boolean usingSSL() {
		return SSL;
	}

	public EncType getEncType() {
		return encryption;
	}

	public SecretKeySpec getSecretKeySpec() {
		return key;
	}

	public void forward() {
		try {
			PortMapper.forward(PORT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void unforward() {
		try {
			PortMapper.unforward(PORT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
