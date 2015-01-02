/* 
 *              weupnp - Trivial upnp java library 
 *
 * Copyright (C) 2008 Alessandro Bahgat Shehata, Daniele Castagna
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * Alessandro Bahgat Shehata - ale dot bahgat at gmail dot com
 * Daniele Castagna - daniele dot castagna at gmail dot com
 * 
 */

/*
 * refer to miniupnpc-1.0-RC8
 */
package subterranean.crimson.universal.upnp;

import java.net.InetAddress;
import java.util.Map;

import subterranean.crimson.universal.Logger;

public class PortMapper {

	private static short WAIT_TIME = 1;
	private static boolean LIST_ALL_MAPPINGS = false;

	public static boolean test(int port) throws Exception {

		addLogLine("Starting weupnp");

		GatewayDiscover gatewayDiscover = new GatewayDiscover();
		addLogLine("Looking for Gateway Devices...");

		Map<InetAddress, GatewayDevice> gateways = gatewayDiscover.discover();

		if (gateways.isEmpty()) {
			addLogLine("No gateways found");
			addLogLine("Stopping weupnp");
			return false;
		}
		addLogLine(gateways.size() + " gateway(s) found\n");

		int counter = 0;
		for (GatewayDevice gw : gateways.values()) {
			counter++;
			addLogLine("Listing gateway details of device #" + counter + "\n\tFriendly name: " + gw.getFriendlyName() + "\n\tPresentation URL: " + gw.getPresentationURL() + "\n\tModel name: " + gw.getModelName() + "\n\tModel number: " + gw.getModelNumber() + "\n\tLocal interface address: " + gw.getLocalAddress().getHostAddress() + "\n");
		}

		// choose the first active gateway for the tests
		GatewayDevice activeGW = gatewayDiscover.getValidGateway();

		if (null != activeGW) {
			addLogLine("Using gateway: " + activeGW.getFriendlyName());
		} else {
			addLogLine("No active gateway device found");
			addLogLine("Stopping weupnp");
			return false;
		}

		// testing PortMappingNumberOfEntries
		Integer portMapCount = activeGW.getPortMappingNumberOfEntries();
		addLogLine("GetPortMappingNumberOfEntries: " + (portMapCount != null ? portMapCount.toString() : "(unsupported)"));

		// testing getGenericPortMappingEntry
		PortMappingEntry portMapping = new PortMappingEntry();
		if (LIST_ALL_MAPPINGS) {
			int pmCount = 0;
			do {
				if (activeGW.getGenericPortMappingEntry(pmCount, portMapping))
					addLogLine("Portmapping #" + pmCount + " successfully retrieved (" + portMapping.getPortMappingDescription() + ":" + portMapping.getExternalPort() + ")");
				else {
					addLogLine("Portmapping #" + pmCount + " retrieval failed");
					break;
				}
				pmCount++;
			} while (portMapping != null);
		} else {
			if (activeGW.getGenericPortMappingEntry(0, portMapping))
				addLogLine("Portmapping #0 successfully retrieved (" + portMapping.getPortMappingDescription() + ":" + portMapping.getExternalPort() + ")");
			else
				addLogLine("Portmapping #0 retrival failed");
		}

		InetAddress localAddress = activeGW.getLocalAddress();
		addLogLine("Using local address: " + localAddress.getHostAddress());
		String externalIPAddress = activeGW.getExternalIPAddress();
		addLogLine("External address: " + externalIPAddress);

		addLogLine("Querying device to see if a port mapping already exists for port " + port);

		if (activeGW.getSpecificPortMappingEntry(port, "TCP", portMapping)) {
			addLogLine("Port " + port + " is already mapped. Aborting test.");
			return false;
		} else {
			addLogLine("Mapping free. Sending port mapping request for port " + port);

			// test static lease duration mapping
			if (activeGW.addPortMapping(port, port, localAddress.getHostAddress(), "TCP", "test")) {
				addLogLine("Mapping SUCCESSFUL. Waiting " + WAIT_TIME + " seconds before removing mapping...");
				Thread.sleep(1000 * WAIT_TIME);

				if (activeGW.deletePortMapping(port, "TCP")) {
					addLogLine("Port mapping removed, test SUCCESSFUL");
					return true;
				} else {
					addLogLine("Port mapping removal FAILED");
					return false;
				}
			}
		}
		return false;
	}

	private static void addLogLine(String line) {
		Logger.add("[UPNP] " + line);

	}

	public static boolean forward(int port) throws Exception {

		addLogLine("Starting weupnp");

		GatewayDiscover gatewayDiscover = new GatewayDiscover();
		addLogLine("Looking for Gateway Devices...");

		Map<InetAddress, GatewayDevice> gateways = gatewayDiscover.discover();

		if (gateways.isEmpty()) {
			addLogLine("No gateways found");
			addLogLine("Stopping weupnp");
			return false;
		}
		addLogLine(gateways.size() + " gateway(s) found\n");

		int counter = 0;
		for (GatewayDevice gw : gateways.values()) {
			counter++;
			addLogLine("Listing gateway details of device #" + counter + "\n\tFriendly name: " + gw.getFriendlyName() + "\n\tPresentation URL: " + gw.getPresentationURL() + "\n\tModel name: " + gw.getModelName() + "\n\tModel number: " + gw.getModelNumber() + "\n\tLocal interface address: " + gw.getLocalAddress().getHostAddress() + "\n");
		}

		// choose the first active gateway for the tests
		GatewayDevice activeGW = gatewayDiscover.getValidGateway();

		if (null != activeGW) {
			addLogLine("Using gateway: " + activeGW.getFriendlyName());
		} else {
			addLogLine("No active gateway device found");
			addLogLine("Stopping weupnp");
			return false;
		}

		// testing PortMappingNumberOfEntries
		Integer portMapCount = activeGW.getPortMappingNumberOfEntries();
		addLogLine("GetPortMappingNumberOfEntries: " + (portMapCount != null ? portMapCount.toString() : "(unsupported)"));

		// testing getGenericPortMappingEntry
		PortMappingEntry portMapping = new PortMappingEntry();
		if (LIST_ALL_MAPPINGS) {
			int pmCount = 0;
			do {
				if (activeGW.getGenericPortMappingEntry(pmCount, portMapping))
					addLogLine("Portmapping #" + pmCount + " successfully retrieved (" + portMapping.getPortMappingDescription() + ":" + portMapping.getExternalPort() + ")");
				else {
					addLogLine("Portmapping #" + pmCount + " retrieval failed");
					break;
				}
				pmCount++;
			} while (portMapping != null);
		} else {
			if (activeGW.getGenericPortMappingEntry(0, portMapping))
				addLogLine("Portmapping #0 successfully retrieved (" + portMapping.getPortMappingDescription() + ":" + portMapping.getExternalPort() + ")");
			else
				addLogLine("Portmapping #0 retrival failed");
		}

		InetAddress localAddress = activeGW.getLocalAddress();
		addLogLine("Using local address: " + localAddress.getHostAddress());
		String externalIPAddress = activeGW.getExternalIPAddress();
		addLogLine("External address: " + externalIPAddress);

		addLogLine("Querying device to see if a port mapping already exists for port " + port);

		if (activeGW.getSpecificPortMappingEntry(port, "TCP", portMapping)) {
			addLogLine("Port " + port + " is already mapped. Aborting test.");
			return false;
		} else {
			addLogLine("Mapping free. Sending port mapping request for port " + port);

			// test static lease duration mapping
			if (activeGW.addPortMapping(port, port, localAddress.getHostAddress(), "TCP", "test")) {
				addLogLine("Mapping SUCCESSFUL");
				return true;
			}
		}
		return false;
	}

	public static boolean unforward(int port) throws Exception {

		addLogLine("Starting weupnp");

		GatewayDiscover gatewayDiscover = new GatewayDiscover();
		addLogLine("Looking for Gateway Devices...");

		Map<InetAddress, GatewayDevice> gateways = gatewayDiscover.discover();

		if (gateways.isEmpty()) {
			addLogLine("No gateways found");
			addLogLine("Stopping weupnp");
			return false;
		}
		addLogLine(gateways.size() + " gateway(s) found\n");

		int counter = 0;
		for (GatewayDevice gw : gateways.values()) {
			counter++;
			addLogLine("Listing gateway details of device #" + counter + "\n\tFriendly name: " + gw.getFriendlyName() + "\n\tPresentation URL: " + gw.getPresentationURL() + "\n\tModel name: " + gw.getModelName() + "\n\tModel number: " + gw.getModelNumber() + "\n\tLocal interface address: " + gw.getLocalAddress().getHostAddress() + "\n");
		}

		// choose the first active gateway for the tests
		GatewayDevice activeGW = gatewayDiscover.getValidGateway();

		if (null != activeGW) {
			addLogLine("Using gateway: " + activeGW.getFriendlyName());
		} else {
			addLogLine("No active gateway device found");
			addLogLine("Stopping weupnp");
			return false;
		}

		// testing PortMappingNumberOfEntries
		Integer portMapCount = activeGW.getPortMappingNumberOfEntries();
		addLogLine("GetPortMappingNumberOfEntries: " + (portMapCount != null ? portMapCount.toString() : "(unsupported)"));

		// testing getGenericPortMappingEntry
		PortMappingEntry portMapping = new PortMappingEntry();
		if (LIST_ALL_MAPPINGS) {
			int pmCount = 0;
			do {
				if (activeGW.getGenericPortMappingEntry(pmCount, portMapping))
					addLogLine("Portmapping #" + pmCount + " successfully retrieved (" + portMapping.getPortMappingDescription() + ":" + portMapping.getExternalPort() + ")");
				else {
					addLogLine("Portmapping #" + pmCount + " retrieval failed");
					break;
				}
				pmCount++;
			} while (portMapping != null);
		} else {
			if (activeGW.getGenericPortMappingEntry(0, portMapping))
				addLogLine("Portmapping #0 successfully retrieved (" + portMapping.getPortMappingDescription() + ":" + portMapping.getExternalPort() + ")");
			else
				addLogLine("Portmapping #0 retrival failed");
		}

		InetAddress localAddress = activeGW.getLocalAddress();
		addLogLine("Using local address: " + localAddress.getHostAddress());
		String externalIPAddress = activeGW.getExternalIPAddress();
		addLogLine("External address: " + externalIPAddress);

		addLogLine("Querying device to see if a port mapping already exists for port " + port);

		if (activeGW.getSpecificPortMappingEntry(port, "TCP", portMapping)) {
			if (activeGW.deletePortMapping(port, "TCP")) {
				addLogLine("Port mapping removed");
				return true;
			} else {
				addLogLine("Port mapping removal FAILED");
				return false;
			}
		} else {
			addLogLine("Port isnt mapped");

		}
		return false;
	}

}
