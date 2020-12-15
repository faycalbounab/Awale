public final class Stopwatch {
    private long start = -1;
    private long cumul=0;
    private long numCumuls=0;
    private long last=0;

    public void start() {
        if (start != -1){
            // On a un probleme
        }
        start = System.nanoTime();
    }
    // Ca devient le stop
    public void stopAndAccumulate() {
        if (start == -1){
            // On a un probleme
        }
        last=(System.nanoTime() - start);
        cumul += last;
        start=-1;
        numCumuls++;
    }
    public void split(){
        if (start == -1) {
            // On a un probleme
        }
        long time=System.nanoTime();
        last=time -start;
        cumul += last;
        start=time;
        numCumuls++;
    }
    public void reinitialize(){
        start=-1;
        last=cumul=numCumuls=0;
    }

    public long readElapsedNanoSeconds(){ return System.nanoTime()-start; }

    public long lastElapsedNanoSeconds() { return last; }
    public long lastElapsedMilliSeconds() { return (last)/1000000; }

    public long elapsedNanoSeconds() { return cumul; }
    public long elapsedMilliSeconds() { return (cumul)/1000000; }
    public double millionOperationPerSecond(long numOp) { // million per seconds
        return (numOp / (double)(elapsedNanoSeconds())) * 1000.;
    }
    public long numberOfAccumulations(){return numCumuls;}

    public static long computeGranularityNanoSeconds(){
        long cur;
        long last=System.nanoTime();
        do {
            cur = System.nanoTime();
        } while (cur == last);
        return cur-last;
    }

}