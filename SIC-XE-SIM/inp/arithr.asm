.registrsko registrski

prog  	START  0
	LDS x
	LDT y
	COMPR S, T
	JLT ELSE  . jump ce je x less or equal to y
.koda ce je x > y
		LDT y
		RMO S, A    . v A damo S
		RMO T, S   . v S damo T
		RMO A, T . v T damo A
. v S bo zdaj manjsi
	J ENDIF


ELSE	LDT y
	



.x mora bit manjsi od y

		
ENDIF	ADDR S, T		. addr shrani v T (desnega)
	STT sum

	SUBR S, T             . ker smo ze prej sesteli mormo dvakrat odstet
	SUBR S, T
	STT diff		. subr shrani v S (levega)
	
	ADDR S, T		. nazaj sestejemo imamo v T spet y
	RMO T, A		. v A je T

	MULR S, T
	STT prod
	DIVR S, T

	DIVR S, T		. T / S
	STT quot		. v T je T / S


	MULR S, T		. v T je T/S * S
	SUBR T, A		. V A je  A(T) - T/S * S
	STA mod			

	
halt    J      halt


x 	WORD 10	.vhodni
y	WORD 3
sum	RESW 1		.izhodni
diff	RESW 1
quot	RESW 1
prod	RESW 1
mod	RESW 1
        END    prog
