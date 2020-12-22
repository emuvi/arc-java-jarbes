package pin.jarbes;

import java.awt.event.KeyEvent;
import pin.jarbox.Local;
import pin.jarbox.Variables;

public class ActClickUp extends Act {

  private static final long serialVersionUID = 5461631669377240283L;

  public Boolean shift;
  public Boolean control;
  public Boolean meta;
  public Boolean alt;
  public Buttons button;
  public String variable;
  public Local fixed;

  @Override
  public String execute(Variables variables) throws Exception {
    if (button == null) {
      throw new Exception("Could not determinate the button to click.");
    }
    Local local = null;
    Object evaluated = variables.evaluate(variable);
    if (evaluated instanceof Local) {
      local = (Local) evaluated;
    } else {
      local = fixed;
    }
    if (local == null || local.x == null || local.y == null) {
      throw new Exception("Could not determinate the local to click.");
    }
    Interact.mouseMove(local);
    Interact.mouseUp(button);
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
