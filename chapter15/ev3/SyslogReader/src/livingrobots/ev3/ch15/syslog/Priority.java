package livingrobots.ev3.ch15.syslog;

public enum Priority {
  FATAL(0),
  ERROR(1),
  WARN(2),
  INFO(3),
  DEBUG(4),
  TRACE(5);

  private final int value;

  private Priority(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public static Priority findByValue(int value) { 
    switch (value) {
      case 0:
        return FATAL;
      case 1:
        return ERROR;
      case 2:
        return WARN;
      case 3:
        return INFO;
      case 4:
        return DEBUG;
      case 5:
        return TRACE;
      default:
        return null;
    }
  }
}