package com.github.naterepos.specialmixture.data.homes;

import com.github.naterepos.specialmixture.SpecialMixture;
import com.github.naterepos.specialmixture.data.marriage.Marriage;
import com.pokeland.api.core.CoreApi;
import com.pokeland.api.core.annotations.Internal;
import com.pokeland.api.core.helper.CollectionHelper;
import com.pokeland.core.shadow.sponge.configurate.objectmapping.ConfigSerializable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ConfigSerializable
public class Homes {

    private UUID user;
    private Set<Home> homes;

    @Internal public Homes() {};

    public Homes(UUID user) {
        this.user = user;
        this.homes = new HashSet<>();
    }

    public void addHome(Home home) {
        this.homes.add(home);
        save();
    }

    public void deleteHome(Home home) {
        this.homes.remove(home);
        save();
    }

    public Optional<Home> getPersonalHome(String id) {
        for (Home home : homes) {
            if(home.getName().equalsIgnoreCase(id)) {
                return Optional.of(home);
            }
        }
        return Optional.empty();
    }

    public Optional<Home> getHome(String id) {
        Optional<Marriage> optMarriage = SpecialMixture.plugin().getMarriages().getMarriage(user);
        Set<Home> selection = null;
        if(optMarriage.isPresent()) {
            Marriage marriage = optMarriage.get();
            Homes homesA = SpecialMixture.plugin().getHomes().getHomes(marriage.getPersonA().getUniqueId());
            Homes homesB = SpecialMixture.plugin().getHomes().getHomes(marriage.getPersonB().getUniqueId());
            selection = new HashSet<>(homesA.getPersonalHomes());
            selection.addAll(homesB.getPersonalHomes());
        } else {
            selection = homes;
        }

        for (Home home : selection) {
            if(home.getName().equalsIgnoreCase(id)) {
                return Optional.of(home);
            }
        }
        return Optional.empty();
    }

    protected Set<Home> getPersonalHomes() {
        return homes == null ? new HashSet<>() : homes;
    }

    public Optional<Home> getFirstHome() {
        Optional<Marriage> optMarriage = SpecialMixture.plugin().getMarriages().getMarriage(user);
        Set<Home> selection = null;
        if(optMarriage.isPresent()) {
            Marriage marriage = optMarriage.get();
            Homes homesA = SpecialMixture.plugin().getHomes().getHomes(marriage.getPersonA().getUniqueId());
            Homes homesB = SpecialMixture.plugin().getHomes().getHomes(marriage.getPersonB().getUniqueId());
            selection = new HashSet<>(homesA.getPersonalHomes());
            selection.addAll(homesB.getPersonalHomes());
        } else {
            selection = homes;
        }
        return selection == null ? Optional.empty() : selection.stream().findFirst();
    }

    public boolean isSingular() {
        return homes == null || homes.size() == 0;
    }

    @Override
    public String toString() {
        Optional<Marriage> optMarriage = SpecialMixture.plugin().getMarriages().getMarriage(user);
        if(optMarriage.isPresent()) {
            Marriage marriage = optMarriage.get();
            Homes homesA = SpecialMixture.plugin().getHomes().getHomes(marriage.getPersonA().getUniqueId());
            Homes homesB = SpecialMixture.plugin().getHomes().getHomes(marriage.getPersonB().getUniqueId());
            Set<Home> allHomes = new HashSet<>(homesA.getPersonalHomes());
            allHomes.addAll(homesB.getPersonalHomes());
            return allHomes.size() == 0 ? "&cYou have no homes registered" : "&7Home List:\n" + CollectionHelper.enumeratedString(allHomes, false);
        }
        return homes == null || homes.size() == 0 ? "&cYou have no homes registered" : "&7Home List:\n" + CollectionHelper.enumeratedString(homes, false);
    }

    private void save() {
        CoreApi.get().getConfig().save(SpecialMixture.plugin(), "homes.data");
    }
}
