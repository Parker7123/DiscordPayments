package org.example.paymentbot.api;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ApiProcessor {
    private static final String URL = "https://shop.pumpwinner.com/";
    private static final String API_ENDPOINT = "/api";
    private static final String INVOICE_ENDPOINT = "/invoices";

    private static final String STORES_ENDPOINT = "/stores";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient httpClient = new OkHttpClient.Builder().build();


    private <T> T sendRequest(String endpoint, Object body, Class<T> c) {
        Request request = new Request.Builder()
                .url(URL + API_ENDPOINT + endpoint)
                .method(body == null ? "GET" : "POST",
                        body == null ? null : RequestBody.create(JsonStream.serialize(body), JSON))
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return JsonIterator.deserialize(Objects.requireNonNull(response.body()).string(), c);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Invoice createInvoice(InvoiceRequest invoiceRequest) {
        return sendRequest(INVOICE_ENDPOINT, invoiceRequest, Invoice.class);
    }

    public List<String> getStores(String domain){
        return sendRequest(STORES_ENDPOINT, null, List.class);
    }
}
