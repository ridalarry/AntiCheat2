package funkemunky.Daedalus.check.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import funkemunky.Daedalus.check.Check;
import funkemunky.Daedalus.packets.events.PacketEntityActionEvent;
import funkemunky.Daedalus.utils.Chance;
import funkemunky.Daedalus.utils.UtilTime;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Sneak extends Check {
    public static Map<UUID, Map.Entry<Integer, Long>> sneakTicks;

    public Sneak(funkemunky.Daedalus.Daedalus daedalus) {
        super("Sneak", "Sneak", daedalus);

        setEnabled(true);
        setBannable(true);
        setMaxViolations(5);

        sneakTicks = new HashMap<>();
    }

    @EventHandler
    public void onLog(PlayerQuitEvent e) {
        sneakTicks.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void EntityAction(PacketEntityActionEvent event) {
        if (event.getAction() != 1) return;
        Player player = event.getPlayer();

        if (player.hasPermission("daedalus.bypass")) return;
        if (getDaedalus().isSotwMode()) return;

        int Count = 0;
        long Time = -1L;
        if (sneakTicks.containsKey(player.getUniqueId())) {
            Count = sneakTicks.get(player.getUniqueId()).getKey();
            Time = sneakTicks.get(player.getUniqueId()).getValue();
        }
        Count++;
        if (sneakTicks.containsKey(player.getUniqueId())) {
            if (UtilTime.elapsed(Time, 100L)) {
                Count = 0;
                Time = System.currentTimeMillis();
            } else {
                Time = System.currentTimeMillis();
            }
        }
        if (Count > 50) {
            Count = 0;

            getDaedalus().logCheat(this, player, null, Chance.HIGH);
        }
        sneakTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(Count, Time));
    }
}
