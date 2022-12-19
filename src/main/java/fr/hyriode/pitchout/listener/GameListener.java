package fr.hyriode.pitchout.listener;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.api.event.HyriEventHandler;
import fr.hyriode.hyrame.game.HyriGameSpectator;
import fr.hyriode.hyrame.game.HyriGameState;
import fr.hyriode.hyrame.game.event.player.HyriGameLeaveEvent;
import fr.hyriode.hyrame.game.event.player.HyriGameSpectatorEvent;
import fr.hyriode.hyrame.listener.HyriListener;
import fr.hyriode.pitchout.HyriPitchOut;
import fr.hyriode.pitchout.game.POGame;
import fr.hyriode.pitchout.game.POPlayer;
import fr.hyriode.pitchout.game.scoreboard.POSpectatorScoreboard;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by AstFaster
 * on 19/12/2022 at 16:30
 */
public class GameListener extends HyriListener<HyriPitchOut> {

    public GameListener(HyriPitchOut plugin) {
        super(plugin);

        HyriAPI.get().getEventBus().register(this);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (this.plugin.getGame().getState() != HyriGameState.PLAYING) {
            return;
        }

        final POPlayer gamePlayer = this.plugin.getGame().getPlayer(event.getPlayer());

        if (gamePlayer == null) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (this.plugin.getGame().getState() != HyriGameState.PLAYING) {
            return;
        }

        final POPlayer gamePlayer = this.plugin.getGame().getPlayer(event.getPlayer());

        if (gamePlayer == null) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (this.plugin.getGame().getState() != HyriGameState.PLAYING) {
            return;
        }

        final POPlayer gamePlayer = this.plugin.getGame().getPlayer(event.getPlayer());

        if (gamePlayer == null || gamePlayer.isSpectator()) {
            return;
        }

        if (event.getTo().getY() <= this.plugin.getConfiguration().getMinY()) { // The player is under the limit of the map
            gamePlayer.death();
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (this.plugin.getGame().getState() != HyriGameState.PLAYING) {
            return;
        }

        final Entity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        final Player player = (Player) entity;
        final POPlayer gamePlayer = this.plugin.getGame().getPlayer(player);

        if (gamePlayer == null) {
            return;
        }

        player.setHealth(player.getMaxHealth());

        event.setDamage(0);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        final POGame game = this.plugin.getGame();

        if (game.getState() != HyriGameState.PLAYING) {
            return;
        }

        final Entity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        final Player player = (Player) entity;
        final POPlayer gamePlayer = game.getPlayer(player);

        if (gamePlayer == null) {
            return;
        }

        event.setCancelled(false);
    }

    @HyriEventHandler
    public void onSpectator(HyriGameSpectatorEvent event) {
        final HyriGameSpectator spectator = event.getSpectator();
        final Player player = spectator.getPlayer();

        player.teleport(this.plugin.getConfiguration().getWaitingRoom().getSpawn().asBukkit());

        new POSpectatorScoreboard(player).show();
    }

    @HyriEventHandler
    public void onLeave(HyriGameLeaveEvent event) {
        if (event.getGame().getState() == HyriGameState.PLAYING) {
            final POPlayer gamePlayer = event.getGamePlayer().cast();

            if (!gamePlayer.isSpectator()) {
                gamePlayer.lose();
            }
        }
    }

}
