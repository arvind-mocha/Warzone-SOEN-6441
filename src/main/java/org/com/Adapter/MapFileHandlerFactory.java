package org.com.Adapter;

public class MapFileHandlerFactory {
    public static MapFileHandler getHandler(String filePath) {
        if (filePath.contains("Conquest")) {
            return new ConquestMapFileAdapter();
        } else if (!filePath.contains("Conquest")) {
            return new DominationMapFileHandler(); // your existing logic wrapped here
        }
        throw new IllegalArgumentException("Unsupported map format: " + filePath);
    }
}
