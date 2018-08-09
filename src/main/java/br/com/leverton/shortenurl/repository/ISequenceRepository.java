package br.com.leverton.shortenurl.repository;


import br.com.leverton.shortenurl.domain.Sequence;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ISequenceRepository extends MongoRepository<Sequence, ObjectId> {
   /* private Datastore datastore;

    @Inject
    public SequenceDao(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Sequence nextSequence(String sequenceName) {

        Query<Sequence> sequenceQuery = datastore.createQuery(Sequence.class).field("sequenceName").equal(sequenceName);
        UpdateOperations<Sequence> ops = datastore
                .createUpdateOperations(Sequence.class)
                .inc("value", 1);


        return datastore.findAndModify(sequenceQuery, ops, false, true);
    }


@Repository
public class ShortenerDao implements IShortenerDao {
    private Datastore datastore;

    @Inject
    public ShortenerDao(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Optional<Shortener> findBy(String alias) {
        return Optional.ofNullable(datastore.createQuery(Shortener.class).field("alias").equal(alias).get());
    }

    @Override
    public boolean checkExistent(String alias) {
        return datastore.createQuery(Shortener.class).field("alias").equal(alias).countAll() > 0;
    }

    @Override
    public void save(Shortener shortener) {
        datastore.save(shortener);
    }
}


    */
}

