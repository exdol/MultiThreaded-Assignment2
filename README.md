# MultiThreaded-Assignment2
Minotaurs Birthday Party and Minotaurs Crystal Vase problems

## How to compile and run program from the command prompt:
1. Pull this repo and cd into your local directory using a terminal/command prompt
2. Enter the command `java MinotaursBirthdayParty.java` or `java MinotaursCrystalVase.java` to both compile and run the respective program
3. Outputs will be displayed within the terminal where the previous command was run
4. In the terminal, when prompted to, enter the desired number of guests

## Minotaurs Birthday Party
For the solution to this problem, I simulated each guest's labyrinth visit with `Thread.sleep(time in miliseconds)` and used a randomized range of 360ms to 1080ms to make the problem more realistic. In the context of the problem, the miliseconds are seconds to the guests in question. Additionally, I used ReentrantLock to simulate the one at a time rule for each guest in the labyrinth. At the end of the labyrinth, guests have a 50% chance to decide whether to re-enter the maze or to not re-enter; this simulates the Minotaur picking any particular guest multiple times to re-enter.

I approached this problem with the strategy of each guest (ordering if necessary and) eating the cupcake at the end of the labyrinth. Each guest will keep track of whether or not they have eaten a cupcake. Once everyone has unanimously eaten at least 1 cupcake, the guests can announce that they have all visited the labyrinth at least once.

## Minotaurs Crystal Vase
Similarly to the Minotaurs Birthday Party problem, I used ReentrantLock to simulate the one at a time rule for each guest in the showroom to view the crystal vase.

On entry into the showroom, each guest will change the sign to "BUSY" and change the sign to "AVAILABLE" when they leave. Each guest will only enter if the sign is "AVAILABLE". This way, more than one guest will **not** be allowed into the showroom at the same time; thus guests cannot gather around the vase nor accidentally break it.
