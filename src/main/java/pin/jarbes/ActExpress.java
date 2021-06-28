package pin.jarbes;

import pin.jarbox.val.Source;
import pin.jarbox.bin.Variables;

public class ActExpress extends Act {

  private static final long serialVersionUID = -940056905325085818L;

  public Source expression;

  @Override
  public String execute(Variables variables) throws Exception {
    Object evaluated = variables.evaluate(expression.value);
    if (evaluated instanceof String) {
      return (String) evaluated;
    } else {
      return "<next>";
    }
  }

}
