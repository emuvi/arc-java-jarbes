package pin.jarbes;

import pin.jarbox.bin.Variables;

public class ActSleep extends Act {

  private static final long serialVersionUID = -625096570029447680L;

  public Integer milliSeconds;

  @Override
  public String execute(Variables variables) throws Exception {
    if (milliSeconds == null || milliSeconds < 0) {
      Thread.sleep(1000);
    } else {
      Thread.sleep(Math.abs(milliSeconds));
    }
    return "<next>";
  }

}
