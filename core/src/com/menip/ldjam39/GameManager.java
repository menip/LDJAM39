package com.menip.ldjam39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created on 7/29/17.
 */
public class GameManager {
    Vector2 playerTargetPosition;

    Base base;

    ArrayList<Unit> playerUnits;
    ArrayList<Unit> enemyUnits;
    ArrayList<Bullet> attacks;

    float timer;

    public int score = 0;
    private float scoreTimer;
    private float scoreDelay = .1f;
    private float scoreIncrease = 3;


    // Spawn stuff
    private boolean keepSpawning = true;
    private float spawnTimer;
    private float spawnDelay = 2;
    private float spawnSpeedIncreaseTimer;
    private float spawnSpeedIncreaseDelay = 1;
    private float spawnSpeedIncrease = .05f;

    // Base stuff
    private float baseTimer;
    private float baseDelay = 1;
    private float baseTimeDamage = 5;

    // Enemy stuff
    private float enemyHealth = 60;
    private float enemyAttack = 10;
    private float enemyAttackSpeed = 1f;
    private float enemySpeed = 20;
    private float energyOnKill = 75;
    private float enemyBulletSpeed = 90;

    // Player stuff
    private float playerHealth = 40;
    private float playerAttack = 15;
    private float playerAttackSpeed = 2f;
    private float playerSpeed = 80;
    private float spawnCost = 45;
    private float retargetCost = 0;
    private float playerBulletSpeed = 120;
    private float playerHealthLossPerSecond = .5f;

    public GameManager(){
        this.base = new Base(1000);
        this.playerTargetPosition = new Vector2();
        this.playerUnits = new ArrayList<Unit>();
        this.enemyUnits = new ArrayList<Unit>();
        this.attacks = new ArrayList<Bullet>();
        this.score = 0;
        this.timer = 0;
        this.baseTimer = 0;
        this.spawnTimer = 0;
        this.scoreTimer = 0;
        this.spawnSpeedIncreaseTimer = timer + spawnSpeedIncreaseDelay;
    }

    public void update(float delta){
        timer += Gdx.graphics.getRawDeltaTime();

        updateScore();
        updatePlayer(delta);
        updateEnemy(delta);
        updateAttacks(delta);
        moveUnits(delta);
    }

    private void updateScore(){
        if (scoreTimer <= timer){
            score += scoreIncrease;
            scoreTimer = timer + scoreDelay;
        }
    }

    private void updatePlayer(float delta){
        updateBase(delta);
        for(Unit unit : playerUnits) {
            unit.attack(playerBulletSpeed, Utils.BULLET_RADIUS, true);
        }
        for (int i = 0; i < playerUnits.size(); i++) {
            playerUnits.get(i).healthLoss();
            playerUnits.get(i).checkForDie();
        }
    }

    private void updateUnitTargetPosition(){
        for (Unit unit: this.playerUnits) {
            unit.setTargetPosition(this.playerTargetPosition);
        }
    }

    private void updateBase(float delta){
        if (baseTimer <= timer){
            base.energy -= baseTimeDamage;
            baseTimer = timer + baseDelay;
        }

        if (base.energy <= 0)
            gameOver();
    }

    private void updateEnemy(float delta){
        increaseSpawnSpeed();
        if (keepSpawning)
            checkEnemySpawn();
        for (Unit unit : enemyUnits) {
            unit.attack(enemyBulletSpeed, Utils.BULLET_RADIUS, false);
        }

        for (int i = 0; i < enemyUnits.size(); i++)
            enemyUnits.get(i).checkForDie();
    }

    private void updateAttacks(float delta){
        for(int i = 0; i < attacks.size(); i++)
            attacks.get(i).update(delta);
    }

    private void checkEnemySpawn(){
        if (spawnTimer <= timer){
            spawnEnemy();
            spawnTimer = timer + spawnDelay;
        }
    }

    private void increaseSpawnSpeed(){
        if (spawnSpeedIncreaseTimer <= timer){
            spawnDelay -= spawnSpeedIncrease;
            spawnSpeedIncreaseTimer = timer + spawnSpeedIncreaseDelay;
        }
    }

    private void moveUnits(float delta){
        for (Unit unit: this.playerUnits) {
            unit.move(delta);
        }
        for (Unit unit: this.enemyUnits) {
            unit.move(delta);
        }
    }

    public void setPlayerTargetPosition(Vector2 newPosition){
        this.playerTargetPosition = new Vector2(newPosition);
        base.energy -= retargetCost;
        updateUnitTargetPosition();
    }

    public void spawnPlayerUnit(Vector2 position){
        this.playerUnits.add(new Unit(this, true, position.x,position.y, playerHealth, playerAttack, playerSpeed, playerAttackSpeed, playerHealthLossPerSecond, playerTargetPosition));

        base.energy -= spawnCost;
    }

    private void spawnEnemy(){
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Vector2 spawnPosition = new Vector2(
                ((float) Math.random() * width) - (width/2),
                ((float)Math.random() * height) - (height / 2));
        Unit enemy = new Unit(this, false, spawnPosition.x, spawnPosition.y, enemyHealth, enemyAttack, enemySpeed, enemyAttackSpeed, 0, new Vector2(0,0));
        this.enemyUnits.add(enemy);
    }

    void removeUnit(Unit unit){
        if (unit.isPlayerUnit)
            playerUnits.remove(unit);
        else {
            enemyUnits.remove(unit);
            base.energy += energyOnKill;
        }
    }

    private void gameOver(){
        //TODO: tell game screen to display final score
        //TODO: stop updating score
        scoreIncrease = 0;
        keepSpawning = false;
        playerUnits.clear();
        enemyUnits.clear();
        base.isDestroyed = true;
    }
}
