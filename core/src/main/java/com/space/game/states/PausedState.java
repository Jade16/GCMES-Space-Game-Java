package com.space.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.space.game.managers.GameStateManager;
import com.space.game.managers.GameStateManager.State;
import com.space.game.managers.MapManager;
import com.space.game.managers.SoundManager;
import com.space.game.managers.UIManager;

public class PausedState implements GameStateInterface {

    private UIManager uiManager;
    private MapManager mapManager;
    private GameStateManager gsm;
    private SoundManager soundManager;
    private boolean wasPlaying;

    public PausedState(GameStateManager gsm, MapManager mapManager, UIManager uiManager, SoundManager soundManager) {
        this.uiManager = uiManager;
        this.mapManager = mapManager;
        this.gsm = gsm;
        this.soundManager = soundManager;
    }

    @Override
    public void enter() {
        if (soundManager.isPlaying()){
            soundManager.pauseMusic();
            wasPlaying = true;
        } else {
            wasPlaying = false;
        }
    }

    @Override
    public void update(SpriteBatch batch) {
        mapManager.update();
        mapManager.render(batch);
        uiManager.displayPausedInfo(mapManager.getSpaceship());

        handleInput();
    }

    @Override
    public State getState() {
        return State.PAUSED;
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (wasPlaying) {
                soundManager.resumeMusic();
            }
            gsm.setState(State.PLAYING);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            soundManager.stopMusic();
            mapManager.dispose();
            gsm.setState(State.MENU);
        }
    }
    
}
