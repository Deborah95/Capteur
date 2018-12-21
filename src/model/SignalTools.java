package model;

import java.util.Vector;

public class SignalTools {
	Signal signal;
	
	// Constructeurs : Initialise un signal fourni en argument
	public SignalTools(Signal signal) {
		this.signal = signal;
	}
	
	public SignalTools(Vector<Double> signal) {
		this.signal.echantillons = signal;
	}
	
	// Methodes : moyenne, variance
	public double moyenne() {
		int i;
		int taille = this.signal.echantillons.size();
		double somme = 0;
		for(i=0;i<taille;i++) {
			somme += this.signal.echantillons.get(i);
		}
		return somme/(double)taille;
	}
	
	public double variance() {
		int i;
		int taille = this.signal.echantillons.size();
		double moyenne = this.moyenne();
		double somme2 = 0;
		for(i=0;i<taille;i++) {
			somme2 += Math.pow(this.signal.echantillons.get(i)-moyenne, 2);
		}
		return somme2/(double)taille;		
	}
}
