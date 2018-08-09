package br.com.leverton.shortenurl.repository;

import br.com.leverton.shortenurl.domain.Shortener;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface IShortenerRepository extends MongoRepository<Shortener, ObjectId> {

    Optional<Shortener> findByAliasEquals(String alias);
}