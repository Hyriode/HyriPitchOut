package fr.hyriode.pitchout.dev;

import fr.hyriode.hyrame.game.waitingroom.HyriWaitingRoom;
import fr.hyriode.hyrame.utils.LocationWrapper;
import fr.hyriode.pitchout.config.POConfig;

import java.util.Arrays;

/**
 * Created by AstFaster
 * on 19/12/2022 at 16:13
 */
public class DevConfig extends POConfig {

    public DevConfig() {
        super(new HyriWaitingRoom.Config(
                new LocationWrapper(0.5, 201, 0.5, 0, 0),
                new LocationWrapper(3, 205, 3),
                new LocationWrapper(-3, 196, -3),
                new LocationWrapper(-1.5, 201, 2.5, 135, 0)
        ), Arrays.asList(
                new LocationWrapper(0.5, 140, 33, 180, 0),
                new LocationWrapper(0.5, 140, -33, 0, 0),
                new LocationWrapper(-33, 140, 0, -90, 0),
                new LocationWrapper(33, 140, 0, 90, 0),
                new LocationWrapper(33, 140, 0, 90, 0),
                new LocationWrapper(25, 140, -25, 50, 0),
                new LocationWrapper(-25, 140, 25, -130, 0),
                new LocationWrapper(25, 140, 25, 130, 0),
                new LocationWrapper(-25, 140, -25, -50, 0)
        ), 135);
    }

}
