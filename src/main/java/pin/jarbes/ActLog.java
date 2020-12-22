package pin.jarbes;

import pin.jarbox.Utils;
import pin.jarbox.Variables;

public class ActLog extends Act {

  private static final long serialVersionUID = -4307306616580952246L;

  public String message;
  public String parameters;

  @Override
  public String execute(Variables variables) throws Exception {
    String[] names = parameters.split("\\,");
    Object[] values = new Object[names.length];
    for (int i = 0; i < names.length; i++) {
      values[i] = variables.get(names[i]);
    }
    Utils.treat(message, values);
    return null;
  }

}
