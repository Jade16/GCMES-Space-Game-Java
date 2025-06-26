package com.space.game.managers;

import java.util.Iterator;
import java.util.List;

import com.space.game.entities.Alien;
import com.space.game.entities.Bullet;
import com.space.game.entities.Spaceship;

public class CollisionManager {
    private BulletManager bulletManager;
    private List<Alien> aliens;
    private Spaceship spaceship;
    private SoundManager soundManager;

    public CollisionManager(BulletManager bulletManager, AlienManager alienManager, Spaceship spaceship, SoundManager soundManager) {
        this.soundManager = soundManager;
        this.bulletManager = bulletManager;
        this.spaceship = spaceship;
        this.aliens = alienManager.getAliens();
    }

    public void checkBulletCollisions() {
        List<Bullet> bullets = bulletManager.getBullets();
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Alien> alienIterator = aliens.iterator();
            while (alienIterator.hasNext()) {
                Alien alien = alienIterator.next();
                if (alien.getBounds().overlaps(bullet.getBounds())) {
                    bullet.markForRemoval();
                    handleAlienHit(alien);
                    checkAndUpdateStreak();
                }
            }
        }
    }

    private void handleAlienHit(Alien alien) {
        if (!alien.isDead()) {
            // Se o alien não está morto, marcar como atingido pela primeira vez.
            spaceship.incrementKillCount();
            alien.hit(); // Muda a textura e inverte a direção.
            soundManager.playAlienHitSound();
            spaceship.incrementCosecutiveKills();
        } else {
            // Se já está morto e foi atingido novamente, marcar para remoção.
            alien.markForImmediateRemoval();
            soundManager.playDeadAlienHitSound();
        }
    }

    private void checkAndUpdateStreak() {
        // Se o streak não está no máximo e o jogador fez 3 kills consecutivos, incrementar streak.
        if (spaceship.getStreakCount() < 7 && spaceship.getConsecutiveKills() >= 3) {
            spaceship.incrementStreakCount();
        }
    }

    public boolean checkSpaceshipCollisions() {
        // Verifica colisões entre a spaceship e aliens
        Iterator<Alien> alienIterator = aliens.iterator();
        while (alienIterator.hasNext()) {
            Alien alien = alienIterator.next();
            if (spaceship.getBounds().overlaps(alien.getBounds())) {
                return true;
            }
        }
        return false;
    }
}
