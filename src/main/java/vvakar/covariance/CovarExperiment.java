package vvakar.covariance;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author vvakar
 *         Date: 1/31/15
 */
public class CovarExperiment {

    private static final class MyCollection<T> {
        List<T> inner = new ArrayList<T>();

        public void add(T t) {
            inner.add(t);
        }

        /**
         // Has to take Iterable<? extends T> instead of Iterable<T>
         // because Iterable<Number> is not covariant with Iterable<Integer>.
         // Hence addAll has to take Iterable of "something that extends Number"
         // rather than Iterable of Number explicitly.
         */
        public void addAll(Iterable<? extends T> iterable) {
            for(T t : iterable) {
                add(t);
            }
        }

        public Iterator<T> iterator() {
            return inner.iterator();
        }

        public Collection<T> getAll() {
            return inner;
        }
    }


   public void doWork() {
       MyCollection<Number> numbers = new MyCollection<Number>();
       numbers.add(123);
       numbers.add(123.5d);

       numbers.addAll(ImmutableList.of(1,2,3));



       List<? super Number> numberList = new ArrayList<Object>();

       numberList.addAll(numbers.getAll());


   }


}
