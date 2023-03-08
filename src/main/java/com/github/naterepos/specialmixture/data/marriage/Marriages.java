package com.github.naterepos.specialmixture.data.marriage;

import com.github.naterepos.specialmixture.SpecialMixture;
import com.pokeland.api.core.CoreApi;
import com.pokeland.api.core.annotations.Internal;
import com.pokeland.api.core.data.CorePlayer;
import com.pokeland.core.shadow.sponge.configurate.objectmapping.ConfigSerializable;

import java.util.*;

@ConfigSerializable
public class Marriages {

    // map: proposed to -> proposer
    private static final Map<UUID, UUID> proposalMap = new HashMap<>();
    private Set<Marriage> marriages = new HashSet<>();

    @Internal public Marriages() {}

    public Optional<Marriage> getMarriage(UUID personA) {
        for (Marriage marriage : marriages) {
            if(marriage.contains(personA)) {
                return Optional.of(marriage);
            }
        }
        return Optional.empty();
    }

    public void propose(CorePlayer proposer, CorePlayer proposedTo) {
        proposalMap.put(proposedTo.getUniqueId(), proposer.getUniqueId());
    }

    public Optional<UUID> getProposalOffer(CorePlayer proposedTo) {
        return Optional.ofNullable(proposalMap.get(proposedTo.getUniqueId()));
    }

    public void marry(CorePlayer personA, CorePlayer personB) {
        marriages.add(new Marriage(personA, personB));
        proposalMap.remove(personA.getUniqueId());
        proposalMap.remove(personB.getUniqueId());
        save();
    }

    public void divorce(CorePlayer personA, CorePlayer personB) {
        marriages.removeIf(marriage -> marriage.contains(personA.getUniqueId()) || marriage.contains(personB.getUniqueId()));
        save();
    }

    private void save() {
        CoreApi.get().getConfig().save(SpecialMixture.plugin(), "marriages.data");
    }
}
