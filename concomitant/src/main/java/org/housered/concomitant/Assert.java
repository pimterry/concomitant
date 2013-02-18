package org.housered.concomitant;

public class Assert {

    public static Block verifyBlocked() {
        return null;
    }
    
    public static Tick tick(int tickNum) {
        return new Tick(tickNum);
    }
    
    public static void announce(Tick tick) {
    }
    
    public static void assertAllBlocked() {
    }
    
    public static void assertBlocked(Thread... threads) {
    }
    
}
