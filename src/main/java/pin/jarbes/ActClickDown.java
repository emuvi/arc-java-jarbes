package pin.jarbes;

import java.awt.event.KeyEvent;
import pin.jarbox.Local;
import pin.jarbox.Variables;

public class ActClickDown extends Act {

  private static final long serialVersionUID = 7725607662034778595L;

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
    Interact.mouseMove(local);
    Interact.mouseDown(button);
    return "<next>";
  }

}
