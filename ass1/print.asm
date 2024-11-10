print	START 0
	LDX #0			.zacetni indeks stringa
	LDT #6			.koncni index stringa


pisi	TD	dev		. preverimo ce pisemo na zaslon
	JEQ	pisi		. ce ne mormo se enkrat preverimo
	LDCH	besed, X	. v A nalozi del besede ki korespondira indeksu X
	WD	dev		. A napise na zaslon
	TIXR	T		. inkrementira X in ga primerja s T
	JLT	pisi		. če je X manjš od T skoči nazaj na piši


nl	TD	dev
	JEQ	nl
	LDCH	newline		.nalozi kodo za newline v akumulator
	WD	dev



halt	J halt 			. s tem nasiloma zaciklamo




besed	BYTE C'SIC/XE'

newline	BYTE	10
space	BYTE	32
stdin	BYTE	0
stdout	BYTE	1
dev	BYTE	X'AA'		. v AA.dev se zapise SIC/XE\n

	END print