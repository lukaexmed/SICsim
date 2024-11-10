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



scree	WORD	X'00B800'
scrcols	WORD	80 .80crk
scrrows	WORD	25 .25crk
scrlen	RESW	1
scrend	RESW	1
targt	RESW	1
col	WORD	40			.v sredini izpise
row	WORD	12
stdin	WORD	0

	END screen