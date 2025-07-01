package fuzs.portablehole.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;

public class ServerConfig implements ConfigCore {
    private static final String PERFORMANCE_DISCLAIMER = "May want to disable this feature to increase performance when a large hole depth is set.";

    @Config(description = "Cooldown in ticks after using a portable hole.")
    @Config.IntRange(min = 0, max = 72000)
    public int portableHoleCooldown = 40;
    @Config(description = "How deep does the hole go created by a portable hole.")
    @Config.IntRange(min = 1)
    public int temporaryHoleDepth = 12;
    @Config(description = "For how long does a temporary hole stay open.")
    @Config.IntRange(min = 0)
    public int temporaryHoleDuration = 100;
    @Config(description = {"Maximum block hardness a portable hole can replace.", "Check out the Minecraft Wiki for more information: https://minecraft.fandom.com/wiki/Breaking#Blocks_by_hardness"})
    public double maxBlockHardness = 20.0;
    @Config(category = "visuals", description = {"Render spark particles to indicate the block replaced by a temporary hole.", PERFORMANCE_DISCLAIMER})
    public boolean sparkParticles = true;
    @Config(category = "visuals", description = "Render the end portal overlay on the outside of a temporary hole.")
    public boolean portalOverlay = true;
    @Config(category = "visuals", description = {"Play a breaking sound and show particles when blocks reappear from a temporary hole.", PERFORMANCE_DISCLAIMER})
    public boolean particlesForReappearingBlocks = true;
    @Config(description = "Allow portable holes to go through block entities. Not safe to use (e.g. breaks double chests), therefore disabled by default.")
    public boolean replaceBlockEntities = false;
}
