package pin.jarbes;

import java.awt.event.InputEvent;

public enum Buttons {

  Main(InputEvent.BUTTON1_DOWN_MASK),

  Middle(InputEvent.BUTTON2_DOWN_MASK),

  Secondary(InputEvent.BUTTON3_DOWN_MASK);

  private final int code;

  private Buttons(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}
