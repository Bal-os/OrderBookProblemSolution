package os.balashov.services;

import os.balashov.requests.OrderRequest;
import os.balashov.requests.QueryRequest;
import os.balashov.requests.UpdateRequest;

import java.util.*;

public class OrderBook {
    private static final String FORMAT_STRING = "%d,%d\n";
    private final SortedMap<Integer, Integer> asks;
    private final SortedMap<Integer, Integer> bids;
    private final List<String> queryList;

    public OrderBook() {
        Comparator<Integer> comparator = Integer::compare;
        this.asks = new TreeMap<>(comparator.reversed());
        this.bids = new TreeMap<>();

        this.queryList = new ArrayList<>();
    }

    public void handleQuery(QueryRequest queryRequest) {
        if(queryRequest.isBID()) {
            if(bids.isEmpty()) {
                queryList.add(String.format(FORMAT_STRING, 0, 0));
            } else {
                Integer bestBid = bids.lastKey();
                queryList.add(String.format(FORMAT_STRING, bestBid, bids.get(bestBid)));
            }
        } else if(queryRequest.isASK()) {
            if(asks.isEmpty()) {
                queryList.add(String.format(FORMAT_STRING, 0, 0));
            } else {
                Integer bestAsk = asks.lastKey();
                queryList.add(String.format(FORMAT_STRING, bestAsk, asks.get(bestAsk)));
            }
        } else if(queryRequest.isSIZE()) {
            if (asks.containsKey(queryRequest.getPrice())) {
                queryList.add(asks.get(queryRequest.getPrice()).toString() + "\n");
            } else if (bids.containsKey(queryRequest.getPrice())) {
                queryList.add(bids.get(queryRequest.getPrice()).toString() + "\n");
            } else {
                queryList.add("0\n");
            }
        }
    }

    public void handleOrder(OrderRequest request) {
        if(request.isSell()) {
            executeOrder(bids, request.getSize());
        } else {
            executeOrder(asks, request.getSize());
        }
    }

    public void handleUpdate(UpdateRequest request) {
        if(request.getSize() > 0) {
            if (request.isBidOrientedRequest()) {
                addUpdate(bids, asks, request.getPrice(), request.getSize());
            } else {
                addUpdate(asks, bids, request.getPrice(), request.getSize());
            }
        } else {
            if (request.isBidOrientedRequest()) {
                bids.remove(request.getPrice());
            } else {
                asks.remove(request.getPrice());
            }
        }
    }

    public List<String> getQueryList() {
        return queryList;
    }

    private static Integer adjustTheBestPrice(SortedMap<Integer, Integer> offers, Integer askedSize) {
        Integer bestPrice = offers.lastKey();
        Integer sizeOfBestPrice = offers.get(bestPrice);
        if(sizeOfBestPrice > askedSize) {
            offers.put(bestPrice, sizeOfBestPrice - askedSize);
        } else {
            offers.remove(bestPrice);
        }
        return sizeOfBestPrice;
    }

    private static void executeOrder(SortedMap<Integer, Integer> offers, Integer askedSize) {
        Integer sizeOfBestPrice = adjustTheBestPrice(offers, askedSize);
        while (sizeOfBestPrice < askedSize) {
            askedSize -= sizeOfBestPrice;
            sizeOfBestPrice = adjustTheBestPrice(offers, askedSize);
        }
    }

    private static void addUpdate(SortedMap<Integer, Integer> expectedToUpdate, SortedMap<Integer, Integer> needToCheck,
                                  Integer price, Integer size) {
        if(needToCheck.containsKey(price)){
            Integer currentSize = needToCheck.get(price);
            if(currentSize > size) {
                needToCheck.put(price, currentSize - size);
            } else {
                needToCheck.remove(price);
                if(size > currentSize) {
                    expectedToUpdate.put(price, size - currentSize);
                }
            }
        } else {
            expectedToUpdate.put(price, size);
        }
    }
}
