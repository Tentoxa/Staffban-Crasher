package de.masaki.staffcrasher;

import de.masaki.staffcrasher.commands.ShowStaffbansCommand;
import de.masaki.staffcrasher.commands.StaffBanCommand;
import de.masaki.staffcrasher.commands.ToggleCrashCommand;
import de.masaki.staffcrasher.utils.ChatUtils;
import de.masaki.staffcrasher.utils.ConfigManager;
import de.masaki.staffcrasher.utils.GetStaffBans;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod(modid = "tnttime", name = "Staffban Crasher", acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class Staffcrasher
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";

    public static int max_staffbans = 0;
    public static int current_staffbans = 1000;
    public static boolean enabled = false;
    public static boolean running = false;

    public static boolean alerted = false;

    public static boolean shouldcrash = false;
    public static ScheduledExecutorService scheduledExecutorService;

    public static File configDir;

    @SubscribeEvent
    public void onServerJoin(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        running = true;
        if(!enabled){
            ChatUtils.sendMessage("Your Staffban crasher is disabled. Enable it with /togglecrash");
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ConfigManager.saveCfg();
        if(!(ConfigManager.isCreated())){
            ConfigManager.defaultConfig();
            System.out.println("First time running mod. Creating config");
        }
        max_staffbans = ConfigManager.getStaffbanMax();
        enabled = ConfigManager.getEnabled();

    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        ClientCommandHandler.instance.registerCommand(new StaffBanCommand());
        ClientCommandHandler.instance.registerCommand(new ShowStaffbansCommand());
        ClientCommandHandler.instance.registerCommand(new ToggleCrashCommand());
        MinecraftForge.EVENT_BUS.register(this);

        enabled = ConfigManager.getEnabled();
        max_staffbans = ConfigManager.getStaffbanMax();
        current_staffbans = GetStaffBans.get();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (running && enabled) {
                current_staffbans = GetStaffBans.get();
                if(current_staffbans >= max_staffbans){
                    if(!(alerted)){
                        alerted = true;
                        ChatUtils.sendMessage(current_staffbans+" Staff bans in the latest 15min. If you don't want to crash in the next 1min type /togglecrash");
                    }else {
                        shouldcrash = true;
                    }
                }
            }
        }, 1L, 1L, TimeUnit.MINUTES);
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        if (shouldcrash) {
            throw new RuntimeException(current_staffbans+" staff bans in latest 15min. Crashing for you!");
        }
    }

    static {
        Staffcrasher.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }
}
