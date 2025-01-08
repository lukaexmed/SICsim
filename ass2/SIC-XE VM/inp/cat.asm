cat	START 0


read	TD	stdin		.preverimo stdin
	JEQ	read		. ce je prazn se enkrat preverimo
	RD	stdin		.preberemo device
	STCH	store		.shranimo prebrano v store


write	TD 	stdout		.preverimo stdout
	JEQ	write		. ce ni ready se enkrat preverimo
	LDCH	store		. v A shranimo to kaj smo prej prebrali
	WD	stdout		. napisemo iz Aja 
	LDA	#0		.resetiramo store
	STA	store
	J	read		.loopamo nazaj na read


halt 	J	halt

stdin	BYTE 	0
stdout	BYTE 	1
store	RESB 	1

	END cat