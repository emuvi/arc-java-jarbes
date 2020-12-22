package pin.jarbes;

import pin.jarbox.HelmEdit;
import pin.jarbox.Utils;

public abstract class HelmAct<T extends Act> extends HelmEdit<T> {

  public HelmAct(HelmEditor owner, Class<T> clazz, T initialValue)
      throws Exception {
      super(clazz, initialValue, true, value -> {
        try {
          owner.edit(value);
          return true;
        } catch (Exception e) {
          Utils.treat(e);
          return false;
        }
      });
      pack();
  }
}
