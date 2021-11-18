package pin.jarbes.dsk;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Window;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import pin.jarbox.bin.Console;
import pin.jarbox.wzd.WzdLang;
import pin.jarbox.wzd.WzdDesk;

public class HelmLogged {

  private final JFrame window = new JFrame("Logged");
  private final JTextArea text = new JTextArea(20, 60);
  private final JScrollPane scroll = new JScrollPane(text);
  private final JPopupMenu menu = new JPopupMenu();
  private final JMenuItem refresh = new JMenuItem("Refresh");
  private final JPanel panel = new JPanel(new BorderLayout(0, 0));

  public HelmLogged(Window owner) throws Exception {
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    window.setIconImage(Icons.getTerminal());
    window.setLocationRelativeTo(owner);
    window.setContentPane(panel);
    text.setEditable(false);
    text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
    text.setComponentPopupMenu(menu);
    menu.add(refresh);
    refresh.addActionListener(event -> menuRefresh());
    panel.add(scroll);
    window.pack();
    WzdDesk.putShortCut(panel, "Exit", "ESCAPE", () -> window.setVisible(false));
  }

  private void menuRefresh() {
    text.setText(Console.instance.getLogged());
  }

  public void show() {
    window.setVisible(true);
    text.setText(Console.instance.getLogged());
    WzdLang.delay(500, () -> {
      SwingUtilities.invokeLater(() -> {
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar()
            .getMaximum());
      });
    });
  }
}
