package pin.jarbes.dsk;

import java.awt.Dimension;
import java.awt.Taskbar;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import pin.jarbox.wzd.WzdDesk;

public class Helm {

  public final Window window;
  public final Panel container;

  public Helm(Window window, Panel container) {
    this.window = window;
    this.container = container;
    if (window instanceof JFrame) {
      ((JFrame) window).setContentPane(container.getMain());
    } else if (window instanceof JDialog) {
      ((JDialog) window).setContentPane(container.getMain());
    }
    container.panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 2, 2));
    putShortcut("Close", "ESCAPE", () -> close());
  }

  public Helm setExitOnClose() {
    if (window instanceof JFrame) {
      ((JFrame) window).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } else if (window instanceof JDialog) {
      ((JDialog) window).setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
    }
    return this;
  }

  public Helm setDisposeOnClose() {
    if (window instanceof JFrame) {
      ((JFrame) window).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    } else if (window instanceof JDialog) {
      ((JDialog) window).setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    return this;
  }

  public Helm setHideOnClose() {
    if (window instanceof JFrame) {
      ((JFrame) window).setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    } else if (window instanceof JDialog) {
      ((JDialog) window).setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }
    return this;
  }

  public Helm setCancelOnClose() {
    if (window instanceof JFrame) {
      ((JFrame) window).setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    } else if (window instanceof JDialog) {
      ((JDialog) window).setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }
    return this;
  }

  public Helm setIcon(BufferedImage image) {
    window.setIconImage(image);
    boolean isMain = false;
    if (window instanceof JFrame) {
      isMain = ((JFrame) window).getDefaultCloseOperation() == JFrame.EXIT_ON_CLOSE;
    } else if (window instanceof JDialog) {
      isMain = ((JDialog) window).getDefaultCloseOperation() == JDialog.EXIT_ON_CLOSE;
    }
    if (isMain) {
      try {
        Taskbar.getTaskbar().setIconImage(image);
      } catch (Exception e) {
      }
    }
    return this;
  }

  public Helm setDefaultButton(JButton button) {
    if (window instanceof JFrame) {
      ((JFrame) window).getRootPane().setDefaultButton(button);
    } else if (window instanceof JDialog) {
      ((JDialog) window).getRootPane().setDefaultButton(button);
    }
    return this;
  }

  public Helm pack() {
    window.pack();
    Dimension size = container.panel.getPreferredSize();
    size.width += 45;
    size.height += 45;
    window.setSize(size);
    WzdDesk.setNextLocationFor(window);
    return this;
  }

  public Helm setSize(int width, int height) {
    window.setSize(width, height);
    return this;
  }

  public Helm show() {
    window.setVisible(true);
    return this;
  }

  public Helm close() {
    callWindowClosing();
    if (window instanceof JFrame) {
      switch (((JFrame) window).getDefaultCloseOperation()) {
        case JFrame.DO_NOTHING_ON_CLOSE:
          break;
        case JFrame.HIDE_ON_CLOSE:
          ((JFrame) window).setVisible(false);
          callWindowClosed();
          break;
        case JFrame.DISPOSE_ON_CLOSE:
          ((JFrame) window).setVisible(false);
          ((JFrame) window).dispose();
          callWindowClosed();
          break;
        case JFrame.EXIT_ON_CLOSE:
          ((JFrame) window).setVisible(false);
          ((JFrame) window).dispose();
          callWindowClosed();
          System.exit(0);
          break;
      }
    } else if (window instanceof JDialog) {
      switch (((JDialog) window).getDefaultCloseOperation()) {
        case JDialog.DO_NOTHING_ON_CLOSE:
          break;
        case JDialog.HIDE_ON_CLOSE:
          ((JDialog) window).setVisible(false);
          callWindowClosed();
          break;
        case JDialog.DISPOSE_ON_CLOSE:
          ((JDialog) window).setVisible(false);
          ((JDialog) window).dispose();
          callWindowClosed();
          break;
        case JDialog.EXIT_ON_CLOSE:
          ((JDialog) window).setVisible(false);
          ((JDialog) window).dispose();
          callWindowClosed();
          System.exit(0);
          break;
      }
    }
    return this;
  }

  public Helm callWindowClosed() {
    window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSED));
    return this;
  }

  public Helm callWindowClosing() {
    window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    return this;
  }

  public Helm putShortcut(String name, String keyStroke, Runnable runnable) {
    if (container != null) {
      WzdDesk.putShortCut(container.getMain(), name, keyStroke, runnable);
    }
    return this;
  }
}
