package hello;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by MEV on 2017/3/6.
 */
public class Greeting {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The name of the user", required = true)
    public String getContent() {
        return content;
    }
}
