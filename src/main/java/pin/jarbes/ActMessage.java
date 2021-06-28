package pin.jarbes;


import pin.jarbox.bin.Variables;
import pin.jarbox.wzd.WzdLog;

public class ActMessage extends Act {

  private static final long serialVersionUID = 8132810119789949801L;

  public String message;
  public String parameters;

  @Override
  public String execute(Variables variables) throws Exception {
    String[] names = parameters.split("\\,");
    Object[] values = new Object[names.length];
    for (int i = 0; i < names.length; i++) {
      values[i] = variables.get(names[i]);
    }
    WzdLog.treat(message, values);
    return null;
  }

}
