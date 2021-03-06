package com.demkom58.lab9.test;

import com.demkom58.lab9.model.IWeight;
import com.demkom58.lab9.model.Timber;
import com.demkom58.lab9.store.ProductStore;
import com.demkom58.lab9.store.WoodDirectory;

public class TestApp {

    public static void main(String[] args) throws Exception {
        iteratorTest();
        doOnlyForTest();
    }

    public static void iteratorTest() throws Exception {
        ProductStore<IWeight> store = new ProductStore<>();
        WoodDirectory directory = new WoodDirectory();

        store.add(new Timber(directory.get(1), 5f, 1f, 5f));
        store.add(new Timber(directory.get(1), 9f, 3f, 7f));
        store.add(new Timber(directory.get(2), 6f, 2f, 8f));
        store.add(new Timber(directory.get(3), 10f, 6f, 6f));

        System.out.println("Content:");
        store.doForAll(System.out::println);
        System.out.println("After remove content:");
        store.remove(o -> o.weight() > 30);
        store.doForAll(System.out::println);
    }

    public static void doOnlyForTest() throws Exception {
        ProductStore<IWeight> store = new ProductStore<>();
        WoodDirectory directory = new WoodDirectory();

        store.add(new Timber(directory.get(1), 5f, 1f, 5f));
        store.add(new Timber(directory.get(1), 9f, 3f, 7f));
        store.add(new Timber(directory.get(2), 6f, 2f, 8f));
        store.add(new Timber(directory.get(3), 10f, 6f, 6f));

        store.doOnlyFor(o -> o.weight() > 30, System.out::println);
    }

}
