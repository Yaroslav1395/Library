package Server_44;

import DataModels.*;
import FileServise.FileService;
import Library.*;
import Server.*;
import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class Server44 extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    public Server44(String host, int port) throws IOException {
        super(host, port);
        registerGet("/sample", this::freemarkerSampleHandler);
        registerGet("/books", this::freemarkerBooksHandler);
        registerGet("/book", this::freemarkerBookHandler);
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            // путь к каталогу в котором у нас хранятся шаблоны
            // это может быть совершенно другой путь, чем тот, откуда сервер берёт файлы
            // которые отправляет пользователю
            cfg.setDirectoryForTemplateLoading(new File("data"));

            // прочие стандартные настройки о них читать тут
            // https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void freemarkerSampleHandler(HttpExchange exchange) {
        renderTemplate(exchange, "sample.html", getSampleDataModel());
    }
    private void freemarkerBooksHandler(HttpExchange exchange){
        Library library = FileService.readJsonFile();

        Map<String, String> parsedCookie = Cookie.parse(Cookie.getCookies(exchange));
        String userEmail = parsedCookie.get("userEmail");
        String userId = parsedCookie.get("userId");

        if(library.userIdCheck(userEmail, userId)){
           library.setUserLogin(true);
        }

        if(library.userHasTwoBook(userEmail)){
            library.setLoginUserHasTwoBook(true);
        }

        String query = getQueryParams(exchange);

        if(query.length() > 0){
            Map<String, String> parsed = Utils.parseUrlEncoded(query, "&");
            library.takeBook(userEmail, Integer.parseInt(parsed.get("index")));
            FileService.writeJson(library);
        }

        renderTemplate(exchange, "books.html", library.getLibraryBooks());
    }
    private void freemarkerBookHandler(HttpExchange exchange){
        Library library = FileService.readJsonFile();
        String queryParams = getQueryParams(exchange);
        Map<String, String> queryMap = Utils.parseUrlEncoded(queryParams, "&");
        Book book = library.getBookByIndex(Integer.parseInt(queryMap.get("index")));
        renderTemplate(exchange, "book.html", book);
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            // загружаем шаблон из файла по имени.
            // шаблон должен находится по пути, указанном в конфигурации
            Template temp = freemarker.getTemplate(templateFile);

            // freemarker записывает преобразованный шаблон в объект класса writer
            // а наш сервер отправляет клиенту массивы байт
            // по этому нам надо сделать "мост" между этими двумя системами

            // создаём поток который сохраняет всё, что в него будет записано в байтовый массив
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // создаём объект, который умеет писать в поток и который подходит для freemarker
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {

                // обрабатываем шаблон заполняя его данными из модели
                // и записываем результат в объект "записи"
                temp.process(dataModel, writer);
                writer.flush();

                // получаем байтовый поток
                var data = stream.toByteArray();

                // отправляем результат клиенту
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private SampleDataModel getSampleDataModel() {
        // возвращаем экземпляр тестовой модели-данных
        // которую freemarker будет использовать для наполнения шаблона
        return new SampleDataModel();
    }
    private Books getBooksModel(){
        Library library = FileService.readJsonFile();
        return library.getLibraryBooks();
    }
    /*private Book getBookModel(){
        Library library = FileService.readJsonFile();
        return library.getLibraryBooks().returnRandomBookFromBooksList(
                library.getEmployees().getEmployeeById(1));
    }*/
}
