fib 	START 	0
	LDX	#1		.za odstevanje
	LDT	#1
	STT	res		.da lahko mnozimo
	JSUB	stackinit
	LDA	#5
	SUB	#1
	RMO 	A, T		. v T n-1
	SUB 	#1
	RMO	A, S		. v S n-2
	STS	tempS
	STT	tempT
	JSUB	fak		.prvi stack
	JSUB	halt
	
fak
	LDA	S
	COMP	#1		.base case
	JEQ	konec

	.push S, T in Link
	STS	tempS		. vrednost A shranimo
	STT	tempT
	STL	tempL		. vrednost link reg shranimo
	LDA	tempT
	JSUB	stackpush
	LDA	tempS
	JSUB	stackpush
	LDA	tempL
	JSUB	stackpush	. na stack damo tudi link register

	. dekrement in dalje
	SUBR	X, T
	SUBR	X, S
	JSUB	fak		. rekurziramo		.drugi, tretji, četrti, ... link reg za izračun


	. ko pridemo do konca dekrementiranja. moramo začet množit
	JSUB	stackpop
	STA	tempL
	JSUB	stackpop

	.množimo 
	MUL	res
	STA	res
	LDL	tempL
	RSUB

konec	RSUB


stackinit
	LDA 	#sklad		.inicializiramo stackPt na zacetni naslov sklada
	STA	skladPt
	RSUB


stackpush
	STA	@skladPt
	STA	temp		.shranimo A v temp
	LDA	skladPt
	ADD	=3		. konstanta 3, ker imamo 24 bitne registre
	STA	skladPt		.updejtamo skladPt
	LDA	temp
	RSUB



stackpop
	STA	temp
	LDA	skladPt
	SUB	=3
	STA	skladPt
	LDA	temp
	LDA	@skladPt
	RSUB


halt	J	halt

skladPt	RESW	1
temp	RESW	1	
tempT	RESW	1
tempS	RESW	1	
tempL	RESW	1
res 	RESW	1
res1 	RESW	1
res2	RESW	1
sklad	RESW	255	.velikost sklada
	END	fib