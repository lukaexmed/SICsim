rec 	START 	0
	LDB	#1
	LDS	#10

	JSUB	stackinit

zacetek
	LDT	#1
	STT	res		.
	LDX	#0
	LDS	#10
	LDB	#1
	JSUB	preberi
	JSUB	fak		.prvi link reg, za izhod iz programa
	RMO	A, T
	COMP	#10		. ce je rezultat manjši od 10, ga direkt izpisemo
	JLT	izpisi
	JEQ	num2str		. ce ni ga mormo dat v string
	JGT	num2str
halt	J	halt
	
.===== rekurzija ======
fak
	COMP	#1		.base case
	JEQ	konec

	.push A in Link
	STA	tempA		. vrednost A shranimo
	STL	tempL		. vrednost link reg shranimo
	JSUB	stackpush
	LDA	tempL
	JSUB	stackpush	. na stack damo tudi link register
	LDA	tempA		. A je spet v orginalnem stanju

	. dekrement in dalje
	SUBR	T, A		. A dekrementiramo
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



preberi	
	CLEAR 	A
	TD	dev	
	JEQ	preberi	
	RD	dev	
	COMP	=10	.skippamo nl
	JEQ	preberi
	SUB	=48 	.iz ascii v number
	COMP	=0	. ze je 0 je konec
	JEQ	halt
	RSUB

izpisi
	ADD	=48
	TD	stdout
	JEQ	izpisi
	WD	stdout
	J	nl

nl	TD	stdout
	JEQ	nl
	LDCH	newline		.nalozi kodo za newline v akumulator
	WD	stdout
	J	zacetek


num2str
	DIVR 	S, T		. T / S (A/10)
	STT 	ostnk		. v T je T / S 12345/10 -> 1234
	MULR 	S, T		. v T je T/S * S
	SUBR 	T, A		. V A je  A(T) - T/S * S 12345mod10 -> 5
	STA 	temp		. mod

	STCH	buff, X
	TIX	#0

	LDT	ostnk
	LDA	ostnk
	COMP	#0
	JGT	num2str
	. zdaj mamo v buffer reverse stringa ki ga hocemo izpisat
	SUBR 	B, X
print
	LDCH	buff, X
	ADD	=48	. za v ascii
	TD	stdout
	JEQ	print
	WD	stdout
	SUBR	B, X
	LDS	#0
	COMPR	X, S
	JLT	nl	
	J	print


.====== stack =======

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

.====== data =======

skladPt	RESW	1
temp	RESW	1	
tempA	RESW	1	
tempL	RESW	1
res 	RESW	1
dev	BYTE	X'FA'
stdout	BYTE	1
newline	BYTE	10
buff	RESB	10
ostnk	RESW	1
sklad	RESW	255	.velikost sklada
	END	rec