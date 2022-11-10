import java.io.IOException;

/**
 * Controller Interface, takes user inputs, tells model what to do and view what to display.
 */
public interface Controller {

  /**
   * Main program running support, the user interface. Support other functions below.
   *
   * @throws IOException When exception occurs when openning file.
   */
  void run() throws IOException;

  /**
   * The function to let Model create a new portfolio from user input.
   *
   * @return Created portfolio.
   * @throws IOException When exception occurs when saving the created portfolio.
   */
  void createPortfolio() throws IOException;

  /**
   * The function to let Model examine the composition of existed portfolio from user input.
   *
   * @throws IOException When exception occurs when openning file.
   */
  void examinePortfolio() throws IOException;


  /**
   * The function to let Model save the created portfolio.
   *
   * @throws IOException When exception occurs when openning directory.
   */
  void savePortfolio(String name) throws IOException;

  /**
   * The function to let Model check the total value on certain date of existed portfolio from user
   * input.
   *
   * @throws IOException When exception occurs when openning file.
   */
  void checkValue() throws IOException;
}
