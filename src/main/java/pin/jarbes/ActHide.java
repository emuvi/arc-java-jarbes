package pin.jarbes;

import java.awt.Window;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import pin.jarbox.Variables;

public class ActHide extends Act {

  private static final long serialVersionUID = 3052456058165201878L;

  @Override
  public String execute(Variables variables) throws Exception {
    ListWindow hidden = new ListWindow();
    for (Window window : JFrame.getWindows()) {
      if (window.isDisplayable() && window.isVisible()) {
        SwingUtilities.invokeLater(() -> window.setVisible(false));
        hidden.add(window);
      }
    }
    var oldHidden = variables.get("<hidden>");
    if (oldHidden instanceof ListWindow) {
      hidden.addAll((ListWindow) oldHidden);
    }
    variables.set("<hidden>", hidden);
    return "<next>";
  }

}
