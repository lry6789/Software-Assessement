package utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MyMouseListener extends MouseAdapter {
    int startX;
    int startY;
    int endX;
    int endY;

    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
    }
    public void mouseReleased(MouseEvent e) {
        Draw ig=(Draw) e.getSource();
        endX = e.getX();
        endY = e.getY();
//        System.out.println("startX:"+startX);
//        System.out.println("startY:"+startY);
//        System.out.println("endX:"+endX);
//        System.out.println("endY:"+endY);
        double angle =Math.toDegrees(Math.atan2(endY-startY,endX-startX) );
        double angleRadians = Math.toRadians(angle);
//        System.out.println("angle:"+angle);
//        System.out.println("sin:"+Math.sin(angleRadians));
//        System.out.println("cos:"+Math.cos(angleRadians));

        double deltaX = 0.1 * Math.cos(angleRadians);
        double deltaY = 0.1 * Math.sin(angleRadians);
        //System.out.println("phi:"+Math.toDegrees(TransformParameters.phi));
        //System.out.println("psi:"+Math.toDegrees(TransformParameters.psi));

        TransformParameters.phi -= deltaY;



        TransformParameters.psi -= deltaX;
//        System.out.println(TransformParameters.psi);

        TransformParameters.update();

        ig.repaint();
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}
