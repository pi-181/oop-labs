import com.demkom58.lab9.model.Timber;
import com.demkom58.lab9.store.ProductStore;
import com.demkom58.lab9.store.WoodDirectory;
import org.junit.Test;

public class TestApp {

    @Test
    public void iteratorTest() throws Exception {
        ProductStore store = new ProductStore();
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

    @Test
    public void doOnlyForTest() throws Exception {
        ProductStore store = new ProductStore();
        WoodDirectory directory = new WoodDirectory();

        store.add(new Timber(directory.get(1), 5f, 1f, 5f));
        store.add(new Timber(directory.get(1), 9f, 3f, 7f));
        store.add(new Timber(directory.get(2), 6f, 2f, 8f));
        store.add(new Timber(directory.get(3), 10f, 6f, 6f));

        store.doOnlyFor(o -> o.weight() > 30, System.out::println);
    }

}

