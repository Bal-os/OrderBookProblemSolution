package os.balashov.requests;

import java.io.IOException;

public class OrderRequest {
    private static final String SELL = "sell";
    private static final String BUY = "buy";
    private static final String SEPARATOR = ",";
    private Boolean isSell;
    private Integer size;

    private OrderRequest() {

    }

    public static OrderRequest createOrderRequest(String s) throws IOException {
        String sizeString = s.substring(s.lastIndexOf(SEPARATOR) + 1);
        if (sizeString.isEmpty()) {
            throw new IOException("Wrong format of input");
        }

        Integer size = Integer.decode(sizeString);
        OrderRequest request = new OrderRequest();
        request.setSize(size);
        if (s.contains(SELL)) {
            request.setSell(true);
        } else if (s.contains(BUY)) {
            request.setSell(false);
        } else {
            throw new IOException("Wrong format of input");
        }
        return request;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean isSell() {
        return isSell;
    }

    public void setSell(Boolean sell) {
        isSell = sell;
    }
}
