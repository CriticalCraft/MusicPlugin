package play.criticalcraft.bettermusic.Commands.subcommands;

import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import play.criticalcraft.bettermusic.BetterMusic;
import play.criticalcraft.bettermusic.Commands.SubCommand;
import play.criticalcraft.bettermusic.Listeners.PlayerBiomeTracker;
import play.criticalcraft.bettermusic.storage.Track;
import play.criticalcraft.bettermusic.storage.TrackStorageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CheckBiomeSongs extends SubCommand {
    @Override
    public String getName() {
        return "check";
    }

    @Override
    public String getDescription() {
        return "Get a list with songs";
    }

    @Override
    public String getSyntax() {
        return "/bm check [biome]";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length == 1) {
            Biome b = BetterMusic.i.playerBiomeTracker.getBiome(player);
            ArrayList<Track> tracks = TrackStorageManager.getInstance().listBiomeTracks(b.name().toLowerCase());
            for (Track t : tracks) {
                player.sendMessage(t.getName());
            }

        } else {

            ArrayList<Track> tracks = TrackStorageManager.getInstance().listBiomeTracks(args[1]);
            for (Track t : tracks) {
                player.sendMessage(t.getName());
            }
        }


    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 2) {


            List<String> biomesNames = new ArrayList<>();


            for (Biome biome : Biome.values()) {

                if (Pattern.matches("\\b(\\w*" + args[1] + "\\w*)\\b", biome.toString().toLowerCase())) {

                    biomesNames.add(biome.toString().toLowerCase());
                }
            }


            return biomesNames;
        }

        return null;
    }
}
