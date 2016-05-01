package remonsinnema.blog.scmlog.util;

import java.util.Iterator;
import java.util.List;


public class Traversal<T> implements Iterator<T> {

  private final List<T> items;
  private int index;

  public Traversal(List<T> items) {
    this.items = items;
    this.index = 0;
  }

  @Override
  public boolean hasNext() {
    return index < items.size();
  }

  @Override
  public T next() {
    return items.get(index++);
  }

  public void previous() {
    if (index == 0) {
      throw new UnsupportedOperationException("Cannot move beyond beginning");
    }
    index--;
  }

  public void replace(T value) {
    items.set(--index, value);
  }

}
