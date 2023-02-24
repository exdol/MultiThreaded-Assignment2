# MultiThreaded-Assignment2
Minotaurs Birthday Party and Minotaurs Crystal Vase problems

## How to compile and run program from the command prompt:
1. Pull this repo and cd into your local directory using a terminal/command prompt
2. Enter the command `java MinotaursBirthdayParty.java` or `java MinotaursCrystalVase.java` to both compile and run the respective program
3. Outputs will be displayed within the terminal where the previous command was run
4. In the terminal, when prompted to, enter the desired number of guests

## Minotaurs Birthday Party
For the solution to this problem, I simulated each guest's labyrinth visit with `Thread.sleep(time in miliseconds)` and used a randomized range of 360ms to 1080ms to make the problem more realistic. In the context of the problem, the miliseconds are seconds to the guests in question. Additionally, I used ReentrantLock to simulate the one at a time rule for each guest in the labyrinth. At the end of the labyrinth, guests have a 50% chance to decide whether to re-enter the maze or to not re-enter; this simulates the Minotaur picking any particular guest multiple times to re-enter.

I approached this problem with the strategy of each guest eating the cupcake at the end of the labyrinth only if it is their first time in the labyrinth. Each guest will be followed by the 0th thread/first guest and it will keep track of whether there was an empty plate left behind or not. Once the total number of empty plates found has reached numGuests-1 (not counting the 0th thread as it won't take a cupcake, but leave one instead), then the 0th thread will announce that all guests have visited the labyrinth. An alternative of this would be to wait until the 0th guest finds that the cupcake they left hasn't been eaten by the next time they re-enter the labyrinth, but it is not certain that another guest would not re-enter prior to that in place of a guest that hasn't taken a cupcake yet. 

## Minotaurs Crystal Vase
Similarly to the Minotaurs Birthday Party problem, I used ReentrantLock to simulate the one at a time rule for each guest in the showroom to view the crystal vase. On entry into the showroom, each guest will change the sign to "BUSY" and change the sign to "AVAILABLE" when they leave. Each guest will only enter if the sign is "AVAILABLE". This way, more than one guest will **not** be allowed into the showroom at the same time; thus guests cannot gather around the vase nor accidentally break it.

One advantage of this would be that guests can easily and efficiently know when to enter the showing room. However, a disadvantage is that guests could check back to the showing room multiple times while it is occupied/busy, wasting time and energy (computation) in the process.

