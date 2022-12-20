package fr.hyriode.pitchout.game.scoreboard;

import fr.hyriode.hyrame.game.scoreboard.HyriGameScoreboard;
import fr.hyriode.pitchout.HyriPitchOut;
import fr.hyriode.pitchout.game.POGame;
import fr.hyriode.pitchout.game.POPlayer;
import fr.hyriode.pitchout.language.POMessage;
import org.bukkit.entity.Player;

/**
 * Project: HyriPearlControl
 * Created by AstFaster
 * on 05/02/2022 at 11:22
 */
public class POScoreboard extends HyriGameScoreboard<POGame> {

    public POScoreboard(Player player) {
        super(HyriPitchOut.get(), HyriPitchOut.get().getGame(), player, "pitchout");

        this.addLines();
        this.addCurrentDateLine(0);
        this.addBlankLine(1);
        this.addBlankLine(4);
        this.addBlankLine(6);
        this.addGameTimeLine(7, POMessage.SCOREBOARD_TIME.asString(this.player));
        this.addBlankLine(8);
        this.addHostnameLine();
    }

    private void addLines() {
        final POPlayer gamePlayer = this.game.getPlayer(this.player);

        this.setLine(2, POMessage.SCOREBOARD_LIVES.asString(this.player).replace("%lives%", String.valueOf(gamePlayer.getLives())));
        this.setLine(3, POMessage.SCOREBOARD_KILLS.asString(this.player).replace("%kills%", String.valueOf(gamePlayer.getKills())));
        this.addPlayersLine(5, POMessage.SCOREBOARD_PLAYERS.asString(this.player), true);
    }

    public void update() {
        this.addLines();
        this.updateLines();
    }

}
