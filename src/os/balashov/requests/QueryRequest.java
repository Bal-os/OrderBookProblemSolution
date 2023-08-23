package os.balashov.requests;

import java.io.IOException;

public class QueryRequest {
    private static final String ASK = "best_ask";
    private static final String BID = "best_bid";
    private static final String SIZE = "size";

    private static final String SEPARATOR = ",";

    public enum QueryRequestType {
        ASK,
        BID,
        SIZE
    }

    private Integer price;

    private QueryRequestType requestType;

    private QueryRequest() {
    }

    public static QueryRequest createQueryRequest(String s) throws IOException {
        QueryRequest request = new QueryRequest();
        if (s.contains(BID)) {
            request.setRequestType(QueryRequestType.BID);
        } else if (s.contains(ASK)) {
            request.setRequestType(QueryRequestType.ASK);
        } else if (s.contains(SIZE)) {
            request.setRequestType(QueryRequestType.SIZE);

            String priceString = s.substring(s.lastIndexOf(SEPARATOR) + 1);
            if (priceString.isEmpty()) {
                throw new IOException("Wrong format of input");
            }
            request.setPrice(Integer.decode(priceString));
        }
        return request;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setRequestType(QueryRequestType requestType) {
        this.requestType = requestType;
    }

    public QueryRequestType getRequestType() {
        return requestType;
    }

    public Boolean isASK() {
        return this.getRequestType().equals(QueryRequestType.ASK);
    }

    public Boolean isBID() {
        return this.getRequestType().equals(QueryRequestType.BID);
    }

    public Boolean isSIZE() {
        return this.getRequestType().equals(QueryRequestType.SIZE);
    }
}
