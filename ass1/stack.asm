stack	START 0

stackinit
	LDA 	#sklad		.inicializiramo stackPt na zacetni naslov sklada
	STA	skladPt
	RSUB


stackpush
	.STA	@stackPtr
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
	.LDA	@stackPtr
	RSUB




skladPt	RESW	1
sklad	RESW	255	.velikost sklada
temp	RESW	1	
	END stack