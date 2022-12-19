package fr.hyriode.pitchout.game;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.game.HyriGame;
import fr.hyriode.hyrame.game.HyriGameType;
import fr.hyriode.hyrame.game.protocol.HyriLastHitterProtocol;
import fr.hyriode.hyrame.game.protocol.HyriWaitingProtocol;
import fr.hyriode.hyrame.game.team.HyriGameTeam;
import fr.hyriode.hyrame.scoreboard.IScoreboardManager;
import fr.hyriode.hyrame.scoreboard.team.HyriScoreboardTeam;
import fr.hyriode.pitchout.HyriPitchOut;
import fr.hyriode.pitchout.config.POConfig;
import fr.hyriode.pitchout.game.scoreboard.POScoreboard;
import fr.hyriode.pitchout.game.scoreboard.POSpectatorScoreboard;
import fr.hyriode.pitchout.language.POMessage;
import org.bukkit.entity.Player;

/**
 * Created by AstFaster
 * on 19/12/2022 at 16:15
 */
public class POGame extends HyriGame<POPlayer> {

    private final POSpawning spawning;

    public POGame() {
        super(IHyrame.get(),
                HyriPitchOut.get(),
                HyriAPI.get().getConfig().isDevEnvironment() ? HyriAPI.get().getGameManager().createGameInfo(HyriPitchOut.ID, HyriPitchOut.NAME) : HyriAPI.get().getGameManager().getGameInfo(HyriPitchOut.ID),
                POPlayer.class,
                HyriAPI.get().getConfig().isDevEnvironment() ? POGameType.SOLO : HyriGameType.getFromData(POGameType.values()));
        this.description = POMessage.GAME_DESCRIPTION.asLang();
        this.waitingRoom = new POWaitingRoom(this);
        this.spawning = new POSpawning();
        this.usingGameTabList = false;
        this.usingTeams = false;
    }

    @Override
    public void postRegistration() {
        super.postRegistration();

        this.protocolManager.getProtocol(HyriWaitingProtocol.class).withTeamSelector(false);
    }

    @Override
    public void handleLogin(Player player) {
        super.handleLogin(player);
    }

    @Override
    public void start() {
        this.protocolManager.enableProtocol(new HyriLastHitterProtocol(this.hyrame, this.plugin, 3 * 20L));

        super.start();

        for (POPlayer gamePlayer : this.players) {
            gamePlayer.start();
        }
    }

    public void updateScoreboards() {
        final IScoreboardManager scoreboardManager = IHyrame.get().getScoreboardManager();

        scoreboardManager.getScoreboards(POScoreboard.class).forEach(POScoreboard::update);
        scoreboardManager.getScoreboards(POSpectatorScoreboard.class).forEach(POSpectatorScoreboard::update);
    }

    public HyriGameTeam getWinner() {
        HyriGameTeam winner = null;
        for (HyriGameTeam team : this.teams) {
            if (team.hasPlayersPlaying()) { // Check if team has players playing
                if (winner != null) { // If a winner has already been set, then 2 teams are playing yet
                    return null;
                }

                winner = team;
            }
        }
        return winner;
    }

    public POSpawning getSpawning() {
        return this.spawning;
    }

}
