package it.benassai.horse;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RacingHorse extends Thread {
    private static final Lock mutex = new ReentrantLock();

    private final Random random = new Random();

    public final int speed = 20;
    public final int maxDeltaSpeed = 5;
    public final int waitTime = 10;
    public final int maxDeltaTime = 5;

    private String horseName;
    private int distance;
    private int currentDistance;
    private long startTime;
    private long endTime;

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getCurrentDistance() {
        return currentDistance;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public RacingHorse(String horseName, int distance) {
        this.horseName = horseName;
        this.distance = distance;
    }


    private int randomDistance() {
        return speed + random.nextInt(-maxDeltaSpeed, maxDeltaSpeed + 1);
    }

    private int randomTime() {
        return waitTime + random.nextInt(-maxDeltaTime, maxDeltaTime + 1);
    }

    @Override
    public void run() {
        try {
            startTime = System.currentTimeMillis();

            currentDistance = 0;
            while (currentDistance < distance) {
                Thread.sleep(randomTime());
                currentDistance += Math.min(randomDistance(), distance - currentDistance);
                System.out.println(horseName + " distance: " + currentDistance + "m");
            }
            
            try {
                mutex.lock();
                endTime = System.currentTimeMillis();
                HorseRace.getInstance().addToRanking(this);
            }
            finally {
                mutex.unlock();
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}