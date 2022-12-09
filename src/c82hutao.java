package c2;

import ars.ARSystem;
import buff.Silence;
import buff.TimeStop;
import c.c00main;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import types.box;
import Main.Main;
import types.modes;

public class c82hutao extends c00main {


    float hutaopmult = 1.33f;
    float hutaos3mult = 1.5f;

    boolean ps = false;
    boolean sk3 = false;

    double sk3sec = 0;

    int spsec = 0;


    public c82hutao(Player p, Plugin pl, c00main ch) {
        super(p, pl, ch);
        this.number = 82;
        this.load();
        this.text();
        this.c = this;
        ARSystem.playSound(player, "hutao_select");
    }


    @Override
    public boolean skill1() {
        if(sk3)
        {
            this.skill("c82_burn_s1");
        }
        else {
            this.skill("c82_s1");
        }
        return true;
    }

    @Override
    public boolean skill2() {
        if(sk3 && !(spsec>0)) {
            this.skill("c82_burn_s2");
            this.cooldown[1] = 0.0f;
            this.cooldown[2] = this.cooldown[2] * 0.5f;
        }
        else if(sk3 && (spsec>0)) {
            this.skill("c82_burn_s2");
            this.cooldown[1] = 0.0f;
            this.cooldown[2] = this.cooldown[2] * 0.2f;
        }
        else {
            this.skill("c82_s2");
        }
        return true;
    }

    @Override
    public boolean skill3() {
        this.cooldown[1] = 0.0f;
        this.cooldown[2] = 0.0f;
        sk3=true;
        sk3sec=180;
        this.player.setHealth(this.player.getHealth()*0.7);

        this.skill("c82_s3");
        return true;
    }

    @Override
    public boolean skill4() {
        this.skill("c82_s4");
        List<Entity> hutao4 = ARSystem.box((Entity)this.player, new Vector(8, 8, 8), box.TARGET);
        for (Entity e : hutao4) {
            if (e == this.player) continue;
            this.player.setHealth((this.player.getHealth()) + (this.player.getMaxHealth()*0.25));
        }
        return true;
    }

    @Override
    protected boolean skill9() {
        player.getWorld().playSound(player.getLocation(), "hutao_s9_1", 1, 1);
        return false;
    }


    @Override
    public boolean tick() {
        if(sk3){
            sk3sec--;
            if(sk3sec<=0)
            {
                sk3=false;
            }
        }
        if (this.tk % 20 == 0 && sk3) {

            this.scoreBoardText.add("&c [" + Main.GetText((String)"c82:sk3") + "]&f : " + sk3sec/20 + "/9");
        }
        if (this.isps && spsec>0)
        {
            spsec--;
        }
        return true;
    }

    @Override
    public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
        if (!isAttack && this.player.getHealth() - e.getDamage() <= 1.0 && skillCooldown(0)) {
            this.player.setHealth(this.player.getMaxHealth()*0.25);
            e.setDamage(0.0);
            e.setCancelled(true);
            ARSystem.giveBuff((LivingEntity)this.player, new TimeStop((LivingEntity)this.player), 40);
            ARSystem.playSoundAll("hutao_sp_1");
                this.spskillon();
                this.spskillen();
                this.cooldown[0] = this.setcooldown[0];
                this.cooldown[1] = 0.0f;
                this.cooldown[2] = 0.0f;
                this.cooldown[3] = 9.0f;
                this.cooldown[4] = 0.0f;
                sk3 = true;
                sk3sec = 180;
                this.skill("c82_burn_s3");
                spsec = 180;
            return false;
        }
        if (isAttack && sk3){
            ((LivingEntity) e.getEntity()).setNoDamageTicks(0);
            e.setDamage(e.getDamage() * (double) this.hutaos3mult);
        }
        if (isAttack && (this.player.getHealth() <= this.player.getMaxHealth()*0.51)){
            ((LivingEntity) e.getEntity()).setNoDamageTicks(0);
            e.setDamage(e.getDamage() * (double) this.hutaopmult);
        }
        if (isAttack && spsec>0){
            ((LivingEntity) e.getEntity()).setNoDamageTicks(0);
            e.setDamage(e.getDamage() + 0.5);
        }

//        if (!(e.getEntity() == this.player) && (this.player.getHealth() <= this.player.getMaxHealth()*0.5) && sk3 && ((LivingEntity) e.getEntity()).getNoDamageTicks() <= 0) {
//            ((LivingEntity) e.getEntity()).setNoDamageTicks(0);
//            if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
//            e.setDamage(e.getDamage() * (double) this.hutaopmult * (double) this.hutaos3mult);
//            //float damage = (float)((double)Math.round(e.getFinalDamage() * 10.0) / 10.0);
//            //this.player.sendTitle("\u00a74Case1!", "\u00a7cDamage : " + damage, 10, 10, 20);
//        }
//        else if (!(e.getEntity() == this.player) && (this.player.getHealth() <= this.player.getMaxHealth()*0.5) && !sk3 && ((LivingEntity) e.getEntity()).getNoDamageTicks() <= 0) {
//            ((LivingEntity) e.getEntity()).setNoDamageTicks(0);
//            if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
//            e.setDamage(e.getDamage() * (double) this.hutaopmult);
//            //float damage = (float)((double)Math.round(e.getFinalDamage() * 10.0) / 10.0);
//            //this.player.sendTitle("\u00a74Case2!", "\u00a7cDamage : " + damage, 10, 10, 20);
//        }
//        else if (!(e.getEntity() == this.player) && !(this.player.getHealth() <= this.player.getMaxHealth()*0.5) && sk3 && ((LivingEntity) e.getEntity()).getNoDamageTicks() <= 0) {
//            ((LivingEntity) e.getEntity()).setNoDamageTicks(0);
//            if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
//            e.setDamage(e.getDamage() * (double) this.hutaos3mult);
//            //float damage = (float)((double)Math.round(e.getFinalDamage() * 10.0) / 10.0);
//            //this.player.sendTitle("\u00a74Case3!", "\u00a7cDamage : " + damage, 10, 10, 20);
//        }
//        else if (!(e.getEntity() == this.player) && !(this.player.getHealth() <= this.player.getMaxHealth()*0.5) && !sk3 && ((LivingEntity) e.getEntity()).getNoDamageTicks() <= 0) {
//            ((LivingEntity) e.getEntity()).setNoDamageTicks(0);
//            if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
//            e.setDamage(e.getDamage() * (double) this.hutaos3mult);
//            //float damage = (float)((double)Math.round(e.getFinalDamage() * 10.0) / 10.0);
//            //this.player.sendTitle("\u00a74Case4!", "\u00a7cDamage : " + damage, 10, 10, 20);
//        }
        return true;
    }


}
