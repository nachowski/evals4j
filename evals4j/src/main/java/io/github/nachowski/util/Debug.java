package io.github.nachowski.util;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Request;
import okio.Buffer;

public class Debug {
    public static void logRequestDetails(final Request request) {
        try {
            // Log URL and Method
            System.out.println("URL: " + request.url());
            System.out.println("Method: " + request.method());

            // Log Headers
            System.out.println("Headers:");
            Headers headers = request.headers();
            for (String name : headers.names()) {
                System.out.println("  " + name + ": " + headers.get(name));
            }

            // Log Body
            final Request copy = request.newBuilder().build();
            if (copy.body() != null) {
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                String bodyString = buffer.readUtf8();
                System.out.println("Body: " + bodyString);
            } else {
                System.out.println("Body: null");
            }

        } catch (final IOException e) {
            System.out.println("Request logging failed: " + e.getMessage());
        }
    }
}