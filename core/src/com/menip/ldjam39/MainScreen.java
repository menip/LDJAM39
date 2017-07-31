package com.menip.ldjam39;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created on 7/29/17.
 */
public class MainScreen implements Screen {
    Game game;
    Stage stage;
    Skin skin;

    public MainScreen(Game game){
        this.game = game;
    }

    /**
     * Called when this screen is no longer the current screen for a Game.
     */
    @Override
    public void hide() {

    }


    @Override
    public void resume() {

    }


    @Override
    public void pause() {

    }


    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    /**
     * Called when this screen becomes the current screen for a Game.
     */
    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.internal("Skin/uiskin.json"));


        Table table;
        TextButton textButton;

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //TODO: Use images instead of text to make her look fancy :)
        textButton = new TextButton("New Game", this.skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                loadGameScreen();
            }
        });
        table.add(textButton);

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    private void loadGameScreen(){
        this.game.setScreen(new GameScreen(game));
    }
}
