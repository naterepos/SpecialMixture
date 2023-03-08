package com.github.naterepos.specialmixture;

import com.github.naterepos.specialmixture.data.marriage.Marriage;
import com.pokeland.api.core.CoreApi;
import com.pokeland.api.core.data.CorePlayer;
import com.pokeland.api.core.text.Message;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;

public class ForgeListeners {

    @SubscribeEvent
    public void onHit(PlayerInteractEvent.EntityInteract e) {
        if(e.getTarget() instanceof ServerPlayerEntity && e.getEntity() instanceof ServerPlayerEntity) {
            return;
        }

        CorePlayer target = CoreApi.get().getPlayer(e.getTarget().getUniqueID());
        CorePlayer source = CoreApi.get().getPlayer(e.getEntity().getUniqueID());

        Optional<Marriage> marriage = SpecialMixture.plugin().getMarriages().getMarriage(target.getUniqueId());
        if(marriage.isEmpty()) {
            return;
        }

        target.sendMessage(Message.of("&dYou've been hugged by " + source.getName()));
        source.sendMessage(Message.of("&dYou've hugged " + target.getName()));
    }
}

