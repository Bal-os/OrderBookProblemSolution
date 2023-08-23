package os.balashov.services;

import os.balashov.requests.OrderRequest;
import os.balashov.requests.QueryRequest;
import os.balashov.requests.UpdateRequest;

import java.io.IOException;

public class RequestResolver {
    private static final String UPDATE_TAG = "u";
    private static final String ORDER_TAG = "o";
    private static final String QUERY_TAG = "q";
    private final OrderBook orderBook;

    public RequestResolver(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public void resolveString(String requestLine) throws IOException {
        if (requestLine.startsWith(UPDATE_TAG)) {
            UpdateRequest request = UpdateRequest.createUpdateRequest(requestLine);
            orderBook.handleUpdate(request);
        } else if (requestLine.startsWith(ORDER_TAG)) {
            OrderRequest orderRequest = OrderRequest.createOrderRequest(requestLine);
            orderBook.handleOrder(orderRequest);
        } else if (requestLine.startsWith(QUERY_TAG)) {
            QueryRequest queryRequest = QueryRequest.createQueryRequest(requestLine);
            orderBook.handleQuery(queryRequest);
        }
    }
}
