package Server;

public enum ContentType {
    TEXT_PLAIN("text/plain; charset=utf-8"),
    TEXT_HTML("text/html; charset=utf-8"),
    TEXT_CSS("text/css"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png");

    private final String description;

    ContentType(String descr) {
        this.description =  descr;
    }

    @Override
    public String toString() {
        return description;
    }
}
