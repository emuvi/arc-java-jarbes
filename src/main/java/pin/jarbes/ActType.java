package pin.jarbes;

import java.awt.event.KeyEvent;
import pin.jarbox.bin.Variables;

public class ActType extends Act {

  private static final long serialVersionUID = 4243929054251721693L;

  public String text;

  @Override
  public String execute(Variables variables) throws Exception {
    String toType = String.valueOf(variables.evaluateNotNull(text));
    if (toType.isEmpty()) {
      throw new Exception("There's nothing to type.");
    }
    for (char key : toType.toCharArray()) {
      char keyUpper = Character.toUpperCase(key);
      boolean shift = keyUpper == key;
      if (shift) {
        Interact.keyDown(KeyEvent.VK_SHIFT);
      }
      int keyCode = KeyEvent.getExtendedKeyCodeForChar(keyUpper);
      Interact.keyDown(keyCode);
      Interact.keyUp(keyCode);
      if (shift) {
        Interact.keyUp(KeyEvent.VK_SHIFT);
      }
    }
    return "<next>";
  }

}
