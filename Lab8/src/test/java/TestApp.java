import com.demkom58.lab8.model.IWeight;
import com.demkom58.lab8.model.Timber;
import com.demkom58.lab8.store.ProductStore;
import com.demkom58.lab8.store.WoodDirectory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

public class TestApp {

    @Test
    public void iteratorTest() throws Exception {
        ProductStore store = new ProductStore();
        WoodDirectory directory = new WoodDirectory();

        store.add(new Timber(directory.get(1), 5f, 1f, 5f));
        store.add(new Timber(directory.get(1), 9f, 3f, 7f));
        store.add(new Timber(directory.get(2), 6f, 2f, 8f));
        store.add(new Timber(directory.get(3), 10f, 6f, 6f));

        System.out.println("Перелік виробів до вилучення: \n" + store.toString());

        final Iterator<IWeight> iterator = store.iterator();
        while (iterator.hasNext()) {
            final IWeight next = iterator.next();
            if (next.weight() > 40)
                iterator.remove();
        }

        System.out.println("Перелік виробів після вилучення: \n" + store.toString());
        Assert.assertEquals(1, store.getArr().length);
    }

    @Test
    public void listIteratorTest() throws Exception {
        ProductStore store = new ProductStore();
        WoodDirectory directory = new WoodDirectory();

        store.add(new Timber(directory.get(1), 5f, 1f, 5f));
        store.add(new Timber(directory.get(1), 9f, 3f, 7f));
        store.add(new Timber(directory.get(2), 6f, 2f, 8f));

        System.out.println("Перелік виробів до вилучення: \n" + store.toString());

        final ListIterator<IWeight> iterator = store.listIterator();
        while (iterator.hasNext()) {
            final IWeight next = iterator.next();
            if (next.weight() > 40) {
                System.out.println("Видаляємо елемент між індексами "
                        + iterator.previousIndex() + " та " + iterator.nextIndex());
                iterator.remove();
            }
        }

        System.out.println("Перелік виробів після вилучення: \n" + store.toString());

        Arrays.asList(
                new Timber(directory.get(1), 5f, 1f, 5f),
                new Timber(directory.get(2), 5f, 1f, 5f),
                new Timber(directory.get(3), 5f, 1f, 5f)
        ).forEach(iterator::add);
        System.out.println("Перелік виробів після заповнення: \n" + store.toString());

        Assert.assertEquals(4, store.getArr().length);
    }
}

