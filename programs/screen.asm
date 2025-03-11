screen	START 0
	.shranimo si vse vrednosti
	LDA	scrrows
	MUL	scrcols
	STA	scrlen
	ADD	scree
	SUB	#1
	STA	scrend
	JSUB	scrfill
	JSUB	scrclear
	JSUB	printch
	JSUB	scrclear
sem	JSUB	printstr
	J	sem


halt	J	halt



scrfill
    	LDB 	#43
	LDA	scrend
	J	loop
scrclear
	LDB	#0
    	LDA 	scrend

loop
    	SUB	 #1 . target inkrementiramo
    	STA 	targt
    	+STB 	@targt . napisemo znak na target .
    	COMP 	scree
    	JGT 	loop

    	RSUB


printch
	LDA	row
	MUL	scrcols
	ADD	col
	ADD	scree
	SUB	#1
	LDB	#33
	STA	targt
	+STB	@targt

	RSUB

printstr
	LDA	row
	MUL	scrcols
	ADD	col
	ADD	scree
	SUB	#1
	STA	targt
	LDX	#0

inp					.branje napisanega
		. da ne popackamo registrov
	TD	stdin
	JEQ	inp
	CLEAR	A
	RD	stdin		. beremo vhod
	LDB	#10
	COMPR 	A, B		. preverimo ce je newline na koncu
	JEQ	na	. ce je greo na konec
	LDB	#0
	COMPR	A, B
	JEQ	na
	STCH	strng, X	. shranimo char na index X v A
	TIX	#0		. inkrementiramo x ampak nic ne preverjamo
	J	inp

inp_end	LDA	#0		. na X damo null terminator
	STCH	strng, X	. na ix X je null terminator
	LDA	temp		. ponastavimo A

na
	LDB	#1
	SUBR	B,X
	LDA	targt	
	SUBR	X,A
	STA	targt
	CLEAR	A
pisi	
	LDCH	strng, X 	. v A nalozimo char
	LDB	#1
	SUBR	B,X
	CLEAR	Bja
	RMO	A,B		. zdaj je v B
	LDA	targt
    	SUB	 #1 . target inkrementiramo
    	STA 	targt
    	+STB 	@targt . napisemo znak na target .
	LDB	#0
    	COMPR	X,B
	JEQ	pisi
    	JGT 	pisi

	RSUB


scree	WORD	X'00B800'
scrcols	WORD	80 .80crk
scrrows	WORD	25 .25crk
scrlen	RESW	1
scrend	RESW	1
targt	RESW	1
col	WORD	40			.v sredini izpise
row	WORD	12
stdin	WORD	0

strng	RESB	255		. max stevilo crk v vrstici
adresa	RESW	1
temp	RESW	1
buff	RESB	10
ostnk	RESW	1
	END screen