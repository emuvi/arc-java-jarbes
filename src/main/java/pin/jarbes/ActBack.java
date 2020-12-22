package pin.jarbes;

import pin.jarbox.Variables;

public class ActBack extends Act {

  private static final long serialVersionUID = -106178216992605498L;

  public Integer amount;

  @Override
  public String execute(Variables variables) throws Exception {
    if (amount == null || amount == 1 || amount == -1) {
      return "<previous>";
    } else {
      if (amount == 0) {
        throw new Exception("Can not back a zero amount.");
      }
      return "-" + Math.abs(amount);
    }
  }

}
