package pin.jarbes.dsk;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import pin.jarbox.wzd.WzdChars;
import pin.jarbox.wzd.WzdDesk;

public class PopMenu {

  private final JPopupMenu jpmMenu;
  private Point lastClick;

  public PopMenu() {
    jpmMenu = new JPopupMenu();
  }

  public JPopupMenu getJMenu() {
    return jpmMenu;
  }

  public Point getLastClick() {
    return lastClick;
  }

  public PopMenu put(String title) {
    return put(null, null, title, null, null, null);
  }

  public PopMenu put(String title, ActionListener action) {
    return put(null, null, title, null, null, action);
  }

  public PopMenu put(String title, Icon icon) {
    return put(null, null, title, icon, null, null);
  }

  public PopMenu put(String title, Icon icon, ActionListener action) {
    return put(null, null, title, icon, null, action);
  }

  public PopMenu put(String title, Icon icon, Character mnemonic) {
    return put(null, null, title, icon, mnemonic, null);
  }

  public PopMenu put(String title, Icon icon, Character mnemonic, ActionListener action) {
    return put(null, null, title, icon, mnemonic, action);
  }

  public PopMenu put(String title, Character mnemonic) {
    return put(null, null, title, null, mnemonic, null);
  }

  public PopMenu put(String title, Character mnemonic, ActionListener action) {
    return put(null, null, title, null, mnemonic, action);
  }

  public PopMenu put(String origin, String title) {
    return put(origin, null, title, null, null, null);
  }

  public PopMenu put(String origin, String title, ActionListener action) {
    return put(origin, null, title, null, null, action);
  }

  public PopMenu put(String origin, String title, Icon icon) {
    return put(origin, null, title, icon, null, null);
  }

  public PopMenu put(String origin, String title, Icon icon, ActionListener action) {
    return put(origin, null, title, icon, null, action);
  }

  public PopMenu put(String origin, String title, Icon icon, Character mnemonic) {
    return put(origin, null, title, icon, mnemonic, null);
  }

  public PopMenu put(String origin, String title, Icon icon, Character mnemonic,
      ActionListener action) {
    return put(origin, null, title, icon, mnemonic, action);
  }

  public PopMenu put(String origin, String title, Character mnemonic) {
    return put(origin, null, title, null, mnemonic, null);
  }

  public PopMenu put(String origin, String title, Character mnemonic,
      ActionListener action) {
    return put(origin, null, title, null, mnemonic, action);
  }

  public PopMenu put(Integer index, String title) {
    return put(null, index, title, null, null, null);
  }

  public PopMenu put(Integer index, String title, ActionListener action) {
    return put(null, index, title, null, null, action);
  }

  public PopMenu put(Integer index, String title, Icon icon) {
    return put(null, index, title, icon, null, null);
  }

  public PopMenu put(Integer index, String title, Icon icon, ActionListener action) {
    return put(null, index, title, icon, null, action);
  }

  public PopMenu put(Integer index, String title, Icon icon, Character mnemonic) {
    return put(null, index, title, icon, mnemonic, null);
  }

  public PopMenu put(Integer index, String title, Icon icon, Character mnemonic,
      ActionListener action) {
    return put(null, index, title, icon, mnemonic, action);
  }

  public PopMenu put(Integer index, String title, Character mnemonic) {
    return put(null, index, title, null, mnemonic, null);
  }

  public PopMenu put(Integer index, String title, Character mnemonic,
      ActionListener action) {
    return put(null, index, title, null, mnemonic, action);
  }

  public PopMenu put(String origin, Integer index, String title) {
    return put(origin, index, title, null, null, null);
  }

  public PopMenu put(String origin, Integer index, String title, ActionListener action) {
    return put(origin, index, title, null, null, action);
  }

  public PopMenu put(String origin, Integer index, String title, Icon icon) {
    return put(origin, index, title, icon, null, null);
  }

  public PopMenu put(String origin, Integer index, String title, Icon icon,
      ActionListener action) {
    return put(origin, index, title, icon, null, action);
  }

  public PopMenu put(String origin, Integer index, String title, Icon icon,
      Character mnemonic) {
    return put(origin, index, title, icon, mnemonic, null);
  }

  public PopMenu put(String origin, Integer index, String title, Character mnemonic) {
    return put(origin, index, title, null, mnemonic, null);
  }

  public PopMenu put(String origin, Integer index, String title, Character mnemonic,
      ActionListener action) {
    return put(origin, index, title, null, mnemonic, action);
  }

  public PopMenu put(String origin, Integer index, String title, Icon icon,
      Character mnemonic, ActionListener action) {
    JComponent whereInsert = jpmMenu;
    if (origin != null) {
      if (!origin.isEmpty()) {
        whereInsert = WzdDesk.getMenu(jpmMenu, origin);
      }
    }
    if (whereInsert == null) {
      whereInsert = jpmMenu;
    }
    if (mnemonic != null) {
      title = title + "   [ " + mnemonic + " ]";
    }
    if (action != null) {
      JMenuItem newItem = new JMenuItem();
      if (title != null) {
        newItem.setText(title);
      }
      if (mnemonic != null) {
        newItem.setMnemonic(mnemonic);
      }
      if (icon != null) {
        newItem.setIcon(icon);
      }
      newItem.addActionListener(action);
      if (index == null) {
        whereInsert.add(newItem);
      } else {
        whereInsert.add(newItem, index.intValue());
      }
    } else {
      JMenu newItem = new JMenu();
      if (title != null) {
        newItem.setText(title);
      }
      if (mnemonic != null) {
        newItem.setMnemonic(mnemonic);
      }
      if (icon != null) {
        newItem.setIcon(icon);
      }
      if (index == null) {
        whereInsert.add(newItem);
      } else {
        whereInsert.add(newItem, index.intValue());
      }
    }
    return this;
  }

  public PopMenu put(String origin, PopMenu menu) {
    JComponent whereInsert = jpmMenu;
    if (origin != null) {
      if (!origin.isEmpty()) {
        whereInsert = WzdDesk.getMenu(jpmMenu, origin);
      }
    }
    if (whereInsert == null) {
      whereInsert = jpmMenu;
    }
    for (Component cmp : menu.getJMenu().getComponents()) {
      whereInsert.add(cmp);
    }
    return this;
  }

  public PopMenu putSeparator() {
    putSeparator(null, null);
    return this;
  }

  public PopMenu putSeparator(String origin) {
    putSeparator(origin, null);
    return this;
  }

  public PopMenu putSeparator(Integer index) {
    putSeparator(null, index);
    return this;
  }

  public PopMenu putSeparator(String origin, Integer index) {
    JComponent whereInsert = jpmMenu;
    if (origin != null) {
      if (!origin.isEmpty()) {
        whereInsert = WzdDesk.getMenu(jpmMenu, origin);
      }
    }
    if (whereInsert == null) {
      whereInsert = jpmMenu;
    }
    JSeparator newItem = new JSeparator();
    if (index == null) {
      whereInsert.add(newItem);
    } else {
      whereInsert.add(newItem, index.intValue());
    }
    return this;
  }

  public PopMenu set(String title, Character mnemonic) {
    return set(null, title, mnemonic, null, null);
  }

  public PopMenu set(String origin, String title, Character mnemonic) {
    return set(origin, title, mnemonic, null, null);
  }

  public PopMenu set(String title, Icon icon) {
    return set(null, title, null, icon, null);
  }

  public PopMenu set(String origin, String title, Icon icon) {
    return set(origin, title, null, icon, null);
  }

  public PopMenu set(String title, ActionListener action) {
    return set(null, title, null, null, action);
  }

  public PopMenu set(String origin, String title, ActionListener action) {
    return set(origin, title, null, null, action);
  }

  public PopMenu set(String title, Character mnemonic, ActionListener action) {
    return set(null, title, mnemonic, null, action);
  }

  public PopMenu set(String origin, String title, Character mnemonic,
      ActionListener action) {
    return set(origin, title, mnemonic, null, action);
  }

  public PopMenu set(String title, Icon icon, ActionListener action) {
    return set(null, title, null, null, action);
  }

  public PopMenu set(String origin, String title, Icon icon, ActionListener action) {
    return set(origin, title, null, icon, action);
  }

  public PopMenu set(String title, Character mnemonic, Icon icon, ActionListener action) {
    return set(null, title, mnemonic, icon, action);
  }

  public PopMenu set(String origin, String title, Character mnemonic, Icon icon,
      ActionListener action) {
    String name = WzdChars.sum(origin, ".", title);
    JComponent element = WzdDesk.getItem(jpmMenu, name);
    if (element instanceof JMenu) {
      JMenu elem = (JMenu) element;
      if (mnemonic != null) {
        elem.setMnemonic(mnemonic);
        elem.setText(title + "   [ " + mnemonic + " ]");
      }
      if (icon != null) {
        elem.setIcon(icon);
      }
      if (action != null) {
        for (ActionListener act : elem.getActionListeners()) {
          elem.removeActionListener(act);
        }
        elem.addActionListener(action);
      }
    } else if (element instanceof JMenuItem) {
      JMenuItem elem = (JMenuItem) element;
      if (mnemonic != null) {
        elem.setMnemonic(mnemonic);
        elem.setText(title + "   [ " + mnemonic + " ]");
      }
      if (icon != null) {
        elem.setIcon(icon);
      }
      if (action != null) {
        for (ActionListener act : elem.getActionListeners()) {
          elem.removeActionListener(act);
        }
        elem.addActionListener(action);
      }
    }
    return this;
  }

  public PopMenu clear() {
    jpmMenu.removeAll();
    return this;
  }

  public PopMenu clear(String origin) {
    JComponent element = WzdDesk.getItem(jpmMenu, origin);
    if (element instanceof JMenu) {
      ((JMenu) element).removeAll();
    } else if (element instanceof JMenuItem) {
      ((JMenuItem) element).removeAll();
    }
    return this;
  }

  public void show(JComponent onComponent) {
    show(onComponent, true);
  }

  public void show(JComponent onComponent, Boolean onBottom) {
    if (onBottom) {
      show(onComponent, 0, onComponent.getHeight());
    } else {
      show(onComponent, 0, 0);
    }
  }

  public void show(JComponent onComponent, Integer naPosX, Integer naPosY) {
    if (naPosX == null) {
      naPosX = 0;
    }
    if (naPosY == null) {
      naPosY = 0;
    }
    jpmMenu.show(onComponent, naPosX, naPosY);
  }

  public PopMenu setup(JComponent onComponent) {
    return setup(onComponent, false, MouseEvent.BUTTON3, false);
  }

  public PopMenu setup(JComponent onComponent, Integer withButton) {
    return setup(onComponent, false, withButton, false);
  }

  public PopMenu setup(JComponent onComponent, Boolean onBottom) {
    return setup(onComponent, onBottom, MouseEvent.BUTTON3, false);
  }

  public PopMenu setup(JComponent onComponent, Boolean onBottom, Integer withButton) {
    return setup(onComponent, onBottom, withButton, false);
  }

  public PopMenu setup(JComponent onComponent, Boolean onBottom, Boolean surePopup) {
    return setup(onComponent, onBottom, MouseEvent.BUTTON3, surePopup);
  }

  public PopMenu setup(JComponent onComponent, Boolean onBottom, Integer withButton,
      Boolean surePopup) {
    if (onComponent == null) {
      return this;
    }
    if (onComponent instanceof JButton && !surePopup) {
      ((JButton) onComponent).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          show(onComponent, 0, onComponent.getHeight());
        }
      });
    } else {
      EvtPopMenu evtContext = new EvtPopMenu(this, withButton, onBottom);
      onComponent.addMouseListener(evtContext);
      setup(onComponent.getComponents(), evtContext);
    }
    return this;
  }

  private void setup(Component[] inComponents, EvtPopMenu event) {
    if (inComponents == null) {
      return;
    }
    for (Component component : inComponents) {
      boolean hasAlready = false;
      for (MouseListener evt : component.getMouseListeners()) {
        if (evt instanceof EvtPopMenu) {
          hasAlready = true;
          break;
        }
      }
      if (!hasAlready) {
        component.addMouseListener(event);
      }
      if (component instanceof JComponent) {
        setup(((JComponent) component).getComponents(), event);
      }
    }
  }

  public void execute(String title) {
    execute(null, title);
  }

  public void execute(String origin, String title) {
    JMenuItem item = WzdDesk.getMenuItem(jpmMenu, WzdChars.sum(origin, ".", title));
    WzdDesk.execute(item.getActionListeners());
  }

  public Boolean isEmpty() {
    return jpmMenu.getComponentCount() == 0;
  }

  private class EvtPopMenu extends MouseAdapter {

    private final PopMenu menu;
    private final Integer button;
    private final Boolean bottom;

    public EvtPopMenu(PopMenu menu, Integer button, Boolean bottom) {
      this.menu = menu;
      this.button = button;
      this.bottom = bottom;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      lastClick = e.getPoint();
      if (e.getButton() == button) {
        if (bottom) {
          menu.show((JComponent) e.getComponent());
        } else {
          menu.show((JComponent) e.getComponent(), e.getX(), e.getY());
        }
      }
    }

  }

}
