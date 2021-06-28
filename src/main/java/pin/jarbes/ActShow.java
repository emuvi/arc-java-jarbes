package pin.jarbes;

import java.awt.Window;
import javax.swing.SwingUtilities;
import pin.jarbox.bin.Variables;

public class ActShow extends Act {

  private static final long serialVersionUID = -7318207519585124970L;

  @Override
  public String execute(Variables variables) throws Exception {
    var value = variables.get("<hidden>");
    if (value instanceof ListWindow) {
      var hidden = (ListWindow) value;
      for (Window window : hidden) {
        SwingUtilities.invokeLater(() -> {
          window.setVisible(true);
          window.toFront();
        });
      }
    }
    return "<next>";
  }

}
