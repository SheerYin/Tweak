package io.github.yin.tweak;

import io.github.yin.tweak.command.brigadier.Literal;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

public class Bootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext bootstrapContext) {
        LifecycleEventManager<BootstrapContext> manager = bootstrapContext.getLifecycleManager();

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register(Literal.INSTANCE.node(), Literal.INSTANCE.getAlias());
        });
    }

}
