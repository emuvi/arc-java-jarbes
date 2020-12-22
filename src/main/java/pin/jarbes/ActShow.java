package pin.jarbes;

import java.awt.Window;
import javax.swing.SwingUtilities;
import pin.jarbox.Variables;

public class ActShow extends Act {

  private static final long serialVersionUID = -7318207519585124970L;

  @Override
  public String execute(Variables variables) throws Exception {
    if (variables.get("<hidden>") instanceof ListWindow hidden) {
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
