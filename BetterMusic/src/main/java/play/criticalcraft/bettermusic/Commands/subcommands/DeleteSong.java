package play.criticalcraft.bettermusic.Commands.subcommands;


import org.bukkit.entity.Player;
import play.criticalcraft.bettermusic.Commands.SubCommand;
import play.criticalcraft.bettermusic.storage.TrackStorageManager;

import java.util.ArrayList;
import java.util.List;


public class DeleteSong extends SubCommand {


    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "deletes a song ";
    }

    @Override
    public String getSyntax() {
        return "/bm delete <name> ";
    }

    @Override
    public void perform(Player player, String[] args) {

        TrackStorageManager.getInstance().deleteTrack(player, args[1]);

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