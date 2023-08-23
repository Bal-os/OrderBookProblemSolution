package os.balashov;

import os.balashov.services.OrderBook;
import os.balashov.services.RequestResolver;

import java.io.*;

public class Main {
    private static final String INPUT_FILE = "input.txt";
    private static final String OUTPUT_FILE = "output.txt";

    public static void main(String[] args) {
        OrderBook orderBook = new OrderBook();
        RequestResolver requestResolver = new RequestResolver(orderBook);

        try(BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE)))
        {
            String inputLine;
            while((inputLine = reader.readLine()) != null){
                requestResolver.resolveString(inputLine);
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE)))
        {
            for(String outputLine: orderBook.getQueryList()) {
                writer.write(outputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}