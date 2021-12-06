package day06;

public class Lanternfish {
    int timeLeft;

    public Lanternfish(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public boolean shouldCreateNewFish(){
        if (timeLeft == 0) {
            timeLeft = 6;
            return true;
        }
        timeLeft--;
        return false;
    }
}
