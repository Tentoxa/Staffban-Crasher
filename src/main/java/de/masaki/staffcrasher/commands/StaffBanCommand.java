package de.masaki.staffcrasher.commands;

import de.masaki.staffcrasher.Staffcrasher;
import de.masaki.staffcrasher.utils.ChatUtils;
import de.masaki.staffcrasher.utils.ConfigManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class StaffBanCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "staffbanslimit";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "sets your staff ban limit";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length > 0){
            try{
                Staffcrasher.max_staffbans = Integer.parseInt(args[0]);
                ConfigManager.setStaffbanMax(Integer.parseInt(args[0]));
                ChatUtils.sendMessage("Successfully updated your max staff ban number to "+ Staffcrasher.max_staffbans);
            }catch (NumberFormatException exception){
                ChatUtils.sendMessage("This Number is too large");
            }
        }
    }
}
