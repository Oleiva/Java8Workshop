import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class C_04_Streams {

    // 5 Streams

    // Stream is not a data structure, it doesn't hold data. It just holds references to the underlying stream
    // source. Streams are pipelines that handle data structures to operations.
    //
    // Streams = intermediate operations (filter, map) + terminal operation (reduce, sum)

    @Test
    public void waysOfCreatingStreams() {

        // 1. collection-based
        String[] stringArray = {"first", "second", "third", "fourth"};
        List<String> stringList = Arrays.asList(stringArray);

        Stream<String> streamFromCollection = stringList.stream();
        Stream<String> parallelStreamFromCollection = stringList.parallelStream();

        // 2. array-based
        Stream<String> streamFromArray = Arrays.stream(stringArray);
        Stream<String> parallelStreamFromArray = Arrays.stream(stringArray).parallel();

        Stream<String> streamFromStaticArrayMethod = Stream.of("first", "second", "third", "fourth");

        // 3.1 generate via supplier
        Stream<Double> randomNumberStream = Stream.generate(Math::random);
        Stream<Integer> integerStream1 = Stream.generate(new AtomicInteger()::getAndIncrement);

        // 3.2 generate via seed + operator
        Stream<Integer> integerStream2 = Stream.iterate(0, integer1 -> integer1 + 1);
    }

    @Test
    public void neverEndingStream() {
        // Supplier is a new functional interface that somehow generates values or objects.
        Supplier<Double> random = Math::random;

        // This stream has no intermediate operations and one terminal operation (println):
        Stream.generate(random).forEach(System.out::println);
    }

    @Test
    public void advancedStream() {

        // This stream has two intermediate operations and one terminal operation (println):
        System.out.println("First stream:");
        Stream.generate(Math::random).limit(3).sorted().forEach(System.out::println);

        // That is the same as above:
        System.out.println("\n2nd stream:");
        Stream.generate(Math::random).limit(3).sorted().forEach((x) -> System.out.println(x));
    }

    @Test
    public void primitiveStreams() {

        // There are special streams for some primitive types: int, long and double.
        // Streams more efficient than Stream<T> because boxing/unboxing not done.
        // For int and long there are Special range-methods:
        IntStream efficientIntStream = IntStream.range(0, 4);
        Stream<Integer> inefficientIntStream = Stream.of(0, 1, 2, 3);

        LongStream efficientLongStream = LongStream.range(0L, 4L);
        Stream<Long> inefficientLongStream = Stream.of(0L, 1L, 2L, 3L);

        DoubleStream doubleStream = DoubleStream.of(0.0d, 0.5d);
    }

    @Test
    public void regexStreams() {
        String string = "This is just a random test string!";
        Stream<String> stringStream = Pattern.compile("\\W").splitAsStream(string);
        stringStream.forEach(System.out::println);
    }

    @Test
    public void fileStreams() throws IOException {
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(new File("src/main/java").toPath());
        directoryStream.forEach(System.out::println);

        Stream<String> linesStream = Files.lines(new File("src/main/java/DeepThought.java").toPath());
        linesStream.forEach(System.out::println);
    }

    @Test
    public void streamsMultiThread() {
        List<String> stringList = Arrays.asList("first", "second", "third", "fourth");

        Stream<String> parallelStream = stringList.parallelStream();
        parallelStream.forEach(System.out::println);
    }

    @Test
    public void streamsMultiThreadPerformance() {

        List<Double> randomDoubleList = new ArrayList<>();
        for (int i = 0; i < 2000000; i++) {
            randomDoubleList.add(Math.random());
        }

        long start = System.currentTimeMillis();
        Double sumSequential = randomDoubleList.stream().reduce((aDouble, aDouble2) -> aDouble + aDouble2).get();
        long end = System.currentTimeMillis();
        long durationSequential = end - start;
        System.out.println("Sequential calculated sum = " + sumSequential);
        System.out.println("Calculated in " + durationSequential + "ms");

        // QUESTION: Why can we use the list of random doubles here again - shouldn't it be manipulated by the
        // operations above?

        start = System.currentTimeMillis();
        Double sumParallel = randomDoubleList.parallelStream().reduce((aDouble, aDouble2) -> aDouble + aDouble2).get();
        end = System.currentTimeMillis();
        long durationParallel = end - start;
        System.out.println("Parallel calculated sum = " + sumParallel);
        System.out.println("Calculated in " + durationParallel + "ms");

        // ANSWER from question above: Input parameters of streams are not changed by stream operations. The list
        // is just the same so we can use it again.

        // TODO values from both calculations are not the same!
        // TODO 9 million operations are nearly equal in time - 3 is very different. Why?
    }

    @Test
    public void splittableRandom() {
        // New class for creating random numbers, that additionally supports streams. To support parallel streams,
        // numbers that are generated in parallel threads should be independent from each other. In other words:
        // this generator is not "shared" between threads, it's "splitted". Also, it's faster then Math.random(). :)

        DoubleStream randomStreamWithThreeDoubles = new SplittableRandom().doubles(3);
        DoubleStream threeRandomNumbersBetween0And100 = new SplittableRandom().doubles(3, 0, 10);
        // actually, the above is [0, 100) = including 0 and < 100
    }

    // ... and many more streams at java doc for java.util.stream
    // http://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
    // TODO Search more relevant examples from the java doc


}
