package br.com.leverton.shortenurl.service;

import br.com.leverton.shortenurl.domain.Sequence;
import br.com.leverton.shortenurl.domain.Shortener;
import br.com.leverton.shortenurl.exception.BusinessException;
import br.com.leverton.shortenurl.exception.domain.CodeError;
import br.com.leverton.shortenurl.helper.URLTinyMe;
import br.com.leverton.shortenurl.repository.ISequenceRepository;
import br.com.leverton.shortenurl.repository.IShortenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortenerService implements IShortenerService {


    @Autowired
    private ISequenceRepository sequenceRepository;

    @Autowired
    private IShortenerRepository shortenerRepository;


    @Autowired
    private MongoTemplate template;

    @Override
    public Shortener shortIt(String url, String alias) {
        Optional<String> optionalAlias = Optional.ofNullable(alias);
        optionalAlias.ifPresent(this::validAlias);
        Shortener shortener = new Shortener();
        shortener.setAlias(optionalAlias.orElse(getUniqueAlias(url)));
        shortener.setUrl(url);
        shortenerRepository.save(shortener);

        return shortener;
    }

    private String getUniqueAlias(String url) {

        Query query = new Query(Criteria.where("sequenceName").is("shortener"));
        Update update = new Update().inc("value", 1);
        Sequence sequence = template.findAndModify(query, update, new FindAndModifyOptions().upsert(true).returnNew(true), Sequence.class);

        return URLTinyMe.encode(sequence.getValue());
    }

    private void validAlias(String alias) {
        shortenerRepository.findByAliasEquals(alias).ifPresent(shortener -> {
            throw new BusinessException(CodeError.CUSTOM_ALIAS_ALREADY_EXISTS, alias);
        });
    }

    @Override
    public Shortener getUrl(String alias) {

        Optional<Shortener> shortenerOptional = shortenerRepository.findByAliasEquals(alias);
        return shortenerOptional.orElseThrow(() -> new BusinessException(CodeError.SHORTENED_URL_NOT_FOUND, alias));
    }
}
