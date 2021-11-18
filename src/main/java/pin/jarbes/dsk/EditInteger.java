package pin.jarbes.dsk;

import javax.swing.JTextField;

public class EditInteger extends Edit<Integer> {

  private static final long serialVersionUID = -4442551784218268278L;

  private final JTextField field = new JTextField(7);

  public EditInteger() {
    super();
    addMain(field);
  }

  @Override
  public Integer getValue() {
    return !field.getText().isBlank() ? Integer.parseInt(field.getText().trim()) : null;
  }

  @Override
  public void setValue(Integer value) {
    field.setText(value != null ? value.toString() : "");
  }

}
