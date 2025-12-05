package entity_test;

import entity.CollisionHandler;
import entity.Food;
import entity.Segment;
import entity.Snake;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollisionHandlerTest {

    @Test
    void check_hitsWall_whenOutOfBoundsNoWrap() {
        Snake snake = new Snake();
        CollisionHandler handler = new CollisionHandler();

        // move head outside left boundary
        snake.getHead().set_Position(-1, 3);

        CollisionHandler.Result result = handler.check(
                snake,
                null,
                null,
                false,
                10,
                10
        );

        assertEquals(CollisionHandler.Result.HIT_WALL, result);
    }

    @Test
    void check_wrapsAround_whenWrapEnabled() {
        Snake snake = new Snake();
        CollisionHandler handler = new CollisionHandler();

        // head is outside left
        snake.getHead().set_Position(-1, 3);

        CollisionHandler.Result result = handler.check(
                snake,
                null,
                null,
                true,   // wrap enabled
                10,
                10
        );

        assertEquals(CollisionHandler.Result.NONE, result);
        // wrapped to cols - 1
        assertEquals(9, snake.getHead().getX());
        assertEquals(3, snake.getHead().getY());
    }

    @Test
    void check_hitsWall_whenWallOnGrid() {
        Snake snake = new Snake();
        CollisionHandler handler = new CollisionHandler();

        boolean[][] walls = new boolean[10][10];
        // wall at initial head position (1,3)
        walls[1][3] = true;

        CollisionHandler.Result result = handler.check(
                snake,
                null,
                walls,
                false,
                10,
                10
        );

        assertEquals(CollisionHandler.Result.HIT_WALL, result);
    }

    @Test
    void check_hitsSelf_whenHeadOverlapsBody() {
        Snake snake = new Snake();
        CollisionHandler handler = new CollisionHandler();

        // make head overlap first body segment
        Segment bodySeg = snake.getBody().get(0);
        snake.getHead().set_Position(bodySeg.getX(), bodySeg.getY());

        CollisionHandler.Result result = handler.check(
                snake,
                null,
                null,
                false,
                10,
                10
        );

        assertEquals(CollisionHandler.Result.HIT_SELF, result);
    }

    @Test
    void check_ateFood_whenHeadOnFood() {
        Snake snake = new Snake();
        CollisionHandler handler = new CollisionHandler();

        // Place head at (4,4)
        snake.getHead().set_Position(4, 4);

        // Fake food that reports x=4,y=4
        Food fakeFood = new Food() {
            @Override public int getX() { return 4; }
            @Override public int getY() { return 4; }
        };

        CollisionHandler.Result result = handler.check(
                snake,
                fakeFood,
                null,
                false,
                10,
                10
        );

        assertEquals(CollisionHandler.Result.ATE_FOOD, result);
    }

    @Test
    void check_none_whenNoCollision() {
        Snake snake = new Snake();
        CollisionHandler handler = new CollisionHandler();

        // place head somewhere safe
        snake.getHead().set_Position(5, 5);

        CollisionHandler.Result result = handler.check(
                snake,
                null,
                null,
                false,
                10,
                10
        );

        assertEquals(CollisionHandler.Result.NONE, result);
    }
}
