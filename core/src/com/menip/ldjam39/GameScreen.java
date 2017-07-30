package com.menip.ldjam39;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created on 7/28/17.
 */
public class GameScreen implements com.badlogic.gdx.Screen {
    Game game;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    Stage stage;
    Skin skin;

    GameManager gameManager;
    DrawWorld drawWorld;
    Vector3 touchPosition;

    Label score;

    public GameScreen (Game game){
        this.game = game;
        this.spriteBatch = new SpriteBatch();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.gameManager = new GameManager();
        this.drawWorld = new DrawWorld(gameManager, spriteBatch);
        this.touchPosition = new Vector3();
    }

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport(), this.spriteBatch);
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.internal("Skin/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        score = new Label("Score: " + Integer.toString(gameManager.score), skin);
        table.add(score).top().left();
        table.align(Align.topLeft);
    }

    @Override
    public void render(float delta) {
        if(gameManager.base.isDestroyed)
            loadGameOverScreen();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(camera.combined);

        processInputs();
        gameManager.update(delta);
        this.drawWorld.render(delta);

        score.setText("Score: " + Integer.toString(gameManager.score));
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void processInputs(){
        if (Gdx.input.justTouched()){
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                updateTargetPosition();
            }
            if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                spawnUnit();
            }
        }
    }

    private void updateTargetPosition(){
        this.touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(this.touchPosition);

        this.gameManager.setPlayerTargetPosition(new Vector2(touchPosition.x, touchPosition.y));

    }

    private void spawnUnit(){
        touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(this.touchPosition);

        this.gameManager.spawnPlayerUnit(new Vector2(touchPosition.x, touchPosition.y));
    }

    void loadGameOverScreen(){
        game.setScreen(new GameOverScreen(game, gameManager.score));
    }
}
