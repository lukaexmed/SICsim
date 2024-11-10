res	START 0

preberi
	TD	dev	
	JEQ	preberi	
	RD	dev	
	COMP	=10	.skippamo nl
	JEQ	preberi
	SUB	=48 	.iz ascii v number
	COMP	=0
	JEQ	halt
	RMO	A,X	.x bo nas "counter"
	LDB	=1	. base case
	SUBR 	B, X
	JSUB	fak
	. v A imamo rezultat, pretvorimo ga v ascii
	LDS	#10
	RMO 	A, T
	LDX	#0
	COMPR	A, S	. ce je manjsa od 10 lahko direkt napisemo
	JLT	izpisi
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
	JLT	preberi	
	J	print


fak	
	MULR	X,A
	SUBR	B, X
	COMPR	X,B
	JGT	fak

	RSUB


nl	TD	dev
	JEQ	nl
	LDCH	newline		.nalozi kodo za newline v akumulator
	WD	dev

	RSUB

izpisi
	ADD	=48
	TD	stdout
	JEQ	izpisi
	WD	stdout
	J	preberi

halt	J	halt


temp	RESW	1	
dev	BYTE	X'FA'
stdout	BYTE	1
newline	BYTE	10
buff	RESB	10
ostnk	RESW	1
	END res