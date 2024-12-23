package pl.edu.agh.school;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import pl.edu.agh.logger.Logger;

public class SchoolClass implements Serializable {

	private static final long serialVersionUID = -1458264557391305041L;

	@Inject
	private transient Logger logger;
	private String name;
	private String profile;

	private final List<Student> students = new ArrayList<>();
	private final List<Subject> subjects = new ArrayList<>();

	public SchoolClass() {
	}

	public String getName() {
		return name;
	}

	public String getProfile() {
		return profile;
	}

	@Override
	public String toString() {
		return "class " + name + ", profile " + profile;
	}

	public void addSubject(Subject subject) {
		if (!subjects.contains(subject)) {
			subjects.add(subject);
			logger.log(
					"Added " + subject + " to " + this);
		}
	}

	public Collection<Subject> getSubjects() {
		return subjects;
	}

	public void addStudent(Student student) {
		if (!students.contains(student)) {
			students.add(student);
			student.setSchoolClass(this);
			logger.log(
					"Added " + student + " to class "
							+ this);
		}
	}

	public Collection<Student> getStudents() {
		return students;
	}

	public Collection<Term> getSchedule() {
		Collection<Term> terms = new ArrayList<>();
		for (Subject subject : subjects) {
			terms.addAll(subject.getSchedule());
		}
		return terms;
	}

	public boolean meetSearchCriteria(String name, String profile) {
		return this.name.equals(name) && this.profile.equals(profile);
	}

	public void setNameAndProfile(String name, String profile){
		this.name = name;
		this.profile = profile;
	}

	public Logger getLogger() {
		return logger;
	}

	@Serial
	private void readObject(ObjectInputStream ois)
			throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		this.logger = Logger.getInstance();
	}
}