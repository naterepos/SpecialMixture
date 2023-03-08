package com.github.naterepos.specialmixture.commands;

import com.github.naterepos.specialmixture.SpecialMixture;
import com.github.naterepos.specialmixture.data.marriage.Marriage;
import com.pokeland.api.core.CoreApi;
import com.pokeland.api.core.commands.CommandSpec;
import com.pokeland.api.core.commands.ExecuteCommandException;
import com.pokeland.api.core.commands.arguments.CorePlayerArgument;
import com.pokeland.api.core.data.CorePlayer;
import com.pokeland.api.core.text.Message;

import java.util.Optional;
import java.util.UUID;

public class MarriageCommands {

    public static CommandSpec getPropose() {
        return CommandSpec.spec("propose").argument("player", CorePlayerArgument.spec()).executor(data -> {
            if(data.isConsoleSource()) throw new ExecuteCommandException("&cOnly a player may use that command!");
            if(SpecialMixture.plugin().getMarriages().getMarriage(data.getSource().getUniqueId()).isPresent()) {
                throw new ExecuteCommandException("&cYou're already married!");
            }
            CorePlayer player = data.getArgument("player");
            SpecialMixture.plugin().getMarriages().propose(data.getSource().asCorePlayer(), player);
            data.sendFeedback(Message.of("&aYou've proposed to " + player.getName()));
            player.sendMessage(Message.of("&d" + data.getSource().getName() + " has asked for your hand in marriage!\n&dUse /accept to accept their proposal"));
        });
    }

    public static CommandSpec getAcceptProposal() {
        return CommandSpec.spec("accept").executor(data -> {
            if(data.isConsoleSource()) throw new ExecuteCommandException("&cOnly a player may use that command!");
            Optional<UUID> otherPlayer = SpecialMixture.plugin().getMarriages().getProposalOffer(data.getSource().asCorePlayer());
            if(otherPlayer.isEmpty()) throw new ExecuteCommandException("&cYou have not been proposed to!");
            CorePlayer other = CoreApi.get().getPlayer(otherPlayer.get());
            SpecialMixture.plugin().getMarriages().marry(data.getSource().asCorePlayer(), other);

            data.sendFeedback(Message.of("&d&lBy the power vested in me by laws of my creator, I now pronounce you married!"));
            other.sendMessage(Message.of("&d&lBy the power vested in me by laws of my creator, I now pronounce you married!"));
        });
    }

    public static CommandSpec getDivorce() {
        return CommandSpec.spec("divorce").executor(data -> {
            if(data.isConsoleSource()) throw new ExecuteCommandException("&cOnly a player may use that command!");
            Optional<Marriage> marriageOpt = SpecialMixture.plugin().getMarriages().getMarriage(data.getSource().getUniqueId());
            if(marriageOpt.isEmpty()) throw new ExecuteCommandException("&cYou are not married!");
            SpecialMixture.plugin().getMarriages().divorce(marriageOpt.get().getPersonA(), marriageOpt.get().getPersonB());

            marriageOpt.get().getPersonA().sendMessage(Message.of("&cYou are now divorced!"));
            marriageOpt.get().getPersonB().sendMessage(Message.of("&cYou are now divorced!"));
        });
    }
}
