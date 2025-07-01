package fuzs.portablehole.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;

public class ClientConfig implements ConfigCore {
    @Config(description = "Set this to true to disable translucent particles, for shader compatibility with some shaders which implement a deferred lighting pass.")
    public boolean opaqueSparkleParticles = false;
}
