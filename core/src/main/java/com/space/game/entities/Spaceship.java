package com.space.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.space.game.SpaceGame;
import com.space.game.graphics.TextureManager;
import com.space.game.managers.BulletManager;
import com.badlogic.gdx.math.Rectangle;

public class Spaceship{
    private Texture texture;
    private int ammunitions;
    private int streak;
    private int consecutiveKills;
    private int kills;
    private BulletManager bulletManager;
    private float angle = 0;

    private float xNave, yNave;

    private float scale;
    private Vector2 position = new Vector2(0, 0);


    public Spaceship(TextureManager textureManager, BulletManager bulletManager) {

        this.texture = textureManager.getTexture("spaceship");

        scale = Math.min(SpaceGame.getGame().getWorldWidth() / (float)texture.getWidth(), SpaceGame.getGame().getWorldHeight() / (float)texture.getHeight());
        scale *= 0.075f;

        xNave = SpaceGame.getGame().getWorldWidth()/2f - texture.getWidth()*scale / 2f  ;
        yNave = SpaceGame.getGame().getWorldHeight()/2f - texture.getHeight()*scale / 2f ;
        position = new Vector2(xNave, yNave);

        this.consecutiveKills = 0;

        this.bulletManager = bulletManager;

    }

    public void update() {
        // Método update para a nave espacial
        // Por enquanto deixamos vazio, mas pode ser usado para:
        // - Atualizar animações
        // - Processar movimentos automáticos
        // - Verificar estados internos
        // - Outras atualizações relacionadas à nave
    }

    public void incrementKillCount() {
        if (this.streak != 1) {
            this.kills = this.kills + this.streak;
        } else {
            this.kills++;
        }
    }

    public int getKillCount() {
        return kills;
    }

    public void setKillCount(int kills) {
        this.kills = kills;
    }

    public void incrementStreakCount() {
        this.streak++;
    }

    public void setStreakCount(int streak) {
        this.streak = streak;
    }

    public int getStreakCount() {
        return streak;
    }

    public void incrementCosecutiveKills() {
        this.consecutiveKills++;
    }

    public void setConsecutiveKills(int consecutiveKills) {
        this.consecutiveKills = consecutiveKills;
    }

    public int getConsecutiveKills() {
        return consecutiveKills;
    }

    public void fire() {
        if (ammunitions > 0) {
            bulletManager.fireBullet(new Vector2(position.x, position.y), angle, texture.getWidth(), texture.getHeight(), scale);
            ammunitions--;
        } // adicionar som de sem munição
    }

    public float getScale() {
        return scale;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void incrementAmmunitions(int ammunitions) {
        this.ammunitions += ammunitions;
    }

    public void setAmmunitions(int ammunitions) {
        this.ammunitions = ammunitions;
    }

    public int getAmmunitions() {
        return ammunitions;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, texture.getWidth() * scale, texture.getHeight() * scale);
    }

    public void render(SpriteBatch batch) {
        // Desenha a textura da nave com a rotação e a escala aplicadas
        batch.draw( texture, 
                    position.x, position.y,                             // x e y da posição da nave
                    texture.getWidth() / 2f, texture.getHeight() / 2f,    // x e y do ponto de origem da rotação
                    texture.getWidth(), texture.getHeight(),            // largura e altura da textura
                    scale, scale,                                       // escala em x e y
                    angle, 0, 0,                              // rotação e coordenadas da textura
                    texture.getWidth(), texture.getHeight(),            // srcWidth e srcHeight (largura e altura da textura original)
                    false, false);                          // flip horizontal e vertical
    }


    public void dispose() {
        texture.dispose();
    }

}
