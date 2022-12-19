package fr.hyriode.pitchout.game;

import fr.hyriode.hyrame.game.waitingroom.HyriWaitingRoom;
import fr.hyriode.pitchout.HyriPitchOut;
import org.bukkit.Material;

/**
 * Created by AstFaster
 * on 23/07/2022 at 12:54
 */
public class POWaitingRoom extends HyriWaitingRoom {

    public POWaitingRoom(POGame game) {
        super(game, Material.WOOD_SPADE, HyriPitchOut.get().getConfiguration().getWaitingRoom());
    }

}
