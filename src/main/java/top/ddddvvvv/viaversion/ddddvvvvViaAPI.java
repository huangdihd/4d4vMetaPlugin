package top.ddddvvvv.viaversion;

import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;
import xin.bbtt.mcbot.Bot;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

public class ddddvvvvViaAPI implements ViaAPI<UUID> {

    @Override
    public int majorVersion() {
        return ViaAPI.super.majorVersion();
    }

    @Override
    public int apiVersion() {
        return ViaAPI.super.apiVersion();
    }

    @Override
    public ServerProtocolVersion getServerVersion() {
        return new ServerProtocolVersion() {
            @Override
            public ProtocolVersion lowestSupportedProtocolVersion() {
                return ProtocolVersion.v1_21;
            }

            @Override
            public ProtocolVersion highestSupportedProtocolVersion() {
                return ProtocolVersion.v1_21;
            }

            @Override
            public SortedSet<ProtocolVersion> supportedProtocolVersions() {
                SortedSet<ProtocolVersion> versions = new TreeSet<>();
                versions.add(ProtocolVersion.v1_21);
                return versions;
            }
        };
    }

    @Override
    public ProtocolVersion getPlayerProtocolVersion(UUID uuid) {
        return null;
    }

    @Override
    public int getPlayerVersion(UUID uuid) {
        return -1;
    }

    @Override
    public boolean isInjected(UUID uuid) {
        return false;
    }

    @Override
    public @Nullable UserConnection getConnection(UUID uuid) {
        return null;
    }

    @Override
    public String getVersion() {
        return Bot.INSTANCE.getPluginManager().getPlugin("4d4vMetaPlugin").getVersion();
    }

    @Override
    public void sendRawPacket(UUID uuid, ByteBuf packet) {
    }

    @Override
    public SortedSet<ProtocolVersion> getSupportedProtocolVersions() {
        return new TreeSet<>();
    }

    @Override
    public SortedSet<ProtocolVersion> getFullSupportedProtocolVersions() {
        return new TreeSet<>();
    }

    @Override
    public LegacyViaAPI<UUID> legacyAPI() {
        return null;
    }
}