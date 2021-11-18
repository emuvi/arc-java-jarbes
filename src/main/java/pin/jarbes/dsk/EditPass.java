package pin.jarbes.dsk;

import javax.swing.JPasswordField;
import pin.jarbox.val.Pass;

public class EditPass extends Edit<Pass> {

  private static final long serialVersionUID = -6818785472026198049L;

  private final JPasswordField field = new JPasswordField(9);

  public EditPass() {
    super();
    addMain(field);
  }

  @Override
  public Pass getValue() {
    return new Pass(new String(field.getPassword()).getBytes());
  }

  @Override
  public void setValue(Pass value) {
    field.setText(value != null ? value.getPassword() : "");
  }

}
