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
    private float volume_sound = 0.5f; // Volume padrão é 1.0 (máximo)
    private float volume_music = 0.25f; // Volume padrão é 1.0 (máximo)
    private Sound bulletSound;
    private Sound hitAlienSound;
    private Sound hitDeadAlienSound;
    private Music menu_music;
    private Music gameover_music;

    private List<Music> playlist;
    private int currentTrackIndex = 0;

    public void loadSounds() {
        menu_music = Gdx.audio.newMusic(Gdx.files.internal("musics/menu/Echoes of the Last Stand.mp3"));
        gameover_music = Gdx.audio.newMusic(Gdx.files.internal("musics/gameover/gameover.mp3"));

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
        playlist.get(currentTrackIndex).setVolume(volume_music);
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
        playlist.get(currentTrackIndex).setVolume(volume_music);
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
        playlist.get(currentTrackIndex).setVolume(volume_music);
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



    public void set_VolumeSound(float volume) {
        if (volume < 0.0f) {
            this.volume_sound = 0.0f;
        } else if (volume > 1.0f) {
            this.volume_sound = 1.0f;
        } else {
            this.volume_sound = volume;
        }
    }

    public float getVolumeSound() {
        return this.volume_sound;
    }

    public void set_VolumeMusic(float volume) {
        if (volume < 0.0f) {
            this.volume_music = 0.0f;
        } else if (volume > 1.0f) {
            this.volume_music = 1.0f;
        } else {
            this.volume_music = volume;
        }
    }

    public float getVolumeMusic() {
        return this.volume_music;
    }

    public void playMenuMusic() {
        if (menu_music != null && !menu_music.isPlaying()) {
            menu_music.setLooping(true);
            menu_music.setVolume(0.4f);
            menu_music.play();
        }
    }

    public void stopMenuMusic() {
        if (menu_music != null && menu_music.isPlaying()) {
            menu_music.stop();
        }
    }

    public void playGameOverMusic() {
        if (gameover_music != null && !gameover_music.isPlaying()) {
            gameover_music.setVolume(0.4f);
            gameover_music.play();
        }
    }

    public void stopGameOverMusic() {
        if (gameover_music != null && gameover_music.isPlaying()) {
            gameover_music.stop();
        }
    }

    public void playBulletSound() {
        bulletSound.play(volume_sound);
    }

    public void playAlienHitSound() {
        hitAlienSound.play(volume_sound);
    }

    public void playDeadAlienHitSound() {
        hitDeadAlienSound.play(volume_sound);
    }

    public boolean isPlaying() {
        return playlist.get(currentTrackIndex).isPlaying();
    }

    public void dispose() {
        bulletSound.dispose();
        hitAlienSound.dispose();
        hitDeadAlienSound.dispose();
        menu_music.dispose();
        if (playlist == null) return;
        else if (playlist.isEmpty()) return;
        else {
            for (Music music : playlist) {
                music.dispose();
            }

        }

    }
}
