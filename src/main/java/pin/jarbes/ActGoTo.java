package pin.jarbes;

import pin.jarbox.bin.Variables;

public class ActGoTo extends Act {

  private static final long serialVersionUID = 2093853924922512676L;

  public String destiny;

  @Override
  public String execute(Variables variables) throws Exception {
    return destiny;
  }

}
