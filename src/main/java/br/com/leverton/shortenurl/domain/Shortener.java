package br.com.leverton.shortenurl.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class Shortener {

    @Id
    private ObjectId id;

    private String url;

    @Indexed(unique = true)
    private String alias;

}
