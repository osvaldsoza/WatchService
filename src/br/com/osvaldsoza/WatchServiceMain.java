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
                Path eventDir = map.get(watchKey);
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path eventPath = (Path)event.context();
                    System.out.println(eventDir + ": " + kind + ": " + eventPath);
                }
            }while (watchKey.reset());

        } catch (Exception e) {

        }
    }
}
