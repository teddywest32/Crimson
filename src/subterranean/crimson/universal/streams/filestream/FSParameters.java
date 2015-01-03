package subterranean.crimson.universal.streams.filestream;

import java.io.File;

import subterranean.crimson.universal.streams.Parameters;

public class FSParameters extends Parameters {

	private static final long serialVersionUID = 1L;
	private File destFile;
	private File srcFile;
	private String SHA1;
	private boolean clientIDLE = false;
	private boolean serverIDLE = false;

	private int containers;
	private int containerSize;

	public FSParameters() {
		this.setStreamName("filestream");
	}

	public boolean isClientIDLE() {
		return clientIDLE;
	}

	public void setClientIDLE(boolean clientIDLE) {
		this.clientIDLE = clientIDLE;
	}

	public boolean isServerIDLE() {
		return serverIDLE;
	}

	public void setServerIDLE(boolean serverIDLE) {
		this.serverIDLE = serverIDLE;
	}

	public File getSrcFile() {
		return srcFile;
	}

	public void setSrcFile(File srcFile) {
		this.srcFile = srcFile;
	}

	public File getDestFile() {
		return destFile;
	}

	public void setDestFile(File destFile) {
		this.destFile = destFile;
	}

	public int getContainers() {
		return containers;
	}

	public void setContainers(int containers) {
		this.containers = containers;
	}

	public int getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(int containerSize) {
		this.containerSize = containerSize;
	}

	public String getSHA1() {
		return SHA1;
	}

	public void setSHA1(String s) {
		SHA1 = s;
	}

}
