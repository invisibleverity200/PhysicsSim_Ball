package Physics.Enteties;

import java.util.ArrayList;

public class PhysicEngine {
    public static double[] calculateDragVector(double A, double[] velVector, double airDensity, double coefficientOfDrag) {
        double velValue = getVectorValue(velVector);
        double dragValueX = 0.5 * airDensity * Math.pow(velVector[0], 2) * coefficientOfDrag * A;
        double dragValueY = 0.5 * airDensity * Math.pow(velVector[1], 2) * coefficientOfDrag * A;
        double[] dragVector = new double[]{((0 - velVector[0]) / velValue) * dragValueX, ((0 - velVector[1]) / velValue) * dragValueY};

        return dragVector;
    }

    public static double[] calculateFrictionVector(Particle p, double coefficientOfFriction, double gravity) { //FIXME
        double weightForce = p.getMass() * gravity;
        double frictionValue = coefficientOfFriction * weightForce;
        return new double[]{p.getVelocity()[0] / Math.sqrt(Math.pow(p.getVelocity()[0], 2)) * -frictionValue, 0};
    }

    public static double[] calculateReactionVector(Particle p, double coefficientOfElastic, int typeOfWall) {

        //FIXME in velocity is lower than out velocity
        double kineticEnergy = 0.5 * p.getMass() * Math.pow(p.getVelocity()[typeOfWall], 2) * coefficientOfElastic;
        double[] velVector = new double[2];
        velVector[typeOfWall] = p.getVelocity()[typeOfWall] / Math.sqrt(Math.pow(p.getVelocity()[typeOfWall], 2)) * -(Math.sqrt(kineticEnergy * 2 / p.getMass()));
        if (typeOfWall == 1) velVector[0] = p.getVelocity()[0];
        else velVector[1] = p.getVelocity()[1];
        p.setVelocity(velVector);
        return new double[]{0, 0};

    }

    public static double getVectorValue(double[] vector) {
        return Math.pow((Math.pow(vector[0], 2) + Math.pow(vector[1], 2)), 0.5);
    }

    public static double[] getResVectorCollision(ArrayList<Particle> particles, int index) {
        int idx = 0;
        double[] resVector = new double[]{0,0};
        Particle mainP = particles.get(index);
        for (Particle p : particles) {
            if (index != idx & calculateDistance(mainP, p) <= mainP.getHitBoxRadius()) {
                double[] directionVector = normalizeVector(new double[]{p.getPosition()[0] - mainP.getPosition()[0] +0.001, p.getPosition()[1] - mainP.getPosition()[1]+0.001});
                double[] p1VelVector = p.getVelocity();
                double[] mainPVelocity = mainP.getVelocity();

                double normalizedVectorScalarP1 = calcScalar(p1VelVector, directionVector);
                double normalizedVectorScalarMainP = calcScalar(mainPVelocity, directionVector);

                double p1Impuls = p.getMass() * normalizedVectorScalarP1;
                double mainPImpuls = mainP.getMass() * normalizedVectorScalarMainP * mainP.getElasisyC();

                double[] vector = new double[]{directionVector[0] * mainPImpuls + directionVector[0] * p1Impuls, directionVector[0] * mainPImpuls + directionVector[0] * p1Impuls};

                resVector[0] =+ vector[0];
                resVector[1] =+ vector[1];

            }
            idx++;
        }
        return  resVector;
    }

    public static double[] calculateForceVector(Particle p, boolean[] groundState, double[]... forceVectors) {
        double[] resVector = new double[2];
        resVector[1] += p.getConstantAcceleration() * p.getMass();
        for (double[] forceVector : forceVectors) {
            resVector[0] += forceVector[0];
            resVector[1] += forceVector[1];
        }

        if (groundState[0]) {
            double[] frictionVector = PhysicEngine.calculateFrictionVector(p, p.getFrictionC(), p.getGravity()); //TODO change
            double[] reactionVector = PhysicEngine.calculateReactionVector(p, p.getElasisyC(), 1);
            resVector[0] += frictionVector[0] + reactionVector[0];
            resVector[1] += frictionVector[1] + reactionVector[1];
        }
        if (groundState[1]) {
            double[] reactionVector = PhysicEngine.calculateReactionVector(p, p.getElasisyC(), 0);

            resVector[0] += reactionVector[0];
            resVector[1] += reactionVector[1];
        }
        return resVector;
    }

    public static double calculateDistance(Particle p1, Particle p2) {
        double x = p1.getPosition()[0] - p2.getPosition()[0];
        double y = p1.getPosition()[1] - p2.getPosition()[1];

        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static double calcScalar(double[] v1, double[] v2) {
        double skalarprodukt = 0;

        for (int i = 0; i < v1.length; i++) {
            skalarprodukt = skalarprodukt + v1[i] * v2[i];
        }

        return skalarprodukt;

    }

    public static double[] normalizeVector(double[] vec) {
        double length = Math.sqrt(Math.pow(vec[0], 2) + Math.pow(vec[1], 2));
        for (int idx = 0; idx < vec.length; idx++) {
            vec[idx] = vec[idx] / length;
        }
        return vec;
    }

}
