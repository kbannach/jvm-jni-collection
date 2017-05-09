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

public class BigIntArrayList implements List<Integer> {

   private JNICollection nativeCollection;

   public BigIntArrayList() {
      this(10);
   }

   public BigIntArrayList(int initSize) {
      nativeCollection = new JNICollection(initSize);
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
      // TODO or not TODO ???
      return null;
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
      c.stream().forEach(i -> add(i));
      // TODO return value
      return true;
   }

   @Override
   public boolean addAll(int index, Collection< ? extends Integer> c) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean removeAll(Collection< ? > c) {
      c.stream().forEach(i -> remove(i));
      // TODO return value
      return true;
   }

   @Override
   public boolean retainAll(Collection< ? > c) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void clear() {
      nativeCollection.clear();
   }

   @Override
   public Integer get(int index) {
      return nativeCollection.get(index);
   }

   @Override
   public Integer set(int index, Integer element) {
      return nativeCollection.set(element, index);
   }

   @Override
   public void add(int index, Integer element) {
      // TODO Auto-generated method stub

   }

   @Override
   public Integer remove(int index) {
      int ret = nativeCollection.get(index);
      nativeCollection.remove(index);
      return ret;
   }

   @Override
   public int indexOf(Object o) {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public int lastIndexOf(Object o) {
      // TODO Auto-generated method stub
      return 0;
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
      return mapToObjStream(0, nativeCollection.lastIdx() + 1);
   }

   private Stream<Integer> mapToObjStream(int fromIndex, int toIndex) {
      return IntStream.range(fromIndex, toIndex).mapToObj(i -> get(i));
   }
}
