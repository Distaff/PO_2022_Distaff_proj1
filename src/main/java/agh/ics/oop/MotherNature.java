package agh.ics.oop;

import java.util.*;

public class MotherNature {
    private List<SingleField> fields;
    private final Comparator<SingleField> cmp;
    private final IWorldMap worldMap;
    private final Random rand = new Random();

    public MotherNature(IWorldMap worldMap, Comparator<SingleField> cmp){
        this.cmp = cmp;
        this.worldMap = worldMap;
        this.fields = worldMap.getAllFields();

        plantGrass(worldMap.getSimulationOptions().beginningGrassCount());
    }

    void plantGrass(int grassCount){
        int privilegedCounter = 0;
        int unprivilegedCounter = 0;
        for(int i = 0; i < grassCount; i++){
            if(rand.nextInt(100) > 20) privilegedCounter++;
            else unprivilegedCounter++;
        }

        this.fields.sort(cmp);
        List<SingleField> privilegedFields = new ArrayList<>(fields.subList(0, fields.size() / 5));
        List<SingleField> unprivilegedFields = new ArrayList<>(fields.subList(fields.size() / 5, fields.size()));

        Collections.shuffle(privilegedFields);
        Collections.shuffle(unprivilegedFields);

        for(; privilegedCounter > 0; privilegedCounter--){
            if(privilegedFields.size() < 1)
                break;

            SingleField field = privilegedFields.remove(privilegedFields.size() - 1);
            field.growGrass();
            worldMap.grassHasGrown(field.fieldPos);
        }

        //If privileged fields are arleady full, planting on unprivilieged
        for(int i = unprivilegedCounter + privilegedCounter; i > 0; i--){
            if(unprivilegedFields.size() < 1)
                throw new IllegalArgumentException("Planting more grass than there are fields is not allowed!");

            SingleField field = unprivilegedFields.remove(privilegedFields.size() - 1);
            field.growGrass();
            worldMap.grassHasGrown(field.fieldPos);
        }
    }
}
