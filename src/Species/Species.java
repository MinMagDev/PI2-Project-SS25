package Species;

import Genom.DNA;
import Canvas.*;
import Particle.Particle;
import Social.SocialParticleRenderer;
import World.World;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Species {
    private static List<Species> species = new ArrayList<Species>();

    private final DNA dna;

    public void setColor(Color color) {
        this.color = color;
    }

    private Color color;

    public Color getColor() {
        return color;
    }

    public DNA getDNA() {
        return dna;
    }

    private final Map<Species, Integer> interactions;

    public Species(DNA dna) {
        this.dna = dna;
        this.color = dna.getColor();
        this.interactions = new HashMap<>();
        species.add(this);
        for (Species s : species) {
            s.updateInteractions();
        }
    }

    public int getInteractionWith(Species other) {
        return interactions.get(other);
    }

    void updateInteractions(){
        for(int i = 0; i < species.size(); i++) {
            interactions.put(species.get(i), dna.getInteraction(i));
        }
    }

    public double getSpeed() {
        return dna.getSpeed();
    }

    public double getInteractionRadius(){
        return dna.getRadius();
    }

    public static Drawable createDemo(int species){


        // list of all colors
        Color[] colors = {
                Color.BLACK,
                Color.BLUE,
                Color.CYAN,
                Color.DARK_GRAY,
                Color.GRAY,
                Color.GREEN,
                Color.LIGHT_GRAY,
                Color.MAGENTA,
                Color.ORANGE,
                Color.PINK,
                Color.RED,
                Color.WHITE,
                Color.YELLOW
        };

        List<SpeciesParticle> particles = new ArrayList<>();

        if(species > colors.length) {
            throw new IllegalArgumentException("Species " + species + " is out of bounds");

        }

        for(int i = 0; i < species; i++) {
            Species s = new Species(new DNA());
            s.setColor(colors[i]);
            particles = Stream.concat(particles.stream(), Arrays.stream(SpeciesParticle.makeParticles(30, s, 400, 400))).toList();
        }



        var renderer = new SocialParticleRenderer(particles);

        return new World(400, 400, particles, renderer);
    }


}
