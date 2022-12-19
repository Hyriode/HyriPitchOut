package fr.hyriode.pitchout.game;

import fr.hyriode.hyrame.utils.LocationWrapper;
import fr.hyriode.pitchout.HyriPitchOut;
import org.bukkit.Location;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by AstFaster
 * on 19/12/2022 at 16:21
 */
public class POSpawning {

    private final Queue<LocationWrapper> spawns = new LinkedBlockingQueue<>();

    public POSpawning() {
        this.spawns.addAll(HyriPitchOut.get().getConfiguration().getSpawns());
    }

    public Location newSpawn() {
        final LocationWrapper spawn = this.spawns.poll(); // Remove the head of the queue

        this.spawns.add(spawn); // Add it back to the end of the queue

        return spawn.asBukkit();
    }

}
