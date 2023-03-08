package com.github.naterepos.specialmixture;

import com.github.naterepos.specialmixture.commands.EmoteCommands;
import com.github.naterepos.specialmixture.commands.HomeCommands;
import com.github.naterepos.specialmixture.commands.MarriageCommands;
import com.github.naterepos.specialmixture.data.homes.HomeData;
import com.github.naterepos.specialmixture.data.marriage.Marriages;
import com.pokeland.api.core.Core;
import com.pokeland.api.core.CoreApi;
import com.pokeland.api.core.Plugin;
import com.pokeland.api.core.event.ReloadEvent;
import com.pokeland.api.core.event.annotations.EventListener;
import com.pokeland.api.core.event.lifecycle.PreStartupEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("specialmixture")
public class SpecialMixture implements Plugin {

    private static final Logger LOGGER = LogManager.getLogger();
    private static SpecialMixture instance;

    public SpecialMixture() {
        instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent e) -> {
            Core core = CoreApi.get();
            core.getEventBus().registerListeners(plugin());
        });
        MinecraftForge.EVENT_BUS.register(plugin());
    }

    @EventListener
    public void onPreStartup(PreStartupEvent e) {
        CoreApi.get().getPlugins().register(plugin());
        e.getPluginConfig().register(plugin(), "homes.data", HomeData.class);
        e.getPluginConfig().register(plugin(), "marriages.data", Marriages.class);
        e.getCommands().registerCommand(HomeCommands.getDelHome());
        e.getCommands().registerCommand(HomeCommands.getSetHome());
        e.getCommands().registerCommand(HomeCommands.getTeleport());
        e.getCommands().registerCommand(HomeCommands.getHomeList());
        e.getCommands().registerCommand(EmoteCommands.getKiss());
        e.getCommands().registerCommand(MarriageCommands.getAcceptProposal());
        e.getCommands().registerCommand(MarriageCommands.getDivorce());
        e.getCommands().registerCommand(MarriageCommands.getPropose());
    }

    @EventListener
    public void onReload(ReloadEvent e) {
        e.reload();
    }

    public static SpecialMixture plugin() {
        return instance;
    }

    public Marriages getMarriages() {
        return CoreApi.get().getConfig().getConfig(plugin(), "marriages.data");
    }

    public HomeData getHomes() {
        return CoreApi.get().getConfig().getConfig(plugin(), "homes.data");
    }

    @Override
    public String id() {
        return "specialmixture";
    }

    @Override
    public String name() {
        return "Special Mixture";
    }

    @Override
    public String version() {
        return "1.0.0";
    }
}
