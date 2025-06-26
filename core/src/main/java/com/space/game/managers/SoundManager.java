package com.space.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.audio.Music;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoundManager {
    private float volumeSound = 0.5f; // Volume padrão é 1.0 (máximo)
    private float volumeMusic = 0.25f; // Volume padrão é 1.0 (máximo)
    private Sound bulletSound;
    private Sound hitAlienSound;
    private Sound hitDeadAlienSound;
    private Music menuMusic;
    private Music gameoverMusic;

    private List<Music> playlist;
    private int currentTrackIndex = 0;

    public void loadSounds() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("musics/menu/Echoes of the Last Stand.mp3"));
        gameoverMusic = Gdx.audio.newMusic(Gdx.files.internal("musics/gameover/gameover.mp3"));

        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Spaceshipshot.wav")); 
        hitAlienSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hitAlien.wav")); 
        hitDeadAlienSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hitDeadAlien.wav")); 
    }

    public void loadMusics() {
        // Carregar a pasta de músicas
        FileHandle musicFolder = Gdx.files.internal("musics/playing");
        FileHandle[] musicFiles = musicFolder.list();

        if (musicFiles == null || musicFiles.length == 0) {
            System.out.println("1 > No music files found in the folder.");
            return;
        } else {
            System.out.println("Found music files in the folder.");
            System.out.println(musicFiles.length);
        }

        playlist = new ArrayList<>();
        for (FileHandle file : musicFiles) {
            System.out.println("> Found music file: " + file.path());

            // Use o método file() para obter um arquivo temporário interno
            File musicFile = file.file();

            // Use Gdx.audio.newMusic com o arquivo temporário para carregar a música
            Music music = Gdx.audio.newMusic(Gdx.files.absolute(musicFile.getAbsolutePath()));

            music.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    playNextTrack();
                }
            });
            playlist.add(music);
        }

        // Embaralhar a playlist para tocar músicas aleatoriamente
        Collections.shuffle(playlist);

        if (!playlist.isEmpty()) {
            currentTrackIndex = 0;
        }
    }

    public void playNextTrack() {
        if (playlist == null) return;
        if (playlist.isEmpty()) return;

        if (playlist.get(currentTrackIndex).isPlaying()) {
            playlist.get(currentTrackIndex).stop();
        }

        currentTrackIndex = (currentTrackIndex + 1) % playlist.size();
        playlist.get(currentTrackIndex).setPosition(0);
        playlist.get(currentTrackIndex).play();
        playlist.get(currentTrackIndex).setVolume(volumeMusic);
    }

    public void playPreviousTrack() {
        if (playlist == null) return;
        if (playlist.isEmpty()) return;

        if (playlist.get(currentTrackIndex).isPlaying()) {
            playlist.get(currentTrackIndex).stop();
        }

        currentTrackIndex = (currentTrackIndex - 1 + playlist.size()) % playlist.size();
        playlist.get(currentTrackIndex).setPosition(0);
        playlist.get(currentTrackIndex).play();
        playlist.get(currentTrackIndex).setVolume(volumeMusic);
    }

    public void playMusic() {
        if (playlist == null) return;
        if (playlist.isEmpty()) return;
        if (playlist.get(currentTrackIndex).isPlaying()) {
            playlist.get(currentTrackIndex).stop();
        }
        // embaralhar a playlist
        Collections.shuffle(playlist);
        // resetar a música para o início
        playlist.get(currentTrackIndex).setPosition(0);
        playlist.get(currentTrackIndex).setLooping(false);
        playlist.get(currentTrackIndex).setVolume(volumeMusic);
        playlist.get(currentTrackIndex).play();
    }

    public void stopMusic() {
        if (playlist == null) return;
        if (!playlist.isEmpty() && playlist.get(currentTrackIndex).isPlaying()) {
            playlist.get(currentTrackIndex).stop();
        }
    }

    public void pauseMusic() {
        if (playlist == null) return;
        if (!playlist.isEmpty() && playlist.get(currentTrackIndex).isPlaying()) {
            playlist.get(currentTrackIndex).pause();
        }
    }

    public void resumeMusic() {
        if (playlist == null) return;
        if (!playlist.isEmpty() && !playlist.get(currentTrackIndex).isPlaying()) {
            playlist.get(currentTrackIndex).play();
        }
    }

    public void setVolumeSound(float volume) {
        if (volume < 0.0f) {
            this.volumeSound = 0.0f;
        } else if (volume > 1.0f) {
            this.volumeSound = 1.0f;
        } else {
            this.volumeSound = volume;
        }
    }

    public float getVolumeSound() {
        return this.volumeSound;
    }

    public void setVolumeMusic(float volume) {
        if (volume < 0.0f) {
            this.volumeMusic = 0.0f;
        } else if (volume > 1.0f) {
            this.volumeMusic = 1.0f;
        } else {
            this.volumeMusic = volume;
        }
    }

    public float getVolumeMusic() {
        return this.volumeMusic;
    }

    public void playMenuMusic() {
        if (menuMusic != null && !menuMusic.isPlaying()) {
            menuMusic.setLooping(true);
            menuMusic.setVolume(0.4f);
            menuMusic.play();
        }
    }

    public void stopMenuMusic() {
        if (menuMusic != null && menuMusic.isPlaying()) {
            menuMusic.stop();
        }
    }

    public void playGameOverMusic() {
        if (gameoverMusic != null && !gameoverMusic.isPlaying()) {
            gameoverMusic.setVolume(0.4f);
            gameoverMusic.play();
        }
    }

    public void stopGameOverMusic() {
        if (gameoverMusic != null && gameoverMusic.isPlaying()) {
            gameoverMusic.stop();
        }
    }

    public void playBulletSound() {
        bulletSound.play(volumeSound);
    }

    public void playAlienHitSound() {
        hitAlienSound.play(volumeSound);
    }

    public void playDeadAlienHitSound() {
        hitDeadAlienSound.play(volumeSound);
    }

    public boolean isPlaying() {
        return playlist.get(currentTrackIndex).isPlaying();
    }

    public void dispose() {
        bulletSound.dispose();
        hitAlienSound.dispose();
        hitDeadAlienSound.dispose();
        menuMusic.dispose();
        if (playlist == null) return;
        else if (playlist.isEmpty()) return;
        else {
            for (Music music : playlist) {
                music.dispose();
            }

        }

    }
}
