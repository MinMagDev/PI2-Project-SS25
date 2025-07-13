# Evolving Clusters
A particle Simulation inspired by the [Ventrella Cluster](https://www.ventrella.com/Clusters/intro.html) Algorithm.
With added functionality for dying, reproduction and mutation.

## How to use

### Runtime actions and Settings
Starting the **main** Method in the DemoWindow class, will open up a
clear window with some settings on the side. 

The buttons on top are used while the simulation is running:
- ⚡: "Zap". Applies a random force to all particles.
- ⏯: Pauses the simulation for viewing and editing the particle
- ☢ - Irradiate: Your cursor becomes a big red circle, and when you click, evey particle covered will mutate a lot. To return to a normal cursor, click the button again.

####

- *Recalculate species*: Starts a KMeans Algorithm with each particle and species, may create a new species from the mutated specimens

The sliders below the buttons are used for changing the start parameters
- *Max Speed*: Sets the maximum speed at which the particle can move
- *Social Radius Multiplier*: Changes the size of the interaction radius of all 
particles. Can be seen as how far the particles can see
- *Speed Multiplier*: Changes the speed of all particles
- *Species*: The amount of different species in the simulation. Default: 5
- *Specimens*: How many specimens / particles are created at the start

The **(Re)start** Button on the button, starts a new simulation with the specified settings.

### Pause actions
Whilst the simulation is pause, clicking on a particle opens up the DNA Editor Window
for this particle.

On the top is the whole DNA of the particle, where the interesting parts are highlighted.

The first 4 groups of 6 Nucleotides are the stats of the Particle:
- *Speed*: Value between 0 and 1. Sets the speed of the particle
- *Interaction Radius*: Value between 0 and 40. Sets how far this particle can notice other.
- *Hunger*: Value between 0 and 0.005. Sets how fast the particle becomes smaller and dies
- *Reproduction Probability*: The Probability each iteration to add to the reproduction count, if full a new child is created

The Nucleotide sets how much is added to value by increasing amounts: A C G T. Whilst A adds nothing, T adds the most.

All Positions after that sets how the particle interacts to other particles:
- *A*: Gets **repelled** by that type
- *C*: Is **neutral** towards that type
- *G*: Trys to create a **spring** to that type (Always stays at a specific distance)
- *T*: Gets **attracted** by that species, and if not of the same, trys to eat it.


Clicking on a Nucleotide and the pressing either A,C,G,T changes the highlighted nucleotide to the chosen.
Pressing escape unselects the nucleotide.



*P.S.: All comments sign with D.R.E.C.K are "Dubiously Rendered Explanation by Computational Knowledge" (created by AI)*



