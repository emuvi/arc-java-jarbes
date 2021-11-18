package pin.jarbes.dsk;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class EditEnum extends Edit<Object> {

  private static final long serialVersionUID = -1731263758335392743L;

  private final DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();
  private final JComboBox<Object> field = new JComboBox<>(model);

  public EditEnum(Class<?> clazz) {
    super();
    model.addElement(null);
    for (Object option : clazz.getEnumConstants()) {
      model.addElement(option);
    }
    addMain(field);
  }

  @Override
  public Object getValue() {
    return field.getSelectedItem();
  }

  @Override
  public void setValue(Object value) {
    field.setSelectedItem(value);
  }

}
