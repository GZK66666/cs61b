import java.awt.event.FocusEvent;

public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static double G = 6.67 * 10e-12;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p){
        double r2 = Math.pow((this.xxPos - p.xxPos) , 2) + Math.pow((this.yyPos - p.yyPos) , 2);
        return Math.sqrt(r2);
    }

    public double calcForceExertedBy(Planet p){
        return (G * this.mass * p.mass) / Math.pow(calcDistance(p) , 2);
    }

    public double calcForceExertedByX(Planet p){
        double F = calcForceExertedBy(p);
        double r = calcDistance(p);
        double dx = p.xxPos - this.xxPos;
        return (F * dx) / r;
    }

    public double calcForceExertedByY(Planet p){
        double F = calcForceExertedBy(p);
        double r = calcDistance(p);
        double dy = p.yyPos - this.yyPos;
        return (F * dy) / r;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets){
        double NetForceX = 0;

        for(int i = 0; i < allPlanets.length; i++){
            if(!this.equals(allPlanets[i])){
                NetForceX += this.calcForceExertedByX(allPlanets[i]);
            }
        }

        return NetForceX;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets){
        double NetForceY = 0;

        for(int i = 0; i < allPlanets.length; i++){
            if(!this.equals(allPlanets[i])){
                NetForceY += this.calcForceExertedByY(allPlanets[i]);
            }
        }

        return NetForceY;
    }

    public void update(double dt , double fx , double fy){
        double ax = fx / mass;
        double ay = fy / mass;
        xxVel += ax*dt;
        yyVel += ay*dt;
        xxPos += dt*xxVel;
        yyPos += dt*yyVel;
    }

    public void draw(){
        StdDraw.picture(xxPos , yyPos , "images/"+imgFileName);
    }
}