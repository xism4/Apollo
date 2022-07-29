package com.moonsworth.apollo.api;

import com.google.protobuf.GeneratedMessageV3;
import com.moonsworth.apollo.api.bridge.ApolloPlayer;
import com.moonsworth.apollo.api.module.ApolloModule;
import com.moonsworth.apollo.api.module.ApolloModuleManager;
import com.moonsworth.apollo.api.proto.ApolloProtocol;
import com.moonsworth.apollo.api.proto.Msg;
import com.moonsworth.apollo.api.proto.SecondMsg;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Main API class for Apollo.
 */
public class Apollo {

    /**
     * The plugin message channel that Lunar Client and Apollo talk over.
     */
    public static final String PLUGIN_MESSAGE_CHANNEL = "lunarclient:apollo";

    /**
     * The currently loaded platform. This is set as early as possible.
     */
    @Getter
    private static ApolloPlatform platform = null;
    @Getter
    private static ApolloModuleManager apolloModuleManager = null;

    public static void setPlatform(ApolloPlatform platform) {
        Apollo.platform = platform;
        apolloModuleManager = new ApolloModuleManager();
    }

    /**
     * Registers modules for Apollo.
     * This needs to be done very close to the start of the platform.
     * @param clazz The module to register.
     */
    public static void using(Class<? extends ApolloModule> clazz) {
        apolloModuleManager.register(clazz);
    }

    public void withPlayer(Object o, Consumer<ApolloPlayer> consumer) {
        var apolloPlayer = platform.tryWrapPlayer(o);
        if (apolloPlayer != null) {
            consumer.accept(apolloPlayer);
        }
    }

    public void test() throws com.google.protobuf.InvalidProtocolBufferException {
        Msg message = Msg.newBuilder().setFoo("test").setBlah(SecondMsg.newBuilder().setBlah(1).build()).build();
//        message.

        com.moonsworth.apollo.api.proto.Msg.parseFrom(new byte[0]);
    }

}
