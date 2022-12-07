package utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JPanel;

/**
 numOfVertices: number of vertices
 numOfFaces: number of faces
 faceList: save the objects of face
 verticesList: save the objects of vertices
 viewPointList: save the objects of vertices transformed into viewpoint coordinate system
 projectedVerticeList: save the objects of vertices transformed into screen coordinate system
 colorList: save the objects of colors used for filling the graph
 */
public class Draw extends JPanel {

    private int numOfVertices;
    private int numOfFaces;
    private final List<Face> faceList;
    private final List<Vertice> verticesList;
    private final List<Vertice> viewPointList;
    private final List<ProjectedVertice> projectedVerticeList;
    private final List<Color> colorList;

    public Draw(){
         faceList = new ArrayList<>();
         verticesList = new ArrayList<>();
         viewPointList = new ArrayList<>();
         projectedVerticeList = new ArrayList<>();
         colorList = new ArrayList<>();
         colorList.add(new Color(0, 0, 95));
         colorList.add(new Color(0, 0, 135));
         colorList.add(new Color(0, 0, 175));
         colorList.add(new Color(0, 0, 215));
         colorList.add(new Color(0, 0, 255));
         colorList.add(new Color(255,255,255));
    }

    public void getInfo(){
        //process input
        String rootPath = System.getProperty("user.dir")+"\\Object.txt";
        System.out.println(rootPath);
        File file = new File(rootPath);
        try{
            Scanner scanner = new Scanner(file);

            String line = scanner.nextLine();
            String[] Num = line.split(",");

            numOfVertices = Integer.parseInt(Num[0]);
            numOfFaces = Integer.parseInt(Num[1]);

            for(int i = 0; i < numOfVertices; i++){
                String line1 = scanner.nextLine();
                String[] vertices = line1.split(",");
                verticesList.add(new Vertice(Double.parseDouble(vertices[1]),Double.parseDouble(vertices[2]),Double.parseDouble(vertices[3])));
            }


            for(int i = 0; i < numOfFaces; i++){
                String line1 = scanner.nextLine();
                String[] faces = line1.split(",");
                faceList.add(new Face(Integer.parseInt(faces[0]),Integer.parseInt(faces[1]),Integer.parseInt(faces[2])));
            }
            scanner.close();
        }
        catch(Exception ex){
            System.out.println("exception");
        }
    }

    public void paint(Graphics g) {

        Graphics2D gx=(Graphics2D) g;
        gx.setColor(Color.WHITE);
        super.paint(gx);



        gx.setStroke(new BasicStroke(3));
        for(int i = 0; i < numOfVertices; i++){
            //project from world point to view point
            double viewPointX = -TransformParameters.param[2]*verticesList.get(i).x + TransformParameters.param[3] * verticesList.get(i).y;
            double viewPointY = TransformParameters.param[7]*verticesList.get(i).x + TransformParameters.param[6]*verticesList.get(i).y - TransformParameters.param[0]*verticesList.get(i).z;
            double viewPointZ = -TransformParameters.param[5]*verticesList.get(i).x - TransformParameters.param[4]*verticesList.get(i).y - TransformParameters.param[1]*verticesList.get(i).z +TransformParameters.R;
            //project from view point to screen point
            double projectedX = TransformParameters.R * viewPointX/viewPointZ;
            double projectedY = TransformParameters.R * viewPointY/viewPointZ;
            System.out.println(i+":"+projectedX+","+projectedY);

            viewPointList.add(new Vertice(viewPointX,viewPointY,viewPointZ));
            projectedVerticeList.add(new ProjectedVertice(projectedX, projectedY));

        }
        //draw
        for(int i = 0; i < numOfFaces; i++){
            //cur face
            Face curFace = faceList.get(i);
            ProjectedVertice v1 = projectedVerticeList.get(curFace.v1);
            ProjectedVertice v2 = projectedVerticeList.get(curFace.v2);
            ProjectedVertice v3 = projectedVerticeList.get(curFace.v3);
            Vertice v_1 = viewPointList.get(curFace.v1);
            Vertice v_2 = viewPointList.get(curFace.v2);
            Vertice v_3 = viewPointList.get(curFace.v3);



            //gx.setColor(new Color(255,255,255));
            //draw triangle
            int[] x = {(int)(v1.x*100+210),(int)(v2.x*100+210),(int)(v3.x*100+210)};
            int[] y = {(int)(v1.y*100+210),(int)(v2.y*100+210),(int)(v3.y*100+210)};
//            System.out.println(x[0]+","+x[1]+","+x[2]);
//            System.out.println(y[0]+","+y[1]+","+y[2]);
            gx.setColor(Color.blue);
            gx.drawPolygon(x, y, 3);
            //get color
            //get normal vector of the face
            double na = (v_2.y - v_1.y)*(v_3.z - v_1.z) - (v_2.z - v_1.z)*(v_3.y - v_1.y);
            double nb = (v_2.z - v_1.z)*(v_3.x - v_1.x) - (v_2.x - v_1.x)*(v_3.z - v_1.z);
            double nc = (v_2.x - v_1.x)*(v_3.y - v_1.y) - (v_2.y - v_1.y)*(v_3.x - v_1.x);
            //get angle
            //costheta = (x1x2+y1y2+z1z2)/[√(x1^2+y1^2+z1^2)*√(x2^2+y2^2+z2^2)]
            double costheta = nc * 1 /(Math.pow((Math.pow(na,2)+Math.pow(nb,2)+Math.pow(nc,2)),0.5));
            System.out.println(costheta);
            if(costheta < -0.8){
                gx.setColor(colorList.get(4));
            }
            else if(costheta <-0.6){
                gx.setColor(colorList.get(4));
            }
            else if(costheta <-0.4){
                gx.setColor(colorList.get(0));
            }
            else if(costheta <-0.2){
                gx.setColor(colorList.get(0));

            }
            else if(costheta <0){
                gx.setColor(colorList.get(1));

            }
            else if(costheta <0.2){
                gx.setColor(colorList.get(1));

            }
            else if(costheta <0.4){
                gx.setColor(colorList.get(2));

            }

            else if(costheta <0.6) {
                gx.setColor(colorList.get(2));

            }
            else if(costheta <0.8) {
                gx.setColor(colorList.get(3));

            }
            else {
                gx.setColor(colorList.get(3));
            }

            //fill triangle
            gx.fillPolygon(x, y, 3);
            //draw vertices
            gx.setColor(Color.BLUE);

//            int [] x = {45, 55, 75, 55, 63, 43, 17, 31, 12, 35, 45};
//            int [] y = {41, 65, 71, 83, 108, 88, 105, 78, 61, 63,41};

//            gx.drawLine((int)(v1.x*100+210), (int)(v1.y*100+210), (int)(v2.x*100+210) , (int)(v2.y*100+210) );
//            gx.drawLine((int)(v2.x*100+210), (int)(v2.y*100+210), (int)(v3.x*100+210), (int)(v3.y*100+210) );
//            gx.drawLine((int)(v3.x*100+210), (int)(v3.y*100+210), (int)(v1.x*100+210) , (int)(v1.y*100+210) );

            gx.setColor(new Color(0, 0, 255));
            gx.fillOval((int)(v1.x*100+205), (int)(v1.y*100+205), 10, 10);
            gx.fillOval((int)(v2.x*100+205), (int)(v2.y*100+205), 10, 10);
            gx.fillOval((int)(v3.x*100+205), (int)(v3.y*100+205), 10, 10);

        }
        viewPointList.clear();
        projectedVerticeList.clear();
        System.out.println("repaint");
    }


}