import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ImageDrawer extends JPanel {
    private int X = 1;
    private int O = 0;
    private BufferedImage paintImage = new BufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR);
    private java.util.List<Integer> field;

    public ImageDrawer(java.util.List<Integer> field) {
        this.field = field;
    }


    public void makePicture() {
        Graphics graphics = paintImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.drawRect(0, 0, 100, 100);
        graphics.drawRect(100, 100, 100, 100);
        graphics.drawRect(200, 200, 100, 100);
        graphics.drawRect(100, 0, 100, 100);
        graphics.drawRect(200, 0, 100, 100);
        graphics.drawRect(0, 100, 100, 100);
        graphics.drawRect(200, 100, 100, 100);
        graphics.drawRect(0, 200, 100, 100);
        //1st area
        if (field.get(0) == X) {
            graphics.drawLine(20, 20, 80, 80);
            graphics.drawLine(20, 80, 80, 20);
        } else if(field.get(0) == O){
            graphics.drawOval(20, 20, 65, 65);
        }
        else{
            graphics.drawString("1",20,20);
        }
        //2nd area
        if (field.get(1) == X) {
            graphics.drawLine(120, 20, 180, 80);
            graphics.drawLine(120, 80, 180, 20);
        } else if(field.get(1) == O) {
            graphics.drawOval(120, 20, 65, 65);
        } else {
            graphics.drawString("2",120,20);}
        //3rd area
        if (field.get(2) == X) {
            graphics.drawLine(220, 20, 280, 80);
            graphics.drawLine(220, 80, 280, 20);
        } else if(field.get(2) == O) {
            graphics.drawOval(220, 20, 65, 65);
        } else {
            graphics.drawString("3",220,20);}
        //4th area
        if (field.get(3) == X) {
            graphics.drawLine(20, 120, 80, 180);
            graphics.drawLine(20, 180, 80, 120);
        } else if(field.get(3) == O) {
            graphics.drawOval(20, 120, 65, 65);
        } else {
            graphics.drawString("4",20,120);}
        //5th area
        if (field.get(4) == X) {
            graphics.drawLine(120, 120, 180, 180);
            graphics.drawLine(120, 180, 180, 120);
        }else if(field.get(4) == O) {
            graphics.drawOval(120, 120, 65, 65);
        } else {
            graphics.drawString("5",120,120);}
        //6th area
        if (field.get(5) == X) {
            graphics.drawLine(220, 120, 280, 180);
            graphics.drawLine(220, 180, 280, 120);
        } else if(field.get(5) == O) {
            graphics.drawOval(220, 120, 65, 65);
        } else {
            graphics.drawString("6",220,120);}
        //7th area
        if (field.get(6) == X) {
            graphics.drawLine(20, 220, 80, 280);
            graphics.drawLine(20, 280, 80, 220);
        } else if(field.get(6) == O){
            graphics.drawOval(20, 220, 65, 65);
        } else {
            graphics.drawString("7",20,220);}
        //8th area
        if (field.get(7) == X) {
            graphics.drawLine(120, 220, 180, 280);
            graphics.drawLine(120, 280, 180, 220);
        } else if(field.get(7) == O) {
            graphics.drawOval(120, 220, 65, 65);
        } else {
            graphics.drawString("8",120,220);}
        //9th area
        if (field.get(8) == X) {
            graphics.drawLine(220, 220, 280, 280);
            graphics.drawLine(220, 280, 280, 220);
        }else if(field.get(8) == O) {
            graphics.drawOval(220, 220, 65, 65);
        } else {
            graphics.drawString("9",220,220);
        }
    }
    public byte[] getBytes() throws IOException{
        makePicture();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(paintImage, "PNG", baos );
        byte[] imageInByte=baos.toByteArray();
        return imageInByte;
    }

    public java.util.List<Integer> getField() {
        return field;
    }

    public void setField(List<Integer> field) {
        this.field = field;
    }
}