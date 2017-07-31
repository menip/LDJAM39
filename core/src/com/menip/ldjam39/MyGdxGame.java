package com.menip.ldjam39;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {
    @Override
    public void create() {
        this.setScreen(new MainScreen(this));
    }
}
