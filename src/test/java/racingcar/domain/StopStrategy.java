package racingcar.domain;

public class StopStrategy implements MovingStrategy {

    @Override
    public boolean movable() {
        return false;
    }

}
