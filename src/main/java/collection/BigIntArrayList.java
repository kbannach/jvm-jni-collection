package collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * !!! IMPORTANT !!!<br>
 * Always destroy this object manually by calling {@code destroy()} method!!!
 */
public class BigIntArrayList implements List<Integer> {

   JNICollection nativeCollection;

   /**
    * !!! IMPORTANT !!!<br>
    * Always destroy this object manually by calling {@code destroy()} method!!!
    */
   public BigIntArrayList() {
      this(10);
   }

   /**
    * !!! IMPORTANT !!!<br>
    * Always destroy this object manually by calling {@code destroy()} method!!!
    */
   public BigIntArrayList(int initSize) {
      if (initSize < 0) {
         throw new IllegalArgumentException("Initial size can't be lower than 0!");
      }
      nativeCollection = new JNICollection(initSize);
   }

   public void destroy() {
      nativeCollection.free();
   }

   @Override
   public int size() {
      return nativeCollection.lastIdx();
   }

   @Override
   public boolean isEmpty() {
      return size() == 0;
   }

   @Override
   public boolean contains(Object o) {
      int obj;
      try {
         obj = (int) o;
      } catch (ClassCastException e) {
         return false;
      }
      return mapAllToObjStream().anyMatch(elem -> elem == obj);
   }

   @Override
   public Iterator<Integer> iterator() {
      return new BigIntIterator();
   }

   @Override
   public Object[] toArray() {
      return mapAllToObjStream().collect(Collectors.toList()).toArray();
   }

   @Override
   public <T> T[] toArray(T[] a) {
      return mapAllToObjStream().collect(Collectors.toList()).toArray(a);
   }

   @Override
   public boolean add(Integer e) {
      return nativeCollection.add(e);
   }

   @Override
   public boolean remove(Object o) {
      OptionalInt findIdx = IntStream.rangeClosed(0, nativeCollection.lastIdx()).filter(i -> nativeCollection.get(i) == (int) o).findFirst();

      try {
         Integer idx = findIdx.getAsInt();
         nativeCollection.remove(idx);
         return true;
      } catch (NoSuchElementException e) {
         return false;
      }
   }

   @Override
   public boolean containsAll(Collection< ? > c) {
      return c.stream().allMatch(elem -> contains(elem));
   }

   @Override
   public boolean addAll(Collection< ? extends Integer> c) {
      boolean ret = true;
      for (Integer i : c) {
         ret = ret && add(i);
      }
      return ret;
   }

   @Override
   public boolean addAll(int index, Collection< ? extends Integer> c) {
      rangeCheck(index);
      int idx = index;
      for (Integer i : c) {
         add(idx++, i);
      }
      return true;
   }

   @Override
   public boolean removeAll(Collection< ? > c) {
      boolean ret = false;
      for (Object i : c) {
         ret = ret || remove(i);
      }
      return ret;
   }

   @Override
   public boolean retainAll(Collection< ? > c) {
      // TODO or not TODO ???
      return false;
   }

   @Override
   public void clear() {
      nativeCollection.clear();
   }

   @Override
   public Integer get(int index) {
      rangeCheck(index);
      return nativeCollection.get(index);
   }

   @Override
   public Integer set(int index, Integer element) {
      rangeCheck(index);
      return nativeCollection.set(element, index);
   }

   @Override
   public void add(int index, Integer element) {
      rangeCheck(index);
      nativeCollection.add(index, element);
   }

   @Override
   public Integer remove(int index) {
      rangeCheck(index);
      int ret = nativeCollection.get(index);
      nativeCollection.remove(index);
      return ret;
   }

   @Override
   public int indexOf(Object o) {
      try {
         return IntStream.rangeClosed(0, nativeCollection.lastIdx()).filter(i -> get(i) == (int) o).findFirst().getAsInt();
      } catch (NoSuchElementException e) {
         return -1;
      }
   }

   @Override
   public int lastIndexOf(Object o) {
      int[] arr = IntStream.rangeClosed(0, nativeCollection.lastIdx()).filter(i -> get(i) == (int) o).toArray();
      if (arr.length == 0) {
         return -1;
      } else {
         return arr[arr.length - 1];
      }
   }

   @Override
   public ListIterator<Integer> listIterator() {
      // TODO or not TODO ???
      return null;
   }

   @Override
   public ListIterator<Integer> listIterator(int index) {
      // TODO or not TODO ???
      return null;
   }

   @Override
   public List<Integer> subList(int fromIndex, int toIndex) {
      return mapToObjStream(fromIndex, toIndex).collect(Collectors.toList());
   }

   private Stream<Integer> mapAllToObjStream() {
      return mapToObjStream(0, nativeCollection.lastIdx());
   }

   /**
    * @param fromIndex
    *        (inclusive)
    * @param toIndex
    *        (inclusive)
    */
   private Stream<Integer> mapToObjStream(int fromIndex, int toIndex) {
      subListRangeCheck(fromIndex, toIndex, nativeCollection.lastIdx());
      return IntStream.rangeClosed(fromIndex, toIndex).mapToObj(i -> get(i));
   }

   private void rangeCheck(int index) {
      if (index > nativeCollection.lastIdx()) {
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nativeCollection.lastIdx());
      }
   }

   private void subListRangeCheck(int fromIndex, int toIndex, int lastIdx) {
      if (fromIndex < 0)
         throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
      if (toIndex > lastIdx)
         throw new IndexOutOfBoundsException("toIndex = " + toIndex);
      if (fromIndex > toIndex)
         throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
   }

   private class BigIntIterator implements Iterator<Integer> {

      private int cursor; // index of next element to return
      private int lastRet; // index of last element returned; -1 if no such

      public BigIntIterator() {
         lastRet = -1;
      }

      @Override
      public boolean hasNext() {
         return cursor != size();
      }

      @Override
      public Integer next() {
         int i = cursor;
         if (i >= size())
            throw new NoSuchElementException();
         lastRet = cursor = i + 1;
         return nativeCollection.get(cursor);
      }

      @Override
      public void remove() {
         if (lastRet < 0)
            throw new IllegalStateException();

         nativeCollection.remove(lastRet);
         cursor = lastRet;
         lastRet = -1;
      }
   }
}
