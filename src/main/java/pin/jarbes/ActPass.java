package pin.jarbes;

import pin.jarbox.Variables;

public class ActPass extends Act {

  private static final long serialVersionUID = 1040470849679182697L;

  public Integer amount;

  @Override
  public String execute(Variables variables) throws Exception {
    if (amount == null || amount == 1 || amount == -1) {
      return "<next>";
    } else {
      if (amount == 0) {
        throw new Exception("Can not pass a zero amount.");
      }
      return "+" + Math.abs(amount);
    }
  }

}
