package agh.ics.oop.gui;

import agh.ics.oop.SingleField;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    private final int ICON_SIZE = 40;
    private SingleField pointedField;
     Label label;
    private VBox vBox = new VBox();
    private Image img;
    ImageView imgView;
    private String texturePath;
    private Image backgroundImg;
    ImageView backgroundImgView;
    private String backgroundPath;


    public VBox getGridElement(){ return this.vBox; }
    public GuiElementBox(SingleField mapElement){
        pointedField = mapElement;
        updateObject();
    }

    public void updateObject(){
        this.label = new Label(this.pointedField.objectDescription());
        label.setFont(new Font("SegoeUI", 10));

        if(!rescourcePath(pointedField.textureName()).equals(texturePath)) {
            texturePath = rescourcePath(pointedField.textureName());
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

        if(!rescourcePath(pointedField.backgroundTextureName()).equals(backgroundPath)) {
            backgroundPath = rescourcePath(pointedField.backgroundTextureName());
            try {
                backgroundImg = new Image(new FileInputStream(backgroundPath));
            } catch (FileNotFoundException ex) {
                System.err.println("Exception caught while loading textures: " + ex);
                System.exit(-1);
            }
            backgroundImgView = new ImageView(backgroundImg);
            backgroundImgView.setFitWidth(ICON_SIZE);
            backgroundImgView.setFitHeight(ICON_SIZE);
        }

        vBox.getChildren().clear();
        vBox.getChildren().addAll(backgroundImgView, imgView, label);
        vBox.setAlignment(Pos.CENTER);
    }

    private String rescourcePath(String filename){
        return "src/main/rescources/" + filename + ".bmp";
    }
}
