package agh.ics.oop.gui;

import agh.ics.oop.SingleField;
import agh.ics.oop.Vector2d;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    private final int ICON_SIZE = 40;
    private SingleField pointedObject;
    private Label label;
    private Image img;
    private ImageView imgView;
    private VBox vBox = new VBox();
    private String texturePath;
    public int getGuiPosX(int minX){
        return this.pointedObject.getPos().x - minX + 1;
    }
    public int getGuiPosY(int maxY){
        return maxY - this.pointedObject.getPos().y + 1;
    }

    public VBox getGridElement(){ return this.vBox; }
    public GuiElementBox(SingleField mapElement){
        pointedObject = mapElement;
        updateObject();
    }

    public void updateObject(){
        this.label = new Label(this.pointedObject.objectDescription());
        label.setFont(new Font("SegoeUI", 10));

        if(!rescourcePath(pointedObject.textureName()).equals(texturePath)) {
            texturePath = rescourcePath(pointedObject.textureName());
            try {
                img = new Image(new FileInputStream(texturePath));
            } catch (FileNotFoundException ex) {
                System.err.println("Exception caught while loading textures: " + ex);
                System.exit(-1);
            }
            imgView = new ImageView(img);
            imgView.setFitWidth(ICON_SIZE);
            imgView.setFitHeight(ICON_SIZE);
        }

        vBox.getChildren().clear();
        vBox.getChildren().addAll(imgView, label);
        vBox.setAlignment(Pos.CENTER);
    }

    private String rescourcePath(String filename){
        return "C:\\Users\\Distaff\\source\\Infa\\PO_2022_Distaff_proj_1\\src\\main\\rescources\\" + filename + ".bmp";
    }
}
