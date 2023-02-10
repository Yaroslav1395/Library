package Server;

import DataModels.Employee;
import DataModels.Employees;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class BasicServer {

    private final HttpServer server;
    // путь к каталогу с файлами, которые будет отдавать сервер по запросам клиентов
    private final String dataDir = "data";
    //мапа содержит ключ(метод + путь и лямбда выражение которое реализовывает метод интерфейса RouteHandler)
    private Map<String, RouteHandler> routes = new HashMap<>();

    //конструктор принимающий host и port для передачи в метод createServer
    protected BasicServer(String host, int port) throws IOException {
        server = createServer(host, port);
        registerAddressRequestWithHandlers();
    }

    //создаем сервер принимая хост и порт
    private static HttpServer createServer(String host, int port) throws IOException {
        var msg = "Starting server on http://%s:%s/%n";
        System.out.printf(msg, host, port);
        //создаем сокет с хостом(ip/dns) и номером свободного порта
        var address = new InetSocketAddress(host, port);
        //создаем httpserver устанавливая соответствие с адресом
        return HttpServer.create(address, 50);
    }

    //метод устанавливает связь между адресом запроса и обработчиком
    private void registerAddressRequestWithHandlers() {
        // обработчик, который будет определять какие обработчики вызывать в дальнейшем
        //handleIncomingServerRequests исполнит лямбду которая в мапе
        server.createContext("/", this::handleIndexIncomingServerRequests);


        //передаем адрес и лямбду в метод, который запишет это в мапу
        registerGet("/", exchange -> sendFile(exchange, makeFilePath("index.html"), ContentType.TEXT_HTML));

        // эти обрабатывают запросы с указанными расширениями
        registerFileHandler(".css", ContentType.TEXT_CSS);
        registerFileHandler(".html", ContentType.TEXT_HTML);
        registerFileHandler(".jpg", ContentType.IMAGE_JPEG);
        registerFileHandler(".png", ContentType.IMAGE_PNG);

    }
    protected final void registerGenericHandler(String method, String route, RouteHandler handler) {
        getRoutes().put(makeKey(method, route), handler);
    }


    //метод помещает в мапу ключ в виде наименования запроса с метод GET + адрес
    //и реализацию интерфейса RouteHandler в виде лямбды
    protected final void registerGet(String route, RouteHandler handler) {
        registerGenericHandler("GET", route, handler);
    }
    protected final void registerPost(String route, RouteHandler handler) {
        registerGenericHandler("POST", route, handler);
    }

    protected final void registerFileHandler(String fileExt, ContentType type) {
        registerGet(fileExt, exchange -> sendFile(exchange, makeFilePath(exchange), type));
    }
    //метод возвращает мапу которая, содержит ключ в виде наименования запроса + адрес
    //и реализацию интерфейса RouteHandler в виде лямбды
    protected final Map<String, RouteHandler> getRoutes() {
        return routes;
    }
    //метод создает путь к фалу
    protected Path makeFilePath(String... s) {
        return Path.of(dataDir, s);
    }
    private Path makeFilePath(HttpExchange exchange) {
        return makeFilePath(exchange.getRequestURI().getPath());
    }
    //метод принимает HttpExchange, путь к файлу, и тип передаваемого файла
    protected final void sendFile(HttpExchange exchange, Path pathToFile, ContentType contentType) {
        try {
            //если такого пути нет ответом на запрос будет 404
            if (Files.notExists(pathToFile)) {
                respond404(exchange);
                return;
            }
            //преобразует файл который, найдет по адресу в байты
            var data = Files.readAllBytes(pathToFile);
            sendByteData(exchange, ResponseCodes.OK, contentType, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //метод отправляет данные клиенту принимая файл преобразованный в байты
    //и указывает тип передаваемых данных принимая contentType(хранится в перечислении)
    protected final void sendByteData(HttpExchange exchange, ResponseCodes responseCode,
                                      ContentType contentType, byte[] data) throws IOException {
        //получаем исходящий поток для отправки
        try (var output = exchange.getResponseBody()) {
            setContentType(exchange, contentType);
            exchange.sendResponseHeaders(responseCode.getCode(), 0);
            output.write(data);
            output.flush();
        }
    }
    //метод устанавливает тип передваиваемых данных принимая ContentType из перечисления
    private static void setContentType(HttpExchange exchange, ContentType type) {
        exchange.getResponseHeaders().set("Content-Type", String.valueOf(type));
    }

    private static String makeKey(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        // уберём конечную косую черту если она есть
        if (path.endsWith("/") && path.length() > 1) {
            path = path.substring(0, path.length() - 1);
        }
        int index = path.lastIndexOf(".");
        String extOrPath = index != -1 ? path.substring(index).toLowerCase() : path;
        return makeKey(method, extOrPath);
    }
    protected static String makeKey(String method, String route) {
        route = ensureStartsWithSlash(route);
        return String.format("%s %s", method.toUpperCase(), route);
    }
    private static String ensureStartsWithSlash(String route){
        if (route.startsWith("."))
            return route;
        return route.startsWith("/") ? route : "/" + route;
    }


    private void handleIndexIncomingServerRequests(HttpExchange exchange) {
        var route = getRoutes().getOrDefault(makeKey(exchange), this::respond404);
        route.handle(exchange);
    }

    private void respond404(HttpExchange exchange) {
        try {
            var data = "404 Not found".getBytes();
            sendByteData(exchange, ResponseCodes.NOT_FOUND, ContentType.TEXT_PLAIN, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void redirect303(HttpExchange exchange, String path){
        try {
            exchange.getResponseHeaders().add("Location", path);
            exchange.sendResponseHeaders(303, 0);
            exchange.getResponseBody().close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    protected String getQueryParams(HttpExchange exchange) {
        String query = exchange.getRequestURI().getQuery();
        return Objects.nonNull(query) ? query : "";
    }

    public final void start() {
        server.start();
    }
}
