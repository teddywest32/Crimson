package subterranean.crimson.server.network;

import java.io.Serializable;

import javax.crypto.spec.SecretKeySpec;

import subterranean.crimson.universal.CompressionLevel;
import subterranean.crimson.universal.EncType;

public class ListenerContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	public int portNumber = 0;
	public String name;

	public EncType encryption;
	public SecretKeySpec key;

	public boolean upnp;
	public boolean ssl;
	public CompressionLevel compression;

}
