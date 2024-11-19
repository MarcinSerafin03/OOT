package pl.edu.agh.school.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.SchoolClass;
import pl.edu.agh.school.Teacher;

import javax.inject.Named;

public final class SerializablePersistenceManager implements PersistenceManager {

    private final Logger log;

    private String teachersStorageFileName;

    private String classStorageFileName;

//    @Inject
//    public SerializablePersistenceManager(String teachersStorageFileName, String classStorageFileName) {
//        this.teachersStorageFileName = teachersStorageFileName;
//        this.classStorageFileName = classStorageFileName;
//    }

    @Inject
    public SerializablePersistenceManager(Logger log) {
        this.log = log;
//        this.teachersStorageFileName = "teachers.dat";
//        this.classStorageFileName = "classes.dat";
    }



    public void saveTeachers(List<Teacher> teachers) {
        if (teachers == null) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(teachersStorageFileName))) {
            oos.writeObject(teachers);
            log.log("Teachers data saved");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            log.log("There was an error while saving the teachers data", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Teacher> loadTeachers() {
        ArrayList<Teacher> res = null;
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(teachersStorageFileName))) {

            res = (ArrayList<Teacher>) ios.readObject();
            log.log("Teachers data loaded");
        } catch (FileNotFoundException e) {
            log.log("Teachers data not found");
            res = new ArrayList<>();
        } catch (IOException e) {
            log.log("There was an error while loading the teachers data", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        return res;
    }

    public void saveClasses(List<SchoolClass> classes) {
        if (classes == null) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(classStorageFileName))) {

            oos.writeObject(classes);
            log.log("Classes data saved");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            log.log("There was an error while saving the classes data", e);
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<SchoolClass> loadClasses() {
        ArrayList<SchoolClass> res = null;
        System.out.println(classStorageFileName);
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(classStorageFileName))) {
            res = (ArrayList<SchoolClass>) ios.readObject();
            log.log("Classes data loaded");
        } catch (FileNotFoundException e) {
            res = new ArrayList<>();
            log.log("Classes data not found");
        } catch (IOException e) {
            log.log("There was an error while loading the classes data", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        return res;
    }


    @Inject
    public void setTeachersStorageFileName(@Named("teachersStorageFileName") String teachersStorageFileName) {
        this.teachersStorageFileName = teachersStorageFileName;
    }

    @Inject
    public void setClassStorageFileName(@Named("classStorageFileName") String classStorageFileName) {
        this.classStorageFileName = classStorageFileName;
    }
}
