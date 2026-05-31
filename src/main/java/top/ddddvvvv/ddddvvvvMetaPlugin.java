package top.ddddvvvv;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import com.viaversion.viaversion.ViaManagerImpl;
import io.netty.channel.Channel;
import org.geysermc.mcprotocollib.network.ClientSession;
import org.geysermc.mcprotocollib.network.event.session.PacketSendingEvent;
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter;
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.GameMode;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundSystemChatPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.ServerboundChatCommandPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ddddvvvv.viaversion.ddddvvvvViaDecoder;
import top.ddddvvvv.viaversion.ddddvvvvViaEncoder;
import top.ddddvvvv.viaversion.ddddvvvvViaPlatform;
import top.ddddvvvv.viaversion.ddddvvvvViaInjector;
import top.ddddvvvv.viaversion.ddddvvvvViaPlatformLoader;
import top.ddddvvvv.viaversion.ddddvvvvViaBackwardsPlatform;
import xin.bbtt.mcbot.Bot;
import xin.bbtt.mcbot.LoginFlow.LoginFlow;
import xin.bbtt.mcbot.Server;
import xin.bbtt.mcbot.Utils;
import xin.bbtt.mcbot.plugin.MetaPlugin;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class ddddvvvvMetaPlugin implements MetaPlugin {

    private static final Logger log = LoggerFactory.getLogger("4d4vMetaPlugin");
    private UserConnection userConnection;
    private volatile boolean setupDone = false;
    
    private static final LoginFlow loginFlow = LoginFlow.builder(cmd -> Bot.INSTANCE.getSession().send(new ServerboundChatCommandPacket(cmd)))
            .eventManager(Bot.INSTANCE.getPluginManager().events())
            .templateExpander(t -> t.replace("{password}",
            Bot.INSTANCE.getConfig().getConfigData().getAccount().getPassword()))
            .step(ClientboundSystemChatPacket.class)
                .match(p -> Utils.toString(p.getContent()).equals("§8[§6玩家系统§8] §c请输入“/register <密码> <再输入一次以确定密码>”以注册"))
                .then("reg {password} {password}")
                .register()
                .successWhen(p -> Utils.toString(p.getContent()).equals("§8[§6玩家系统§8] §c已成功注册！"))
                .skipWhen(p -> Utils.toString(p.getContent()).contains("§8[§6玩家系统§8] §c请输入“/login <密码>”以登录"))
                .add()
            .step(ClientboundSystemChatPacket.class)
                .match(p -> Utils.toString(p.getContent()).equals("§8[§6玩家系统§8] §c请输入“/login <密码>”以登录"))
                .then("l {password}")
                .login()
                .successWhen(p -> Utils.toString(p.getContent()).equals("§8[§6玩家系统§8] §c已成功登录！"))
                .onSuccess(p -> log.info("登录/注册成功"))
                .add()
            .cooldown(2000)
            .build();

    @Override
    public SocketAddress getServerSocketAddress() {
        return new InetSocketAddress("4d4v.top", 25565);
    }

    @Override
    public Server getServer(ClientboundLoginPacket loginPacket) {
        if (loginPacket.getCommonPlayerSpawnInfo().getGameMode() == GameMode.ADVENTURE) {
            loginFlow.reset();
            return Server.Login;
        }
        return Server.Game;
    }

    @Override
    public void onLoad() {
        ddddvvvvViaPlatform platform = new ddddvvvvViaPlatform();
        ViaManagerImpl manager = new ViaManagerImpl(platform, new ddddvvvvViaInjector(), null, new ddddvvvvViaPlatformLoader());
        Via.init(manager);
        platform.getConf().reload();
        manager.init();
        new ddddvvvvViaBackwardsPlatform().init(new File("viabackwards"));
    }

    @Override
    public void onUnload() {
    }

    @Override
    public void onEnable() {
        ClientSession session = Bot.INSTANCE.getSession();
        loginFlow.reset();
        session.addListener(loginFlow);
        session.addListener(new SessionAdapter() {
            @Override
            public void packetSending(PacketSendingEvent event) {
                if (setupDone) {
                    return;
                }
                Channel channel = event.getSession().getChannel();
                if (channel != null) {
                    setupDone = true;
                    log.info("Intercepted packet {}, setting up handlers synchronously", event.getPacket().getClass().getSimpleName());
                    setupViaHandlers(channel);
                }
            }
        });
    }

    private void setupViaHandlers(Channel channel) {
        if (Via.getManager() == null || Via.getManager().getProtocolManager() == null) {
            log.warn("ViaVersion not ready yet");
            return;
        }

        userConnection = new UserConnectionImpl(channel, true);

        ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
        protocolInfo.setProtocolVersion(ProtocolVersion.v1_21_11);
        protocolInfo.setServerProtocolVersion(ProtocolVersion.v1_21);
        protocolInfo.setState(State.HANDSHAKE);
        protocolInfo.setUuid(Bot.INSTANCE.getProtocol().getProfile().getId());

        new ProtocolPipelineImpl(userConnection);

        Via.getManager().getConnectionManager().onLoginSuccess(userConnection);

        try {
            channel.pipeline().addBefore("codec", "via-decoder", new ddddvvvvViaDecoder(userConnection));
            channel.pipeline().addBefore("codec", "via-encoder", new ddddvvvvViaEncoder(userConnection));
        } catch (Exception e) {
            log.error("Failed to add handlers", e);
        }
    }

    @Override
    public void onDisable() {
        setupDone = false;

        ClientSession session = Bot.INSTANCE.getSession();
        if (session != null) {
            Channel channel = session.getChannel();
            if (channel != null) {
                channel.eventLoop().execute(() -> {
                    if (channel.pipeline().get("via-decoder") != null) {
                        channel.pipeline().remove("via-decoder");
                    }
                    if (channel.pipeline().get("via-encoder") != null) {
                        channel.pipeline().remove("via-encoder");
                    }
                });
            }
        }

        if (userConnection != null) {
            try {
                userConnection.disconnect("Plugin disabled");
            } catch (Exception ignored) {}
            userConnection = null;
        }
    }
}
