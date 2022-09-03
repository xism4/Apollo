package com.moonsworth.apollo.api.module.impl;

import com.google.common.collect.ImmutableList;
import com.moonsworth.apollo.api.ApolloPlatform;
import com.moonsworth.apollo.api.bridge.ApolloPlayer;
import com.moonsworth.apollo.api.module.ApolloModule;
import com.moonsworth.apollo.api.options.ApolloOption;
import com.moonsworth.apollo.api.protocol.DisplayVignetteMessage;
import com.moonsworth.apollo.api.protocol.EquipSuitMessage;
import com.moonsworth.apollo.api.protocol.ToggleArmorMessage;

import java.util.List;

public class EVNTModule extends ApolloModule {

    public EVNTModule() {
        super("EVNTModule");
    }

    @Override
    public List<ApolloOption> options() {
        return ImmutableList.of();
    }

    @Override
    public boolean notifyPlayers() {
        return true;
    }

    @Override
    public List<ApolloPlatform.Kind> runsOn() {
        return ImmutableList.of(ApolloPlatform.Kind.SERVER);
    }

    public void togglePlayerArmor(ApolloPlayer player, boolean showArmor) {
        player.sendPacket(ToggleArmorMessage.newBuilder().setShowArmor(showArmor).build());
    }

    public void equipSuit(ApolloPlayer player, int suitId, boolean equiped) {
        player.sendPacket(EquipSuitMessage.newBuilder().setSuitId(suitId).setEquipped(equiped).build());
    }

    /**
     * Displays a vignette texture (such as a pumpkin head).
     * @param texture The texture to display
     * @param opacity The opacity to render the texture with in the range [0.0, 1.0]
     */
    public void displayVignette(ApolloPlayer player, String texture, float opacity) {
        var builder = DisplayVignetteMessage.newBuilder();
        if (texture != null) {
            builder = builder.addTexture(texture);
        }
        builder = builder.setOpacity(opacity);
        player.sendPacket(builder.build());
    }

    public void clearVignette(ApolloPlayer player) {
        player.sendPacket(DisplayVignetteMessage.newBuilder().addTexture("").setOpacity(0).build());
    }

//    public void displayGui(ApolloPlayer player, Gui gui) {
//        player.sendPacket(OpenGuiMessage.newBuilder().setGuiId(gui.ordinal()).build());
//    }
//
//    public void closeGui(ApolloPlayer player) {
//        //Pass in -1 to close the gui
//        player.sendPacket(OpenGuiMessage.newBuilder().setGuiId(-1).build());
//    }
}