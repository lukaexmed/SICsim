#cd src || exit

mkdir /bin

javac -d bin src/simulator/*.java src/simulator/UI/*.java

cd bin

java simulator.Main ../inp/"$1"


