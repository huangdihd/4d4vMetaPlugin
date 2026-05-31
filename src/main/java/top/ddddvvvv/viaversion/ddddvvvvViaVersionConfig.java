package top.ddddvvvv.viaversion;

import com.viaversion.viaversion.api.configuration.RateLimitConfig;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocol.BlockedProtocolVersionsImpl;

import java.util.Map;

public class ddddvvvvViaVersionConfig implements ViaVersionConfig {
    @Override
    public boolean isCheckForUpdates() {
        return false;
    }

    @Override
    public void setCheckForUpdates(boolean checkForUpdates) {

    }

    @Override
    public boolean isPreventCollision() {
        return false;
    }

    @Override
    public boolean isNewEffectIndicator() {
        return false;
    }

    @Override
    public boolean isShowNewDeathMessages() {
        return false;
    }

    @Override
    public boolean logEntityDataErrors() {
        return false;
    }

    @Override
    public boolean isShieldBlocking() {
        return false;
    }

    @Override
    public boolean isNoDelayShieldBlocking() {
        return false;
    }

    @Override
    public boolean isShowShieldWhenSwordInHand() {
        return false;
    }

    @Override
    public boolean isHologramPatch() {
        return false;
    }

    @Override
    public boolean isPistonAnimationPatch() {
        return false;
    }

    @Override
    public boolean isBossbarPatch() {
        return false;
    }

    @Override
    public boolean isBossbarAntiflicker() {
        return false;
    }

    @Override
    public double getHologramYOffset() {
        return 0;
    }

    @Override
    public boolean isAutoTeam() {
        return false;
    }

    @Override
    public RateLimitConfig getPacketTrackerConfig() {
        return new RateLimitConfig(
                false,
                Integer.MAX_VALUE,
                "",
                Integer.MAX_VALUE,
                Integer.MAX_VALUE,
                1000000000L,
                "",
                ""
        );
    }

    @Override
    public RateLimitConfig getPacketSizeTrackerConfig() {
        return getPacketTrackerConfig();
    }

    @Override
    public boolean isSendSupportedVersions() {
        return false;
    }

    @Override
    public boolean isSimulatePlayerTick() {
        return false;
    }

    @Override
    public boolean isItemCache() {
        return false;
    }

    @Override
    public boolean isNMSPlayerTicking() {
        return false;
    }

    @Override
    public boolean isReplacePistons() {
        return false;
    }

    @Override
    public int getPistonReplacementId() {
        return 0;
    }

    @Override
    public boolean isChunkBorderFix() {
        return false;
    }

    @Override
    public boolean is1_13TeamColourFix() {
        return false;
    }

    @Override
    public boolean shouldRegisterUserConnectionOnJoin() {
        return false;
    }

    @Override
    public boolean is1_12QuickMoveActionFix() {
        return false;
    }

    @Override
    public BlockedProtocolVersions blockedProtocolVersions() {
        return new BlockedProtocolVersionsImpl(
                java.util.Collections.emptySet(),
                ProtocolVersion.unknown,
                ProtocolVersion.unknown
        );
    }

    @Override
    public String getBlockedDisconnectMsg() {
        return "";
    }

    @Override
    public boolean logBlockedJoins() {
        return false;
    }

    @Override
    public String getReloadDisconnectMsg() {
        return "";
    }

    @Override
    public boolean logOtherConversionWarnings() {
        return false;
    }

    @Override
    public boolean logTextComponentConversionErrors() {
        return false;
    }

    @Override
    public boolean isDisable1_13AutoComplete() {
        return false;
    }

    @Override
    public boolean isServersideBlockConnections() {
        return false;
    }

    @Override
    public String getBlockConnectionMethod() {
        return "";
    }

    @Override
    public boolean isReduceBlockStorageMemory() {
        return false;
    }

    @Override
    public boolean isStemWhenBlockAbove() {
        return false;
    }

    @Override
    public boolean isVineClimbFix() {
        return false;
    }

    @Override
    public boolean isSnowCollisionFix() {
        return false;
    }

    @Override
    public boolean isInfestedBlocksFix() {
        return false;
    }

    @Override
    public int get1_13TabCompleteDelay() {
        return 0;
    }

    @Override
    public boolean isTruncate1_14Books() {
        return false;
    }

    @Override
    public boolean isLeftHandedHandling() {
        return false;
    }

    @Override
    public boolean is1_9HitboxFix() {
        return false;
    }

    @Override
    public boolean is1_14HitboxFix() {
        return false;
    }

    @Override
    public boolean isNonFullBlockLightFix() {
        return false;
    }

    @Override
    public boolean is1_14HealthNaNFix() {
        return false;
    }

    @Override
    public boolean is1_15InstantRespawn() {
        return false;
    }

    @Override
    public boolean isIgnoreLong1_16ChannelNames() {
        return false;
    }

    @Override
    public boolean isForcedUse1_17ResourcePack() {
        return false;
    }

    @Override
    public JsonElement get1_17ResourcePackPrompt() {
        return null;
    }

    @Override
    public WorldIdentifiers get1_16WorldNamesMap() {
        return new WorldIdentifiers(
                "minecraft:overworld",
                "minecraft:the_nether",
                "minecraft:the_end"
        );
    }

    @Override
    public boolean cache1_17Light() {
        return false;
    }

    @Override
    public boolean isArmorToggleFix() {
        return false;
    }

    @Override
    public boolean translateOcelotToCat() {
        return false;
    }

    @Override
    public boolean enforceSecureChat() {
        return false;
    }

    @Override
    public boolean handleInvalidItemCount() {
        return false;
    }

    @Override
    public boolean cancelBlockSounds() {
        return false;
    }

    @Override
    public boolean hideScoreboardNumbers() {
        return false;
    }

    @Override
    public boolean fix1_21PlacementRotation() {
        return false;
    }

    @Override
    public boolean cancelSwingInInventory() {
        return false;
    }

    @Override
    public int maxErrorLength() {
        return 0;
    }

    @Override
    public boolean use1_8HitboxMargin() {
        return false;
    }

    @Override
    public boolean sendPlayerDetails() {
        return false;
    }

    @Override
    public boolean sendServerDetails() {
        return false;
    }

    @Override
    public void reload() {

    }

    @Override
    public void save() {

    }

    @Override
    public void set(String path, Object value) {

    }

    @Override
    public Map<String, Object> getValues() {
        return Map.of();
    }
}
