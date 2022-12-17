package agh.ics.oop;

public class Grass implements IMapElement {
    protected Vector2d pos;

    Grass(Vector2d initPos){
        this.pos = initPos;
    }

    public Vector2d getPos(){
        return this.pos;
    }

    public String toString(){
        return "*";
    }

    public String textureName(){ return "grass"; }
    public String objectDescription(){ return "grass"; }

}
