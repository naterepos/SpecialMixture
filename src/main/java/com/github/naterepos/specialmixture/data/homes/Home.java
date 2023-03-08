package com.github.naterepos.specialmixture.data.homes;

import com.github.naterepos.specialmixture.ServerUtilities;
import com.pokeland.api.core.Vector;
import com.pokeland.api.core.annotations.Internal;
import com.pokeland.api.core.commands.ExecuteCommandException;
import com.pokeland.api.core.data.interfaces.CommandSender;
import com.pokeland.api.core.text.Message;
import com.pokeland.core.shadow.sponge.configurate.objectmapping.ConfigSerializable;
import net.minecraft.entity.player.ServerPlayerEntity;

@ConfigSerializable
public class Home {

    private Vector location;
    private String name;

    @Internal public Home() {}

    public Home(String name, Vector location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Vector getLocation() {
        return location;
    }

    public void teleport(CommandSender sender) {
        try {
            ServerPlayerEntity player = sender.<ServerPlayerEntity>asServerPlayer();
            player.teleport(ServerUtilities.getDefaultWorld(), location.getX(), location.getY(), location.getZ(), player.rotationYawHead, player.rotationPitch);
        } catch (ExecuteCommandException e) {
            sender.sendMessage(Message.of("&cOnly a player may teleport!"));
        }
    }

    @Override
    public String toString() {
        return "&r&6 - &e" + name + ": " + location.toString();
    }
}
