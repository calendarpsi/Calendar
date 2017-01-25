package com.psi.calendar;

public class CalculoFitness {

	static byte[] solution = new byte[64];

	/* Public methods */

	/* */
	/**
	 * Calculate individuals fitness. We must compare our ideal timetable with the real one.
	 * Our ideal timetable ("matriz de disponibilidad") was calculated previously but the real 
	 * one for this individual should be calculated.
	 * @param individuo
	 * @return
	 */
	static int getFitness(IndividuoHorario individuo) {
		//TODO repasar y añadir retoques como resta si hay horas sueltas ir el usuario no quiere etc...
		int fitness = 0;
		int[] gen4add;
		int[][] horarioGen;
		Horario obHorario=new Horario();

		horarioGen=(Algorithm.getElHorario()).crearMat_vacia();//we get an empty timetable
		for (int i = 0; i < individuo.size();i++){//for(each class in the timetable)	
			gen4add=individuo.genomaWithCeros(i, individuo.getGene(i), individuo.size());
			horarioGen=(Algorithm.getElHorario()).addHorario(gen4add, horarioGen);//we add the class to the timetable
		}
		horarioGen=ponderarMatriz(horarioGen, individuo.size());
		fitness=obHorario.sumarMat(horarioGen);
		return fitness;
		//horarioGen=obHorario.crearMat_vacia();//T ODO cambiar
		//horarioGen=obHorario.addHorario(gen4add,horarioGen); 
	}
	
	
	static int[][] ponderarMatriz(int[][] horario,int size){
		int[][] matrizdisp;
		
		matrizdisp=(Algorithm.getElHorario()).getMat_disponibilidad();
		for(int i=0;i<23;i++){
			for(int j=0;j<5;j++){
				horario[i][j]=matrizdisp[i][j]*horario[i][j];
			}
		}
		return horario;
	}

	// Get optimum fitness
	static int getMaxFitness() {
		int maxFitness = solution.length;
		return maxFitness;
	}
}





// Calculate inidividuals fittness by comparing it to our candidate solution
// COD ORIGINAL: Loop through our individuals genes and compare them to our cadidates
/*for (int i = 0; i < individuo.size() && i < solution.length; i++) {
    if (individuo.getGene(i) == solution[i]) {
        fitness++;

    } 
}*/

/*
// Set a candidate solution as a byte array
public static void setSolution(byte[] newSolution) {
    solution = newSolution;
}

// To make it easier we can use this method to set our candidate solution 
// with string of 0s and 1s
static void setSolution(String newSolution) {
    solution = new byte[newSolution.length()];
    // Loop through each character of our string and save it in our byte 
    // array
    for (int i = 0; i < newSolution.length(); i++) {
        String character = newSolution.substring(i, i + 1);
        if (character.contains("0") || character.contains("1")) {
            solution[i] = Byte.parseByte(character);
        } else {
            solution[i] = 0;
        }
    }
}

 */   