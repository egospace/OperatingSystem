package DiskShiftScheduling;

import javax.swing.*;
import java.awt.*;


public class Draw extends JFrame {
    MyPanel mp = null;
    public Draw(int[] list,int[] out){
        mp = new MyPanel();
        mp.list = list;
        mp.out = out;
        this.add(mp);
        this.setSize(400,300);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class MyPanel extends JPanel	//我自己的面板，用于绘图和实现绘图区域
{
    int[] list;
    int[] out;
    //覆盖JPanel的paint方法
    //Graphics是绘图的重要类，可以理解成一支画笔
    public void paint(Graphics g) {
        //1.调用父类函数完成初始化
        super.paint(g);
        //画直线
        g.drawLine(10, 20, 200, 20);
        for (int i=0; i < list.length ;i++){
            g.drawString(String.valueOf(list[i]),list[i],20);
            //画圆
            g.drawOval(list[i], 20, 5, 5);
            g.setColor(Color.RED);		//设置颜色
            g.fillRect(list[i], 20, 5, 5);
            g.setColor(Color.BLACK);
        }
        int y = 40;
        for (int i=0; i < out.length-1 ; i++){
            //画圆
            g.drawOval(out[i], y, 5, 5);
            g.setColor(Color.RED);		//设置颜色
            g.fillRect(out[i], y, 5, 5);
            g.setColor(Color.BLACK);
            //画直线
            g.drawLine(out[i], y, out[i+1], y+20);
            //画圆
            g.drawOval(out[i+1], y+20, 5, 5);
            g.setColor(Color.RED);		//设置颜色
            g.fillRect(out[i+1], y+20, 5, 5);
            g.setColor(Color.BLACK);
            y+=20;
        }
    }
}