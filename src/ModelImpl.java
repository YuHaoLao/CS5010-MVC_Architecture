import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * An implementation of Model.
 */

public class ModelImpl implements Model {

  private String input;

  private ArrayList<Portfolio> p_lists;

  private ArrayList<Portfolio> new_lists;

  /**
   * Constructor for ModelImpl.
   */
  public ModelImpl() {
    input = "";
    p_lists = new ArrayList<>(); // old
    new_lists = new ArrayList<>();  // new  get datelist
  }

  public ArrayList<Portfolio> getP_lists() {

    return p_lists;

  }

  @Override
  public void setInput(String i) {

    input = i;

  }

  @Override
  public String getInput() {

    return input;

  }




  @Override
  public String CreatePortfolio(String name) {
    String message = "";
    for (int i = 0; i < this.getP_lists().size(); i++) {
      if (Objects.equals(name, this.getP_lists().get(i).getName())) {

        message = "name already exits";
        return message;
      }
    }
    Portfolio portfolio = new Portfolio(name);

    this.getP_lists().add(portfolio);
    message = "created";
    return message;
  }

  @Override
  public void addStock(String p_name, String s_name, int share) {

    for (int i = 0; i < this.getP_lists().size(); i++) {
      if (Objects.equals(p_name, this.getP_lists().get(i).getName())) {
        this.getP_lists().get(i).addStock(new Stock(new Api(s_name)), share);
      }
    }
  }


  @Override
  public void savePortfolio(String name) throws IOException {


    ArrayList<String> stockList = new ArrayList<>();
    ArrayList<Integer> shareList = new ArrayList<>();


    for (int i = 0; i < this.getP_lists().size(); i++) {
      if (name.equals(this.getP_lists().get(i).getName())) {
        stockList = this.getP_lists().get(i).getStockName();
        shareList = this.getP_lists().get(i).getShareList();
      }
    }
    String dirPath = "./docs";
    File dir = new File(dirPath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String pathname = String.format("./docs/%s.txt", name);
    File output = new File(pathname);
    if (output.exists()) {
      input = " already exist!";
    } else {
      FileOutputStream fos = new FileOutputStream(output);
      OutputStreamWriter dos = new OutputStreamWriter(fos);
      for (int i = 0; i < stockList.size(); i++) {
        String line = String.format("%s %d\n", stockList.get(i), shareList.get(i));
        dos.write(line);
      }
      dos.close();
      input = " successfully saved!";
    }
  }


  @Override
  public File openFile(String name) throws IOException {
    String pathname = String.format("./docs/%s.txt", name);
    File f = new File(pathname);

    return f;
  }


  @Override
  public String checkValue(File f, String date) throws FileNotFoundException {
    double value = 0;
    Scanner sc = new Scanner(f);
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      int space = line.indexOf(" ");
      Api api = new Api(line.substring(0, space));
      int index = api.getInfo().indexOf(date);
      if (index == -1) {
        return "-1";
      }
      int share = Integer.parseInt(line.substring(space + 1));
      value += Double.parseDouble((api.getCloseList().get(index))) * share;
    }
    return String.valueOf(value);
  }
}
