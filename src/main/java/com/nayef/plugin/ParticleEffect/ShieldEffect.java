package com.nayef.plugin.ParticleEffect;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.*;


public final class ShieldEffect extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) throws IOException {
        Player player = e.getPlayer();
        Location location = player.getLocation();
        if (e.getAction() == Action.LEFT_CLICK_AIR) {
            drawInPlane(player);
        }
    }

    public static Vector perp(Vector onto, Vector u) {
        return u.clone().subtract(proj(onto, u));
    }

    public static Vector proj(Vector onto, Vector u) {
        return onto.clone().multiply(onto.dot(u) / onto.lengthSquared());
    }

    public void drawInPlane(Player p) {
        double twopi = 2 * Math.PI;
        double times = 1 * twopi;
        double division = twopi / 100;
        double radius = 2;
        Location c = p.getEyeLocation();
        Vector nv = c.getDirection().normalize();

        double nx = radius * nv.getX() + c.getX();
        double ny = radius * nv.getY() + c.getY();
        double nz = radius * nv.getZ()
                + c.getZ();

        Vector ya = perp(nv, new Vector(0, 1, 0)).normalize();
        Vector xa = ya.getCrossProduct(nv).normalize();

        Random random = new Random();
        int number = random.nextInt(3);
        Location location = new Location(p.getWorld(), nx, ny, nz);
        new BukkitRunnable() {
            float Radius = 1;
            int counter = 0;
            @Override
            public void run() {
                if (number == 1) {
                    for (double angle = 0; angle <= Math.PI * 2; angle += Math.PI / 8) {
                        double xb = Math.cos(angle); //calculate x coordinate
                        double yb = Math.sin(angle);

                        double xi = xa.getX() * xb + ya.getX() * yb;
                        double yi = xa.getY() * xb + ya.getY() * yb;
                        double zi = xa.getZ() * xb + ya.getZ() * yb;
                        for (Radius = 1; Radius >= 0; Radius -= 0.1) {
                            double x = xi * Radius;
                            double y = yi * Radius;
                            double z = zi * Radius;
                            Location location2 = new Location(p.getWorld(), nx, ny, nz);
                            location2.add(x, y, z);
                            p.getWorld().spawnParticle(Particle.FLAME, location2, 0, 0, 0, 0);
                            location2.subtract(x, y, z);
                        }
                    }
                } else if (number == 2) {
                    Location loc = new Location(p.getWorld(), nx, ny, nz);
                    int points = 4;
                    for (double i = 0; i < points; i++) {
                        double vinkel = 360.0 / points * i;
                        double nesteVinkel = 360.0 / points * (i + 1);
                        vinkel = Math.toRadians(vinkel);
                        nesteVinkel = Math.toRadians(nesteVinkel);
                        double x = Math.cos(vinkel);
                        double z = Math.sin(vinkel);
                        double nesteX = Math.cos(nesteVinkel);
                        double nesteZ = Math.sin(nesteVinkel);
                        double differanceX = nesteX - x;
                        double differanceZ = nesteZ - z;
                        double distance = Math.sqrt((Math.pow(differanceX - x, 2) + Math.pow(differanceZ - z, 2)));
                        for (Radius = 1; Radius >= 0; Radius -= .1) {
                            for (double dist = 0; dist < (distance - 1.2); dist += .1) {
                                double distX = x + differanceX * dist;
                                double distZ = z + differanceZ * dist;
                                double xi = xa.getX() * distX + ya.getX() * distZ * Radius;
                                double yi = xa.getY() * distX + ya.getY() * distZ * Radius;
                                double zi = xa.getZ() * distX + ya.getZ() * distZ * Radius;
                                loc.add(xi, yi, zi);
                                p.getWorld().spawnParticle(Particle.FLAME, loc, 0);
                                loc.subtract(xi, yi, zi);
                            }
                        }
                    }
                } else if (number == 0) {
                    Location loc = new Location(p.getWorld(), nx, ny, nz);

                    int points = 5;

                    for (double i = 0; i < points; i++) {
                        double vinkel = 360.0 / points * i;
                        double nesteVinkel = 360.0 / points * (i + 1);
                        vinkel = Math.toRadians(vinkel);
                        nesteVinkel = Math.toRadians(nesteVinkel);
                        double x = Math.cos(vinkel);
                        double z = Math.sin(vinkel);
                        double nesteX = Math.cos(nesteVinkel);
                        double nesteZ = Math.sin(nesteVinkel);
                        double differanceX = nesteX - x;
                        double differanceZ = nesteZ - z;
                        double distance = Math.sqrt((Math.pow(differanceX - x, 2) + Math.pow(differanceZ - z, 2)));
                        for (Radius = 1; Radius >= 0; Radius -= .1) {
                            for (double dist = 0; dist < (distance - 0.8); dist += .1) {
                                double distX = x + differanceX * dist;
                                double distZ = z + differanceZ * dist;
                                double xi = xa.getX() * distX + ya.getX() * distZ * Radius;
                                double yi = xa.getY() * distX + ya.getY() * distZ * Radius;
                                double zi = xa.getZ() * distX + ya.getZ() * distZ * Radius;
                                loc.add(xi, yi, zi);
                                p.getWorld().spawnParticle(Particle.FLAME, loc, 0);
                                loc.subtract(xi, yi, zi);
                            }
                        }
                    }
                }

                ++counter;
                if (counter == 60 * 20) cancel();
            }}.runTaskTimer(this, 0, 1L);








    }


    }

