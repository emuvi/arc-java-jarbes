package pin.jarbes;

import pin.jarbes.dsk.HelmEdit;
import pin.jarbox.wzd.WzdLog;


public abstract class HelmAct<T extends Act> extends HelmEdit<T> {

  public HelmAct(HelmEditor owner, Class<T> clazz, T initialValue)
      throws Exception {
      super(clazz, initialValue, true, value -> {
        try {
          owner.edit(value);
          return true;
        } catch (Exception e) {
          WzdLog.treat(e);
          return false;
        }
      });
      pack();
  }
}
