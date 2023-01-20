# RickAndMorty-GraphQL
![Badge IN PROGRESS](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

This project was built with Android Studio Dolphin, using clean layered architecture in a modular structure. The core module is the center and other modules branch off of it. The data module wraps remote, hubSrc, and local modules. Each module has its own interface. The hubSrc module implements these interfaces. There's also a common and a domain module inside the core. The features module outside of the core uses the domain module and its usecases.

<img src="images/graph.svg"/>
<img src="images/core.png" width="50%"/>

