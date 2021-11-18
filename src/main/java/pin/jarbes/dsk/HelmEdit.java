package pin.jarbes.dsk;

import java.awt.FlowLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pin.jarbox.val.Local;
import pin.jarbox.val.Pass;
import pin.jarbox.val.Picture;
import pin.jarbox.val.Source;
import pin.jarbox.val.Zone;
import pin.jarbox.wzd.WzdChars;
import pin.jarbox.wzd.WzdDesk;
import pin.jarbox.wzd.WzdLog;

public class HelmEdit<T> extends Helm {

  private final Class<T> clazz;
  private final T value;
  private final boolean returnNewValue;
  private final Function<T, Boolean> returnFunction;

  private final Map<String, Edit<?>> edits = new HashMap<>();
  private final List<JComponent> labels = new ArrayList<>();

  public HelmEdit(Class<T> clazz, T value, boolean returnNewValue,
      Function<T, Boolean> returnFunction) throws Exception {
    super(new JFrame("Edit " + WzdChars.insertSpaceInUppers(clazz.getSimpleName())),
        new ColPanel(true));
    this.clazz = clazz;
    this.value = value;
    this.returnNewValue = returnNewValue;
    this.returnFunction = returnFunction;
    setDisposeOnClose();
    setIcon(Icons.getFormEdit());
    initFields();
    closeFields();
    pack();
  }

  private void initFields() throws Exception {
    for (Field field : clazz.getFields()) {
      if (field.getType().isEnum()) {
        addField(field, new EditEnum(field.getType()));
      } else if (field.getType().isAssignableFrom(Boolean.class)) {
        addField(field, new EditBoolean());
      } else if (field.getType().isAssignableFrom(Integer.class)) {
        addField(field, new EditInteger());
      } else if (field.getType().isAssignableFrom(Double.class)) {
        addField(field, new EditDouble());
      } else if (field.getType().isAssignableFrom(String.class)) {
        addField(field, new EditString());
      } else if (field.getType().isAssignableFrom(Pass.class)) {
        addField(field, new EditPass());
      } else if (field.getType().isAssignableFrom(Source.class)) {
        addField(field, new EditSource());
      } else if (field.getType().isAssignableFrom(Local.class)) {
        addField(field, new EditLocal());
      } else if (field.getType().isAssignableFrom(Zone.class)) {
        addField(field, new EditZone());
      } else if (field.getType().isAssignableFrom(Picture.class)) {
        addField(field, new EditPicture());
      }
    }
  }

  private void addField(Field field, Edit<?> edit) throws Exception {
    if (value != null) {
      edit.defValue(field.get(value));
    }
    JLabel label = new JLabel(WzdChars.toUpperFirstChar(WzdChars.insertSpaceInUppers(field
        .getName())) + ": ");
    JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
    line.add(label);
    line.add(edit);
    container.add(line);
    edits.put(field.getName(), edit);
    labels.add(label);
  }

  private void closeFields() {
    WzdDesk.setWidthMinAsPreferredMax(labels.toArray(new JComponent[labels.size()]));
    JButton confirm = new JButton("Confirm");
    confirm.addActionListener(event -> confirm());
    JButton cancel = new JButton("Cancel");
    cancel.addActionListener(event -> close());
    JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
    line.add(confirm);
    line.add(cancel);
    container.add(line);
    setDefaultButton(confirm);
    container.close();
  }

  private T setFieldsValues(T overValues) throws Exception {
    for (var entry : edits.entrySet()) {
      Field field = clazz.getField(entry.getKey());
      field.set(overValues, entry.getValue().getValue());
    }
    return overValues;
  }

  public T getValue() throws Exception {
    if (returnNewValue) {
      T newValue = clazz.getConstructor().newInstance();
      return setFieldsValues(newValue);
    } else {
      return setFieldsValues(value);
    }
  }

  private void confirm() {
    try {
      if (returnFunction.apply(getValue())) {
        close();
      }
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }
}
