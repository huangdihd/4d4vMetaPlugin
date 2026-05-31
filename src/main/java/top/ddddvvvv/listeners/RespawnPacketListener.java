package top.ddddvvvv.listeners;

import org.geysermc.mcprotocollib.network.Session;
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter;
import org.geysermc.mcprotocollib.network.packet.Packet;
import org.geysermc.mcprotocollib.protocol.data.game.ClientCommand;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerCombatKillPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundSetHealthPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.ServerboundClientCommandPacket;
import xin.bbtt.mcbot.Bot;
import xin.bbtt.mcbot.Server;

public class RespawnPacketListener extends SessionAdapter {
    @Override
    public void packetReceived(Session session, Packet packet) {
        if (Bot.INSTANCE.getServer() == Server.Game) return;
        if (packet instanceof ClientboundPlayerCombatKillPacket) respawn();
        if (packet instanceof ClientboundSetHealthPacket setHealthPacket) handleSetHealthPacket(setHealthPacket);

    }
    private void respawn() {
        Bot.INSTANCE.getSession().send(new ServerboundClientCommandPacket(
                ClientCommand.RESPAWN
        ));
    }
    private void handleSetHealthPacket(ClientboundSetHealthPacket setHealthPacket) {
        if (setHealthPacket.getHealth() <= 0) respawn();
    }
}
