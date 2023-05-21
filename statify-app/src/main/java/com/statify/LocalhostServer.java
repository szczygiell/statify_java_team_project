package com.statify;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalhostServer {
    private Socket clientSocket = null;
    private String queryString = null;
    private ServerSocket serverSocket;

    public void startServer() {
        int port = 8080; // Specify the desired port number
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (queryString == null) {
                clientSocket = serverSocket.accept();

                readCallbackQuery();

                clientSocket.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readCallbackQuery() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            queryString = reader.readLine();
            System.out.println("Callback query: " + queryString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getQueryParameterValue(String query, String parameterName) {
        query = query.substring(6, query.length() - 9);
        // if (query.startsWith("?")) {
        // query = query.substring(1);
        // }
        String[] queryParameters = query.split("&");
        String parameterValue = null;
        for (String queryParameter : queryParameters) {
            String[] parameter = queryParameter.split("=");
            if (parameter.length == 2 & parameter[0].equals(parameterName)) {
                parameterValue = parameter[1];
                break;
            }
        }
        return parameterValue;
    }

    public String getCodePrameterValue() {
        return LocalhostServer.getQueryParameterValue(queryString, "code");
    }

    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server stopped");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}