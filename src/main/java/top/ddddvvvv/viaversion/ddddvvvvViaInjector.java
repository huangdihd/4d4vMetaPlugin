package top.ddddvvvv.viaversion;

import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.gson.JsonObject;

public class ddddvvvvViaInjector implements ViaInjector {
    @Override
    public void inject() throws Exception {
    }

    @Override
    public void uninject() throws Exception {
    }

    @Override
    public ProtocolVersion getServerProtocolVersion() throws Exception {
        return ProtocolVersion.v1_21;
    }

    @Override
    public JsonObject getDump() {
        return new JsonObject();
    }
}
