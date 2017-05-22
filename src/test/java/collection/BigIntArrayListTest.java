package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;

public class BigIntArrayListTest {

   private BigIntArrayList list;
   private Integer[]       oneToFourArray = new Integer[]{1, 2, 3, 4};

   @After
   public void destroy() {
      if (list != null) {
         list.destroy();
      }
   }

   @Test
   public void simpleTest() {
      list = new BigIntArrayList();
      list.add(oneToFourArray);

      Assertions.assertThat(list).isNotNull().hasSize(4).containsExactly(1, 2, 3, 4);
   }

   @Test
   public void enlargementTest() {
      list = new BigIntArrayList(2);
      list.add(oneToFourArray);

      Assertions.assertThat(list).isNotNull().hasSize(4).containsExactly(1, 2, 3, 4);
   }

   @Test
   public void multipleEnlargementsTest() {
      list = new BigIntArrayList(1);
      IntStream.range(0, 5).forEach(i -> list.add(i));

      Assertions.assertThat(list).isNotNull().hasSize(5);
   }

   @Test
   public void removeTest() {
      list = new BigIntArrayList();
      list.add(oneToFourArray);
      list.remove(2);

      Assertions.assertThat(list).isNotNull().hasSize(3).containsExactly(1, 2, 4);
   }

   @Test
   public void clearTest() {
      list = new BigIntArrayList();
      Random rand = new Random();
      IntStream.range(0, 10).forEach(i -> list.add(rand.nextInt(100)));
      list.clear();

      Assertions.assertThat(list).isNotNull().isEmpty();
   }

   @Test
   public void containsTest() {
      list = new BigIntArrayList();
      list.add(10, 20, 30, 40);

      Assertions.assertThat(list.contains(20)).isTrue();
      Assertions.assertThat(list.contains(10)).isTrue();
      Assertions.assertThat(list.contains(40)).isTrue();

      Assertions.assertThat(list.contains(2)).isFalse();
      Assertions.assertThat(list.contains(50)).isFalse();
      Assertions.assertThat(list.contains(0)).isFalse();
   }

   @Test
   public void containsAllTest() {
      list = new BigIntArrayList();
      List<Integer> arr = new ArrayList<>(Arrays.asList(10, 20, 30, 40));
      list.addAll(arr);

      Assertions.assertThat(list.containsAll(arr)).isTrue();

      arr.add(0);
      Assertions.assertThat(list.containsAll(arr)).isFalse();
   }

   @Test
   public void toArrayTest() {
      list = new BigIntArrayList();
      list.add(oneToFourArray);

      Assertions.assertThat(list.toArray()).isNotNull().hasSize(4).isEqualTo(new Object[]{1, 2, 3, 4});
   }

   @Test
   public void getTest() {
      list = new BigIntArrayList();
      list.add(oneToFourArray);

      Assertions.assertThat(list.get(0)).isNotNull().isEqualTo(1);
      Assertions.assertThat(list.get(1)).isNotNull().isEqualTo(2);
      Assertions.assertThat(list.get(3)).isNotNull().isEqualTo(4);
   }

   @Test
   public void setTest() {
      list = new BigIntArrayList();
      list.add(oneToFourArray);
      list.set(1, -1);

      Assertions.assertThat(list).isNotNull().hasSize(4).containsExactly(1, -1, 3, 4);
   }

   @Test
   public void indexAddTest() {
      list = new BigIntArrayList();
      list.add(oneToFourArray);
      list.add(1, -1);

      Assertions.assertThat(list).isNotNull().hasSize(5).containsExactly(1, -1, 2, 3, 4);
   }

   @Test
   public void indexOfTest() {
      list = new BigIntArrayList();
      list.add(40, 30, 20, 10, 40, 10);

      Assertions.assertThat(list.indexOf(0)).isNotNull().isEqualTo(-1);
      Assertions.assertThat(list.indexOf(40)).isNotNull().isEqualTo(0);
      Assertions.assertThat(list.indexOf(30)).isNotNull().isEqualTo(1);
      Assertions.assertThat(list.indexOf(10)).isNotNull().isEqualTo(3);
   }

   @Test
   public void lastIndexOfTest() {
      list = new BigIntArrayList();
      list.add(40, 30, 20, 10, 40, 10);

      Assertions.assertThat(list.lastIndexOf(0)).isNotNull().isEqualTo(-1);
      Assertions.assertThat(list.lastIndexOf(40)).isNotNull().isEqualTo(4);
      Assertions.assertThat(list.lastIndexOf(30)).isNotNull().isEqualTo(1);
      Assertions.assertThat(list.lastIndexOf(10)).isNotNull().isEqualTo(5);
   }

   @Test
   public void subListTest() {
      list = new BigIntArrayList();
      list.add(40, 30, 20, 10, 40, 10);

      Assertions.assertThat(list.subList(0, 3)).isNotNull().hasSize(3).containsExactly(40, 30, 20);
      Assertions.assertThat(list.subList(2, 4)).isNotNull().hasSize(2).containsExactly(20, 10);
      Assertions.assertThat(list.subList(1, 6)).isNotNull().hasSize(5).containsExactly(30, 20, 10, 40, 10);
   }
}
