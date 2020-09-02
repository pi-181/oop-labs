import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class MultithreadingTest {
    private static final int COLLECTIONS = 10;
    private static final Random RANDOM = new Random();

    @Test
    public void task9() throws Exception {
        final Map<Integer, Collection<Integer>> map = randomMap(() -> RANDOM.nextInt(10));
        System.out.println("Map filled!");
        map.values().forEach(System.out::println);

        final ExecutorService pool = Executors.newFixedThreadPool(map.size());

        final Map<Integer, Integer> sizeMap = new ConcurrentHashMap<>();
        for (int i = 0; i < map.size(); i++)
            sizeMap.put(i, map.get(i).size());

        final int[] ints = new int[sizeMap.values().stream().mapToInt(Integer::intValue).sum()];
        System.out.println("Total integers: " + ints.length);

        final Map<Integer, Integer> startIndexMap = new HashMap<>();
        startIndexMap.put(0, 0);
        for (int i = 1; i < map.size(); i++)
            startIndexMap.put(i, sizeMap.get(i - 1) + startIndexMap.get(i - 1));

        final CountDownLatch fillLatch = new CountDownLatch(map.size());
        for (int i = 0; i < map.size(); i++) {
            final int collectionKey = i;
            final int startIndex = startIndexMap.get(i);
            pool.execute(() -> {
                final Collection<Integer> collection = map.get(collectionKey);
                final Iterator<Integer> iterator = collection.iterator();
                for (int j = 0; j < collection.size(); j++) {
                    ints[startIndex + j] = iterator.next();
                }
                fillLatch.countDown();
            });
        }

        fillLatch.await();

        System.out.println("Done!");
        System.out.println(Arrays.toString(ints));
    }

    private <T> Map<Integer, Collection<T>> randomMap(@NotNull Supplier<T> generator) {
        Map<Integer, Collection<T>> map = new HashMap<>();

        for (int i = 0; i < MultithreadingTest.COLLECTIONS; i++) {
            final List<T> collection = new ArrayList<>();
            final int size = 5 + RANDOM.nextInt(10);

            for (int j = 0; j < size; j++)
                collection.add(generator.get());

            generator.get();
            map.put(i, collection);
        }

        return map;
    }
}
