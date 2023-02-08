package Server_45;

import DataModels.Employee;
import DataModels.Employees;
import FileServise.FileService;
import Library.Library;
import Server.ContentType;
import Server.ResponseCodes;
import Server.Utils;
import Server_44.Server44;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class Server_45 extends Server44 {
    public Server_45(String host, int port) throws IOException {
        super(host, port);
        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/registration", this::registrationGet);
        registerPost("/registration", this::registrationPost);
        registerGet("/successfulRegistration", this::successfulRegistrationGet);
        registerGet("/failedRegistration", this::failedRegistrationGet);
        registerGet("/profile", this::freemarkerEmployeeHandler);
    }

    private void loginGet(HttpExchange exchange){
        Path path = makeFilePath("login.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }
    private void loginPost(HttpExchange exchange){
        Library library = FileService.readJsonFile();
        String loginInfo = getBody(exchange);
        Map<String, String> parsedInfo = Utils.parseUrlEncoded(loginInfo, "&");
        if(library.passwordCheck(parsedInfo.get("email"), parsedInfo.get("password"))){
            Employee employee = library.getEmployeeByEmail(parsedInfo.get("email"));
            renderTemplate(exchange, "profile.html", employee);
        }else {
            redirect303(exchange, "/login");
        }
    }
    private void registrationGet(HttpExchange exchange){
        Path path = makeFilePath("registration.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }
    private void successfulRegistrationGet(HttpExchange exchange){
        Path path = makeFilePath("successfulRegistration.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }
    private void failedRegistrationGet(HttpExchange exchange){
        Path path = makeFilePath("failedRegistration.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }
    private void registrationPost(HttpExchange exchange){
        Library library = FileService.readJsonFile();
        String registrationInfo = getBody(exchange);
        Map<String, String> parsedInfo = Utils.parseUrlEncoded(registrationInfo, "&");
        if(!library.emailCheck(parsedInfo.get("email"))){
            library.addNewRegisteredEmployee(new Employee(
                    parsedInfo.get("name"),
                    parsedInfo.get("surname"),
                    parsedInfo.get("email"),
                    parsedInfo.get("password"))
            );
            redirect303(exchange, "/successfulRegistration");
            FileService.writeJson(library);
        }else {
            redirect303(exchange, "/failedRegistration");
        }
    }
    private void freemarkerEmployeeHandler(HttpExchange exchange){
        renderTemplate(exchange, "profile.html", getEmployeeModel());
    }
    private Employee getEmployeeModel() {
        Library library = FileService.readJsonFile();
        return library.getPlugEmployee();
    }
    public static String getContentType(HttpExchange exchange){
        return exchange.getRequestHeaders()
                .getOrDefault("Content-Type", List.of(""))
                .get(0);
    }
    protected String getBody(HttpExchange exchange){
        InputStream input = exchange.getRequestBody();
        InputStreamReader isr = new InputStreamReader(input, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(isr)){
            return reader.lines().collect(joining(""));
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }
}
