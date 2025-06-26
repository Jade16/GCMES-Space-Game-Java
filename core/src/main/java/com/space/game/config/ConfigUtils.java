package com.space.game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;

public class ConfigUtils {
    
    private ConfigUtils() {
        // Private constructor to hide the implicit public one
    }

    public static int getWindowWidth() {
        // Obter a resolução da tela usando LibGDX
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();

        // Calcular o tamanho da janela como uma porcentagem da resolução do monitor
        return (int) (displayMode.width * 0.85f);
    }

    public static int getWindowHeight() {
        return getWindowWidth() * 9 / 16;
    }

    public static float calcularFatorDeEscala() {
        // Resolução base (onde a velocidade do tiro é 800)
        float larguraBase = 1920f;
        float alturaBase = 1080f;
    
        // Obter a resolução da tela atual
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
    
        // Calcula o fator de escala como a média geométrica das razões das resoluções
        float fatorEscalaLargura = displayMode.width / larguraBase;
        float fatorEscalaAltura = displayMode.height / alturaBase;
    
        return (float) Math.sqrt(fatorEscalaLargura * fatorEscalaAltura);
    }
    
    
}
