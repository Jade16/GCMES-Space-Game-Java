package com.space.game.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.space.game.SpaceGame;
import com.space.game.entities.Spaceship;
import com.space.game.managers.GameStateManager.State;

public class InputManager extends InputAdapter {
    private Spaceship spaceship;
    private GameStateManager gsm;
    private boolean turningLeft;
    private boolean turningRight;

    public InputManager(GameStateManager gsm, Spaceship spaceship) {
        this.spaceship = spaceship;
        this.gsm = gsm;
        this.turningLeft = false;
        this.turningRight = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (gsm.getState() != State.PLAYING) {
            return false;
        }
        switch (keycode) {
            //virar caso o jogador pressione a seta pra esquerda
            case Keys.LEFT:
                turningLeft = true;
                break;
            //virar caso o jogador pressione a seta pra direita
            case Keys.RIGHT:
                turningRight = true;
                break;

            case Keys.A:
                turningLeft = true;
                break;
            case Keys.D:
                turningRight = true;
                break;
            case Keys.SPACE:
                if(SpaceGame.getGame().getMapManager().isWaveActive()){
                    spaceship.fire();
                }
                break;
            case Keys.P:
                if(SpaceGame.getGame().getMapManager().isWaveActive()){
                    gsm.setState(State.PAUSED);
                }
                break;
            case Keys.Q:
                SpaceGame.getGame().getSoundManager().playPreviousTrack();
                break;
            case Keys.E:
                SpaceGame.getGame().getSoundManager().playNextTrack();
                break;
            case Keys.W:
                if (SpaceGame.getGame().getSoundManager().isPlaying()) {
                    SpaceGame.getGame().getSoundManager().pauseMusic();
                } else {
                    SpaceGame.getGame().getSoundManager().resumeMusic();
                }
                break;
            default:
                System.out.println("Tecla não tratada: " + keycode);
                return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (gsm.getState() != State.PLAYING) {
            return false;
        }
        switch (keycode) {
            case Keys.LEFT:
                turningLeft = false;
                break;
            case Keys.RIGHT:
                turningRight = false;
                break;
            case Keys.A:
                turningLeft = false;
                break;
            case Keys.D:
                turningRight = false;
                break;
            default:
                System.out.println("Tecla liberada não tratada: " + keycode);
                return false;
        }
        return true;
    }

    public void update(float deltaTime) {
        if (turningLeft) {
            spaceship.setAngle(spaceship.getAngle() + 180 * deltaTime);
        }
        if (turningRight) {
            spaceship.setAngle(spaceship.getAngle() - 180 * deltaTime);
        }
    }
}