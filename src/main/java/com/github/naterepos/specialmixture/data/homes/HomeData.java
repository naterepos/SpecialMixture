package com.github.naterepos.specialmixture.data.homes;

import com.github.naterepos.specialmixture.SpecialMixture;
import com.pokeland.api.core.CoreApi;
import com.pokeland.api.core.annotations.Internal;
import com.pokeland.core.shadow.sponge.configurate.objectmapping.ConfigSerializable;

import java.util.Map;
import java.util.UUID;

@ConfigSerializable
public class HomeData {

    private Map<UUID, Homes> homes;

    @Internal public HomeData() {}

    public Homes getHomes(UUID player) {
        if(!homes.containsKey(player)) {
            Homes registered = new Homes(player);
            homes.put(player, registered);
            CoreApi.get().getConfig().save(SpecialMixture.plugin());
            return registered;
        }
        return homes.get(player);
    }
}
