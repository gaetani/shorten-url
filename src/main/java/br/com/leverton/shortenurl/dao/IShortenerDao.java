package br.com.leverton.shortenurl.dao;

import br.com.leverton.shortenurl.domain.Shortener;

import java.util.Optional;

public interface IShortenerDao {
    Optional<Shortener> findBy(String alias);

    boolean checkExistent(String alias);

    void save(Shortener shortener);
}
