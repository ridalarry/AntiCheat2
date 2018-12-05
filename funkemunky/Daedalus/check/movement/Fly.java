package funkemunky.Daedalus.check.movement;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import funkemunky.Daedalus.check.Check;
import funkemunky.Daedalus.check.other.Latency;
import funkemunky.Daedalus.utils.Chance;
import funkemunky.Daedalus.utils.UtilCheat;
import funkemunky.Daedalus.utils.UtilMath;
import funkemunky.Daedalus.utils.UtilPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Fly extends Check {

    public static Map<UUID, Long> flyTicksA;

    public Fly(funkemunky.Daedalus.Daedalus Daedalus) {
        super("FlyA", "Fly (Type A)", Daedalus);

        this.setEnabled(true);
        this.setBannable(true);
        setMaxViolations(5);

        flyTicksA = new HashMap<>();
    }

    @EventHandler
    public void onLog(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        flyTicksA.remove(uuid);
    }

    @EventHandler
    public void CheckFlyA(PlayerMoveEvent event) {
        if (!getDaedalus().isEnabled()) return;
        Player player = event.getPlayer();

        /** False positive/optimization check **/
        if (event.isCancelled()
                || (event.getTo().getX() == event.getFrom().getX()) && (event.getTo().getZ() == event.getFrom().getZ())
                || getDaedalus().isSotwMode()
                || player.getAllowFlight()
                || player.getVehicle() != null
                || player.hasPermission("daedalus.bypass")
                || getDaedalus().getLag().getTPS() < getDaedalus().getTPSCancel()
                || UtilPlayer.isInWater(player)
                || UtilCheat.isInWeb(player)
                || Latency.getLag(player) > 92) return;

        if (UtilCheat.blocksNear(player.getLocation())) {
            flyTicksA.remove(player.getUniqueId());
            return;
        }
        if (Math.abs(event.getTo().getY() - event.getFrom().getY()) > 0.06) {
            flyTicksA.remove(player.getUniqueId());
            return;
        }

        long Time = System.currentTimeMillis();
        if (flyTicksA.containsKey(player.getUniqueId())) {
            Time = flyTicksA.get(player.getUniqueId());
        }
        long MS = System.currentTimeMillis() - Time;
        if (MS > 200L) {
            dumplog(player, "Logged Fly. MS: " + MS);
            getDaedalus().logCheat(this, player,
                    "Hovering for " + UtilMath.trim(1, (double) (MS / 1000)) + " second(s)", Chance.HIGH
            );
            flyTicksA.remove(player.getUniqueId());
            return;
        }
        flyTicksA.put(player.getUniqueId(), Time);
    }
}