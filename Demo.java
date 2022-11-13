import java.io.IOException;

/**
 * Demo class to excute this MVC system.
 */
public class Demo {

  /**
   * Main.
   *
   * @param args main.
   * @throws IOException when error occurs opening files.
   */
  public static void main(String[] args) throws IOException {
    Model m = new ModelImpl();
    View v = new ViewImpl();


    Controller c = new ControllerImpl(m, v);
    c.run();
  }
}
