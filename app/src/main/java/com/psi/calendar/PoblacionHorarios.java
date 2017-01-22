package com.psi.calendar;

public class PoblacionHorarios {
	IndividuoHorario[] individuos;

	/*
	 * Constructors
	 */
	// Create a population
	/**
	 * It creates a population object. A population is group of individuals.
	 * @param tamanoPoblacion
	 * Number of individuals that makes up a population.
	 * @param iniciar
	 * If it is true, it creates all the individual that makes up the population (each individual is a 
	 * possible timetable). If not, it only creates the empty array to of individuals.
	 */
	public PoblacionHorarios(int tamanoPoblacion, boolean iniciar) {
		individuos = new IndividuoHorario[tamanoPoblacion];
		//boolean valido=false;
		
		// First population: it will be calculated randomly
		if (iniciar) {
			// Loop to create individuals
			for (int i = 0; i < tamano(); i++) {//tamano: the number os indivuduals that make up a population.
				System.out.println("Se va a generar el individuo "+i);
				IndividuoHorario nuevoIndividuo = new IndividuoHorario();
				nuevoIndividuo.generateIndividual();//generated randomly but then we have to make sure that the timetable doesn't overlap
				saveIndividual(i, nuevoIndividuo); //"individuo" saved in for the new population
				System.out.println("Se ha generado el individuo: "+i);
			}
		}
	}


	/* Getters */
	/**
	 * Gets the individual on the indicated index of our population.
	 * @param index
	 * The index of the population where the individual we want to retrieve is.
	 * @return
	 * The individual we want.
	 */
	public IndividuoHorario getIndividual(int index) {
		return individuos[index];
	}

	
	/**
	 * Gets the best individual (timetable) of the population.
	 * @return
	 * The best individual.
	 */
	public IndividuoHorario getFittest() {
		IndividuoHorario fittest = individuos[0];//porque no: IndividuoHorario fittest=getIndividual(0);
		// Loop through individuals to find fittest
		for (int i = 0; i < tamano(); i++) {
			if (fittest.getFitness() <= getIndividual(i).getFitness()) {
				fittest = getIndividual(i);
			}
		}
		return fittest;
	}

	/* Public methods */
	
	/**
	 * Gets population size.
	 * @return
	 * The size of the population.
	 */
	public int tamano() {
		return individuos.length;
	}


	/**
	 * Puts inside the population in the index we want the individual we want to save.
	 * @param index
	 * The index of the population where we want our individual to be. 
	 * @param indiv
	 * The individual we want to save.
	 */
	public void saveIndividual(int index, IndividuoHorario indiv) {
		individuos[index] = indiv;
	}
}
