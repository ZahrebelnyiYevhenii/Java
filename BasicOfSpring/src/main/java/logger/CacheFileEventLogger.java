package logger;

import entity.Event;

import java.util.List;

public class CacheFileEventLogger extends FileEventLogger {
    private int cacheSize;
    private List<Event> cache;

    @Override
    public void logEvent(Event event) {
        cache.add(event);

        if(cache.size() == cacheSize){
            writeEventsFromCache();
            cache.clear();
        }
    }

    private void writeEventsFromCache(){
        cache.forEach(super::logEvent);
    }

    public void destroy(){
        if(!cache.isEmpty()){
            writeEventsFromCache();
        }
    }

    public CacheFileEventLogger() {
    }

    public CacheFileEventLogger(int cacheSize, List<Event> cache) {
        this.cacheSize = cacheSize;
        this.cache = cache;
    }

    public CacheFileEventLogger(String filename, int cacheSize, List<Event> cache) {
        super(filename);
        this.cacheSize = cacheSize;
        this.cache = cache;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public List<Event> getCache() {
        return cache;
    }

    public void setCache(List<Event> cache) {
        this.cache = cache;
    }
}
