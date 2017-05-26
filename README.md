<h1> PLPA-robotControl </h1>

<h2> Description </h2>

<p>The project was created as a part of the Programming Language Paradigms course at Aarhus University. 
Its main goal was to design and implement robot control system made for a factory application. 
Its main purpose was to provide the interface for programming robot behaviour. </p>

<p>The project was implemented with use of two different programming paradigms: <br>
- imperative (Java),<br>
- functional (Scheme).</p>

<p>The system consists of two independent components: Java and Scheme, communicating with each other. The project was designed with focus on proper separation of concerns between this two components. </p>

<p>The Scheme component can be viewed as robot's internal memory, and as a result it contains all robot state, and defines its behaviour
based on programmed movement restrictions and floor state.</p>

<p> The imperative part is written in Java, and is build as a JavaFX application \cite{javafx}. This component contains all the GUI logic
and uses a Kawa framework for communicating with the functional part. This framework allows Scheme to run in a Java environment. </p>

<p>More information can be found in pdf report in repo's Report directory.</p>


