package tech.smartkit.istorybook.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yangboz on 23/08/2018.
 * @see: https://dzone.com/articles/memcached-memory-caching
 */
@ConfigurationProperties(prefix = "memcached")
@Component
public class MemcachedSettings {
    List<String> servers;

    public MemcachedSettings(List<String> servers) {
        this.servers = servers;
    }

    public MemcachedSettings() {
    }

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    @Override
    public String toString() {
        return "MemcachedSettings{" +
                "servers=" + servers +
                '}';
    }
}
