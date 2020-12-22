package pin.jarbes;

public class HelmActMessage extends HelmAct<ActMessage> {

  public HelmActMessage(HelmEditor owner, ActMessage initialValue) throws Exception {
    super(owner, ActMessage.class, initialValue);
  }

}
