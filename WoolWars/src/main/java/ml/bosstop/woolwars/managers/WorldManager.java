package ml.bosstop.woolwars.managers;

import ml.bosstop.woolwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class WorldManager {

    public static void createWorld(Main plugin, String name) {

        try {
            World newWorld = Bukkit.getServer().createWorld(new WorldCreator("plugins/" + plugin.getDataFolder().getName() + "/" + name));
            if(newWorld != null) {
                newWorld.setPVP(true);   
            }
        } finally {
            System.out.println(" World");
        }

    }

    public static void unloadWorld(Main plugin, String name) {

        World unload = Bukkit.getWorld("plugins/" + plugin.getDataFolder().getName() + "/" + name);

        if(unload != null) {
            Bukkit.unloadWorld(unload, false);
        }

    }

    public static void copyFileStructure(File source, File target){
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock", "advancements", "poi", "stats", "entities", "datapacks"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists())
                        if (!target.mkdirs())
                            throw new IOException("Couldn't create world directory!");
                    String[] files = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyFileStructure(srcFile, destFile);
                    }
                } else {
                    InputStream in = Files.newInputStream(source.toPath());
                    OutputStream out = Files.newOutputStream(target.toPath());
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
