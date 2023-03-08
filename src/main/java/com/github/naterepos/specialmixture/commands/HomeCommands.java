package com.github.naterepos.specialmixture.commands;

import com.github.naterepos.specialmixture.SpecialMixture;
import com.github.naterepos.specialmixture.data.homes.Home;
import com.github.naterepos.specialmixture.data.homes.HomeData;
import com.github.naterepos.specialmixture.data.homes.Homes;
import com.pokeland.api.core.commands.CommandSpec;
import com.pokeland.api.core.commands.ExecuteCommandException;
import com.pokeland.api.core.commands.arguments.WordArgument;
import com.pokeland.api.core.text.Message;

import java.util.Optional;

public class HomeCommands {

    public static CommandSpec getTeleport() {
        return CommandSpec.spec("home").argument("name", WordArgument.spec()).executor(data -> {
            if(data.isConsoleSource()) throw new ExecuteCommandException("&cOnly a player may use that command!");
            HomeData homes = SpecialMixture.plugin().getHomes();
            Homes cached = homes.getHomes(data.getSource().getUniqueId());
            if(cached.isSingular()) {
                cached.getFirstHome().ifPresentOrElse(home -> {
                    home.teleport(data.getSource());
                    data.sendFeedback(Message.of("&aTeleported home!"));
                }, () -> data.sendFeedback(Message.of("&cYou currently have no homes!")));
            } else {
                cached.getHome(data.getArgument("name")).ifPresentOrElse(home -> {
                    home.teleport(data.getSource());
                    data.sendFeedback(Message.of("&aTeleported home!"));
                }, () -> data.sendFeedback(Message.of("&cThere are no homes by that name!!")));
            }
        });
    }

    public static CommandSpec getSetHome() {
        return CommandSpec.spec("sethome").argument("name", WordArgument.spec()).executor(data -> {
            if(data.isConsoleSource()) throw new ExecuteCommandException("&cOnly a player may use that command!");
            HomeData homes = SpecialMixture.plugin().getHomes();
            Homes cached = homes.getHomes(data.getSource().getUniqueId());
            cached.addHome(new Home(data.getArgument("name"), data.getSource().asCorePlayer().getLocation().orElseThrow(() -> new ExecuteCommandException("You are not online!"))));
            data.sendFeedback(Message.of("&aSet home \"" + data.getArgument("name") + "\" to your location"));
        });
    }

    public static CommandSpec getDelHome() {
        return CommandSpec.spec("delhome").argument("name", WordArgument.spec()).executor(data -> {
            if(data.isConsoleSource()) throw new ExecuteCommandException("&cOnly a player may use that command!");
            HomeData homes = SpecialMixture.plugin().getHomes();
            Homes cached = homes.getHomes(data.getSource().getUniqueId());
            Optional<Home> optHome = cached.getPersonalHome(data.getArgument("name"));
            optHome.ifPresentOrElse(home -> {
                cached.deleteHome(home);
                data.sendFeedback(Message.of("&aDeleted home \"" + data.getArgument("name") + "\""));
            }, () -> data.sendFeedback(Message.of("&cThere are no homes by that name!")));
        });
    }

    public static CommandSpec getHomeList() {
        return CommandSpec.spec("homes").executor(data -> {
            if(data.isConsoleSource()) throw new ExecuteCommandException("&cOnly a player may use that command!");
            data.sendFeedback(Message.of(SpecialMixture.plugin().getHomes().getHomes(data.getSource().getUniqueId()).toString()));
        });
    }
}
