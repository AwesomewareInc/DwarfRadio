# DwarfRadio

Code for the "Dwarf Radio" from which you can listen to music in real time, using generated ogg files from the resource pack.

## Building/testing

Build with `mvn package`

Then do 
```
docker run --rm -e EULA=true  -p 25565:25565 -v $(pwd)/target:/data/plugins cmunroe/spigot:1.18.2
```