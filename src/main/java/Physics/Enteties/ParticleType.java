package Physics.Enteties;

import java.util.ArrayList;

public class ParticleType implements Particle {
    private volatile double[] velocity = new double[2];
    private volatile double mass;
    private volatile double[] position;
    private volatile double gravity;
    private volatile double area;
    private volatile double cDrag;
    private volatile double cElasticy;
    private volatile double cFriction;
    private volatile double airDensity;
    private volatile ArrayList<double[]> prevPos = new ArrayList<>();

    public ParticleType(double mass, double[] position, double[] velocity, double gravity, double area, double cDrag, double cElasticy, double cFriction, double airDensity) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.gravity = gravity;
        this.area = area;
        this.cElasticy = cElasticy;
        this.cDrag = cDrag;
        this.cFriction = cFriction;
        this.airDensity = airDensity;
    }

    ParticleType(double mass, double[] position) {
        this.mass = mass;
        this.position = position;
    }

    @Override
    public double[] getPosition() {
        return position;
    }

    @Override
    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    @Override
    public double[] getVelocity() {
        return velocity;
    }

    @Override
    public void setPos(double[] pos) {
        prevPos.add(pos);
        position = pos;
    }

    @Override
    public double getMass() {
        return mass;
    }

    private int i = 0;

    @Override
    public boolean[] calculateNewPosition(double[] forceVector, double delta_t) {
        i++;
        //FIXME look paper
        double[] tempVel = new double[2];
        double[] accelerationVector = {(forceVector[0] / getMass()), (forceVector[1] / getMass())};
        tempVel[0] = velocity[0];
        tempVel[1] = velocity[1];
        //System.out.println("[["+position[0]+"]["+position[1]+"]] "+ "KineticEngergy: ["+ (0.5 * getMass() * Math.pow(getVelocity()[1], 2) * 1)+"]");
        System.out.println("[NR" + i + "  " + "[" + position[0] + "][" + position[1] + "]] " + "Velocity: [" + this.getVelocity()[1] + "]  Aceeleration: [" + accelerationVector[1] + "]");

        velocity[0] = (velocity[0] + (accelerationVector[0] * delta_t)); //FIXME
        velocity[1] = (velocity[1] + (accelerationVector[1] * delta_t));
        position = new double[]{(position[0] + (velocity[0] * delta_t * 100)), (position[1] + (velocity[1] * delta_t * 100))};
        boolean[] ret;
        if (position[0] > (1920) && position[1] > 910) {
            /*velocity[0] = tempVel[0];
            velocity[1] = tempVel[1];*/
            position[0] = 1920;
            position[1] = 910;
            ret = new boolean[]{true, true};

        } else if (position[0] > 1920) {
            //velocity[0] = tempVel[0];
            position[0] = 1920;
            ret = new boolean[]{false, true};
        } else if (position[1] > 910) {
            //velocity[1] = tempVel[1];
            position[1] = 910;
            i = 1;
            ret = new boolean[]{true, false};
        } else {
            ret = new boolean[]{false, false};
        }
        if (position[0] <= 0) {
            //velocity[0] = tempVel[0];
            position[0] = 0;
            ret = new boolean[]{false, true};
        }
        this.setPos(position);
        return ret;

    }

    @Override
    public double getConstantAcceleration() {
        return gravity;
    }

    @Override
    public ArrayList<double[]> getPrevPositions() {
        return prevPos;
    }

    @Override
    public double getGravity() {
        return gravity;
    }

    @Override
    public double getArea() {
        return area;
    }

    @Override
    public double getDragC() {
        return cDrag;
    }

    @Override
    public double getFrictionC() {
        return cFriction;
    }

    @Override
    public double getElasisyC() {
        return cElasticy;
    }

    @Override
    public double getAirDensity() {
        return airDensity;
    }

    @Override
    public void setMass(double mass) {
        this.mass = mass;
    }

    @Override
    public void setGravity(double g) {
        this.gravity = g;
    }

    @Override
    public void setArea(double a) {
        this.area = a;
    }

    @Override
    public void setAirDensity(double airDensity) {
        this.airDensity = airDensity;
    }

    @Override
    public void setDrag(double drag) {
        this.cDrag = drag;
    }

    @Override
    public void setCFriction(double cFriction) {
        this.cFriction = cFriction;
    }

    @Override
    public void setElasity(double elasity) {
        this.cElasticy = elasity;
    }
}
