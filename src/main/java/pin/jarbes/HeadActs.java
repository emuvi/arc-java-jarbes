package pin.jarbes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class HeadActs {

  private final ListActs list = new ListActs();

  public synchronized void set(ListActs actions) {
    list.clear();
    list.addAll(actions);
  }

  public synchronized int getIndex(String name) {
    for (int i = 0; i < list.size(); i++) {
      Act act = list.get(i);
      if (Objects.equals(act.name, name)) {
        return i;
      }
    }
    return -1;
  }

  public synchronized Act get(String name) {
    for (Act act : list) {
      if (Objects.equals(act.name, name)) {
        return act;
      }
    }
    return null;
  }

  public synchronized Act get(int index) {
    return list.get(index);
  }

  public synchronized int size() {
    return list.size();
  }

  public synchronized void clear() {
    list.clear();
  }

  public synchronized void load(File fromFile) throws Exception {
    ListActs actions = bring(fromFile);
    list.clear();
    list.addAll(actions);
  }

  public synchronized void save(File toFile) throws Exception {
    carry(list, toFile);
  }

  public static ListActs bring(File fromFile) throws Exception {
    try (ObjectInputStream ois =
        new ObjectInputStream(new FileInputStream(fromFile))) {
      Object read = ois.readObject();
      if (read instanceof ListActs) {
        return (ListActs) read;
      } else {
        throw new Exception(
            String.format("The file %s does not contains a list of actions.",
              fromFile.getName()));
      }
        }
  }

  public static void carry(ListActs actions, File toFile) throws Exception {
    try (ObjectOutputStream oos =
        new ObjectOutputStream(new FileOutputStream(toFile))) {
      oos.writeObject(actions);
        }
  }

}
