package pin.jarbes;

public enum TypesAct {

  Back(ActBack.class, HelmActBack.class),

  Capture(ActCapture.class, HelmActCapture.class),

  Click(ActClick.class, HelmActClick.class),

  ClickDown(ActClickDown.class, HelmActClickDown.class),

  ClickUp(ActClickUp.class, HelmActClickUp.class),

  Condition(ActCondition.class, HelmActCondition.class),

  Done(ActDone.class, HelmActDone.class),

  Express(ActExpress.class, HelmActExpress.class),

  Find(ActFind.class, HelmActFind.class),

  GoTo(ActGoTo.class, HelmActGoTo.class),

  Halt(ActHalt.class, HelmActHalt.class),

  Hide(ActHide.class, HelmActHide.class),

  Log(ActLog.class, HelmActLog.class),

  Message(ActMessage.class, HelmActMessage.class),

  Pass(ActPass.class, HelmActPass.class),

  Press(ActPress.class, HelmActPress.class),

  PressDown(ActPressDown.class, HelmActPressDown.class),

  PressUp(ActPressUp.class, HelmActPressUp.class),

  Show(ActShow.class, HelmActShow.class),

  Sleep(ActSleep.class, HelmActSleep.class),

  Type(ActType.class, HelmActType.class);

  private final Class<? extends Act> type;
  private final Class<? extends HelmAct<?>> helm;

  private TypesAct(Class<? extends Act> type, Class<? extends HelmAct<?>> helm) {
    this.type = type;
    this.helm = helm;
  }

  public Class<? extends Act> getType() {
    return type;
  }

  public Class<? extends HelmAct<?>> getHelm() {
    return helm;
  }

  public static Class<? extends HelmAct<?>> getHelmClass(
      Class<? extends Act> ofValueClass) {
    for (TypesAct typeAct : TypesAct.values()) {
      if (typeAct.getType().equals(ofValueClass)) {
        return typeAct.getHelm();
      }
    }
    return null;
      }

}
