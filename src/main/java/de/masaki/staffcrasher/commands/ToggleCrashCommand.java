package de.masaki.staffcrasher.commands;

import de.masaki.staffcrasher.Staffcrasher;
import de.masaki.staffcrasher.utils.ChatUtils;
import de.masaki.staffcrasher.utils.ConfigManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ToggleCrashCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "togglecrash";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "toggles mod status";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Staffcrasher.enabled = !Staffcrasher.enabled;
        if(!Staffcrasher.enabled){
            Staffcrasher.alerted = false;
        }
        ConfigManager.setEnabled(Staffcrasher.enabled);
        ChatUtils.sendMessage("Staffban Crasher is now "+ Staffcrasher.enabled);
    }
}
