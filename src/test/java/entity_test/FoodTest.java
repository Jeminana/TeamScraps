package entity_test;

import entity.Food;
import entity.Snake;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {
    @Test
    void respawn_doesNotSpawnOnWallsOrSnake() {
        int cols = 5;
        int rows = 5;

        boolean[][] walls = new boolean[cols][rows];
        // Put a wall at (0,0) and (2,2)
        walls[0][0] = true;
        walls[2][2] = true;

        Snake snake = new Snake();
        Food food = new Food();

        food.respawn(walls, snake, cols, rows);

        int fx = food.getX();
        int fy = food.getY();

        // Food should not be on a wall
        assertFalse(walls[fx][fy], "Food spawned on a wall!");

        // Food should not be on the snake
        assertFalse(snake.occupies(fx, fy), "Food spawned on the snake!");
    }

    @Test
    void respawn_worksWithoutWalls() {
        int cols = 10;
        int rows = 10;

        boolean[][] walls = null;
        Snake snake = new Snake();
        Food food = new Food();

        food.respawn(walls, snake, cols, rows);

        int fx = food.getX();
        int fy = food.getY();

        assertTrue(fx >= 0 && fx < cols);
        assertTrue(fy >= 0 && fy < rows);
        assertFalse(snake.occupies(fx, fy));
    }
}