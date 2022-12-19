package fr.hyriode.pitchout.config;

import fr.hyriode.hyrame.game.waitingroom.HyriWaitingRoom;
import fr.hyriode.hyrame.utils.LocationWrapper;
import fr.hyriode.hystia.api.config.IConfig;

import java.util.List;

/**
 * Created by AstFaster
 * on 19/12/2022 at 16:12
 */
public class POConfig implements IConfig {

    private final HyriWaitingRoom.Config waitingRoom;
    private final List<LocationWrapper> spawns;

    private final int minY;

    public POConfig(HyriWaitingRoom.Config waitingRoom, List<LocationWrapper> spawns, int minY) {
        this.waitingRoom = waitingRoom;
        this.spawns = spawns;
        this.minY = minY;
    }

    public HyriWaitingRoom.Config getWaitingRoom() {
        return this.waitingRoom;
    }

    public List<LocationWrapper> getSpawns() {
        return this.spawns;
    }

    public int getMinY() {
        return this.minY;
    }

}
