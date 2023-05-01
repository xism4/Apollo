package com.lunarclient.apollo.example.modules;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.coloredfire.ColoredFireModule;
import com.lunarclient.apollo.player.ApolloPlayer;

import java.awt.Color;
import java.util.Collection;
import java.util.UUID;

public class ColoredFireExample {

    private final ColoredFireModule coloredFireModule = Apollo.getModuleManager().getModule(ColoredFireModule.class);

    public void overrideColoredFireExample(UUID burningPlayer) {
        Collection<ApolloPlayer> viewers = Apollo.getPlayerManager().getPlayers();

        this.coloredFireModule.overrideColoredFire(viewers,
            burningPlayer, 
            Color.BLUE
        );
    }

    public void resetColoredFireExample(UUID burningPlayer) {
        Collection<ApolloPlayer> viewers = Apollo.getPlayerManager().getPlayers();

        this.coloredFireModule.resetColoredFire(viewers, burningPlayer);
    }

    public void resetColoredFiresExample() {
        Collection<ApolloPlayer> viewers = Apollo.getPlayerManager().getPlayers();

        for (ApolloPlayer viewer : viewers) {
            this.coloredFireModule.resetColoredFires(viewer);
        }
    }

}