package MongoDB;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

public class MainMongoDB {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);
        String user = "user";
        String password = "user";
        String host = "localhost";
        int port = 27017;
        String database = "database01";

        String clientURI = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database;
        MongoClientURI uri = new MongoClientURI(clientURI);

        MongoClient mongoClient = new MongoClient(uri);

        MongoDatabase db = mongoClient.getDatabase(database);

        //db.getCollection("student").drop();
        //db.getCollection("order").drop();
        MongoCollection<Document> studentsCollection = db.getCollection("student");
        MongoCollection<Document> orderCollection = db.getCollection("order");

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("|=================> System zarządzania dziekanatem <=================|");
            System.out.println("1.Studenci");
            System.out.println("===> 1a.Dodaj studenta");
            System.out.println("===> 1b.Wyświetl wszystkich studentów");
            System.out.println("===> 1c.Usuń studenta");
            System.out.println("===> 1d.Szukaj studenta po id");
            System.out.println("===> 1e.Wyszukiwanie studentów po imieniu, nazwisku i kierunku studiów");
            System.out.println("===> 1f.Wyszukiwanie studentów po przedmiocie i ocenie większej niż wybrana");
            System.out.println("===> 1g.Edytuj studenta studenta");
            System.out.println("2.Podania studentów");
            System.out.println("===> 2a.Dodaj podanie");
            System.out.println("===> 2b.Wyświetl wszystkie podania");
            System.out.println("===> 2c.Usuń wybrane podanie");
            System.out.println("===> 2d.Szukaj podania po id");
            System.out.println("===> 2e.Szukaj podania po statusie i danych osoby");
            System.out.println("===> 2f.Edytuj wybrane podanie");
            System.out.println("Wybór:");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1a":
                    addStudent(studentsCollection, scanner);
                    break;
                case "1b":
                    viewAllStudents(studentsCollection);
                    break;
                case "1c":
                    viewAllStudents(studentsCollection);
                    deleteStudent(studentsCollection, scanner);
                    break;
                case "1d":
                    viewAllStudents(studentsCollection);
                    findStudentByID(studentsCollection, scanner);
                    break;
                case "1e":
                    findStudentByFirsNameSecondNameSpec(studentsCollection, scanner);
                    break;
                case "1f":
                    findStudentBySubjectAndGrade(studentsCollection, scanner);
                    break;
                case "1g":
                    viewAllStudents(studentsCollection);
                    editStudent(studentsCollection, scanner);
                    break;
                case "2a":
                    addOrder(orderCollection, scanner);
                    break;
                case "2b":
                    viewAllOrders(orderCollection);
                    break;
                case "2c":
                    deleteOrder(orderCollection, scanner);
                    break;
                case "2d":
                    findOrderById(orderCollection, scanner);
                    break;
                case "2e":
                    findOrderByStatusAndPersonData(orderCollection, scanner);
                    break;
                case "2f":
                    editOrder(orderCollection, scanner);
                    break;
            }
        } while (true);
    }


    private static void addStudent(MongoCollection<Document> studentsCollection, Scanner scanner) {
        String firstName, lastName, specialization, group;
        int studyYear;
        double mathGrade, programmingGrade, physicsGrade;
        Integer indexNumber;
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
        System.out.println("Wpisz numer indeksu");
        indexNumber = scanner.nextInt();
        System.out.println("Wpisz ocenę z matematki");
        mathGrade = scanner.nextDouble();
        System.out.println("Wpisz ocenę z programowania");
        programmingGrade = scanner.nextDouble();
        System.out.println("Wpisz ocenę z fizyki");
        physicsGrade= scanner.nextDouble();
        scanner.nextLine();
        Document studentDocument = new Document("Imie", firstName )
                .append("Nazwisko", lastName)
                .append("Kierunek", specialization)
                .append("Grupa", group)
                .append("Rok studiow", studyYear)
                .append("Numer indeksu", indexNumber)
                .append("Oceny", new Document("matematyka", mathGrade)
                        .append("programowanie", programmingGrade)
                        .append("fizyka", physicsGrade));
        studentsCollection.insertOne(studentDocument);
        System.out.println("Dane zostały dodane");
    }

    private static void viewAllStudents(MongoCollection<Document> studentsCollection){
        for (Document doc : studentsCollection.find())
            System.out.println(doc.toJson());
    }

    private static void deleteStudent(MongoCollection<Document> studentsCollection, Scanner scanner){
        String idStudent;
        System.out.println("Wpisz id studenta, który ma być usunięty");
        idStudent = scanner.nextLine();
        studentsCollection.deleteOne(new Document("_id", new ObjectId(idStudent)));
        for (Document doc : studentsCollection.find())
            System.out.println(doc.toJson());
    }

    private static void findStudentByID(MongoCollection<Document> studentsCollection, Scanner scanner){
        String idStudent;
        System.out.println("Wpisz id studenta");
        idStudent = scanner.nextLine();
        Document studentDoc = studentsCollection.find(Filters.eq("_id", new ObjectId(idStudent))).first();
        System.out.println("Znaleziony student to:");
        System.out.println(studentDoc.toJson());
    }

    private static void findStudentByFirsNameSecondNameSpec(MongoCollection<Document> studentsCollection, Scanner scanner) {
        String firstName, lastName, specialization;
        System.out.println("Wyszukiwanie studentów po imieniu, nazwisku i kierunku studiów");
        System.out.println("Wpisz imię");
        firstName = scanner.nextLine();
        System.out.println("Wpisz nazwisko");
        lastName = scanner.nextLine();
        System.out.println("Wpisz kierunek");
        specialization = scanner.nextLine();
        for (Document doc : studentsCollection.find(Filters.and(
                eq("Imie", firstName),
                eq("Nazwisko", lastName),
                eq("Kierunek", specialization))))
            System.out.println(doc.toJson());
    }

    private static void findStudentBySubjectAndGrade(MongoCollection<Document> studentsCollection, Scanner scanner){
        String subject;
        double grade;
        System.out.println("Wyszukiwanie studentów po przedmiocie i ocenie większej niż wybrana");
        System.out.println("Wpisz przedmiot");
        subject = scanner.nextLine();
        System.out.println("Wpisz ocenę od jakiej ma być znaleziony");
        grade = scanner.nextDouble();
        scanner.nextLine();
        for (Document doc : studentsCollection.find((
                gt("Oceny." + subject, grade))))
            System.out.println(doc.toJson());
    }

    private static void viewStudentByIdEdited(MongoCollection<Document> studentsCollection, String idStudent){
        Document studentDoc = studentsCollection.find(Filters.eq("_id", new ObjectId(idStudent))).first();
        System.out.println("Dane zostały aktualizowane");
        System.out.println(studentDoc.toJson());
    }

    private static void editStudent(MongoCollection<Document> studentsCollection, Scanner scanner) {
        System.out.println("Wpisz id studenta którego dane chcesz edytować");
        String idStudent = scanner.nextLine();
        System.out.println("Wybierz pole które chcesz edytować");
        System.out.println("1.  Imię");
        System.out.println("2.  Nazwisko");
        System.out.println("3.  Kierunek");
        System.out.println("4.  Grupa");
        System.out.println("5.  Rok studiów");
        System.out.println("6.  Numer indeksu");
        System.out.println("7.  Ocenę z matematyki");
        System.out.println("8.  Ocenę z programowania");
        System.out.println("9.  Ocenę z fizyki");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Wpisz nowe imię");
                String newFirstName = scanner.nextLine();
                studentsCollection.updateOne(eq("_id", new ObjectId(idStudent)), new Document("$set", new Document("Imie", newFirstName)));
                viewStudentByIdEdited(studentsCollection, idStudent);
                break;
            case 2:
                System.out.println("Wpisz nowe nazwisko");
                String newLastName = scanner.nextLine();
                studentsCollection.updateOne(eq("_id", new ObjectId(idStudent)), new Document("$set", new Document("Nazwisko", newLastName)));
                viewStudentByIdEdited(studentsCollection, idStudent);
                break;
            case 3:
                System.out.println("Wpisz nowy kierunek");
                String newSpecialization = scanner.nextLine();
                studentsCollection.updateOne(eq("_id", new ObjectId(idStudent)), new Document("$set", new Document("Kierunek", newSpecialization)));
                viewStudentByIdEdited(studentsCollection, idStudent);
                break;
            case 4:
                System.out.println("Wpisz nową grupa");
                String newGroup = scanner.nextLine();
                studentsCollection.updateOne(eq("_id", new ObjectId(idStudent)), new Document("$set", new Document("Grupa", newGroup)));
                viewStudentByIdEdited(studentsCollection, idStudent);
                break;
            case 5:
                System.out.println("Wpisz nowy rok studiów");
                int newStudyYear = scanner.nextInt();
                studentsCollection.updateOne(eq("_id", new ObjectId(idStudent)), new Document("$set", new Document("Rok studiow", newStudyYear)));
                viewStudentByIdEdited(studentsCollection, idStudent);
                break;
            case 6:
                System.out.println("Wpisz nowy numer indeksu");
                String newIndexNumber = scanner.nextLine();
                studentsCollection.updateOne(eq("_id", new ObjectId(idStudent)), new Document("$set", new Document("Numer indeksu", newIndexNumber)));
                viewStudentByIdEdited(studentsCollection, idStudent);
                break;
            case 7:
                System.out.println("Wpisz nową ocenę z matematyki");
                double newMathGrade = scanner.nextDouble();
                scanner.nextLine();
                studentsCollection.updateOne(eq("_id", new ObjectId(idStudent)), new Document("$set", new Document("Oceny.matematyka", newMathGrade)));
                viewStudentByIdEdited(studentsCollection, idStudent);
                break;
            case 8:
                System.out.println("Wpisz nową ocenę z programowania");
                double newProgrammingGrade = scanner.nextDouble();
                scanner.nextLine();
                studentsCollection.updateOne(eq("_id", new ObjectId(idStudent)), new Document("$set", new Document("Oceny.programowanie", newProgrammingGrade)));
                viewStudentByIdEdited(studentsCollection, idStudent);
                break;
            case 9:
                System.out.println("Wpisz nową ocenę z fizyki");
                double newPhysicsGrade = scanner.nextDouble();
                scanner.nextLine();
                studentsCollection.updateOne(eq("_id", new ObjectId(idStudent)), new Document("$set", new Document("Oceny.fizyka", newPhysicsGrade)));
                viewStudentByIdEdited(studentsCollection, idStudent);
                break;
        }
    }

    private static void addOrder(MongoCollection<Document> orderCollection, Scanner scanner){
        String orderName, nameOfOrderingPerson, specialization, decision, status;
        int indexNumber;

        System.out.println("Wpisz treść podania");
        orderName = scanner.nextLine();
        System.out.println("Wpisz decyzję");
        decision = scanner.nextLine();
        System.out.println("Status podania");
        status = scanner.nextLine();
        System.out.println("Imię i nazwisko składającego podanie");
        nameOfOrderingPerson = scanner.nextLine();
        System.out.println("Numer indeksu");
        indexNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Kierunek studenta");
        specialization = scanner.nextLine();

        Document studentDocument = new Document("Tresc podania", orderName )
                .append("Decyzja", decision)
                .append("Status", status)
                .append("Osoba",
                        new Document("Dane osoby", nameOfOrderingPerson)
                        .append("Numer indeksu", indexNumber)
                        .append("Kierunek", specialization));
        orderCollection.insertOne(studentDocument);
        System.out.println("Dane zostały dodane");
    }

    private static void viewAllOrders(MongoCollection<Document> orderCollection){
        for (Document doc : orderCollection.find())
            System.out.println(doc.toJson());
    }

    private static void deleteOrder(MongoCollection<Document> orderCollection, Scanner scanner) {
        String idOrder;
        System.out.println("Wpisz id podania, które ma być usunięte");
        idOrder = scanner.nextLine();
        orderCollection.deleteOne(new Document("_id", new ObjectId(idOrder)));
        for (Document doc : orderCollection.find())
            System.out.println(doc.toJson());
    }

    private static void findOrderById(MongoCollection<Document> orderCollection, Scanner scanner) {
        String idOrder;
        System.out.println("Wpisz id podania");
        idOrder = scanner.nextLine();
        Document studentDoc = orderCollection.find(Filters.eq("_id", new ObjectId(idOrder))).first();
        System.out.println("Znalezione podanie to:");
        System.out.println(studentDoc.toJson());
    }

    private static void findOrderByStatusAndPersonData(MongoCollection<Document> orderCollection, Scanner scanner){
        System.out.println("Wyszukiwanie podania po statusie i danych osoby");
        String status, nameOfOrderingPerson;
        System.out.println("Wpisz status podania");
        status = scanner.nextLine();
        System.out.println("Wpisz dane osoby składającej podanie");
        nameOfOrderingPerson = scanner.nextLine();
        for (Document doc : orderCollection.find(Filters.and(
                eq("Status", status),
                eq("Osoba.Dane osoby", nameOfOrderingPerson))))
            System.out.println(doc.toJson());
    }

    private static void viewOrderByIdEdited(MongoCollection<Document> orderCollection, String idStudent){
        Document orderDoc = orderCollection.find(Filters.eq("_id", new ObjectId(idStudent))).first();
        System.out.println("Dane podania aktualizowane");
        System.out.println(orderDoc.toJson());
    }

    private static void editOrder(MongoCollection<Document> orderCollection, Scanner scanner){
        System.out.println("Wpisz id podania którego dane chcesz edytować");
        String idOrder = scanner.nextLine();
        System.out.println("1. Treść podania");
        System.out.println("2. Decyzja");
        System.out.println("3. Status podania");
        System.out.println("4. Imię i nazwisko składającego podanie");
        System.out.println("5. Numer indeksu");
        System.out.println("6. Kierunek studenta");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Wpisz nową treść podania");
                String orderName = scanner.nextLine();
                orderCollection.updateOne(eq("_id", new ObjectId(idOrder)), new Document("$set", new Document("Tresc podania", orderName)));
                viewOrderByIdEdited(orderCollection, idOrder);
                break;
            case 2:
                System.out.println("Wpisz nową decyzję");
                String decision = scanner.nextLine();
                orderCollection.updateOne(eq("_id", new ObjectId(idOrder)), new Document("$set", new Document("Decyzja", decision)));
                viewOrderByIdEdited(orderCollection, idOrder);
                break;
            case 3:
                System.out.println("Wpisz nowy status");
                String status = scanner.nextLine();
                orderCollection.updateOne(eq("_id", new ObjectId(idOrder)), new Document("$set", new Document("Status", status)));
                viewOrderByIdEdited(orderCollection, idOrder);
                break;
            case 4:
                System.out.println("Wpisz nowe imię i nazwisko osoby składającej podanie");
                String nameOfOrderingPerson = scanner.nextLine();
                orderCollection.updateOne(eq("_id", new ObjectId(idOrder)), new Document("$set", new Document("Osoba.Dane osoby", nameOfOrderingPerson)));
                viewOrderByIdEdited(orderCollection, idOrder);
                break;
            case 5:
                System.out.println("Wpisz nowy numer indeksu");
                int indexNumber = scanner.nextInt();
                orderCollection.updateOne(eq("_id", new ObjectId(idOrder)), new Document("$set", new Document("Osoba.Numer indeksu", indexNumber)));
                viewOrderByIdEdited(orderCollection, idOrder);
                break;
            case 6:
                System.out.println("Wpisz nowy kierunek");
                String specialization = scanner.nextLine();
                orderCollection.updateOne(eq("_id", new ObjectId(idOrder)), new Document("$set", new Document("Osoba.Kierunek", specialization)));
                viewOrderByIdEdited(orderCollection, idOrder);
                break;
        }
    }
}
