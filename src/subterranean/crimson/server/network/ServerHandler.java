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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;

import subterranean.crimson.server.graphics.Constants;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.containers.Message;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	private ChannelHandlerContext context;
	public Connection parent;

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		// channel is now active
		Logger.add("Channel is now active");
		context = ctx;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (msg instanceof Message) {
			Message message = (Message) msg;
			// determine what to do with the message
			parent.handleMessage(message);
		} else {
			Logger.add("Received nonmessage: " + msg.getClass().getName());
		}
		MainScreen.window.blinkSTL(Constants.readSTL, (byte) 3);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		if (cause instanceof IOException) {
			// the connection was closed by the remote host
			parent.disconnect();
		} else {
			MainScreen.window.blinkSTL(Constants.exceptionSTL, (byte) 2);
			cause.printStackTrace();
			parent.disconnect();
		}

		ctx.close();
	}

	public void send(Message m) {

		context.write(m);
		context.flush();
		MainScreen.window.blinkSTL(Constants.writeSTL, (byte) 1);
	}
}
