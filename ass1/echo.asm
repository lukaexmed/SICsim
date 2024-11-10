echo	START 0

main	
	LDX	#0
	LDB	#10
	JSUB	inp
	LDA	#strng
	LDB	#0
	JSUB	string
	JSUB	nl
	J	main

string				. pisanje prebranega
	STA	adresa		. temp da lahko povrnemo
	RMO	X, T
	LDX	#0		. indeks X na zacetek stringa

loop1
	CLEAR	A
	LDCH	strng, X	. v A damo prvo crko niza
	COMPR	A, B	. preverimo ce je to null terminator
	JEQ	halt

td1	TD	stdout		. preverimo stdout
	JEQ	td1
	WD	stdout		. pisemo stdout
	TIXR	T		. inkrementiramo
	JEQ	end1
	J	loop1


end1		
	RSUB



inp				.branje napisanega
	STA	temp		. da ne popackamo registrov
loop2	TD	dev
	JEQ	inp
	RD	dev		. beremo vhod
	LDB	#10
	COMPR 	A, B		. preverimo ce je newline na koncu
	JEQ	inp_end		. ce je greo na konec
	LDB	#0
	COMPR	A, B
	JEQ	halt
	STCH	strng, X	. shranimo char na index X v A
	TIX	#0		. inkrementiramo x ampak nic ne preverjamo
	J	loop2

inp_end	LDA	#0		. v X damo null terminator
	STCH	strng, X	. na ix X je null terminator
	LDA	temp		. ponastavimo A
	RSUB


char			.izpis znaka, podanega v registru A.
	TD	stdout
	JEQ	char
	WD	stdout

	RSUB

nl			.newline
	TD	stdout
	JEQ	nl
	LDCH	newline		.nalozi kodo za newline v akumulator
	WD	stdout

	RSUB

num	
	LDA	#1234		. stevilo zapisano v A
	LDS	#10
	RMO 	A, T
	LDX	#0
	LDB	#1

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

.to nam zdaj da reversed order
	SUBR	B,X
str2ascii
	LDCH	buff, X
	ADD	=48	. za v ascii
	TD	stdout
	JEQ	str2ascii
	WD	stdout
	SUBR	B, X
	LDS	#0
	COMPR	X, S
	JLT	halt	
	J	str2ascii

halt	J	halt

newline	BYTE	10
space	BYTE	32
stdin	BYTE	0
stdout	BYTE	1
strng	RESB	255		. max stevilo crk v vrstici
adresa	RESW	1
temp	RESW	1
dev	BYTE	X'AA'	
buff	RESB	10
ostnk	RESW	1


	END	echo