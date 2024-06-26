# BearMaps

Bear Maps is a web mapping application that finds the shortest route between two points in Berkeley. Features: turn-by-turn directions, Autocomplete, and location search.
This is a project from UC Berkeley's [CS61B Data Structures](https://sp18.datastructur.es/materials/proj/proj3/proj3) course.

I implemented the back end features: [**Rasterer**](https://github.com/dramallama26/BearMaps/blob/main/bearmaps/server/handler/impl/RasterAPIHandler.java), [**KDTree**](https://github.com/dramallama26/BearMaps/blob/main/bearmaps/utils/ps/KDTree.java), [**AugmentedFeatures**](https://github.com/dramallama26/BearMaps/blob/main/bearmaps/AugmentedStreetMapGraph.java), and [**Router**](https://github.com/dramallama26/BearMaps/blob/main/bearmaps/Router.java).

<img src="demo.gif">

###
| File | Description |
| --- | --- |
| [Rasterer](https://github.com/dramallama26/BearMaps/blob/main/bearmaps/server/handler/impl/RasterAPIHandler.java) | Renders map images given a user's requested area and level of zoom |
| [KDTree](https://github.com/dramallama26/BearMaps/blob/main/bearmaps/utils/ps/KDTree.java) | Implemented KD Trees for Route finding|
| [AugmentedFeatures](https://github.com/dramallama26/BearMaps/blob/main/bearmaps/AugmentedStreetMapGraph.java) | Adds Autocomplete and Euclidean distance calculator |
| [Router](https://github.com/dramallama26/BearMaps/blob/main/bearmaps/Router.java) | Uses A* search algorithm to find the shortest path between two points in Berkeley; uses shortest path to generate navigation directions. |

### How to run
1. git clone this repo and [library-sp18](https://github.com/Berkeley-CS61B/library-sp18/tree/proj3), which contains Open Street Maps images and dataset

2a. Compiling in a terminal (Bear Maps uses Maven as its build system):
```
If you do want to use it through the command line here are some basic instructions: 
Windows users: Follow the instructions here, making sure to adjust them to your machine which should already have JDK8 installed. 
Use command prompt, not git bash. 
Mac users: brew install maven Ubuntu users: sudo apt-get install maven.

You can then use the mvn compile and mvn exec:java -Dexec.mainClass="MapServer" targets to run MapServer, 
after patching your pom.xml to include src/static as a sources root. 
Do so by renaming pom_alternate.xml to pom.xml. You can also run the tests with mvn test. 
```

2b. Running with IntelliJ:
```
1. New -> Project from Existing Sources -> select Bear-Maps -> "Import Project from External Model"(Maven)
2. At the Import Project window, check: “Import Maven projects automatically”
3. Run MapServer.java
```
