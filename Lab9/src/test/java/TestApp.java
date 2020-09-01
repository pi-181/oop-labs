import com.demkom58.lab9.model.IWeight;
import com.demkom58.lab9.model.Timber;
import com.demkom58.lab9.store.ProductStore;
import com.demkom58.lab9.store.WoodDirectory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class TestApp {

    @Test
    public void iteratorTest() throws Exception {
        ProductStore store = new ProductStore();
        WoodDirectory directory = new WoodDirectory();

        store.add(new Timber(directory.getById(1), 5f, 1f, 5f));
        store.add(new Timber(directory.getById(1), 9f, 3f, 7f));
        store.add(new Timber(directory.getById(2), 6f, 2f, 8f));
        store.add(new Timber(directory.getById(3), 10f, 6f, 6f));

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

}

