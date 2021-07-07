package me.sweettie.ChatMaster;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetColor implements CommandExecutor {

    Main mian;

    public SetColor(Main main) {
        this.mian = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("this command can use only players");
            return true;
        }
        if (label.equals("getcolors")) return getColors((Player) sender);
        if (label.equals("setcolor")) return setColor((Player) sender, args);
        return false;
    }

    private boolean setColor(Player player, String[] args) {
        if (args.length != 1) return false;
        if (!mian.color_name.contains(args[0])) {
            player.sendMessage("this color is doesn't exist");
            return true;
        }
        ChatColor c = mian.color.get(mian.color_name.indexOf(args[0]));
        player.setDisplayName(c + player.getName() + ChatColor.RESET);
        mian.users.put(player.getUniqueId().toString(), args[0]);
        mian.jsonWorkd.SaveColors(mian.users);
        player.sendMessage("Color " + c + c.name() + ChatColor.WHITE + " set");
        return true;
    }

    private boolean getColors(Player player) {
        player.sendMessage(String.valueOf(mian.string));
        return true;
    }
}
