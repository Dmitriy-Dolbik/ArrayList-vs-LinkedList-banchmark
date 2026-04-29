package dmitriy.dolbik;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static dmitriy.dolbik.util.Utils.createRandomArray;

//@BenchmarkMode({Mode.SampleTime, Mode.AverageTime})
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 3, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@State(Scope.Benchmark)
public class Get {

    private List<Integer> arrayList;
    private List<Integer> linkedList;

    @Param({"100000"})
    private int listSize;
    private int[] randomIndices;

    @Setup
    public void beforeEach() {
        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();
        randomIndices = createRandomArray(listSize, 0, listSize - 1);
        for (int i = 0; i < listSize; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
    }

    @Benchmark
    public long arrayListTest() {
        return getTest(arrayList);
    }

    @Benchmark
    public long linkedListTest() {
        return getTest(linkedList);
    }

    private long getTest(List<Integer> testingList) {
        long sum = 0;
        for (int i = 0; i < listSize; i++) {
            sum += testingList.get(randomIndices[i]);
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
