package me.drawethree.ultraprisoncore.gangs.commands.impl;

import com.google.common.collect.ImmutableList;
import me.drawethree.ultraprisoncore.gangs.UltraPrisonGangs;
import me.drawethree.ultraprisoncore.gangs.commands.GangCommand;
import me.drawethree.ultraprisoncore.gangs.model.Gang;
import me.lucko.helper.utils.Players;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class GangAdminCommand extends GangCommand {

	public GangAdminCommand(UltraPrisonGangs plugin) {
		super(plugin, "admin");
	}

	@Override
	public String getUsage() {
		return ChatColor.RED + "/gang admin <add/remove/disband> <player> <gang>";
	}

	@Override
	public boolean execute(CommandSender sender, ImmutableList<String> args) {
		if (args.size() > 0) {
			String operation = args.get(0);
			if (operation.equalsIgnoreCase("add") && args.size() == 3) {
				Player target = Players.getNullable(args.get(1));
				Optional<Gang> gangOptional = this.plugin.getGangsManager().getGangWithName(args.get(2));
				return this.plugin.getGangsManager().forceAdd(sender, target, gangOptional);
			} else if (operation.equalsIgnoreCase("remove") && args.size() == 2) {
				Player target = Players.getNullable(args.get(1));
				return this.plugin.getGangsManager().forceRemove(sender, target);
			} else if (operation.equalsIgnoreCase("disband") && args.size() == 2) {
				Optional<Gang> gangOptional = this.plugin.getGangsManager().getGangWithName(args.get(1));
				return this.plugin.getGangsManager().forceDisband(sender, gangOptional);
			} else if (operation.equalsIgnoreCase("rename") && args.size() == 3) {
				Player target = Players.getNullable(args.get(1));
				String newName = args.get(2);
				return this.plugin.getGangsManager().forceRename(sender, target, newName);
			}
		}
		return false;
	}

	@Override
	public boolean canExecute(CommandSender sender) {
		return sender.hasPermission(UltraPrisonGangs.GANGS_ADMIN_PERM);
	}
}