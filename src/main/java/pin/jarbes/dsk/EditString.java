package pin.jarbes.dsk;

import javax.swing.JTextField;

public class EditString extends Edit<String> {

  private static final long serialVersionUID = -6818785472026198049L;

  private final JTextField field = new JTextField(18);

  public EditString() {
    super();
    addMain(field);
  }

  @Override
  public String getValue() {
    return field.getText();
  }

  @Override
  public void setValue(String value) {
    field.setText(value);
  }

}
