package de.masaki.staffcrasher.commands;

import de.masaki.staffcrasher.utils.ChatUtils;
import de.masaki.staffcrasher.utils.GetStaffBans;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ShowStaffbansCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "showstaffbans";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "shows you the current latest 15min staff bans";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        final int staff_bans = GetStaffBans.get();
        ChatUtils.sendMessage("There were "+staff_bans+" in the latest 15min.");
    }
}
