import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Implementaion of View.
 */
public class ViewImpl implements View {

  private String message;

  @Override
  public void setMessage(String m) {
    message = m;
    System.out.println(message);
  }

  @Override
  public void clearMessage() {
    message = "";
  }



  @Override
  public void printPortfolio(File f) throws FileNotFoundException {
    System.out.println(
        "The composition of portfolio--" + f.getName().substring(0, f.getName().length() - 4)
            + " is:");
    Scanner sc = new Scanner(f);
    while (sc.hasNextLine()) {
      System.out.println(sc.nextLine());
    }
  }
}
