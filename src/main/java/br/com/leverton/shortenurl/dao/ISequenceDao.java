package br.com.leverton.shortenurl.dao;

import br.com.leverton.shortenurl.domain.Sequence;

/**
 * Created by gaetani on 15/01/17.
 */
public interface ISequenceDao {
    Sequence nextSequence(String sequenceName);
}
