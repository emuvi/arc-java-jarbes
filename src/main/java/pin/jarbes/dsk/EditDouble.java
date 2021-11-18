package pin.jarbes.dsk;

import javax.swing.JTextField;

public class EditDouble extends Edit<Double> {

  private static final long serialVersionUID = -4731706758078413802L;

  private final JTextField field = new JTextField(9);

  public EditDouble() {
    super();
    addMain(field);
  }

  @Override
  public Double getValue() {
    return !field.getText().isBlank() ? Double.parseDouble(field.getText().trim()) : null;
  }

  @Override
  public void setValue(Double value) {
    field.setText(value != null ? value.toString() : "");
  }

}
