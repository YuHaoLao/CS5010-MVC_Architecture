import java.io.File;
import java.io.FileNotFoundException;

/**
 * View Interface, display results to user.
 */
public interface View {

  /**
   * Get the String message from controller, pass into view.
   *
   * @param m String message.
   */
  void setMessage(String m);

  /**
   * Clear message in view.
   */
  void clearMessage();

  /**
   * Print current stored message in view.
   */

  /**
   * Print the composition of certain portfolio.
   */
  void printPortfolio(File f) throws FileNotFoundException;
}
