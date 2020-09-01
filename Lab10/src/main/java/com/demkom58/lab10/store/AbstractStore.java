package com.demkom58.lab10.store;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractStore<T> implements Iterable<T>, Serializable {
    protected final String name;

    protected Object[] arr = new Object[10];
    protected int count;

    public AbstractStore(@NotNull String name) {
        this.name = name;
    }

    public boolean add(T t) {
        if (arr.length == count)
            arr = Arrays.copyOf(arr, count + count / 2);

        arr[count++] = t;
        return true;
    }

    public T get(int index) {
        if (index >= count)
            throw new IndexOutOfBoundsException(index + " element requested, when it's contain only " + count);

        return (T) arr[index];
    }

    public void remove(Predicate<T> condition) {
        final Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            if (condition.test(iterator.next())) {
                iterator.remove();
            }
        }
    }

    public void doOnlyFor(Predicate<T> condition, Consumer<T> action) {
        for (T t : this)
            if (condition.test(t))
                action.accept(t);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new StoreIterator();
    }

    @NotNull
    public ListIterator<T> listIterator() {
        return new StoreListIterator();
    }

    public Object[] getArr() {
        return Arrays.copyOf(arr, count);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(name).append(": \n");

        for (T o : this)
            builder.append(o).append("\n");

        return builder.toString();
    }

    private class StoreIterator implements Iterator<T> {
        protected int current = 0;

        @Override
        public boolean hasNext() {
            return current < count;
        }

        @Override
        public T next() {
            return (T) arr[current++];
        }

        @Override
        public void remove() {
            current--;
            count--;

            final int leftItems = count - current;
            System.arraycopy(arr, current + 1, arr, current, leftItems);
        }
    }

    private class StoreListIterator extends StoreIterator implements ListIterator<T> {

        @Override
        public boolean hasPrevious() {
            return super.current > 0;
        }

        @Override
        public T previous() {
            return (T) arr[current--];
        }

        @Override
        public int nextIndex() {
            return current + 1;
        }

        @Override
        public int previousIndex() {
            return current - 1;
        }

        @Override
        public void set(T t) {
            arr[current] = t;
        }

        @Override
        public void add(T t) {
            if (arr.length == count) {
                arr = Arrays.copyOf(arr, count + count / 2);
            }

            System.arraycopy(arr, current, arr, current + 1, count - current);
            arr[current] = t;

            count++;
            current++;
        }

    }

}
