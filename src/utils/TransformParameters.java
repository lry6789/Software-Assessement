package utils;
import java.lang.*;
public class TransformParameters {
    public static double[] param = new double[8];
    public static double R = 10;
    public static double phi = 0;
    public static double psi = Math.toRadians(270);
    public static void init(){
        param[0] = Math.sin(phi);
        param[1] = Math.cos(phi);
        param[2] = Math.sin(psi);
        param[3] = Math.cos(psi);
        param[4] = param[0] * param[2];
        param[5] = param[0] * param[3];
        param[6] = param[1] * param[2];
        param[7] = param[1] * param[3];
    }
    public static void update(){
        param[0] = Math.sin(phi);
        param[1] = Math.cos(phi);
        param[2] = Math.sin(psi);
        param[3] = Math.cos(psi);
        param[4] = param[0] * param[2];
        param[5] = param[0] * param[3];
        param[6] = param[1] * param[2];
        param[7] = param[1] * param[3];
    }
}
