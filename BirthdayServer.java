import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.awt.Desktop;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * ═════════════════════════════════════════════════════════════════════
 * BABYGIRL PUNJABAN BIRTHDAY CELEBRATION - JAVA HTTP BACKEND SERVER
 * Zero External Dependencies (Runs on standard Java SE 8+)
 * ═════════════════════════════════════════════════════════════════════
 */
public class BirthdayServer {

    private static final int PORT = 8080;
    private static final String DATA_DIR = "data";
    private static final String MEMORIES_FILE = DATA_DIR + "/memories.json";

    public static void main(String[] args) {
        try {
            // Ensure data directory exists
            File dir = new File(DATA_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            System.out.println("===============================================================");
            System.out.println("   💖 MY BABYGIRL PUNJABAN - BIRTHDAY CELEBRATION SERVER 💖   ");
            System.out.println("===============================================================");
            System.out.println("🌹 Starting romantic web server on port: " + PORT);

            // API Endpoints
            server.createContext("/api/status", new StatusHandler());
            server.createContext("/api/memories", new MemoriesHandler());

            // Static File Handler (serves index.html, css/*, js/*, etc.)
            server.createContext("/", new StaticFileHandler());

            server.setExecutor(null); // default executor
            server.start();

            String url = "http://localhost:" + PORT;
            System.out.println("✨ Server running smoothly at: " + url);
            System.out.println("💌 Press Ctrl+C in terminal to stop the server.");
            System.out.println("===============================================================");

            // Automatically open browser
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(url));
                    System.out.println("🌐 Browser opened automatically to " + url);
                }
            } catch (Exception e) {
                System.out.println("💡 Please open your browser and navigate to: " + url);
            }

        } catch (IOException e) {
            System.err.println("❌ Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles /api/status
     */
    static class StatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendCors(exchange);
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            String response = "{\n" +
                    "  \"status\": \"online\",\n" +
                    "  \"message\": \"Happy Birthday, My Babygirl Punjaban! 💖🌹\",\n" +
                    "  \"port\": " + PORT + ",\n" +
                    "  \"server\": \"Romantic Java Embedded Server\"\n" +
                    "}";

            sendCors(exchange);
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    /**
     * Handles /api/memories (GET and POST)
     */
    static class MemoriesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod().toUpperCase();
            sendCors(exchange);

            if ("OPTIONS".equals(method)) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("GET".equals(method)) {
                File file = new File(MEMORIES_FILE);
                String json;
                if (file.exists()) {
                    json = new String(Files.readAllBytes(Paths.get(MEMORIES_FILE)), StandardCharsets.UTF_8);
                } else {
                    json = "{\"status\":\"empty\",\"message\":\"No saved memories yet.\"}";
                }
                byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } else if ("POST".equals(method)) {
                InputStream is = exchange.getRequestBody();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int read;
                while ((read = is.read(temp)) != -1) {
                    buffer.write(temp, 0, read);
                }
                String body = buffer.toString(StandardCharsets.UTF_8.name());

                // Persist to file
                Files.write(Paths.get(MEMORIES_FILE), body.getBytes(StandardCharsets.UTF_8));

                String ack = "{\"status\":\"saved\",\"message\":\"Memories successfully saved 💕\"}";
                byte[] bytes = ack.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    /**
     * Serves static files: index.html, css/*, js/*, and assets
     */
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path == null || path.equals("/") || path.isEmpty()) {
                path = "/index.html";
            }

            // Clean path to prevent path traversal
            path = path.replace("\\", "/");
            if (path.startsWith("/")) {
                path = path.substring(1);
            }

            File file = new File(path);
            if (!file.exists() || file.isDirectory()) {
                String notFound = "<h1>404 Not Found</h1><p>Could not find file: " + path + "</p>";
                byte[] bytes = notFound.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(404, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
                return;
            }

            String mimeType = getMimeType(file.getName());
            exchange.getResponseHeaders().set("Content-Type", mimeType);
            sendCors(exchange);

            byte[] fileBytes = Files.readAllBytes(file.toPath());
            exchange.sendResponseHeaders(200, fileBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(fileBytes);
            }
        }

        private String getMimeType(String filename) {
            String lower = filename.toLowerCase();
            if (lower.endsWith(".html") || lower.endsWith(".htm")) return "text/html; charset=UTF-8";
            if (lower.endsWith(".css")) return "text/css; charset=UTF-8";
            if (lower.endsWith(".js")) return "application/javascript; charset=UTF-8";
            if (lower.endsWith(".json")) return "application/json; charset=UTF-8";
            if (lower.endsWith(".png")) return "image/png";
            if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";
            if (lower.endsWith(".svg")) return "image/svg+xml";
            if (lower.endsWith(".gif")) return "image/gif";
            if (lower.endsWith(".ico")) return "image/x-icon";
            if (lower.endsWith(".mp3")) return "audio/mpeg";
            return "application/octet-stream";
        }
    }

    private static void sendCors(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }
}
