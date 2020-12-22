package pin.jarbes;

import pin.jarbox.Variables;

public class ActHalt extends Act {

  private static final long serialVersionUID = -9114750387126560072L;

  @Override
  public String execute(Variables variables) throws Exception {
    return "<halt>";
  }

}
