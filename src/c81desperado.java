package c2;

import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import java.util.ArrayList;

import manager.Holo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import Main.Main;
import org.bukkit.util.Vector;
import types.modes;
import util.AMath;
import event.Skill;

public class c81desperado extends c00main {

    ArrayList<Entity> despescud = new ArrayList<Entity>();

    float mult = 1.0f;

    int critcount = 0;

    boolean spon = false;


    public c81desperado(Player p, Plugin pl, c00main ch) {
        super(p, pl, ch);
        this.number = 81;
        this.load();
        this.text();
        this.c = this;
        ARSystem.playSound(player, "desperado_db_revolver");
    }

    @Override
    public boolean skill1() {
        this.skill("c81_s1");
        return true;
    }

    @Override
    public boolean skill2() {
        this.skill("c81_s2");
        ARSystem.giveBuff((LivingEntity) this.player, new Silence((LivingEntity) this.player), 40);
        ARSystem.giveBuff((LivingEntity) this.player, new Stun((LivingEntity) this.player), 40);
        return true;
    }

    @Override
    public boolean skill3() {
        this.skill("c81_s3");
        return true;
    }

    @Override
    public boolean skill4() {
        this.skill("c81_s4");
        return true;
    }

    @Override
    protected boolean skill9() {
        player.getWorld().playSound(player.getLocation(), "desperado_sniping_fail_01", 1, 1);
        return false;
    }

    public void sp() {
        spon=true;
        ARSystem.potion((LivingEntity) this.player, 1, 300, 9);
        //ARSystem.giveBuff((LivingEntity) this.player, new Nodamage((LivingEntity) this.player), 300);
        ARSystem.giveBuff((LivingEntity) this.player, new Silence((LivingEntity) this.player), 300);
        ARSystem.playSoundAll("desperado_sj_start");
        this.skill("c81_sp");


        this.delay(new Runnable() {

            @Override
            public void run() {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tm anitext all SUBTITLE true 20 c81:t1/" + c81desperado.this.player.getName());
            }
        }, 0);
        this.delay(new Runnable() {

            @Override
            public void run() {
                if(player.getHealth()>0) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tm anitext all SUBTITLE true 20 c81:t2/" + c81desperado.this.player.getName());
                    ARSystem.playSoundAll("desperado_punisherend_02");
                }

            }
        }, 280);

        this.delay(new Runnable() {

            //private Player player;

            @Override
            public void run() {
                if(player.getHealth()>0) {
                    for (Entity e : despescud) {
                        if (e == player) continue;
                        Skill.remove((Entity) e, (Entity) c81desperado.this.player);
                    }
                }
                    spon=false;
                    critcount=0;
            }
        }, 300);



    }


    @Override
    public boolean tick() {
        if (this.tk % 20 == 0 && this.psopen) {
            this.scoreBoardText.add("&c [" + Main.GetText((String) "c81:sk0") + "] : " + this.critcount + " / 25");
        }
        if (this.critcount >= 25 && skillCooldown(0)) {
            this.spskillen();
            this.spskillon();
            this.sp();
            this.cooldown[0] = this.setcooldown[0];
            //ARSystem.playSound((Entity) this.player, "c81sp");
        }

            return true;
    }

    @Override
    public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
        if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
        if (isAttack && !(e.getEntity() == this.player) && AMath.random(10) < 5 && ((LivingEntity) e.getEntity()).getNoDamageTicks() <= 0) {
            ((LivingEntity) e.getEntity()).setNoDamageTicks(0);
            e.setDamage(e.getDamage() * (double) this.mult);
            ARSystem.playSoundAll("desperado_death_critical");
            Holo.create(e.getEntity().getLocation(), "§c☠", 10, new Vector(0, 1, 0));
            this.critcount++;
        }
        else if (isAttack && !(e.getEntity() == this.player) && ((LivingEntity) e.getEntity()).getNoDamageTicks() <= 0) {
            ((LivingEntity) e.getEntity()).setNoDamageTicks(0);
            e.setDamage(e.getDamage() * 0.1);
        }
        if(spon && isAttack){
            if(((LivingEntity)e.getEntity()).getHealth() < e.getDamage()+1.0) {
                e.setDamage(0.0);
                e.setCancelled(true);
                ARSystem.giveBuff((LivingEntity) e.getEntity(), new TimeStop((LivingEntity) e.getEntity()), 300);
                despescud.add((LivingEntity) e.getEntity());
            }
        }






        return true;
    }


    @Override
    public void gunSkill(LivingEntity target, String n) {
        if (target instanceof Player) {
            Player entity = (Player) target;
            if (Rule.c.get(entity) != null) {
                if (Rule.c.get(entity).number == 84) {
                    c84chisato c84 = (c84chisato) Rule.c.get(entity);
                    c84.move(player);
                }
            }
        }
    }
}
