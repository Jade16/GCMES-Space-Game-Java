package com.space.game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;

public class ConfigUtils {

    public static int get_windowWidth() {
        // Obter a resolução da tela usando LibGDX
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        int screenWidth = displayMode.width;

        // Calcular o tamanho da janela como uma porcentagem da resolução do monitor
        int windowWidth = (int) (screenWidth * 0.85f);
        return windowWidth;
    }

    public static int get_windowHeight() {
        int windowWidth = get_windowWidth();
        int windowHeight = (int) (windowWidth * 9 / 16);
        return windowHeight;
    }

    public static float calcularFatorDeEscala() {
        // Resolução base (onde a velocidade do tiro é 800)
        float larguraBase = 1920;
        float alturaBase = 1080;
    
        // Obter a resolução da tela atual
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        float larguraAtual = displayMode.width;
        float alturaAtual = displayMode.height;
    
        // Calcula o fator de escala como a média geométrica das razões das resoluções
        float fatorEscalaLargura = larguraAtual / larguraBase;
        float fatorEscalaAltura = alturaAtual / alturaBase;
        float fatorEscala = (float) Math.sqrt(fatorEscalaLargura * fatorEscalaAltura);
    
        return fatorEscala;
    }
    
    
}
