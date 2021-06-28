package pin.jarbes;

import java.awt.event.KeyEvent;
import pin.jarbox.val.Local;
import pin.jarbox.bin.Variables;

public class ActClick extends Act {

  private static final long serialVersionUID = -8991052184660306072L;

  public Boolean shift;
  public Boolean control;
  public Boolean meta;
  public Boolean alt;
  public Buttons button;
  public Integer times;
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
    int forTimes = Math.abs(times != null ? times : 1);
    Interact.mouseMove(local);
    for (int i = 0; i < forTimes; i++) {   
      Interact.mouseDown(button);
      Interact.mouseUp(button); 
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
