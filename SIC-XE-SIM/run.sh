#cd src || exit

mkdir /bin

javac -d bin src/sic/simulator/*.java src/sic/simulator/UI/*.java

cd bin

java sic.simulator.Main ../inp/"$1"


