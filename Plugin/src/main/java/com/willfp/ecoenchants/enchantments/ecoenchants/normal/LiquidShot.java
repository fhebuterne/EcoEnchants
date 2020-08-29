package com.willfp.ecoenchants.enchantments.ecoenchants.normal;

import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchantBuilder;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.util.checks.EnchantChecks;
import com.willfp.ecoenchants.nms.Target;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
public class LiquidShot extends EcoEnchant {
    public LiquidShot() {
        super(
                new EcoEnchantBuilder("liquid_shot", EnchantmentType.NORMAL, new Target.Applicable[]{Target.Applicable.BOW, Target.Applicable.CROSSBOW}, 4.0)
        );
    }

    // START OF LISTENERS

    @EventHandler
    public void onLiquidShotDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow)) return;

        if (!(((Arrow) event.getDamager()).getShooter() instanceof Player)) return;

        if (!(event.getEntity() instanceof LivingEntity)) return;

        Player player = (Player) ((Arrow) event.getDamager()).getShooter();
        Arrow arrow = (Arrow) event.getDamager();
        LivingEntity victim = (LivingEntity) event.getEntity();

        if(!(victim instanceof Blaze || victim instanceof MagmaCube || victim instanceof Enderman))
            return;

        if (!EnchantChecks.arrow(arrow, this)) return;
        int level = EnchantChecks.getArrowLevel(arrow, this);

        double multiplier = this.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "multiplier");

        double damageMultiplier = (level * multiplier) + 1;

        event.setDamage(event.getDamage() * damageMultiplier);
    }
}