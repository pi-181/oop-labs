import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class StoryTest5 {
    public static void main(String... args) {
        final Furnace f = new Furnace();
        final Resource wood = new Resource("Wood", 1, 0);

        final ReentrantLock lock = new ReentrantLock();
        final Condition woodCond = lock.newCondition();
        final Condition doneCond = lock.newCondition();
        final Condition cookCond = lock.newCondition();

        final Consumer<String> tryEat = name -> {
            lock.lock();
            try {
                cookCond.signal();
                doneCond.await();
                System.out.println(name + " eats!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };
        final BiConsumer<String, Resource> simpleIncrementer = (name, res) -> {
            new Thread(() -> {
                for (int i = 0; i < res.maxValue; i++) {
                    lock.lock();
                    try {
                        res.value++;
                        System.out.println(res.name + ": " + res.value);
                    } finally {
                        lock.unlock();
                    }
                }

                tryEat.accept(name);
            }).start();
        };

        // magpie
        new Thread(() -> {
            lock.lock();

            try {
                while (!f.isEnough())
                    cookCond.await();

                System.out.println("Porridge is cooked!");
                doneCond.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        // woodcutter
        new Thread(() -> {
            for (int i = 0; i < f.fuel.maxValue; i++) {
                lock.lock();
                try {
                    while (wood.isFull())
                        woodCond.await();

                    wood.value++;
                    System.out.println(wood.name + ": " + wood.value);
                    woodCond.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

            tryEat.accept("Woodcutter");
        }).start();

        // fuel filler
        new Thread(() -> {
            final Resource fuel = f.fuel;
            for (int i = 0; i < fuel.maxValue; i++) {
                lock.lock();
                try {
                    while (wood.isEmpty())
                        woodCond.await();

                    fuel.value++;
                    System.out.println(fuel.name + ": " + fuel.value);
                    wood.value--;
                    woodCond.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

            tryEat.accept("Fuel filler");
        }).start();

        simpleIncrementer.accept("Water filler", f.water);
        simpleIncrementer.accept("Grainer", f.grain);

    }

    private static class Resource {
        private final String name;
        private final int maxValue;
        private int value;

        public Resource(String name, int maxValue, int value) {
            this.name = name;
            this.maxValue = maxValue;
            this.value = value;
        }

        public boolean isFull() {
            return value >= maxValue;
        }

        public boolean isEmpty() {
            return value == 0;
        }
    }

    private static class Furnace {
        private final Resource grain = new Resource("Grain", 2, 0);
        private final Resource water = new Resource("Water", 3, 0);
        private final Resource fuel = new Resource("Fuel", 3, 0);

        public boolean isEnough() {
            return grain.isFull() && water.isFull() && fuel.isFull();
        }
    }

}
