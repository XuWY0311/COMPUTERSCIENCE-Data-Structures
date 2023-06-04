# Project 3 Prep

**For tessellating pluses, one of the hardest parts is figuring out where to place each plus/how to easily place plus on screen in an algorithmic way.
If you did not implement tesselation, please try to come up with an algorithmic approach to place pluses on the screen. This means a strategy, pseudocode, or even actual code! 
Consider the `HexWorld` implementation provided near the end of the lab (within the lab video). Note that `HexWorld` was the lab assignment from a previous semester (in which students drew hexagons instead of pluses). 
How did your proposed implementation/algorithm differ from the given one (aside from obviously hexagons versus pluses) ? What lessons can be learned from it?**

Answer:
1. Algorithm: 
1-1. Find the "keypoint" of the the left-most and upper-most plus. 
(Here, the "keypoint" means the left-most and upper-most point of the middle part of the plus.)
1-2. After we confirm the keypoint, we are able to draw the first plus in the world and do iteration.
(Here, the method of doing iteration is that the upper/lower/left/right plus's keypoints' coordinate individually satisfies a function of the coordinate of the keypoint of the center plus.) 
2. Difference with the algorithm in the video:
Our algos are similar,
the obvious difference lies in the choise of anchors.
I chose the anchor predeterminedly, as for the video, the coder chose arbitrarily.
3. Lessons:
3-1. To get the overall graph/world, we need to firstly determine the subpart, i.e. plus/keypoint;
3-2. In order to build the world with our individual subparts, we need to find the mapping to combine the subparts.
-----

**Can you think of an analogy between the process of tessellating pluses and randomly generating a world using rooms and hallways?
What is the plus and what is the tesselation on the Project 3 side?**

Answer:
An analogy: In order to find the next element, we need to find the specific function relationship between the previous keypoint and the next keypoint.

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating pluses.**

Answer:
1. Fill the world with NOTHING to avoid Null;
2. Write two helper functions for drawing rooms and hallways;
3. Put the rooms and hallways in the world.

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer:
A hallway only has 1-2 length on width in the world while the room can have any.