package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        AList<Integer> Ns = new AList();
        AList<Double> times = new AList();
        AList<Integer> opCounts = new AList();
        AList<Integer> timeCollection = new AList();
        int initialNumber = 1000;
        int maxNumber = 64000;
        Stopwatch sw = new Stopwatch();
        for (int i = 1; i <= initialNumber; i = i + 1){
            timeCollection.addLast(1);
            if (i == initialNumber && i <= maxNumber){
                Double theTimeNow = sw.elapsedTime();
                Ns.addLast(initialNumber);
                opCounts.addLast(initialNumber);
                times.addLast(theTimeNow);
                initialNumber = initialNumber*2;
            }
        }
        printTimingTable(Ns, times, opCounts);
    }
}
