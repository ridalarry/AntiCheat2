package funkemunky.Daedalus.check.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import funkemunky.Daedalus.check.Check;
import funkemunky.Daedalus.utils.Chance;
import funkemunky.Daedalus.utils.UtilTime;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerB extends Check {

    public static Map<UUID, Map.Entry<Integer, Long>> timerTicks;

    public TimerB(funkemunky.Daedalus.Daedalus Daedalus) {
        super("TimerB", "Timer (Type B)", Daedalus);

        setViolationsToNotify(1);
        setMaxViolations(9);
        setEnabled(true);
        setBannable(false);

        timerTicks = new HashMap<>();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent e) {
        if (!getDaedalus().isEnabled()) return;
        Player player = e.getPlayer();

        if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()
                && e.getFrom().getY() == e.getTo().getY()) return;
        if (getDaedalus().isSotwMode() || player.hasPermission("daedalus.bypass")
                || Latency.getLag(player) > 500) return;
        int Count = 0;
        long Time = System.currentTimeMillis();
        if (timerTicks.containsKey(player.getUniqueId())) {
            Count = timerTicks.get(player.getUniqueId()).getKey();
            Time = timerTicks.get(player.getUniqueId()).getValue();
        }

        Count++;

        if ((timerTicks.containsKey(player.getUniqueId())) && (UtilTime.elapsed(Time, 1000L))) {
            if (Count > 35) {
                this.getDaedalus().logCheat(this, player, null, Chance.LIKELY, "Experimental");
            }
            Count = 0;
            Time = UtilTime.nowlong();
        }
        timerTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(Count, Time));
    }

}
