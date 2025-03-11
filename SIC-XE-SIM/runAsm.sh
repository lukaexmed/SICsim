#cd src || exit

mkdir /bin

javac -d bin src/sic/simulator/Opcode.java src/sic/asm/Asm.java src/sic/asm/code/*.java src/sic/asm/mnemonics/*.java src/sic/asm/parsing/*.java

cd bin

java sic.asm.Asm ../inp/"$1"
