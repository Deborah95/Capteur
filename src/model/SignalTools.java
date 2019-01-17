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

	// Methodes : moyenne, variance, maximum, démodulation(delta), détection des
	// pics d'un signal (find_peaks)
	public double moyenne() {
		int i;
		int taille = this.signal.get_size();
		double somme = 0;
		for (i = 0; i < taille; i++) {
			somme += this.signal.get_echantillon(i);
		}
		return somme / (double) taille;
	}

	public double variance() {
		int i;
		int taille = this.signal.get_size();
		double moyenne = this.moyenne();
		double somme2 = 0;
		for (i = 0; i < taille; i++) {
			somme2 += Math.pow(this.signal.get_echantillon(i) - moyenne, 2);
		}
		return somme2 / (double) taille;
	}

	public double maximum() {
		int i;
		int taille = this.signal.get_size();
//		System.out.println("Taille :" + taille);
		double max = Signal._DEFAULT_PRESSURE;
		for (i = 0; i < taille; i++) {
			if (this.signal.get_echantillon(i) > max) {
				max = this.signal.get_echantillon(i);
				// System.out.println("Max :" + max);
			}
		}
		return max;
	}

	public int pos_maximum() {
		int i;
		int taille = this.signal.get_size();
		int pos_max = 0;
		double max = Signal._DEFAULT_PRESSURE;
		for (i = 0; i < taille; i++) {
			if (this.signal.get_echantillon(i) > max) {
				max = this.signal.get_echantillon(i);
				pos_max = i;
			}
		}
		return pos_max;
	}

	public Signal delta() {
		int i;
		int taille = this.signal.get_size();
		Signal dsignal = new Signal();
		if (taille > 0) {
			double dernier = this.signal.get_echantillon(taille - 1);
			double premier = this.signal.get_echantillon(0);
			double pente = (dernier - premier) / taille;
			for (i = 0; i < taille; i++) {
				dsignal.add_echantillon_end((this.signal.get_echantillon(i) + pente * (taille - i)));
				System.out.println(dsignal.get_echantillon(i));
			}
		}
		return dsignal;
	}

	public double[][] find_peaks() {
		int taille = this.signal.get_size();
		double tab[][] = new double[2][taille];
		int i = 1;
		int j = 0;
		for (i = 1; i < taille - 1; i++) {
			if (this.signal.get_echantillon(i) > this.signal.get_echantillon(i - 1)
					&& this.signal.get_echantillon(i) > this.signal.get_echantillon(i + 1)) {
				tab[0][j] = this.signal.get_echantillon(i);
				tab[1][j] = (double) i;
				j++;
			}
		}
		return tab;
	}
}
