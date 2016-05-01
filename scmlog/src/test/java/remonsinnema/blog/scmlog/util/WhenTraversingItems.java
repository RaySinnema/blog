package remonsinnema.blog.scmlog.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import remonsinnema.blog.scmlog.util.Traversal;


public class WhenTraversingItems {

  @Test
  public void shouldMoveForwardLikeIterator() {
    Integer item1 = 42;
    Integer item2 = 313;
    Traversal<Integer> items = new Traversal<>(Arrays.asList(item1, item2));

    assertTrue("Missing item #1", items.hasNext());
    assertEquals("Item #1", item1, items.next());
    assertTrue("Missing item #2", items.hasNext());
    assertEquals("Item #2", item2, items.next());
    assertFalse("Extra items", items.hasNext());
  }

  @Test
  public void shouldRetrieveItemMultipleTimesWhenGoingBackwards() {
    Integer item = 169;

    Traversal<Integer> items = new Traversal<>(Arrays.asList(item));
    items.next();
    items.previous();

    assertEquals("Item", item, items.next());
  }

  @Test
  public void shouldReplaceText() {
    Traversal<String> items = new Traversal<>(Arrays.asList("ape", "bear"));

    items.next();
    items.replace("cheetah");
    
    assertEquals("cheetah", items.next());
    assertEquals("bear", items.next());
  }

}
