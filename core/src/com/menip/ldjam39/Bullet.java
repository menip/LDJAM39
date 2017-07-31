package com.menip.ldjam39;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created on 7/30/17.
 */
public class Bullet {
    GameManager gameManager;
    Circle circle;

    Vector2 moveDirection;

    float speed;
    float damageAmount;
    boolean attackEnemy;

    public Bullet(GameManager gameManager, float x, float y, Vector2 moveDirection,
                  float speed, float damageAmount, float radius, boolean attackEnemy){
        this.gameManager = gameManager;
        circle = new Circle(x,y, radius);
        this.moveDirection = moveDirection;
        this.speed = speed;
        this.damageAmount = damageAmount;
        this.attackEnemy = attackEnemy;
    }

    void update(float delta){
        move(delta);
        checkCollision();
    }

    void move (float delta){
        Vector2 newPosition = new Vector2(
                circle.x + (speed * moveDirection.x * delta),
                circle.y + (speed * moveDirection.y * delta));
        circle.setPosition(newPosition);
    }

    void checkCollision(){
        Intersector intersector = new Intersector();
        ArrayList<Unit> toAttack = attackEnemy ? gameManager.enemyUnits : gameManager.playerUnits;

        if (intersector.overlaps(circle, gameManager.base.rectangle) && !attackEnemy) {
            gameManager.base.energy -= damageAmount;
            destruct();
            damageAmount = 0;
        }

        for (Unit unit : toAttack) {
            if (intersector.overlaps(circle, unit.rectangle)) {
                dealDamage(unit);
                destruct();
                damageAmount = 0;
            }
        }


    }

    private void dealDamage(Unit unit){
        unit.health -= damageAmount;
    }

    void destruct(){
        gameManager.attacks.remove(this);

    }
}
