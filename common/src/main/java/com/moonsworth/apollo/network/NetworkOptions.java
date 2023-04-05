package com.moonsworth.apollo.network;

import com.google.protobuf.Value;
import com.moonsworth.apollo.module.ApolloModule;
import com.moonsworth.apollo.option.AbstractOptions;
import com.moonsworth.apollo.option.Option;
import com.moonsworth.apollo.option.Options;
import com.moonsworth.apollo.player.AbstractApolloPlayer;
import com.moonsworth.apollo.player.ApolloPlayer;
import com.moonsworth.apollo.player.ApolloPlayerVersion;
import java.util.Set;
import lunarclient.apollo.common.ModuleConfig;
import lunarclient.apollo.common.Modules;
import org.jetbrains.annotations.Nullable;

public final class NetworkOptions {

    public static void sendOption(ApolloModule module,
                                  Option<?, ?, ?> key,
                                  Value value,
                                  Iterable<ApolloPlayer> players) {
        if(!key.isNotify()) return;

        Modules.Builder modulesBuilder = Modules.newBuilder();
        ModuleConfig.Builder moduleBuilder = NetworkOptions.module(module);
        moduleBuilder.putOptions(key.getKey(), value);
        modulesBuilder.addModules(moduleBuilder.build());

        for(ApolloPlayer player : players) {
            checkPlayerVersionSupport(module, player);

            ((AbstractApolloPlayer) player).sendPacket(modulesBuilder.build());
        }
    }

    public static void sendOptions(Iterable<ApolloModule> modules,
                                   ApolloPlayer... players) {
        for(ApolloPlayer player : players) {
            Modules.Builder modulesBuilder = Modules.newBuilder();
            for(ApolloModule module : modules) {
                checkPlayerVersionSupport(module, player);

                modulesBuilder.addModules(NetworkOptions.moduleWithOptions(
                        module,
                        player
                ).build());
            }

            ((AbstractApolloPlayer) player).sendPacket(modulesBuilder.build());
        }
    }

    private static void checkPlayerVersionSupport(ApolloModule module, ApolloPlayer player) {
        Set<ApolloPlayerVersion> supportedVersions = module.getSupportedClientVersions();
        ApolloPlayerVersion playerVersion = player.getVersion();

        if(!supportedVersions.contains(playerVersion)) {
            throw new IllegalStateException(String.format("Module %s doesn't support client version %s!",
                module.getName(), playerVersion.name()));
        }
    }

    private static ModuleConfig.Builder moduleWithOptions(ApolloModule module,
                                                           @Nullable ApolloPlayer player) {
        ModuleConfig.Builder builder = NetworkOptions.module(module);
        Options options = player != null ? module.getOptions().get(player) : module.getOptions();
        for(Option<?, ?, ?> option : options) {
            if(!option.isNotify()) continue;
            Value.Builder valueBuilder = Value.newBuilder();
            Object value = options.get(option);
            Value wrapper = ((AbstractOptions) options).wrapValue(valueBuilder, value);
            builder.putOptions(option.getKey(), wrapper);
        }
        return builder;
    }

    private static ModuleConfig.Builder module(ApolloModule module) {
        return ModuleConfig.newBuilder()
                .setName(module.getName())
                .setEnable(module.isEnabled());
    }

}
