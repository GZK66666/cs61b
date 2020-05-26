public class NBody {
    public static double readRadius(String FileName){
        In in = new In(FileName);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String FileName){
        In in = new In(FileName);
        int N = in.readInt();
        Planet[] planets = new Planet[N];

        in.readDouble();
        for(int i = 0; i < N; i++){
            planets[i] = new Planet(in.readDouble() , in.readDouble() , in.readDouble() , in.readDouble()
            , in.readDouble() , in.readString());
        }

        return planets;
    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        /** drawing the background */
        StdDraw.setScale(-radius , radius);
        StdDraw.picture(0 , 0 , "images/starfield.jpg");

        for(int i = 0; i < planets.length; i++){
            planets[i].draw();
        }

        /** animation */
        StdDraw.enableDoubleBuffering();
        double t = 0;
        while(t < T){
            StdDraw.clear();

            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            for(int i = 0; i < planets.length; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            StdDraw.picture(0 , 0 , "images/starfield.jpg");
            for(int i = 0; i < planets.length; i++){
                planets[i].update(dt , xForces[i] , yForces[i]);
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(1);

            t += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
