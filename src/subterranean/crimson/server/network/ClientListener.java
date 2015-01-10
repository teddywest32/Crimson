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

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;

import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLException;

import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.enumerations.EncType;
import subterranean.crimson.universal.upnp.PortMapper;

public final class ClientListener extends Listener {

	public ArrayList<Connection> connections;

	public ClientListener(String n, int p, SecretKeySpec k, EncType enc, boolean u, boolean s) {
		SSL = s;
		PORT = p;
		UPNP = u;
		key = k;
		encryption = enc;
		connections = new ArrayList<Connection>();

		if (SSL) {
			try {
				SelfSignedCertificate ssc = new SelfSignedCertificate();
				sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SSLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			sslCtx = null;
		}
		this.start();
	}

	@Override
	public void run() {
		if (UPNP) {
			Logger.add("Attempting to automatically forward the port");
			try {
				PortMapper.forward(PORT);
				Logger.add("Port mapping Success!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.add("Port mapping failed!");
			}
		}

		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(final SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					if (sslCtx != null) {
						p.addLast("SSL Handler", sslCtx.newHandler(ch.alloc()));
					}
					final ServerHandler sh = new ServerHandler();
					final ChannelTrafficShapingHandler ctsh = new ChannelTrafficShapingHandler(300);
					p.addLast(ctsh, new ObjectEncoder(), new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)), sh);
					// spawn a new connection object
					new Thread(new Runnable() {
						public void run() {
							Connection c = new Connection(key, encryption, sh, ctsh, ch.remoteAddress());
							c.handshake();
						}
					}).start();

				}
			});

			// Bind and start to accept incoming connections.
			b.bind(PORT).sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();

			if (UPNP) {
				Logger.add("Attempting to remove port mapping");
				try {
					PortMapper.unforward(PORT);
					Logger.add("Port mapping removed");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Logger.add("Failed to remove port mapping");
				}
			}
		}

	}

}
