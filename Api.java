import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class to obtain stock infomation from AlphaVantage.co.
 */
public class Api {

  String symbol;

  public Api(String symbol) {
    this.symbol = symbol;
  }

  /**
   * Get the information of a stock from a csv file. An API key is required to access AlphaVantage.
   *
   * @return the information of given stock.
   */
  public StringBuilder getInfo() {

    String apiKey = "MJ7SYPW8RD0JKULX";
    URL url;
    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */
      url = new URL("https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full"
          + "&symbol"
          + "=" + symbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
          + "no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      /*
      Execute this query. This returns an InputStream object.
      In the csv format, it returns several lines, each line being separated
      by commas. Each line contains the date, price at opening time, highest
      price for that date, lowest price for that date, price at closing time
      and the volume of trade (no. of shares bought/sold) on that date.

      This is printed below.
       */
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + symbol);
    }

    return output;
  }

  /**
   * Return an arraylist contains all time information of this stock.
   *
   * @return an arraylist contains all time information of this stock.
   */
  public ArrayList<String> getTimeList() {
    ArrayList<String> timeList = new ArrayList<>();
    Scanner scanner = new Scanner(String.valueOf(this.getInfo()));
    String line;
    while (scanner.hasNextLine()) {
      line = scanner.nextLine();
      // process the line
      String[] buff = line.split(",");
      timeList.add(buff[0]);
    }
    return timeList;
  }

  /**
   * Return an arraylist contains all open price information of this stock.
   *
   * @return an arraylist contains all open price information of this stock.
   */
  public ArrayList<String> getOpenList() {
    ArrayList<String> openList = new ArrayList<>();
    Scanner scanner = new Scanner(String.valueOf(this.getInfo()));
    String line;
    while (scanner.hasNextLine()) {
      line = scanner.nextLine();
      // process the line
      String[] buff = line.split(",");
      openList.add(buff[1]);
    }
    return openList;
  }


  /**
   * Return an arraylist contains all highest daily price information of this stock.
   *
   * @return an arraylist contains all highest daily price information of this stock.
   */
  public ArrayList<String> getHighList() {
    ArrayList<String> highList = new ArrayList<>();
    Scanner scanner = new Scanner(String.valueOf(this.getInfo()));
    String line;
    while (scanner.hasNextLine()) {
      line = scanner.nextLine();
      // process the line
      String[] buff = line.split(",");
      highList.add(buff[2]);
    }
    return highList;
  }

  /**
   * Return an arraylist contains all lowest daily price information of this stock.
   *
   * @return an arraylist contains all lowest daily price information of this stock.
   */
  public ArrayList<String> getLowList() {
    ArrayList<String> lowList = new ArrayList<>();
    Scanner scanner = new Scanner(String.valueOf(this.getInfo()));
    String line;
    while (scanner.hasNextLine()) {
      line = scanner.nextLine();
      // process the line
      String[] buff = line.split(",");
      lowList.add(buff[3]);
    }
    return lowList;
  }


  /**
   * Return an arraylist contains all close price information of this stock.
   *
   * @return an arraylist contains all close price information of this stock.
   */
  public ArrayList<String> getCloseList() {
    ArrayList<String> closeList = new ArrayList<>();
    Scanner scanner = new Scanner(String.valueOf(this.getInfo()));
    String line;
    while (scanner.hasNextLine()) {
      line = scanner.nextLine();
      // process the line
      String[] buff = line.split(",");
      closeList.add(buff[4]);
    }
    return closeList;
  }


  /**
   * Return an arraylist contains all total volume price information of this stock.
   *
   * @return an arraylist contains all total volume price information of this stock.
   */
  public ArrayList<String> getVolumeList() {
    ArrayList<String> volumeList = new ArrayList<>();
    Scanner scanner = new Scanner(String.valueOf(this.getInfo()));
    String line;
    while (scanner.hasNextLine()) {
      line = scanner.nextLine();
      // process the line
      String[] buff = line.split(",");
      volumeList.add(buff[5]);
    }
    return volumeList;
  }


}
