package com.space.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.space.game.managers.GameStateManager;
import com.space.game.managers.GameStateManager.State;
import com.space.game.managers.MapManager;
import com.space.game.managers.SoundManager;
import com.space.game.managers.UIManager;
import com.space.game.Game;

public class PlayingState implements GameStateInterface {

    private UIManager uiManager;
    private MapManager mapManager;
    private SoundManager soundManager;

    public PlayingState(Game game, GameStateManager gsm, UIManager uiManager, SoundManager soundManager) {
        this.uiManager = uiManager;
        this.mapManager = game.getMapManager();
        this.soundManager = soundManager;

    }

    @Override
    public void enter() {
        if (this.mapManager.getSpaceship() == null){
            System.out.println("Loading level 1");
            this.mapManager.loadLevel(1);
        }
    }

    @Override
    public void update(SpriteBatch batch) {
        mapManager.update();
        mapManager.render(batch);
        uiManager.displayGameInfo(mapManager.getSpaceship());
    }

    @Override
    public State getState() {
        return State.PLAYING;
    }
    
}
