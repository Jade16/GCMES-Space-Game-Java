package com.space.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.space.game.SpaceGame;
import com.space.game.graphics.TextureManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Alien {
    private Texture texture;
    private TextureManager textureManager;
    private Vector2 position;
    private float speed;
    private boolean isDead = false;
    private float scale;
    private Rectangle bounds;
    private float deathTimer = Gdx.graphics.getDeltaTime();
    private boolean isMarkedForRemoval = false;
    private static final float TIME_TO_REMOVE = 2f; // Tempo em segundos antes da remoção

    private int movementPattern;
    private float elapsedTime;
    private float waveAmplitude;
    private float waveFrequency;
    private float radiusDecay;
    private float angleSpeed;
    private int signalX;
    private int signalY;

    public Alien(TextureManager textureManager, Vector2 position, float scale, int textureType, float speed, int movementPattern) {
        this.position = position;
        this.scale = scale;
        this.speed = speed;
        this.textureManager = textureManager;
        this.movementPattern = movementPattern;

        // Escolhe a textura baseada no tipo do alien
        switch (textureType) {
            case 0:
                this.texture = textureManager.getTexture("alien");
                break;
            case 1:
                this.texture = textureManager.getTexture("alien2");
                break;
            default:
                this.texture = textureManager.getTexture("alien");
                break;
        }

        float boundsPadding = 14f; // Ajuste este valor para aumentar a área de colisão
        bounds = new Rectangle(position.x - boundsPadding/2, position.y - boundsPadding/2, texture.getWidth() * scale + boundsPadding, texture.getHeight() * scale + boundsPadding);
        
        // Initialize variables for sine wave and spiral movements
        waveAmplitude = MathUtils.random(SpaceGame.getGame().getWorldHeight()/9, SpaceGame.getGame().getWorldHeight()/5); // Randomize the amplitude 
        waveFrequency = MathUtils.random(1, 5); // Randomize the frequency 

        // Calcula a direção para o centro da nave
        radiusDecay = speed; // Fator de decaimento do raio
        angleSpeed = SpaceGame.getGame().getWorldWidth() / 3840;  // Velocidade angular da espiral

        elapsedTime = MathUtils.random(0, 5); // Randomize the starting time (0 to 5 seconds)

        signalX = MathUtils.random(0, 1) == 0 ? -1 : 1;
        signalY = MathUtils.random(0, 1) == 0 ? -1 : 1;

    }

    public void setMovementPattern(int movementPattern) {
        this.movementPattern = movementPattern;
    }

    public void update(float deltaTime, Spaceship spaceship) {
        switch (movementPattern) {
            case 0:
                moveLinearly(deltaTime, spaceship);
                // criar aceleração
                speed += (deltaTime * speed/MathUtils.random(12, 16));
                break;
            case 1:
                moveInWave(deltaTime, spaceship);
                // criar aceleração
                speed += (deltaTime * speed/MathUtils.random(14, 18));
                break;
            case 2:
                moveInSpiral(deltaTime, spaceship);
                // criar aceleração
                speed += (deltaTime * speed/MathUtils.random(12, 20));
                break;
            default:
                moveLinearly(deltaTime, spaceship);
                break;
        }

        


        // Continua a lógica para morte
        if (isDead()) {
            deathTimer += deltaTime;
        }
    }

    private void moveLinearly(float deltaTime, Spaceship spaceship) {
        // Supondo que getPosition() retorna o canto inferior esquerdo
        float naveCenterX = spaceship.getPosition().x + spaceship.getBounds().width * spaceship.getScale() / 2;
        float naveCenterY = spaceship.getPosition().y + spaceship.getBounds().height * spaceship.getScale() / 2;
    
        float alienCenterX = position.x + bounds.width * scale / 2;
        float alienCenterY = position.y + bounds.height * scale / 2;
    
        // Cálculo da direção para o centro da nave
        Vector2 direction = new Vector2(naveCenterX - alienCenterX, naveCenterY - alienCenterY);
        direction.nor(); // Normaliza o vetor de direção
    
        // Aplica o movimento ao alien
        position.x += direction.x * speed * deltaTime;
        position.y += direction.y * speed * deltaTime;
    
        // Ajusta a posição do retângulo de limites
        bounds.setPosition(position.x, position.y);
    }

    private void moveInWave(float deltaTime, Spaceship spaceship) {
        // Supondo que getPosition() retorna o canto inferior esquerdo
        float naveCenterX = spaceship.getPosition().x + spaceship.getBounds().width * spaceship.getScale() / 2;
        float naveCenterY = spaceship.getPosition().y + spaceship.getBounds().height * spaceship.getScale() / 2;

        float alienCenterX = position.x + bounds.width * scale / 2;
        float alienCenterY = position.y + bounds.height * scale / 2;

        // Cálculo da direção para o centro da nave
        Vector2 direction = new Vector2(naveCenterX - alienCenterX, naveCenterY - alienCenterY);
        direction.nor(); // Normaliza o vetor de direção

        // Aplica o movimento linear ao alien na direção da nave
        position.x += direction.x * speed * deltaTime;
        position.y += direction.y * speed * deltaTime;

        // Atualiza o tempo decorrido
        elapsedTime += deltaTime;

        // Calcula a onda usando o tempo decorrido
        float waveOffset = waveAmplitude * (float)Math.sin(waveFrequency * elapsedTime) * deltaTime;

        // Adiciona a onda na direção perpendicular ao movimento linear
        Vector2 perpendicularDirection = new Vector2(-direction.y, direction.x); // Direção perpendicular
        position.x += perpendicularDirection.x * waveOffset;
        position.y += perpendicularDirection.y * waveOffset;

        // Ajusta a posição do retângulo de limites
        bounds.setPosition(position.x, position.y);
    }


    private void moveInSpiral(float deltaTime, Spaceship spaceship) {
        // Supondo que getPosition() retorna o canto inferior esquerdo
        float naveCenterX = spaceship.getPosition().x + spaceship.getBounds().width * spaceship.getScale() / 2;
        float naveCenterY = spaceship.getPosition().y + spaceship.getBounds().height * spaceship.getScale() / 2;
    
        // Atualiza o tempo decorrido
        elapsedTime += deltaTime;
    
        // Calcula o novo raio (diminuindo ao longo do tempo)
        float radius = Math.max(2, SpaceGame.getGame().getWorldHeight() - radiusDecay * elapsedTime * speed *deltaTime);
    
        // Calcula a nova posição em espiral
        float angle = angleSpeed * elapsedTime;
    
        // Atualiza a posição do alien usando as coordenadas polares convertidas para cartesianas
        position.x = naveCenterX + (signalX*radius) * (float) Math.cos(angle);
        position.y = naveCenterY + (signalY*radius) * (float) Math.sin(angle);
    
        // Ajusta a posição do retângulo de limites
        bounds.setPosition(position.x, position.y);
    }

    public void render(SpriteBatch batch) {
        if (!isMarkedForRemoval) {
            batch.draw(texture, position.x, position.y, texture.getWidth() * scale, texture.getHeight() * scale);
        }
    }

    public void setTextureToDraw(String key) {
        texture = this.textureManager.getTexture(key);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean shouldRemove() {
        return isMarkedForRemoval || (isDead && deathTimer > TIME_TO_REMOVE);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void hit() {
        if (isDead) {
            isMarkedForRemoval = true; // Marcar para remoção se já estava morto e foi atingido novamente
        } else {
            isDead = true;
            deathTimer = 0;
            setTextureToDraw("alienDead");
            setMovementPattern(0); // Padrão de movimento linear
            setSpeed(-speed/2); // Inverter a velocidade para mover para trás
        }
    }

    public void markForImmediateRemoval() {
        isMarkedForRemoval = true;
    }

    public boolean isDead() {
        return isDead || deathTimer > TIME_TO_REMOVE;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        // Resources are managed by TextureManager
    }
}
