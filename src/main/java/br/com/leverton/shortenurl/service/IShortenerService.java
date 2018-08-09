package br.com.leverton.shortenurl.service;

import br.com.leverton.shortenurl.domain.Shortener;

import java.util.Optional;

public interface IShortenerService {
    Shortener shortIt(String url, String optionalAlias);

    Shortener getUrl(String alias);
}
