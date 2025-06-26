package com.space.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;

public class SpaceGame extends ApplicationAdapter {

    private static Game game;
    private static final Logger LOGGER = new Logger(SpaceGame.class.getName(), Logger.DEBUG);

    @Override
    public void create() {
        Gdx.app.setLogLevel(Logger.DEBUG);
        // Captura o cursor para fazer ele desaparecer
        Gdx.input.setCursorCatched(true);
        SpaceGame.game = new Game();
    }
    @Override
    public void render() {
        game.render();
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }

    @Override
    public void dispose() {
        game.dispose();

    }

    public static Game getGame() {
        return game;
    }   

    public static Logger getLogger() {
        return LOGGER;
    }
    
}
