package pin.jarbes.dsk;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class Edit<T> extends JPanel {

  private static final long serialVersionUID = -8052465378546159046L;

  public Edit() {
    super(new BorderLayout(0, 0));
  }

  protected void addMain(JComponent component) {
    add(component, BorderLayout.CENTER);
  }

  public abstract T getValue();

  public abstract void setValue(T value);

  @SuppressWarnings(value = "unchecked")
  public Edit<T> defValue(Object value) {
    setValue((T) value);
    return this;
  }

}
