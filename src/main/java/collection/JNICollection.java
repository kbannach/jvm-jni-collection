package collection;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JNICollection {

   private long pointer;
   private int  size;
   private int  lastIdx;

   public JNICollection(int initSize) {
      size = initSize;
      pointer = allocate(initSize);
   }

   private native long allocate(int newSize);

   private native void enlarge();

   private native long free();

   public native int get(int index);

   /**
    * @return the element previously at the specified position
    */
   public native int set(int el, int idx);

   public native void remove(int index);

   public boolean add(int e) {
      if (lastIdx == size - 1) {
         enlarge();
      }
      set(e, ++lastIdx);
      return true;
   }

   public int lastIdx() {
      return lastIdx;
   }

   public int size() {
      return size;
   }

   public void clear() {
      IntStream.rangeClosed(0, lastIdx).forEach(i -> set(0, i));
      lastIdx = 0;
   }
}
