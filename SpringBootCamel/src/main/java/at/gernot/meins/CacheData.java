package at.gernot.meins;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CacheData implements Serializable {

    private static final long serialVersionUID = 996437822624011472L;

    private Long id;

    private String value;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
