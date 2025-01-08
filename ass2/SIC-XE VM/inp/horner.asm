horner START 0
	LDA x
	MUL x4
	ADD x3
	MUL x
	ADD x2
	MUL x
	ADD x1
	MUL x
	ADD x0
	STA result
	
.hitrejši! load in store se zgodita samo 2x + manj komand

. (((x4*x + x3)*x + x2)x+x1)*x + x0

halt    J      halt

x	WORD 2
x4	WORD 1
x3	WORD 2
x2	WORD 3
x1	WORD 4
x0	WORD 5
result  RESW 1
	END horner