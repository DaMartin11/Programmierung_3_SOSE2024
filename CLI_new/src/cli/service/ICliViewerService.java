package cli.service;

public interface ICliViewerService {
    int viewOptions();
    void viewAll();

    void addMedia();

    void editMedia();

    void removeMedia();
    void persistencemode();
}
