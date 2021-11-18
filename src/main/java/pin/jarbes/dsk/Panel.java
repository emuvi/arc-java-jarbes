package pin.jarbes.dsk;

import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class Panel {

  public final JPanel panel;
  public final JScrollPane scroll;

  public Panel() {
    this(false);
  }

  public Panel(boolean scrollable) {
    panel = new JPanel();
    scroll = scrollable ? new JScrollPane(panel) : null;
  }

  public Panel(boolean scrollable, LayoutManager layout) {
    panel = new JPanel(layout);
    scroll = scrollable ? new JScrollPane(panel) : null;
  }

  public JComponent getMain() {
    return scroll != null ? scroll : panel;
  }

  public abstract Panel add(Component component);

  public abstract Panel addGrow(Component component, int weight);

  public abstract Panel addGrow(Component component);

  public abstract Panel addCross(Component component, int weight);

  public abstract Panel addCross(Component component);

  public abstract Panel addMax(Component component, int weight);

  public abstract Panel addMax(Component component);

  public abstract Panel close();

  public Panel add(Panel panel) {
    add(panel.getMain());
    return this;
  }

  public Panel addGrow(Panel panel, int weight) {
    addGrow(panel.getMain(), weight);
    return this;
  }

  public Panel addGrow(Panel panel) {
    addGrow(panel.getMain());
    return this;
  }

  public Panel addCross(Panel panel, int weight) {
    addCross(panel.getMain(), weight);
    return this;
  }

  public Panel addCross(Panel panel) {
    addCross(panel.getMain());
    return this;
  }

  public Panel addMax(Panel panel, int weight) {
    addMax(panel.getMain(), weight);
    return this;
  }

  public Panel addMax(Panel panel) {
    addMax(panel.getMain());
    return this;
  }

  public Panel del(Component component) {
    panel.remove(component);
    return this;
  }

  public Panel del(Panel panel) {
    return del(panel.getMain());
  }

  public boolean contains(Component component) {
    for (int i = 0; i < panel.getComponentCount(); i++) {
      if (panel.getComponent(i) == component) {
        return true;
      }
    }
    return false;
  }

  public boolean contains(Panel panel) {
    return contains(panel.getMain());
  }

  public boolean isEmpty() {
    return panel.getComponentCount() == 0;
  }

}
