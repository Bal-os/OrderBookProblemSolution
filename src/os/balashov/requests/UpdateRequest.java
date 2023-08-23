package os.balashov.requests;

import java.io.IOException;

public class UpdateRequest {
    private static final String BID = "bid";
    private static final String ASK = "ask";
    private static final String SEPARATOR = ",";
    private Boolean isBidOrientedRequest;
    private Integer price;
    private Integer size;

    private UpdateRequest() {

    }

    public static UpdateRequest createUpdateRequest(String s) throws IOException {
        String[] numbers = s.substring(s.indexOf(SEPARATOR) + 1, s.lastIndexOf(SEPARATOR)).split(SEPARATOR);
        if (numbers[0].isEmpty() || numbers[1].isEmpty()) {
            throw new IOException("Wrong format of input");
        }
        Integer price = Integer.decode(numbers[0]);
        Integer size = Integer.decode(numbers[1]);

        UpdateRequest request = new UpdateRequest();
        request.setPrice(price);
        request.setSize(size);
        if (s.endsWith(BID)) {
            request.setBidOrientedRequest(true);
        } else if (s.endsWith(ASK)) {
            request.setBidOrientedRequest(false);
        } else {
            throw new IOException("Wrong format of input");
        }
        return request;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean isBidOrientedRequest() {
        return isBidOrientedRequest;
    }

    public void setBidOrientedRequest(Boolean bidOrientedRequest) {
        isBidOrientedRequest = bidOrientedRequest;
    }
}
