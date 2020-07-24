package play.criticalcraft.bettermusic.Commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import play.criticalcraft.bettermusic.Commands.SubCommand;
import play.criticalcraft.bettermusic.storage.TrackStorage;
import play.criticalcraft.bettermusic.storage.TrackStorageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class AddSong extends SubCommand {


    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Adds a song to a playlist";
    }

    @Override
    public String getSyntax() {
        return "/bm add <name> <playlist/biome> <min> <sec>  <url> [time: day/night] ";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("bettermusic.bm.add")) {
            if (args.length >= 6) {
                TrackStorageManager.getInstance().insertTrack(player, args);
            }else {
                player.sendMessage("Missing arguments");
            }
        }else{
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
        if (args.length == 3) {
            List<String> biomesNames = new ArrayList<>();


            for (Biome biome : Biome.values()) {

                if (Pattern.matches("\\b(\\w*" + args[2] + "\\w*)\\b", biome.toString().toLowerCase())) {

                    biomesNames.add(biome.toString().toLowerCase());
                }
            }


            return biomesNames;
        }
        if (args.length == 4) {
            List<String> minutes = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                if (Pattern.matches("\\b(\\w*" + args[3] + "\\w*)\\b", Integer.toString(i))) {
                    minutes.add(Integer.toString(i));

                }

            }
            return minutes;
        }
        if (args.length == 5) {
            List<String> seconds = new ArrayList<>();
            for (int i = 0; i < 60; i++) {
                if (Pattern.matches("\\b(\\w*" + args[4] + "\\w*)\\b", Integer.toString(i))) {
                    seconds.add(Integer.toString(i));

                }
            }
            return seconds;
        }
        if (args.length == 6) {
            List<String> thing = new ArrayList<>();

            thing.add("<url>");
            return thing;

        } else if (args.length == 7) {
            List<String> thing = new ArrayList<>();

            if (args[6].isEmpty()) {
                thing.add("time: [day/night]");
            }
            return thing;
        }
        return null;
    }

}
