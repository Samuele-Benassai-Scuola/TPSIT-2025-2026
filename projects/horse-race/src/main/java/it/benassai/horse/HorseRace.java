package it.benassai.horse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HorseRace {
    private static Lock mutex = new ReentrantLock();
    private static HorseRace instance;

    public static int track = 1000;

    private List<String> names = new ArrayList<>(Arrays.asList(
        "1","2","3","4","5","6","7","8","9","10"
    ));
    private List<RacingHorse> horses = new ArrayList<>();
    private List<RacingHorse> ranking = new ArrayList<>();

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<RacingHorse> getHorses() {
        return horses;
    }

    public List<RacingHorse> getRanking() {
        return ranking;
    }

    private HorseRace() { }

    public static HorseRace getInstance() {
        try {
            mutex.lock();
            if (instance == null)
                instance = new HorseRace();
            return instance;
        }
        finally {
            mutex.unlock();
        }
    }


    private void initHorses() {
        horses.clear();
        for (String name : names) {
            horses.add(new RacingHorse(name, track));
        }
    }

    private void showRanking() {
        for (RacingHorse horse : ranking) {
            System.out.println(horse.getHorseName() + ": " + (horse.getEndTime() - horse.getStartTime()) + "ms");
        }
    }

    public void race() {
        initHorses();
        try {
            for (RacingHorse horse : horses) {
                horse.start();
            }
            for (RacingHorse horse : horses) {
                horse.join();
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        showRanking();
    }

    public void addToRanking(RacingHorse horse) {
        try {
            mutex.lock();
            ranking.add(horse);
        }
        finally {
            mutex.unlock();
        }
    }

}
