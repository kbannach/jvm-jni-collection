package collection;

import java.util.stream.IntStream;

class JNICollection {

   static {
      System.loadLibrary("collection");
   }

   private long pointer;
   private int  size;
   private int  lastIdx;

   public JNICollection(int initSize) {
      lastIdx = 0;
      size = initSize;
      pointer = allocate(initSize);
   }

   public int lastIdx() {
      return lastIdx;
   }

   /**
    * allocates an int array of size {@code newSize} in JVM native memory
    * 
    * @return pointer to allocated array
    */
   private native long allocate(int newSize);

   /**
    * enlarges the collection
    * 
    * @return new pointer
    */
   private native long enlarge();

   /**
    * free memory
    */
   public native void free();

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

   public boolean add(int e) {
      if (lastIdx == size - 1) {
         pointer = enlarge();
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

   public void remove(int index) {
      for (int i = index + 1; i < lastIdx; i++) {
         set(get(i + 1), i);
      }
      set(0, lastIdx--);
   }

   public void clear() {
      IntStream.rangeClosed(0, lastIdx).forEach(i -> set(0, i));
      lastIdx = 0;
   }
}