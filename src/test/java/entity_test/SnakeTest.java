package entity_test;

import entity.Segment;
import entity.Snake;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {

    @Test
    void initializeSnake_setsDefaultState() {
        Snake snake = new Snake();

        Segment head = snake.getHead();
        List<Segment> body = snake.getBody();

        // length = 3
        assertEquals(3, body.size() + 1);

        // initial positions
        assertEquals(1, head.getX());
        assertEquals(3, head.getY());

        assertEquals(1, body.get(0).getX());
        assertEquals(2, body.get(0).getY());

        assertEquals(1, body.get(1).getX());
        assertEquals(1, body.get(1).getY());

        // occupies
        assertTrue(snake.occupies(1, 3));
        assertTrue(snake.occupies(1, 2));
        assertTrue(snake.occupies(1, 1));
        assertFalse(snake.occupies(0, 0));
    }

    @Test
    void setDirection_doesNotAllowOppositeDirection() {
        Snake snake = new Snake(); // default RIGHT

        // Try opposite (LEFT) – should be ignored
        snake.setDirection(Snake.Direction.LEFT);
        snake.move();
        Segment head = snake.getHead();

        // Still moving RIGHT from (1,3) → (2,3)
        assertEquals(2, head.getX());
        assertEquals(3, head.getY());

        // Now change to UP – allowed
        snake.setDirection(Snake.Direction.UP);
        snake.move();
        head = snake.getHead();

        // From (2,3) moving UP → (2,2)
        assertEquals(2, head.getX());
        assertEquals(2, head.getY());
    }

    @Test
    void move_updatesAllSegmentsCorrectly() {
        Snake snake = new Snake();
        // initial: head (1,3), body (1,2), (1,1)

        snake.move();
        Segment head = snake.getHead();
        List<Segment> body = snake.getBody();

        // head moved RIGHT
        assertEquals(2, head.getX());
        assertEquals(3, head.getY());

        // body follows previous segment
        assertEquals(1, body.get(0).getX());
        assertEquals(3, body.get(0).getY());

        assertEquals(1, body.get(1).getX());
        assertEquals(2, body.get(1).getY());
    }

    @Test
    void grow_addsSegmentAtTailPosition() {
        Snake snake = new Snake();
        List<Segment> bodyBefore = snake.getBody();
        int lengthBefore = bodyBefore.size() + 1;

        Segment tailBefore = bodyBefore.get(bodyBefore.size() - 1);
        int tailX = tailBefore.getX();
        int tailY = tailBefore.getY();

        snake.grow();

        List<Segment> bodyAfter = snake.getBody();
        int lengthAfter = bodyAfter.size() + 1;

        assertEquals(lengthBefore + 1, lengthAfter);

        Segment tailAfter = bodyAfter.get(bodyAfter.size() - 1);
        assertEquals(tailX, tailAfter.getX());
        assertEquals(tailY, tailAfter.getY());
    }
}
