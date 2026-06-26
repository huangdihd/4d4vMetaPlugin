package top.ddddvvvv;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import io.netty.channel.Channel;
import lombok.Getter;
import org.geysermc.mcprotocollib.network.ClientSession;
import org.geysermc.mcprotocollib.network.event.session.PacketSendingEvent;
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter;
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.GameMode;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundSystemChatPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.ServerboundChatCommandPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ddddvvvv.listeners.AutoJoinListener;
import top.ddddvvvv.listeners.JoinButtonRecorder;
import top.ddddvvvv.listeners.RespawnPacketListener;
import xin.bbtt.via.XinViaProvider;
import xin.bbtt.mcbot.Bot;
import xin.bbtt.mcbot.LoginFlow.LoginFlow;
import xin.bbtt.mcbot.Server;
import xin.bbtt.mcbot.Utils;
import xin.bbtt.mcbot.plugin.MetaPlugin;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class ddddvvvvMetaPlugin implements MetaPlugin {

    private static final Logger log = LoggerFactory.getLogger("4d4vMetaPlugin");
    private UserConnection userConnection;
    private volatile boolean setupDone = false;
    @Getter
    private static final LoginFlow loginFlow = LoginFlow.builder(cmd -> Bot.INSTANCE.getSession().send(new ServerboundChatCommandPacket(cmd)))
            .eventManager(Bot.INSTANCE.getPluginManager().events())
            .templateExpander(t -> t.replace("{password}",
            Bot.INSTANCE.getConfig().getConfigData().getAccount().getPassword()))
            .step(ClientboundSystemChatPacket.class)
                .match(p -> Utils.toString(p.getContent()).equals("§8[§6玩家系统§8] §c请输入“/register <密码> <再输入一次以确定密码>”以注册"))
                .then("reg {password} {password}")
                .register()
                .successWhen(p -> Utils.toString(p.getContent()).equals("§8[§6玩家系统§8] §c已成功注册！"))
                .skipWhen(p -> Utils.toString(p.getContent()).contains("登录"))
                .add()
            .step(ClientboundSystemChatPacket.class)
                .match(p -> Utils.toString(p.getContent()).equals("§8[§6玩家系统§8] §c请输入“/login <密码>”以登录"))
                .then("l {password}")
                .login()
                .successWhen(p -> Utils.toString(p.getContent()).equals("§8[§6玩家系统§8] §c已成功登录！"))
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
            AutoJoinListener.last_action_time = System.currentTimeMillis();
            return Server.Login;
        }
        return Server.Game;
    }

    @Override
    public void onLoad() {
        // ViaVersion/ViaBackwards are bootstrapped by the XinVia library plugin
        // (declared as a dependency in plugin.yml), so nothing is initialized here.
    }

    @Override
    public void onUnload() {
    }

    @Override
    public void onEnable() {
        loginFlow.reset();
        Bot.INSTANCE.addPacketListener(loginFlow, this);
        Bot.INSTANCE.addPacketListener(new RespawnPacketListener(), this);
        Bot.INSTANCE.addPacketListener(new JoinButtonRecorder(), this);
        Bot.INSTANCE.addPacketListener(new AutoJoinListener(), this);
        Bot.INSTANCE.addPacketListener(new SessionAdapter() {
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
        }, this);
    }

    private void setupViaHandlers(Channel channel) {
        userConnection = XinViaProvider.setup(
                channel,
                ProtocolVersion.v1_21_11,
                ProtocolVersion.v1_21,
                Bot.INSTANCE.getProtocol().getProfile().getId());
    }

    @Override
    public void onDisable() {
        setupDone = false;

        ClientSession session = Bot.INSTANCE.getSession();
        Channel channel = session != null ? session.getChannel() : null;
        XinViaProvider.teardown(channel, userConnection);
        userConnection = null;
    }
}
