package pin.jarbes;

import pin.jarbox.Source;
import pin.jarbox.Variables;

public class ActCondition extends Act {

  private static final long serialVersionUID = -4555789390590705188L;

  public Source expression;
  public String goToIfTrue;
  public String goToElse;

  @Override
  public String execute(Variables variables) throws Exception {
    Object evaluated = variables.evaluate(expression.value);
    if (evaluated instanceof Boolean) {
      var result = (Boolean) evaluated;
      if (result) {
        if (goToIfTrue != null && !goToIfTrue.isEmpty()) {
          return goToIfTrue;
        } else {
          throw new Exception("The condition returned true but there was no valid Go To If True.");
        }
      } else {
        if (goToElse != null && !goToElse.isEmpty()) {
          return goToElse;
        } else {
          throw new Exception("The condition returned false but there was no valid Go To Else.");
        }
      }
    }
    throw new Exception("The condition does not returned a valid result.");
  }

}
