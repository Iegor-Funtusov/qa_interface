package ua.com.alevel.config;

public enum FileType {

    JSON_TYPE("users.json"),
    CSV_TYPE("users.csv");

    private final String path;

    FileType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
