import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test class for the stock portfolio management MVC system.
 */
public class TestMVC {

  private Model m;
  private Controller c;
  private View v;
  private File f;

  private static ByteArrayInputStream inp;
  private static ByteArrayOutputStream info;
  private static PrintStream console;

  public void setInput(String input) {
    inp = new ByteArrayInputStream(input.getBytes());
    System.setIn(inp);
  }

  @Before
  public void setUp() throws IOException {
    m = new ModelImpl();
    v = new ViewImpl();
    c = new ControllerImpl(m, v);
    info = null;
    console = null;
  }

  /**
   * Test controller to create a new portfolio and save it, name = test; Composition: MSFT 200; IBM
   * 500.
   */
  @Test
  public void testCreateNewPortfolioAndSave() throws IOException {
    String inputMess = "test\n"
        + "msft\n"
        + "200\n"
        + "y\n"
        + "ibm\n"
        + "500\n"
        + "n\n"
        + "";
    setInput(inputMess);

    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    Portfolio p = c.createPortfolio();
    f = new File("./docs/test.txt");

    System.setOut(console);
    assertEquals("Please enter the name of this portfolio:\n"
        + "Creating new portfolio: test\n"
        + "Please enter the stock you wish to purchase:\n"
        + "Please enter the number of share you wish to purchase:\n"
        + "Do you want to purchase other stock for this portfolio?(y/n)\n"
        + "Please enter the stock you wish to purchase:\n"
        + "Please enter the number of share you wish to purchase:\n"
        + "Do you want to purchase other stock for this portfolio?(y/n)\n"
        + "Portfolio test successfully saved!", info.toString().trim());
    assertEquals("test.txt", f.getName());
  }

  /**
   * Test if portfolio test is correctly created and saved.
   */
  @Test
  public void testSavedPortfolio() {
    f = new File("./docs/test.txt");
    assertEquals("test.txt", f.getName());
  }

  /**
   * Test controller to create test portfolio again; Composition: MSFT 200; IBM 500.
   */
  @Test
  public void testCreateExistedPortfolio() throws IOException {
    String inputMess = "test\n"
        + "msft\n"
        + "200\n"
        + "y\n"
        + "ibm\n"
        + "500\n"
        + "n\n"
        + "";
    setInput(inputMess);

    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    Portfolio p = c.createPortfolio();

    System.setOut(console);
    assertEquals("Please enter the name of this portfolio:\n"
        + "Creating new portfolio: test\n"
        + "Please enter the stock you wish to purchase:\n"
        + "Please enter the number of share you wish to purchase:\n"
        + "Do you want to purchase other stock for this portfolio?(y/n)\n"
        + "Please enter the stock you wish to purchase:\n"
        + "Please enter the number of share you wish to purchase:\n"
        + "Do you want to purchase other stock for this portfolio?(y/n)\n"
        + "Portfolio test already exist!", info.toString().trim());
  }


  /**
   * Test Model to examine the composition of portfolio test.
   */
  @Test
  public void testModelExamine() throws IOException {
    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    f = m.openFile("test");
    v.printPortfolio(f);

    System.setOut(console);
    assertEquals("The composition of portfolio--test is:\n"
        + "msft 200\n"
        + "ibm 500", info.toString().trim());
  }

  /**
   * Test Model to examine the composition of a not existed portfolio.
   */
  @Test(expected = IOException.class)
  public void testModelExamineNotExist() throws IOException {
    f = m.openFile("notExisted");
    v.printPortfolio(f);
  }


  /**
   * Test Controller to take invalid share input(less than 200).
   */
  @Test
  public void testModelTakeInvalidShareInput1() throws IOException {
    String inputMess = "test\n"
        + "msft\n"
        + "100\n"
        + "200\n"
        + "n\n"
        + "";
    setInput(inputMess);

    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    Portfolio p = c.createPortfolio();

    System.setOut(console);
    assertEquals("Please enter the name of this portfolio:\n"
        + "Creating new portfolio: test\n"
        + "Please enter the stock you wish to purchase:\n"
        + "Please enter the number of share you wish to purchase:\n"
        + "share must be positive and no less than 200\n"
        + "Do you want to purchase other stock for this portfolio?(y/n)\n"
        + "Portfolio test already exist!", info.toString().trim());
  }

  /**
   * Test Controller to take invalid share input(not int).
   */
  @Test
  public void testModelTakeInvalidShareInput2() throws IOException {
    String inputMess = "test\n"
        + "msft\n"
        + "abc\n"
        + "200\n"
        + "n\n"
        + "";
    setInput(inputMess);

    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    Portfolio p = c.createPortfolio();

    System.setOut(console);
    assertEquals("Please enter the name of this portfolio:\n"
        + "Creating new portfolio: test\n"
        + "Please enter the stock you wish to purchase:\n"
        + "Please enter the number of share you wish to purchase:\n"
        + "share must be positive and no less than 200\n"
        + "Do you want to purchase other stock for this portfolio?(y/n)\n"
        + "Portfolio test already exist!", info.toString().trim());
  }

  /**
   * Test the function of Model to open a portfolio.
   */
  @Test
  public void testModelOpenPortfolio() throws IOException {
    f = m.openFile("health");
    assertEquals("health.txt", f.getName());
  }

  /**
   * Test the function of Model to open a not existed portfolio.
   */
  @Test(expected = IOException.class)
  public void testModelOpenNullPortfolio() throws IOException {
    f = m.openFile("notExist");
    v.printPortfolio(f);
  }

  /**
   * Test the function of Model take parameters from controller.
   */
  @Test
  public void testModelGetPara() {
    m.setInput("input");
    assertEquals("input", m.getInput());
  }

  /**
   * Test the function of View to print message.
   */
  @Test
  public void testViewPrint() {
    v.setMessage("input");

    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    v.print();

    System.setOut(console);
    assertEquals("input", info.toString().trim());
  }

  /**
   * Test the function of View to clear message.
   */
  @Test
  public void testViewClear() {
    v.setMessage("input");
    v.clearMessage();

    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    v.print();

    System.setOut(console);
    assertEquals("", info.toString().trim());
  }

  /**
   * Test the function of controller to examine the composition of portfolio test.
   */
  @Test
  public void testExamine() throws IOException {
    String inputMess = "test\n"
        + "";
    setInput(inputMess);

    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    c.examinePortfolio();

    System.setOut(console);
    assertEquals("Enter the name of the portfolio you want to examine: \n"
        + "The composition of portfolio--test is:\n"
        + "msft 200\n"
        + "ibm 500", info.toString().trim());
  }

  /**
   * Test the function of controller to examine the composition of a not existed portfolio.
   */
  @Test
  public void testExamineNotExist() throws IOException {
    String inputMess = "notexist\n"
        + "test\n"
        + "";
    setInput(inputMess);

    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    c.examinePortfolio();

    System.setOut(console);
    assertEquals("nter the name of the portfolio you want to examine: \n"
        + "Portfolio notexist not exist! Please enter correctly: \n"
        + "The composition of portfolio--test is:\n"
        + "msft 200\n"
        + "ibm 500", info.toString().trim());
  }

  /**
   * Test the function of controller to check the value of portfolio test on 2022-10-31.
   */
  @Test
  public void testCheckValue() throws IOException {
    String inputMess = "test\n"
        + "2022-10-31\n";
    setInput(inputMess);

    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    c.checkValue();

    System.setOut(console);
    assertEquals("Enter the name of the portfolio you want to check value: \n"
            + "Enter the date you want to check value(yyyy-mm-d(no prefix 0 for days)): \n"
            + "The total value of portfolio--test on Date 2022-10-31 is:118455.0",
        info.toString().trim());
  }

  /**
   * Test the function of controller to check the value of a not existed portfolio.
   */
  @Test
  public void testCheckValueOnNotExisted() throws IOException {
    String inputMess = "notExisted\n"
        + "test\n"
        + "2022-10-31\n";
    setInput(inputMess);

    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    c.checkValue();

    System.setOut(console);
    assertEquals("Enter the name of the portfolio you want to check value: \n"
            + "Portfolio notExisted not exist! Please enter correctly: \n"
            + "Enter the date you want to check value(yyyy-mm-d(no prefix 0 for days)): \n"
            + "The total value of portfolio--test on Date 2022-10-31 is:118455.0",
        info.toString().trim());
  }

  /**
   * Test the function of controller to check the value of a not existed date.
   */
  @Test
  public void testCheckValueOnNotExistedDate() throws IOException {
    String inputMess = "test\n"
        + "2022-10-40\n"
        + "2022-10-31\n";
    setInput(inputMess);

    info = new ByteArrayOutputStream();
    console = System.out;
    System.setOut(new PrintStream(info));

    c.checkValue();

    System.setOut(console);
    assertEquals("Enter the name of the portfolio you want to check value: \n"
            + "Enter the date you want to check value(yyyy-mm-d(no prefix 0 for days)): \n"
            + "Please enter correct date(yyyy-mm-d(no prefix 0 for days)): \n"
            + "The total value of portfolio--test on Date 2022-10-31 is:118455.0",
        info.toString().trim());
  }
}
