package pl.edu.agh.school.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import pl.edu.agh.school.persistence.PersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule {
    @Provides
    PersistenceManager getPersistenceManager(SerializablePersistenceManager persistenceManager) {
        return persistenceManager;
    }

    @Provides
    @Named("teachersStorageFileName")
    String provideTeachersStorageFileName() {
        return "guice-teachers.dat";
    }

    @Provides
    @Named("classStorageFileName")
    String provideClassStorageFileName() {
        return "guice-classes.dat";
    }




}

