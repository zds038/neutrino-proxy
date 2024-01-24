package org.dromara.neutrinoproxy.client.sdk.core;

import cn.hutool.core.util.StrUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dromara.neutrinoproxy.client.sdk.config.ProxyConfig;
import org.dromara.neutrinoproxy.client.sdk.util.ProxyUtil;
import org.dromara.neutrinoproxy.client.sdk.util.UdpServerUtil;
import org.dromara.neutrinoproxy.core.ProxyMessage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 代理客户端服务
 * @author: aoshiguchen
 * @date: 2022/6/16
 */
@Slf4j
@Data
public class IAbProxyClientService {

    public ProxyConfig proxyConfig;

    public Bootstrap cmdTunnelBootstrap;

    public Bootstrap udpServerBootstrap;

    public Boolean isAotRuntime;

    private volatile Channel channel;
    /**
     * 重连次数
     */
    private volatile int reconnectCount = 0;

	/**
	 * 重连服务执行器
	 */
    public static final ScheduledExecutorService reconnectExecutor = Executors.newSingleThreadScheduledExecutor(new CustomThreadFactory("ClientReconnect"));

    public void init(){
        reconnectExecutor.scheduleWithFixedDelay(this::reconnect, 10, proxyConfig.getTunnel().getReconnection().getIntervalSeconds(), TimeUnit.SECONDS);
        try {
            this.start();
            UdpServerUtil.initCache(proxyConfig, udpServerBootstrap,isAotRuntime);
        } catch (Exception e) {
            // 启动连不上也做一下重连，因此先catch异常
            log.error("[CmdChannel] start error", e);
        }
    }

	public void start() {
		if (StrUtil.isEmpty(proxyConfig.getTunnel().getServerIp())) {
			log.error("not found server-ip config.");
			return;
		}
		if (null == proxyConfig.getTunnel().getServerPort()) {
			log.error("not found server-port config.");
			return;
		}
		if (null != proxyConfig.getTunnel().getSslEnable() && proxyConfig.getTunnel().getSslEnable()
				&& StrUtil.isEmpty(proxyConfig.getTunnel().getJksPath())) {
			log.error("not found jks-path config.");
			return;
		}
		if (StrUtil.isEmpty(proxyConfig.getTunnel().getLicenseKey())) {
			log.error("not found license-key config.");
			return;
		}
		if (null == channel || !channel.isActive()) {
			try {
				connectProxyServer();
			} catch (Exception e) {
				log.error("client start error", e);
			}
		} else {
			channel.writeAndFlush(ProxyMessage.buildAuthMessage(proxyConfig.getTunnel().getLicenseKey(), ProxyUtil.getClientId(proxyConfig)));
		}
	}

	/**
	 * 连接代理服务器
	 */
	private void connectProxyServer() throws InterruptedException {
		cmdTunnelBootstrap.connect()
			.addListener(new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						channel = future.channel();
						// 连接成功，向服务器发送客户端认证信息（licenseKey）
						ProxyUtil.setCmdChannel(future.channel());
						future.channel().writeAndFlush(ProxyMessage.buildAuthMessage(proxyConfig.getTunnel().getLicenseKey(), ProxyUtil.getClientId(proxyConfig)));
						log.info("[CmdChannel] connect proxy server {}:{} success. channelId:{}", proxyConfig.getTunnel().getServerIp(), proxyConfig.getTunnel().getServerPort(), future.channel().id().asLongText());

//						reconnectServiceEnable = true;
						reconnectCount = 0;
					} else {
						log.info("[CmdChannel] connect proxy server {}:{} failed!", proxyConfig.getTunnel().getServerIp(), proxyConfig.getTunnel().getServerPort());
					}
				}
			}).sync();
	}

	protected synchronized void reconnect() {
		if (null != channel) {
			if (channel.isActive()) {
				return;
			}
			channel.close();
		}

		log.info("[CmdChannel] client reconnect seq:{}", ++reconnectCount);
		try {
			connectProxyServer();
		} catch (Exception e) {
			log.error("[CmdChannel] reconnect error", e);
		}
	}
}
