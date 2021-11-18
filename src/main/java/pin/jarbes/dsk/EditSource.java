package pin.jarbes.dsk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Caret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import pin.jarbox.bin.Token;
import pin.jarbox.bin.Tokenizer;
import pin.jarbox.val.Source;

public class EditSource extends Edit<Source> {

  private static final long serialVersionUID = 5730182675717964344L;

  private final JTextPane field = new JTextPane();
  private final JPanel panel;
  private final JScrollPane scroll;

  private final SimpleAttributeSet defaultSet = new SimpleAttributeSet();
  private final SimpleAttributeSet keywordSet = new SimpleAttributeSet();
  private final SimpleAttributeSet variableSet = new SimpleAttributeSet();
  private final SimpleAttributeSet stringSet = new SimpleAttributeSet();
  private final SimpleAttributeSet numberSet = new SimpleAttributeSet();
  private final SimpleAttributeSet blockSet = new SimpleAttributeSet();
  private final SimpleAttributeSet underSet = new SimpleAttributeSet();
  private final SimpleAttributeSet unknownSet = new SimpleAttributeSet();

  private final StyledDocument document = field.getStyledDocument();
  private final Caret caret = field.getCaret();

  public EditSource() {
    super();
    field.setBackground(new Color(252, 252, 243));
    field.setForeground(new Color(36, 18, 18));
    field.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
    panel = new JPanel(new BorderLayout());
    panel.add(field);
    scroll = new JScrollPane(panel);
    addMain(scroll);
    initSettings();
  }

  private void initSettings() {
    StyleConstants.setBackground(defaultSet, field.getBackground());
    StyleConstants.setForeground(defaultSet, field.getForeground());
    StyleConstants.setLineSpacing(defaultSet, 0.2f);
    StyleConstants.setUnderline(defaultSet, false);
    StyleConstants.setForeground(keywordSet, new Color(128, 0, 128));
    StyleConstants.setForeground(variableSet, new Color(0, 0, 128));
    StyleConstants.setForeground(stringSet, new Color(0, 128, 0));
    StyleConstants.setForeground(numberSet, new Color(0, 128, 128));
    StyleConstants.setForeground(blockSet, new Color(128, 128, 0));
    StyleConstants.setForeground(unknownSet, new Color(210, 0, 0));
    StyleConstants.setUnderline(underSet, true);
    document.setCharacterAttributes(0, document.getLength(), defaultSet, false);
    document.setParagraphAttributes(0, document.getLength(), defaultSet, false);
    caret.addChangeListener(new CaretListener());
    document.addDocumentListener(new UpdateListener());
    Dimension dimension = new Dimension(540, 54);
    scroll.setMinimumSize(dimension);
    scroll.setPreferredSize(dimension);
    startHighlighter();
  }

  @Override
  public Source getValue() {
    return new Source(field.getText());
  }

  @Override
  public void setValue(Source value) {
    field.setText(value != null ? value.value : "");
  }

  private final String HIGHLIGHTER_NAME = "SourceHighlighter";

  private final AtomicBoolean cleaningLineBreaks = new AtomicBoolean(false);
  private final AtomicBoolean runningHighlighter = new AtomicBoolean(false);
  private final AtomicBoolean mustRedoHighlighter = new AtomicBoolean(false);

  public synchronized void startHighlighter() {
    if (HIGHLIGHTER_NAME.equals(Thread.currentThread().getName())) {
      return;
    }
    if (!cleaningLineBreaks.get()) {
      cleaningLineBreaks.set(true);
      SwingUtilities.invokeLater(() -> {
        try {
          var source = field.getText();
          if (source.contains("\r") || source.contains("\n")) {
            int start = field.getSelectionStart();
            int end = field.getSelectionEnd();
            int dot = caret.getDot();
            field.setText(source.replaceAll("\\r\\n|\\n|\\r", " "));
            field.setSelectionStart(start);
            field.setSelectionEnd(end);
            caret.setDot(dot);
          }
        } catch (Exception e) {
        } finally {
          cleaningLineBreaks.set(false);
        }
      });
    }
    if (!runningHighlighter.get()) {
      runningHighlighter.set(true);
      mustRedoHighlighter.set(true);
      SwingUtilities.invokeLater(() -> {
        new Highlighter().start();
      });
    } else {
      mustRedoHighlighter.set(true);
    }
  }

  private synchronized void finalizedHighlighter() {
    runningHighlighter.set(false);
  }

  private class Highlighter extends Thread {

    public Highlighter() {
      super(HIGHLIGHTER_NAME);
    }

    @Override
    public void run() {
      try {
        while (mustRedoHighlighter.get()) {
          mustRedoHighlighter.set(false);
          final String source = field.getText();
          final int blockIndex = caret.getDot() - 1;
          int relativeBlock = -1;
          List<Token> tokens = new Tokenizer(source).get();
          document.setCharacterAttributes(0, document.getLength(), defaultSet, false);
          document.setParagraphAttributes(0, document.getLength(), defaultSet, false);
          for (int it = 0; it < tokens.size(); it++) {
            if (mustRedoHighlighter.get()) {
              break;
            }
            final Token token = tokens.get(it);
            if (token.type == Token.Type.STRING) {
              document.setCharacterAttributes(token.start, token.source.length(),
                  stringSet, false);
            } else if (token.type == Token.Type.NUMBER) {
              document.setCharacterAttributes(token.start, token.source.length(),
                  numberSet, false);
            } else if (token.type == Token.Type.KEYWORD) {
              document.setCharacterAttributes(token.start, token.source.length(),
                  keywordSet, false);
            } else if (token.type == Token.Type.VARIABLE) {
              document.setCharacterAttributes(token.start, token.source.length(),
                  variableSet, false);
            } else if (token.type == Token.Type.BLOCK) {
              if (token.start == blockIndex) {
                document.setCharacterAttributes(token.start, token.source.length(),
                    blockSet, false);
                final int adder = token.source.equals("(") ? 1 : -1;
                final String expected = token.source.equals("(") ? ")" : "(";
                int sameFound = 0;
                for (int ib = it + adder; ib >= 0 && ib < tokens.size(); ib = ib
                    + adder) {
                  if (token.source.equals(tokens.get(ib).source)) {
                    sameFound++;
                  } else if (expected.equals(tokens.get(ib).source)) {
                    if (sameFound == 0) {
                      relativeBlock = ib;
                      document.setCharacterAttributes(tokens.get(ib).start, tokens.get(
                          ib).source.length(), blockSet, false);
                      int blockStart = Math.min(token.start, tokens.get(ib).start);
                      int blockEnd = Math.max(token.start, tokens.get(ib).start);
                      document.setCharacterAttributes(blockStart, blockEnd - blockStart
                          + 1, underSet, false);
                      break;
                    } else {
                      sameFound--;
                    }
                  }
                }
              } else if (it != relativeBlock) {
                document.setCharacterAttributes(token.start, token.source.length(),
                    keywordSet, false);
              }
            } else {
              document.setCharacterAttributes(token.start, token.source.length(),
                  unknownSet, false);
            }
          }
        }
      } catch (Exception e) {
      } finally {
        finalizedHighlighter();
      }
    }

  }

  private class CaretListener implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
      startHighlighter();
    }
  }

  private class UpdateListener implements DocumentListener {

    @Override
    public void insertUpdate(DocumentEvent e) {
      startHighlighter();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
      startHighlighter();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
      startHighlighter();
    }
  }

}
