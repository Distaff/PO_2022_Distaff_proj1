package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genotype {
    private Random rand = new Random();
    private int currentGene = 0;
    private SimulationOptions simulationOptions;
    private List<Rotations> genes;

    public Rotations nextGene() {
        if(simulationOptions.crazyBehavior() && rand.nextInt(100) < 20)
            currentGene = rand.nextInt(simulationOptions.genotypeSize());
        else
            currentGene = (currentGene + 1) % simulationOptions.genotypeSize();
        return genes.get(currentGene);
    }

    public Genotype(SimulationOptions simulationOptions){
        this.simulationOptions = simulationOptions;
        this.genes = new ArrayList<>();

        for(int i = 0; i < simulationOptions.genotypeSize(); i++){
            this.genes.add(Rotations.values()[rand.nextInt(8)]);
        }
    }

    public Genotype(Genotype genotype1, Genotype genotype2, float energyRatio){
        this.simulationOptions = genotype1.simulationOptions;
        this.genes = new ArrayList<>();

        int crossPoint = (int) (simulationOptions.genotypeSize() * energyRatio / (1 + energyRatio));    //if a+b=c and a/b=x:   a = cx/(1+x)
        this.genes.addAll(genotype1.genes.subList(0, crossPoint));
        this.genes.addAll(genotype2.genes.subList(crossPoint, simulationOptions.genotypeSize()));

        if(simulationOptions.heavyMutations())
            heavilyMutate();
        else
            mutate();
    }

    private void mutate(){
        int mutationsCount = rand.nextInt(simulationOptions.minMutationCount(), simulationOptions.maxMutationCount());
        for (int mutatingGene : rand.ints(0, simulationOptions.genotypeSize()).distinct().limit(5).toArray()){
            genes.set(mutatingGene, genes.get(mutatingGene).smallChange());
        }
    }
    private void heavilyMutate(){
        int mutationsCount = rand.nextInt(simulationOptions.minMutationCount(), simulationOptions.maxMutationCount());
        for (int mutatingGene : rand.ints(0, simulationOptions.genotypeSize()).distinct().limit(5).toArray()){
            genes.set(mutatingGene, genes.get(mutatingGene).randomVal());
        }
    }

    @Override
    public String toString(){
        return genes.toString() + "; current active index: " + currentGene;
    }

    public String description(){ return genes.toString(); }
}
