package subterranean.crimson.server.network;

import io.netty.handler.ssl.SslContext;

import javax.crypto.spec.SecretKeySpec;

import subterranean.crimson.universal.EncType;
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
