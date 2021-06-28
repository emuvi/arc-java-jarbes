package pin.jarbes;

import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import pin.jarbox.bin.Variables;

public class ActPress extends Act {

  private static final long serialVersionUID = -8922960761011539990L;

  public Boolean shift;
  public Boolean control;
  public Boolean meta;
  public Boolean alt;
  public Integer times;
  public String key;

  @Override
  public String execute(Variables variables) throws Exception {
    String toPress = String.valueOf(variables.evaluateNotNull(key));
    if (toPress.isEmpty()) {
      throw new Exception("There's nothing to press.");
    }
    toPress = toPress.toUpperCase();
    Integer keyCode = null;
    if (toPress.length() == 1) {
      keyCode = KeyEvent.getExtendedKeyCodeForChar(toPress.charAt(0));
    } else {
      KeyStroke stroke = KeyStroke.getKeyStroke(toPress);
      if (stroke == null) {
        throw new Exception("Could not find the stroke for: '" + toPress + "'");
      }
      keyCode = stroke.getKeyCode();
    }
    if (shift != null && shift == true) {
      Interact.keyDown(KeyEvent.VK_SHIFT);
    }
    if (control != null && control == true) {
      Interact.keyDown(KeyEvent.VK_CONTROL);
    }
    if (meta != null && meta == true) {
      Interact.keyDown(KeyEvent.VK_META);
    }
    if (alt != null && alt == true) {
      Interact.keyDown(KeyEvent.VK_ALT);
    }
    int forTimes = Math.abs(times != null ? times : 1);
    for (int i = 0; i < forTimes; i++) {
      Interact.keyDown(keyCode);
      Interact.keyUp(keyCode);  
    }
    if (alt != null && alt == true) {
      Interact.keyUp(KeyEvent.VK_ALT);
    }
    if (meta != null && meta == true) {
      Interact.keyUp(KeyEvent.VK_META);
    }
    if (control != null && control == true) {
      Interact.keyUp(KeyEvent.VK_CONTROL);
    }
    if (shift != null && shift == true) {
      Interact.keyUp(KeyEvent.VK_SHIFT);
    }
    return "<next>";
  }

}
