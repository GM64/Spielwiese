package at.gernot.meins;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataService.class);

    public CacheData getData(Long id) {
        LOG.info("Creating data with ID = {}", id);

        // simulating time-consuming calculation
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
        }

        CacheData data = new CacheData();
        data.setId(id);
        data.setValue(UUID.randomUUID().toString());
        data.setCreated(new Date());
        return data;
    }
}
