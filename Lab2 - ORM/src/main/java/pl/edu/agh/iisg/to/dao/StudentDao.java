package pl.edu.agh.iisg.to.dao;

import org.hibernate.Session;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.SessionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentDao extends GenericDao<Student> {

    public StudentDao(SessionService sessionService) {
        super(sessionService, Student.class);
    }

    public Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
        return save(new Student(firstName, lastName, indexNumber));
    }

    public List<Student> findAll() {
        try {
            Session session = currentSession();
            return session.createQuery("SELECT s FROM Student s ORDER BY lastName", Student.class)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Optional<Student> findByIndexNumber(final int indexNumber){
            try {
                Session session = currentSession();
                return session.createQuery("SELECT s FROM Student s WHERE s.indexNumber = :indexNumber", Student.class)
                        .setParameter("indexNumber", indexNumber)
                        .uniqueResultOptional();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return Optional.empty();
        }

}
