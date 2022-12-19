package fr.hyriode.pitchout.game.scoreboard;

import fr.hyriode.hyrame.game.scoreboard.HyriGameScoreboard;
import fr.hyriode.pitchout.HyriPitchOut;
import fr.hyriode.pitchout.game.POGame;
import fr.hyriode.pitchout.language.POMessage;
import org.bukkit.entity.Player;

/**
 * Project: HyriPearlControl
 * Created by AstFaster
 * on 05/02/2022 at 11:22
 */
public class POSpectatorScoreboard extends HyriGameScoreboard<POGame> {

    public POSpectatorScoreboard(Player player) {
        super(HyriPitchOut.get(), HyriPitchOut.get().getGame(), player, "pitchout-spec");

        this.addLines();
        this.addCurrentDateLine(0);
        this.addBlankLine(1);
        this.addBlankLine(3);
        this.addGameTimeLine(4, POMessage.SCOREBOARD_TIME.asString(this.player));
        this.addBlankLine(5);
        this.addHostnameLine();
    }

    private void addLines() {
        this.addPlayersLine(2, POMessage.SCOREBOARD_PLAYERS.asString(this.player), true);
    }

    public void update() {
        this.addLines();
        this.updateLines();
    }

}
