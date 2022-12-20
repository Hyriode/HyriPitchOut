package fr.hyriode.pitchout.game;

import fr.hyriode.hyrame.game.HyriGamePlayer;
import fr.hyriode.hyrame.game.protocol.HyriLastHitterProtocol;
import fr.hyriode.hyrame.item.ItemBuilder;
import fr.hyriode.hyrame.utils.BroadcastUtil;
import fr.hyriode.pitchout.HyriPitchOut;
import fr.hyriode.pitchout.game.scoreboard.POScoreboard;
import fr.hyriode.pitchout.language.POMessage;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

/**
 * Created by AstFaster
 * on 19/12/2022 at 16:15
 */
public class POPlayer extends HyriGamePlayer {

    private POScoreboard scoreboard;

    private int lives = 5;
    private int kills = 0;

    private final POGame game;

    public POPlayer(Player player) {
        super(player);
        this.game = HyriPitchOut.get().getGame();
    }

    public void start() {
        this.spawn();

        // Setup scoreboard
        this.scoreboard = new POScoreboard(this.player);
        this.scoreboard.update();

        // Set health to the remaining lives
        this.player.setMaxHealth(2.0D * this.lives);
        this.player.setGameMode(GameMode.SURVIVAL);

        // Setup inventory
        final PlayerInventory inventory = this.player.getInventory();
        final ItemStack shovel = new ItemBuilder(Material.WOOD_SPADE)
                .withEnchant(Enchantment.KNOCKBACK, 3)
                .withItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                .unbreakable()
                .build();
        final ItemStack bow = new ItemBuilder(Material.BOW)
                .withEnchant(Enchantment.ARROW_KNOCKBACK, 3)
                .withEnchant(Enchantment.ARROW_INFINITE, 1)
                .withItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                .unbreakable()
                .build();

        inventory.setItem(0, shovel);
        inventory.setItem(1, bow);
        inventory.setItem(17, new ItemStack(Material.ARROW));
    }

    public void death() {
        this.removeLife();

        if (this.lives > 0) {
            this.scoreboard.update();
            this.player.setMaxHealth(2.0D * this.lives);
            this.player.setFallDistance(0.0f);

            final POPlayer killer = this.getKiller();
            final String playerName = this.asHyriPlayer().getNameWithRank();

            if (killer == null) {
                BroadcastUtil.broadcast(target -> POMessage.PLAYER_KILL.asString(target)
                        .replace("%player%", playerName));
            } else {
                final String killerName = killer.asHyriPlayer().getNameWithRank();

                killer.addKill();

                BroadcastUtil.broadcast(target -> POMessage.PLAYER_KILL_PLAYER.asString(target)
                        .replace("%player%", playerName)
                        .replace("%killer%", killerName));
            }

            this.spawn();
        } else {
            this.lose();
        }

        this.player.playSound(this.player.getLocation(), Sound.EXPLODE, 0.8f, 2.0f);
    }

    public void lose() {
        this.scoreboard.hide();

        this.setSpectator(true);

        final POPlayer killer = this.getKiller();
        final String playerName = this.asHyriPlayer().getNameWithRank();

        if (killer == null) {
            BroadcastUtil.broadcast(target -> POMessage.PLAYER_KILL.asString(target)
                    .replace("%player%", playerName)
                    + " " + POMessage.PLAYER_FINAL_KILL.asString(target));
        } else {
            final String killerName = killer.asHyriPlayer().getNameWithRank();

            killer.addKill();

            BroadcastUtil.broadcast(target -> POMessage.PLAYER_KILL_PLAYER.asString(target)
                    .replace("%player%", playerName)
                    .replace("%killer%", killerName)
                    + " " + POMessage.PLAYER_FINAL_KILL.asString(target));
        }

        this.game.updateScoreboards();
        this.game.win(this.game.getWinner());
    }

    private POPlayer getKiller() {
        final List<HyriLastHitterProtocol.LastHitter> lastHitters = this.game.getProtocolManager().getProtocol(HyriLastHitterProtocol.class).getLastHitters(this.player);

        return lastHitters == null || lastHitters.size() == 0 ? null : lastHitters.get(0).asGamePlayer().cast();
    }

    private void spawn() {
        final Location spawn = this.game.getSpawning().newSpawn();

        this.player.teleport(spawn);
    }

    public int getLives() {
        return this.lives;
    }

    public void removeLife() {
        this.lives--;
    }

    public int getKills() {
        return this.kills;
    }

    public void addKill() {
        this.kills++;

        if (this.scoreboard != null) {
            this.scoreboard.update();
        }
    }

    public POScoreboard getScoreboard() {
        return this.scoreboard;
    }

}
