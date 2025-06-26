package com.space.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.space.game.graphics.Background;
import com.space.game.graphics.TextureManager;
import com.space.game.managers.GameStateManager;
import com.space.game.managers.MapManager;
import com.space.game.managers.SoundManager;
import com.space.game.managers.UIManager;

public class Game {

    private SpriteBatch batch;

    private TextureManager textureManager;
    private UIManager uiManager;
    private ExtendViewport extendViewport;
    private GameStateManager gsm;
    private MapManager mapManager;
    private Background background;
    private SoundManager soundManager;

    public Game() {
        batch = new SpriteBatch();

        extendViewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        extendViewport.getCamera().position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);

        textureManager = new TextureManager();
        textureManager.loadTextures(textureManager);

        soundManager = new SoundManager();
        soundManager.loadSounds();
        soundManager.loadMusics();

        uiManager = new UIManager(this, batch);
        mapManager = new MapManager(this);

        background = new Background(textureManager, this);

        gsm = new GameStateManager(this);

    }


    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        extendViewport.apply();

        batch.begin();
        
        batch.setProjectionMatrix(extendViewport.getCamera().combined);

        background.render(batch);
        background.update();

        gsm.update(batch);

        batch.end();
        batch.setShader(null);
    }

    public void resize(int width, int height) {
        extendViewport.update(width, height);
        extendViewport.getCamera().position.set(getWorldWidth() / 2f, getWorldHeight() / 2f, 0);
        extendViewport.getCamera().update();
    }

    public void dispose() {
        if (mapManager != null) {
            mapManager.dispose();
        }
        batch.dispose();
        textureManager.dispose();
        background.dispose();
        soundManager.dispose();
    }

    public GameStateManager getGsm() {
        return gsm;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public UIManager getUiManager() {
        return uiManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    public float getWorldWidth(){
        return extendViewport.getWorldWidth();
    }

    public float getWorldHeight(){
        return extendViewport.getWorldHeight();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    
}
