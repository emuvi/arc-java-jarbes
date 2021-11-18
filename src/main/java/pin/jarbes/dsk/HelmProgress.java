package pin.jarbes.dsk;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class HelmProgress extends Helm {

  public static int BUFFER_SIZE = 100;

  private final JTextArea text = new JTextArea(20, 40);
  private final JScrollPane scroll = new JScrollPane(text);
  private final String[] messages = new String[BUFFER_SIZE];
  private final AtomicBoolean calledProcess = new AtomicBoolean(false);
  private volatile int position = -1;
  private volatile int length = 0;

  public HelmProgress() {
    super(new JFrame("Progress"), new ColPanel());
    setDisposeOnClose();
    text.setBorder(new EmptyBorder(4, 4, 4, 4));
    text.setEditable(false);
    container.addMax(scroll);
  }

  public synchronized String processMessages() {
    StringBuilder builder = new StringBuilder(BUFFER_SIZE);
    if (length >= BUFFER_SIZE) {
      for (int i = position + 1; i < BUFFER_SIZE; i++) {
        builder.append(messages[i]);
      }
    }
    for (int i = 0; i <= position; i++) {
      builder.append(messages[i]);
    }
    return builder.toString();
  }

  public synchronized void append(final String message) {
    position++;
    if (position >= BUFFER_SIZE) {
      position = 0;
    }
    messages[position] = message + System.lineSeparator();
    if (length < BUFFER_SIZE) {
      length++;
    }
    if (calledProcess.compareAndSet(false, true)) {
      SwingUtilities.invokeLater(() -> {
        calledProcess.set(false);
        text.setText(processMessages());
      });
    }
  }
}
