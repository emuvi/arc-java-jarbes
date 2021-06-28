package pin.jarbes;

import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import pin.jarbox.bin.Variables;

public class ActPressUp extends Act {

  private static final long serialVersionUID = 5872754921511840709L;

  public Boolean shift;
  public Boolean control;
  public Boolean meta;
  public Boolean alt;
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
    Interact.keyUp(keyCode);  
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
