package br.com.leverton.shortenurl.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
public class Sequence {

    @Id
    private ObjectId id;

    private String sequenceName;

    private int value;

}
