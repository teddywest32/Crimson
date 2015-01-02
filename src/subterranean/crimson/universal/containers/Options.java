package subterranean.crimson.universal.containers;

import java.io.Serializable;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import subterranean.crimson.universal.EncType;
import subterranean.crimson.universal.Path;
import subterranean.crimson.universal.PortSpec;

public class Options implements Serializable {

	private static final long serialVersionUID = 1L;
	public EncType encryptionType;
	public SecretKeySpec key;
	public boolean melt;
	public boolean crimsonDNS;
	public String crimsonDNS_user;
	public String crimsonDNS_hash;

	public AutostartWINDOWS win_autostart_method;
	public String unix_autostart_method;

	public boolean ssl;
	
	public AddressSpec primaryAddress;
	public AddressSpec backupAddress;

	public PortSpec primaryPort;
	public PortSpec backupPort;

	public long connectPeriod = 60000;

	public boolean packed_feature_keylogger;

	public Path windows;
	public Path linux;
	public Path osx;

	// for bootstrapper
	public Date permaJarCreationDate;
	public boolean waitForIDLE;
	public long executionDelay;
	public boolean handleErrors;
	public InstallationDetails details;// gets populated by bootstrapper

}
