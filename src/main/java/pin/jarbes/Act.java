package pin.jarbes;

import java.io.Serializable;
import java.lang.reflect.Field;
import pin.jarbox.bin.Variables;

public abstract class Act implements Serializable {

  private static final long serialVersionUID = 4378542853265007821L;

  public String name;

  public String getName() {
    return name;
  }

  public abstract String execute(Variables variables) throws Exception;

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("[");
    result.append(getClass().getSimpleName());
    result.append("] (");
    result.append(name);
    result.append(") {");
    boolean primeiro = true;
    for (Field field : this.getClass().getFields()) {
      if (primeiro) {
        primeiro = false;
      } else {
        result.append(", ");
      }
      result.append(field.getName());
      result.append(" = '");
      try {
        result.append(String.valueOf(field.get(this)));
      } catch (Exception e) {
        result.append("<error>");
      }
      result.append("'");
    }
    result.append("}");
    return  result.toString();
  }

}
