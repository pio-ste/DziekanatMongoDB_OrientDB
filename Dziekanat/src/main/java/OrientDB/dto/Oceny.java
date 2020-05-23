package OrientDB.dto;

public class Oceny {
    
    private double mathGrade, programmingGrade, physicsGrade;

    public Oceny() {

    }

    public double getMathGrade() {
        return mathGrade;
    }

    public void setMathGrade(double mathGrade) {
        this.mathGrade = mathGrade;
    }

    public double getProgrammingGrade() {
        return programmingGrade;
    }

    public void setProgrammingGrade(double programmingGrade) {
        this.programmingGrade = programmingGrade;
    }

    public double getPhysicsGrade() {
        return physicsGrade;
    }

    public void setPhysicsGrade(double physicsGrade) {
        this.physicsGrade = physicsGrade;
    }

    @Override
    public String toString() {
        return "Oceny{" +
                "matematyka=" + getMathGrade() +
                ", programowanie=" + getProgrammingGrade() +
                ", fizyka=" + getPhysicsGrade() +
                '}';
    }
}
