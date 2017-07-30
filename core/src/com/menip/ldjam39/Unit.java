package com.menip.ldjam39;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created on 7/29/17.
 */
public class Unit {
    GameManager gameManager;

    Rectangle rectangle;
    Vector2 targetPosition;
    float health;
    float attack;
    float speed;
    float attackDelay;
    float attackTimer;
    boolean isPlayerUnit;

    float healthLossTimer;
    float healthLossDelay = .2f;
    float healthLossPerSecond;

    public Unit(GameManager gameManager, boolean isPlayerUnit, float x, float y, float health, float attack, float speed, float attackSpeed, float healthLossPerSecond, Vector2 targetPosition){
        this.gameManager = gameManager;
        this.rectangle = new Rectangle(x - Utils.UNIT_SIZE/2 ,y - Utils.UNIT_SIZE/2, Utils.UNIT_SIZE, Utils.UNIT_SIZE);
        this.health = health;
        this.attack = attack;
        this.speed = speed;
        this.attackDelay = attackSpeed;
        this.attackTimer = 0;
        this.isPlayerUnit = isPlayerUnit;
        this.healthLossPerSecond = healthLossPerSecond;
        this.healthLossTimer = gameManager.timer + healthLossDelay;
        setTargetPosition(targetPosition);
    }

    // Check if we have a target, if we are far enough away from target, and whether we will overlap if we move, then moves
    public void move(float delta){
        if (targetPosition != null) {
            if (Math.abs(targetPosition.x - rectangle.x) > 1 && Math.abs(targetPosition.y - rectangle.y) > 1) {
                Vector2 moveDirection;
                moveDirection = new Vector2(
                        this.targetPosition.x - this.rectangle.x,
                        this.targetPosition.y - this.rectangle.y);
                Utils.normalize(moveDirection);

                Vector2 futurePosition = new Vector2(
                        (this.rectangle.x + (moveDirection.x * speed * delta)),
                        (this.rectangle.y + (moveDirection.y * speed * delta)));

                    this.rectangle.setPosition(futurePosition);
            }
        }
    }


    public void setTargetPosition(Vector2 newPosition){
        // Add some random for more interestingness
        float randomness = 150;
        this.targetPosition = new Vector2(
                newPosition.x - (Utils.UNIT_SIZE/2) + (((float)Math.random() * randomness) - randomness / 2),
                newPosition.y - (Utils.UNIT_SIZE/2) + (((float)Math.random() * randomness)- randomness / 2));
    }

    void attack(float speed, float radius, boolean attackEnemy){
        if (attackTimer <= gameManager.timer){
            Vector2 attackDirection = findAttackDirection();
            if (attackDirection != null) {
                gameManager.attacks.add(new Bullet(gameManager, rectangle.x + Utils.UNIT_SIZE / 2, rectangle.y + Utils.UNIT_SIZE / 2, attackDirection, speed, attack, radius, attackEnemy));
                attackTimer = gameManager.timer + attackDelay;
            }
        }

    }

    void checkForDie(){
        if(health <= 0)
            gameManager.removeUnit(this);
    }

    void healthLoss(){
        if (healthLossTimer <= gameManager.timer){
            health -= healthLossPerSecond;
            healthLossTimer = gameManager.timer + healthLossDelay;
        }
    }

    // Returns direction to closest enemy
    Vector2 findAttackDirection(){
        ArrayList<Unit> toAttack = isPlayerUnit ? gameManager.enemyUnits : gameManager.playerUnits;
        float shortestDistance = Float.MAX_VALUE;
        Unit possibleTarget = null;
        Vector2 attackDirection = null;

        for (Unit unit : toAttack){
            float thisDistance = Utils.distance(
                    new Vector2(rectangle.x, rectangle.y),
                    new Vector2(unit.rectangle.x, unit.rectangle.y));

            if (thisDistance < shortestDistance) {
                shortestDistance = thisDistance;
                possibleTarget = unit;
            }

        }

        if (possibleTarget != null){
            attackDirection  = new Vector2(
                    possibleTarget.rectangle.x - rectangle.x,
                    possibleTarget.rectangle.y - rectangle.y);
            Utils.normalize(attackDirection);
        } else if (!isPlayerUnit){
            attackDirection = new Vector2(
                    gameManager.base.rectangle.x + Utils.BASE_SIZE/2 - rectangle.x,
                    gameManager.base.rectangle.y + Utils.BASE_SIZE/2 - rectangle.y);
            Utils.normalize(attackDirection);

        }

        return attackDirection;
    }
}
