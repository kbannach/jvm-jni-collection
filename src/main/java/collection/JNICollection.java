package collection;

import java.util.stream.IntStream;

public class JNICollection {

   private long pointer;
   private int  size;
   private int  lastIdx;

   public JNICollection(int initSize) {
      size = initSize;
      pointer = allocate(initSize);
   }

   public int lastIdx() {
      return lastIdx;
   }

   public int size() {
      return size;
   }

   /**
    * allocates an int array of size {@code newSize} in JVM native memory
    * 
    * @return pointer to allocated array
    */
   private native long allocate(int newSize);

   /**
    * enlarges the collection to 5/3 of its current size
    */
   private native void enlarge();

   private native long free();

   /**
    * @return element at the specified position
    */
   public native int get(int index);

   /**
    * set {@code element} at the specified position
    * 
    * @return the element previously at the specified position
    */
   public native int set(int element, int idx);

   /**
    * remove element at the specified position
    */
   public native void remove(int index);

   public boolean add(int e) {
      if (lastIdx == size - 1) {
         enlarge();
      }
      set(e, ++lastIdx);
      return true;
   }

   public void add(int index, Integer element) {
      // move last element, ensuring that it woult fit in
      add(get(lastIdx));

      // move rest of the elements
      for (int i = lastIdx - 1; i >= index; i--) {
         set(get(i), i + 1);
      }

      // add the specified element
      set(element, index);
   }

   public void clear() {
      IntStream.rangeClosed(0, lastIdx).forEach(i -> set(0, i));
      lastIdx = 0;
   }
}