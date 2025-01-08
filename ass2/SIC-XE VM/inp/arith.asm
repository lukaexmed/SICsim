prog  	START  0
	LDA x		. v register A nalozimo x
	ADD y		. add deluje z registrom A
	STA sum 	. shranimo A v sum
    
.registrsko registerski

	
	LDA x
	SUB y
	STA diff

	LDA x
	DIV y
	STA quot

	MUL y
	STA temp .v temp je zdaj (x / y) * y
	LDA x		. v A je zdaj x
	SUB temp	. odštejemo a in dobimo x%y
	STA mod

	LDA x
	MUL y
	STA prod





halt    J      halt


x 	WORD 24		.vhodni
y	WORD 5
sum	RESW 1		.izhodni
diff	RESW 1
quot	RESW 1
temp	RESW 1
prod	RESW 1
mod	RESW 1
        END    prog

