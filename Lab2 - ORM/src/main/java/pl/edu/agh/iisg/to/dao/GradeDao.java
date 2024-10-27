package pl.edu.agh.iisg.to.dao;

import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.SessionService;


public class GradeDao extends GenericDao<Grade> {

    public GradeDao(SessionService sessionService) {
        super(sessionService, Grade.class);
    }


    public void create(Student student, Course course, float gradeValue) {
        save(new Grade(student, course, gradeValue));
    }
}
