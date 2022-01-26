package br.com.osvaldsoza;

import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class WatchServiceMain {

    public static void main(String[] args) {
        try (WatchService service = FileSystems.getDefault().newWatchService()) {
            Map<WatchKey, Path> map = new HashMap<>();
            Path path = Paths.get("files");
            map.put(path.register(service,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY), path);

            WatchKey watchKey;

            do {
                watchKey = service.take();
                Path pathDir = map.get(watchKey);
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> watchEvenKind = event.kind();
                    Path fileName = (Path)event.context();
                    System.out.println(pathDir + ": " + watchEvenKind + ": " + fileName);
                }
            }while (watchKey.reset());

        } catch (Exception e) {

        }
    }
}
