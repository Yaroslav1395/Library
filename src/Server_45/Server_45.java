package Server_45;

import DataModels.Employee;
import DataModels.Employees;
import FileServise.FileService;
import Library.Library;
import Server.*;
import Server_44.Server44;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static Server.Cookie.getCookies;
import static Server.Cookie.setCookie;
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
        registerPost("/profile", this::employeePost);
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

            String encrypted = CodeGenerator.makeCode(parsedInfo.get("email") + new Random().nextInt(10000));

            setCookie(exchange, Cookie.make("userId", encrypted, 300));
            setCookie(exchange, Cookie.make("userEmail", parsedInfo.get("email"), 300));

            library.setEmployeeUserId(parsedInfo.get("email"), encrypted);

            Employee employee = library.getEmployeeByEmail(parsedInfo.get("email"));
            renderTemplate(exchange, "profile.html", employee);

            FileService.writeJson(library);
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
            redirect303(exchange, "/profile");
            FileService.writeJson(library);
        }else {
            redirect303(exchange, "/failedRegistration");
        }
    }
    private void freemarkerEmployeeHandler(HttpExchange exchange){
        Library library = FileService.readJsonFile();
        Map<String, String> parsed = Cookie.parse(getCookies(exchange));
        String emailId = parsed.getOrDefault("userId", null);
        String email =  parsed.getOrDefault("userEmail", null);
        if(library.userIdCheck(email, emailId)){
            renderTemplate(exchange, "profile.html", getEmployeeModel(email));
        }else{
            redirect303(exchange, "/login");
        }
    }
    private void employeePost(HttpExchange exchange){
        setCookie(exchange, Cookie.make("userId", 0, 0));
        setCookie(exchange, Cookie.make("userEmail", 0, 0));

        redirect303(exchange, "/login");
    }
    private Employee getEmployeeModel(String userEmail) {
        Library library = FileService.readJsonFile();
        return library.getEmployeeByEmail(userEmail);
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
