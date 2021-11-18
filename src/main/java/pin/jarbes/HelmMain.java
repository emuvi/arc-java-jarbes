package pin.jarbes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import pin.jarbox.wzd.WzdDesk;
import pin.jarbox.wzd.WzdLog;
import pin.jarbes.dsk.HelmLogged;
import pin.jarbox.bin.Variables;

public class HelmMain {

  private final JFrame window = new JFrame("Jarbes");
  private final MouseHandler mouse = new MouseHandler();
  private final JPanel panel = new JPanel(new BorderLayout());
  private final JLabel label = new JLabel();
  private final JPopupMenu menu = new JPopupMenu();
  private final JMenuItem jarbesItem = new JMenuItem("Jarbes");
  private final JMenuItem actionsItem = new JMenuItem("Actions");
  private final JMenuItem startStopItem = new JMenuItem("Start");
  private final JMenuItem pauseResumeItem = new JMenuItem("Pause");
  private final JMenuItem captureItem = new JMenuItem("Capture");
  private final JMenuItem loggedItem = new JMenuItem("Logged");
  private final JMenuItem exitItem = new JMenuItem("Exit");

  private final HeadActs manager = new HeadActs();

  private volatile boolean running = false;
  private volatile boolean paused = false;
  private volatile boolean stop = false;

  public HelmMain() throws Exception {
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setIconImage(
        ImageIO.read(HelmMain.class.getResourceAsStream("jarbes.png")));
    window.setUndecorated(true);
    window.setBounds(48, 48, 48, 48);
    window.addMouseListener(mouse);
    window.addMouseMotionListener(mouse);
    window.setContentPane(panel);
    panel.setFocusable(true);
    panel.setOpaque(false);
    panel.setBackground(new Color(0, 0, 0, 0));
    panel.add(label, BorderLayout.CENTER);
    label.setOpaque(false);
    label.setBackground(new Color(0, 0, 0, 0));
    label.setIcon(new ImageIcon(
          ImageIO.read(HelmMain.class.getResourceAsStream("jarbes-48.png"))));
    jarbesItem.setMnemonic('J');
    actionsItem.setMnemonic('A');
    startStopItem.setMnemonic('S');
    pauseResumeItem.setMnemonic('E');
    captureItem.setMnemonic('C');
    loggedItem.setMnemonic('L');
    exitItem.setMnemonic('X');
    menu.add(jarbesItem);
    menu.add(new JSeparator());
    menu.add(actionsItem);
    menu.add(startStopItem);
    menu.add(pauseResumeItem);
    menu.add(new JSeparator());
    menu.add(captureItem);
    menu.add(loggedItem);
    menu.add(exitItem);
    jarbesItem.addActionListener(event -> { menuJarbes(); });
    actionsItem.addActionListener(event -> { menuActions(); });
    startStopItem.addActionListener(event -> { menuStartStop(); });
    pauseResumeItem.addActionListener(event -> { menuPauseResume(); });
    pauseResumeItem.setEnabled(false);
    captureItem.addActionListener(event -> { menuCapture(); });
    loggedItem.addActionListener(event -> { menuLogged(); });
    exitItem.addActionListener(event -> { System.exit(0); });
    initKeyShortcuts();
  }

  private void initKeyShortcuts() {
    WzdDesk.putShortCut(panel, "MenuChar", "M", () -> showMenu());
    WzdDesk.putShortCut(panel, "MenuControlChar", "control M", () -> showMenu());
    WzdDesk.putShortCut(panel, "JarbesChar", "J", () -> menuJarbes());
    WzdDesk.putShortCut(panel, "JarbesControlChar", "control J",
        () -> menuJarbes());
    WzdDesk.putShortCut(panel, "ActionsChar", "A", () -> menuActions());
    WzdDesk.putShortCut(panel, "ActionsControlChar", "control A",
        () -> menuActions());
    WzdDesk.putShortCut(panel, "StartStopChar", "S", () -> menuStartStop());
    WzdDesk.putShortCut(panel, "StartStopControlChar", "control S",
        () -> menuStartStop());
    WzdDesk.putShortCut(panel, "PauseResumeChar", "E", () -> menuPauseResume());
    WzdDesk.putShortCut(panel, "PauseResumeControlChar", "control E",
        () -> menuPauseResume());
    WzdDesk.putShortCut(panel, "CaptureChar", "C", () -> menuCapture());
    WzdDesk.putShortCut(panel, "CaptureControlChar", "control C",
        () -> menuCapture());
    WzdDesk.putShortCut(panel, "LoggedChar", "L", () -> menuLogged());
    WzdDesk.putShortCut(panel, "LoggedControlChar", "control L",
        () -> menuLogged());
    WzdDesk.putShortCut(panel, "ExitChar", "X", () -> System.exit(0));
    WzdDesk.putShortCut(panel, "ExitControlChar", "control X",
        () -> System.exit(0));
    WzdDesk.putShortCut(panel, "Exit", "ESCAPE", () -> System.exit(0));
  }

  public JFrame getWindow() { return window; }

  public void show() { window.setVisible(true); }

  public void start() {
    if (!running) {
      running = true;
      paused = false;
      stop = false;
      startStopItem.setText("Stop");
      pauseResumeItem.setText("Pause");
      pauseResumeItem.setEnabled(true);
      new Runner().start();
    }
  }

  public void set(ListActs actions) { manager.set(actions); }

  private void toggle() {
    if (!running) {
      start();
    } else {
      if (paused) {
        resume();
      } else {
        pause();
      }
    }
  }

  private void pause() {
    paused = true;
    pauseResumeItem.setText("Resume");
  }

  private void resume() {
    paused = false;
    pauseResumeItem.setText("Pause");
  }

  private void stop() {
    stop = true;
    startStopItem.setText("Stopping");
  }

  private void stopped() {
    running = false;
    paused = false;
    stop = false;
    startStopItem.setText("Start");
    pauseResumeItem.setText("Pause");
    pauseResumeItem.setEnabled(false);
  }

  private void menuJarbes() {
    try {
      Desktop.getDesktop().browse(
          new URI("http://www.pointel.com.br/apps/jarbes"));
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  private void menuStartStop() {
    if (running) {
      stop();
    } else {
      start();
    }
  }

  private void menuPauseResume() {
    if (paused) {
      resume();
    } else {
      pause();
    }
  }

  private void menuActions() {
    try {
      new HelmEditor(this).loadAndShow(manager);
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  private void menuCapture() {
    try {
      new HelmCapture().show();
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  private void menuLogged() {
    try {
      new HelmLogged(window).show();
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  private void showMenu() { menu.show(panel, 0, panel.getHeight()); }

  private class MouseHandler extends MouseAdapter {

    private Point mouseStart;
    private Point windowStart;

    @Override
    public void mousePressed(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) {
        mouseStart = e.getLocationOnScreen();
        windowStart = window.getLocation();
      }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) {
        Point actual = e.getLocationOnScreen();
        window.setLocation(windowStart.x + (actual.x - mouseStart.x),
            windowStart.y + (actual.y - mouseStart.y));
      }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() >= 2) {
        toggle();
      } else if (e.getButton() == MouseEvent.BUTTON3) {
        showMenu();
      }
    }
  }

  private class Runner extends Thread {

    private boolean erros = false;
    private Variables variables = new Variables();

    public Runner() { super("Runner"); }

    @Override
    public void run() {
      try {
        int index = 0;
        while (!stop) {
          if (index >= manager.size()) {
            WzdLog.treat("Actions complete.");
            break;
          }
          Act act = manager.get(index);
          WzdLog.treat("Calling Act %d = %s", index, act.toString());
          String result = act.execute(variables);
          WzdLog.treat("Act %d (%s) executed.", index, act.name);
          if (result == null || result.isBlank() || result.equals("<next>")) {
            index++;
          } else if (result.equals("<previous>")) {
            index--;
          } else if (result.equals("<last>")) {
            index = manager.size() - 1;
          } else if (result.equals("<first>")) {
            index = 0;
          } else if (result.equals("<done>")) {
            index = manager.size();
          } else if (result.equals("<halt>")) {
            System.exit(0);
          } else if (result.startsWith("+")) {
            index += Integer.parseInt(result.substring(1));
          } else if (result.startsWith("-")) {
            index -= Integer.parseInt(result.substring(1));
          } else if (result.startsWith("=")) {
            index = Integer.parseInt(result.substring(1));
          } else {
            index = manager.getIndex(result);
          }
          if (index < 0) {
            throw new Exception(
                String.format("Could not get a valid act for: '%s'.", result));
          }
          while (paused && !stop) {
            sleep(100);
          }
        }
      } catch (Exception e) {
        erros = true;
        WzdLog.treat(e, true);
        WzdLog.treat("Actions terminated.");
      } finally {
        try {
          new ActShow().execute(variables);
        } catch (Exception e) {
          erros = true;
          WzdLog.treat(e, true);
        }
        SwingUtilities.invokeLater(() -> {
          stopped();
          JOptionPane.showMessageDialog(
              window, "Actions complete" + (erros ? " with erros" : "") + ".",
              "Jarbes",
              (erros ? JOptionPane.ERROR_MESSAGE
               : JOptionPane.INFORMATION_MESSAGE));
        });
      }
    }
  }
}
