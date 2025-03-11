poly	START 0

	LDA x			
	MUL x
	MUL x
	MUL x     . cetrta potenca

	STA result		. x^4

	LDA x			. A je edini register s katerim lahko računamo
	MUL x
	MUL x			.
	MUL #2
	ADD result		. . x^4 + 2x^3
	STA result		

	LDA x
	MUL x
	MUL #3
	ADD result		. x^4 + 2x^3 + 3*x^2 
	STA result

	LDA x
	MUL #4
	ADD result
	STA result 		. x^4 + 2x^3 + 3*x^2 + 4*x

	LDA result		. . x^4 + 2x^3 + 3*x^2 + 4*x
	ADD #5			. x^4 + 2x^3 + 3*x^2 + 4*x + 5
	STA result		
	
	.
	.
	.


halt	J halt

x	WORD 2
result	RESW 1		. simboli so lahko do 6 znakov dolgi
	END poly