package com.space.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.space.game.graphics.TextureManager;
import com.space.game.SpaceGame;

public class Bullet {
    private Texture texture;
    private Vector2 position;
    private float angle;
    private float scale;
    private float speed; // padrao eh 800
    private Rectangle bounds;
    private boolean shouldRemove = false;

    public Bullet(TextureManager textureManager, Vector2 spaceshipPosition, float angle, float spaceshipWidth, float spaceshipHeight, float scale) {
        this.angle = angle + 90; // Ajusta o ângulo para a direção correta
        this.scale = scale;
        this.speed = SpaceGame.getGame().getWorldWidth() / 2; // Velocidade do tiro
    
        texture = textureManager.getTexture("bullet");
        float bulletWidth = texture.getWidth();
        float bulletHeight = texture.getHeight();

        // Considera o centro da nave como o ponto de origem
        float centerX = spaceshipPosition.x + (spaceshipWidth) / 2f;
        float centerY = spaceshipPosition.y + (spaceshipHeight) / 2f;

        // Calcula o deslocamento para posicionar o tiro no bico da nave
        float offsetFromCenter = spaceshipHeight / 2f;  // Base padrão no bico superior/inferior
        float bulletOffsetX = MathUtils.cosDeg(this.angle) * offsetFromCenter;
        float bulletOffsetY = MathUtils.sinDeg(this.angle) * offsetFromCenter;
    
        // Posiciona o tiro no bico da nave considerando o ângulo
        float bulletX = centerX + bulletOffsetX - (bulletWidth / 2f);
        float bulletY = centerY + bulletOffsetY - (bulletHeight / 2f);
    
        position = new Vector2(bulletX, bulletY);
        bounds = new Rectangle(position.x, position.y, bulletWidth, bulletHeight);
    }

    public boolean shouldRemove() {
        return shouldRemove;
    }

    public void markForRemoval() {
        shouldRemove = true;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update() {
        position.x += speed * Math.cos(Math.toRadians(angle)) * Gdx.graphics.getDeltaTime();
        position.y += speed * Math.sin(Math.toRadians(angle)) * Gdx.graphics.getDeltaTime();
        bounds.setPosition(position);
    }

    public void render(SpriteBatch batch) {
        if (shouldRemove) return;

        batch.setColor(Color.WHITE); // Restaura a cor padrão
        batch.setColor(1, 1, 1, 0.77f);

        batch.draw(texture, 
        position.x, position.y, 
        texture.getWidth() / 2f, texture.getHeight() / 2f, 
        texture.getWidth(), texture.getHeight(), 
        this.scale, this.scale, 
        angle+90, 0, 0, 
        texture.getWidth(), texture.getHeight(), 
        false, false);

        batch.setColor(Color.WHITE); // Restaura a cor padrão
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        // Resources are managed by TextureManager

    }
}
