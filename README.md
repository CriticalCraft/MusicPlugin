# MusicPlugin

This is a Music plugin for a mincraft server which uses the plugin MCJukebox. This plugin is for playing music per biomes and in the future also world guard regions. It is possible to have a playlist of songs per biome.

## Getting Started

Use your desired IDE
Install the minecraft plugin API
Import Project


### Prerequisites

U will need a IDE with the minecraft pligin API and the external API from MCJukebox.

### Installing

- When installed and started click "import project"
- GOTO the location where u have the git repository and search for the "pom.xml"
- U probably need to install the Minecraft plugin API
- GOTO File -> Settings -> Plugins  and search for "Minecraft Development"
- Click Install

- For a extern API u will need to include the file path the the plugin 
- So for the BetterMusic plugin u goto File -> Project Structure -> Libraries 
- Click the + -> Java -> Look for the src file winthin the project -> deps 
- And click the jar
- U can now work on the plugin


## Using the plugin
 
 Variables that are required are <>
 Variables that are optional are []
 
 There are 3 commands for the plugin
 - `/bm add  <name> <playlist/biome> <min> <sec> <url> [time: day/night]`
 - `/bm check [biome]`
 - `/bm delete <name>`




## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

