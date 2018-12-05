package funkemunky.Daedalus.check.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import funkemunky.Daedalus.check.Check;
import funkemunky.Daedalus.packets.events.PacketBlockPlacementEvent;
import funkemunky.Daedalus.packets.events.PacketHeldItemChangeEvent;
import funkemunky.Daedalus.packets.events.PacketSwingArmEvent;
import funkemunky.Daedalus.utils.Chance;
import funkemunky.Daedalus.utils.UtilTime;

import java.util.*;

public class Crash extends Check {
    public static Map<UUID, Map.Entry<Integer, Long>> faggotTicks;
    public static Map<UUID, Map.Entry<Integer, Long>> faggot2Ticks;
    public static Map<UUID, Map.Entry<Integer, Long>> faggot3Ticks;
    public List<UUID> faggots;

    public Crash(funkemunky.Daedalus.Daedalus Daedalus) {
        super("Crash", "Crash", Daedalus);
        faggotTicks = new HashMap<>();
        faggot2Ticks = new HashMap<>();
        faggot3Ticks = new HashMap<>();
        faggots = new ArrayList<>();
        setMaxViolations(0);

        this.setEnabled(true);
        this.setBannable(true);
    }

    @EventHandler
    public void Swing(final PacketSwingArmEvent e) {
        final Player faggot = e.getPlayer();
        if (this.faggots.contains(faggot.getUniqueId())) {
            e.getPacketEvent().setCancelled(true);
            return;
        }
        int Count = 0;
        long Time = System.currentTimeMillis();
        if (faggotTicks.containsKey(faggot.getUniqueId())) {
            Count = faggotTicks.get(faggot.getUniqueId()).getKey();
            Time = faggotTicks.get(faggot.getUniqueId()).getValue();
        }
        ++Count;
        if (faggotTicks.containsKey(faggot.getUniqueId()) && UtilTime.elapsed(Time, 100L)) {
            Count = 0;
            Time = UtilTime.nowlong();
        }
        if (Count > 2000) {
            this.getDaedalus().logCheat(this, faggot, null, Chance.HIGH);
            this.faggots.add(faggot.getUniqueId());
        }
        faggotTicks.put(faggot.getUniqueId(), new AbstractMap.SimpleEntry<>(Count, Time));
    }

    @EventHandler
    public void Switch(final PacketHeldItemChangeEvent e) {
        final Player faggot = e.getPlayer();
        if (this.faggots.contains(faggot.getUniqueId())) {
            e.getPacketEvent().setCancelled(true);
            return;
        }
        int Count = 0;
        long Time = System.currentTimeMillis();
        if (faggot2Ticks.containsKey(faggot.getUniqueId())) {
            Count = faggot2Ticks.get(faggot.getUniqueId()).getKey();
            Time = faggot2Ticks.get(faggot.getUniqueId()).getValue();
        }
        ++Count;
        if (faggot2Ticks.containsKey(faggot.getUniqueId()) && UtilTime.elapsed(Time, 100L)) {
            Count = 0;
            Time = UtilTime.nowlong();
        }
        if (Count > 2000) {
            this.getDaedalus().logCheat(this, faggot, null, Chance.HIGH);
            this.faggots.add(faggot.getUniqueId());
        }
        faggot2Ticks.put(faggot.getUniqueId(), new AbstractMap.SimpleEntry<>(Count, Time));
    }

    @EventHandler
    public void BlockPlace(final PacketBlockPlacementEvent e) {
        final Player faggot = e.getPlayer();
        if (this.faggots.contains(faggot.getUniqueId())) {
            e.getPacketEvent().setCancelled(true);
            return;
        }
        int Count = 0;
        long Time = System.currentTimeMillis();
        if (faggot3Ticks.containsKey(faggot.getUniqueId())) {
            Count = faggot3Ticks.get(faggot.getUniqueId()).getKey();
            Time = faggot3Ticks.get(faggot.getUniqueId()).getValue();
        }
        ++Count;
        if (faggot3Ticks.containsKey(faggot.getUniqueId()) && UtilTime.elapsed(Time, 100L)) {
            Count = 0;
            Time = UtilTime.nowlong();
        }
        if (Count > 2000) {
            this.getDaedalus().logCheat(this, faggot, null, Chance.HIGH);
            this.faggots.add(faggot.getUniqueId());
        }
        faggot3Ticks.put(faggot.getUniqueId(), new AbstractMap.SimpleEntry<>(Count, Time));
    }
}