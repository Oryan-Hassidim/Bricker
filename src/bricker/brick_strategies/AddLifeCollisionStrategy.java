package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.Life;
import bricker.utils.AddGameObjectCommand;
import bricker.utils.Logger;
import bricker.utils.Services;
import danogl.GameObject;

public class AddLifeCollisionStrategy extends BasicCollisionStrategy {

    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        if (!(thisObject instanceof Brick)) {
            Services.getService(Logger.class).logError("Collision of non-Brick element");
            return;
        }
        var brick = (Brick) thisObject;
        var life = new Life(brick.getCenter());
        Services.getService(AddGameObjectCommand.class).add(life);
    }

}
