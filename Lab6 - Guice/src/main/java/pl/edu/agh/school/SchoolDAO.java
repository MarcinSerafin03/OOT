package pl.edu.agh.school;

import java.util.Collections;
import java.util.List;

import com.google.inject.Inject;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.persistence.PersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolDAO {

    private final Logger log;

    private final List<Teacher> teachers;

    private final List<SchoolClass> classes;

    private final PersistenceManager manager;

    @Inject
    public SchoolDAO(Logger logger, PersistenceManager persistenceManager) {
        log = logger;
        manager = persistenceManager;
        teachers = manager.loadTeachers();
        classes = manager.loadClasses();
    }

    public PersistenceManager getManager() {
        return manager;
    }

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
            manager.saveTeachers(teachers);
            log.log("Added " + teacher.toString());
        }
    }

    public void addClass(SchoolClass newClass) {
        if (!classes.contains(newClass)) {
            classes.add(newClass);
            manager.saveClasses(classes);
            log.log("Added " + newClass.toString());
        }
    }

    public List<SchoolClass> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    public List<Teacher> getTeachers() {
        return Collections.unmodifiableList(teachers);
    }
}
