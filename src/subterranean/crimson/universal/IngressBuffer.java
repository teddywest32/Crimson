package subterranean.crimson.universal;

import java.util.ArrayList;

import subterranean.crimson.universal.containers.Message;

public class IngressBuffer {
	public ArrayList<Message> ingress;

	public IngressBuffer() {
		ingress = new ArrayList<Message>();
	}

	public void add(Message r) {
		ingress.add(r);
	}

	public Message getId(int id) {
		int slept = 0;
		while (true) {

			if (ingress.size() == 0) {
				// wait
			} else {
				// find one with id
				for (Message c : ingress) {
					if (c.getStreamID() == id) {
						// found right one
						ingress.remove(c);
						return c;
					}
				}
				// wait
			}
			if (slept > 5) {
				Logger.add("No response received for id: " + id + " Ingress Buffer size: " + ingress.size());

				return null;
			}
			// sleep
			slept++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Logger.add("Terminated while waiting for response from client");
			}
		}
	}

}
