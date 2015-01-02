package subterranean.crimson.universal;

public enum BMN {
	;

	// MODULE_command
	public static final byte KEYLOGGER_start = -128;
	public static final byte KEYLOGGER_stop = -127;
	public static final byte KEYLOGGER_getLog = -126;
	public static final byte CLIENT_stageQuery = -125;
	public static final byte CLIENT_sendStage = -124;
	public static final byte CLIENT_getBasicInfo = -123;
	public static final byte CLIENT_relocate = -122;
	public static final byte CLIENT_restart = -121;
	public static final byte CLIENT_uninstall = -120;
	public static final byte CLIENT_shutdown = -119;
	public static final byte CLIENT_update = -118;
	public static final byte CLIENT_assignID = -117;
	public static final byte CLIENT_getClientLog = -116;
	public static final byte INCOMING_keyloggerData = -115;
	public static final byte INCOMING_packetData = -114;
	public static final byte INCOMING_processStatus = -112;
	public static final byte INCOMING_activityStatus = -111;
	public static final byte INCOMING_systemMessage = -110;
	public static final byte PASSWORD_getLocalHashes = -109;
	public static final byte POWER_shutdown = -108;
	public static final byte POWER_restart = -107;
	public static final byte POWER_standby = -106;
	public static final byte POWER_hibernate = -105;
	public static final byte USER_add = -104;
	public static final byte USER_delete = -103;
	public static final byte USER_elevate = -102;
	public static final byte USER_delevate = -101;
	public static final byte USER_getInfo = -100;
	public static final byte USER_logoff = -99;
	public static final byte SHELL_initialize = -98;
	public static final byte SHELL_kill = -97;
	public static final byte SHELL_execute = -96;
	public static final byte SIGAR_gather = -95;
	public static final byte FILEMANAGER_up = -94;
	public static final byte FILEMANAGER_down = -93;
	public static final byte FILEMANAGER_pwd = -92;
	public static final byte FILEMANAGER_list = -91;
	public static final byte FILEMANAGER_delete = -90;
	public static final byte FILEMANAGER_getFileInfo = -89;
	public static final byte CHAT_clear = -88;
	public static final byte CHAT_message = -87;
	public static final byte CHAT_open = -86;
	public static final byte CHAT_close = -85;
	public static final byte CLIPBOARD_inject = -84;
	public static final byte CLIPBOARD_retrieve = -83;
	public static final byte PROCESSMANAGER_list = -82;
	public static final byte PROCESSMANAGER_kill = -81;
	public static final byte STREAM_data = -82;
	public static final byte STREAMCONTROL_start = -80;
	public static final byte STREAMCONTROL_stop = -79;
	public static final byte STREAMCONTROL_pause = -78;
	public static final byte STREAMCONTROL_resume = -77;
	public static final byte SCREENMANAGER_screenshot = -76;
	public static final byte SCREENMANAGER_getScreenList = -75;
	public static final byte PLUGIN_message = -74;
	public static final byte PLUGIN_install = -73;
	public static final byte PROXY_message = -72;
	public static final byte DISCONNECTION = -71;
	public static final byte REGISTRY_list = -70;
	public static final byte REGISTRY_add = -69;
	public static final byte REGISTRY_delete = -68;
	public static final byte REGISTRY_edit = -67;
	public static final byte REGISTRY_pwd = -66;
	

}