package Physics.Enteties;

public class PhysicsCalculations {
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
    public static double[] calculateForceVector(Particle p, boolean[] groundState, double[]... forceVectors) {
        double[] resVector = new double[2];

        resVector[1] += p.getConstantAcceleration() * p.getMass();
        for (double[] forceVector : forceVectors) {
            resVector[0] += forceVector[0];
            resVector[1] += forceVector[1];
        }

        if (groundState[0]) {
            double[] frictionVector = PhysicsCalculations.calculateFrictionVector(p, p.getFrictionC(), p.getGravity()); //TODO change
            double[] reactionVector = PhysicsCalculations.calculateReactionVector(p, p.getElasisyC(), 1);
            resVector[0] += frictionVector[0] + reactionVector[0];
            resVector[1] += frictionVector[1] + reactionVector[1];
        }
        if (groundState[1]) {
            double[] reactionVector = PhysicsCalculations.calculateReactionVector(p, p.getElasisyC(), 0);

            resVector[0] += reactionVector[0];
            resVector[1] += reactionVector[1];
        }
        return resVector;
    }
}
