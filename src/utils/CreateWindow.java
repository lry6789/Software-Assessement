package utils;

import javax.swing.JFrame;

public class CreateWindow extends JFrame{

    public CreateWindow(){
        this.setSize(600,500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Display");
        TransformParameters.init();

        Draw graph = new Draw();
        graph.addMouseListener(new MyMouseListener());//添加监听器
        graph.getInfo();

        this.add(graph);

    }

}