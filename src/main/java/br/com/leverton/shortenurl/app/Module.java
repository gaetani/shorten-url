package br.com.leverton.shortenurl.app;

import br.com.leverton.shortenurl.controller.IShortenerController;
import br.com.leverton.shortenurl.controller.ShortenerController;
import br.com.leverton.shortenurl.dao.ISequenceDao;
import br.com.leverton.shortenurl.dao.IShortenerDao;
import br.com.leverton.shortenurl.dao.SequenceDao;
import br.com.leverton.shortenurl.dao.ShortenerDao;
import br.com.leverton.shortenurl.infraestructure.morphia.DatastoreConnector;
import br.com.leverton.shortenurl.routes.IRoutesDefinition;
import br.com.leverton.shortenurl.routes.RoutesDefinition;
import br.com.leverton.shortenurl.service.IShortenerService;
import br.com.leverton.shortenurl.service.ShortenerService;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.mongodb.morphia.Datastore;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by gaetani on 11/01/17.
 */
public class Module extends AbstractModule implements TypeListener {

    /**
     * Call postconstruct method (if annotation exists).
     */
    @Override
    public <I> void hear(final TypeLiteral<I> type, final TypeEncounter<I> encounter) {
        encounter.register(new InjectionListener<I>() {

            @Override
            public void afterInjection(final I injectee) {
                Arrays.stream(injectee.getClass().getMethods()).filter(method -> method.isAnnotationPresent(PostConstruct.class)).forEach(method -> {
                    try {
                        method.invoke(injectee);
                    } catch (final Exception e) {
                        throw new RuntimeException(String.format("@PostConstruct %s", method), e);
                    }
                });
            }
        });
    }

    @Override
    protected void configure() {


        bind(ISequenceDao.class).to(SequenceDao.class);
        bind(IShortenerDao.class).to(ShortenerDao.class);
        bind(IShortenerService.class).to(ShortenerService.class);
        bind(IShortenerController.class).to(ShortenerController.class);
        bind(IRoutesDefinition.class).to(RoutesDefinition.class);


        bindListener(Matchers.any(), this);
    }


    @Provides
    @Inject
    public Datastore datastore(DatastoreConnector datastoreConnector) {
        return datastoreConnector.getDatastore();
    }
}
