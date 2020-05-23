package OrientDB;

import OrientDB.dto.Oceny;
import OrientDB.dto.Student;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.db.object.ODatabaseObject;
import com.orientechnologies.orient.object.db.OrientDBObject;

import java.util.Scanner;
import java.util.UUID;

public class MainOrientDB {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            OrientDBObject orientDB = new OrientDBObject("remote:localhost", OrientDBConfig.defaultConfig());
            ODatabaseObject db = orientDB.open("database01","user", "user");

            db.getEntityManager().registerEntityClasses("OrientDB.dto");
            Oceny oceny = db.newInstance(Oceny.class);
            Student student = db.newInstance(Student.class);
            System.out.println("|=================> System zarządzania dziekanatem <=================|");
            System.out.println("1.Studenci");
            System.out.println("===> 1a.Dodaj studenta");
            System.out.println("===> 1b.Wyświetl wszystkich studentów");
            System.out.println("===> 1c.Usuń studenta");
            System.out.println("===> 1d.Edytuj studenta studenta");
            System.out.println("===> 1e.Zmiana imienia i nazwiska na większe litery");
            System.out.println("===> 1f.Wyświetlanie studenta po id");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1a":
                    addStudent(oceny, student, scanner, db);
                    break;
                case "1b":
                    displayAllStudents(db);
                    break;
                case "1c":
                    deleteStudent(db, scanner);
                    break;
                case "1d":
                    editStudent(db, scanner);
                    break;
                case "1e":
                    setLastNameToUpper(db, scanner);
                    break;
                case "1f":
                    findStudentByID(db, scanner);
                    break;
            }
        } while (true);
    }

    private static void addStudent(Oceny oceny, Student student, Scanner scanner, ODatabaseObject db) {
        String firstName, lastName, specialization, group;
        int studyYear;
        double mathGrade, programmingGrade, physicsGrade;
        System.out.println("Wpisz imię");
        firstName = scanner.nextLine();
        System.out.println("Wpisz nazwisko");
        lastName = scanner.nextLine();
        System.out.println("Wpisz kierunek");
        specialization = scanner.nextLine();
        System.out.println("Wpisz grupę");
        group = scanner.nextLine();
        System.out.println("Wpisz rok studiów");
        studyYear = scanner.nextInt();
        System.out.println("Wpisz ocenę z matematki");
        mathGrade = scanner.nextDouble();
        System.out.println("Wpisz ocenę z programowania");
        programmingGrade = scanner.nextDouble();
        System.out.println("Wpisz ocenę z fizyki");
        physicsGrade= scanner.nextDouble();
        scanner.nextLine();

        student.setId(UUID.randomUUID());
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setGroup(group);
        student.setSpecialization(specialization);
        student.setStudyYear(studyYear);
        student.setOceny(oceny);
        oceny.setMathGrade(mathGrade);
        oceny.setPhysicsGrade(programmingGrade);
        oceny.setProgrammingGrade(physicsGrade);
        db.save(student);
        db.close();
    }

    private static void displayAllStudents(ODatabaseObject db){
        for (Student studentDoc : db.browseClass(Student.class)){
            System.out.println(studentDoc.toString());
        }
        db.close();
    }

    private static void deleteStudent(ODatabaseObject db, Scanner scanner){
        UUID id;
        System.out.println("Podaj id które ma być usunięte");
        id = UUID.fromString(scanner.nextLine());
        for (Student studentDoc : db.browseClass(Student.class)){
            if (studentDoc.getId().equals(id)){
                db.delete(studentDoc.getOceny());
                db.delete(studentDoc);
            }
        }
        db.close();
    }

    private static void editStudent(ODatabaseObject db, Scanner scanner){
        UUID id;
        System.out.println("Podaj id które ma być edytowane");
        id = UUID.fromString(scanner.nextLine());
        for (Student student : db.browseClass(Student.class)){
            if (student.getId().equals(id)){
                System.out.println("Wybierz pole które chcesz edytować");
                System.out.println("1.  Imię");
                System.out.println("2.  Nazwisko");
                System.out.println("3.  Kierunek");
                System.out.println("4.  Grupa");
                System.out.println("5.  Rok studiów");
                System.out.println("6.  Ocenę z matematyki");
                System.out.println("7.  Ocenę z programowania");
                System.out.println("8.  Ocenę z fizyki");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Wpisz nowe imię");
                        String newFirstName = scanner.nextLine();
                        student.setFirstName(newFirstName);
                        db.save(student);
                        break;
                    case 2:
                        System.out.println("Wpisz nowe nazwisko");
                        String newLastName = scanner.nextLine();
                        student.setLastName(newLastName);
                        db.save(student);
                        break;
                    case 3:
                        System.out.println("Wpisz nowy kierunek");
                        String newSpecialization = scanner.nextLine();
                        student.setSpecialization(newSpecialization);
                        db.save(student);
                        break;
                    case 4:
                        System.out.println("Wpisz nową grupa");
                        String newGroup = scanner.nextLine();
                        student.setGroup(newGroup);
                        db.save(student);
                        break;
                    case 5:
                        System.out.println("Wpisz nowy rok studiów");
                        int newStudyYear = scanner.nextInt();
                        student.setStudyYear(newStudyYear);
                        db.save(student);
                        break;
                    case 6:
                        System.out.println("Wpisz nową ocenę z matematyki");
                        double newMathGrade = scanner.nextDouble();
                        scanner.nextLine();
                        student.getOceny().setMathGrade(newMathGrade);
                        db.save(student);
                        break;
                    case 7:
                        System.out.println("Wpisz nową ocenę z programowania");
                        double newProgrammingGrade = scanner.nextDouble();
                        scanner.nextLine();
                        student.getOceny().setProgrammingGrade(newProgrammingGrade);
                        db.save(student);
                        break;
                    case 8:
                        System.out.println("Wpisz nową ocenę z fizyki");
                        double newPhysicsGrade = scanner.nextDouble();
                        scanner.nextLine();
                        student.getOceny().setPhysicsGrade(newPhysicsGrade);
                        db.save(student);
                        break;
                }
            }
        }
        db.close();
    }

    private static void setLastNameToUpper(ODatabaseObject db, Scanner scanner){
        UUID id;
        System.out.println("Podaj id w którym nazwisko ma być zmienione");
        id = UUID.fromString(scanner.nextLine());
        for (Student studentDoc : db.browseClass(Student.class)){
            if (studentDoc.getId().equals(id)){
                String lastName = studentDoc.getLastName();
                if (lastName.equals(lastName.toLowerCase())){
                    lastName = lastName.toUpperCase();
                    studentDoc.setLastName(lastName);
                    db.save(studentDoc);
                } else {
                    lastName = lastName.toLowerCase();
                    studentDoc.setLastName(lastName);
                    db.save(studentDoc);
                }
            }
        }
        db.close();
    }

    private static void findStudentByID(ODatabaseObject db, Scanner scanner){
        UUID id;
        System.out.println("Podaj id które ma być wyświetlone");
        id = UUID.fromString(scanner.nextLine());
        for (Student studentDoc : db.browseClass(Student.class)){
            if (studentDoc.getId().equals(id)){
                System.out.println(studentDoc.toString());
            }
        }
        db.close();
    }
}
