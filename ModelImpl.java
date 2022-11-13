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

  private String info;

  private ArrayList<InflexiblePortfolio> p_lists;

  private ArrayList<FlexiblePortfolio> flexList;

  /**
   * Constructor for ModelImpl.
   */
  public ModelImpl() throws FileNotFoundException {
    info = "";

    p_lists = new ArrayList<>();// old
    p_lists = constructP_list();
    flexList = new ArrayList<>();// new get datelist

  }

  public ArrayList<InflexiblePortfolio> getP_lists() {

    return p_lists;

  }

  @Override
  public void setInfo(String i) {

    info = i;

  }

  @Override
  public String getInfo() {

    return info;

  }

  public InflexiblePortfolio txt2port(File f) throws FileNotFoundException {
    InflexiblePortfolio p = new InflexiblePortfolio(f.getName().substring(0, f.getName().length() - 4));
    Scanner sc = new Scanner(f);
    while (sc.hasNextLine()) {
      String str = sc.nextLine();
      int index = str.indexOf(" ");
      Api api = new Api(str.substring(0, index));
      Stock s = new Stock(api);
      int share = Integer.parseInt(str.substring(index + 1));
      p.addStock(s, share);
    }
    return p;
  }

  public ArrayList<InflexiblePortfolio> constructP_list() throws FileNotFoundException {
    String dirPath = "./docs/inflexible";
    File dir = new File(dirPath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    ArrayList<File> fileList = getFile(dir);
    for (File file : fileList) {
      InflexiblePortfolio p = txt2port(file);
      p_lists.add(p);
    }
    return p_lists;
  }

  public static ArrayList<File> getFile(File file) {
    ArrayList<File> listLocal = new ArrayList<>();
    if (file != null) {
      File[] f = file.listFiles();
      if (f != null) {
        for (File value : f) {
          getFile(value);
          listLocal.add(value);
        }
      }
    }
    return listLocal;
  }


  @Override
  public String CreateInfPortfolio(String name) {
    String message = "";
    for (InflexiblePortfolio p_list : p_lists) {
      if (name.equals(p_list.getName())) {
        message = "This portfolio already exists.";
        return message;
      }
    }
    InflexiblePortfolio inflexiblePortfolio = new InflexiblePortfolio(name);

    p_lists.add(inflexiblePortfolio);
    message = "Creating new inflexible portfolio...";
    return message;
  }

  @Override
  public String CreateFlexPortfolio(String name) {
    String message = "";
    for (FlexiblePortfolio flexiblePortfolio : flexList) {
      if (name.equals(flexiblePortfolio.getName())) {
        message = "This portfolio already exists.";
        return message;
      }
    }
    FlexiblePortfolio flexiblePortfolio = new FlexiblePortfolio(name);

    flexList.add(flexiblePortfolio);
    message = "Creating new flexible portfolio...";
    return message;
  }


  @Override
  public void addStock(String p_name, String s_name, int share, String date) {

    if (date == null) {
      for (int i = 0; i < p_lists.size(); i++) {
        if (Objects.equals(p_name, p_lists.get(i).getName())) {
          p_lists.get(i).addStock(new Stock(new Api(s_name)), share);
        }
      }
    } else {
      for (int i = 0; i < flexList.size(); i++) {
        if (Objects.equals(p_name, flexList.get(i).getName())) {
          flexList.get(i).addStock(new Stock(new Api(s_name)), share, date);
        }
      }

    }

  }


  @Override
  public void saveInfPortfolio(String name) throws IOException {

    ArrayList<String> stockList = new ArrayList<>();
    ArrayList<Integer> shareList = new ArrayList<>();

    for (int i = 0; i < p_lists.size(); i++) {
      if (name.equals(p_lists.get(i).getName())) {
        stockList = p_lists.get(i).getStockName();
        shareList = p_lists.get(i).getShareList();
      }
    }
    String dirPath = "./docs/inflexible";
    File dir = new File(dirPath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String pathname = String.format("./docs/inflexible/%s.txt", name);
    File output = new File(pathname);
    if (output.exists()) {
      info = " already exist!";
    } else {
      FileOutputStream fos = new FileOutputStream(output);
      OutputStreamWriter dos = new OutputStreamWriter(fos);
      for (int i = 0; i < stockList.size(); i++) {
        String line = String.format("%s %d\n", stockList.get(i), shareList.get(i));
        dos.write(line);
      }
      dos.close();
      info = " successfully saved!";
    }
  }

  @Override
  public void saveFlexPortfolio(String name) throws IOException {

    ArrayList<String> stockList = new ArrayList<>();
    ArrayList<Integer> shareList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();

    for (int i = 0; i < flexList.size(); i++) {
      if (name.equals(flexList.get(i).getName())) {
        stockList = flexList.get(i).getStockName();
        shareList = flexList.get(i).getShareList();
        dateList = flexList.get(i).getDateList();
      }
    }

    String dirPath = "./docs/flexible";
    File dir = new File(dirPath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String pathname = String.format("./docs/flexible/%s.txt", name);
    File output = new File(pathname);
    if (output.exists()) {
      info = " already exist!";
    } else {
      FileOutputStream fos = new FileOutputStream(output);
      OutputStreamWriter dos = new OutputStreamWriter(fos);
      for (int i = 0; i < stockList.size(); i++) {
        String line = String.format("%s %d %s\n", stockList.get(i), shareList.get(i), dateList.get(i));
        dos.write(line);
      }
      dos.close();
      info = " successfully saved!";
    }
  }


  @Override
  public File openInfFile(String name) throws IOException {
    String pathname = String.format("./docs/inflexible/%s.txt", name);
    File f = new File(pathname);

    return f;
  }


  @Override
  public String checkInfValue(File f, String date) throws FileNotFoundException {
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


//  public void edit(String p_name,String s_name, int share, String date){
//    for( int i =0; i<flexList.size();i++){
//      if (Objects.equals(p_name, flexList.get(i).getName())){
//        flexList.get(i).addStock(new Stock(new Api(s_name)),share,date);
//      }
//    }


//  }


}
