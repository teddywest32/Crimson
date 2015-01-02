package subterranean.crimson.universal.streams.filestream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import subterranean.crimson.universal.containers.TransferContainer;

public class BufferWatcher extends Thread {

	private volatile ArrayList<TransferContainer> buffer = new ArrayList<TransferContainer>();
	private FileStream fs;
	private BufferedInputStream is;

	public BufferWatcher(FileStream fileStream) {
		fs = fileStream;
		try {
			is = new BufferedInputStream(new FileInputStream(fs.getFSP().getSrcFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start();
	}

	public void run() {
		while (!Thread.interrupted()) {
			if (buffer.size() < 10) {
				TransferContainer tc = new TransferContainer();
				tc.seq = (int) fs.getFSP().getProgress();
				tc.payload = new byte[fs.getFSP().getContainerSize()];
				try {
					is.read(tc.payload, fs.getFSP().getContainerSize() * (int) fs.getFSP().getProgress(), tc.payload.length);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				buffer.add(tc);
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public TransferContainer remove() {
		return buffer.get(0);
	}

}
