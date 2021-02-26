package Physics.Enteties;

import java.util.ArrayList;

public interface Particle {

    double[] getPosition();

    void setVelocity(double[] velocity);

    double[] getVelocity();

    void setPos(double[] pos);

    double getMass();

    boolean[] calculateNewPosition(double[] forceVector, double delta_t);

    double getConstantAcceleration();

    ArrayList<double[]> getPrevPositions();

    double getGravity();

    double getArea();

    double getDragC();

    double getFrictionC();

    double getElasisyC();

    double getAirDensity();

    void setMass(double mass);

    void setGravity(double g);

    void setArea(double a);

    void setAirDensity(double airDensity);

    void setDrag(double drag);

    void setCFriction(double cFriction);

    void setElasity(double elasity);

}
