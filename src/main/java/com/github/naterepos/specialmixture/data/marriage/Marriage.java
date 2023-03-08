package com.github.naterepos.specialmixture.data.marriage;

import com.pokeland.api.core.CoreApi;
import com.pokeland.api.core.annotations.Internal;
import com.pokeland.api.core.data.CorePlayer;
import com.pokeland.core.shadow.sponge.configurate.objectmapping.ConfigSerializable;

import java.util.UUID;

@ConfigSerializable
public class Marriage {

    private UUID personA;
    private UUID personB;

    @Internal public Marriage() {}

    public Marriage(CorePlayer personA, CorePlayer personB) {
        this.personA = personA.getUniqueId();
        this.personB = personB.getUniqueId();
    }

    public boolean contains(UUID person) {
        return this.personB == person || this.personA == person;
    }

    public CorePlayer getPersonA() {
        return CoreApi.get().getPlayer(personA);
    }

    public CorePlayer getPersonB() {
        return CoreApi.get().getPlayer(personB);
    }
}
