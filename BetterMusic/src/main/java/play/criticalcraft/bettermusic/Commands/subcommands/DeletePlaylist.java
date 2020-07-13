package play.criticalcraft.bettermusic.Commands.subcommands;

import org.bukkit.entity.Player;
import play.criticalcraft.bettermusic.Commands.SubCommand;
import play.criticalcraft.bettermusic.storage.TrackStorageManager;

import java.util.ArrayList;
import java.util.List;

public class DeletePlaylist extends SubCommand {
    @Override
    public String getName() {
        return "deletepl";
    }

    @Override
    public String getDescription() {
        return "deletes a playlist ";
    }

    @Override
    public String getSyntax() {
        return "/bm deletepl <name> ";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("bettermusic.bm.delete")) {
            if (args.length >= 2) {
                TrackStorageManager.getInstance().deletePlaylist(player, args[1]);
            } else {
                player.sendMessage("Missing arguments");
            }
        } else {
            player.sendMessage("You do not have the right permisisons!");
        }


    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 2) {

            List<String> thing = new ArrayList<>();
            if (args[1].isEmpty()) {
                thing.add("<name>");
            }
            return thing;


        }


        return null;
    }
}
