package nl.sjtek.smartmobile.libpong.ui;

import nl.sjtek.smartmobile.libpong.game.GameState;

public interface DrawUpdateListener {
    public void doDraw(GameState gameState);
}