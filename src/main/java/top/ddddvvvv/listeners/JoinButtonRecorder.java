package top.ddddvvvv.listeners;

import org.geysermc.mcprotocollib.network.Session;
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter;
import org.geysermc.mcprotocollib.network.packet.Packet;
import org.geysermc.mcprotocollib.protocol.data.game.item.ItemStack;
import org.geysermc.mcprotocollib.protocol.data.game.item.component.DataComponentType;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.inventory.ClientboundContainerSetContentPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xin.bbtt.mcbot.Bot;
import xin.bbtt.mcbot.Server;

public class JoinButtonRecorder extends SessionAdapter {

    @Override
    public void packetReceived(Session session, Packet packet) {
        if (!(packet instanceof ClientboundContainerSetContentPacket containerSetContentPacket)) return;
        if (Bot.INSTANCE.getServer() == Server.Game) return;
        for(int i = 0;i < containerSetContentPacket.getItems().length;i++) {
            checkItemStack(i, containerSetContentPacket.getItems()[i]);
        }
    }

    private void checkItemStack(int index, ItemStack itemStack) {
        if (itemStack == null) return;
        if (!itemStack.toString().contains("开始游戏")) return;
        AutoJoinListener.join_button_slot = index % 9;
    }
}
