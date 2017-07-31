package com.menip.ldjam39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created on 7/29/17.
 */
public class DrawWorld {
    GameManager gameManager;
    SpriteBatch spriteBatch;

    Texture base = new Texture(Gdx.files.internal("Base.png"));
    Texture playerUnit = new Texture(Gdx.files.internal("PlayerUnit.png"));
    Texture enemyUnit = new Texture(Gdx.files.internal("Enemy.png"));
    Texture bullet = new Texture(Gdx.files.internal("Bullet1.png"));

    BitmapFont baseFont = new BitmapFont();

    public DrawWorld(GameManager gameManager, SpriteBatch spriteBatch){
        this.gameManager = gameManager;
        this.spriteBatch = spriteBatch;
    }

    public void render(float delta){
        spriteBatch.begin();

        drawAttacks(delta);
        drawEnemyUnits(delta);
        drawPlayerUnits(delta);
        drawBase(delta);

        spriteBatch.end();
    }

    private void drawBase(float delta){
        if (!gameManager.base.isDestroyed) {
            spriteBatch.draw(base, gameManager.base.rectangle.x, gameManager.base.rectangle.y, Utils.BASE_SIZE, Utils.BASE_SIZE);
            baseFont.draw(spriteBatch, Float.toString(gameManager.base.energy), 0 - Utils.BASE_SIZE / 4, 0);
        }
    }

    private void drawPlayerUnits(float delta){
        for (Unit unit : gameManager.playerUnits){
            spriteBatch.draw(playerUnit, unit.rectangle.x, unit.rectangle.y, Utils.UNIT_SIZE, Utils.UNIT_SIZE);
            baseFont.draw(spriteBatch, Float.toString(unit.health),unit.rectangle.x + Utils.UNIT_SIZE / 4, unit.rectangle.y + Utils.UNIT_SIZE );
        }
    }

    private void drawEnemyUnits(float delta){
        for (Unit unit : gameManager.enemyUnits){
            spriteBatch.draw(enemyUnit, unit.rectangle.x, unit.rectangle.y, Utils.UNIT_SIZE, Utils.UNIT_SIZE);
            baseFont.draw(spriteBatch, Float.toString(unit.health),unit.rectangle.x + Utils.UNIT_SIZE / 4, unit.rectangle.y + Utils.UNIT_SIZE);
        }
    }

    private void drawAttacks(float delta){
        for(Bullet attack : gameManager.attacks){
            spriteBatch.draw(bullet, attack.circle.x - Utils.BULLET_RADIUS, attack.circle.y - Utils.BULLET_RADIUS, Utils.BULLET_RADIUS * 2, Utils.BULLET_RADIUS * 2);
        }
    }
}
