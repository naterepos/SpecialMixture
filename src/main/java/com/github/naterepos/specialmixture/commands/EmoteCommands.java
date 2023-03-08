package com.github.naterepos.specialmixture.commands;

import com.github.naterepos.specialmixture.SpecialMixture;
import com.github.naterepos.specialmixture.data.marriage.Marriage;
import com.pokeland.api.core.commands.CommandSpec;
import com.pokeland.api.core.commands.ExecuteCommandException;
import com.pokeland.api.core.commands.arguments.CorePlayerArgument;
import com.pokeland.api.core.data.CorePlayer;
import com.pokeland.api.core.text.Message;

import java.util.Optional;

public class EmoteCommands {

    public static CommandSpec getKiss() {
        return CommandSpec.spec("kiss").argument("player", CorePlayerArgument.spec()).executor(data -> {
            if(data.isConsoleSource()) throw new ExecuteCommandException("&cOnly a player may use that command!");
            Optional<Marriage> marriage = SpecialMixture.plugin().getMarriages().getMarriage(data.getSource().getUniqueId());
            if(marriage.isEmpty()) {
                throw new ExecuteCommandException("&c&l&oNO PRE-MARITAL KISSING!");
            }
            CorePlayer player = data.getArgument("player");
            data.sendFeedback(Message.of("&dYou've kissed " + player.getName()));
            player.sendMessage(Message.of("&dYou've been kissed by " + data.getSource().getName()));
        });
    }
}
