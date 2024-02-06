package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import bricker.utils.AddGameObjectCommand;
import bricker.utils.Services;
import danogl.GameObject;

public class AddPucksStrategy extends BasicCollisionStrategy {
    private static final int ADD_PUCKS_NUMBER = 2;

    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        for (int i = 0; i < ADD_PUCKS_NUMBER; i++) {
            var puck = new Puck(thisObject.getCenter());
            Services.getService(AddGameObjectCommand.class).add(puck);
        }
    }
}